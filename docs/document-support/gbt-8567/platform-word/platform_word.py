"""在平台 Word 文档和 JSON 正文包之间转换。"""

import argparse
from hashlib import sha256
from io import BytesIO
import json
from pathlib import Path
import re
import subprocess
from zipfile import BadZipFile, ZipFile

from lxml import etree


WORD = "http://schemas.openxmlformats.org/wordprocessingml/2006/main"
DRAWING = "http://schemas.openxmlformats.org/drawingml/2006/main"
MATH = "http://schemas.openxmlformats.org/officeDocument/2006/math"
TEXT_TAGS = {f"{{{namespace}}}t" for namespace in (WORD, DRAWING, MATH)}
TEXT_TAGS.add(f"{{{WORD}}}delText")
PARAGRAPH_TAGS = {f"{{{WORD}}}p", f"{{{DRAWING}}}p", f"{{{MATH}}}oMathPara"}
XML_SPACE = "{http://www.w3.org/XML/1998/namespace}space"
FORMAT_VERSION = 1
PERFORMANCE_HEADING = "联邦学习模块性能设计"
PERFORMANCE_LABELS = ("查询响应", "命令与事件处理", "参与方协作规模")
REMOVED_PERFORMANCE_LABEL = "（4）训练与聚合耗时。"


def text_groups(root):
    groups = {}
    for node in root.iter(*TEXT_TAGS):
        if node.text is None:
            continue
        paragraph = next(
            (parent for parent in node.iterancestors() if parent.tag in PARAGRAPH_TAGS),
            node.getparent(),
        )
        groups.setdefault(paragraph, []).append(node)
    return list(groups.values())


def slot(group, run):
    return f"__PLATFORM_WORD_TEXT_{group}_{run}__"


def xml_tree(data):
    return etree.fromstring(data, parser=etree.XMLParser(resolve_entities=False))


def serialize(root):
    # Word parts ordinarily use a UTF-8 XML declaration with standalone="yes".
    info = root.getroottree().docinfo
    return etree.tostring(
        root, encoding="UTF-8", xml_declaration=True, standalone=info.standalone
    )


def word_xml(archive):
    for name in archive.namelist():
        if name.startswith("word/") and name.endswith((".xml", ".vml")):
            yield name, xml_tree(archive.read(name))


def write_archive(archive, replacements):
    output = BytesIO()
    with ZipFile(output, "w") as word:
        for entry in archive.infolist():
            name = entry.filename
            data = replacements[name] if name in replacements else archive.read(name)
            word.writestr(entry, data)
    return output.getvalue()


