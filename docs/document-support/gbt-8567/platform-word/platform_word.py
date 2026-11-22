"""在平台 Word 文档和 JSON 正文包之间转换。"""

import argparse
from hashlib import sha256
from io import BytesIO
import json
from pathlib import Path
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


def build(bundle, output):
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
    layout = (bundle / "layout.docx").read_bytes()
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
            replacements[name] = serialize(root)
        if replacements.keys() != parts.keys():
            raise ValueError("JSON 含有版式中不存在的文字部件")
        result = write_archive(template, replacements)

    output.parent.mkdir(parents=True, exist_ok=True)
    with output.open("xb") as file:
        file.write(result)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    commands = parser.add_subparsers(dest="command", required=True)
    extraction = commands.add_parser("extract", help="从 Word 提取正文和版式")
    extraction.add_argument("--input", type=Path, required=True)
    extraction.add_argument("--bundle", type=Path, required=True, help="新的正文包目录")
    generation = commands.add_parser("build", help="从正文包生成 Word")
    generation.add_argument("--bundle", type=Path, required=True)
    generation.add_argument("--output", type=Path, required=True, help="新的 Word 路径")
    args = parser.parse_args()
    try:
        if args.command == "extract":
            extract(args.input, args.bundle)
            print(args.bundle.resolve())
        else:
            build(args.bundle, args.output)
            print(args.output.resolve())
    except (OSError, BadZipFile, ValueError, KeyError, etree.XMLSyntaxError) as error:
        parser.exit(1, f"{error}\n")


if __name__ == "__main__":
    main()
