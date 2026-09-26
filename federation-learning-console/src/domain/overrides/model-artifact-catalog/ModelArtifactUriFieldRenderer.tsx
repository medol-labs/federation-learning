import type { FieldRendererProps } from "@/platform/composition";
import { DownloadFileButton } from "@/components/download/file-download";

type ModelArtifactRecord = {
  modelName?: string | null;
  modelVersion?: string | null;
  modelFormat?: string | null;
};

const extensionByFormat: Record<string, string> = {
  JSON: "json",
  PYTORCH_STATE_DICT: "pt",
  ONNX: "onnx",
  CSV: "csv",
};

const safeFilenamePart = (value?: string | null) =>
  String(value || "model-artifact").replace(/[^A-Za-z0-9._-]+/gu, "-");

export const modelArtifactFilename = (record?: ModelArtifactRecord) => {
  const name = safeFilenamePart(record?.modelName);
  const version = safeFilenamePart(record?.modelVersion || "version");
  const extension =
    extensionByFormat[String(record?.modelFormat ?? "").toUpperCase()] ?? "bin";
  return `${name}-${version}.${extension}`;
};

export function ModelArtifactUriFieldRenderer({
  value,
  record,
  compact,
}: FieldRendererProps<ModelArtifactRecord>) {
  const uri = typeof value === "string" ? value : "";
  if (!uri) {
    return <p className="text-sm text-muted-foreground">-</p>;
  }

  return (
    <div className="flex min-w-0 items-center gap-2">
      <p
        className={
          compact
            ? "max-w-[18rem] truncate text-sm text-muted-foreground"
            : "min-w-0 flex-1 break-all text-sm text-muted-foreground"
        }
        title={uri}
      >
        {uri}
      </p>
      <DownloadFileButton
        uri={uri}
        filename={modelArtifactFilename(record)}
        tooltip="Download model artifact"
        aria-label="Download model artifact"
      />
    </div>
  );
}
