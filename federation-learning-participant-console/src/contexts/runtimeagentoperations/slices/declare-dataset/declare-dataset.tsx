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
import { DeclareDatasetCommandSchema, type DeclareDatasetCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const DatasetCapabilityDeclareDataset = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationId: searchParams.get("organizationId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    datasetName: searchParams.get("datasetName") ?? undefined,
    datasetUsage: searchParams.get("datasetUsage") ?? undefined,
    organizationName: searchParams.get("organizationName") ?? undefined,
    featureDomain: searchParams.get("featureDomain") ?? undefined,
    featureSchemaVersion: searchParams.get("featureSchemaVersion") ?? undefined,
  } as unknown as Partial<DeclareDatasetCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<DeclareDatasetCommandInput, DeclareDatasetCommandInput>({
    resource: "dataset_capability",
    command: "declareDataset",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "dataset_capability_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_capability.label", "Dataset Capability"),
      aggregateRoute: "dataset",
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
      resolver: zodResolver(DeclareDatasetCommandSchema) as never,
    },
  });

  async function onSubmit(values: DeclareDatasetCommandInput) {
    const result = await runFormBehavior<DeclareDatasetCommandInput>(
      frontendComposition,
      "behavior:dataset-capability:declareDataset",
      {
      ...defaultValues,
      ...values,
      } as DeclareDatasetCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/dataset-capability");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dataset_capability.commands.declareDataset.label", "Declare Dataset")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("DeclareDataset validation failed", errors))} className="space-y-8">
          {defaultValues.organizationName !== undefined && defaultValues.organizationName !== null ? (
            <input type="hidden" {...form.register("organizationName" as never)} />
          ) : null}
          {defaultValues.featureDomain !== undefined && defaultValues.featureDomain !== null ? (
            <input type="hidden" {...form.register("featureDomain" as never)} />
          ) : null}
          {defaultValues.featureSchemaVersion !== undefined && defaultValues.featureSchemaVersion !== null ? (
            <input type="hidden" {...form.register("featureSchemaVersion" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.declareDataset.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="agent_organization_directory"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={(value, option) => {
                    field.onChange(value);
                    form.setValue(
                      "organizationName" as never,
                      String(option?.record?.["organizationName"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                  }}
                  placeholder={t("resources.dataset_capability.commands.declareDataset.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.dataset_capability.commands.declareDataset.fields.organizationId.label", "Agent Organization Directory"),
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
                <FormLabel>{t("resources.dataset_capability.commands.declareDataset.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="agent_feature_schema_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={(value, option) => {
                    field.onChange(value);
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
                  }}
                  placeholder={t("resources.dataset_capability.commands.declareDataset.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.dataset_capability.commands.declareDataset.fields.featureSchemaId.label", "Agent Feature Schema Catalog"),
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
            name="datasetName"
            rules={{ required: "Dataset Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.declareDataset.fields.datasetName.label", "Dataset Name")}</FormLabel>
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
            name="datasetUsage"
            rules={{ required: "Dataset Usage is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.declareDataset.fields.datasetUsage.label", "Dataset Usage")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Dataset Usage"}
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
