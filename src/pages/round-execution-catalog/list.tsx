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

type RoundExecutionCatalogRecord = {
  roundExecutionId: string;
  executionSessionId?: string;
  executionPlanId?: string;
  trainingJobId: string;
  trainingRunConfigurationId: string;
  roundId: string;
  roundNumber?: number;
  organizationId?: string;
  runtimeId: string;
  state: string;
  featureSchemaId?: string;
  baseModelVersionId?: string;
  runtimeEngineJobId?: string;
  localExecutionRequirementsSatisfied?: boolean;
  runtimeIdentityMatched?: boolean;
  runtimeDatasetBindingAvailable?: boolean;
  datasetAccessValidated?: boolean;
  baseModelAvailable?: boolean;
  trainingConfigurationSupported?: boolean;
  runtimeResourceAvailable?: boolean;
  runtimeAgentIdle?: boolean;
  updateArtifactId?: string;
  artifactRef?: string;
  artifactDigest?: string;
  trainingLoss?: string;
  receivedAt?: string;
  acceptedAt?: string;
  rejectedAt?: string;
  startedAt?: string;
  completedAt?: string;
  failedAt?: string;
  submittedAt?: string;
  failureReason?: string;
  retryReason?: string;
  runtimeEngineReleased?: boolean;
  runtimeEngineReleaseFailureReason?: string;
  rejectionReasons: string[];
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RoundExecutionCatalogRecord,
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

export const RoundExecutionCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RoundExecutionCatalogRecord>();
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
      columnHelper.accessor("roundExecutionId", {
        id: "roundExecutionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.roundExecutionId.label", "Round Execution Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("executionSessionId", {
        id: "executionSessionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.executionSessionId.label", "Execution Session Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("executionPlanId", {
        id: "executionPlanId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.executionPlanId.label", "Execution Plan Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingRunConfigurationId", {
        id: "trainingRunConfigurationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundId", {
        id: "roundId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.roundId.label", "Round Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundNumber", {
        id: "roundNumber",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.roundNumber.label", "Round Number")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("baseModelVersionId", {
        id: "baseModelVersionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.baseModelVersionId.label", "Base Model Version Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineJobId", {
        id: "runtimeEngineJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineJobId.label", "Runtime Engine Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("localExecutionRequirementsSatisfied", {
        id: "localExecutionRequirementsSatisfied",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.localExecutionRequirementsSatisfied.label", "Local Execution Requirements Satisfied")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeIdentityMatched", {
        id: "runtimeIdentityMatched",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeIdentityMatched.label", "Runtime Identity Matched")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeDatasetBindingAvailable", {
        id: "runtimeDatasetBindingAvailable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeDatasetBindingAvailable.label", "Runtime Dataset Binding Available")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetAccessValidated", {
        id: "datasetAccessValidated",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.datasetAccessValidated.label", "Dataset Access Validated")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("baseModelAvailable", {
        id: "baseModelAvailable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.baseModelAvailable.label", "Base Model Available")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("trainingConfigurationSupported", {
        id: "trainingConfigurationSupported",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.trainingConfigurationSupported.label", "Training Configuration Supported")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeResourceAvailable", {
        id: "runtimeResourceAvailable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeResourceAvailable.label", "Runtime Resource Available")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeAgentIdle", {
        id: "runtimeAgentIdle",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeAgentIdle.label", "Runtime Agent Idle")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("updateArtifactId", {
        id: "updateArtifactId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.updateArtifactId.label", "Update Artifact Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("artifactRef", {
        id: "artifactRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.artifactRef.label", "Artifact Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("artifactDigest", {
        id: "artifactDigest",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.artifactDigest.label", "Artifact Digest")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingLoss", {
        id: "trainingLoss",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.trainingLoss.label", "Training Loss")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("receivedAt", {
        id: "receivedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.receivedAt.label", "Received At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("acceptedAt", {
        id: "acceptedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.acceptedAt.label", "Accepted At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("rejectedAt", {
        id: "rejectedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.rejectedAt.label", "Rejected At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("startedAt", {
        id: "startedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.startedAt.label", "Started At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("completedAt", {
        id: "completedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.completedAt.label", "Completed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("failedAt", {
        id: "failedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.failedAt.label", "Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("submittedAt", {
        id: "submittedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.submittedAt.label", "Submitted At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("failureReason", {
        id: "failureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.failureReason.label", "Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("retryReason", {
        id: "retryReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.retryReason.label", "Retry Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineReleased", {
        id: "runtimeEngineReleased",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineReleased.label", "Runtime Engine Released")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeEngineReleaseFailureReason", {
        id: "runtimeEngineReleaseFailureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineReleaseFailureReason.label", "Runtime Engine Release Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("rejectionReasons", {
        id: "rejectionReasons",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.rejectionReasons.label", "Rejection Reasons")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
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
                {isCommandVisible(row.original, "", "state", ["Running"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="retryRoundExecutionAfterStartFailure"
                    recordItemId={row.original.roundExecutionId}
                    size="sm"
                    query={{
                      executionSessionId: row.original.executionSessionId,
                      executionPlanId: row.original.executionPlanId,
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                      roundNumber: row.original.roundNumber,
                      runtimeId: row.original.runtimeId,
                      organizationId: row.original.organizationId,
                      featureSchemaId: row.original.featureSchemaId,
                      baseModelVersionId: row.original.baseModelVersionId,
                      runtimeEngineJobId: row.original.runtimeEngineJobId,
                      retryReason: row.original.retryReason,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Running"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="completeRoundExecution"
                    recordItemId={row.original.roundExecutionId}
                    size="sm"
                    query={{
                      executionSessionId: row.original.executionSessionId,
                      executionPlanId: row.original.executionPlanId,
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                      runtimeId: row.original.runtimeId,
                      runtimeEngineJobId: row.original.runtimeEngineJobId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Running"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="failRoundExecution"
                    recordItemId={row.original.roundExecutionId}
                    size="sm"
                    query={{
                      executionSessionId: row.original.executionSessionId,
                      executionPlanId: row.original.executionPlanId,
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                      runtimeId: row.original.runtimeId,
                      runtimeEngineJobId: row.original.runtimeEngineJobId,
                      failureReason: row.original.failureReason,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Failed"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="retryRoundExecutionAfterRuntimeFailure"
                    recordItemId={row.original.roundExecutionId}
                    size="sm"
                    query={{
                      executionSessionId: row.original.executionSessionId,
                      executionPlanId: row.original.executionPlanId,
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                      roundNumber: row.original.roundNumber,
                      runtimeId: row.original.runtimeId,
                      organizationId: row.original.organizationId,
                      featureSchemaId: row.original.featureSchemaId,
                      baseModelVersionId: row.original.baseModelVersionId,
                      runtimeEngineJobId: row.original.runtimeEngineJobId,
                      retryReason: row.original.retryReason,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="submitModelUpdateSubmission"
                    recordItemId={row.original.roundExecutionId}
                    size="sm"
                    query={{
                      executionSessionId: row.original.executionSessionId,
                      executionPlanId: row.original.executionPlanId,
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                      roundExecutionId: row.original.roundExecutionId,
                      runtimeId: row.original.runtimeId,
                      featureSchemaId: row.original.featureSchemaId,
                      updateArtifactId: row.original.updateArtifactId,
                      artifactRef: row.original.artifactRef,
                      artifactDigest: row.original.artifactDigest,
                      trainingLoss: row.original.trainingLoss,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.roundExecutionId} size="sm" />
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
    getRowId: (row) => String(row.roundExecutionId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: true,
      meta: {
        tableName: "round_execution_catalog_read_model_entity",
        idField: "roundExecutionId",
        idFields: ["roundExecutionId"],
        label: t("resources.round_execution_catalog.label", "Round Execution Catalog"),
        aggregateRoute: "roundexecution",
        queryRoute: "roundexecutioncatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
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
