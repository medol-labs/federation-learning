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
import { MarkAllSystemNotificationsReadCommandSchema, type MarkAllSystemNotificationsReadCommandInput } from "@/contexts/domain/schemas";

export const SystemNotificationSummaryMarkAllSystemNotificationsRead = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    recipientUserId: searchParams.get("recipientUserId") ?? undefined,
  } as unknown as Partial<MarkAllSystemNotificationsReadCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<MarkAllSystemNotificationsReadCommandInput, MarkAllSystemNotificationsReadCommandInput>({
    resource: "system_notification_summary",
    command: "markAllSystemNotificationsRead",
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
      resolver: zodResolver(MarkAllSystemNotificationsReadCommandSchema) as never,
    },
  });

  async function onSubmit(values: MarkAllSystemNotificationsReadCommandInput) {
    const result = await runFormBehavior<MarkAllSystemNotificationsReadCommandInput>(
      frontendComposition,
      "behavior:system-notification-summary:markAllSystemNotificationsRead",
      {
      ...defaultValues,
      ...values,
      } as MarkAllSystemNotificationsReadCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/system-notification-summary");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.system_notification_summary.commands.markAllSystemNotificationsRead.label", "Mark All System Notifications Read")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("MarkAllSystemNotificationsRead validation failed", errors))} className="space-y-8">
          {defaultValues.recipientUserId !== undefined && defaultValues.recipientUserId !== null ? (
            <input type="hidden" {...form.register("recipientUserId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="readAt"
            rules={{ required: "Read At is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_summary.commands.markAllSystemNotificationsRead.fields.readAt.label", "Read At")}</FormLabel>
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
