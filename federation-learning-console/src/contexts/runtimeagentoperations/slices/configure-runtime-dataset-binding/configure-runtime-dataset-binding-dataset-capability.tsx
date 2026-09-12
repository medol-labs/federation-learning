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
import { ConfigureRuntimeDatasetBindingCommandSchema, type ConfigureRuntimeDatasetBindingCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const DatasetCapabilityConfigureRuntimeDatasetBinding = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    datasetId: searchParams.get("datasetId") ?? undefined,
    organizationId: searchParams.get("organizationId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    organizationName: searchParams.get("organizationName") ?? undefined,
    featureDomain: searchParams.get("featureDomain") ?? undefined,
    featureSchemaVersion: searchParams.get("featureSchemaVersion") ?? undefined,
    datasetName: searchParams.get("datasetName") ?? undefined,
    runtimeId: searchParams.get("runtimeId") ?? undefined,
    runtimeName: searchParams.get("runtimeName") ?? undefined,
  } as unknown as Partial<ConfigureRuntimeDatasetBindingCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ConfigureRuntimeDatasetBindingCommandInput, ConfigureRuntimeDatasetBindingCommandInput>({
    resource: "dataset_capability",
    command: "configureRuntimeDatasetBinding",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "dataset_capability_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_capability.label", "Dataset Capability"),
      aggregateRoute: "runtimedatasetbinding",
      queryRoute: "datasetcapability",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "dataset_capability_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_capability.label", "Dataset Capability"),
      aggregateRoute: "dataset",
      queryRoute: "datasetcapability",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ConfigureRuntimeDatasetBindingCommandSchema) as never,
    },
  });

  async function onSubmit(values: ConfigureRuntimeDatasetBindingCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/dataset-capability");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.label", "Configure Runtime Dataset Binding")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ConfigureRuntimeDatasetBinding validation failed", errors))} className="space-y-8">
          {defaultValues.runtimeName !== undefined && defaultValues.runtimeName !== null ? (
            <input type="hidden" {...form.register("runtimeName" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="datasetId"
            rules={{ required: "Dataset Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.datasetId.label", "Dataset Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dataset_capability"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="datasetName"
                  optionValue="datasetId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.datasetId.placeholder", "Select Dataset Id")}
                  meta={{
                    idField: "datasetId",
                    label: t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.datasetId.label", "Dataset Capability"),
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.organizationId.label", "Organization Directory"),
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="feature_schema_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.featureSchemaId.label", "Feature Schema Catalog"),
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
            name="organizationName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.organizationName.label", "Organization Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Organization Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="featureDomain"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.featureDomain.label", "Feature Domain")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="current_recommended_feature_schema_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="recommendedVersion"
                  optionValue="featureDomain"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.featureDomain.placeholder", "Select Feature Domain")}
                  meta={{
                    idField: "featureDomain",
                    label: t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.featureDomain.label", "Current Recommended Feature Schema Catalog"),
                    aggregateRoute: "featureschema",
                    queryRoute: "currentrecommendedfeatureschemacatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="featureSchemaVersion"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.featureSchemaVersion.label", "Feature Schema Version")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Feature Schema Version"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="datasetName"
            rules={{ required: "Dataset Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.datasetName.label", "Dataset Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Dataset Name"}
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.runtimeId.label", "Runtime Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_identity_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="runtimeName"
                  optionValue="runtimeId"
                  value={field.value || ""}
                  onValueChange={(value, option) => {
                    field.onChange(value);
                    form.setValue(
                      "runtimeName" as never,
                      String(option?.record?.["runtimeName"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                  }}
                  placeholder={t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.runtimeId.placeholder", "Select Runtime Id")}
                  meta={{
                    idField: "runtimeId",
                    label: t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.runtimeId.label", "Runtime Identity Catalog"),
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.dataSourceType.label", "Data Source Type")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.dataSourceType.placeholder", "Select Data Source Type")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_DATA_SOURCE_TYPE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.dataSourceType.label", "Dictionary Value Catalog"),
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.host.label", "Host")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.port.label", "Port")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.url.label", "Url")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.databaseName.label", "Database Name")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.schemaName.label", "Schema Name")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.tableName.label", "Table Name")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.filePath.label", "File Path")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.objectBucket.label", "Object Bucket")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.objectPrefix.label", "Object Prefix")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.dataFormat.label", "Data Format")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.dataFormat.placeholder", "Select Data Format")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"DATA_FORMAT"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.dataFormat.label", "Dictionary Value Catalog"),
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
                <FormLabel>{t("resources.dataset_capability.commands.configureRuntimeDatasetBinding.fields.credentialSecretName.label", "Credential Secret Name")}</FormLabel>
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
