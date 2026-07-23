// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

import { DataTableColumnHeader } from "@/components/data-table/data-table-column-header";
import { CommandButton } from "@/components/refine-ui/buttons/command";
import { EditButton } from "@/components/refine-ui/buttons/edit";
import { ShowButton } from "@/components/refine-ui/buttons/show";
import { RefineDataTable } from "@/components/refine-ui/data-table/refine-data-table";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@/components/ui/dropdown-menu";
import { MoreHorizontal } from "lucide-react";

type RuntimeAgentLifecycleCatalogRecord = {
  runtimeAgentId: string;
  runtimeInfrastructureId: string;
  agentVersion: string;
  lifecycleStatus: string;
  runtimeAgentSelfCheckPassed?: boolean;
  configurationLoaded?: boolean;
  secretStoreAccessible?: boolean;
  runtimeEngineAdapterReady?: boolean;
  modelRepositoryClientReady?: boolean;
  localDatasetBindingStoreReady?: boolean;
  workingDirectoryWritable?: boolean;
  startedAt?: string;
  readyAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeAgentLifecycleCatalogRecord,
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

export const RuntimeAgentLifecycleCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeAgentLifecycleCatalogRecord>();
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
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("agentVersion", {
        id: "agentVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.agentVersion.label", "Agent Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("lifecycleStatus", {
        id: "lifecycleStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.lifecycleStatus.label", "Lifecycle Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentSelfCheckPassed", {
        id: "runtimeAgentSelfCheckPassed",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.runtimeAgentSelfCheckPassed.label", "Runtime Agent Self Check Passed")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("configurationLoaded", {
        id: "configurationLoaded",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.configurationLoaded.label", "Configuration Loaded")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("secretStoreAccessible", {
        id: "secretStoreAccessible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.secretStoreAccessible.label", "Secret Store Accessible")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeEngineAdapterReady", {
        id: "runtimeEngineAdapterReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.runtimeEngineAdapterReady.label", "Runtime Engine Adapter Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("modelRepositoryClientReady", {
        id: "modelRepositoryClientReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.modelRepositoryClientReady.label", "Model Repository Client Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("localDatasetBindingStoreReady", {
        id: "localDatasetBindingStoreReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.localDatasetBindingStoreReady.label", "Local Dataset Binding Store Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("workingDirectoryWritable", {
        id: "workingDirectoryWritable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.workingDirectoryWritable.label", "Working Directory Writable")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("startedAt", {
        id: "startedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.startedAt.label", "Started At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("readyAt", {
        id: "readyAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_agent_lifecycle_catalog.fields.readyAt.label", "Ready At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.runtimeAgentId} size="sm" />
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
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
    getRowId: (row) => String(row.runtimeAgentId),
    refineCoreProps: {
      dataProviderName: "flruntime-agent",
      syncWithLocation: true,
      meta: {
        tableName: "runtime_agent_lifecycle_catalog_read_model_entity",
        idField: "runtimeAgentId",
        idFields: ["runtimeAgentId"],
        label: t("resources.runtime_agent_lifecycle_catalog.label", "Runtime Agent Lifecycle Catalog"),
        aggregateRoute: "runtimeagentlifecycle",
        queryRoute: "runtimeagentlifecyclecatalog",
        dataProviderName: "flruntime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="reportRuntimeAgentStarted" />
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar table={table.reactTable} />
      </RefineDataTable>
    </ListView>
  );
};
