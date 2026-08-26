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
import { CompleteSecureAggregationCommandSchema, type CompleteSecureAggregationCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const SecureAggregationSessionCatalogCompleteSecureAggregation = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    trainingRunConfigurationId: searchParams.get("trainingRunConfigurationId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    roundId: searchParams.get("roundId") ?? undefined,
    roundNumber: (() => { const value = searchParams.get("roundNumber"); return value === null ? undefined : Number(value); })(),
    secureAggregationSessionId: searchParams.get("secureAggregationSessionId") ?? undefined,
    aggregatedModelId: searchParams.get("aggregatedModelId") ?? undefined,
    modelFormat: searchParams.get("modelFormat") ?? undefined,
    modelArtifactDigest: searchParams.get("modelArtifactDigest") ?? undefined,
    trainingJobId: searchParams.get("trainingJobId") ?? undefined,
    aggregatedModelArtifactUri: searchParams.get("aggregatedModelArtifactUri") ?? undefined,
    aggregatedModelRegistryRef: searchParams.get("aggregatedModelRegistryRef") ?? undefined,
    aggregatedModelSignatureUri: searchParams.get("aggregatedModelSignatureUri") ?? undefined,
  } as Partial<CompleteSecureAggregationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<CompleteSecureAggregationCommandInput, CompleteSecureAggregationCommandInput>({
    resource: "secure_aggregation_session_catalog",
    command: "completeSecureAggregation",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "secure_aggregation_session_catalog_read_model_entity",
      idField: "secureAggregationSessionId",
      label: t("resources.secure_aggregation_session_catalog.label", "Secure Aggregation Session Catalog"),
      aggregateRoute: "traininground",
      queryRoute: "secureaggregationsessioncatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "secure_aggregation_session_catalog_read_model_entity",
      idField: "secureAggregationSessionId",
      label: t("resources.secure_aggregation_session_catalog.label", "Secure Aggregation Session Catalog"),
      aggregateRoute: "secureaggregationsession",
      queryRoute: "secureaggregationsessioncatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(CompleteSecureAggregationCommandSchema) as never,
    },
  });

  async function onSubmit(values: CompleteSecureAggregationCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.label", "Complete Secure Aggregation")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("CompleteSecureAggregation validation failed", errors))} className="space-y-8">
          {defaultValues.trainingJobId !== undefined && defaultValues.trainingJobId !== null ? (
            <input type="hidden" {...form.register("trainingJobId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="trainingRunConfigurationId"
            rules={{ required: "Training Run Configuration Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_run_configuration_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="initialModelName"
                  optionValue="trainingRunConfigurationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.trainingRunConfigurationId.placeholder", "Select Training Run Configuration Id")}
                  meta={{
                    idField: "trainingRunConfigurationId",
                    label: t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.trainingRunConfigurationId.label", "Training Run Configuration Catalog"),
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
            name="featureSchemaId"
            rules={{ required: "Feature Schema Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="feature_schema_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.featureSchemaId.label", "Feature Schema Catalog"),
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
            name="roundId"
            rules={{ required: "Round Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.roundId.label", "Round Id")}</FormLabel>
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
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.roundNumber.label", "Round Number")}</FormLabel>
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
            name="secureAggregationSessionId"
            rules={{ required: "Secure Aggregation Session Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.secureAggregationSessionId.label", "Secure Aggregation Session Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="secure_aggregation_session_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="publicKeyVersion"
                  optionValue="secureAggregationSessionId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.secureAggregationSessionId.placeholder", "Select Secure Aggregation Session Id")}
                  meta={{
                    idField: "secureAggregationSessionId",
                    label: t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.secureAggregationSessionId.label", "Secure Aggregation Session Catalog"),
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
            name="aggregatedModelId"
            rules={{ required: "Aggregated Model Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.aggregatedModelId.label", "Aggregated Model Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Aggregated Model Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="aggregatedModelArtifactUri"
            rules={{ required: "Aggregated Model Artifact Uri is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.aggregatedModelArtifactUri.label", "Aggregated Model Artifact Uri")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Aggregated Model Artifact Uri"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="aggregatedModelRegistryRef"
            rules={{ required: "Aggregated Model Registry Ref is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.aggregatedModelRegistryRef.label", "Aggregated Model Registry Ref")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Aggregated Model Registry Ref"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelFormat"
            rules={{ required: "Model Format is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.modelFormat.label", "Model Format")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Format"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelArtifactDigest"
            rules={{ required: "Model Artifact Digest is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.modelArtifactDigest.label", "Model Artifact Digest")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Artifact Digest"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="aggregatedModelSignatureUri"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.completeSecureAggregation.fields.aggregatedModelSignatureUri.label", "Aggregated Model Signature Uri")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Aggregated Model Signature Uri"}
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
