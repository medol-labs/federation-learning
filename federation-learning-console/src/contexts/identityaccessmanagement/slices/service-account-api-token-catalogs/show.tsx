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

export const ServiceAccountApiTokenCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "service_account_api_token_catalog_read_model_entity",
      idField: "apiTokenId",
      label: t("resources.service_account_api_token_catalog.label", "Service Account Api Token Catalog"),
      aggregateRoute: "serviceaccountapitoken",
      queryRoute: "serviceaccountapitokencatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.apiTokenId ?? t("resources.service_account_api_token_catalog.label", "Service Account Api Token Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.service_account_api_token_catalog.fields.apiTokenId.label", "Api Token Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:service-account-api-token-catalog:display:apiTokenId", { value: record?.apiTokenId, record, resource: "service-account-api-token-catalog", field: "apiTokenId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.apiTokenId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.service_account_api_token_catalog.fields.userAccountId.label", "User Account Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:service-account-api-token-catalog:display:userAccountId", { value: record?.userAccountId, record, resource: "service-account-api-token-catalog", field: "userAccountId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.userAccountId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.service_account_api_token_catalog.fields.username.label", "Username")}</h4>
              {renderFieldOverride(frontendComposition, "field:service-account-api-token-catalog:display:username", { value: record?.username, record, resource: "service-account-api-token-catalog", field: "username", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.username, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.service_account_api_token_catalog.fields.tokenName.label", "Token Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:service-account-api-token-catalog:display:tokenName", { value: record?.tokenName, record, resource: "service-account-api-token-catalog", field: "tokenName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.tokenName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.service_account_api_token_catalog.fields.tokenPrefix.label", "Token Prefix")}</h4>
              {renderFieldOverride(frontendComposition, "field:service-account-api-token-catalog:display:tokenPrefix", { value: record?.tokenPrefix, record, resource: "service-account-api-token-catalog", field: "tokenPrefix", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.tokenPrefix, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.service_account_api_token_catalog.fields.issuedAt.label", "Issued At")}</h4>
              {renderFieldOverride(frontendComposition, "field:service-account-api-token-catalog:display:issuedAt", { value: record?.issuedAt, record, resource: "service-account-api-token-catalog", field: "issuedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.issuedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.service_account_api_token_catalog.fields.roles.label", "Roles")}</h4>
              {renderFieldOverride(frontendComposition, "field:service-account-api-token-catalog:display:roles", { value: record?.roles, record, resource: "service-account-api-token-catalog", field: "roles", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roles, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.service_account_api_token_catalog.fields.permissions.label", "Permissions")}</h4>
              {renderFieldOverride(frontendComposition, "field:service-account-api-token-catalog:display:permissions", { value: record?.permissions, record, resource: "service-account-api-token-catalog", field: "permissions", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.permissions, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
