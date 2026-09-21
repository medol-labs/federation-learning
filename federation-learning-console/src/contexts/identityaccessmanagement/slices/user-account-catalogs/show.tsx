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
              {renderFieldOverride(frontendComposition, "field:user-account-catalog:display:userAccountId", { value: record?.userAccountId, record, resource: "user-account-catalog", field: "userAccountId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.userAccountId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.username.label", "Username")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-account-catalog:display:username", { value: record?.username, record, resource: "user-account-catalog", field: "username", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.username, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.providerSubject.label", "Provider Subject")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-account-catalog:display:providerSubject", { value: record?.providerSubject, record, resource: "user-account-catalog", field: "providerSubject", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.providerSubject, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.userSource.label", "User Source")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-account-catalog:display:userSource", { value: record?.userSource, record, resource: "user-account-catalog", field: "userSource", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.userSource, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.passwordHash.label", "Password Hash")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-account-catalog:display:passwordHash", { value: record?.passwordHash, record, resource: "user-account-catalog", field: "passwordHash", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.passwordHash, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_account_catalog.fields.active.label", "Active")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-account-catalog:display:active", { value: record?.active, record, resource: "user-account-catalog", field: "active", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.active, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
