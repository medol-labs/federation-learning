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
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";

type RuntimeInstallationPlanCatalogRecord = {
  runtimeInstallationPlanId: string;
  organizationId: string;
  organizationName?: string;
  runtimeInfrastructurePackageId: string;
  runtimeInfrastructurePackageName?: string;
  runtimeInfrastructurePackageVersion?: string;
  runtimeName: string;
  agentInstallMode: string;
  expectedNodeCount: number;
  planStatus: string;
  runtimeInfrastructureId?: string;
  preparedAt?: string;
  preparedNodeCount?: number;
  observedNodeCount?: number;
  runtimeAgentId?: string;
  runtimeAgentVersion?: string;
  plannedAt: string;
  verifiedAt?: string;
  verificationFailedAt?: string;
  verificationFailureReason?: string;
  agentReadyAt?: string;
  agentDeploymentFailedAt?: string;
  agentDeploymentFailureReason?: string;
  agentDeploymentRetryFailedAt?: string;
  agentDeploymentRetryFailureReason?: string;
  lastConnectedAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeInstallationPlanCatalogRecord,
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

export const RuntimeInstallationPlanCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeInstallationPlanCatalogRecord>();
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
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id"),
          placeholder: "Enter Runtime Installation Plan Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageId", {
        id: "runtimeInfrastructurePackageId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id"),
          placeholder: "Enter Runtime Infrastructure Package Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageName", {
        id: "runtimeInfrastructurePackageName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name"),
          placeholder: "Enter Runtime Infrastructure Package Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageVersion", {
        id: "runtimeInfrastructurePackageVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version"),
          placeholder: "Enter Runtime Infrastructure Package Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeName", {
        id: "runtimeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.runtimeName.label", "Runtime Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.runtimeName.label", "Runtime Name"),
          placeholder: "Enter Runtime Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("agentInstallMode", {
        id: "agentInstallMode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.agentInstallMode.label", "Agent Install Mode")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.agentInstallMode.label", "Agent Install Mode"),
          placeholder: "Enter Agent Install Mode",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("expectedNodeCount", {
        id: "expectedNodeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.expectedNodeCount.label", "Expected Node Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.expectedNodeCount.label", "Expected Node Count"),
          placeholder: "Enter Expected Node Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("planStatus", {
        id: "planStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.planStatus.label", "Plan Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.planStatus.label", "Plan Status"),
          placeholder: "Enter Plan Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id"),
          placeholder: "Enter Runtime Infrastructure Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("preparedAt", {
        id: "preparedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.preparedAt.label", "Prepared At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.preparedAt.label", "Prepared At"),
          placeholder: "Enter Prepared At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("preparedNodeCount", {
        id: "preparedNodeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.preparedNodeCount.label", "Prepared Node Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.preparedNodeCount.label", "Prepared Node Count"),
          placeholder: "Enter Prepared Node Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("observedNodeCount", {
        id: "observedNodeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.observedNodeCount.label", "Observed Node Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.observedNodeCount.label", "Observed Node Count"),
          placeholder: "Enter Observed Node Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentVersion", {
        id: "runtimeAgentVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.runtimeAgentVersion.label", "Runtime Agent Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.runtimeAgentVersion.label", "Runtime Agent Version"),
          placeholder: "Enter Runtime Agent Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("plannedAt", {
        id: "plannedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.plannedAt.label", "Planned At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.plannedAt.label", "Planned At"),
          placeholder: "Enter Planned At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("verifiedAt", {
        id: "verifiedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.verifiedAt.label", "Verified At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.verifiedAt.label", "Verified At"),
          placeholder: "Enter Verified At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("verificationFailedAt", {
        id: "verificationFailedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.verificationFailedAt.label", "Verification Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.verificationFailedAt.label", "Verification Failed At"),
          placeholder: "Enter Verification Failed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("verificationFailureReason", {
        id: "verificationFailureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.verificationFailureReason.label", "Verification Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.verificationFailureReason.label", "Verification Failure Reason"),
          placeholder: "Enter Verification Failure Reason",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("agentReadyAt", {
        id: "agentReadyAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.agentReadyAt.label", "Agent Ready At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.agentReadyAt.label", "Agent Ready At"),
          placeholder: "Enter Agent Ready At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("agentDeploymentFailedAt", {
        id: "agentDeploymentFailedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.agentDeploymentFailedAt.label", "Agent Deployment Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.agentDeploymentFailedAt.label", "Agent Deployment Failed At"),
          placeholder: "Enter Agent Deployment Failed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("agentDeploymentFailureReason", {
        id: "agentDeploymentFailureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.agentDeploymentFailureReason.label", "Agent Deployment Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.agentDeploymentFailureReason.label", "Agent Deployment Failure Reason"),
          placeholder: "Enter Agent Deployment Failure Reason",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("agentDeploymentRetryFailedAt", {
        id: "agentDeploymentRetryFailedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.agentDeploymentRetryFailedAt.label", "Agent Deployment Retry Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.agentDeploymentRetryFailedAt.label", "Agent Deployment Retry Failed At"),
          placeholder: "Enter Agent Deployment Retry Failed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("agentDeploymentRetryFailureReason", {
        id: "agentDeploymentRetryFailureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.agentDeploymentRetryFailureReason.label", "Agent Deployment Retry Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.agentDeploymentRetryFailureReason.label", "Agent Deployment Retry Failure Reason"),
          placeholder: "Enter Agent Deployment Retry Failure Reason",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("lastConnectedAt", {
        id: "lastConnectedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_plan_catalog.fields.lastConnectedAt.label", "Last Connected At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_plan_catalog.fields.lastConnectedAt.label", "Last Connected At"),
          placeholder: "Enter Last Connected At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="retryRuntimeInfrastructureVerification"
                    recordItemId={row.original.runtimeInstallationPlanId}
                    size="sm"
                    query={{
                      runtimeInstallationPlanId: row.original.runtimeInstallationPlanId,
                      runtimeAgentId: row.original.runtimeAgentId,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="retryRuntimeAgentDeployment"
                    recordItemId={row.original.runtimeInstallationPlanId}
                    size="sm"
                    query={{
                      runtimeAgentId: row.original.runtimeAgentId,
                      runtimeInstallationPlanId: row.original.runtimeInstallationPlanId,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "planStatus", ["Planned"]) && (
                  <CommandButton
                    variant="ghost"
                    command="registerRuntimeInfrastructure"
                    recordItemId={row.original.runtimeInstallationPlanId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                      organizationName: row.original.organizationName,
                      runtimeInfrastructurePackageId: row.original.runtimeInfrastructurePackageId,
                      runtimeInfrastructurePackageName: row.original.runtimeInfrastructurePackageName,
                      runtimeInfrastructurePackageVersion: row.original.runtimeInfrastructurePackageVersion,
                      runtimeName: row.original.runtimeName,
                      agentInstallMode: row.original.agentInstallMode,
                      expectedNodeCount: row.original.expectedNodeCount,
                      runtimeInfrastructureId: row.original.runtimeInfrastructureId,
                      runtimeInstallationPlanId: row.original.runtimeInstallationPlanId,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "planStatus", ["Registered"]) && (
                  <CommandButton
                    variant="ghost"
                    command="confirmRuntimeInfrastructurePrepared"
                    recordItemId={row.original.runtimeInstallationPlanId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                      organizationName: row.original.organizationName,
                      runtimeInfrastructurePackageId: row.original.runtimeInfrastructurePackageId,
                      runtimeInfrastructurePackageName: row.original.runtimeInfrastructurePackageName,
                      runtimeInfrastructurePackageVersion: row.original.runtimeInfrastructurePackageVersion,
                      runtimeName: row.original.runtimeName,
                      agentInstallMode: row.original.agentInstallMode,
                      expectedNodeCount: row.original.expectedNodeCount,
                      preparedNodeCount: row.original.preparedNodeCount,
                      runtimeInfrastructureId: row.original.runtimeInfrastructureId,
                      runtimeInstallationPlanId: row.original.runtimeInstallationPlanId,
                      runtimeAgentId: row.original.runtimeAgentId,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.runtimeInstallationPlanId} size="sm" />
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
    getRowId: (row) => String(row.runtimeInstallationPlanId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_installation_plan_catalog_read_model_entity",
        idField: "runtimeInstallationPlanId",
        idFields: ["runtimeInstallationPlanId"],
        queryFields: ["runtimeInstallationPlanId","organizationId","organizationName","runtimeInfrastructurePackageId","runtimeInfrastructurePackageName","runtimeInfrastructurePackageVersion","runtimeName","agentInstallMode","expectedNodeCount","planStatus","runtimeInfrastructureId","preparedAt","preparedNodeCount","observedNodeCount","runtimeAgentId","runtimeAgentVersion","plannedAt","verifiedAt","verificationFailedAt","verificationFailureReason","agentReadyAt","agentDeploymentFailedAt","agentDeploymentFailureReason","agentDeploymentRetryFailedAt","agentDeploymentRetryFailureReason","lastConnectedAt"],
        label: t("resources.runtime_installation_plan_catalog.label", "Runtime Installation Plan Catalog"),
        aggregateRoute: "runtimeinstallationplan",
        queryRoute: "runtimeinstallationplancatalog",
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
