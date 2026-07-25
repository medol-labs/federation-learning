// Generated from config.json by the refine generator.
import { useParsed } from "@refinedev/core";
import { useTranslate } from "@refinedev/core";
import { useNavigate, useSearchParams } from "react-router";

import {
  CreateView,
  CreateViewHeader,
} from "@/components/refine-ui/views/create-view";
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
import { zodResolver } from "@hookform/resolvers/zod";
import { RetryRoundExecutionAfterStartFailureCommandSchema, type RetryRoundExecutionAfterStartFailureCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


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
    baseModelVersionId: searchParams.get("baseModelVersionId") ?? undefined,
    runtimeEngineJobId: searchParams.get("runtimeEngineJobId") ?? undefined,
    retryReason: searchParams.get("retryReason") ?? undefined,
    roundExecutionId: searchParams.get("roundExecutionId") ?? undefined,
  } as Partial<RetryRoundExecutionAfterStartFailureCommandInput>;

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

  function onSubmit(values: RetryRoundExecutionAfterStartFailureCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.label", "Retry Round Execution After Start Failure")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <input type="hidden" {...form.register("roundExecutionId" as never)} />
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
                <ResourceSelect
                  withFormControl
                  resource="training_participant_eligibility"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingJobId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.trainingJobId.placeholder", "Select Training Job Id")}
                  meta={{
                    idField: "trainingJobId",
                    label: t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.trainingJobId.label", "Training Participant Eligibility"),
                    aggregateRoute: "trainingjob",
                    queryRoute: "trainingparticipanteligibility",
                  }}
                />
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
                <ResourceSelect
                  withFormControl
                  resource="training_run_configuration_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingRunConfigurationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.trainingRunConfigurationId.placeholder", "Select Training Run Configuration Id")}
                  meta={{
                    idField: "trainingRunConfigurationId",
                    label: t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.trainingRunConfigurationId.label", "Training Run Configuration Catalog"),
                    aggregateRoute: "trainingrunconfiguration",
                    queryRoute: "trainingrunconfigurationcatalog",
                  }}
                />
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
                  resource="runtime_identity_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="runtimeId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.runtimeId.placeholder", "Select Runtime Id")}
                  meta={{
                    idField: "runtimeId",
                    label: t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.runtimeId.label", "Runtime Identity Catalog"),
                    aggregateRoute: "runtimeidentity",
                    queryRoute: "runtimeidentitycatalog",
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
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.organizationId.label", "Organization Directory"),
                    aggregateRoute: "organization",
                    queryRoute: "organizationdirectory",
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
                  resource="feature_schema_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.featureSchemaId.label", "Feature Schema Catalog"),
                    aggregateRoute: "featureschema",
                    queryRoute: "featureschemacatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="baseModelVersionId"
            rules={{ required: "Base Model Version Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.retryRoundExecutionAfterStartFailure.fields.baseModelVersionId.label", "Base Model Version Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Base Model Version Id"}
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
              {...form.saveButtonProps}
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
