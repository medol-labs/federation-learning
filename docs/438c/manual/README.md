# 438C 人工章节

本目录保存 438C 文档中需要人工维护的章节内容。生成脚本会优先使用这里的 Markdown 文件，避免重新生成时覆盖人工调整。

命名规则：

```text
docs/438c/manual/<document-id>/<section>.md
```

`document-id` 当前包括：

- `software-requirements`
- `software-design`
- `database-design`
- `test-plan`
- `test-description`
- `installation-plan`
- `user-manual`

章节文件可以使用原始章节名或去掉编号后的章节名，例如：

```text
software-requirements/2引用文档.md
installation-plan/联络点.md
test-plan/测试进度.md
```

没有人工章节时，脚本会从系统设计输入整理内容。
