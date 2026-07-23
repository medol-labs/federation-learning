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
import { RemoveParticipantCommandSchema, type RemoveParticipantCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const TrainingParticipantEligibilityRemoveParticipant = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationId: searchParams.get("organizationId") ?? undefined,
    federationId: searchParams.get("federationId") ?? undefined,
  } as Partial<RemoveParticipantCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RemoveParticipantCommandInput, RemoveParticipantCommandInput>({
    resource: "training_participant_eligibility",
    command: "removeParticipant",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_participant_eligibility_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_participant_eligibility.label", "Training Participant Eligibility"),
      aggregateRoute: "federationmembership",
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
      resolver: zodResolver(RemoveParticipantCommandSchema) as never,
    },
  });

  function onSubmit(values: RemoveParticipantCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.training_participant_eligibility.commands.removeParticipant.label", "Remove Participant")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <input type="hidden" {...form.register("federationId" as never)} />
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.removeParticipant.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_participant_eligibility.commands.removeParticipant.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.training_participant_eligibility.commands.removeParticipant.fields.organizationId.label", "Organization Directory"),
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
            name="removalReason"
            rules={{ required: "Removal Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_participant_eligibility.commands.removeParticipant.fields.removalReason.label", "Removal Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Removal Reason"}
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
