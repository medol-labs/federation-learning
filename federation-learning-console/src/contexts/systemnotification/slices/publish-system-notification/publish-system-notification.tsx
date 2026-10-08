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
import { PublishSystemNotificationCommandSchema, type PublishSystemNotificationCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const SystemNotificationCatalogPublishSystemNotification = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    recipientUserId: searchParams.get("recipientUserId") ?? undefined,
    recipientRole: searchParams.get("recipientRole") ?? undefined,
    category: searchParams.get("category") ?? undefined,
    severity: searchParams.get("severity") ?? undefined,
    messageKey: searchParams.get("messageKey") ?? undefined,
    messageArgsJson: searchParams.get("messageArgsJson") ?? undefined,
    fallbackTitle: searchParams.get("fallbackTitle") ?? undefined,
    fallbackBody: searchParams.get("fallbackBody") ?? undefined,
    targetType: searchParams.get("targetType") ?? undefined,
    targetId: searchParams.get("targetId") ?? undefined,
    targetRoute: searchParams.get("targetRoute") ?? undefined,
    toastPolicy: searchParams.get("toastPolicy") ?? undefined,
    publishedAt: searchParams.get("publishedAt") ?? undefined,
  } as unknown as Partial<PublishSystemNotificationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<PublishSystemNotificationCommandInput, PublishSystemNotificationCommandInput>({
    resource: "system_notification_catalog",
    command: "publishSystemNotification",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "system_notification_catalog_read_model_entity",
      idField: "notificationId",
      label: t("resources.system_notification_catalog.label", "System Notification Catalog"),
      aggregateRoute: "systemnotification",
      queryRoute: "systemnotificationcatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "system_notification_catalog_read_model_entity",
      idField: "notificationId",
      label: t("resources.system_notification_catalog.label", "System Notification Catalog"),
      aggregateRoute: "systemnotification",
      queryRoute: "systemnotificationcatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(PublishSystemNotificationCommandSchema) as never,
    },
  });

  async function onSubmit(values: PublishSystemNotificationCommandInput) {
    const result = await runFormBehavior<PublishSystemNotificationCommandInput>(
      frontendComposition,
      "behavior:system-notification-catalog:publishSystemNotification",
      {
      ...defaultValues,
      ...values,
      } as PublishSystemNotificationCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/system-notification-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.system_notification_catalog.commands.publishSystemNotification.label", "Publish System Notification")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("PublishSystemNotification validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="recipientUserId"
            rules={{ required: "Recipient User Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.recipientUserId.label", "Recipient User Id")}</FormLabel>
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
                  placeholder={t("resources.system_notification_catalog.commands.publishSystemNotification.fields.recipientUserId.placeholder", "Select Recipient User Id")}
                  meta={{
                    idField: "recipientUserId",
                    label: t("resources.system_notification_catalog.commands.publishSystemNotification.fields.recipientUserId.label", "System Notification Summary"),
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
            name="recipientRole"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.recipientRole.label", "Recipient Role")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Recipient Role"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="category"
            rules={{ required: "Category is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.category.label", "Category")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={field.onChange}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.system_notification_catalog.commands.publishSystemNotification.fields.category.placeholder", "Select Category")} />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="SYSTEM">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.category.options.SYSTEM", "System")}</SelectItem>
                    <SelectItem value="DATA_EXCHANGE">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.category.options.DATA_EXCHANGE", "Data Exchange")}</SelectItem>
                    <SelectItem value="TRAINING">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.category.options.TRAINING", "Training")}</SelectItem>
                    <SelectItem value="RUNTIME">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.category.options.RUNTIME", "Runtime")}</SelectItem>
                    <SelectItem value="SECURITY">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.category.options.SECURITY", "Security")}</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="severity"
            rules={{ required: "Severity is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.severity.label", "Severity")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={field.onChange}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.system_notification_catalog.commands.publishSystemNotification.fields.severity.placeholder", "Select Severity")} />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="INFO">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.severity.options.INFO", "Info")}</SelectItem>
                    <SelectItem value="SUCCESS">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.severity.options.SUCCESS", "Success")}</SelectItem>
                    <SelectItem value="WARNING">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.severity.options.WARNING", "Warning")}</SelectItem>
                    <SelectItem value="ERROR">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.severity.options.ERROR", "Error")}</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="messageKey"
            rules={{ required: "Message Key is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.messageKey.label", "Message Key")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Message Key"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="messageArgsJson"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.messageArgsJson.label", "Message Args Json")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Message Args Json"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="fallbackTitle"
            rules={{ required: "Fallback Title is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.fallbackTitle.label", "Fallback Title")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Fallback Title"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="fallbackBody"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.fallbackBody.label", "Fallback Body")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Fallback Body"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="targetType"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.targetType.label", "Target Type")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Target Type"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="targetId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.targetId.label", "Target Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Target Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="targetRoute"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.targetRoute.label", "Target Route")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Target Route"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="toastPolicy"
            rules={{ required: "Toast Policy is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.toastPolicy.label", "Toast Policy")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={field.onChange}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.system_notification_catalog.commands.publishSystemNotification.fields.toastPolicy.placeholder", "Select Toast Policy")} />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="NONE">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.toastPolicy.options.NONE", "None")}</SelectItem>
                    <SelectItem value="DEFAULT">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.toastPolicy.options.DEFAULT", "Default")}</SelectItem>
                    <SelectItem value="IMPORTANT">{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.toastPolicy.options.IMPORTANT", "Important")}</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="publishedAt"
            rules={{ required: "Published At is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.publishedAt.label", "Published At")}</FormLabel>
                <FormControl>
                  <Input
                    type="datetime-local"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Published At"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="status"
            rules={{ required: "Status is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.system_notification_catalog.commands.publishSystemNotification.fields.status.label", "Status")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Status"}
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
