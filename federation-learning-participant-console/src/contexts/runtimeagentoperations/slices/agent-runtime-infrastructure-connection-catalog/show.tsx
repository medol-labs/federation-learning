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

export const AgentRuntimeInfrastructureConnectionCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
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
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:runtimeInfrastructureId", { value: record?.runtimeInfrastructureId, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "runtimeInfrastructureId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.runtimePlatformConnectionReady.label", "Runtime Platform Connection Ready")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:runtimePlatformConnectionReady", { value: record?.runtimePlatformConnectionReady, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "runtimePlatformConnectionReady", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimePlatformConnectionReady, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.platformApiReachable.label", "Platform Api Reachable")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:platformApiReachable", { value: record?.platformApiReachable, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "platformApiReachable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.platformApiReachable, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.agentAuthenticationSucceeded.label", "Agent Authentication Succeeded")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:agentAuthenticationSucceeded", { value: record?.agentAuthenticationSucceeded, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "agentAuthenticationSucceeded", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.agentAuthenticationSucceeded, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.controlChannelEstablished.label", "Control Channel Established")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:controlChannelEstablished", { value: record?.controlChannelEstablished, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "controlChannelEstablished", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.controlChannelEstablished, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.heartbeatAccepted.label", "Heartbeat Accepted")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:heartbeatAccepted", { value: record?.heartbeatAccepted, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "heartbeatAccepted", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.heartbeatAccepted, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectedAt.label", "Connected At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:connectedAt", { value: record?.connectedAt, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "connectedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.connectedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportFailedAt.label", "Connection Report Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:connectionReportFailedAt", { value: record?.connectionReportFailedAt, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "connectionReportFailedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.connectionReportFailedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportFailureReason.label", "Connection Report Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:connectionReportFailureReason", { value: record?.connectionReportFailureReason, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "connectionReportFailureReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.connectionReportFailureReason, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_infrastructure_connection_catalog.fields.connectionReportRetryable.label", "Connection Report Retryable")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-infrastructure-connection-catalog:display:connectionReportRetryable", { value: record?.connectionReportRetryable, record, resource: "agent-runtime-infrastructure-connection-catalog", field: "connectionReportRetryable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.connectionReportRetryable, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
