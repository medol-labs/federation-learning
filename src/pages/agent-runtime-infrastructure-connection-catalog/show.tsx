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

export const AgentRuntimeInfrastructureConnectionCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_runtime_infrastructure_connection_catalog_read_model_entity",
      idField: "runtimeInfrastructureId",
      label: t("resources.agent_runtime_infrastructure_connection_catalog.label", "Agent Runtime Infrastructure Connection Catalog"),
      aggregateRoute: "agentruntimeinfrastructureconnection",
      queryRoute: "agentruntimeinfrastructureconnectioncatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeInfrastructureId ?? t("resources.agent_runtime_infrastructure_connection_catalog.label", "Agent Runtime Infrastructure Connection Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimePlatformConnectionReady.label", "Runtime Platform Connection Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimePlatformConnectionReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.platformApiReachable.label", "Platform Api Reachable")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.platformApiReachable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.agentAuthenticationSucceeded.label", "Agent Authentication Succeeded")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.agentAuthenticationSucceeded, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.controlChannelEstablished.label", "Control Channel Established")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.controlChannelEstablished, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.heartbeatAccepted.label", "Heartbeat Accepted")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.heartbeatAccepted, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectedAt.label", "Connected At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.connectedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportFailedAt.label", "Connection Report Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.connectionReportFailedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportFailureReason.label", "Connection Report Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.connectionReportFailureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportRetryable.label", "Connection Report Retryable")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.connectionReportRetryable, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
