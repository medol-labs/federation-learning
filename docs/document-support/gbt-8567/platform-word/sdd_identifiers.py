"""Consistent business and deployment identifiers for the platform SDD."""

import re
from io import BytesIO
from zipfile import ZipFile

DESIGN_ID = "LBLS-GN-LBXX"
# Prefer the published SRS module codes; supporting modules use the function register.
MODULE_CODES = {
    "FL-U-C-ORG": "ORG", "FL-U-C-FED": "FED", "FL-U-C-PROV": "CONF", "FL-U-C-GOV": "GOV",
    "FL-U-C-DATA": "DATA", "FL-U-C-MODEL": "REPO", "FL-U-C-TRAIN": "TRAIN", "FL-U-C-SEC": "AGG",
    "FL-U-C-MON": "MON", "FL-U-C-AGENT": "AGENT", "FL-U-C-FILE": "FILE", "FL-U-C-DICT": "DICT",
    "FL-U-C-NOTIFY": "SN", "FL-U-C-EXCHANGE": "DE", "FL-U-C-IAM": "IAM", "FL-U-PROJ": "COMMON",
}
STEP_CODES = {
    "FL-U-SEL": "SEL", "FL-U-PLAN": "PLAN", "FL-U-DISPATCH": "DISPATCH", "FL-U-UPDATE": "UPDATE",
    "FL-U-AGG": "AGG", "FL-U-EVAL": "EVAL", "FL-U-EXEC": "EXEC",
}
NESTED_UNITS = {
    "FL-U-C-TRAIN": ("FL-U-SEL", "FL-U-PLAN", "FL-U-DISPATCH", "FL-U-UPDATE", "FL-U-AGG", "FL-U-EVAL"),
    "FL-U-C-AGENT": ("FL-U-EXEC",),
}
DEPLOYMENT_IDS = {
    f"FL-SU-{code}": f"{DESIGN_ID}-DEP-{code}" for code in ("P", "A", "S", "E", "AGG", "UI-P", "UI-A")
}
IDENTIFIERS = {code: f"{DESIGN_ID}-{suffix}" for code, suffix in MODULE_CODES.items()}
IDENTIFIERS.update({
    child: f"{IDENTIFIERS[parent]}-{STEP_CODES[child]}" for parent, children in NESTED_UNITS.items()
    for child in children
})
IDENTIFIERS.update(DEPLOYMENT_IDS)
PATTERN = re.compile(r"(?<![A-Z0-9-])(?:" + "|".join(
    re.escape(key) for key in sorted(IDENTIFIERS, key=len, reverse=True)
) + r")(?![A-Z0-9-])")
DIAGRAMS = {
    "40-3": "federated-twin-software-deployment-units",
    "40-4": "federated-twin-third-party-component-relations",
    "40-6": "federation-learning-component-structure",
    "41": "federation-learning-components",
    "174-1": "federation-learning-interface-relations",
    "174-2": "federation-learning-sync-interface-relations",
}


def normalize_identifiers(value):
    return PATTERN.sub(lambda match: IDENTIFIERS[match[0]], value)


def revise_identifiers(source):
    from copy import copy

    from docx import Document
    from docx.image.image import Image
    from docx.oxml.ns import qn

    from sdd_interface_terms import replace_labels, text
    from sdd_interfaces import ROOT
    from platform_word import serialize

    doc = Document(BytesIO(source))
    body = doc.element.body
    for p in body.iter(qn("w:p")):
        replace_labels(p, PATTERN, IDENTIFIERS)

    figures = {}
    for node in body:
        if node.tag != qn("w:p"):
            continue
        caption = re.match(r"^图\s*(\d+(?:-\d+)?)\s", text(node).strip())
        if not caption or caption[1] not in DIAGRAMS:
            continue
        preceding = node.getprevious()
        blips = list(preceding.iter(qn("a:blip"))) if preceding is not None else []
        if not blips:
            continue
        if len(blips) != 1:
            raise ValueError(f"Expected one image before figure {caption[1]}")
        rel = blips[0].get(qn("r:embed"))
        if sum(other.get(qn("r:embed")) == rel for other in body.iter(qn("a:blip"))) != 1:
            raise ValueError(f"Image for figure {caption[1]} is reused")
        name = DIAGRAMS[caption[1]]
        image = ROOT / "docs/generated/diagrams" / (name + ".png")
        diagram = ROOT / "docs/438c/merge/figures" / (name + ".drawio")
        if not image.is_file() or image.stat().st_mtime < diagram.stat().st_mtime:
            raise ValueError(f"Export updated figure {caption[1]} before generating Word")
        part = doc.part.related_parts[rel]
        data = image.read_bytes()
        before, after = Image.from_blob(part.blob), Image.from_blob(data)
        ratio_change = (after.px_width * before.px_height) / (after.px_height * before.px_width) - 1
        if after.content_type != "image/png" or abs(ratio_change) > 0.02:
            raise ValueError(f"Figure {caption[1]} image format or aspect ratio changed")
        if caption[1] in figures:
            raise ValueError(f"Duplicate figure caption {caption[1]}")
        figures[caption[1]] = (str(part.partname).lstrip("/"), data)
    if figures.keys() != DIAGRAMS.keys():
        raise ValueError(f"Missing figures: {DIAGRAMS.keys() - figures.keys()}")

    replacements = dict(figures.values())
    replacements["word/document.xml"] = serialize(doc.element)
    output = BytesIO()
    with ZipFile(BytesIO(source)) as archive, ZipFile(output, "w") as revised:
        for entry in archive.infolist():
            name = entry.filename
            revised.writestr(copy(entry), replacements[name] if name in replacements else archive.read(name))
    return output.getvalue()
