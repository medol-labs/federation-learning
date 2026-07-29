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

type RuntimeDatasetBindingCatalogRecord = {
  runtimeDatasetBindingId: string;
  datasetId: string;
  organizationId: string;
  runtimeId: string;
  datasetName?: string;
  dataSourceType: string;
  host?: string;
  port?: number;
  url?: string;
  databaseName?: string;
  schemaName?: string;
  tableName?: string;
  filePath?: string;
  objectBucket?: string;
  objectPrefix?: string;
  dataFormat: string;
  credentialSecretName?: string;
  configuredAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeDatasetBindingCatalogRecord,
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

export const RuntimeDatasetBindingCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeDatasetBindingCatalogRecord>();
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
      columnHelper.accessor("runtimeDatasetBindingId", {
        id: "runtimeDatasetBindingId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetId", {
        id: "datasetId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.datasetId.label", "Dataset Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetName", {
        id: "datasetName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.datasetName.label", "Dataset Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dataSourceType", {
        id: "dataSourceType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.dataSourceType.label", "Data Source Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("host", {
        id: "host",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.host.label", "Host")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("port", {
        id: "port",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.port.label", "Port")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("url", {
        id: "url",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.url.label", "Url")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("databaseName", {
        id: "databaseName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.databaseName.label", "Database Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("schemaName", {
        id: "schemaName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.schemaName.label", "Schema Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("tableName", {
        id: "tableName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.tableName.label", "Table Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("filePath", {
        id: "filePath",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.filePath.label", "File Path")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("objectBucket", {
        id: "objectBucket",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.objectBucket.label", "Object Bucket")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("objectPrefix", {
        id: "objectPrefix",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.objectPrefix.label", "Object Prefix")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dataFormat", {
        id: "dataFormat",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.dataFormat.label", "Data Format")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("credentialSecretName", {
        id: "credentialSecretName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.credentialSecretName.label", "Credential Secret Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("configuredAt", {
        id: "configuredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_binding_catalog.fields.configuredAt.label", "Configured At")} />
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
                  <ShowButton variant="ghost" recordItemId={row.original.runtimeDatasetBindingId} size="sm" />
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
    getRowId: (row) => String(row.runtimeDatasetBindingId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: true,
      meta: {
        tableName: "runtime_dataset_binding_catalog_read_model_entity",
        idField: "runtimeDatasetBindingId",
        idFields: ["runtimeDatasetBindingId"],
        label: t("resources.runtime_dataset_binding_catalog.label", "Runtime Dataset Binding Catalog"),
        aggregateRoute: "runtimedatasetbinding",
        queryRoute: "runtimedatasetbindingcatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="configureRuntimeDatasetBinding" />
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
      </RefineDataTable>
    </ListView>
  );
};
