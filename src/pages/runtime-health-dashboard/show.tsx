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

export const RuntimeHealthDashboardShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flplatform-backend",
    meta: {
      tableName: "runtime_health_dashboard_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_health_dashboard.label", "Runtime Health Dashboard"),
      aggregateRoute: "noderuntimehealth",
      queryRoute: "runtimehealthdashboard",
      dataProviderName: "flplatform-backend",
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.federationId.label", "Federation Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.trainingJobId.label", "Training Job Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.roundExecutionId.label", "Round Execution Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundExecutionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.federationName.label", "Federation Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.cpuLoad.label", "Cpu Load")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.cpuLoad, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.gpuLoad.label", "Gpu Load")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.gpuLoad, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.memoryLoad.label", "Memory Load")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.memoryLoad, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.nodeReady.label", "Node Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.availableCpuCores.label", "Available Cpu Cores")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.availableCpuCores, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.availableMemoryGb.label", "Available Memory Gb")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.availableMemoryGb, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.availableGpuCount.label", "Available Gpu Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.availableGpuCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.runningWorkloadCount.label", "Running Workload Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runningWorkloadCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.workloadCapacity.label", "Workload Capacity")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.workloadCapacity, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.healthStatus.label", "Health Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.healthStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.lastHeartbeatAt.label", "Last Heartbeat At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lastHeartbeatAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_health_dashboard.fields.lastResourceSnapshotAt.label", "Last Resource Snapshot At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lastResourceSnapshotAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
