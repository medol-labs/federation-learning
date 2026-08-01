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
import { ConfigureRuntimeDatasetBindingCommandSchema, type ConfigureRuntimeDatasetBindingCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const TrainingParticipantEligibilityConfigureRuntimeDatasetBinding = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    datasetId: searchParams.get("datasetId") ?? undefined,
    organizationId: searchParams.get("organizationId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    runtimeId: searchParams.get("runtimeId") ?? undefined,
  } as Partial<ConfigureRuntimeDatasetBindingCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ConfigureRuntimeDatasetBindingCommandInput, ConfigureRuntimeDatasetBindingCommandInput>({
    resource: "training_participant_eligibility",
    command: "configureRuntimeDatasetBinding",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_participant_eligibility_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_participant_eligibility.label", "Training Participant Eligibility"),
      aggregateRoute: "runtimedatasetbinding",
      queryRoute: "trainingparticipanteligibility",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "training_participant_eligibility_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_participant_eligibility.label", "Training Participant Eligibility"),
      aggregateRoute: "trainingjob",
      queryRoute: "trainingparticipanteligibility",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ConfigureRuntimeDatasetBindingCommandSchema) as never,
    },
  });

  function onSubmit(values: ConfigureRuntimeDatasetBindingCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.label", "Configure Runtime Dataset Binding")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ConfigureRuntimeDatasetBinding validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="datasetId"
            rules={{ required: "Dataset Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.datasetId.label", "Dataset Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dataset_capability"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="datasetName"
                  optionValue="datasetId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.datasetId.placeholder", "Select Dataset Id")}
                  meta={{
                    idField: "datasetId",
                    label: t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.datasetId.label", "Dataset Capability"),
                    aggregateRoute: "dataset",
                    queryRoute: "datasetcapability",
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
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.organizationId.label", "Organization Directory"),
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
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="feature_schema_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.featureSchemaId.label", "Feature Schema Catalog"),
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
            name="runtimeId"
            rules={{ required: "Runtime Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.runtimeId.label", "Runtime Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_identity_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="runtimeName"
                  optionValue="runtimeId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.runtimeId.placeholder", "Select Runtime Id")}
                  meta={{
                    idField: "runtimeId",
                    label: t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.runtimeId.label", "Runtime Identity Catalog"),
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
            name="dataSourceType"
            rules={{ required: "Data Source Type is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.dataSourceType.label", "Data Source Type")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-dictionary"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.dataSourceType.placeholder", "Select Data Source Type")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_DATA_SOURCE_TYPE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.dataSourceType.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="host"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.host.label", "Host")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Host"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="port"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.port.label", "Port")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Port"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="url"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.url.label", "Url")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Url"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="databaseName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.databaseName.label", "Database Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Database Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="schemaName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.schemaName.label", "Schema Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Schema Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="tableName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.tableName.label", "Table Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Table Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="filePath"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.filePath.label", "File Path")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter File Path"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="objectBucket"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.objectBucket.label", "Object Bucket")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Object Bucket"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="objectPrefix"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.objectPrefix.label", "Object Prefix")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Object Prefix"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="dataFormat"
            rules={{ required: "Data Format is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.dataFormat.label", "Data Format")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-dictionary"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.dataFormat.placeholder", "Select Data Format")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"DATA_FORMAT"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.dataFormat.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="credentialSecretName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.configureRuntimeDatasetBinding.fields.credentialSecretName.label", "Credential Secret Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Credential Secret Name"}
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
