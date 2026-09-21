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

export const RolePermissionGrantCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "role_permission_grant_catalog_read_model_entity",
      idField: "roleCode",
      label: t("resources.role_permission_grant_catalog.label", "Role Permission Grant Catalog"),
      aggregateRoute: "rolepermissiongrant",
      queryRoute: "rolepermissiongrantcatalog",
      dataProviderName: "federation-learning-support",
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
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:roleId", { value: record?.roleId, record, resource: "role-permission-grant-catalog", field: "roleId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roleId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.roleCode.label", "Role Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:roleCode", { value: record?.roleCode, record, resource: "role-permission-grant-catalog", field: "roleCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roleCode, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.roleName.label", "Role Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:roleName", { value: record?.roleName, record, resource: "role-permission-grant-catalog", field: "roleName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roleName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.permissionCode.label", "Permission Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:permissionCode", { value: record?.permissionCode, record, resource: "role-permission-grant-catalog", field: "permissionCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.permissionCode, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.permissionName.label", "Permission Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:permissionName", { value: record?.permissionName, record, resource: "role-permission-grant-catalog", field: "permissionName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.permissionName, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
