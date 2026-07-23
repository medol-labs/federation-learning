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
import { SubmitModelUpdateSubmissionCommandSchema, type SubmitModelUpdateSubmissionCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const RoundExecutionCatalogSubmitModelUpdateSubmission = () => {
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
    roundExecutionId: searchParams.get("roundExecutionId") ?? undefined,
    runtimeId: searchParams.get("runtimeId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    updateArtifactId: searchParams.get("updateArtifactId") ?? undefined,
    artifactRef: searchParams.get("artifactRef") ?? undefined,
    artifactDigest: searchParams.get("artifactDigest") ?? undefined,
    trainingLoss: (() => { const value = searchParams.get("trainingLoss"); return value === null ? undefined : Number(value); })(),
    localModelVersionId: searchParams.get("localModelVersionId") ?? undefined,
  } as Partial<SubmitModelUpdateSubmissionCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<SubmitModelUpdateSubmissionCommandInput, SubmitModelUpdateSubmissionCommandInput>({
    resource: "round_execution_catalog",
    command: "submitModelUpdateSubmission",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-console",
    queryDataProviderName: "federation-learning-console",
    meta: {
      tableName: "round_execution_catalog_read_model_entity",
      idField: "roundExecutionId",
      label: t("resources.round_execution_catalog.label", "Round Execution Catalog"),
      aggregateRoute: "traininground",
      queryRoute: "roundexecutioncatalog",
      dataProviderName: "federation-learning-console",
    },
    queryMeta: {
      tableName: "round_execution_catalog_read_model_entity",
      idField: "roundExecutionId",
      label: t("resources.round_execution_catalog.label", "Round Execution Catalog"),
      aggregateRoute: "roundexecution",
      queryRoute: "roundexecutioncatalog",
      dataProviderName: "federation-learning-console",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(SubmitModelUpdateSubmissionCommandSchema) as never,
    },
  });

  function onSubmit(values: SubmitModelUpdateSubmissionCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.label", "Submit Model Update Submission")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="executionSessionId"
            rules={{ required: "Execution Session Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.executionSessionId.label", "Execution Session Id")}</FormLabel>
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
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.executionPlanId.label", "Execution Plan Id")}</FormLabel>
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
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.trainingJobId.label", "Training Job Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_participant_eligibility"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingJobId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.trainingJobId.placeholder", "Select Training Job Id")}
                  meta={{
                    idField: "trainingJobId",
                    label: t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.trainingJobId.label", "Training Participant Eligibility"),
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
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_run_configuration_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingRunConfigurationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.trainingRunConfigurationId.placeholder", "Select Training Run Configuration Id")}
                  meta={{
                    idField: "trainingRunConfigurationId",
                    label: t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.trainingRunConfigurationId.label", "Training Run Configuration Catalog"),
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
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.roundId.label", "Round Id")}</FormLabel>
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
            name="roundExecutionId"
            rules={{ required: "Round Execution Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.roundExecutionId.label", "Round Execution Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="round_execution_catalog"
                  dataProviderName="federation-learning-console"
                  optionLabel="artifactRef"
                  optionValue="roundExecutionId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.roundExecutionId.placeholder", "Select Round Execution Id")}
                  meta={{
                    idField: "roundExecutionId",
                    label: t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.roundExecutionId.label", "Round Execution Catalog"),
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
            name="runtimeId"
            rules={{ required: "Runtime Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.runtimeId.label", "Runtime Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_identity_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="runtimeId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.runtimeId.placeholder", "Select Runtime Id")}
                  meta={{
                    idField: "runtimeId",
                    label: t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.runtimeId.label", "Runtime Identity Catalog"),
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
            name="featureSchemaId"
            rules={{ required: "Feature Schema Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="feature_schema_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.featureSchemaId.label", "Feature Schema Catalog"),
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
            name="localModelVersionId"
            rules={{ required: "Local Model Version Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.localModelVersionId.label", "Local Model Version Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Local Model Version Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="updateArtifactId"
            rules={{ required: "Update Artifact Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.updateArtifactId.label", "Update Artifact Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Update Artifact Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="artifactRef"
            rules={{ required: "Artifact Ref is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.artifactRef.label", "Artifact Ref")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Artifact Ref"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="artifactDigest"
            rules={{ required: "Artifact Digest is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.artifactDigest.label", "Artifact Digest")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Artifact Digest"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="trainingLoss"
            rules={{ required: "Training Loss is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.round_execution_catalog.commands.submitModelUpdateSubmission.fields.trainingLoss.label", "Training Loss")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Training Loss"}
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
