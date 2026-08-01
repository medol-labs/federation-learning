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

type RuntimeInstallationGuideRecord = {
  runtimeInstallationPlanId: string;
  organizationId: string;
  runtimeInfrastructureId: string;
  runtimeInfrastructurePackageId?: string;
  runtimeInfrastructurePackageName?: string;
  runtimeInfrastructurePackageVersion?: string;
  infrastructureInstallGuide?: string;
  organizationName?: string;
  runtimeName?: string;
  bootstrapCommand: string;
  runtimeDeploymentTargetType?: string;
  runtimeEnvironmentType?: string;
  agentInstallMode?: string;
  installProfile?: string;
  architecture?: string;
  expectedNodeCount?: number;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeInstallationGuideRecord,
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

export const RuntimeInstallationGuideList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeInstallationGuideRecord>();
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
      columnHelper.accessor("runtimeInstallationPlanId", {
        id: "runtimeInstallationPlanId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageId", {
        id: "runtimeInfrastructurePackageId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageName", {
        id: "runtimeInfrastructurePackageName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageVersion", {
        id: "runtimeInfrastructurePackageVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("infrastructureInstallGuide", {
        id: "infrastructureInstallGuide",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.infrastructureInstallGuide.label", "Infrastructure Install Guide")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeName", {
        id: "runtimeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeName.label", "Runtime Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("bootstrapCommand", {
        id: "bootstrapCommand",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.bootstrapCommand.label", "Bootstrap Command")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeDeploymentTargetType", {
        id: "runtimeDeploymentTargetType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeDeploymentTargetType.label", "Runtime Deployment Target Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEnvironmentType", {
        id: "runtimeEnvironmentType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeEnvironmentType.label", "Runtime Environment Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("agentInstallMode", {
        id: "agentInstallMode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.agentInstallMode.label", "Agent Install Mode")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("installProfile", {
        id: "installProfile",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.installProfile.label", "Install Profile")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("architecture", {
        id: "architecture",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.architecture.label", "Architecture")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("expectedNodeCount", {
        id: "expectedNodeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.expectedNodeCount.label", "Expected Node Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
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
                  <ShowButton variant="ghost" recordItemId={row.original.runtimeInstallationPlanId} size="sm" />
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
    getRowId: (row) => String(row.runtimeInstallationPlanId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: true,
      meta: {
        tableName: "runtime_installation_guide_read_model_entity",
        idField: "runtimeInstallationPlanId",
        idFields: ["runtimeInstallationPlanId"],
        label: t("resources.runtime_installation_guide.label", "Runtime Installation Guide"),
        aggregateRoute: "runtimeinstallationplan",
        queryRoute: "runtimeinstallationguide",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="createRuntimeInstallationPlan" />
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
