// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { DataTableColumnHeader } from "@/components/data-table/data-table-column-header";
import { CommandButton } from "@/components/refine-ui/buttons/command";
import { ShowButton } from "@/components/refine-ui/buttons/show";
import { RefineDataTable } from "@/components/refine-ui/data-table/refine-data-table";
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
import type { NotificationSeverity, NotificationToastPolicy } from "@/contexts/domain/value-types";

type SystemNotificationSummaryRecord = {
  recipientUserId: string;
  unreadCount: number;
  latestNotificationId?: string;
  latestSeverity?: NotificationSeverity;
  latestPublishedAt?: string;
  messageKey?: string;
  fallbackTitle?: string;
  fallbackBody?: string;
  toastPolicy?: NotificationToastPolicy;
  hasUnreadError: boolean;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: SystemNotificationSummaryRecord,
  enabledField?: string,
  stateField?: string,
  allowedStates: string[] = [],
) => {
  const row = record as Record<string, unknown>;
  if (enabledField && row[enabledField] === false) return false;
  if (allowedStates.length === 0) return true;
  if (!stateField) return false;
  const currentState = normalizeWorkflowState(row[stateField]);
  return allowedStates.map(normalizeWorkflowState).includes(currentState);
};

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

export const SystemNotificationSummaryList = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<SystemNotificationSummaryRecord>();
    return [
      columnHelper.display({
        id: "select",
        header: ({ table }) => (
          <Checkbox
            checked={table.getIsAllPageRowsSelected() || (table.getIsSomePageRowsSelected() && "indeterminate")}
            onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
            aria-label={t("table.selectAll", "Select all")}
          />
        ),
        cell: ({ row }) => (
          <Checkbox
            checked={row.getIsSelected()}
            onCheckedChange={(value) => row.toggleSelected(!!value)}
            aria-label={t("table.selectRow", "Select row")}
          />
        ),
        size: 32,
        enableSorting: false,
        enableHiding: false,
      }),
      columnHelper.accessor("recipientUserId", {
        id: "recipientUserId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.recipientUserId.label", "Recipient User Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.recipientUserId.label", "Recipient User Id"),
          placeholder: "Enter Recipient User Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:recipientUserId",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "recipientUserId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("unreadCount", {
        id: "unreadCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.unreadCount.label", "Unread Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.unreadCount.label", "Unread Count"),
          placeholder: "Enter Unread Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:unreadCount",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "unreadCount",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("latestNotificationId", {
        id: "latestNotificationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.latestNotificationId.label", "Latest Notification Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.latestNotificationId.label", "Latest Notification Id"),
          placeholder: "Enter Latest Notification Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:latestNotificationId",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "latestNotificationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("latestSeverity", {
        id: "latestSeverity",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.latestSeverity.label", "Latest Severity")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.latestSeverity.label", "Latest Severity"),
          placeholder: "Select Latest Severity",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.system_notification_summary.fields.latestSeverity.options.INFO", "Info"), value: "INFO" },
            { label: t("resources.system_notification_summary.fields.latestSeverity.options.SUCCESS", "Success"), value: "SUCCESS" },
            { label: t("resources.system_notification_summary.fields.latestSeverity.options.WARNING", "Warning"), value: "WARNING" },
            { label: t("resources.system_notification_summary.fields.latestSeverity.options.ERROR", "Error"), value: "ERROR" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:latestSeverity",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "latestSeverity",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.system_notification_summary.fields.latestSeverity.options.INFO", "Info"), value: "INFO" },
            { label: t("resources.system_notification_summary.fields.latestSeverity.options.SUCCESS", "Success"), value: "SUCCESS" },
            { label: t("resources.system_notification_summary.fields.latestSeverity.options.WARNING", "Warning"), value: "WARNING" },
            { label: t("resources.system_notification_summary.fields.latestSeverity.options.ERROR", "Error"), value: "ERROR" },
          ]),
      }),
      columnHelper.accessor("latestPublishedAt", {
        id: "latestPublishedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.latestPublishedAt.label", "Latest Published At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.latestPublishedAt.label", "Latest Published At"),
          placeholder: "Enter Latest Published At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:latestPublishedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "latestPublishedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("messageKey", {
        id: "messageKey",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.messageKey.label", "Message Key")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.messageKey.label", "Message Key"),
          placeholder: "Enter Message Key",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:messageKey",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "messageKey",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("fallbackTitle", {
        id: "fallbackTitle",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.fallbackTitle.label", "Fallback Title")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.fallbackTitle.label", "Fallback Title"),
          placeholder: "Enter Fallback Title",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:fallbackTitle",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "fallbackTitle",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("fallbackBody", {
        id: "fallbackBody",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.fallbackBody.label", "Fallback Body")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.fallbackBody.label", "Fallback Body"),
          placeholder: "Enter Fallback Body",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:fallbackBody",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "fallbackBody",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("toastPolicy", {
        id: "toastPolicy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.toastPolicy.label", "Toast Policy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.toastPolicy.label", "Toast Policy"),
          placeholder: "Select Toast Policy",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.system_notification_summary.fields.toastPolicy.options.NONE", "None"), value: "NONE" },
            { label: t("resources.system_notification_summary.fields.toastPolicy.options.DEFAULT", "Default"), value: "DEFAULT" },
            { label: t("resources.system_notification_summary.fields.toastPolicy.options.IMPORTANT", "Important"), value: "IMPORTANT" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:toastPolicy",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "toastPolicy",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.system_notification_summary.fields.toastPolicy.options.NONE", "None"), value: "NONE" },
            { label: t("resources.system_notification_summary.fields.toastPolicy.options.DEFAULT", "Default"), value: "DEFAULT" },
            { label: t("resources.system_notification_summary.fields.toastPolicy.options.IMPORTANT", "Important"), value: "IMPORTANT" },
          ]),
      }),
      columnHelper.accessor("hasUnreadError", {
        id: "hasUnreadError",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_summary.fields.hasUnreadError.label", "Has Unread Error")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_summary.fields.hasUnreadError.label", "Has Unread Error"),
          placeholder: "Enter Has Unread Error",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationSummaryRecord>(
            frontendComposition,
            "field:system-notification-summary:display:hasUnreadError",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-summary",
              field: "hasUnreadError",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<SystemNotificationSummaryRecord>(
                frontendComposition,
                "row-actions:system-notification-summary:list",
                "rowActions.before",
                { resource: "system-notification-summary", record: row.original },
              )}
                {isCommandVisible(row.original, "", "", ["Unread"]) && (
                  <CommandButton
                    variant="ghost"
                    command="markSystemNotificationRead"
                    recordItemId={row.original.recipientUserId}
                    size="sm"
                    query={{
                      recipientUserId: row.original.recipientUserId,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="markAllSystemNotificationsRead"
                    recordItemId={row.original.recipientUserId}
                    size="sm"
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.recipientUserId} size="sm" />
              {renderSlotExtensions<SystemNotificationSummaryRecord>(
                frontendComposition,
                "row-actions:system-notification-summary:list",
                "rowActions.after",
                { resource: "system-notification-summary", record: row.original },
              )}
            </RowActionMenu>
          </div>
        ),
        enableSorting: false,
        size: 32,
      }),
    ];
  }, [dictionaryLabel, t]);

  const table = useTable({
    columns,
    initialState: {
      columnPinning: { right: ["actions"], left: ["select"] },
    },
    getRowId: (row) => String(row.recipientUserId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "system_notification_summary_read_model_entity",
        idField: "recipientUserId",
        idFields: ["recipientUserId"],
        queryFields: ["recipientUserId","unreadCount","latestNotificationId","latestSeverity","latestPublishedAt","messageKey","fallbackTitle","fallbackBody","toastPolicy","hasUnreadError"],
        label: t("resources.system_notification_summary.label", "System Notification Summary"),
        aggregateRoute: "systemnotification",
        queryRoute: "systemnotificationsummary",
        dataProviderName: "federation-learning-support",
      },
    },
  });


  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:system-notification-summary:list", "toolbar.before", { resource: "system-notification-summary", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:system-notification-summary:list", "toolbar.actions", { resource: "system-notification-summary", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        >
        </ListToolbar>
        {renderSlotExtensions(frontendComposition, "toolbar:system-notification-summary:list", "toolbar.after", { resource: "system-notification-summary", table })}
      </RefineDataTable>
    </ListView>
  );
};
