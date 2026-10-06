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

export const AgentRuntimeTelemetryLatestShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_runtime_telemetry_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.agent_runtime_telemetry_latest.label", "Agent Runtime Telemetry Latest"),
      aggregateRoute: "agentruntimetelemetry",
      queryRoute: "agentruntimetelemetrylatest",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.nodeId ?? t("resources.agent_runtime_telemetry_latest.label", "Agent Runtime Telemetry Latest")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.nodeId.label", "Node Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:nodeId", { value: record?.nodeId, record, resource: "agent-runtime-telemetry-latest", field: "nodeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nodeId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "agent-runtime-telemetry-latest", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.federationId.label", "Federation Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:federationId", { value: record?.federationId, record, resource: "agent-runtime-telemetry-latest", field: "federationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:trainingJobId", { value: record?.trainingJobId, record, resource: "agent-runtime-telemetry-latest", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.roundExecutionId.label", "Round Execution Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:roundExecutionId", { value: record?.roundExecutionId, record, resource: "agent-runtime-telemetry-latest", field: "roundExecutionId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundExecutionId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.cpuLoad.label", "Cpu Load")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:cpuLoad", { value: record?.cpuLoad, record, resource: "agent-runtime-telemetry-latest", field: "cpuLoad", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.cpuLoad, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.gpuLoad.label", "Gpu Load")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:gpuLoad", { value: record?.gpuLoad, record, resource: "agent-runtime-telemetry-latest", field: "gpuLoad", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.gpuLoad, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.memoryLoad.label", "Memory Load")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:memoryLoad", { value: record?.memoryLoad, record, resource: "agent-runtime-telemetry-latest", field: "memoryLoad", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.memoryLoad, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.lastHeartbeatAt.label", "Last Heartbeat At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:lastHeartbeatAt", { value: record?.lastHeartbeatAt, record, resource: "agent-runtime-telemetry-latest", field: "lastHeartbeatAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastHeartbeatAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_telemetry_latest.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-telemetry-latest:display:telemetryRetentionPolicy", { value: record?.telemetryRetentionPolicy, record, resource: "agent-runtime-telemetry-latest", field: "telemetryRetentionPolicy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.telemetryRetentionPolicy, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
