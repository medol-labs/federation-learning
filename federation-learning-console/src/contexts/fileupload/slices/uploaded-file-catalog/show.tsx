// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

const formatValue = (value: unknown, t: ReturnType<typeof useTranslate>) => {
  if (value === null || value === undefined || value === "") return "-";
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  return String(value);
};

export const UploadedFileCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "uploaded_file_catalog_read_model_entity",
      idField: "fileId",
      label: t("resources.uploaded_file_catalog.label", "Uploaded File Catalog"),
      aggregateRoute: "uploadedfile",
      queryRoute: "uploadedfilecatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.fileId ?? t("resources.uploaded_file_catalog.label", "Uploaded File Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.fileId.label", "File Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:fileId", { value: record?.fileId, record, resource: "uploaded-file-catalog", field: "fileId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.fileId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.originalFileName.label", "Original File Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:originalFileName", { value: record?.originalFileName, record, resource: "uploaded-file-catalog", field: "originalFileName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.originalFileName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.contentType.label", "Content Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:contentType", { value: record?.contentType, record, resource: "uploaded-file-catalog", field: "contentType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.contentType, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.sizeBytes.label", "Size Bytes")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:sizeBytes", { value: record?.sizeBytes, record, resource: "uploaded-file-catalog", field: "sizeBytes", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.sizeBytes, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.purpose.label", "Purpose")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:purpose", { value: record?.purpose, record, resource: "uploaded-file-catalog", field: "purpose", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.purpose, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.fileLocation.label", "File Location")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:fileLocation", { value: record?.fileLocation, record, resource: "uploaded-file-catalog", field: "fileLocation", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.fileLocation, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.checksum.label", "Checksum")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:checksum", { value: record?.checksum, record, resource: "uploaded-file-catalog", field: "checksum", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.checksum, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:state", { value: record?.state, record, resource: "uploaded-file-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.uploadedAt.label", "Uploaded At")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:uploadedAt", { value: record?.uploadedAt, record, resource: "uploaded-file-catalog", field: "uploadedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.uploadedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.referencedAt.label", "Referenced At")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:referencedAt", { value: record?.referencedAt, record, resource: "uploaded-file-catalog", field: "referencedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.referencedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.referencedByContext.label", "Referenced By Context")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:referencedByContext", { value: record?.referencedByContext, record, resource: "uploaded-file-catalog", field: "referencedByContext", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.referencedByContext, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.referencedByCommand.label", "Referenced By Command")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:referencedByCommand", { value: record?.referencedByCommand, record, resource: "uploaded-file-catalog", field: "referencedByCommand", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.referencedByCommand, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.referencedByCommandId.label", "Referenced By Command Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:referencedByCommandId", { value: record?.referencedByCommandId, record, resource: "uploaded-file-catalog", field: "referencedByCommandId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.referencedByCommandId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.discardedAt.label", "Discarded At")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:discardedAt", { value: record?.discardedAt, record, resource: "uploaded-file-catalog", field: "discardedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.discardedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.discardReason.label", "Discard Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:discardReason", { value: record?.discardReason, record, resource: "uploaded-file-catalog", field: "discardReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.discardReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.expiresAt.label", "Expires At")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:expiresAt", { value: record?.expiresAt, record, resource: "uploaded-file-catalog", field: "expiresAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.expiresAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.expiredAt.label", "Expired At")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:expiredAt", { value: record?.expiredAt, record, resource: "uploaded-file-catalog", field: "expiredAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.expiredAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.uploaded_file_catalog.fields.expirationReason.label", "Expiration Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:uploaded-file-catalog:display:expirationReason", { value: record?.expirationReason, record, resource: "uploaded-file-catalog", field: "expirationReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.expirationReason, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
