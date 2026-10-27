# 文档图表的 draw.io 来源与统一样式

业务架构图以原生 `.drawio` 文件作为唯一编辑来源，SVG、PNG 是用于文档的导出结果。原生形状、文字、分组及连接线均可编辑，手工调整保存后直接用于后续导出。

## 编辑与共享

使用 draw.io Desktop 或 diagrams.net 打开 `.drawio` 文件。模块使用固定 ID；模块内的能力框和文字作为子元素，随模块移动；业务连接线绑定相应模块，并保存明确的连接点和折线拐点。连线文字作为连接线的子元素维护。

共享原始 `.drawio` 文件即可继续编辑。SVG 和 PNG 用于 Word 插图与预览，源文件单独保存。团队应对同一张图串行统稿，审阅时同时查看 XML 差异与图片变化。

## 校验、格式化与导出

在项目根目录执行：

```bash
python3 docs/diagrams/drawio.py check docs/438c/merge/figures/federated-twin-business-architecture.drawio
python3 docs/diagrams/drawio.py format docs/438c/merge/figures/federated-twin-business-architecture.drawio
python3 docs/diagrams/drawio.py export docs/438c/merge/figures/federated-twin-business-architecture.drawio
```

`check` 检查 XML、重复 ID 及缺失的父元素或连接端点。`format` 自动展开 draw.io 保存的压缩页并逐属性排版 XML，保留元素顺序与 ID，去除保存时间等易变元数据；此操作不重新布局。`export` 用 draw.io Desktop 导出同目录 SVG 和 2 倍尺寸 PNG，读取源文件而不改写它。

脚本使用 Python 标准库。导出需要本机安装 draw.io Desktop，支持 PATH 中的 `drawio` 和 macOS 默认安装路径；其他位置可通过 `DRAWIO_EXECUTABLE` 指定。图源、图形编辑和导出均不依赖原来的 Mermaid 或 Python 绘图逻辑。

## 统一样式库

在 draw.io 的“文件 → 打开图库”中导入 `project-styles.mxlibrary`。库内提供业务应用、评估管理、协同能力、模型推理、外部系统、独立支撑六类节点，以及业务信息、模型交付和验证反馈三类连接线。

- 使用浅色背景、深色文字，模块标题与正文保留清楚的字号层次。
- 业务信息使用蓝色实线，模型交付使用绿色实线，验证优化反馈使用棕色虚线。
- 系统支撑能力分别归属各自系统；同类样式不表示共用实例或服务。
- 新图形从样式库拖入；修改公共样式后，对已有图形批量应用相同样式。图库不会自动改写已创建的图形。

## Git 对照

源文件保存为未压缩 XML，提交前运行 `format`。文字、连接关系及几何位置可逐行对照；节点移动会产生坐标差异，仍需配合预览图审阅。不要重建或随机替换原有节点 ID，不要对 XML 元素排序，以免改变图层顺序。

当前业务架构图及[系统集成关系图](../438c/merge/federated-twin-internal-integration.md)均以 draw.io 维护。系统集成图保留历史文件名 `docs/mermaid-diagram`，其 `.drawio` 为图源，`.png`、`.svg` 为导出结果，与 Mermaid 无关。已有概念关系图的 DOT 来源按原流程维护。
