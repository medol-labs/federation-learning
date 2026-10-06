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

export const RolePermissionGrantCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
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
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:roleId", { value: record?.roleId, record, resource: "role-permission-grant-catalog", field: "roleId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roleId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.roleCode.label", "Role Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:roleCode", { value: record?.roleCode, record, resource: "role-permission-grant-catalog", field: "roleCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roleCode, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.roleName.label", "Role Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:roleName", { value: record?.roleName, record, resource: "role-permission-grant-catalog", field: "roleName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roleName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.permissionCode.label", "Permission Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:permissionCode", { value: record?.permissionCode, record, resource: "role-permission-grant-catalog", field: "permissionCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.permissionCode, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_permission_grant_catalog.fields.permissionName.label", "Permission Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:role-permission-grant-catalog:display:permissionName", { value: record?.permissionName, record, resource: "role-permission-grant-catalog", field: "permissionName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.permissionName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
