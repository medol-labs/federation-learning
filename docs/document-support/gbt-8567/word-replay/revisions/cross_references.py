"""更正需规章节引用及末轮评估的对应标识。"""

REPLACEMENTS = {
    "srs": (
        "数据流及外部实体见 3.5.1。",
        "训练主流程见 3.3.1，外部接口边界见 3.5.1。",
    ),
    "dbdd": (
        "记录 finalRoundId、finalModelId、最终制品摘要及评估关联标识；任务看板可查 finalModelId。",
        "记录 finalRoundId、finalModelId 和最终制品摘要。当前 finalEvaluationReportId 取末轮 roundId，"
        "按任务与轮次查找成功评估事件；任务看板可查 finalModelId。",
    ),
}


def apply(kind, document):
    before, after = REPLACEMENTS[kind]
    nodes = [node for node in document.element.xpath(".//w:t") if before in (node.text or "")]
    if len(nodes) != 1:
        raise ValueError(f"{kind} 的待修正引用应出现一次，实际出现 {len(nodes)} 次：{before}")
    nodes[0].text = nodes[0].text.replace(before, after)
