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

export const AgentRuntimeNodeResourceLatestShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_runtime_node_resource_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.agent_runtime_node_resource_latest.label", "Agent Runtime Node Resource Latest"),
      aggregateRoute: "agentruntimenoderesourcetelemetry",
      queryRoute: "agentruntimenoderesourcelatest",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.nodeId ?? t("resources.agent_runtime_node_resource_latest.label", "Agent Runtime Node Resource Latest")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.nodeId.label", "Node Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.runtimeNodeName.label", "Runtime Node Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.nodeReady.label", "Node Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.allocatableCpuCores.label", "Allocatable Cpu Cores")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.allocatableCpuCores, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.allocatableMemoryGb.label", "Allocatable Memory Gb")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.allocatableMemoryGb, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.allocatableGpuCount.label", "Allocatable Gpu Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.allocatableGpuCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.allocatedCpuCores.label", "Allocated Cpu Cores")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.allocatedCpuCores, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.allocatedMemoryGb.label", "Allocated Memory Gb")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.allocatedMemoryGb, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.allocatedGpuCount.label", "Allocated Gpu Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.allocatedGpuCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.availableCpuCores.label", "Available Cpu Cores")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.availableCpuCores, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.availableMemoryGb.label", "Available Memory Gb")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.availableMemoryGb, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.availableGpuCount.label", "Available Gpu Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.availableGpuCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.runningWorkloadCount.label", "Running Workload Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runningWorkloadCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.workloadCapacity.label", "Workload Capacity")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.workloadCapacity, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.observedAt.label", "Observed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.observedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_node_resource_latest.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.telemetryRetentionPolicy, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
