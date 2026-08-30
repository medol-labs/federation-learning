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

type RuntimeInfrastructureAccessViewRecord = {
  runtimeInfrastructureId: string;
  organizationId: string;
  runtimeInstallationPlanId: string;
  runtimeInfrastructurePackageId: string;
  runtimeInfrastructurePackageName?: string;
  runtimeInfrastructurePackageVersion?: string;
  organizationName?: string;
  runtimeName?: string;
  runtimeEnvironmentType?: string;
  agentInstallMode: string;
  expectedNodeCount: number;
  runtimeAgentId?: string;
  runtimeAgentVersion?: string;
  infrastructureVerifiedAt?: string;
  infrastructureVerificationFailedAt?: string;
  infrastructureVerificationFailureReason?: string;
  agentReadyAt?: string;
  agentDeploymentFailedAt?: string;
  agentDeploymentFailureReason?: string;
  agentDeploymentRetryFailedAt?: string;
  agentDeploymentRetryFailureReason?: string;
  connectedAt?: string;
  state: "PLANNED" | "REGISTERED" | "VERIFIED" | "VERIFICATION_FAILED" | "AGENT_READY" | "RUNTIME_AGENT_FAILED" | "OFFLINE" | "CONNECTED";
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeInfrastructureAccessViewRecord,
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

export const RuntimeInfrastructureAccessViewList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeInfrastructureAccessViewRecord>();
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
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id"),
          placeholder: "Enter Runtime Infrastructure Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInstallationPlanId", {
        id: "runtimeInstallationPlanId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id"),
          placeholder: "Enter Runtime Installation Plan Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageId", {
        id: "runtimeInfrastructurePackageId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id"),
          placeholder: "Enter Runtime Infrastructure Package Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageName", {
        id: "runtimeInfrastructurePackageName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name"),
          placeholder: "Enter Runtime Infrastructure Package Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageVersion", {
        id: "runtimeInfrastructurePackageVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version"),
          placeholder: "Enter Runtime Infrastructure Package Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeName", {
        id: "runtimeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.runtimeName.label", "Runtime Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.runtimeName.label", "Runtime Name"),
          placeholder: "Enter Runtime Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEnvironmentType", {
        id: "runtimeEnvironmentType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.runtimeEnvironmentType.label", "Runtime Environment Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.runtimeEnvironmentType.label", "Runtime Environment Type"),
          placeholder: "Enter Runtime Environment Type",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("agentInstallMode", {
        id: "agentInstallMode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.agentInstallMode.label", "Agent Install Mode")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.agentInstallMode.label", "Agent Install Mode"),
          placeholder: "Enter Agent Install Mode",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("expectedNodeCount", {
        id: "expectedNodeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.expectedNodeCount.label", "Expected Node Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.expectedNodeCount.label", "Expected Node Count"),
          placeholder: "Enter Expected Node Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentVersion", {
        id: "runtimeAgentVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.runtimeAgentVersion.label", "Runtime Agent Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.runtimeAgentVersion.label", "Runtime Agent Version"),
          placeholder: "Enter Runtime Agent Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("infrastructureVerifiedAt", {
        id: "infrastructureVerifiedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.infrastructureVerifiedAt.label", "Infrastructure Verified At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.infrastructureVerifiedAt.label", "Infrastructure Verified At"),
          placeholder: "Enter Infrastructure Verified At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("infrastructureVerificationFailedAt", {
        id: "infrastructureVerificationFailedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailedAt.label", "Infrastructure Verification Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailedAt.label", "Infrastructure Verification Failed At"),
          placeholder: "Enter Infrastructure Verification Failed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("infrastructureVerificationFailureReason", {
        id: "infrastructureVerificationFailureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailureReason.label", "Infrastructure Verification Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.infrastructureVerificationFailureReason.label", "Infrastructure Verification Failure Reason"),
          placeholder: "Enter Infrastructure Verification Failure Reason",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("agentReadyAt", {
        id: "agentReadyAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.agentReadyAt.label", "Agent Ready At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.agentReadyAt.label", "Agent Ready At"),
          placeholder: "Enter Agent Ready At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("agentDeploymentFailedAt", {
        id: "agentDeploymentFailedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailedAt.label", "Agent Deployment Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailedAt.label", "Agent Deployment Failed At"),
          placeholder: "Enter Agent Deployment Failed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("agentDeploymentFailureReason", {
        id: "agentDeploymentFailureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailureReason.label", "Agent Deployment Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.agentDeploymentFailureReason.label", "Agent Deployment Failure Reason"),
          placeholder: "Enter Agent Deployment Failure Reason",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("agentDeploymentRetryFailedAt", {
        id: "agentDeploymentRetryFailedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailedAt.label", "Agent Deployment Retry Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailedAt.label", "Agent Deployment Retry Failed At"),
          placeholder: "Enter Agent Deployment Retry Failed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("agentDeploymentRetryFailureReason", {
        id: "agentDeploymentRetryFailureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailureReason.label", "Agent Deployment Retry Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.agentDeploymentRetryFailureReason.label", "Agent Deployment Retry Failure Reason"),
          placeholder: "Enter Agent Deployment Retry Failure Reason",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("connectedAt", {
        id: "connectedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.connectedAt.label", "Connected At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.connectedAt.label", "Connected At"),
          placeholder: "Enter Connected At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_infrastructure_access_view.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_infrastructure_access_view.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Planned", value: "PLANNED" },
            { label: "Registered", value: "REGISTERED" },
            { label: "Verified", value: "VERIFIED" },
            { label: "Verification Failed", value: "VERIFICATION_FAILED" },
            { label: "Agent Ready", value: "AGENT_READY" },
            { label: "Runtime Agent Failed", value: "RUNTIME_AGENT_FAILED" },
            { label: "Offline", value: "OFFLINE" },
            { label: "Connected", value: "CONNECTED" },
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
                {isCommandVisible(row.original, "", "state", ["Planned"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="registerRuntimeInfrastructure"
                    recordItemId={row.original.runtimeInfrastructureId}
                    size="sm"
                    query={{
                      runtimeInfrastructureId: row.original.runtimeInfrastructureId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="retryRuntimeAgentDeployment"
                    recordItemId={row.original.runtimeInfrastructureId}
                    size="sm"
                    query={{
                      runtimeAgentId: row.original.runtimeAgentId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="recordRuntimeConnectionEstablished"
                    recordItemId={row.original.runtimeInfrastructureId}
                    size="sm"
                    query={{
                      runtimeAgentId: row.original.runtimeAgentId,
                      agentInstallMode: row.original.agentInstallMode,
                      organizationId: row.original.organizationId,
                      runtimeName: row.original.runtimeName,
                      runtimeInfrastructureId: row.original.runtimeInfrastructureId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.runtimeInfrastructureId} size="sm" />
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
    getRowId: (row) => String(row.runtimeInfrastructureId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_infrastructure_access_view_read_model_entity",
        idField: "runtimeInfrastructureId",
        idFields: ["runtimeInfrastructureId"],
        queryFields: ["runtimeInfrastructureId","organizationId","runtimeInstallationPlanId","runtimeInfrastructurePackageId","runtimeInfrastructurePackageName","runtimeInfrastructurePackageVersion","organizationName","runtimeName","runtimeEnvironmentType","agentInstallMode","expectedNodeCount","runtimeAgentId","runtimeAgentVersion","infrastructureVerifiedAt","infrastructureVerificationFailedAt","infrastructureVerificationFailureReason","agentReadyAt","agentDeploymentFailedAt","agentDeploymentFailureReason","agentDeploymentRetryFailedAt","agentDeploymentRetryFailureReason","connectedAt","state"],
        label: t("resources.runtime_infrastructure_access_view.label", "Runtime Infrastructure Access View"),
        aggregateRoute: "runtimeinfrastructure",
        queryRoute: "runtimeinfrastructureaccessview",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="registerOrganization" />
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
