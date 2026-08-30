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
import { RecordModelEvaluationPackageCommandSchema, type RecordModelEvaluationPackageCommandInput } from "@/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const ModelCatalogRecordModelEvaluationPackage = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    trainingJobId: searchParams.get("trainingJobId") ?? undefined,
    evaluationReportId: searchParams.get("evaluationReportId") ?? undefined,
    experimentId: searchParams.get("experimentId") ?? undefined,
    hyperparameterSnapshotId: searchParams.get("hyperparameterSnapshotId") ?? undefined,
    reproducibilityManifestId: searchParams.get("reproducibilityManifestId") ?? undefined,
    modelCardId: searchParams.get("modelCardId") ?? undefined,
    baselineModelId: searchParams.get("baselineModelId") ?? undefined,
    modelId: searchParams.get("modelId") ?? undefined,
  } as unknown as Partial<RecordModelEvaluationPackageCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RecordModelEvaluationPackageCommandInput, RecordModelEvaluationPackageCommandInput>({
    resource: "model_catalog",
    command: "recordModelEvaluationPackage",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "model_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_catalog.label", "Model Catalog"),
      aggregateRoute: "model",
      queryRoute: "modelcatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "model_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_catalog.label", "Model Catalog"),
      aggregateRoute: "model",
      queryRoute: "modelcatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RecordModelEvaluationPackageCommandSchema) as never,
    },
  });

  async function onSubmit(values: RecordModelEvaluationPackageCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/model-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.model_catalog.commands.recordModelEvaluationPackage.label", "Record Model Evaluation Package")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RecordModelEvaluationPackage validation failed", errors))} className="space-y-8">
          {defaultValues.modelId !== undefined && defaultValues.modelId !== null ? (
            <input type="hidden" {...form.register("modelId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="trainingJobId"
            rules={{ required: "Training Job Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.recordModelEvaluationPackage.fields.trainingJobId.label", "Training Job Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_participant_eligibility"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingJobId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.model_catalog.commands.recordModelEvaluationPackage.fields.trainingJobId.placeholder", "Select Training Job Id")}
                  meta={{
                    idField: "trainingJobId",
                    label: t("resources.model_catalog.commands.recordModelEvaluationPackage.fields.trainingJobId.label", "Training Participant Eligibility"),
                    aggregateRoute: "trainingjob",
                    queryRoute: "trainingparticipanteligibility",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="evaluationReportId"
            rules={{ required: "Evaluation Report Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.recordModelEvaluationPackage.fields.evaluationReportId.label", "Evaluation Report Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Evaluation Report Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="experimentId"
            rules={{ required: "Experiment Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.recordModelEvaluationPackage.fields.experimentId.label", "Experiment Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Experiment Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="hyperparameterSnapshotId"
            rules={{ required: "Hyperparameter Snapshot Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.recordModelEvaluationPackage.fields.hyperparameterSnapshotId.label", "Hyperparameter Snapshot Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Hyperparameter Snapshot Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="reproducibilityManifestId"
            rules={{ required: "Reproducibility Manifest Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.recordModelEvaluationPackage.fields.reproducibilityManifestId.label", "Reproducibility Manifest Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Reproducibility Manifest Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelCardId"
            rules={{ required: "Model Card Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.recordModelEvaluationPackage.fields.modelCardId.label", "Model Card Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Card Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="baselineModelId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.recordModelEvaluationPackage.fields.baselineModelId.label", "Baseline Model Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Baseline Model Id"}
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
