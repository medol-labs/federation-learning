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

export const PermissionCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "permission_catalog_read_model_entity",
      idField: "permissionId",
      label: t("resources.permission_catalog.label", "Permission Catalog"),
      aggregateRoute: "permission",
      queryRoute: "permissioncatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.permissionId ?? t("resources.permission_catalog.label", "Permission Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.permission_catalog.fields.permissionId.label", "Permission Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:permission-catalog:display:permissionId", { value: record?.permissionId, record, resource: "permission-catalog", field: "permissionId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.permissionId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.permission_catalog.fields.permissionCode.label", "Permission Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:permission-catalog:display:permissionCode", { value: record?.permissionCode, record, resource: "permission-catalog", field: "permissionCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.permissionCode, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.permission_catalog.fields.permissionName.label", "Permission Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:permission-catalog:display:permissionName", { value: record?.permissionName, record, resource: "permission-catalog", field: "permissionName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.permissionName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.permission_catalog.fields.description.label", "Description")}</h4>
              {renderFieldOverride(frontendComposition, "field:permission-catalog:display:description", { value: record?.description, record, resource: "permission-catalog", field: "description", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.description, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
