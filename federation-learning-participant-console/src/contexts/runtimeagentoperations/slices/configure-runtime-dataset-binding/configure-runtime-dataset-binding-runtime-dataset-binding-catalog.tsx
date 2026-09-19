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
    runtimeId: searchParams.get("runtimeId") ?? undefined,
    filePath: searchParams.get("filePath") ?? undefined,
    dataFormat: searchParams.get("dataFormat") ?? undefined,
    organizationId: searchParams.get("organizationId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    organizationName: searchParams.get("organizationName") ?? undefined,
    featureDomain: searchParams.get("featureDomain") ?? undefined,
    featureSchemaVersion: searchParams.get("featureSchemaVersion") ?? undefined,
    datasetName: searchParams.get("datasetName") ?? undefined,
    runtimeName: searchParams.get("runtimeName") ?? undefined,
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
          {defaultValues.organizationId !== undefined && defaultValues.organizationId !== null ? (
            <input type="hidden" {...form.register("organizationId" as never)} />
          ) : null}
          {defaultValues.featureSchemaId !== undefined && defaultValues.featureSchemaId !== null ? (
            <input type="hidden" {...form.register("featureSchemaId" as never)} />
          ) : null}
          {defaultValues.organizationName !== undefined && defaultValues.organizationName !== null ? (
            <input type="hidden" {...form.register("organizationName" as never)} />
          ) : null}
          {defaultValues.featureDomain !== undefined && defaultValues.featureDomain !== null ? (
            <input type="hidden" {...form.register("featureDomain" as never)} />
          ) : null}
          {defaultValues.featureSchemaVersion !== undefined && defaultValues.featureSchemaVersion !== null ? (
            <input type="hidden" {...form.register("featureSchemaVersion" as never)} />
          ) : null}
          {defaultValues.datasetName !== undefined && defaultValues.datasetName !== null ? (
            <input type="hidden" {...form.register("datasetName" as never)} />
          ) : null}
          {defaultValues.runtimeName !== undefined && defaultValues.runtimeName !== null ? (
            <input type="hidden" {...form.register("runtimeName" as never)} />
          ) : null}
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
                  onValueChange={(value, option) => {
                    field.onChange(value);
                    form.setValue(
                      "organizationId" as never,
                      String(option?.record?.["organizationId"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                    form.setValue(
                      "featureSchemaId" as never,
                      String(option?.record?.["featureSchemaId"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                    form.setValue(
                      "organizationName" as never,
                      String(option?.record?.["organizationName"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                    form.setValue(
                      "featureDomain" as never,
                      String(option?.record?.["featureDomain"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                    form.setValue(
                      "featureSchemaVersion" as never,
                      String(option?.record?.["featureSchemaVersion"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                    form.setValue(
                      "datasetName" as never,
                      String(option?.record?.["datasetName"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
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
            name="runtimeId"
            rules={{ required: "Runtime Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.runtimeId.label", "Runtime Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="agent_runtime_identity_catalog"
                  dataProviderName="federation-learning-runtime-agent"
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
                  placeholder={t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.runtimeId.placeholder", "Select Runtime Id")}
                  meta={{
                    idField: "runtimeId",
                    label: t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.runtimeId.label", "Agent Runtime Identity Catalog"),
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
            name="filePath"
            rules={{ required: "File Path is required" }}
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
            name="dataFormat"
            rules={{ required: "Data Format is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.dataFormat.label", "Data Format")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="agent_dictionary_value_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.dataFormat.placeholder", "Select Data Format")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"DATA_FORMAT"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.runtime_dataset_binding_catalog.commands.configureRuntimeDatasetBinding.fields.dataFormat.label", "Agent Dictionary Value Catalog"),
                    aggregateRoute: "agentdictionaryvaluecatalog",
                    queryRoute: "agentdictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
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
