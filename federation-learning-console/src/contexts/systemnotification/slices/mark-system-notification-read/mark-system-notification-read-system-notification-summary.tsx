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
import { MarkSystemNotificationReadCommandSchema, type MarkSystemNotificationReadCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const SystemNotificationSummaryMarkSystemNotificationRead = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    recipientUserId: searchParams.get("recipientUserId") ?? undefined,
    notificationId: searchParams.get("notificationId") ?? undefined,
  } as unknown as Partial<MarkSystemNotificationReadCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<MarkSystemNotificationReadCommandInput, MarkSystemNotificationReadCommandInput>({
    resource: "system_notification_summary",
    command: "markSystemNotificationRead",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "system_notification_summary_read_model_entity",
      idField: "recipientUserId",
      label: t("resources.system_notification_summary.label", "System Notification Summary"),
      aggregateRoute: "systemnotification",
      queryRoute: "systemnotificationsummary",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "system_notification_summary_read_model_entity",
      idField: "recipientUserId",
      label: t("resources.system_notification_summary.label", "System Notification Summary"),
      aggregateRoute: "systemnotification",
      queryRoute: "systemnotificationsummary",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(MarkSystemNotificationReadCommandSchema) as never,
    },
  });

  async function onSubmit(values: MarkSystemNotificationReadCommandInput) {
    const result = await runFormBehavior<MarkSystemNotificationReadCommandInput>(
      frontendComposition,
      "behavior:system-notification-summary:markSystemNotificationRead",
      {
      ...defaultValues,
      ...values,
      } as MarkSystemNotificationReadCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/system-notification-summary");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.system_notification_summary.commands.markSystemNotificationRead.label", "Mark System Notification Read")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("MarkSystemNotificationRead validation failed", errors))} className="space-y-8">
          {defaultValues.notificationId !== undefined && defaultValues.notificationId !== null ? (
            <input type="hidden" {...form.register("notificationId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="recipientUserId"
            rules={{ required: "Recipient User Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_summary.commands.markSystemNotificationRead.fields.recipientUserId.label", "Recipient User Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="system_notification_summary"
                  dataProviderName="federation-learning-support"
                  optionLabel="messageKey"
                  optionValue="recipientUserId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.system_notification_summary.commands.markSystemNotificationRead.fields.recipientUserId.placeholder", "Select Recipient User Id")}
                  meta={{
                    idField: "recipientUserId",
                    label: t("resources.system_notification_summary.commands.markSystemNotificationRead.fields.recipientUserId.label", "System Notification Summary"),
                    aggregateRoute: "systemnotification",
                    queryRoute: "systemnotificationsummary",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="readAt"
            rules={{ required: "Read At is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_summary.commands.markSystemNotificationRead.fields.readAt.label", "Read At")}</FormLabel>
                <FormControl>
                  <Input
                    type="datetime-local"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Read At"}
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
