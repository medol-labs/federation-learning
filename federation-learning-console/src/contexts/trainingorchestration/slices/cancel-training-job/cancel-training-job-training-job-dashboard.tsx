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
import { CancelTrainingJobCommandSchema, type CancelTrainingJobCommandInput } from "@/contexts/domain/schemas";

export const TrainingJobDashboardCancelTrainingJob = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    trainingJobId: searchParams.get("trainingJobId") ?? undefined,
  } as unknown as Partial<CancelTrainingJobCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<CancelTrainingJobCommandInput, CancelTrainingJobCommandInput>({
    resource: "training_job_dashboard",
    command: "cancelTrainingJob",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_job_dashboard_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_job_dashboard.label", "Training Job Dashboard"),
      aggregateRoute: "trainingjob",
      queryRoute: "trainingjobdashboard",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "training_job_dashboard_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_job_dashboard.label", "Training Job Dashboard"),
      aggregateRoute: "trainingjob",
      queryRoute: "trainingjobdashboard",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(CancelTrainingJobCommandSchema) as never,
    },
  });

  async function onSubmit(values: CancelTrainingJobCommandInput) {
    const result = await runFormBehavior<CancelTrainingJobCommandInput>(
      frontendComposition,
      "behavior:training-job-dashboard:cancelTrainingJob",
      {
      ...defaultValues,
      ...values,
      } as CancelTrainingJobCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/training-job-dashboard");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.training_job_dashboard.commands.cancelTrainingJob.label", "Cancel Training Job")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("CancelTrainingJob validation failed", errors))} className="space-y-8">
          {defaultValues.trainingJobId !== undefined && defaultValues.trainingJobId !== null ? (
            <input type="hidden" {...form.register("trainingJobId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="cancelReason"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_job_dashboard.commands.cancelTrainingJob.fields.cancelReason.label", "Cancel Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Cancel Reason"}
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
