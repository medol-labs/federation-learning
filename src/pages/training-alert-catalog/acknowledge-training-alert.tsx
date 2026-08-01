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
import { AcknowledgeTrainingAlertCommandSchema, type AcknowledgeTrainingAlertCommandInput } from "@/domain/schemas";


export const TrainingAlertCatalogAcknowledgeTrainingAlert = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    alertId: searchParams.get("alertId") ?? undefined,
  } as Partial<AcknowledgeTrainingAlertCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<AcknowledgeTrainingAlertCommandInput, AcknowledgeTrainingAlertCommandInput>({
    resource: "training_alert_catalog",
    command: "acknowledgeTrainingAlert",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_alert_catalog_read_model_entity",
      idField: "alertId",
      label: t("resources.training_alert_catalog.label", "Training Alert Catalog"),
      aggregateRoute: "trainingalert",
      queryRoute: "trainingalertcatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "training_alert_catalog_read_model_entity",
      idField: "alertId",
      label: t("resources.training_alert_catalog.label", "Training Alert Catalog"),
      aggregateRoute: "trainingalert",
      queryRoute: "trainingalertcatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(AcknowledgeTrainingAlertCommandSchema) as never,
    },
  });

  function onSubmit(values: AcknowledgeTrainingAlertCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.training_alert_catalog.commands.acknowledgeTrainingAlert.label", "Acknowledge Training Alert")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("AcknowledgeTrainingAlert validation failed", errors))} className="space-y-8">
          {defaultValues.alertId !== undefined && defaultValues.alertId !== null ? (
            <input type="hidden" {...form.register("alertId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="acknowledgementNote"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_alert_catalog.commands.acknowledgeTrainingAlert.fields.acknowledgementNote.label", "Acknowledgement Note")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Acknowledgement Note"}
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
