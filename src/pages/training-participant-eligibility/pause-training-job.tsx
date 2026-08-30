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
import { PauseTrainingJobCommandSchema, type PauseTrainingJobCommandInput } from "@/domain/schemas";

export const TrainingParticipantEligibilityPauseTrainingJob = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    trainingJobId: searchParams.get("trainingJobId") ?? undefined,
  } as unknown as Partial<PauseTrainingJobCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<PauseTrainingJobCommandInput, PauseTrainingJobCommandInput>({
    resource: "training_participant_eligibility",
    command: "pauseTrainingJob",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_participant_eligibility_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_participant_eligibility.label", "Training Participant Eligibility"),
      aggregateRoute: "trainingjob",
      queryRoute: "trainingparticipanteligibility",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "training_participant_eligibility_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_participant_eligibility.label", "Training Participant Eligibility"),
      aggregateRoute: "trainingjob",
      queryRoute: "trainingparticipanteligibility",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(PauseTrainingJobCommandSchema) as never,
    },
  });

  async function onSubmit(values: PauseTrainingJobCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/training-participant-eligibility");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.training_participant_eligibility.commands.pauseTrainingJob.label", "Pause Training Job")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("PauseTrainingJob validation failed", errors))} className="space-y-8">
          {defaultValues.trainingJobId !== undefined && defaultValues.trainingJobId !== null ? (
            <input type="hidden" {...form.register("trainingJobId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="pauseReason"
            rules={{ required: "Pause Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.pauseTrainingJob.fields.pauseReason.label", "Pause Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Pause Reason"}
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
