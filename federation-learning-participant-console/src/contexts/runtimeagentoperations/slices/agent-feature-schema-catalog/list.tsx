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

type AgentFeatureSchemaCatalogRecord = {
  featureSchemaId: string;
  featureDomain: string;
  featureSchemaVersion: string;
  schemaStatus: string;
  syncedAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: AgentFeatureSchemaCatalogRecord,
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

export const AgentFeatureSchemaCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<AgentFeatureSchemaCatalogRecord>();
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
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_feature_schema_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_feature_schema_catalog.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentFeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:agent-feature-schema-catalog:display:featureSchemaId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-feature-schema-catalog",
              field: "featureSchemaId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_feature_schema_catalog.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_feature_schema_catalog.fields.featureDomain.label", "Feature Domain"),
          placeholder: "Enter Feature Domain",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentFeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:agent-feature-schema-catalog:display:featureDomain",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-feature-schema-catalog",
              field: "featureDomain",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_feature_schema_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_feature_schema_catalog.fields.featureSchemaVersion.label", "Feature Schema Version"),
          placeholder: "Enter Feature Schema Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentFeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:agent-feature-schema-catalog:display:featureSchemaVersion",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-feature-schema-catalog",
              field: "featureSchemaVersion",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("schemaStatus", {
        id: "schemaStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_feature_schema_catalog.fields.schemaStatus.label", "Schema Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_feature_schema_catalog.fields.schemaStatus.label", "Schema Status"),
          placeholder: "Enter Schema Status",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentFeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:agent-feature-schema-catalog:display:schemaStatus",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-feature-schema-catalog",
              field: "schemaStatus",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("syncedAt", {
        id: "syncedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_feature_schema_catalog.fields.syncedAt.label", "Synced At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_feature_schema_catalog.fields.syncedAt.label", "Synced At"),
          placeholder: "Enter Synced At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentFeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:agent-feature-schema-catalog:display:syncedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-feature-schema-catalog",
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
              {renderSlotExtensions<AgentFeatureSchemaCatalogRecord>(
                frontendComposition,
                "row-actions:agent-feature-schema-catalog:list",
                "rowActions.before",
                { resource: "agent-feature-schema-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.featureSchemaId} size="sm" />
              {renderSlotExtensions<AgentFeatureSchemaCatalogRecord>(
                frontendComposition,
                "row-actions:agent-feature-schema-catalog:list",
                "rowActions.after",
                { resource: "agent-feature-schema-catalog", record: row.original },
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
    getRowId: (row) => String(row.featureSchemaId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "agent_feature_schema_catalog_read_model_entity",
        idField: "featureSchemaId",
        idFields: ["featureSchemaId"],
        queryFields: ["featureSchemaId","featureDomain","featureSchemaVersion","schemaStatus","syncedAt"],
        label: t("resources.agent_feature_schema_catalog.label", "Agent Feature Schema Catalog"),
        aggregateRoute: "agentfeatureschemacatalog",
        queryRoute: "agentfeatureschemacatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:agent-feature-schema-catalog:list", "toolbar.before", { resource: "agent-feature-schema-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:agent-feature-schema-catalog:list", "toolbar.actions", { resource: "agent-feature-schema-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:agent-feature-schema-catalog:list", "toolbar.after", { resource: "agent-feature-schema-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
