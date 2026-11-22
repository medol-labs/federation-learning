"""从项目内的 Word 基线重放历次定稿修改。"""

import argparse
from io import BytesIO
from pathlib import Path
from zipfile import ZipFile

from docx import Document

from revisions import (
    acceptance_interfaces_deployment,
    alignment,
    business_ids,
    cross_references,
    q11,
    start_checks,
)

BASELINES = Path(__file__).resolve().parent / "baselines"
DOCUMENTS = {
    "srs": (
        "srs-v0.11.docx",
        "联邦学习模块 软件需求规格说明（V0.12 业务流程版）.docx",
    ),
    "sdd": (
        "sdd-v0.3.docx",
        "联邦学习模块 软件设计说明（GB-T 8567—2006）.docx",
    ),
    "dbdd": (
        "dbdd-v0.2.docx",
        "联邦学习模块 数据库（顶层）设计说明（GB-T 8567—2006）.docx",
    ),
}
REVISIONS = (
    (alignment, ("srs", "sdd", "dbdd")),
    (business_ids, ("srs", "sdd", "dbdd")),
    (cross_references, ("srs", "dbdd")),
    (acceptance_interfaces_deployment, ("srs", "sdd", "dbdd")),
    (q11, ("srs", "sdd")),
    (start_checks, ("srs", "sdd")),
)
BODY = "word/document.xml"
CORE_PROPERTIES = "docProps/core.xml"


def replay(kind, baseline):
    data = baseline.read_bytes()
    for revision, kinds in REVISIONS:
        if kind not in kinds:
            continue
        document = Document(BytesIO(data))
        revision.apply(kind, document)
        updated_parts = (BODY, CORE_PROPERTIES) if revision is alignment else (BODY,)
        edited = BytesIO()
        document.save(edited)
        output = BytesIO()
        with (
            ZipFile(BytesIO(data)) as original,
            ZipFile(edited) as updated,
            ZipFile(output, "w") as archive,
        ):
            for entry in original.infolist():
                source = updated if entry.filename in updated_parts else original
                archive.writestr(entry, source.read(entry.filename))
        data = output.getvalue()
    return data


def verify(data, reference):
    with ZipFile(BytesIO(data)) as actual, ZipFile(reference) as expected:
        actual_names = set(actual.namelist())
        expected_names = set(expected.namelist())
        differences = [
            name
            for name in sorted(actual_names | expected_names)
            if name not in actual_names
            or name not in expected_names
            or actual.read(name) != expected.read(name)
        ]
    if differences:
        raise ValueError(f"{reference.name} 的包内差异：{', '.join(differences)}")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--output-dir", type=Path, required=True, help="存放三份结果的新目录")
    parser.add_argument("--verify-against", type=Path, help="包含同名定稿的目录，仅用于比对")
    args = parser.parse_args()

    if args.output_dir.exists():
        parser.error(f"输出目录已存在：{args.output_dir}")

    try:
        results = {
            name: replay(kind, BASELINES / baseline)
            for kind, (baseline, name) in DOCUMENTS.items()
        }
        if args.verify_against:
            for name, data in results.items():
                verify(data, args.verify_against / name)
        args.output_dir.mkdir(parents=True)
        for name, data in results.items():
            path = args.output_dir / name
            with path.open("xb") as file:
                file.write(data)
            print(path.resolve())
    except (OSError, ValueError) as error:
        parser.exit(1, f"{error}\n")


if __name__ == "__main__":
    main()
