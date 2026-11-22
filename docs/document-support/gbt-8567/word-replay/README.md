# 联邦学习模块 Word 修订复现

从 `baselines/` 中的需规 V0.11、软设 V0.3 和数据库设计 V0.2 开始，依次应用六轮修订，输出需规 V0.12、软设 V0.4 和数据库设计 V0.3。基线是对齐前的原始副本。软设和数据库设计沿用原交付文件名，版本见文档内页。

## 运行

在 `federation-learning` 根目录执行。使用 Python 3.10 或更新版本，已在 Python 3.12 验证。

```sh
python3 -m venv docs/generated/word-replay-venv
docs/generated/word-replay-venv/bin/python -m pip install \
  -r docs/document-support/gbt-8567/word-replay/requirements.txt
docs/generated/word-replay-venv/bin/python -B \
  docs/document-support/gbt-8567/word-replay/replay.py \
  --output-dir docs/generated/word-replay
```

每次运行指定一个新输出目录。基线、修订脚本和数据库设计目录页码表随项目保存，安装依赖后即可重放。命令打印生成文件的路径。

本地已有同名定稿时，可在命令末尾加上 `--verify-against docs/generated/gbt-8567`。脚本在写入结果前逐项比较 DOCX 压缩包内的文件内容，发现差异时列出对应文件。现有定稿仅用于读取比对。

## 修订顺序

| 顺序 | 脚本 | 文档 | 内容 |
| --- | --- | --- | --- |
| 1 | `alignment.py` | 需规、软设、数据库设计 | 对齐业务规则、验收判据、追溯和待确认事项，更新文档版本 |
| 2 | `business_ids.py` | 需规、软设、数据库设计 | 统一业务需求编号和父子关系 |
| 3 | `cross_references.py` | 需规、数据库设计 | 纠正流程、接口与末轮评估引用 |
| 4 | `acceptance_interfaces_deployment.py` | 需规、软设、数据库设计 | 明确可验收结果、接口对应和实际部署配置 |
| 5 | `q11.py` | 需规、软设 | 补充 Q-11 验证项及实际接口路径 |
| 6 | `start_checks.py` | 需规、软设 | 区分计划接收和作业启动的检查、验收及追溯 |

修订脚本位于 `revisions/`。部分逻辑按固定基线的段落和表格位置定位，适用于复现这批文档。脚本沿用基线的样式、页眉页脚和其他包内资源。`revisions/dbdd-toc-pages.json` 记录该版数据库设计定稿时核对过的目录页码。后续正文改动涉及分页时，应在 Word 中复核目录位置。
