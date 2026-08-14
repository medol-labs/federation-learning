// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";

const formatValue = (value: unknown, t: ReturnType<typeof useTranslate>) => {
  if (value === null || value === undefined || value === "") return "-";
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  return String(value);
};

export const StagedFileCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "staged_file_catalog_read_model_entity",
      idField: "stagedFileId",
      label: t("resources.staged_file_catalog.label", "Staged File Catalog"),
      aggregateRoute: "stagedfile",
      queryRoute: "stagedfilecatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.stagedFileId ?? t("resources.staged_file_catalog.label", "Staged File Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.stagedFileId.label", "Staged File Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.stagedFileId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.originalFileName.label", "Original File Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.originalFileName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.contentType.label", "Content Type")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.contentType, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.sizeBytes.label", "Size Bytes")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sizeBytes, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.purpose.label", "Purpose")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.purpose, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.stagedFileLocation.label", "Staged File Location")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.stagedFileLocation, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.checksum.label", "Checksum")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.checksum, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.stagedAt.label", "Staged At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.stagedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.consumedAt.label", "Consumed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.consumedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.consumedByContext.label", "Consumed By Context")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.consumedByContext, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.consumedByCommand.label", "Consumed By Command")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.consumedByCommand, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.consumedByCommandId.label", "Consumed By Command Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.consumedByCommandId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.discardedAt.label", "Discarded At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.discardedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.discardReason.label", "Discard Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.discardReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.expiresAt.label", "Expires At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.expiresAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.expiredAt.label", "Expired At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.expiredAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.staged_file_catalog.fields.expirationReason.label", "Expiration Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.expirationReason, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
