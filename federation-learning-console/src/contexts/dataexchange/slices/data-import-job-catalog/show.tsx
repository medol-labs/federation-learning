// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

const formatValue = (
  value: unknown,
  t: ReturnType<typeof useTranslate>,
  dictionaryLabel: ReturnType<typeof useDictionaryTranslation>["dictionaryLabel"],
  options?: Array<{ label: string; value: string }>,
  dictionaryCode?: string,
): string => {
  if (value === null || value === undefined || value === "") return "-";
  if (Array.isArray(value)) {
    const formatted: string[] = value.map((item) => formatValue(item, t, dictionaryLabel, options, dictionaryCode)).filter((item) => item !== "-");
    return formatted.length > 0 ? formatted.join(", ") : "-";
  }
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  const stringValue = String(value);
  if (dictionaryCode) return dictionaryLabel(dictionaryCode, stringValue, t(`dictionaries.${dictionaryCode}.${stringValue}`, stringValue));
  return options?.find((option) => option.value === stringValue)?.label ?? stringValue;
};

export const DataImportJobCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "data_import_job_catalog_read_model_entity",
      idField: "dataImportJobId",
      label: t("resources.data_import_job_catalog.label", "Data Import Job Catalog"),
      aggregateRoute: "dataimportjob",
      queryRoute: "dataimportjobcatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.dataImportJobId ?? t("resources.data_import_job_catalog.label", "Data Import Job Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.dataImportJobId.label", "Data Import Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:dataImportJobId", { value: record?.dataImportJobId, record, resource: "data-import-job-catalog", field: "dataImportJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dataImportJobId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.commandName.label", "Command Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:commandName", { value: record?.commandName, record, resource: "data-import-job-catalog", field: "commandName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.commandName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.resourceName.label", "Resource Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:resourceName", { value: record?.resourceName, record, resource: "data-import-job-catalog", field: "resourceName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.resourceName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.originalFileName.label", "Original File Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:originalFileName", { value: record?.originalFileName, record, resource: "data-import-job-catalog", field: "originalFileName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.originalFileName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.filePath.label", "File Path")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:filePath", { value: record?.filePath, record, resource: "data-import-job-catalog", field: "filePath", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.filePath, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.requestedLocale.label", "Requested Locale")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:requestedLocale", { value: record?.requestedLocale, record, resource: "data-import-job-catalog", field: "requestedLocale", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.requestedLocale, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.requestedAt.label", "Requested At")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:requestedAt", { value: record?.requestedAt, record, resource: "data-import-job-catalog", field: "requestedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.requestedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.requestHash.label", "Request Hash")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:requestHash", { value: record?.requestHash, record, resource: "data-import-job-catalog", field: "requestHash", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.requestHash, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.status.label", "Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:status", { value: record?.status, record, resource: "data-import-job-catalog", field: "status", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.status, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.totalRows.label", "Total Rows")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:totalRows", { value: record?.totalRows, record, resource: "data-import-job-catalog", field: "totalRows", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.totalRows, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.successRows.label", "Success Rows")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:successRows", { value: record?.successRows, record, resource: "data-import-job-catalog", field: "successRows", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.successRows, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.failedRows.label", "Failed Rows")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:failedRows", { value: record?.failedRows, record, resource: "data-import-job-catalog", field: "failedRows", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failedRows, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.failureIndexPath.label", "Failure Index Path")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:failureIndexPath", { value: record?.failureIndexPath, record, resource: "data-import-job-catalog", field: "failureIndexPath", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failureIndexPath, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_import_job_catalog.fields.errorMessage.label", "Error Message")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-import-job-catalog:display:errorMessage", { value: record?.errorMessage, record, resource: "data-import-job-catalog", field: "errorMessage", view: "display" }) ?? <CopyableText value={record?.errorMessage} />}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
