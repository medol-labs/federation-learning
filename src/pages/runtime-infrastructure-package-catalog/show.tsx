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

export const RuntimeInfrastructurePackageCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flplatform-backend",
    meta: {
      tableName: "runtime_infrastructure_package_catalog_read_model_entity",
      idField: "runtimeInfrastructurePackageId",
      label: t("resources.runtime_infrastructure_package_catalog.label", "Runtime Infrastructure Package Catalog"),
      aggregateRoute: "runtimeinfrastructurepackage",
      queryRoute: "runtimeinfrastructurepackagecatalog",
      dataProviderName: "flplatform-backend",
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructurePackageId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.packageName.label", "Package Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.packageName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.packageVersion.label", "Package Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.packageVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnvironmentType, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.runtimeDeploymentTargetType.label", "Runtime Deployment Target Type")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDeploymentTargetType, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.installProfile.label", "Install Profile")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.installProfile, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.architecture.label", "Architecture")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.architecture, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.installGuide.label", "Install Guide")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.installGuide, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_infrastructure_package_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
