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
import { CompleteRoundExecutionCommandSchema, type CompleteRoundExecutionCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const RoundExecutionCatalogCompleteRoundExecution = () => {
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
    runtimeId: searchParams.get("runtimeId") ?? undefined,
    runtimeEngineJobId: searchParams.get("runtimeEngineJobId") ?? undefined,
    roundExecutionId: searchParams.get("roundExecutionId") ?? undefined,
  } as Partial<CompleteRoundExecutionCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<CompleteRoundExecutionCommandInput, CompleteRoundExecutionCommandInput>({
    resource: "round_execution_catalog",
    command: "completeRoundExecution",
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
      resolver: zodResolver(CompleteRoundExecutionCommandSchema) as never,
    },
  });

  function onSubmit(values: CompleteRoundExecutionCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.round_execution_catalog.commands.completeRoundExecution.label", "Complete Round Execution")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("CompleteRoundExecution validation failed", errors))} className="space-y-8">
          {defaultValues.roundExecutionId !== undefined && defaultValues.roundExecutionId !== null ? (
            <input type="hidden" {...form.register("roundExecutionId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="executionSessionId"
            rules={{ required: "Execution Session Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.completeRoundExecution.fields.executionSessionId.label", "Execution Session Id")}</FormLabel>
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
                <FormLabel>{t("resources.round_execution_catalog.commands.completeRoundExecution.fields.executionPlanId.label", "Execution Plan Id")}</FormLabel>
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
                <FormLabel>{t("resources.round_execution_catalog.commands.completeRoundExecution.fields.trainingJobId.label", "Training Job Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_participant_eligibility"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingJobId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.completeRoundExecution.fields.trainingJobId.placeholder", "Select Training Job Id")}
                  meta={{
                    idField: "trainingJobId",
                    label: t("resources.round_execution_catalog.commands.completeRoundExecution.fields.trainingJobId.label", "Training Participant Eligibility"),
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
                <FormLabel>{t("resources.round_execution_catalog.commands.completeRoundExecution.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_run_configuration_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingRunConfigurationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.completeRoundExecution.fields.trainingRunConfigurationId.placeholder", "Select Training Run Configuration Id")}
                  meta={{
                    idField: "trainingRunConfigurationId",
                    label: t("resources.round_execution_catalog.commands.completeRoundExecution.fields.trainingRunConfigurationId.label", "Training Run Configuration Catalog"),
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
                <FormLabel>{t("resources.round_execution_catalog.commands.completeRoundExecution.fields.roundId.label", "Round Id")}</FormLabel>
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
            name="runtimeId"
            rules={{ required: "Runtime Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.completeRoundExecution.fields.runtimeId.label", "Runtime Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_identity_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="runtimeName"
                  optionValue="runtimeId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.completeRoundExecution.fields.runtimeId.placeholder", "Select Runtime Id")}
                  meta={{
                    idField: "runtimeId",
                    label: t("resources.round_execution_catalog.commands.completeRoundExecution.fields.runtimeId.label", "Runtime Identity Catalog"),
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
            name="runtimeEngineJobId"
            rules={{ required: "Runtime Engine Job Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.completeRoundExecution.fields.runtimeEngineJobId.label", "Runtime Engine Job Id")}</FormLabel>
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
