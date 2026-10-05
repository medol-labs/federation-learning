// Generated from config.json by the refine generator.
import { useParsed } from "@refinedev/core";
import { useTranslate } from "@refinedev/core";
import { useNavigate, useSearchParams } from "react-router";

import {
  CreateView,
  CreateViewHeader,
} from "@/components/refine-ui/views/create-view";
import { frontendComposition } from "@/app/composition/composition.resolved";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { useCommandForm } from "@/hooks/command/useCommandForm";
import { runFormBehavior } from "@/platform/composition";
import { zodResolver } from "@hookform/resolvers/zod";
import { ReportAgentRuntimeNodeResourceTelemetryCommandSchema, type ReportAgentRuntimeNodeResourceTelemetryCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const AgentRuntimeNodeResourceLatestReportAgentRuntimeNodeResourceTelemetry = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeAgentId: searchParams.get("runtimeAgentId") ?? undefined,
    runtimeInfrastructureId: searchParams.get("runtimeInfrastructureId") ?? undefined,
    runtimeNodeName: searchParams.get("runtimeNodeName") ?? undefined,
    nodeReady: (() => { const value = searchParams.get("nodeReady"); return value === null ? undefined : value === "true"; })(),
    allocatableCpuCores: (() => { const value = searchParams.get("allocatableCpuCores"); return value === null ? undefined : Number(value); })(),
    allocatableMemoryGb: (() => { const value = searchParams.get("allocatableMemoryGb"); return value === null ? undefined : Number(value); })(),
    allocatableGpuCount: (() => { const value = searchParams.get("allocatableGpuCount"); return value === null ? undefined : Number(value); })(),
    allocatedCpuCores: (() => { const value = searchParams.get("allocatedCpuCores"); return value === null ? undefined : Number(value); })(),
    allocatedMemoryGb: (() => { const value = searchParams.get("allocatedMemoryGb"); return value === null ? undefined : Number(value); })(),
    allocatedGpuCount: (() => { const value = searchParams.get("allocatedGpuCount"); return value === null ? undefined : Number(value); })(),
    availableCpuCores: (() => { const value = searchParams.get("availableCpuCores"); return value === null ? undefined : Number(value); })(),
    availableMemoryGb: (() => { const value = searchParams.get("availableMemoryGb"); return value === null ? undefined : Number(value); })(),
    availableGpuCount: (() => { const value = searchParams.get("availableGpuCount"); return value === null ? undefined : Number(value); })(),
    runningWorkloadCount: (() => { const value = searchParams.get("runningWorkloadCount"); return value === null ? undefined : Number(value); })(),
    workloadCapacity: (() => { const value = searchParams.get("workloadCapacity"); return value === null ? undefined : Number(value); })(),
    observedAt: searchParams.get("observedAt") ?? undefined,
    telemetryRetentionPolicy: searchParams.get("telemetryRetentionPolicy") ?? undefined,
    nodeId: searchParams.get("nodeId") ?? undefined,
  } as unknown as Partial<ReportAgentRuntimeNodeResourceTelemetryCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ReportAgentRuntimeNodeResourceTelemetryCommandInput, ReportAgentRuntimeNodeResourceTelemetryCommandInput>({
    resource: "agent_runtime_node_resource_latest",
    command: "reportAgentRuntimeNodeResourceTelemetry",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_runtime_node_resource_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.agent_runtime_node_resource_latest.label", "Agent Runtime Node Resource Latest"),
      aggregateRoute: "agentruntimenoderesourcetelemetry",
      queryRoute: "agentruntimenoderesourcelatest",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "agent_runtime_node_resource_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.agent_runtime_node_resource_latest.label", "Agent Runtime Node Resource Latest"),
      aggregateRoute: "agentruntimenoderesourcetelemetry",
      queryRoute: "agentruntimenoderesourcelatest",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ReportAgentRuntimeNodeResourceTelemetryCommandSchema) as never,
    },
  });

  async function onSubmit(values: ReportAgentRuntimeNodeResourceTelemetryCommandInput) {
    const result = await runFormBehavior<ReportAgentRuntimeNodeResourceTelemetryCommandInput>(
      frontendComposition,
      "behavior:agent-runtime-node-resource-latest:reportAgentRuntimeNodeResourceTelemetry",
      {
      ...defaultValues,
      ...values,
      } as ReportAgentRuntimeNodeResourceTelemetryCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/agent-runtime-node-resource-latest");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.label", "Report Agent Runtime Node Resource Telemetry")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ReportAgentRuntimeNodeResourceTelemetry validation failed", errors))} className="space-y-8">
          {defaultValues.nodeId !== undefined && defaultValues.nodeId !== null ? (
            <input type="hidden" {...form.register("nodeId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="runtimeAgentId"
            rules={{ required: "Runtime Agent Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.runtimeAgentId.label", "Runtime Agent Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_agent_lifecycle_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="agentVersion"
                  optionValue="runtimeAgentId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.runtimeAgentId.placeholder", "Select Runtime Agent Id")}
                  meta={{
                    idField: "runtimeAgentId",
                    label: t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.runtimeAgentId.label", "Runtime Agent Lifecycle Catalog"),
                    aggregateRoute: "runtimeagentlifecycle",
                    queryRoute: "runtimeagentlifecyclecatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeInfrastructureId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="agent_runtime_infrastructure_connection_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="connectionReportFailureReason"
                  optionValue="runtimeInfrastructureId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.runtimeInfrastructureId.placeholder", "Select Runtime Infrastructure Id")}
                  meta={{
                    idField: "runtimeInfrastructureId",
                    label: t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.runtimeInfrastructureId.label", "Agent Runtime Infrastructure Connection Catalog"),
                    aggregateRoute: "agentruntimeinfrastructureconnection",
                    queryRoute: "agentruntimeinfrastructureconnectioncatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeNodeName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.runtimeNodeName.label", "Runtime Node Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Runtime Node Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="nodeReady"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.nodeReady.label", "Node Ready")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.nodeReady.placeholder", "Select Node Ready")} />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="true">{t("values.boolean.true", "True")}</SelectItem>
                    <SelectItem value="false">{t("values.boolean.false", "False")}</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="allocatableCpuCores"
            rules={{ required: "Allocatable Cpu Cores is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.allocatableCpuCores.label", "Allocatable Cpu Cores")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Allocatable Cpu Cores"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="allocatableMemoryGb"
            rules={{ required: "Allocatable Memory Gb is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.allocatableMemoryGb.label", "Allocatable Memory Gb")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Allocatable Memory Gb"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="allocatableGpuCount"
            rules={{ required: "Allocatable Gpu Count is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.allocatableGpuCount.label", "Allocatable Gpu Count")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Allocatable Gpu Count"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="allocatedCpuCores"
            rules={{ required: "Allocated Cpu Cores is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.allocatedCpuCores.label", "Allocated Cpu Cores")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Allocated Cpu Cores"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="allocatedMemoryGb"
            rules={{ required: "Allocated Memory Gb is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.allocatedMemoryGb.label", "Allocated Memory Gb")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Allocated Memory Gb"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="allocatedGpuCount"
            rules={{ required: "Allocated Gpu Count is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.allocatedGpuCount.label", "Allocated Gpu Count")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Allocated Gpu Count"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="availableCpuCores"
            rules={{ required: "Available Cpu Cores is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.availableCpuCores.label", "Available Cpu Cores")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Available Cpu Cores"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="availableMemoryGb"
            rules={{ required: "Available Memory Gb is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.availableMemoryGb.label", "Available Memory Gb")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Available Memory Gb"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="availableGpuCount"
            rules={{ required: "Available Gpu Count is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.availableGpuCount.label", "Available Gpu Count")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Available Gpu Count"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runningWorkloadCount"
            rules={{ required: "Running Workload Count is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.runningWorkloadCount.label", "Running Workload Count")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Running Workload Count"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="workloadCapacity"
            rules={{ required: "Workload Capacity is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.workloadCapacity.label", "Workload Capacity")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Workload Capacity"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="observedAt"
            rules={{ required: "Observed At is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.observedAt.label", "Observed At")}</FormLabel>
                <FormControl>
                  <Input
                    type="datetime-local"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Observed At"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="telemetryRetentionPolicy"
            rules={{ required: "Telemetry Retention Policy is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_node_resource_latest.commands.reportAgentRuntimeNodeResourceTelemetry.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Telemetry Retention Policy"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="flex gap-2">
            <Button
              type="submit"
              disabled={form.formState.isSubmitting}
            >
              {form.formState.isSubmitting ? t("buttons.submitting", "Submitting...") : t("buttons.submit", "Submit")}
            </Button>
            <Button
              type="button"
              variant="outline"
              onClick={() => navigate(-1)}
            >
              {t("buttons.cancel", "Cancel")}
            </Button>
          </div>
        </form>
      </Form>
    </CreateView>
  );
};
