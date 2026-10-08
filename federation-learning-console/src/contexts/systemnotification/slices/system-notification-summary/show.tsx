// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

const formatValue = (
  value: unknown,
  t: ReturnType<typeof useTranslate>,
  dictionaryLabel: ReturnType<typeof useDictionaryTranslation>["dictionaryLabel"],
  options?: Array<{ label: string; value: string }>,
  dictionaryCode?: string,
): string => {
  if (value === null || value === undefined || value === "") return "-";
  if (Array.isArray(value)) {
    const formatted: string[] = value.map((item) => formatValue(item, t, dictionaryLabel, options, dictionaryCode)).filter((item) => item !== "-");
    return formatted.length > 0 ? formatted.join(", ") : "-";
  }
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  const stringValue = String(value);
  if (dictionaryCode) return dictionaryLabel(dictionaryCode, stringValue, t(`dictionaries.${dictionaryCode}.${stringValue}`, stringValue));
  return options?.find((option) => option.value === stringValue)?.label ?? stringValue;
};

export const SystemNotificationSummaryShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "system_notification_summary_read_model_entity",
      idField: "recipientUserId",
      label: t("resources.system_notification_summary.label", "System Notification Summary"),
      aggregateRoute: "systemnotification",
      queryRoute: "systemnotificationsummary",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.recipientUserId ?? t("resources.system_notification_summary.label", "System Notification Summary")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.recipientUserId.label", "Recipient User Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:recipientUserId", { value: record?.recipientUserId, record, resource: "system-notification-summary", field: "recipientUserId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recipientUserId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.unreadCount.label", "Unread Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:unreadCount", { value: record?.unreadCount, record, resource: "system-notification-summary", field: "unreadCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.unreadCount, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.latestNotificationId.label", "Latest Notification Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:latestNotificationId", { value: record?.latestNotificationId, record, resource: "system-notification-summary", field: "latestNotificationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.latestNotificationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.latestSeverity.label", "Latest Severity")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:latestSeverity", { value: record?.latestSeverity, record, resource: "system-notification-summary", field: "latestSeverity", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.latestSeverity, t, dictionaryLabel, [
                { label: t("resources.system_notification_summary.fields.latestSeverity.options.INFO", "Info"), value: "INFO" },
                { label: t("resources.system_notification_summary.fields.latestSeverity.options.SUCCESS", "Success"), value: "SUCCESS" },
                { label: t("resources.system_notification_summary.fields.latestSeverity.options.WARNING", "Warning"), value: "WARNING" },
                { label: t("resources.system_notification_summary.fields.latestSeverity.options.ERROR", "Error"), value: "ERROR" },
              ])}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.latestPublishedAt.label", "Latest Published At")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:latestPublishedAt", { value: record?.latestPublishedAt, record, resource: "system-notification-summary", field: "latestPublishedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.latestPublishedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.messageKey.label", "Message Key")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:messageKey", { value: record?.messageKey, record, resource: "system-notification-summary", field: "messageKey", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.messageKey, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.fallbackTitle.label", "Fallback Title")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:fallbackTitle", { value: record?.fallbackTitle, record, resource: "system-notification-summary", field: "fallbackTitle", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.fallbackTitle, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.fallbackBody.label", "Fallback Body")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:fallbackBody", { value: record?.fallbackBody, record, resource: "system-notification-summary", field: "fallbackBody", view: "display" }) ?? <CopyableText value={record?.fallbackBody} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.toastPolicy.label", "Toast Policy")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:toastPolicy", { value: record?.toastPolicy, record, resource: "system-notification-summary", field: "toastPolicy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.toastPolicy, t, dictionaryLabel, [
                { label: t("resources.system_notification_summary.fields.toastPolicy.options.NONE", "None"), value: "NONE" },
                { label: t("resources.system_notification_summary.fields.toastPolicy.options.DEFAULT", "Default"), value: "DEFAULT" },
                { label: t("resources.system_notification_summary.fields.toastPolicy.options.IMPORTANT", "Important"), value: "IMPORTANT" },
              ])}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_summary.fields.hasUnreadError.label", "Has Unread Error")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-summary:display:hasUnreadError", { value: record?.hasUnreadError, record, resource: "system-notification-summary", field: "hasUnreadError", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.hasUnreadError, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
