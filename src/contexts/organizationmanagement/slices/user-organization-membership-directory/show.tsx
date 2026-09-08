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

export const UserOrganizationMembershipDirectoryShow = () => {
  const t = useTranslate();
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.userOrganizationMembershipId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.userAccountId.label", "User Account Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.userAccountId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.username.label", "Username")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.username, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.organizationUserRole.label", "Organization User Role")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationUserRole, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.user_organization_membership_directory.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
