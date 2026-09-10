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
import { ReprofileAgentDatasetCommandSchema, type ReprofileAgentDatasetCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RuntimeDatasetMetadataCatalogReprofileAgentDataset = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeDatasetBindingId: searchParams.get("runtimeDatasetBindingId") ?? undefined,
  } as unknown as Partial<ReprofileAgentDatasetCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ReprofileAgentDatasetCommandInput, ReprofileAgentDatasetCommandInput>({
    resource: "runtime_dataset_metadata_catalog",
    command: "reprofileAgentDataset",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_dataset_metadata_catalog_read_model_entity",
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_metadata_catalog.label", "Runtime Dataset Metadata Catalog"),
      aggregateRoute: "agentdatasetprofile",
      queryRoute: "runtimedatasetmetadatacatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_dataset_metadata_catalog_read_model_entity",
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_metadata_catalog.label", "Runtime Dataset Metadata Catalog"),
      aggregateRoute: "runtimedatasetmetadata",
      queryRoute: "runtimedatasetmetadatacatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ReprofileAgentDatasetCommandSchema) as never,
    },
  });

  async function onSubmit(values: ReprofileAgentDatasetCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-dataset-metadata-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_dataset_metadata_catalog.commands.reprofileAgentDataset.label", "Reprofile Agent Dataset")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ReprofileAgentDataset validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="runtimeDatasetBindingId"
            rules={{ required: "Runtime Dataset Binding Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_dataset_metadata_catalog.commands.reprofileAgentDataset.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_dataset_metadata_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="datasetName"
                  optionValue="runtimeDatasetBindingId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_dataset_metadata_catalog.commands.reprofileAgentDataset.fields.runtimeDatasetBindingId.placeholder", "Select Runtime Dataset Binding Id")}
                  meta={{
                    idField: "runtimeDatasetBindingId",
                    label: t("resources.runtime_dataset_metadata_catalog.commands.reprofileAgentDataset.fields.runtimeDatasetBindingId.label", "Runtime Dataset Metadata Catalog"),
                    aggregateRoute: "runtimedatasetmetadata",
                    queryRoute: "runtimedatasetmetadatacatalog",
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
