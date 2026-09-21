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

export const RuntimeHealthDashboardShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_health_dashboard_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_health_dashboard.label", "Runtime Health Dashboard"),
      aggregateRoute: "noderuntimehealth",
      queryRoute: "runtimehealthdashboard",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.nodeId ?? t("resources.runtime_health_dashboard.label", "Runtime Health Dashboard")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.nodeId.label", "Node Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:nodeId", { value: record?.nodeId, record, resource: "runtime-health-dashboard", field: "nodeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nodeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "runtime-health-dashboard", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.federationId.label", "Federation Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:federationId", { value: record?.federationId, record, resource: "runtime-health-dashboard", field: "federationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:trainingJobId", { value: record?.trainingJobId, record, resource: "runtime-health-dashboard", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.roundExecutionId.label", "Round Execution Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:roundExecutionId", { value: record?.roundExecutionId, record, resource: "runtime-health-dashboard", field: "roundExecutionId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundExecutionId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.federationName.label", "Federation Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:federationName", { value: record?.federationName, record, resource: "runtime-health-dashboard", field: "federationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:trainingJobObjective", { value: record?.trainingJobObjective, record, resource: "runtime-health-dashboard", field: "trainingJobObjective", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.cpuLoad.label", "Cpu Load")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:cpuLoad", { value: record?.cpuLoad, record, resource: "runtime-health-dashboard", field: "cpuLoad", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.cpuLoad, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.gpuLoad.label", "Gpu Load")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:gpuLoad", { value: record?.gpuLoad, record, resource: "runtime-health-dashboard", field: "gpuLoad", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.gpuLoad, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.memoryLoad.label", "Memory Load")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:memoryLoad", { value: record?.memoryLoad, record, resource: "runtime-health-dashboard", field: "memoryLoad", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.memoryLoad, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.nodeReady.label", "Node Ready")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:nodeReady", { value: record?.nodeReady, record, resource: "runtime-health-dashboard", field: "nodeReady", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nodeReady, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.availableCpuCores.label", "Available Cpu Cores")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:availableCpuCores", { value: record?.availableCpuCores, record, resource: "runtime-health-dashboard", field: "availableCpuCores", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.availableCpuCores, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.availableMemoryGb.label", "Available Memory Gb")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:availableMemoryGb", { value: record?.availableMemoryGb, record, resource: "runtime-health-dashboard", field: "availableMemoryGb", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.availableMemoryGb, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.availableGpuCount.label", "Available Gpu Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:availableGpuCount", { value: record?.availableGpuCount, record, resource: "runtime-health-dashboard", field: "availableGpuCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.availableGpuCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.runningWorkloadCount.label", "Running Workload Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:runningWorkloadCount", { value: record?.runningWorkloadCount, record, resource: "runtime-health-dashboard", field: "runningWorkloadCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runningWorkloadCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.workloadCapacity.label", "Workload Capacity")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:workloadCapacity", { value: record?.workloadCapacity, record, resource: "runtime-health-dashboard", field: "workloadCapacity", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.workloadCapacity, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.healthStatus.label", "Health Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:healthStatus", { value: record?.healthStatus, record, resource: "runtime-health-dashboard", field: "healthStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.healthStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.lastHeartbeatAt.label", "Last Heartbeat At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:lastHeartbeatAt", { value: record?.lastHeartbeatAt, record, resource: "runtime-health-dashboard", field: "lastHeartbeatAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastHeartbeatAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.lastResourceSnapshotAt.label", "Last Resource Snapshot At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-health-dashboard:display:lastResourceSnapshotAt", { value: record?.lastResourceSnapshotAt, record, resource: "runtime-health-dashboard", field: "lastResourceSnapshotAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastResourceSnapshotAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
