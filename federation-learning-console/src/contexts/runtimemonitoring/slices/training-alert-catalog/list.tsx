// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { DataTableColumnHeader } from "@/components/data-table/data-table-column-header";
import { CommandButton } from "@/components/refine-ui/buttons/command";
import { EditButton } from "@/components/refine-ui/buttons/edit";
import { ShowButton } from "@/components/refine-ui/buttons/show";
import { RefineDataTable } from "@/components/refine-ui/data-table/refine-data-table";
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";

type TrainingAlertCatalogRecord = {
  alertId: string;
  nodeId: string;
  trainingJobId?: string;
  runtimeNodeName?: string;
  trainingJobObjective?: string;
  severity: string;
  message: string;
  state: "RAISED" | "ACKNOWLEDGED" | "RESOLVED";
  acknowledgedAt?: string;
  resolvedAt?: string;
  resolutionSummary?: string;
  canAcknowledge: boolean;
  canResolve: boolean;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: TrainingAlertCatalogRecord,
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

export const TrainingAlertCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<TrainingAlertCatalogRecord>();
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
      columnHelper.accessor("alertId", {
        id: "alertId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.alertId.label", "Alert Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.alertId.label", "Alert Id"),
          placeholder: "Enter Alert Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:alertId",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "alertId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nodeId", {
        id: "nodeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.nodeId.label", "Node Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.nodeId.label", "Node Id"),
          placeholder: "Enter Node Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:nodeId",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "nodeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:trainingJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "trainingJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeName", {
        id: "runtimeNodeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.runtimeNodeName.label", "Runtime Node Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.runtimeNodeName.label", "Runtime Node Name"),
          placeholder: "Enter Runtime Node Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:runtimeNodeName",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "runtimeNodeName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobObjective", {
        id: "trainingJobObjective",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.trainingJobObjective.label", "Training Job Objective")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.trainingJobObjective.label", "Training Job Objective"),
          placeholder: "Enter Training Job Objective",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:trainingJobObjective",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "trainingJobObjective",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("severity", {
        id: "severity",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.severity.label", "Severity")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.severity.label", "Severity"),
          placeholder: "Enter Severity",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:severity",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "severity",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("message", {
        id: "message",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.message.label", "Message")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.message.label", "Message"),
          placeholder: "Enter Message",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:message",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "message",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Raised", value: "RAISED" },
            { label: "Acknowledged", value: "ACKNOWLEDGED" },
            { label: "Resolved", value: "RESOLVED" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("acknowledgedAt", {
        id: "acknowledgedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.acknowledgedAt.label", "Acknowledged At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.acknowledgedAt.label", "Acknowledged At"),
          placeholder: "Enter Acknowledged At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:acknowledgedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "acknowledgedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("resolvedAt", {
        id: "resolvedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.resolvedAt.label", "Resolved At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.resolvedAt.label", "Resolved At"),
          placeholder: "Enter Resolved At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:resolvedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "resolvedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("resolutionSummary", {
        id: "resolutionSummary",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.resolutionSummary.label", "Resolution Summary")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.resolutionSummary.label", "Resolution Summary"),
          placeholder: "Enter Resolution Summary",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:resolutionSummary",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "resolutionSummary",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("canAcknowledge", {
        id: "canAcknowledge",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.canAcknowledge.label", "Can Acknowledge")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.canAcknowledge.label", "Can Acknowledge"),
          placeholder: "Enter Can Acknowledge",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:canAcknowledge",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "canAcknowledge",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canResolve", {
        id: "canResolve",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_alert_catalog.fields.canResolve.label", "Can Resolve")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_alert_catalog.fields.canResolve.label", "Can Resolve"),
          placeholder: "Enter Can Resolve",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<TrainingAlertCatalogRecord>(
            frontendComposition,
            "field:training-alert-catalog:display:canResolve",
            {
              value: getValue(),
              record: row.original,
              resource: "training-alert-catalog",
              field: "canResolve",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<TrainingAlertCatalogRecord>(
                frontendComposition,
                "row-actions:training-alert-catalog:list",
                "rowActions.before",
                { resource: "training-alert-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "canAcknowledge", "state", ["Raised"]) && (
                  <CommandButton
                    variant="ghost"
                    command="acknowledgeTrainingAlert"
                    recordItemId={row.original.alertId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "canResolve", "state", ["Acknowledged"]) && (
                  <CommandButton
                    variant="ghost"
                    command="resolveTrainingAlert"
                    recordItemId={row.original.alertId}
                    size="sm"
                    query={{
                      resolutionSummary: row.original.resolutionSummary,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.alertId} size="sm" />
              {renderSlotExtensions<TrainingAlertCatalogRecord>(
                frontendComposition,
                "row-actions:training-alert-catalog:list",
                "rowActions.after",
                { resource: "training-alert-catalog", record: row.original },
              )}
            </RowActionMenu>
          </div>
        ),
        enableSorting: false,
        size: 32,
      }),
    ];
  }, [t]);

  const table = useTable({
    columns,
    initialState: {
      columnPinning: { right: ["actions"], left: ["select"] },
    },
    getRowId: (row) => String(row.alertId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "training_alert_catalog_read_model_entity",
        idField: "alertId",
        idFields: ["alertId"],
        queryFields: ["alertId","nodeId","trainingJobId","runtimeNodeName","trainingJobObjective","severity","message","state","acknowledgedAt","resolvedAt","resolutionSummary","canAcknowledge","canResolve"],
        label: t("resources.training_alert_catalog.label", "Training Alert Catalog"),
        aggregateRoute: "trainingalert",
        queryRoute: "trainingalertcatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:training-alert-catalog:list", "toolbar.before", { resource: "training-alert-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:training-alert-catalog:list", "toolbar.actions", { resource: "training-alert-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:training-alert-catalog:list", "toolbar.after", { resource: "training-alert-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
