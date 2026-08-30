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

export const RoleCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "role_catalog_read_model_entity",
      idField: "roleCode",
      label: t("resources.role_catalog.label", "Role Catalog"),
      aggregateRoute: "useraccount",
      queryRoute: "rolecatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.roleCode ?? t("resources.role_catalog.label", "Role Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_catalog.fields.roleCode.label", "Role Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roleCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_catalog.fields.roleName.label", "Role Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roleName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.role_catalog.fields.permissionCodes.label", "Permission Codes")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.permissionCodes, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
