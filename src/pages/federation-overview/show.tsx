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

export const FederationOverviewShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flplatform-backend",
    meta: {
      tableName: "federation_overview_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_overview.label", "Federation Overview"),
      aggregateRoute: "federation",
      queryRoute: "federationoverview",
      dataProviderName: "flplatform-backend",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.federationId ?? t("resources.federation_overview.label", "Federation Overview")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_overview.fields.federationId.label", "Federation Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_overview.fields.federationName.label", "Federation Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_overview.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_overview.fields.minimumParticipantCount.label", "Minimum Participant Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.minimumParticipantCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_overview.fields.activeMemberCount.label", "Active Member Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.activeMemberCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_overview.fields.pendingInvitationCount.label", "Pending Invitation Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.pendingInvitationCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_overview.fields.activeRuntimeCount.label", "Active Runtime Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.activeRuntimeCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.federation_overview.fields.activeTrainingJobCount.label", "Active Training Job Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.activeTrainingJobCount, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
