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
import { DeclareDatasetCommandSchema, type DeclareDatasetCommandInput } from "@/contexts/domain/schemas";

export const DatasetCapabilityDeclareDataset = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationId: searchParams.get("organizationId") ?? undefined,
    organizationName: searchParams.get("organizationName") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    featureDomain: searchParams.get("featureDomain") ?? undefined,
    featureSchemaVersion: searchParams.get("featureSchemaVersion") ?? undefined,
    datasetName: searchParams.get("datasetName") ?? undefined,
    datasetUsage: searchParams.get("datasetUsage") ?? undefined,
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
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/dataset-capability");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dataset_capability.commands.declareDataset.label", "Declare Dataset")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("DeclareDataset validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.declareDataset.fields.organizationId.label", "Organization Id")}</FormLabel>
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
            name="organizationName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.declareDataset.fields.organizationName.label", "Organization Name")}</FormLabel>
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
            name="featureSchemaId"
            rules={{ required: "Feature Schema Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.declareDataset.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
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
            name="featureDomain"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.declareDataset.fields.featureDomain.label", "Feature Domain")}</FormLabel>
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
                <FormLabel>{t("resources.dataset_capability.commands.declareDataset.fields.featureSchemaVersion.label", "Feature Schema Version")}</FormLabel>
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
