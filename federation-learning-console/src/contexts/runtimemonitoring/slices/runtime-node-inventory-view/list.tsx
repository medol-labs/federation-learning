// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useNotification, useTranslate, type CrudFilter, type CrudSorting } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import { Download } from "lucide-react";
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
import { Button } from "@/components/ui/button";
import { requestDataExport, type DataExportColumn } from "@/lib/data-export";
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";

type RuntimeNodeInventoryViewRecord = {
  nodeId: string;
  runtimeNodeInventoryReportId: string;
  organizationId: string;
  runtimeInfrastructureId: string;
  runtimeAgentId: string;
  organizationName?: string;
  runtimeName?: string;
  runtimeNodeName: string;
  infrastructureNodeId?: string;
  runtimeNodeRole: string;
  nodeReady: boolean;
  runtimeEngineVersion?: string;
  containerEngineVersion?: string;
  operatingSystem?: string;
  architecture: string;
  inventoryHash: string;
  discoveredAt: string;
  recordedAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeNodeInventoryViewRecord,
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

export const RuntimeNodeInventoryViewList = () => {
  const t = useTranslate();
  const { open } = useNotification();
  const [isExporting, setIsExporting] = React.useState(false);
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeNodeInventoryViewRecord>();
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
      columnHelper.accessor("nodeId", {
        id: "nodeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.nodeId.label", "Node Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.nodeId.label", "Node Id"),
          placeholder: "Enter Node Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:nodeId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "nodeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeInventoryReportId", {
        id: "runtimeNodeInventoryReportId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeNodeInventoryReportId.label", "Runtime Node Inventory Report Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.runtimeNodeInventoryReportId.label", "Runtime Node Inventory Report Id"),
          placeholder: "Enter Runtime Node Inventory Report Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:runtimeNodeInventoryReportId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "runtimeNodeInventoryReportId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:organizationId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "organizationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id"),
          placeholder: "Enter Runtime Infrastructure Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:runtimeInfrastructureId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "runtimeInfrastructureId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:runtimeAgentId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "runtimeAgentId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:organizationName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "organizationName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeName", {
        id: "runtimeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeName.label", "Runtime Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.runtimeName.label", "Runtime Name"),
          placeholder: "Enter Runtime Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:runtimeName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "runtimeName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeName", {
        id: "runtimeNodeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeNodeName.label", "Runtime Node Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.runtimeNodeName.label", "Runtime Node Name"),
          placeholder: "Enter Runtime Node Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:runtimeNodeName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "runtimeNodeName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("infrastructureNodeId", {
        id: "infrastructureNodeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.infrastructureNodeId.label", "Infrastructure Node Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.infrastructureNodeId.label", "Infrastructure Node Id"),
          placeholder: "Enter Infrastructure Node Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:infrastructureNodeId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "infrastructureNodeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeRole", {
        id: "runtimeNodeRole",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeNodeRole.label", "Runtime Node Role")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.runtimeNodeRole.label", "Runtime Node Role"),
          placeholder: "Enter Runtime Node Role",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:runtimeNodeRole",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "runtimeNodeRole",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nodeReady", {
        id: "nodeReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.nodeReady.label", "Node Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.nodeReady.label", "Node Ready"),
          placeholder: "Enter Node Ready",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:nodeReady",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "nodeReady",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel),
      }),
      columnHelper.accessor("runtimeEngineVersion", {
        id: "runtimeEngineVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.runtimeEngineVersion.label", "Runtime Engine Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.runtimeEngineVersion.label", "Runtime Engine Version"),
          placeholder: "Enter Runtime Engine Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:runtimeEngineVersion",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "runtimeEngineVersion",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("containerEngineVersion", {
        id: "containerEngineVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.containerEngineVersion.label", "Container Engine Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.containerEngineVersion.label", "Container Engine Version"),
          placeholder: "Enter Container Engine Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:containerEngineVersion",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "containerEngineVersion",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("operatingSystem", {
        id: "operatingSystem",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.operatingSystem.label", "Operating System")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.operatingSystem.label", "Operating System"),
          placeholder: "Enter Operating System",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:operatingSystem",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "operatingSystem",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("architecture", {
        id: "architecture",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.architecture.label", "Architecture")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.architecture.label", "Architecture"),
          placeholder: "Enter Architecture",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:architecture",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "architecture",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("inventoryHash", {
        id: "inventoryHash",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.inventoryHash.label", "Inventory Hash")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.inventoryHash.label", "Inventory Hash"),
          placeholder: "Enter Inventory Hash",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:inventoryHash",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "inventoryHash",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("discoveredAt", {
        id: "discoveredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.discoveredAt.label", "Discovered At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.discoveredAt.label", "Discovered At"),
          placeholder: "Enter Discovered At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:discoveredAt",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "discoveredAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("recordedAt", {
        id: "recordedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_node_inventory_view.fields.recordedAt.label", "Recorded At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_node_inventory_view.fields.recordedAt.label", "Recorded At"),
          placeholder: "Enter Recorded At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeNodeInventoryViewRecord>(
            frontendComposition,
            "field:runtime-node-inventory-view:display:recordedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-node-inventory-view",
              field: "recordedAt",
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
              {renderSlotExtensions<RuntimeNodeInventoryViewRecord>(
                frontendComposition,
                "row-actions:runtime-node-inventory-view:list",
                "rowActions.before",
                { resource: "runtime-node-inventory-view", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.nodeId} size="sm" />
              {renderSlotExtensions<RuntimeNodeInventoryViewRecord>(
                frontendComposition,
                "row-actions:runtime-node-inventory-view:list",
                "rowActions.after",
                { resource: "runtime-node-inventory-view", record: row.original },
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
    getRowId: (row) => String(row.nodeId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_node_inventory_view_read_model_entity",
        idField: "nodeId",
        idFields: ["nodeId"],
        queryFields: ["nodeId","runtimeNodeInventoryReportId","organizationId","runtimeInfrastructureId","runtimeAgentId","organizationName","runtimeName","runtimeNodeName","infrastructureNodeId","runtimeNodeRole","nodeReady","runtimeEngineVersion","containerEngineVersion","operatingSystem","architecture","inventoryHash","discoveredAt","recordedAt"],
        label: t("resources.runtime_node_inventory_view.label", "Runtime Node Inventory View"),
        aggregateRoute: "runtimenodeinventory",
        queryRoute: "runtimenodeinventoryview",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  const handleExport = React.useCallback(async () => {
    setIsExporting(true);
    try {
      const tableState = table.reactTable.getState();
      const filters: CrudFilter[] = tableState.columnFilters.flatMap((filter) => {
        const currentFilter = filter as { id: string; operator?: string; value?: unknown };
        if (!currentFilter.operator) {
          return [];
        }
        return [{
          field: currentFilter.id,
          operator: currentFilter.operator,
          value: currentFilter.value,
        } as CrudFilter];
      });
      const sorters: CrudSorting = tableState.sorting.map((sort) => ({
        field: sort.id,
        order: sort.desc ? "desc" : "asc",
      }));
      const selectedIds = Object.entries(tableState.rowSelection)
        .filter(([, selected]) => selected)
        .map(([id]) => id);
      const exportFilters: CrudFilter[] = selectedIds.length > 0
        ? [...filters, { field: "nodeId", operator: "in", value: selectedIds } as CrudFilter]
        : filters;
      const columns: DataExportColumn[] = table.reactTable
        .getAllLeafColumns()
        .filter((column) => column.getIsVisible())
        .filter((column) => !["select", "actions"].includes(column.id))
        .map((column) => ({
          field: column.id,
          label: String(column.columnDef.meta?.label ?? column.id),
          dictionaryCode: typeof column.columnDef.meta?.dictionaryCode === "string"
            ? column.columnDef.meta.dictionaryCode
            : undefined,
        }));
      const result = await requestDataExport({
        aggregateRoute: "runtimenodeinventory",
        queryRoute: "runtimenodeinventoryview",
        dataProviderName: "federation-learning-platform",
        filters: exportFilters,
        sorters,
        columns,
      });
      open?.({
        type: "success",
        message: result.kind === "job"
          ? t("dataExport.jobCreated", "Export job created")
          : t("dataExport.downloadStarted", "Export download started"),
        description: result.kind === "job" ? result.jobId : result.filename,
      });
    } catch (error) {
      open?.({
        type: "error",
        message: t("dataExport.failed", "Export failed"),
        description: error instanceof Error ? error.message : undefined,
      });
    } finally {
      setIsExporting(false);
    }
  }, [open, table, t]);


  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-node-inventory-view:list", "toolbar.before", { resource: "runtime-node-inventory-view", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-node-inventory-view:list", "toolbar.actions", { resource: "runtime-node-inventory-view", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        >
          <Button type="button" variant="outline" size="sm" onClick={handleExport} disabled={isExporting}>
            <Download className="size-4" />
            {isExporting
              ? t("dataExport.exporting", "Exporting")
              : Object.values(table.reactTable.getState().rowSelection).some(Boolean)
                ? t("dataExport.exportSelected", "Export selected")
                : t("dataExport.export", "Export")}
          </Button>
        </ListToolbar>
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-node-inventory-view:list", "toolbar.after", { resource: "runtime-node-inventory-view", table })}
      </RefineDataTable>
    </ListView>
  );
};
