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
import { RetryRoundExecutionAfterStartFailureCommandSchema, type RetryRoundExecutionAfterStartFailureCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RoundExecutionCatalogRetryRoundExecutionAfterStartFailure = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    executionSessionId: searchParams.get("executionSessionId") ?? undefined,
    executionPlanId: searchParams.get("executionPlanId") ?? undefined,
    trainingJobId: searchParams.get("trainingJobId") ?? undefined,
    trainingRunConfigurationId: searchParams.get("trainingRunConfigurationId") ?? undefined,
    roundId: searchParams.get("roundId") ?? undefined,
    roundNumber: (() => { const value = searchParams.get("roundNumber"); return value === null ? undefined : Number(value); })(),
    runtimeId: searchParams.get("runtimeId") ?? undefined,
    organizationId: searchParams.get("organizationId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    baseModelId: searchParams.get("baseModelId") ?? undefined,
    runtimeEngineJobId: searchParams.get("runtimeEngineJobId") ?? undefined,
    retryReason: searchParams.get("retryReason") ?? undefined,
    roundExecutionId: searchParams.get("roundExecutionId") ?? undefined,
  } as unknown as Partial<RetryRoundExecutionAfterStartFailureCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RetryRoundExecutionAfterStartFailureCommandInput, RetryRoundExecutionAfterStartFailureCommandInput>({
    resource: "round_execution_catalog",
    command: "retryRoundExecutionAfterStartFailure",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "round_execution_catalog_read_model_entity",
      idField: "roundExecutionId",
      label: t("resources.round_execution_catalog.label", "Round Execution Catalog"),
      aggregateRoute: "roundexecution",
      queryRoute: "roundexecutioncatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "round_execution_catalog_read_model_entity",
      idField: "roundExecutionId",
      label: t("resources.round_execution_catalog.label", "Round Execution Catalog"),
      aggregateRoute: "roundexecution",
      queryRoute: "roundexecutioncatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RetryRoundExecutionAfterStartFailureCommandSchema) as never,
    },
  });

  async function onSubmit(values: RetryRoundExecutionAfterStartFailureCommandInput) {
    const result = await runFormBehavior<RetryRoundExecutionAfterStartFailureCommandInput>(
      frontendComposition,
      "behavior:round-execution-catalog:retryRoundExecutionAfterStartFailure",
      {
      ...defaultValues,
      ...values,
      } as RetryRoundExecutionAfterStartFailureCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/round-execution-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.label", "Retry Round Execution After Start Failure")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RetryRoundExecutionAfterStartFailure validation failed", errors))} className="space-y-8">
          {defaultValues.roundExecutionId !== undefined && defaultValues.roundExecutionId !== null ? (
            <input type="hidden" {...form.register("roundExecutionId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="executionSessionId"
            rules={{ required: "Execution Session Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.executionSessionId.label", "Execution Session Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Execution Session Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="executionPlanId"
            rules={{ required: "Execution Plan Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.executionPlanId.label", "Execution Plan Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Execution Plan Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="trainingJobId"
            rules={{ required: "Training Job Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.trainingJobId.label", "Training Job Id")}</FormLabel>
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
            name="trainingRunConfigurationId"
            rules={{ required: "Training Run Configuration Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Training Run Configuration Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="roundId"
            rules={{ required: "Round Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.roundId.label", "Round Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Round Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="roundNumber"
            rules={{ required: "Round Number is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.roundNumber.label", "Round Number")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Round Number"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeId"
            rules={{ required: "Runtime Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.runtimeId.label", "Runtime Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="agent_runtime_identity_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="runtimeName"
                  optionValue="runtimeId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.runtimeId.placeholder", "Select Runtime Id")}
                  meta={{
                    idField: "runtimeId",
                    label: t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.runtimeId.label", "Agent Runtime Identity Catalog"),
                    aggregateRoute: "agentruntimeidentitycatalog",
                    queryRoute: "agentruntimeidentitycatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="agent_organization_directory"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.organizationId.label", "Agent Organization Directory"),
                    aggregateRoute: "agentorganizationdirectory",
                    queryRoute: "agentorganizationdirectory",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="featureSchemaId"
            rules={{ required: "Feature Schema Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="agent_feature_schema_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.featureSchemaId.label", "Agent Feature Schema Catalog"),
                    aggregateRoute: "agentfeatureschemacatalog",
                    queryRoute: "agentfeatureschemacatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="baseModelId"
            rules={{ required: "Base Model Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.baseModelId.label", "Base Model Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Base Model Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeEngineJobId"
            rules={{ required: "Runtime Engine Job Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.runtimeEngineJobId.label", "Runtime Engine Job Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Runtime Engine Job Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="retryReason"
            rules={{ required: "Retry Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.retryReason.label", "Retry Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Retry Reason"}
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
