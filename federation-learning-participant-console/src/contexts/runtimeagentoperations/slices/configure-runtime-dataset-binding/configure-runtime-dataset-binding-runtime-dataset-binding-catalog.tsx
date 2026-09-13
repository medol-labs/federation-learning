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

export const RuntimeDatasetBindingCatalogConfigureRuntimeDatasetBinding = () => {
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
    dataSourceType: searchParams.get("dataSourceType") ?? undefined,
    host: searchParams.get("host") ?? undefined,
    port: (() => { const value = searchParams.get("port"); return value === null ? undefined : Number(value); })(),
    url: searchParams.get("url") ?? undefined,
    databaseName: searchParams.get("databaseName") ?? undefined,
    schemaName: searchParams.get("schemaName") ?? undefined,
    tableName: searchParams.get("tableName") ?? undefined,
    filePath: searchParams.get("filePath") ?? undefined,
    objectBucket: searchParams.get("objectBucket") ?? undefined,
    objectPrefix: searchParams.get("objectPrefix") ?? undefined,
    dataFormat: searchParams.get("dataFormat") ?? undefined,
    credentialSecretName: searchParams.get("credentialSecretName") ?? undefined,
  } as unknown as Partial<ConfigureRuntimeDatasetBindingCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ConfigureRuntimeDatasetBindingCommandInput, ConfigureRuntimeDatasetBindingCommandInput>({
    resource: "runtime_dataset_binding_catalog",
    command: "configureRuntimeDatasetBinding",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "runtime_dataset_binding_catalog_read_model_entity",
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_binding_catalog.label", "Runtime Dataset Binding Catalog"),
      aggregateRoute: "runtimedatasetbinding",
      queryRoute: "runtimedatasetbindingcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "runtime_dataset_binding_catalog_read_model_entity",
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_binding_catalog.label", "Runtime Dataset Binding Catalog"),
      aggregateRoute: "runtimedatasetbinding",
      queryRoute: "runtimedatasetbindingcatalog",
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
    navigate("/runtime-dataset-binding-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.label", "Configure Runtime Dataset Binding")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ConfigureRuntimeDatasetBinding validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="datasetId"
            rules={{ required: "Dataset Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.datasetId.label", "Dataset Id")}</FormLabel>
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
                  placeholder={t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.datasetId.placeholder", "Select Dataset Id")}
                  meta={{
                    idField: "datasetId",
                    label: t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.datasetId.label", "Dataset Capability"),
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.organizationId.label", "Organization Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Organization Id"}
                  />
                </FormControl>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Feature Schema Id"}
                  />
                </FormControl>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.organizationName.label", "Organization Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.featureDomain.label", "Feature Domain")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Feature Domain"}
                  />
                </FormControl>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.featureSchemaVersion.label", "Feature Schema Version")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.datasetName.label", "Dataset Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.runtimeId.label", "Runtime Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Runtime Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.runtimeName.label", "Runtime Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Runtime Name"}
                  />
                </FormControl>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.dataSourceType.label", "Data Source Type")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Data Source Type"}
                  />
                </FormControl>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.host.label", "Host")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.port.label", "Port")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.url.label", "Url")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.databaseName.label", "Database Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.schemaName.label", "Schema Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.tableName.label", "Table Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.filePath.label", "File Path")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.objectBucket.label", "Object Bucket")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.objectPrefix.label", "Object Prefix")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.dataFormat.label", "Data Format")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Data Format"}
                  />
                </FormControl>
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
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.credentialSecretName.label", "Credential Secret Name")}</FormLabel>
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
