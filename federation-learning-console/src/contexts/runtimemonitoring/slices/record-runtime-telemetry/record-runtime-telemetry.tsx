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
import { RecordRuntimeTelemetryCommandSchema, type RecordRuntimeTelemetryCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RuntimeTelemetryLatestRecordRuntimeTelemetry = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeAgentId: searchParams.get("runtimeAgentId") ?? undefined,
    federationId: searchParams.get("federationId") ?? undefined,
    federationName: searchParams.get("federationName") ?? undefined,
    trainingJobId: searchParams.get("trainingJobId") ?? undefined,
    trainingJobObjective: searchParams.get("trainingJobObjective") ?? undefined,
    roundExecutionId: searchParams.get("roundExecutionId") ?? undefined,
    runtimeNodeName: searchParams.get("runtimeNodeName") ?? undefined,
    cpuLoad: (() => { const value = searchParams.get("cpuLoad"); return value === null ? undefined : Number(value); })(),
    gpuLoad: (() => { const value = searchParams.get("gpuLoad"); return value === null ? undefined : Number(value); })(),
    memoryLoad: (() => { const value = searchParams.get("memoryLoad"); return value === null ? undefined : Number(value); })(),
    lastHeartbeatAt: searchParams.get("lastHeartbeatAt") ?? undefined,
    lastRecoveredAt: searchParams.get("lastRecoveredAt") ?? undefined,
    offlineDetectionPending: (() => { const value = searchParams.get("offlineDetectionPending"); return value === null ? undefined : value === "true"; })(),
    recoveryDetectionPending: (() => { const value = searchParams.get("recoveryDetectionPending"); return value === null ? undefined : value === "true"; })(),
    resourcePressureDetectionPending: (() => { const value = searchParams.get("resourcePressureDetectionPending"); return value === null ? undefined : value === "true"; })(),
    offlineReason: searchParams.get("offlineReason") ?? undefined,
    recoveryReason: searchParams.get("recoveryReason") ?? undefined,
    pressureType: searchParams.get("pressureType") ?? undefined,
    observedValue: (() => { const value = searchParams.get("observedValue"); return value === null ? undefined : Number(value); })(),
    thresholdValue: (() => { const value = searchParams.get("thresholdValue"); return value === null ? undefined : Number(value); })(),
    alertSeverity: searchParams.get("alertSeverity") ?? undefined,
    alertMessage: searchParams.get("alertMessage") ?? undefined,
    healthStatus: searchParams.get("healthStatus") ?? undefined,
    telemetryRetentionPolicy: searchParams.get("telemetryRetentionPolicy") ?? undefined,
    nodeId: searchParams.get("nodeId") ?? undefined,
  } as unknown as Partial<RecordRuntimeTelemetryCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RecordRuntimeTelemetryCommandInput, RecordRuntimeTelemetryCommandInput>({
    resource: "runtime_telemetry_latest",
    command: "recordRuntimeTelemetry",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_telemetry_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_telemetry_latest.label", "Runtime Telemetry Latest"),
      aggregateRoute: "noderuntimehealth",
      queryRoute: "runtimetelemetrylatest",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_telemetry_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_telemetry_latest.label", "Runtime Telemetry Latest"),
      aggregateRoute: "noderuntimehealth",
      queryRoute: "runtimetelemetrylatest",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RecordRuntimeTelemetryCommandSchema) as never,
    },
  });

  async function onSubmit(values: RecordRuntimeTelemetryCommandInput) {
    const result = await runFormBehavior<RecordRuntimeTelemetryCommandInput>(
      frontendComposition,
      "behavior:runtime-telemetry-latest:recordRuntimeTelemetry",
      {
      ...defaultValues,
      ...values,
      } as RecordRuntimeTelemetryCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/runtime-telemetry-latest");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.label", "Record Runtime Telemetry")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RecordRuntimeTelemetry validation failed", errors))} className="space-y-8">
          {defaultValues.nodeId !== undefined && defaultValues.nodeId !== null ? (
            <input type="hidden" {...form.register("nodeId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="runtimeAgentId"
            rules={{ required: "Runtime Agent Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.runtimeAgentId.label", "Runtime Agent Id")}</FormLabel>
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
                  placeholder={t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.runtimeAgentId.placeholder", "Select Runtime Agent Id")}
                  meta={{
                    idField: "runtimeAgentId",
                    label: t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.runtimeAgentId.label", "Runtime Agent Endpoint Catalog"),
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
            name="federationId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.federationId.label", "Federation Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="federation_overview"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="federationId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.federationId.placeholder", "Select Federation Id")}
                  filters={[{"field":"state","operator":"eq","value":"Active"}]}
                  meta={{
                    idField: "federationId",
                    label: t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.federationId.label", "Federation Overview"),
                    aggregateRoute: "federation",
                    queryRoute: "federationoverview",
                    queryFields: ["state"],
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="federationName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.federationName.label", "Federation Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Federation Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="trainingJobId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.trainingJobId.label", "Training Job Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_job_dashboard"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingJobId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.trainingJobId.placeholder", "Select Training Job Id")}
                  meta={{
                    idField: "trainingJobId",
                    label: t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.trainingJobId.label", "Training Job Dashboard"),
                    aggregateRoute: "trainingjob",
                    queryRoute: "trainingjobdashboard",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="trainingJobObjective"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.trainingJobObjective.label", "Training Job Objective")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Training Job Objective"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="roundExecutionId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.roundExecutionId.label", "Round Execution Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Round Execution Id"}
                  />
                </FormControl>
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
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.runtimeNodeName.label", "Runtime Node Name")}</FormLabel>
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
            name="cpuLoad"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.cpuLoad.label", "Cpu Load")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Cpu Load"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="gpuLoad"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.gpuLoad.label", "Gpu Load")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Gpu Load"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="memoryLoad"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.memoryLoad.label", "Memory Load")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Memory Load"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="lastHeartbeatAt"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.lastHeartbeatAt.label", "Last Heartbeat At")}</FormLabel>
                <FormControl>
                  <Input
                    type="datetime-local"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Last Heartbeat At"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="lastRecoveredAt"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.lastRecoveredAt.label", "Last Recovered At")}</FormLabel>
                <FormControl>
                  <Input
                    type="datetime-local"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Last Recovered At"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="offlineDetectionPending"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.offlineDetectionPending.label", "Offline Detection Pending")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.offlineDetectionPending.placeholder", "Select Offline Detection Pending")} />
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
            name="recoveryDetectionPending"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.recoveryDetectionPending.label", "Recovery Detection Pending")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.recoveryDetectionPending.placeholder", "Select Recovery Detection Pending")} />
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
            name="resourcePressureDetectionPending"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.resourcePressureDetectionPending.label", "Resource Pressure Detection Pending")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.resourcePressureDetectionPending.placeholder", "Select Resource Pressure Detection Pending")} />
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
            name="offlineReason"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.offlineReason.label", "Offline Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Offline Reason"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="recoveryReason"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.recoveryReason.label", "Recovery Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Recovery Reason"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="pressureType"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.pressureType.label", "Pressure Type")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Pressure Type"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="observedValue"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.observedValue.label", "Observed Value")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Observed Value"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="thresholdValue"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.thresholdValue.label", "Threshold Value")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Threshold Value"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="alertSeverity"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.alertSeverity.label", "Alert Severity")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Alert Severity"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="alertMessage"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.alertMessage.label", "Alert Message")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Alert Message"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="healthStatus"
            rules={{ required: "Health Status is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.healthStatus.label", "Health Status")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Health Status"}
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
                <FormLabel>{t("resources.runtime_telemetry_latest.commands.recordRuntimeTelemetry.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")}</FormLabel>
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
