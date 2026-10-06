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

export const UserOrganizationMembershipDirectoryShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "user_organization_membership_directory_read_model_entity",
      idField: "userOrganizationMembershipId",
      label: t("resources.user_organization_membership_directory.label", "User Organization Membership Directory"),
      aggregateRoute: "userorganizationmembership",
      queryRoute: "userorganizationmembershipdirectory",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.userOrganizationMembershipId ?? t("resources.user_organization_membership_directory.label", "User Organization Membership Directory")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.userOrganizationMembershipId.label", "User Organization Membership Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-organization-membership-directory:display:userOrganizationMembershipId", { value: record?.userOrganizationMembershipId, record, resource: "user-organization-membership-directory", field: "userOrganizationMembershipId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.userOrganizationMembershipId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.userAccountId.label", "User Account Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-organization-membership-directory:display:userAccountId", { value: record?.userAccountId, record, resource: "user-organization-membership-directory", field: "userAccountId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.userAccountId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.username.label", "Username")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-organization-membership-directory:display:username", { value: record?.username, record, resource: "user-organization-membership-directory", field: "username", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.username, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-organization-membership-directory:display:organizationId", { value: record?.organizationId, record, resource: "user-organization-membership-directory", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-organization-membership-directory:display:organizationName", { value: record?.organizationName, record, resource: "user-organization-membership-directory", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.organizationUserRole.label", "Organization User Role")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-organization-membership-directory:display:organizationUserRole", { value: record?.organizationUserRole, record, resource: "user-organization-membership-directory", field: "organizationUserRole", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationUserRole, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:user-organization-membership-directory:display:state", { value: record?.state, record, resource: "user-organization-membership-directory", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t, dictionaryLabel, [
                { label: t("resources.user_organization_membership_directory.fields.state.options.Active", "Active"), value: "Active" },
              ])}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
