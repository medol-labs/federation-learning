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
import { RevokeDatasetTrainingApprovalCommandSchema, type RevokeDatasetTrainingApprovalCommandInput } from "@/domain/schemas";

export const DatasetCapabilityRevokeDatasetTrainingApproval = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    datasetId: searchParams.get("datasetId") ?? undefined,
  } as Partial<RevokeDatasetTrainingApprovalCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RevokeDatasetTrainingApprovalCommandInput, RevokeDatasetTrainingApprovalCommandInput>({
    resource: "dataset_capability",
    command: "revokeDatasetTrainingApproval",
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
      resolver: zodResolver(RevokeDatasetTrainingApprovalCommandSchema) as never,
    },
  });

  async function onSubmit(values: RevokeDatasetTrainingApprovalCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/dataset-capability");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dataset_capability.commands.revokeDatasetTrainingApproval.label", "Revoke Dataset Training Approval")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RevokeDatasetTrainingApproval validation failed", errors))} className="space-y-8">
          {defaultValues.datasetId !== undefined && defaultValues.datasetId !== null ? (
            <input type="hidden" {...form.register("datasetId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="revokeReason"
            rules={{ required: "Revoke Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_capability.commands.revokeDatasetTrainingApproval.fields.revokeReason.label", "Revoke Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Revoke Reason"}
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
