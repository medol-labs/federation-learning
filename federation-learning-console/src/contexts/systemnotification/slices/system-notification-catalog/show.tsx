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

export const SystemNotificationCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "system_notification_catalog_read_model_entity",
      idField: "notificationId",
      label: t("resources.system_notification_catalog.label", "System Notification Catalog"),
      aggregateRoute: "systemnotification",
      queryRoute: "systemnotificationcatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.notificationId ?? t("resources.system_notification_catalog.label", "System Notification Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.notificationId.label", "Notification Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:notificationId", { value: record?.notificationId, record, resource: "system-notification-catalog", field: "notificationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.notificationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.recipientUserId.label", "Recipient User Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:recipientUserId", { value: record?.recipientUserId, record, resource: "system-notification-catalog", field: "recipientUserId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recipientUserId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.recipientRole.label", "Recipient Role")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:recipientRole", { value: record?.recipientRole, record, resource: "system-notification-catalog", field: "recipientRole", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recipientRole, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.category.label", "Category")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:category", { value: record?.category, record, resource: "system-notification-catalog", field: "category", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.category, t, dictionaryLabel, [
                { label: t("resources.system_notification_catalog.fields.category.options.SYSTEM", "System"), value: "SYSTEM" },
                { label: t("resources.system_notification_catalog.fields.category.options.DATA_EXCHANGE", "Data Exchange"), value: "DATA_EXCHANGE" },
                { label: t("resources.system_notification_catalog.fields.category.options.TRAINING", "Training"), value: "TRAINING" },
                { label: t("resources.system_notification_catalog.fields.category.options.RUNTIME", "Runtime"), value: "RUNTIME" },
                { label: t("resources.system_notification_catalog.fields.category.options.SECURITY", "Security"), value: "SECURITY" },
              ])}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.severity.label", "Severity")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:severity", { value: record?.severity, record, resource: "system-notification-catalog", field: "severity", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.severity, t, dictionaryLabel, [
                { label: t("resources.system_notification_catalog.fields.severity.options.INFO", "Info"), value: "INFO" },
                { label: t("resources.system_notification_catalog.fields.severity.options.SUCCESS", "Success"), value: "SUCCESS" },
                { label: t("resources.system_notification_catalog.fields.severity.options.WARNING", "Warning"), value: "WARNING" },
                { label: t("resources.system_notification_catalog.fields.severity.options.ERROR", "Error"), value: "ERROR" },
              ])}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.messageKey.label", "Message Key")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:messageKey", { value: record?.messageKey, record, resource: "system-notification-catalog", field: "messageKey", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.messageKey, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.messageArgsJson.label", "Message Args Json")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:messageArgsJson", { value: record?.messageArgsJson, record, resource: "system-notification-catalog", field: "messageArgsJson", view: "display" }) ?? <CopyableText value={record?.messageArgsJson} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.fallbackTitle.label", "Fallback Title")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:fallbackTitle", { value: record?.fallbackTitle, record, resource: "system-notification-catalog", field: "fallbackTitle", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.fallbackTitle, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.fallbackBody.label", "Fallback Body")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:fallbackBody", { value: record?.fallbackBody, record, resource: "system-notification-catalog", field: "fallbackBody", view: "display" }) ?? <CopyableText value={record?.fallbackBody} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.targetType.label", "Target Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:targetType", { value: record?.targetType, record, resource: "system-notification-catalog", field: "targetType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.targetType, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.targetId.label", "Target Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:targetId", { value: record?.targetId, record, resource: "system-notification-catalog", field: "targetId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.targetId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.targetRoute.label", "Target Route")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:targetRoute", { value: record?.targetRoute, record, resource: "system-notification-catalog", field: "targetRoute", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.targetRoute, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.toastPolicy.label", "Toast Policy")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:toastPolicy", { value: record?.toastPolicy, record, resource: "system-notification-catalog", field: "toastPolicy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.toastPolicy, t, dictionaryLabel, [
                { label: t("resources.system_notification_catalog.fields.toastPolicy.options.NONE", "None"), value: "NONE" },
                { label: t("resources.system_notification_catalog.fields.toastPolicy.options.DEFAULT", "Default"), value: "DEFAULT" },
                { label: t("resources.system_notification_catalog.fields.toastPolicy.options.IMPORTANT", "Important"), value: "IMPORTANT" },
              ])}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.publishedAt.label", "Published At")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:publishedAt", { value: record?.publishedAt, record, resource: "system-notification-catalog", field: "publishedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.publishedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.readAt.label", "Read At")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:readAt", { value: record?.readAt, record, resource: "system-notification-catalog", field: "readAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.readAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.system_notification_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:system-notification-catalog:display:state", { value: record?.state, record, resource: "system-notification-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t, dictionaryLabel, [
                { label: t("resources.system_notification_catalog.fields.state.options.Unread", "Unread"), value: "Unread" },
                { label: t("resources.system_notification_catalog.fields.state.options.Read", "Read"), value: "Read" },
              ])}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
