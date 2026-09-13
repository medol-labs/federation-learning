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

export const UserRoleAssignmentCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "user_role_assignment_catalog_read_model_entity",
      idField: "userAccountId",
      label: t("resources.user_role_assignment_catalog.label", "User Role Assignment Catalog"),
      aggregateRoute: "userroleassignment",
      queryRoute: "userroleassignmentcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.userAccountId ?? t("resources.user_role_assignment_catalog.label", "User Role Assignment Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_role_assignment_catalog.fields.userAccountId.label", "User Account Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.userAccountId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_role_assignment_catalog.fields.username.label", "Username")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.username, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_role_assignment_catalog.fields.roleCode.label", "Role Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roleCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_role_assignment_catalog.fields.roleName.label", "Role Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roleName, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
