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

type AgentDictionaryValueCatalogRecord = {
  dictionaryValueId: string;
  dictionaryId: string;
  dictionaryCode: string;
  valueCode: string;
  displayName: string;
  displayOrder?: number;
  active: boolean;
  state: string;
  syncedAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: AgentDictionaryValueCatalogRecord,
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

export const AgentDictionaryValueCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<AgentDictionaryValueCatalogRecord>();
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
      columnHelper.accessor("dictionaryValueId", {
        id: "dictionaryValueId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dictionary_value_catalog.fields.dictionaryValueId.label", "Dictionary Value Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dictionary_value_catalog.fields.dictionaryValueId.label", "Dictionary Value Id"),
          placeholder: "Enter Dictionary Value Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDictionaryValueCatalogRecord>(
            frontendComposition,
            "field:agent-dictionary-value-catalog:display:dictionaryValueId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dictionary-value-catalog",
              field: "dictionaryValueId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryId", {
        id: "dictionaryId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dictionary_value_catalog.fields.dictionaryId.label", "Dictionary Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dictionary_value_catalog.fields.dictionaryId.label", "Dictionary Id"),
          placeholder: "Enter Dictionary Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDictionaryValueCatalogRecord>(
            frontendComposition,
            "field:agent-dictionary-value-catalog:display:dictionaryId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dictionary-value-catalog",
              field: "dictionaryId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryCode", {
        id: "dictionaryCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dictionary_value_catalog.fields.dictionaryCode.label", "Dictionary Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dictionary_value_catalog.fields.dictionaryCode.label", "Dictionary Code"),
          placeholder: "Enter Dictionary Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDictionaryValueCatalogRecord>(
            frontendComposition,
            "field:agent-dictionary-value-catalog:display:dictionaryCode",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dictionary-value-catalog",
              field: "dictionaryCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("valueCode", {
        id: "valueCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dictionary_value_catalog.fields.valueCode.label", "Value Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dictionary_value_catalog.fields.valueCode.label", "Value Code"),
          placeholder: "Enter Value Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDictionaryValueCatalogRecord>(
            frontendComposition,
            "field:agent-dictionary-value-catalog:display:valueCode",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dictionary-value-catalog",
              field: "valueCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("displayName", {
        id: "displayName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dictionary_value_catalog.fields.displayName.label", "Display Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dictionary_value_catalog.fields.displayName.label", "Display Name"),
          placeholder: "Enter Display Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDictionaryValueCatalogRecord>(
            frontendComposition,
            "field:agent-dictionary-value-catalog:display:displayName",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dictionary-value-catalog",
              field: "displayName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("displayOrder", {
        id: "displayOrder",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dictionary_value_catalog.fields.displayOrder.label", "Display Order")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dictionary_value_catalog.fields.displayOrder.label", "Display Order"),
          placeholder: "Enter Display Order",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDictionaryValueCatalogRecord>(
            frontendComposition,
            "field:agent-dictionary-value-catalog:display:displayOrder",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dictionary-value-catalog",
              field: "displayOrder",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("active", {
        id: "active",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dictionary_value_catalog.fields.active.label", "Active")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dictionary_value_catalog.fields.active.label", "Active"),
          placeholder: "Enter Active",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDictionaryValueCatalogRecord>(
            frontendComposition,
            "field:agent-dictionary-value-catalog:display:active",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dictionary-value-catalog",
              field: "active",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dictionary_value_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dictionary_value_catalog.fields.state.label", "State"),
          placeholder: "Enter State",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDictionaryValueCatalogRecord>(
            frontendComposition,
            "field:agent-dictionary-value-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dictionary-value-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("syncedAt", {
        id: "syncedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dictionary_value_catalog.fields.syncedAt.label", "Synced At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dictionary_value_catalog.fields.syncedAt.label", "Synced At"),
          placeholder: "Enter Synced At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDictionaryValueCatalogRecord>(
            frontendComposition,
            "field:agent-dictionary-value-catalog:display:syncedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dictionary-value-catalog",
              field: "syncedAt",
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
              {renderSlotExtensions<AgentDictionaryValueCatalogRecord>(
                frontendComposition,
                "row-actions:agent-dictionary-value-catalog:list",
                "rowActions.before",
                { resource: "agent-dictionary-value-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.dictionaryValueId} size="sm" />
              {renderSlotExtensions<AgentDictionaryValueCatalogRecord>(
                frontendComposition,
                "row-actions:agent-dictionary-value-catalog:list",
                "rowActions.after",
                { resource: "agent-dictionary-value-catalog", record: row.original },
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
    getRowId: (row) => String(row.dictionaryValueId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "agent_dictionary_value_catalog_read_model_entity",
        idField: "dictionaryValueId",
        idFields: ["dictionaryValueId"],
        queryFields: ["dictionaryValueId","dictionaryId","dictionaryCode","valueCode","displayName","displayOrder","active","state","syncedAt"],
        label: t("resources.agent_dictionary_value_catalog.label", "Agent Dictionary Value Catalog"),
        aggregateRoute: "agentdictionaryvaluecatalog",
        queryRoute: "agentdictionaryvaluecatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:agent-dictionary-value-catalog:list", "toolbar.before", { resource: "agent-dictionary-value-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:agent-dictionary-value-catalog:list", "toolbar.actions", { resource: "agent-dictionary-value-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:agent-dictionary-value-catalog:list", "toolbar.after", { resource: "agent-dictionary-value-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
