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

type RuntimeCapabilityCatalogRecord = {
  runtimeId: string;
  capabilityTypes: string[];
  capabilityStatus: string;
  detectedAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeCapabilityCatalogRecord,
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

export const RuntimeCapabilityCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeCapabilityCatalogRecord>();
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
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_capability_catalog.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_capability_catalog.fields.runtimeId.label", "Runtime Id"),
          placeholder: "Enter Runtime Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeCapabilityCatalogRecord>(
            frontendComposition,
            "field:runtime-capability-catalog:display:runtimeId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-capability-catalog",
              field: "runtimeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("capabilityTypes", {
        id: "capabilityTypes",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_capability_catalog.fields.capabilityTypes.label", "Capability Types")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.runtime_capability_catalog.fields.capabilityTypes.label", "Capability Types"),
          placeholder: "Enter Capability Types",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeCapabilityCatalogRecord>(
            frontendComposition,
            "field:runtime-capability-catalog:display:capabilityTypes",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-capability-catalog",
              field: "capabilityTypes",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("capabilityStatus", {
        id: "capabilityStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_capability_catalog.fields.capabilityStatus.label", "Capability Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_capability_catalog.fields.capabilityStatus.label", "Capability Status"),
          placeholder: "Enter Capability Status",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeCapabilityCatalogRecord>(
            frontendComposition,
            "field:runtime-capability-catalog:display:capabilityStatus",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-capability-catalog",
              field: "capabilityStatus",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("detectedAt", {
        id: "detectedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_capability_catalog.fields.detectedAt.label", "Detected At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_capability_catalog.fields.detectedAt.label", "Detected At"),
          placeholder: "Enter Detected At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeCapabilityCatalogRecord>(
            frontendComposition,
            "field:runtime-capability-catalog:display:detectedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-capability-catalog",
              field: "detectedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<RuntimeCapabilityCatalogRecord>(
                frontendComposition,
                "row-actions:runtime-capability-catalog:list",
                "rowActions.before",
                { resource: "runtime-capability-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.runtimeId} size="sm" />
              {renderSlotExtensions<RuntimeCapabilityCatalogRecord>(
                frontendComposition,
                "row-actions:runtime-capability-catalog:list",
                "rowActions.after",
                { resource: "runtime-capability-catalog", record: row.original },
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
    getRowId: (row) => String(row.runtimeId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_capability_catalog_read_model_entity",
        idField: "runtimeId",
        idFields: ["runtimeId"],
        queryFields: ["runtimeId","capabilityStatus","detectedAt"],
        label: t("resources.runtime_capability_catalog.label", "Runtime Capability Catalog"),
        aggregateRoute: "runtimecapability",
        queryRoute: "runtimecapabilitycatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-capability-catalog:list", "toolbar.before", { resource: "runtime-capability-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-capability-catalog:list", "toolbar.actions", { resource: "runtime-capability-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-capability-catalog:list", "toolbar.after", { resource: "runtime-capability-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
