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

export const OrganizationDirectoryShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "organization_directory_read_model_entity",
      idField: "organizationId",
      label: t("resources.organization_directory.label", "Organization Directory"),
      aggregateRoute: "organization",
      queryRoute: "organizationdirectory",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.organizationId ?? t("resources.organization_directory.label", "Organization Directory")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.organization_directory.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:organization-directory:display:organizationId", { value: record?.organizationId, record, resource: "organization-directory", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.organization_directory.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:organization-directory:display:organizationName", { value: record?.organizationName, record, resource: "organization-directory", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.organization_directory.fields.organizationType.label", "Organization Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:organization-directory:display:organizationType", { value: record?.organizationType, record, resource: "organization-directory", field: "organizationType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationType, t, dictionaryLabel, [
                { label: t("resources.organization_directory.fields.organizationType.options.HOSPITAL", "Hospital"), value: "HOSPITAL" },
                { label: t("resources.organization_directory.fields.organizationType.options.RESEARCH_INSTITUTE", "Research Institute"), value: "RESEARCH_INSTITUTE" },
                { label: t("resources.organization_directory.fields.organizationType.options.PUBLIC_HEALTH_AGENCY", "Public Health Agency"), value: "PUBLIC_HEALTH_AGENCY" },
                { label: t("resources.organization_directory.fields.organizationType.options.LABORATORY", "Laboratory"), value: "LABORATORY" },
                { label: t("resources.organization_directory.fields.organizationType.options.REHABILITATION_CENTER", "Rehabilitation Center"), value: "REHABILITATION_CENTER" },
              ])}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.organization_directory.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:organization-directory:display:state", { value: record?.state, record, resource: "organization-directory", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t, dictionaryLabel, [
                { label: t("resources.organization_directory.fields.state.options.Registered", "Registered"), value: "Registered" },
                { label: t("resources.organization_directory.fields.state.options.Active", "Active"), value: "Active" },
                { label: t("resources.organization_directory.fields.state.options.Deactivated", "Deactivated"), value: "Deactivated" },
              ])}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.organization_directory.fields.approvedDatasetCount.label", "Approved Dataset Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:organization-directory:display:approvedDatasetCount", { value: record?.approvedDatasetCount, record, resource: "organization-directory", field: "approvedDatasetCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.approvedDatasetCount, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
