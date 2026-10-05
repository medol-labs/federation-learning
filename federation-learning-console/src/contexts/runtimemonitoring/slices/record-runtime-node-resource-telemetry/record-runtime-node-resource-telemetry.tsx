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
import { RecordRuntimeNodeResourceTelemetryCommandSchema, type RecordRuntimeNodeResourceTelemetryCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RuntimeNodeResourceLatestRecordRuntimeNodeResourceTelemetry = () => {
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
    lastResourceSnapshotAt: searchParams.get("lastResourceSnapshotAt") ?? undefined,
    telemetryRetentionPolicy: searchParams.get("telemetryRetentionPolicy") ?? undefined,
    nodeId: searchParams.get("nodeId") ?? undefined,
  } as unknown as Partial<RecordRuntimeNodeResourceTelemetryCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RecordRuntimeNodeResourceTelemetryCommandInput, RecordRuntimeNodeResourceTelemetryCommandInput>({
    resource: "runtime_node_resource_latest",
    command: "recordRuntimeNodeResourceTelemetry",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_node_resource_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_node_resource_latest.label", "Runtime Node Resource Latest"),
      aggregateRoute: "runtimenoderesourcetelemetry",
      queryRoute: "runtimenoderesourcelatest",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_node_resource_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_node_resource_latest.label", "Runtime Node Resource Latest"),
      aggregateRoute: "runtimenoderesourcetelemetry",
      queryRoute: "runtimenoderesourcelatest",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RecordRuntimeNodeResourceTelemetryCommandSchema) as never,
    },
  });

  async function onSubmit(values: RecordRuntimeNodeResourceTelemetryCommandInput) {
    const result = await runFormBehavior<RecordRuntimeNodeResourceTelemetryCommandInput>(
      frontendComposition,
      "behavior:runtime-node-resource-latest:recordRuntimeNodeResourceTelemetry",
      {
      ...defaultValues,
      ...values,
      } as RecordRuntimeNodeResourceTelemetryCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/runtime-node-resource-latest");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.label", "Record Runtime Node Resource Telemetry")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RecordRuntimeNodeResourceTelemetry validation failed", errors))} className="space-y-8">
          {defaultValues.nodeId !== undefined && defaultValues.nodeId !== null ? (
            <input type="hidden" {...form.register("nodeId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="runtimeAgentId"
            rules={{ required: "Runtime Agent Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.runtimeAgentId.label", "Runtime Agent Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_agent_endpoint_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="runtimeName"
                  optionValue="runtimeAgentId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.runtimeAgentId.placeholder", "Select Runtime Agent Id")}
                  meta={{
                    idField: "runtimeAgentId",
                    label: t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.runtimeAgentId.label", "Runtime Agent Endpoint Catalog"),
                    aggregateRoute: "runtimeinfrastructure",
                    queryRoute: "runtimeagentendpointcatalog",
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_infrastructure_access_view"
                  dataProviderName="federation-learning-platform"
                  optionLabel="runtimeName"
                  optionValue="runtimeInfrastructureId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.runtimeInfrastructureId.placeholder", "Select Runtime Infrastructure Id")}
                  meta={{
                    idField: "runtimeInfrastructureId",
                    label: t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Access View"),
                    aggregateRoute: "runtimeinfrastructure",
                    queryRoute: "runtimeinfrastructureaccessview",
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.runtimeNodeName.label", "Runtime Node Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.nodeReady.label", "Node Ready")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.nodeReady.placeholder", "Select Node Ready")} />
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.allocatableCpuCores.label", "Allocatable Cpu Cores")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.allocatableMemoryGb.label", "Allocatable Memory Gb")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.allocatableGpuCount.label", "Allocatable Gpu Count")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.allocatedCpuCores.label", "Allocated Cpu Cores")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.allocatedMemoryGb.label", "Allocated Memory Gb")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.allocatedGpuCount.label", "Allocated Gpu Count")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.availableCpuCores.label", "Available Cpu Cores")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.availableMemoryGb.label", "Available Memory Gb")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.availableGpuCount.label", "Available Gpu Count")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.runningWorkloadCount.label", "Running Workload Count")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.workloadCapacity.label", "Workload Capacity")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.observedAt.label", "Observed At")}</FormLabel>
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
            name="lastResourceSnapshotAt"
            rules={{ required: "Last Resource Snapshot At is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.lastResourceSnapshotAt.label", "Last Resource Snapshot At")}</FormLabel>
                <FormControl>
                  <Input
                    type="datetime-local"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Last Resource Snapshot At"}
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
                <FormLabel>{t("resources.runtime_node_resource_latest.commands.recordRuntimeNodeResourceTelemetry.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")}</FormLabel>
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
