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
import type { NotificationCategory, NotificationSeverity, NotificationToastPolicy } from "@/contexts/domain/value-types";

type SystemNotificationCatalogRecord = {
  notificationId: string;
  recipientUserId: string;
  recipientRole?: string;
  category: NotificationCategory;
  severity: NotificationSeverity;
  messageKey: string;
  messageArgsJson?: string;
  fallbackTitle: string;
  fallbackBody?: string;
  targetType?: string;
  targetId?: string;
  targetRoute?: string;
  toastPolicy: NotificationToastPolicy;
  publishedAt: string;
  readAt?: string;
  state: "Unread" | "Read";
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: SystemNotificationCatalogRecord,
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

export const SystemNotificationCatalogList = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<SystemNotificationCatalogRecord>();
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
      columnHelper.accessor("notificationId", {
        id: "notificationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.notificationId.label", "Notification Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.notificationId.label", "Notification Id"),
          placeholder: "Enter Notification Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:notificationId",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "notificationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("recipientUserId", {
        id: "recipientUserId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.recipientUserId.label", "Recipient User Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.recipientUserId.label", "Recipient User Id"),
          placeholder: "Enter Recipient User Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:recipientUserId",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "recipientUserId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("recipientRole", {
        id: "recipientRole",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.recipientRole.label", "Recipient Role")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.recipientRole.label", "Recipient Role"),
          placeholder: "Enter Recipient Role",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:recipientRole",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "recipientRole",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("category", {
        id: "category",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.category.label", "Category")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.category.label", "Category"),
          placeholder: "Select Category",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.system_notification_catalog.fields.category.options.SYSTEM", "System"), value: "SYSTEM" },
            { label: t("resources.system_notification_catalog.fields.category.options.DATA_EXCHANGE", "Data Exchange"), value: "DATA_EXCHANGE" },
            { label: t("resources.system_notification_catalog.fields.category.options.TRAINING", "Training"), value: "TRAINING" },
            { label: t("resources.system_notification_catalog.fields.category.options.RUNTIME", "Runtime"), value: "RUNTIME" },
            { label: t("resources.system_notification_catalog.fields.category.options.SECURITY", "Security"), value: "SECURITY" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:category",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "category",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.system_notification_catalog.fields.category.options.SYSTEM", "System"), value: "SYSTEM" },
            { label: t("resources.system_notification_catalog.fields.category.options.DATA_EXCHANGE", "Data Exchange"), value: "DATA_EXCHANGE" },
            { label: t("resources.system_notification_catalog.fields.category.options.TRAINING", "Training"), value: "TRAINING" },
            { label: t("resources.system_notification_catalog.fields.category.options.RUNTIME", "Runtime"), value: "RUNTIME" },
            { label: t("resources.system_notification_catalog.fields.category.options.SECURITY", "Security"), value: "SECURITY" },
          ]),
      }),
      columnHelper.accessor("severity", {
        id: "severity",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.severity.label", "Severity")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.severity.label", "Severity"),
          placeholder: "Select Severity",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.system_notification_catalog.fields.severity.options.INFO", "Info"), value: "INFO" },
            { label: t("resources.system_notification_catalog.fields.severity.options.SUCCESS", "Success"), value: "SUCCESS" },
            { label: t("resources.system_notification_catalog.fields.severity.options.WARNING", "Warning"), value: "WARNING" },
            { label: t("resources.system_notification_catalog.fields.severity.options.ERROR", "Error"), value: "ERROR" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:severity",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "severity",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.system_notification_catalog.fields.severity.options.INFO", "Info"), value: "INFO" },
            { label: t("resources.system_notification_catalog.fields.severity.options.SUCCESS", "Success"), value: "SUCCESS" },
            { label: t("resources.system_notification_catalog.fields.severity.options.WARNING", "Warning"), value: "WARNING" },
            { label: t("resources.system_notification_catalog.fields.severity.options.ERROR", "Error"), value: "ERROR" },
          ]),
      }),
      columnHelper.accessor("messageKey", {
        id: "messageKey",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.messageKey.label", "Message Key")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.messageKey.label", "Message Key"),
          placeholder: "Enter Message Key",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:messageKey",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "messageKey",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("messageArgsJson", {
        id: "messageArgsJson",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.messageArgsJson.label", "Message Args Json")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.messageArgsJson.label", "Message Args Json"),
          placeholder: "Enter Message Args Json",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:messageArgsJson",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "messageArgsJson",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("fallbackTitle", {
        id: "fallbackTitle",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.fallbackTitle.label", "Fallback Title")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.fallbackTitle.label", "Fallback Title"),
          placeholder: "Enter Fallback Title",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:fallbackTitle",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "fallbackTitle",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("fallbackBody", {
        id: "fallbackBody",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.fallbackBody.label", "Fallback Body")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.fallbackBody.label", "Fallback Body"),
          placeholder: "Enter Fallback Body",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:fallbackBody",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "fallbackBody",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("targetType", {
        id: "targetType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.targetType.label", "Target Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.targetType.label", "Target Type"),
          placeholder: "Enter Target Type",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:targetType",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "targetType",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("targetId", {
        id: "targetId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.targetId.label", "Target Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.targetId.label", "Target Id"),
          placeholder: "Enter Target Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:targetId",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "targetId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("targetRoute", {
        id: "targetRoute",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.targetRoute.label", "Target Route")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.targetRoute.label", "Target Route"),
          placeholder: "Enter Target Route",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:targetRoute",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "targetRoute",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("toastPolicy", {
        id: "toastPolicy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.toastPolicy.label", "Toast Policy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.toastPolicy.label", "Toast Policy"),
          placeholder: "Select Toast Policy",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.system_notification_catalog.fields.toastPolicy.options.NONE", "None"), value: "NONE" },
            { label: t("resources.system_notification_catalog.fields.toastPolicy.options.DEFAULT", "Default"), value: "DEFAULT" },
            { label: t("resources.system_notification_catalog.fields.toastPolicy.options.IMPORTANT", "Important"), value: "IMPORTANT" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:toastPolicy",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "toastPolicy",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.system_notification_catalog.fields.toastPolicy.options.NONE", "None"), value: "NONE" },
            { label: t("resources.system_notification_catalog.fields.toastPolicy.options.DEFAULT", "Default"), value: "DEFAULT" },
            { label: t("resources.system_notification_catalog.fields.toastPolicy.options.IMPORTANT", "Important"), value: "IMPORTANT" },
          ]),
      }),
      columnHelper.accessor("publishedAt", {
        id: "publishedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.publishedAt.label", "Published At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.publishedAt.label", "Published At"),
          placeholder: "Enter Published At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:publishedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "publishedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("readAt", {
        id: "readAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.readAt.label", "Read At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.readAt.label", "Read At"),
          placeholder: "Enter Read At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:readAt",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "readAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.system_notification_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.system_notification_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.system_notification_catalog.fields.state.options.Unread", "Unread"), value: "Unread" },
            { label: t("resources.system_notification_catalog.fields.state.options.Read", "Read"), value: "Read" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SystemNotificationCatalogRecord>(
            frontendComposition,
            "field:system-notification-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "system-notification-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.system_notification_catalog.fields.state.options.Unread", "Unread"), value: "Unread" },
            { label: t("resources.system_notification_catalog.fields.state.options.Read", "Read"), value: "Read" },
          ]),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<SystemNotificationCatalogRecord>(
                frontendComposition,
                "row-actions:system-notification-catalog:list",
                "rowActions.before",
                { resource: "system-notification-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "state", ["Unread"]) && (
                  <CommandButton
                    variant="ghost"
                    command="markSystemNotificationRead"
                    recordItemId={row.original.notificationId}
                    size="sm"
                    query={{
                      recipientUserId: row.original.recipientUserId,
                      readAt: row.original.readAt,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.notificationId} size="sm" />
              {renderSlotExtensions<SystemNotificationCatalogRecord>(
                frontendComposition,
                "row-actions:system-notification-catalog:list",
                "rowActions.after",
                { resource: "system-notification-catalog", record: row.original },
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
    getRowId: (row) => String(row.notificationId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "system_notification_catalog_read_model_entity",
        idField: "notificationId",
        idFields: ["notificationId"],
        queryFields: ["notificationId","recipientUserId","recipientRole","category","severity","messageKey","messageArgsJson","fallbackTitle","fallbackBody","targetType","targetId","targetRoute","toastPolicy","publishedAt","readAt","state"],
        label: t("resources.system_notification_catalog.label", "System Notification Catalog"),
        aggregateRoute: "systemnotification",
        queryRoute: "systemnotificationcatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });


  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:system-notification-catalog:list", "toolbar.before", { resource: "system-notification-catalog", table })}
        <CommandButton variant="default" command="publishSystemNotification" />
        {renderSlotExtensions(frontendComposition, "toolbar:system-notification-catalog:list", "toolbar.actions", { resource: "system-notification-catalog", table })}
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
        {renderSlotExtensions(frontendComposition, "toolbar:system-notification-catalog:list", "toolbar.after", { resource: "system-notification-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
