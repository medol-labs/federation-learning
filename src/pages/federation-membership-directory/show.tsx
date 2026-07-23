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

export const FederationMembershipDirectoryShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flplatform-backend",
    meta: {
      tableName: "federation_membership_directory_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_membership_directory.label", "Federation Membership Directory"),
      aggregateRoute: "federationmembership",
      queryRoute: "federationmembershipdirectory",
      dataProviderName: "flplatform-backend",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.federationId ?? t("resources.federation_membership_directory.label", "Federation Membership Directory")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_membership_directory.fields.federationId.label", "Federation Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_membership_directory.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_membership_directory.fields.federationName.label", "Federation Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_membership_directory.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_membership_directory.fields.membershipStatus.label", "Membership Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.membershipStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_membership_directory.fields.invitationNote.label", "Invitation Note")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.invitationNote, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_membership_directory.fields.approvalNote.label", "Approval Note")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.approvalNote, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
