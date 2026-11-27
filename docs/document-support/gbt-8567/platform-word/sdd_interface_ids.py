"""Stable document identifiers for federation learning resource contracts."""

import json
import re
from io import BytesIO
from pathlib import Path

PREFIX = "LBLS-JK-NB"
LEGACY_ID = r"E-[0-9A-F]{8}"
NUMBERED_ID = rf"{PREFIX}-[A-Z]+-\d{{3}}"
PURPOSE_ID = rf"{PREFIX}-[A-Z]+-[A-Z][A-Z0-9]*"
INTERFACE_ID = rf"(?:{PURPOSE_ID}|{NUMBERED_ID}|{LEGACY_ID})"
OVERVIEW = (
    "表 137 给出内外部接口的分类与标识规则。联邦学习接口采用 LBLS-JK-NB-模块缩写-功能缩写，"
    "模块缩写表示业务归属，训练引擎和聚合引擎分别使用 TRNENG 和 AGGENG。"
    "功能缩写表示契约用途，在所属模块内唯一；同一资源契约的多项操作共用标识。"
    "对外使用已有接口时引用原编号，已有接口变更时保留标识。"
)
TABLE_RULE = "既有 JCYL-JK-NB-{功能缩写}；联邦学习 LBLS-JK-NB-{模块缩写}-{功能缩写}"
OLD_TABLE_RULES = {
    "既有 JCYL-JK-NB-{功能缩写}；联邦学习沿用 E- 编号",
    "既有 JCYL-JK-NB-{功能缩写}；联邦学习 LBLS-JK-NB-{模块缩写}-{序号}",
}
OLD_DIAGRAM_REFERENCE = "查找 E- 编号"
DIAGRAM_REFERENCE = "查找联邦学习接口标识"

CONTEXT_CODES = {
    "organizationmanagement": "ORG", "federationmanagement": "FED", "runtimeprovisioning": "CONF",
    "runtimegovernance": "GOV", "datasetgovernance": "DATA", "modelrepository": "REPO",
    "trainingorchestration": "TRAIN", "secureaggregation": "AGG", "runtimemonitoring": "MON",
    "runtimeagentoperations": "AGENT", "fileupload": "FILE", "dictionarymaintenance": "DICT",
    "systemnotification": "SN", "dataexchange": "DE", "identityaccessmanagement": "IAM",
}
RESOURCE_CODES = {
    "RuntimeAgentEventResource.kt": "DATA", "DataExportJobResource.kt": "DE",
    "MeResource.kt": "IAM", "IamAdminSetupResource.kt": "IAM", "IamAuthResource.kt": "IAM",
    "StoredFileContentResource.kt": "FILE",
}


def owner_code(item):
    route, _ = item["contracts"][0]
    if item["provider"] in ("训练引擎", "聚合引擎"):
        return "TRNENG" if item["provider"] == "训练引擎" else "AGGENG"
    code = CONTEXT_CODES.get(route["context"]) or RESOURCE_CODES.get(Path(item["source"]).name)
    if not code:
        raise ValueError(f"Missing module assignment for {item['source']}")
    return code


def identifier_aliases(*, include_numbered=False):
    groups = json.loads(Path(__file__).with_name("sdd-interface-numbers.json").read_text(encoding="utf-8"))
    aliases = {}
    identifiers = set()
    for owner, entries in groups.items():
        if not re.fullmatch(r"[A-Z]+", owner) or len(entries) > 999:
            raise ValueError(f"Invalid interface numbering group: {owner}")
        for number, (legacy, purpose) in enumerate(entries, 1):
            if not re.fullmatch(LEGACY_ID, legacy) or legacy in aliases:
                raise ValueError(f"Invalid or repeated interface registry key: {legacy}")
            identifier = f"{PREFIX}-{owner}-{purpose}"
            if not re.fullmatch(PURPOSE_ID, identifier) or identifier in identifiers:
                raise ValueError(f"Invalid or repeated interface purpose identifier: {identifier}")
            identifiers.add(identifier)
            aliases[legacy] = identifier
            if include_numbered:
                aliases[f"{PREFIX}-{owner}-{number:03}"] = identifier
    return aliases


def apply_interface_ids(items):
    aliases = identifier_aliases()
    for item in items:
        legacy = item["id"]
        owner = owner_code(item)
        if (identifier := aliases.get(legacy)) is None:
            raise ValueError(f"Register {legacy} under {owner}: {item['name']}")
        if not identifier.startswith(f"{PREFIX}-{owner}-"):
            raise ValueError(f"Interface owner mismatch for {identifier}: {owner}")
        item["id"] = identifier


def revise_interface_ids(source):
    from docx import Document
    from docx.oxml.ns import qn
    from docx.table import Table

    from sdd_interface_layout import format_table
    from sdd_interface_terms import replace_labels, text
    from sdd_interfaces import save_section

    doc = Document(BytesIO(source))
    aliases = identifier_aliases(include_numbered=True)
    body = doc.element.body
    pattern = re.compile(rf"(?<![A-Z0-9_-])(?:{LEGACY_ID}|{NUMBERED_ID})(?![A-Z0-9_-])")
    for p in body.iter(qn("w:p")):
        missing = set(pattern.findall(text(p))) - aliases.keys()
        if missing:
            raise ValueError(f"Unregistered interface reference: {sorted(missing)}")
        replace_labels(p, pattern, aliases)
        value = text(p)
        if value.startswith("表 137 给出内外部接口的分类与标识规则。"):
            replace_labels(p, re.compile(re.escape(value)), {value: OVERVIEW})
        elif value in OLD_TABLE_RULES:
            replace_labels(p, re.compile(re.escape(value)), {value: TABLE_RULE})
        elif OLD_DIAGRAM_REFERENCE in value:
            replace_labels(p, re.compile(re.escape(OLD_DIAGRAM_REFERENCE)), {OLD_DIAGRAM_REFERENCE: DIAGRAM_REFERENCE})
    for node in body.iterchildren(qn("w:tbl")):
        row = node.find(qn("w:tr"))
        cells = row.findall(qn("w:tc")) if row is not None else []
        if [text(cell) for cell in cells] == ["接口标识", "接口名称", "调用方及图示关系", "主要操作"]:
            format_table(Table(node, doc._body))
    return save_section(source, doc)
