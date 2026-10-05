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

export const RuntimeTelemetryLatestShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_telemetry_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_telemetry_latest.label", "Runtime Telemetry Latest"),
      aggregateRoute: "noderuntimehealth",
      queryRoute: "runtimetelemetrylatest",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.nodeId ?? t("resources.runtime_telemetry_latest.label", "Runtime Telemetry Latest")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.nodeId.label", "Node Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:nodeId", { value: record?.nodeId, record, resource: "runtime-telemetry-latest", field: "nodeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nodeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "runtime-telemetry-latest", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.federationId.label", "Federation Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:federationId", { value: record?.federationId, record, resource: "runtime-telemetry-latest", field: "federationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.federationName.label", "Federation Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:federationName", { value: record?.federationName, record, resource: "runtime-telemetry-latest", field: "federationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:trainingJobId", { value: record?.trainingJobId, record, resource: "runtime-telemetry-latest", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:trainingJobObjective", { value: record?.trainingJobObjective, record, resource: "runtime-telemetry-latest", field: "trainingJobObjective", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.roundExecutionId.label", "Round Execution Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:roundExecutionId", { value: record?.roundExecutionId, record, resource: "runtime-telemetry-latest", field: "roundExecutionId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundExecutionId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.runtimeNodeName.label", "Runtime Node Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:runtimeNodeName", { value: record?.runtimeNodeName, record, resource: "runtime-telemetry-latest", field: "runtimeNodeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.cpuLoad.label", "Cpu Load")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:cpuLoad", { value: record?.cpuLoad, record, resource: "runtime-telemetry-latest", field: "cpuLoad", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.cpuLoad, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.gpuLoad.label", "Gpu Load")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:gpuLoad", { value: record?.gpuLoad, record, resource: "runtime-telemetry-latest", field: "gpuLoad", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.gpuLoad, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.memoryLoad.label", "Memory Load")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:memoryLoad", { value: record?.memoryLoad, record, resource: "runtime-telemetry-latest", field: "memoryLoad", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.memoryLoad, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.lastHeartbeatAt.label", "Last Heartbeat At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:lastHeartbeatAt", { value: record?.lastHeartbeatAt, record, resource: "runtime-telemetry-latest", field: "lastHeartbeatAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastHeartbeatAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.lastRecoveredAt.label", "Last Recovered At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:lastRecoveredAt", { value: record?.lastRecoveredAt, record, resource: "runtime-telemetry-latest", field: "lastRecoveredAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastRecoveredAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.offlineDetectionPending.label", "Offline Detection Pending")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:offlineDetectionPending", { value: record?.offlineDetectionPending, record, resource: "runtime-telemetry-latest", field: "offlineDetectionPending", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.offlineDetectionPending, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.recoveryDetectionPending.label", "Recovery Detection Pending")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:recoveryDetectionPending", { value: record?.recoveryDetectionPending, record, resource: "runtime-telemetry-latest", field: "recoveryDetectionPending", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recoveryDetectionPending, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.resourcePressureDetectionPending.label", "Resource Pressure Detection Pending")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:resourcePressureDetectionPending", { value: record?.resourcePressureDetectionPending, record, resource: "runtime-telemetry-latest", field: "resourcePressureDetectionPending", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.resourcePressureDetectionPending, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.offlineReason.label", "Offline Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:offlineReason", { value: record?.offlineReason, record, resource: "runtime-telemetry-latest", field: "offlineReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.offlineReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.recoveryReason.label", "Recovery Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:recoveryReason", { value: record?.recoveryReason, record, resource: "runtime-telemetry-latest", field: "recoveryReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recoveryReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.pressureType.label", "Pressure Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:pressureType", { value: record?.pressureType, record, resource: "runtime-telemetry-latest", field: "pressureType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.pressureType, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.observedValue.label", "Observed Value")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:observedValue", { value: record?.observedValue, record, resource: "runtime-telemetry-latest", field: "observedValue", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.observedValue, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.thresholdValue.label", "Threshold Value")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:thresholdValue", { value: record?.thresholdValue, record, resource: "runtime-telemetry-latest", field: "thresholdValue", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.thresholdValue, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.alertSeverity.label", "Alert Severity")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:alertSeverity", { value: record?.alertSeverity, record, resource: "runtime-telemetry-latest", field: "alertSeverity", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.alertSeverity, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.alertMessage.label", "Alert Message")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:alertMessage", { value: record?.alertMessage, record, resource: "runtime-telemetry-latest", field: "alertMessage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.alertMessage, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.healthStatus.label", "Health Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:healthStatus", { value: record?.healthStatus, record, resource: "runtime-telemetry-latest", field: "healthStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.healthStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_telemetry_latest.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-telemetry-latest:display:telemetryRetentionPolicy", { value: record?.telemetryRetentionPolicy, record, resource: "runtime-telemetry-latest", field: "telemetryRetentionPolicy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.telemetryRetentionPolicy, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
