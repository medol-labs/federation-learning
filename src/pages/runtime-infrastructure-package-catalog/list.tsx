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

type RuntimeInfrastructurePackageCatalogRecord = {
  runtimeInfrastructurePackageId: string;
  packageName: string;
  packageVersion: string;
  runtimeEnvironmentType: string;
  state: "REGISTERED";
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeInfrastructurePackageCatalogRecord,
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

export const RuntimeInfrastructurePackageCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeInfrastructurePackageCatalogRecord>();
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
      columnHelper.accessor("runtimeInfrastructurePackageId", {
        id: "runtimeInfrastructurePackageId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_package_catalog.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_package_catalog.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id"),
          placeholder: "Enter Runtime Infrastructure Package Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("packageName", {
        id: "packageName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_package_catalog.fields.packageName.label", "Package Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_package_catalog.fields.packageName.label", "Package Name"),
          placeholder: "Enter Package Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("packageVersion", {
        id: "packageVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_package_catalog.fields.packageVersion.label", "Package Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_package_catalog.fields.packageVersion.label", "Package Version"),
          placeholder: "Enter Package Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEnvironmentType", {
        id: "runtimeEnvironmentType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_package_catalog.fields.runtimeEnvironmentType.label", "Runtime Environment Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_package_catalog.fields.runtimeEnvironmentType.label", "Runtime Environment Type"),
          placeholder: "Enter Runtime Environment Type",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_package_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_package_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Registered", value: "REGISTERED" },
          ],
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
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
                  <ShowButton variant="ghost" recordItemId={row.original.runtimeInfrastructurePackageId} size="sm" />
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
    getRowId: (row) => String(row.runtimeInfrastructurePackageId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_infrastructure_package_catalog_read_model_entity",
        idField: "runtimeInfrastructurePackageId",
        idFields: ["runtimeInfrastructurePackageId"],
        queryFields: ["runtimeInfrastructurePackageId","packageName","packageVersion","runtimeEnvironmentType","state"],
        label: t("resources.runtime_infrastructure_package_catalog.label", "Runtime Infrastructure Package Catalog"),
        aggregateRoute: "runtimeinfrastructurepackage",
        queryRoute: "runtimeinfrastructurepackagecatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="registerRuntimeInfrastructurePackage" />
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
