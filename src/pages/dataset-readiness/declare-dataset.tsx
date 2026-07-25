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
import { DeclareDatasetCommandSchema, type DeclareDatasetCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const DatasetReadinessDeclareDataset = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationId: searchParams.get("organizationId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    datasetName: searchParams.get("datasetName") ?? undefined,
    datasetUsage: searchParams.get("datasetUsage") ?? undefined,
  } as Partial<DeclareDatasetCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<DeclareDatasetCommandInput, DeclareDatasetCommandInput>({
    resource: "dataset_readiness",
    command: "declareDataset",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "dataset_readiness_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_readiness.label", "Dataset Readiness"),
      aggregateRoute: "dataset",
      queryRoute: "datasetreadiness",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "dataset_readiness_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_readiness.label", "Dataset Readiness"),
      aggregateRoute: "dataset",
      queryRoute: "datasetreadiness",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(DeclareDatasetCommandSchema) as never,
    },
  });

  function onSubmit(values: DeclareDatasetCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dataset_readiness.commands.declareDataset.label", "Declare Dataset")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_readiness.commands.declareDataset.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.dataset_readiness.commands.declareDataset.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.dataset_readiness.commands.declareDataset.fields.organizationId.label", "Organization Directory"),
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
                <FormLabel>{t("resources.dataset_readiness.commands.declareDataset.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="feature_schema_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.dataset_readiness.commands.declareDataset.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.dataset_readiness.commands.declareDataset.fields.featureSchemaId.label", "Feature Schema Catalog"),
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
            name="datasetName"
            rules={{ required: "Dataset Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_readiness.commands.declareDataset.fields.datasetName.label", "Dataset Name")}</FormLabel>
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
            name="datasetType"
            rules={{ required: "Dataset Type is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_readiness.commands.declareDataset.fields.datasetType.label", "Dataset Type")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Dataset Type"}
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
                <FormLabel>{t("resources.dataset_readiness.commands.declareDataset.fields.datasetUsage.label", "Dataset Usage")}</FormLabel>
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
