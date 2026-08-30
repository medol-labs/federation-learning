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

export const UserAccountCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "user_account_catalog_read_model_entity",
      idField: "userAccountId",
      label: t("resources.user_account_catalog.label", "User Account Catalog"),
      aggregateRoute: "useraccount",
      queryRoute: "useraccountcatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.userAccountId ?? t("resources.user_account_catalog.label", "User Account Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.userAccountId.label", "User Account Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.userAccountId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.username.label", "Username")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.username, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.providerSubject.label", "Provider Subject")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.providerSubject, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.passwordHash.label", "Password Hash")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.passwordHash, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.active.label", "Active")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.active, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.roleCodes.label", "Role Codes")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roleCodes, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