def extract(input_file, bundle):
    if bundle.exists():
        raise ValueError(f"正文包目录已存在：{bundle}")
    replacements = {}
    parts = {}
    with ZipFile(input_file) as source:
        if "word/document.xml" not in source.namelist():
            raise ValueError("输入文件缺少 Word 正文")
        for name, root in word_xml(source):
            groups = text_groups(root)
            if not groups:
                continue

            entries = []
            for group, nodes in enumerate(groups):
                texts = [node.text for node in nodes]
                entries.append(texts[0] if len(nodes) == 1 else texts)
                for run, node in enumerate(nodes):
                    node.text = slot(group, run)
            parts[name] = entries
            replacements[name] = serialize(root)
        layout = write_archive(source, replacements)

    bundle.mkdir(parents=True)
    (bundle / "layout.docx").write_bytes(layout)
    content = {
        "format": FORMAT_VERSION,
        "source": input_file.name,
        "layout_sha256": sha256(layout).hexdigest(),
        "parts": parts,
    }
    (bundle / "text.json").write_text(
        json.dumps(content, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )


def fill_part(groups, entries, name):
    if not isinstance(entries, list) or len(groups) != len(entries):
        raise ValueError(f"{name} 的正文段落数量与版式不一致")
    for group, (nodes, entry) in enumerate(zip(groups, entries)):
        texts = [entry] if isinstance(entry, str) else entry
        if not isinstance(texts, list) or len(texts) != len(nodes):
            raise ValueError(f"{name} 第 {group + 1} 组的文字片段数量与版式不一致")
        for run, (node, value) in enumerate(zip(nodes, texts)):
            if node.text != slot(group, run) or not isinstance(value, str):
                raise ValueError(f"{name} 第 {group + 1} 组的文字片段与版式不匹配")
            node.text = value
            if value != value.strip():
                node.set(XML_SPACE, "preserve")


def performance_entries(entries):
    start = entries.index(PERFORMANCE_HEADING) + 1
    end = start + len(PERFORMANCE_LABELS)
    items = entries[start:end]
    if len(items) != len(PERFORMANCE_LABELS) or not all(
        isinstance(value, str) and value.startswith(f"（{number}）{label}。")
        for number, (value, label) in enumerate(zip(items, PERFORMANCE_LABELS), 1)
    ):
        raise ValueError("正文包缺少联邦学习性能设计三项内容")
    return items, end


def read_layout(bundle, content):
    path = bundle / "layout.docx"
    if path.is_file():
        return path.read_bytes()

    # Existing layouts can be recovered from the Git history that already contains them.
    blob = content.get("layout_git_blob")
    if isinstance(blob, str) and re.fullmatch(r"[0-9a-f]{40}", blob):
        try:
            return subprocess.run(
                ["git", "cat-file", "blob", blob], cwd=bundle,
                stdout=subprocess.PIPE, stderr=subprocess.DEVNULL, check=True,
            ).stdout
        except (OSError, subprocess.CalledProcessError):
            pass
    raise ValueError(f"缺少版式文件：{path}。请从共享文档存储获取与正文 JSON 配套的版本。")


def build(bundle, output, *, interfaces=False):
    if output.exists():
        raise ValueError(f"输出文件已存在：{output}")
    if output.suffix.lower() != ".docx":
        raise ValueError("输出文件须为 .docx")

    content = json.loads((bundle / "text.json").read_text(encoding="utf-8"))
    if not isinstance(content, dict) or content.get("format") != FORMAT_VERSION:
        raise ValueError("无法识别正文包格式")
    parts = content.get("parts")
    if not isinstance(parts, dict) or "word/document.xml" not in parts:
        raise ValueError("正文包缺少 Word 正文")
    layout = read_layout(bundle, content)
    if sha256(layout).hexdigest() != content.get("layout_sha256"):
        raise ValueError("JSON 与版式文件不配套")

    with ZipFile(BytesIO(layout)) as template:
        replacements = {}
        for name, root in word_xml(template):
            groups = text_groups(root)
            if not groups:
                continue
            if name not in parts:
                raise ValueError(f"JSON 缺少 {name} 的文字")
            fill_part(groups, parts[name], name)
            if name == "word/document.xml" and bundle.resolve() == Path(__file__).resolve().with_name("sdd"):
                # The layout pack still has a slot for the retired fourth item.
                _, retired = performance_entries(parts[name])
                node = groups[retired][0]
                paragraph = next(node.iterancestors(f"{{{WORD}}}p"))
                if parts[name][retired] != "" or paragraph.getparent().tag != f"{{{WORD}}}body":
                    raise ValueError("无法移除已废弃的性能设计段落")
                paragraph.getparent().remove(paragraph)
            replacements[name] = serialize(root)
        if replacements.keys() != parts.keys():
            raise ValueError("JSON 含有版式中不存在的文字部件")
        result = write_archive(template, replacements)

    if interfaces:
        from sdd_chapter3 import revise_chapter3_decisions
        from sdd_detail import revise_detail
        from sdd_identifiers import revise_identifiers
        from sdd_interfaces import insert_interfaces
        from sdd_io_decisions import revise_io_decisions
        from sdd_physical_storage import revise_physical_storage
        from sdd_trace import revise_trace
        for revise in (revise_io_decisions, revise_chapter3_decisions, revise_physical_storage, insert_interfaces,
                       revise_detail, revise_trace, revise_identifiers):
            result = revise(result)

    output.parent.mkdir(parents=True, exist_ok=True)
    with output.open("xb") as file:
        file.write(result)


def revise_performance(source):
    part = "word/document.xml"
    bundle = Path(__file__).resolve().with_name("sdd")
    content = json.loads((bundle / "text.json").read_text(encoding="utf-8"))["parts"][part]
    replacements, _ = performance_entries(content)

    with ZipFile(BytesIO(source)) as archive:
        root = xml_tree(archive.read(part))
        body = root.find(f"{{{WORD}}}body")
        paragraph = f"{{{WORD}}}p"

        def value(node):
            return "".join(t.text or "" for t in node.iter(f"{{{WORD}}}t")).strip()

        anchors = [node for node in body.findall(paragraph) if value(node) == PERFORMANCE_HEADING]
        if len(anchors) != 1:
            raise ValueError("未找到唯一的联邦学习模块性能设计标题")
        block = []
        node = None
        for node in anchors[0].itersiblings():
            if node.tag != paragraph or value(node) == "CSCI详细设计":
                break
            if value(node):
                block.append(node)
        if len(block) == len(replacements) + 1 and value(block[-1]).startswith(REMOVED_PERFORMANCE_LABEL):
            body.remove(block.pop())
        if (len(block) != len(replacements) or node is None or value(node) != "CSCI详细设计"
                or not all(value(item).startswith(f"（{number}）{label}。")
                           for number, (item, label) in enumerate(zip(block, PERFORMANCE_LABELS), 1))):
            raise ValueError("联邦学习模块性能设计的段落不符合预期")

        for node, replacement in zip(block, replacements):
            if value(node) == replacement:
                continue
            runs = [t for t in node.iter(f"{{{WORD}}}t") if t.text]
            runs[0].text = replacement
            for run in runs[1:]:
                run.text = ""
        return write_archive(archive, {part: serialize(root)})


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    commands = parser.add_subparsers(dest="command", required=True)
    extraction = commands.add_parser("extract", help="从 Word 提取正文和版式")
    extraction.add_argument("--input", type=Path, required=True)
    extraction.add_argument("--bundle", type=Path, required=True, help="新的正文包目录")
    generation = commands.add_parser("build", help="从正文包生成 Word")
    generation.add_argument("--bundle", type=Path, required=True)
    generation.add_argument("--output", type=Path, required=True, help="新的 Word 路径")
    generation.add_argument("--base-only", action="store_true", help="仅还原版式包，不附加软设接口章节")
    for name, description in (
        ("revise-io-decisions", "只更新现有软设 Word 的 3.1.1 联邦学习输入/输出设计决策"),
        ("revise-chapter3-decisions", "更新现有软设 Word 中 3.2 至 3.4 的联邦学习设计决策"),
        ("revise-federation-decisions", "只补充现有软设 Word 中联邦学习行为和数据呈现决策"),
        ("remove-security-decisions", "移除现有软设 Word 的 3.3.1 联邦学习安全与保密设计决策及目录项"),
        ("revise-maintainability-decisions", "只更新现有软设 Word 的 3.4.1 联邦学习扩展性、可维护性与故障恢复"),
        ("revise-physical-storage", "只补充现有软设 Word 的联邦学习物理存储清单"),
        ("revise-interfaces", "更新现有软设 Word 的接口分类、清单和契约"),
        ("classify-interfaces", "调整现有软设 Word 的接口归类与引用，保留联邦学习契约正文"),
        ("revise-interface-outline", "只调整现有软设 Word 的 4.5 标题与目录层级"),
        ("revise-interface-layout", "只重排现有软设 Word 的 4.5 接口设计版式"),
        ("revise-interface-descriptions", "只重排现有软设 Word 的接口说明"),
        ("revise-interface-terms", "只补全现有软设 Word 的联邦学习接口术语翻译"),
        ("revise-interface-ids", "统一现有软设 Word 的联邦学习接口标识及引用"),
        ("revise-identifiers", "统一现有软设 Word 的联邦学习部件和部署单元标识"),
        ("revise-performance", "只更新现有软设 Word 的联邦学习模块性能设计"),
        ("restore-detail", "只补全现有软设 Word 中空缺的联邦学习模块详细设计"),
        ("revise-detail", "只更新现有软设 Word 的联邦学习模块详细设计"),
        ("revise-detail-review", "更新现有软设 Word 的联邦学习接口、详细设计及追溯"),
    ):
        revision = commands.add_parser(name, help=description)
        revision.add_argument("--input", type=Path, required=True, help="已生成的软设 Word")
        revision.add_argument("--output", type=Path, required=True, help="新的 Word 路径")
    args = parser.parse_args()
    try:
        if args.command == "extract":
            extract(args.input, args.bundle)
            print(args.bundle.resolve())
        elif args.command == "build":
            sdd = Path(__file__).resolve().with_name("sdd")
            build(args.bundle, args.output, interfaces=args.bundle.resolve() == sdd and not args.base_only)
            print(args.output.resolve())
        else:
            if args.output.suffix.lower() != ".docx":
                raise ValueError("输出文件须为 .docx")
            if args.command == "revise-performance":
                revise = revise_performance
            elif args.command == "revise-io-decisions":
                from sdd_io_decisions import revise_io_decisions
                revise = revise_io_decisions
            elif args.command == "revise-chapter3-decisions":
                from sdd_chapter3 import revise_chapter3_decisions
                revise = revise_chapter3_decisions
            elif args.command == "revise-federation-decisions":
                from sdd_federation_decisions import revise_federation_decisions
                revise = revise_federation_decisions
            elif args.command == "remove-security-decisions":
                from sdd_chapter3 import remove_security_decisions
                revise = remove_security_decisions
            elif args.command == "revise-maintainability-decisions":
                from sdd_chapter3 import revise_maintainability_decisions
                revise = revise_maintainability_decisions
            elif args.command == "revise-physical-storage":
                from sdd_physical_storage import revise_physical_storage
                revise = revise_physical_storage
            elif args.command == "restore-detail":
                from sdd_detail import revise_detail
                revise = revise_detail
            elif args.command == "revise-detail":
                from sdd_detail import revise_detail
                from sdd_trace import revise_trace

                def revise(source):
                    return revise_trace(revise_detail(source, refresh=True))
            elif args.command == "revise-interface-outline":
                from sdd_interfaces import revise_interface_outline
                revise = revise_interface_outline
            elif args.command == "revise-interface-layout":
                from sdd_interface_layout import revise_interface_layout
                revise = revise_interface_layout
            elif args.command == "revise-interface-descriptions":
                from sdd_interface_descriptions import revise_interface_descriptions
                revise = revise_interface_descriptions
            elif args.command == "revise-interface-terms":
                from sdd_interface_terms import revise_interface_terms
                revise = revise_interface_terms
            elif args.command == "revise-interface-ids":
                from sdd_interface_ids import revise_interface_ids
                revise = revise_interface_ids
            elif args.command == "revise-identifiers":
                from sdd_identifiers import revise_identifiers
                revise = revise_identifiers
            elif args.command == "revise-detail-review":
                from sdd_detail import revise_detail
                from sdd_interfaces import insert_interfaces
                from sdd_trace import revise_trace

                def revise(source):
                    return revise_trace(revise_detail(insert_interfaces(source), refresh=True))
            else:
                from sdd_interfaces import insert_interfaces, revise_interface_classification
                revise = insert_interfaces if args.command == "revise-interfaces" else revise_interface_classification
            result = revise(args.input.read_bytes())
            args.output.parent.mkdir(parents=True, exist_ok=True)
            with args.output.open("xb") as file:
                file.write(result)
            print(args.output.resolve())
    except (OSError, BadZipFile, ValueError, KeyError, etree.XMLSyntaxError) as error:
        parser.exit(1, f"{error}\n")


if __name__ == "__main__":
    main()
