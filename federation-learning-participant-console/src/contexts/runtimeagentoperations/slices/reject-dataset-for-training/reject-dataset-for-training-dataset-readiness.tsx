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
import { RejectDatasetForTrainingCommandSchema, type RejectDatasetForTrainingCommandInput } from "@/contexts/domain/schemas";

export const DatasetReadinessRejectDatasetForTraining = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationId: searchParams.get("organizationId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    datasetName: searchParams.get("datasetName") ?? undefined,
    datasetId: searchParams.get("datasetId") ?? undefined,
  } as unknown as Partial<RejectDatasetForTrainingCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RejectDatasetForTrainingCommandInput, RejectDatasetForTrainingCommandInput>({
    resource: "dataset_readiness",
    command: "rejectDatasetForTraining",
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
      resolver: zodResolver(RejectDatasetForTrainingCommandSchema) as never,
    },
  });

  async function onSubmit(values: RejectDatasetForTrainingCommandInput) {
    const result = await runFormBehavior<RejectDatasetForTrainingCommandInput>(
      frontendComposition,
      "behavior:dataset-readiness:rejectDatasetForTraining",
      {
      ...defaultValues,
      ...values,
      } as RejectDatasetForTrainingCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/dataset-readiness");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dataset_readiness.commands.rejectDatasetForTraining.label", "Reject Dataset For Training")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RejectDatasetForTraining validation failed", errors))} className="space-y-8">
          {defaultValues.organizationId !== undefined && defaultValues.organizationId !== null ? (
            <input type="hidden" {...form.register("organizationId" as never)} />
          ) : null}
          {defaultValues.featureSchemaId !== undefined && defaultValues.featureSchemaId !== null ? (
            <input type="hidden" {...form.register("featureSchemaId" as never)} />
          ) : null}
          {defaultValues.datasetName !== undefined && defaultValues.datasetName !== null ? (
            <input type="hidden" {...form.register("datasetName" as never)} />
          ) : null}
          {defaultValues.datasetId !== undefined && defaultValues.datasetId !== null ? (
            <input type="hidden" {...form.register("datasetId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="rejectionReason"
            rules={{ required: "Rejection Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_readiness.commands.rejectDatasetForTraining.fields.rejectionReason.label", "Rejection Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Rejection Reason"}
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
