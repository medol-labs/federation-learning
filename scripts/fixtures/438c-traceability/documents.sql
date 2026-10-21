CREATE TABLE modeling_documents (id TEXT, workspace_id TEXT, title TEXT, kind TEXT, language TEXT, markdown TEXT, source_hash TEXT, updated_at TEXT);
CREATE TABLE modeling_document_versions (id TEXT, document_id TEXT, workspace_id TEXT, revision_no INTEGER, origin TEXT, markdown TEXT, source_hash TEXT, workspace_version_id TEXT, created_at TEXT, workspace_version_no INTEGER, workspace_release_label TEXT);
CREATE TABLE modeling_workspace_versions (id TEXT, workspace_id TEXT, version_no INTEGER, dsl TEXT, model_hash TEXT, release_channel TEXT, release_label TEXT, released_at TEXT);
