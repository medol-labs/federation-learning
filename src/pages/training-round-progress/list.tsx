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
import type { TrainingRoundParticipant } from "@/domain/value-types";

type TrainingRoundProgressRecord = {
  trainingJobId: string;
  trainingRunConfigurationId: string;
  featureSchemaId: string;
  roundId: string;
  trainingJobObjective?: string;
  featureDomain?: string;
  featureSchemaVersion?: string;
  roundNumber: number;
  state: string;
  selectedOrganizationIds: string[];
  selectedParticipants: TrainingRoundParticipant[];
  selectedOrganizationCount: number;
  selectedRuntimeCount: number;
  targetRuntimeCount: number;
  executionPlanDispatchedCount: number;
  roundExecutionStartedCount: number;
  submittedModelUpdateCount: number;
  rejectedUpdateCount: number;
  acceptedModelUpdateCount: number;
  acceptedUpdateCount: number;
  pendingUpdateCount: number;
  failedRoundExecutionCount: number;
  completedRoundExecutionCount: number;
  retriedRoundExecutionCount: number;
  failedRoundExecutionRetryCount: number;
  quorumMet: boolean;
  quorumStatus: string;
  minimumNodesPerRound?: number;
  aggregationReady: boolean;
  secureAggregationRequired: boolean;
  secureAggregationStatus?: string;
  evaluationComplete: boolean;
  progressPercent: number;
  currentPhase: string;
  nextAction?: string;
  blockedReason?: string;
  delayedReason?: string;
  roundStartedAt?: string;
  contributionDeadlineAt?: string;
  aggregationStartedAt?: string;
  evaluationSubmittedAt?: string;
  completedAt?: string;
  failedAt?: string;
  baseModelId?: string;
  artifactRefs: string[];
  rejectedUpdateReasons: string[];
  aggregatedModelId?: string;
  globalAccuracy?: string;
  globalFairnessScore?: string;
  failureReason?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: TrainingRoundProgressRecord,
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

export const TrainingRoundProgressList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<TrainingRoundProgressRecord>();
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
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingRunConfigurationId", {
        id: "trainingRunConfigurationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundId", {
        id: "roundId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.roundId.label", "Round Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobObjective", {
        id: "trainingJobObjective",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.trainingJobObjective.label", "Training Job Objective")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundNumber", {
        id: "roundNumber",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.roundNumber.label", "Round Number")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedOrganizationIds", {
        id: "selectedOrganizationIds",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.selectedOrganizationIds.label", "Selected Organization Ids")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedParticipants", {
        id: "selectedParticipants",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.selectedParticipants.label", "Selected Participants")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedOrganizationCount", {
        id: "selectedOrganizationCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.selectedOrganizationCount.label", "Selected Organization Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedRuntimeCount", {
        id: "selectedRuntimeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.selectedRuntimeCount.label", "Selected Runtime Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("targetRuntimeCount", {
        id: "targetRuntimeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.targetRuntimeCount.label", "Target Runtime Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("executionPlanDispatchedCount", {
        id: "executionPlanDispatchedCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.executionPlanDispatchedCount.label", "Execution Plan Dispatched Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundExecutionStartedCount", {
        id: "roundExecutionStartedCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.roundExecutionStartedCount.label", "Round Execution Started Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("submittedModelUpdateCount", {
        id: "submittedModelUpdateCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.submittedModelUpdateCount.label", "Submitted Model Update Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("rejectedUpdateCount", {
        id: "rejectedUpdateCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.rejectedUpdateCount.label", "Rejected Update Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("acceptedModelUpdateCount", {
        id: "acceptedModelUpdateCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.acceptedModelUpdateCount.label", "Accepted Model Update Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("acceptedUpdateCount", {
        id: "acceptedUpdateCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.acceptedUpdateCount.label", "Accepted Update Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("pendingUpdateCount", {
        id: "pendingUpdateCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.pendingUpdateCount.label", "Pending Update Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failedRoundExecutionCount", {
        id: "failedRoundExecutionCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.failedRoundExecutionCount.label", "Failed Round Execution Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("completedRoundExecutionCount", {
        id: "completedRoundExecutionCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.completedRoundExecutionCount.label", "Completed Round Execution Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("retriedRoundExecutionCount", {
        id: "retriedRoundExecutionCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.retriedRoundExecutionCount.label", "Retried Round Execution Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failedRoundExecutionRetryCount", {
        id: "failedRoundExecutionRetryCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.failedRoundExecutionRetryCount.label", "Failed Round Execution Retry Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("quorumMet", {
        id: "quorumMet",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.quorumMet.label", "Quorum Met")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("quorumStatus", {
        id: "quorumStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.quorumStatus.label", "Quorum Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("minimumNodesPerRound", {
        id: "minimumNodesPerRound",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("aggregationReady", {
        id: "aggregationReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.aggregationReady.label", "Aggregation Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("secureAggregationRequired", {
        id: "secureAggregationRequired",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.secureAggregationRequired.label", "Secure Aggregation Required")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("secureAggregationStatus", {
        id: "secureAggregationStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.secureAggregationStatus.label", "Secure Aggregation Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("evaluationComplete", {
        id: "evaluationComplete",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.evaluationComplete.label", "Evaluation Complete")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("progressPercent", {
        id: "progressPercent",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.progressPercent.label", "Progress Percent")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("currentPhase", {
        id: "currentPhase",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.currentPhase.label", "Current Phase")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nextAction", {
        id: "nextAction",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.nextAction.label", "Next Action")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("blockedReason", {
        id: "blockedReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.blockedReason.label", "Blocked Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("delayedReason", {
        id: "delayedReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.delayedReason.label", "Delayed Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundStartedAt", {
        id: "roundStartedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.roundStartedAt.label", "Round Started At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("contributionDeadlineAt", {
        id: "contributionDeadlineAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.contributionDeadlineAt.label", "Contribution Deadline At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("aggregationStartedAt", {
        id: "aggregationStartedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.aggregationStartedAt.label", "Aggregation Started At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("evaluationSubmittedAt", {
        id: "evaluationSubmittedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.evaluationSubmittedAt.label", "Evaluation Submitted At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("completedAt", {
        id: "completedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.completedAt.label", "Completed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("failedAt", {
        id: "failedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.failedAt.label", "Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("baseModelId", {
        id: "baseModelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.baseModelId.label", "Base Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("artifactRefs", {
        id: "artifactRefs",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.artifactRefs.label", "Artifact Refs")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("rejectedUpdateReasons", {
        id: "rejectedUpdateReasons",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.rejectedUpdateReasons.label", "Rejected Update Reasons")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("aggregatedModelId", {
        id: "aggregatedModelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.aggregatedModelId.label", "Aggregated Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("globalAccuracy", {
        id: "globalAccuracy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.globalAccuracy.label", "Global Accuracy")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("globalFairnessScore", {
        id: "globalFairnessScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.globalFairnessScore.label", "Global Fairness Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failureReason", {
        id: "failureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_round_progress.fields.failureReason.label", "Failure Reason")} />
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
            {isCommandVisible(row.original, "", "", []) && (
            <CommandButton
              variant="outline"
              command="cancelTrainingJob"
              recordItemId={row.original.trainingJobId}
              size="sm"
            />
            )}
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
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                    query={{
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                      roundNumber: row.original.roundNumber,
                      featureSchemaId: row.original.featureSchemaId,
                      baseModelId: row.original.baseModelId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Running"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="completeRoundExecution"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                    query={{
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Running"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="failRoundExecution"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                    query={{
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
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
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                    query={{
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                      roundNumber: row.original.roundNumber,
                      featureSchemaId: row.original.featureSchemaId,
                      baseModelId: row.original.baseModelId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="submitGlobalModelEvaluation"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                    query={{
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      featureSchemaId: row.original.featureSchemaId,
                      roundId: row.original.roundId,
                      aggregatedModelId: row.original.aggregatedModelId,
                      globalAccuracy: row.original.globalAccuracy,
                      globalFairnessScore: row.original.globalFairnessScore,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Draft"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="submitTrainingJob"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="pauseTrainingJob"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.trainingJobId} size="sm" />
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
    getRowId: (row) => String(row.trainingJobId) + ":" + String(row.roundId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: true,
      meta: {
        tableName: "training_round_progress_read_model_entity",
        idField: "trainingJobId",
        idFields: ["trainingJobId","roundId"],
        label: t("resources.training_round_progress.label", "Training Round Progress"),
        aggregateRoute: "traininground",
        queryRoute: "trainingroundprogress",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        <CommandButton variant="destructive" command="cancelTrainingJob" size="sm" />
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
