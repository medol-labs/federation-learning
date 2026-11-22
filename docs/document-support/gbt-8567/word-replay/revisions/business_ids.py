"""将数字占位编号换成有父子关系的业务需求编号。"""

import re

GROUPS = {
    "001": ("FED", {"001": "MEMBER", "002": "ELIGIBILITY"}),
    "002": ("PREP", {"001": "SCHEMA-MODEL", "002": "DATA"}),
    "003": ("JOB", {"001": "CONFIG", "002": "SUBMIT"}),
    "004": ("ROUND", {"001": "SELECT", "002": "EXECUTE"}),
    "005": ("MODEL", {"001": "AGGREGATE", "002": "EVALUATE", "003": "DELIVER"}),
}
ID_MAPPING = {}
for old_group, (new_group, children) in GROUPS.items():
    ID_MAPPING[f"FL-SRS-{old_group}"] = f"FL-SRS-{new_group}"
    for old_child, new_child in children.items():
        ID_MAPPING[f"FL-SRS-{old_group}-{old_child}"] = f"FL-SRS-{new_group}-{new_child}"
NUMERIC_ID = re.compile(r"FL-SRS-\d{3}(?:-\d{3})?\b")


def rewrite(text):
    text = text.replace(
        "FL-SRS-nnn 为一级业务阶段，FL-SRS-nnn-mmm 为该阶段下的子需求。",
        "需求标识由 FL-SRS 前缀和业务代码组成。一级需求使用业务阶段代码，子需求继承完整的上级标识并追加本项业务代码。",
    )
    return NUMERIC_ID.sub(lambda match: ID_MAPPING.get(match[0], match[0]), text)

def apply(kind, document):
    headings = [p.text for p in document.paragraphs if p.style.name.startswith("Heading")]
    for paragraph in document.element.xpath(".//w:p"):
        text_nodes = paragraph.xpath(".//w:t")
        for node in text_nodes:
            if node.text:
                node.text = rewrite(node.text)
        text = "".join(node.text or "" for node in text_nodes)
        if NUMERIC_ID.search(text):
            raise ValueError(f"{kind} 仍含旧编号：{text}")
    if [rewrite(heading) for heading in headings] != [
        p.text for p in document.paragraphs if p.style.name.startswith("Heading")
    ]:
        raise ValueError(f"{kind} 的原有章节标题发生变化")
