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
import { SubmitModelUpdateSubmissionCommandSchema, type SubmitModelUpdateSubmissionCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const TrainingRoundProgressSubmitModelUpdateSubmission = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    trainingJobId: searchParams.get("trainingJobId") ?? undefined,
    trainingRunConfigurationId: searchParams.get("trainingRunConfigurationId") ?? undefined,
    roundId: searchParams.get("roundId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    secureAggregationRequired: (() => { const value = searchParams.get("secureAggregationRequired"); return value === null ? undefined : value === "true"; })(),
    executionSessionId: searchParams.get("executionSessionId") ?? undefined,
    executionPlanId: searchParams.get("executionPlanId") ?? undefined,
    roundExecutionId: searchParams.get("roundExecutionId") ?? undefined,
    runtimeId: searchParams.get("runtimeId") ?? undefined,
    secureAggregationSessionId: searchParams.get("secureAggregationSessionId") ?? undefined,
    encryptionScheme: searchParams.get("encryptionScheme") ?? undefined,
    publicKeyVersion: searchParams.get("publicKeyVersion") ?? undefined,
    localModelId: searchParams.get("localModelId") ?? undefined,
    updateArtifactId: searchParams.get("updateArtifactId") ?? undefined,
    artifactRef: searchParams.get("artifactRef") ?? undefined,
    artifactDigest: searchParams.get("artifactDigest") ?? undefined,
    updateProtectionType: searchParams.get("updateProtectionType") ?? undefined,
    trainingLoss: (() => { const value = searchParams.get("trainingLoss"); return value === null ? undefined : Number(value); })(),
  } as unknown as Partial<SubmitModelUpdateSubmissionCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<SubmitModelUpdateSubmissionCommandInput, SubmitModelUpdateSubmissionCommandInput>({
    resource: "training_round_progress",
    command: "submitModelUpdateSubmission",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_round_progress_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_round_progress.label", "Training Round Progress"),
      aggregateRoute: "traininground",
      queryRoute: "trainingroundprogress",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "training_round_progress_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_round_progress.label", "Training Round Progress"),
      aggregateRoute: "traininground",
      queryRoute: "trainingroundprogress",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(SubmitModelUpdateSubmissionCommandSchema) as never,
    },
  });

  async function onSubmit(values: SubmitModelUpdateSubmissionCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/training-round-progress");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.training_round_progress.commands.submitModelUpdateSubmission.label", "Submit Model Update Submission")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("SubmitModelUpdateSubmission validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="executionSessionId"
            rules={{ required: "Execution Session Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.executionSessionId.label", "Execution Session Id")}</FormLabel>
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.executionPlanId.label", "Execution Plan Id")}</FormLabel>
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.trainingJobId.label", "Training Job Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_job_dashboard"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingJobId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.trainingJobId.placeholder", "Select Training Job Id")}
                  meta={{
                    idField: "trainingJobId",
                    label: t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.trainingJobId.label", "Training Job Dashboard"),
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
            name="trainingRunConfigurationId"
            rules={{ required: "Training Run Configuration Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_run_configuration_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="configurationName"
                  optionValue="trainingRunConfigurationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.trainingRunConfigurationId.placeholder", "Select Training Run Configuration Id")}
                  meta={{
                    idField: "trainingRunConfigurationId",
                    label: t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.trainingRunConfigurationId.label", "Training Run Configuration Catalog"),
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.roundId.label", "Round Id")}</FormLabel>
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.roundExecutionId.label", "Round Execution Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="round_execution_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="runtimeEngineObservedStatus"
                  optionValue="roundExecutionId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.roundExecutionId.placeholder", "Select Round Execution Id")}
                  meta={{
                    idField: "roundExecutionId",
                    label: t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.roundExecutionId.label", "Round Execution Catalog"),
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.runtimeId.label", "Runtime Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_identity_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="runtimeName"
                  optionValue="runtimeId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.runtimeId.placeholder", "Select Runtime Id")}
                  meta={{
                    idField: "runtimeId",
                    label: t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.runtimeId.label", "Runtime Identity Catalog"),
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="feature_schema_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.featureSchemaId.label", "Feature Schema Catalog"),
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
            name="secureAggregationRequired"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.secureAggregationRequired.label", "Secure Aggregation Required")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.secureAggregationRequired.placeholder", "Select Secure Aggregation Required")} />
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
            name="secureAggregationSessionId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.secureAggregationSessionId.label", "Secure Aggregation Session Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="secure_aggregation_session_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="publicKeyVersion"
                  optionValue="secureAggregationSessionId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.secureAggregationSessionId.placeholder", "Select Secure Aggregation Session Id")}
                  meta={{
                    idField: "secureAggregationSessionId",
                    label: t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.secureAggregationSessionId.label", "Secure Aggregation Session Catalog"),
                    aggregateRoute: "secureaggregationsession",
                    queryRoute: "secureaggregationsessioncatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="encryptionScheme"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.encryptionScheme.label", "Encryption Scheme")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Encryption Scheme"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="publicKeyVersion"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.publicKeyVersion.label", "Public Key Version")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Public Key Version"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="localModelId"
            rules={{ required: "Local Model Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.localModelId.label", "Local Model Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Local Model Id"}
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.updateArtifactId.label", "Update Artifact Id")}</FormLabel>
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.artifactRef.label", "Artifact Ref")}</FormLabel>
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.artifactDigest.label", "Artifact Digest")}</FormLabel>
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
            name="updateProtectionType"
            rules={{ required: "Update Protection Type is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.updateProtectionType.label", "Update Protection Type")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Update Protection Type"}
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
                <FormLabel>{t("resources.training_round_progress.commands.submitModelUpdateSubmission.fields.trainingLoss.label", "Training Loss")}</FormLabel>
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
