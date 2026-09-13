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

export const RolePermissionGrantCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "role_permission_grant_catalog_read_model_entity",
      idField: "roleCode",
      label: t("resources.role_permission_grant_catalog.label", "Role Permission Grant Catalog"),
      aggregateRoute: "rolepermissiongrant",
      queryRoute: "rolepermissiongrantcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.roleCode ?? t("resources.role_permission_grant_catalog.label", "Role Permission Grant Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.roleId.label", "Role Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roleId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.roleCode.label", "Role Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roleCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.roleName.label", "Role Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roleName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.permissionCode.label", "Permission Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.permissionCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.permissionName.label", "Permission Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.permissionName, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
