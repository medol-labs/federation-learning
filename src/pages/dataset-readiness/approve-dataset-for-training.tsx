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
import { ApproveDatasetForTrainingCommandSchema, type ApproveDatasetForTrainingCommandInput } from "@/domain/schemas";

export const DatasetReadinessApproveDatasetForTraining = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    datasetId: searchParams.get("datasetId") ?? undefined,
  } as unknown as Partial<ApproveDatasetForTrainingCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ApproveDatasetForTrainingCommandInput, ApproveDatasetForTrainingCommandInput>({
    resource: "dataset_readiness",
    command: "approveDatasetForTraining",
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
      resolver: zodResolver(ApproveDatasetForTrainingCommandSchema) as never,
    },
  });

  async function onSubmit(values: ApproveDatasetForTrainingCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/dataset-readiness");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dataset_readiness.commands.approveDatasetForTraining.label", "Approve Dataset For Training")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ApproveDatasetForTraining validation failed", errors))} className="space-y-8">
          {defaultValues.datasetId !== undefined && defaultValues.datasetId !== null ? (
            <input type="hidden" {...form.register("datasetId" as never)} />
          ) : null}
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
