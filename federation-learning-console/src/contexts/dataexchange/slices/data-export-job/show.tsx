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
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.requestedLocale.label", "Requested Locale")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:requestedLocale", { value: record?.requestedLocale, record, resource: "data-export-job-catalog", field: "requestedLocale", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.requestedLocale, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.data_export_job_catalog.fields.status.label", "Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:data-export-job-catalog:display:status", { value: record?.status, record, resource: "data-export-job-catalog", field: "status", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.status, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
