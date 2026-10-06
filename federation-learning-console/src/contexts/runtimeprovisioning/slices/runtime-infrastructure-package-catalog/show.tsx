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

export const RuntimeInfrastructurePackageCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_infrastructure_package_catalog_read_model_entity",
      idField: "runtimeInfrastructurePackageId",
      label: t("resources.runtime_infrastructure_package_catalog.label", "Runtime Infrastructure Package Catalog"),
      aggregateRoute: "runtimeinfrastructurepackage",
      queryRoute: "runtimeinfrastructurepackagecatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeInfrastructurePackageId ?? t("resources.runtime_infrastructure_package_catalog.label", "Runtime Infrastructure Package Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-package-catalog:display:runtimeInfrastructurePackageId", { value: record?.runtimeInfrastructurePackageId, record, resource: "runtime-infrastructure-package-catalog", field: "runtimeInfrastructurePackageId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.packageName.label", "Package Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-package-catalog:display:packageName", { value: record?.packageName, record, resource: "runtime-infrastructure-package-catalog", field: "packageName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.packageName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.packageVersion.label", "Package Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-package-catalog:display:packageVersion", { value: record?.packageVersion, record, resource: "runtime-infrastructure-package-catalog", field: "packageVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.packageVersion, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-package-catalog:display:runtimeEnvironmentType", { value: record?.runtimeEnvironmentType, record, resource: "runtime-infrastructure-package-catalog", field: "runtimeEnvironmentType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnvironmentType, t, dictionaryLabel, undefined, "RUNTIME_ENVIRONMENT_TYPE")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-infrastructure-package-catalog:display:state", { value: record?.state, record, resource: "runtime-infrastructure-package-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t, dictionaryLabel, [
                { label: t("resources.runtime_infrastructure_package_catalog.fields.state.options.Registered", "Registered"), value: "Registered" },
              ])}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
