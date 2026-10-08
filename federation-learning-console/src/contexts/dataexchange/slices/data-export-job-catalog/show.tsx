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

export const DataExportJobCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "data_export_job_catalog_read_model_entity",
      idField: "dataExportJobId",
      label: t("resources.data_export_job_catalog.label", "Data Export Job Catalog"),
      aggregateRoute: "dataexportjob",
      queryRoute: "dataexportjobcatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.dataExportJobId ?? t("resources.data_export_job_catalog.label", "Data Export Job Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.dataExportJobId.label", "Data Export Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:dataExportJobId", { value: record?.dataExportJobId, record, resource: "data-export-job-catalog", field: "dataExportJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dataExportJobId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.resourceName.label", "Resource Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:resourceName", { value: record?.resourceName, record, resource: "data-export-job-catalog", field: "resourceName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.resourceName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.criteriaJson.label", "Criteria Json")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:criteriaJson", { value: record?.criteriaJson, record, resource: "data-export-job-catalog", field: "criteriaJson", view: "display" }) ?? <CopyableText value={record?.criteriaJson} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.sortJson.label", "Sort Json")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:sortJson", { value: record?.sortJson, record, resource: "data-export-job-catalog", field: "sortJson", view: "display" }) ?? <CopyableText value={record?.sortJson} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.columnsJson.label", "Columns Json")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:columnsJson", { value: record?.columnsJson, record, resource: "data-export-job-catalog", field: "columnsJson", view: "display" }) ?? <CopyableText value={record?.columnsJson} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.requestedLocale.label", "Requested Locale")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:requestedLocale", { value: record?.requestedLocale, record, resource: "data-export-job-catalog", field: "requestedLocale", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.requestedLocale, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.requestedAt.label", "Requested At")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:requestedAt", { value: record?.requestedAt, record, resource: "data-export-job-catalog", field: "requestedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.requestedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.snapshotUpperBound.label", "Snapshot Upper Bound")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:snapshotUpperBound", { value: record?.snapshotUpperBound, record, resource: "data-export-job-catalog", field: "snapshotUpperBound", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.snapshotUpperBound, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.requestHash.label", "Request Hash")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:requestHash", { value: record?.requestHash, record, resource: "data-export-job-catalog", field: "requestHash", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.requestHash, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.status.label", "Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:status", { value: record?.status, record, resource: "data-export-job-catalog", field: "status", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.status, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.fileName.label", "File Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:fileName", { value: record?.fileName, record, resource: "data-export-job-catalog", field: "fileName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.fileName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.filePath.label", "File Path")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:filePath", { value: record?.filePath, record, resource: "data-export-job-catalog", field: "filePath", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.filePath, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.rowCount.label", "Row Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:rowCount", { value: record?.rowCount, record, resource: "data-export-job-catalog", field: "rowCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.rowCount, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.errorMessage.label", "Error Message")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:errorMessage", { value: record?.errorMessage, record, resource: "data-export-job-catalog", field: "errorMessage", view: "display" }) ?? <CopyableText value={record?.errorMessage} />}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
