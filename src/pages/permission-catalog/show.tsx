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

export const PermissionCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "permission_catalog_read_model_entity",
      idField: "permissionCode",
      label: t("resources.permission_catalog.label", "Permission Catalog"),
      aggregateRoute: "useraccount",
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
            <CardTitle>{record?.permissionCode ?? t("resources.permission_catalog.label", "Permission Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.permission_catalog.fields.permissionCode.label", "Permission Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.permissionCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.permission_catalog.fields.permissionName.label", "Permission Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.permissionName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.permission_catalog.fields.description.label", "Description")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.description, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
