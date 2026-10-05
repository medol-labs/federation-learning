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
import { ReportAgentRuntimeTelemetryCommandSchema, type ReportAgentRuntimeTelemetryCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const AgentRuntimeTelemetryLatestReportAgentRuntimeTelemetry = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeAgentId: searchParams.get("runtimeAgentId") ?? undefined,
    federationId: searchParams.get("federationId") ?? undefined,
    trainingJobId: searchParams.get("trainingJobId") ?? undefined,
    roundExecutionId: searchParams.get("roundExecutionId") ?? undefined,
    cpuLoad: (() => { const value = searchParams.get("cpuLoad"); return value === null ? undefined : Number(value); })(),
    gpuLoad: (() => { const value = searchParams.get("gpuLoad"); return value === null ? undefined : Number(value); })(),
    memoryLoad: (() => { const value = searchParams.get("memoryLoad"); return value === null ? undefined : Number(value); })(),
    lastHeartbeatAt: searchParams.get("lastHeartbeatAt") ?? undefined,
    telemetryRetentionPolicy: searchParams.get("telemetryRetentionPolicy") ?? undefined,
    nodeId: searchParams.get("nodeId") ?? undefined,
  } as unknown as Partial<ReportAgentRuntimeTelemetryCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ReportAgentRuntimeTelemetryCommandInput, ReportAgentRuntimeTelemetryCommandInput>({
    resource: "agent_runtime_telemetry_latest",
    command: "reportAgentRuntimeTelemetry",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_runtime_telemetry_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.agent_runtime_telemetry_latest.label", "Agent Runtime Telemetry Latest"),
      aggregateRoute: "agentruntimetelemetry",
      queryRoute: "agentruntimetelemetrylatest",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "agent_runtime_telemetry_latest_read_model_entity",
      idField: "nodeId",
      label: t("resources.agent_runtime_telemetry_latest.label", "Agent Runtime Telemetry Latest"),
      aggregateRoute: "agentruntimetelemetry",
      queryRoute: "agentruntimetelemetrylatest",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ReportAgentRuntimeTelemetryCommandSchema) as never,
    },
  });

  async function onSubmit(values: ReportAgentRuntimeTelemetryCommandInput) {
    const result = await runFormBehavior<ReportAgentRuntimeTelemetryCommandInput>(
      frontendComposition,
      "behavior:agent-runtime-telemetry-latest:reportAgentRuntimeTelemetry",
      {
      ...defaultValues,
      ...values,
      } as ReportAgentRuntimeTelemetryCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/agent-runtime-telemetry-latest");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.label", "Report Agent Runtime Telemetry")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ReportAgentRuntimeTelemetry validation failed", errors))} className="space-y-8">
          {defaultValues.nodeId !== undefined && defaultValues.nodeId !== null ? (
            <input type="hidden" {...form.register("nodeId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="runtimeAgentId"
            rules={{ required: "Runtime Agent Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.runtimeAgentId.label", "Runtime Agent Id")}</FormLabel>
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
                  placeholder={t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.runtimeAgentId.placeholder", "Select Runtime Agent Id")}
                  meta={{
                    idField: "runtimeAgentId",
                    label: t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.runtimeAgentId.label", "Runtime Agent Lifecycle Catalog"),
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
            name="federationId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.federationId.label", "Federation Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Federation Id"}
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
                <FormLabel>{t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.trainingJobId.label", "Training Job Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Training Job Id"}
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
                <FormLabel>{t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.roundExecutionId.label", "Round Execution Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="round_execution_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="runtimeEngineProfileName"
                  optionValue="roundExecutionId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.roundExecutionId.placeholder", "Select Round Execution Id")}
                  meta={{
                    idField: "roundExecutionId",
                    label: t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.roundExecutionId.label", "Round Execution Catalog"),
                    aggregateRoute: "roundexecution",
                    queryRoute: "roundexecutioncatalog",
                  }}
                />
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
                <FormLabel>{t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.cpuLoad.label", "Cpu Load")}</FormLabel>
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
                <FormLabel>{t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.gpuLoad.label", "Gpu Load")}</FormLabel>
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
                <FormLabel>{t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.memoryLoad.label", "Memory Load")}</FormLabel>
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
                <FormLabel>{t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.lastHeartbeatAt.label", "Last Heartbeat At")}</FormLabel>
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
            name="telemetryRetentionPolicy"
            rules={{ required: "Telemetry Retention Policy is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_telemetry_latest.commands.reportAgentRuntimeTelemetry.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")}</FormLabel>
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
