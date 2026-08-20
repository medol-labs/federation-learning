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

type TrainingJobDashboardRecord = {
  trainingJobId: string;
  federationId: string;
  trainingRunConfigurationId: string;
  featureSchemaId: string;
  federationName?: string;
  featureDomain?: string;
  featureSchemaVersion?: string;
  objective: string;
  strategyName?: string;
  aggregationAlgorithm?: string;
  secureAggregationRequired?: boolean;
  state: string;
  workflowStage: string;
  workflowStep: number;
  nextAction?: string;
  availableActions: string[];
  blockedReason?: string;
  canSubmit: boolean;
  canStartRound: boolean;
  canPause: boolean;
  canResume: boolean;
  canCancel: boolean;
  canComplete: boolean;
  currentRoundNumber: number;
  startedRuntimeCount: number;
  minimumNodesPerRound: number;
  maxRounds: number;
  roundProgressPercent: number;
  globalAccuracy?: string;
  finalModelId?: string;
  stopReason?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: TrainingJobDashboardRecord,
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

export const TrainingJobDashboardList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<TrainingJobDashboardRecord>();
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
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationId", {
        id: "federationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.federationId.label", "Federation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.federationId.label", "Federation Id"),
          placeholder: "Enter Federation Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingRunConfigurationId", {
        id: "trainingRunConfigurationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.trainingRunConfigurationId.label", "Training Run Configuration Id"),
          placeholder: "Enter Training Run Configuration Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationName", {
        id: "federationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.federationName.label", "Federation Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.federationName.label", "Federation Name"),
          placeholder: "Enter Federation Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.featureDomain.label", "Feature Domain"),
          placeholder: "Enter Feature Domain",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.featureSchemaVersion.label", "Feature Schema Version"),
          placeholder: "Enter Feature Schema Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("objective", {
        id: "objective",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.objective.label", "Objective")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.objective.label", "Objective"),
          placeholder: "Enter Objective",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("strategyName", {
        id: "strategyName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.strategyName.label", "Strategy Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.strategyName.label", "Strategy Name"),
          placeholder: "Enter Strategy Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("aggregationAlgorithm", {
        id: "aggregationAlgorithm",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.aggregationAlgorithm.label", "Aggregation Algorithm")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.aggregationAlgorithm.label", "Aggregation Algorithm"),
          placeholder: "Enter Aggregation Algorithm",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("secureAggregationRequired", {
        id: "secureAggregationRequired",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.secureAggregationRequired.label", "Secure Aggregation Required")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.secureAggregationRequired.label", "Secure Aggregation Required"),
          placeholder: "Enter Secure Aggregation Required",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.state.label", "State"),
          placeholder: "Enter State",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("workflowStage", {
        id: "workflowStage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.workflowStage.label", "Workflow Stage")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.workflowStage.label", "Workflow Stage"),
          placeholder: "Enter Workflow Stage",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("workflowStep", {
        id: "workflowStep",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.workflowStep.label", "Workflow Step")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.workflowStep.label", "Workflow Step"),
          placeholder: "Enter Workflow Step",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nextAction", {
        id: "nextAction",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.nextAction.label", "Next Action")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.nextAction.label", "Next Action"),
          placeholder: "Enter Next Action",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("availableActions", {
        id: "availableActions",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.availableActions.label", "Available Actions")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.training_job_dashboard.fields.availableActions.label", "Available Actions"),
          placeholder: "Enter Available Actions",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("blockedReason", {
        id: "blockedReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.blockedReason.label", "Blocked Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.blockedReason.label", "Blocked Reason"),
          placeholder: "Enter Blocked Reason",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("canSubmit", {
        id: "canSubmit",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.canSubmit.label", "Can Submit")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.canSubmit.label", "Can Submit"),
          placeholder: "Enter Can Submit",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canStartRound", {
        id: "canStartRound",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.canStartRound.label", "Can Start Round")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.canStartRound.label", "Can Start Round"),
          placeholder: "Enter Can Start Round",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canPause", {
        id: "canPause",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.canPause.label", "Can Pause")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.canPause.label", "Can Pause"),
          placeholder: "Enter Can Pause",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canResume", {
        id: "canResume",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.canResume.label", "Can Resume")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.canResume.label", "Can Resume"),
          placeholder: "Enter Can Resume",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canCancel", {
        id: "canCancel",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.canCancel.label", "Can Cancel")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.canCancel.label", "Can Cancel"),
          placeholder: "Enter Can Cancel",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canComplete", {
        id: "canComplete",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.canComplete.label", "Can Complete")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.canComplete.label", "Can Complete"),
          placeholder: "Enter Can Complete",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("currentRoundNumber", {
        id: "currentRoundNumber",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.currentRoundNumber.label", "Current Round Number")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.currentRoundNumber.label", "Current Round Number"),
          placeholder: "Enter Current Round Number",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("startedRuntimeCount", {
        id: "startedRuntimeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.startedRuntimeCount.label", "Started Runtime Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.startedRuntimeCount.label", "Started Runtime Count"),
          placeholder: "Enter Started Runtime Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("minimumNodesPerRound", {
        id: "minimumNodesPerRound",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round"),
          placeholder: "Enter Minimum Nodes Per Round",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("maxRounds", {
        id: "maxRounds",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.maxRounds.label", "Max Rounds")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.maxRounds.label", "Max Rounds"),
          placeholder: "Enter Max Rounds",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundProgressPercent", {
        id: "roundProgressPercent",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.roundProgressPercent.label", "Round Progress Percent")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.roundProgressPercent.label", "Round Progress Percent"),
          placeholder: "Enter Round Progress Percent",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("globalAccuracy", {
        id: "globalAccuracy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.globalAccuracy.label", "Global Accuracy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.globalAccuracy.label", "Global Accuracy"),
          placeholder: "Enter Global Accuracy",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("finalModelId", {
        id: "finalModelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.finalModelId.label", "Final Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.finalModelId.label", "Final Model Id"),
          placeholder: "Enter Final Model Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("stopReason", {
        id: "stopReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_job_dashboard.fields.stopReason.label", "Stop Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_job_dashboard.fields.stopReason.label", "Stop Reason"),
          placeholder: "Enter Stop Reason",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            {isCommandVisible(row.original, "canCancel", "", []) && (
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
                {isCommandVisible(row.original, "canSubmit", "state", ["Draft"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="submitTrainingJob"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "canPause", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="pauseTrainingJob"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "canResume", "state", ["Paused"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="resumeTrainingJob"
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
    getRowId: (row) => String(row.trainingJobId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "training_job_dashboard_read_model_entity",
        idField: "trainingJobId",
        idFields: ["trainingJobId"],
        queryFields: ["trainingJobId","federationId","trainingRunConfigurationId","featureSchemaId","federationName","featureDomain","featureSchemaVersion","objective","strategyName","aggregationAlgorithm","secureAggregationRequired","state","workflowStage","workflowStep","nextAction","blockedReason","canSubmit","canStartRound","canPause","canResume","canCancel","canComplete","currentRoundNumber","startedRuntimeCount","minimumNodesPerRound","maxRounds","roundProgressPercent","globalAccuracy","finalModelId","stopReason"],
        label: t("resources.training_job_dashboard.label", "Training Job Dashboard"),
        aggregateRoute: "trainingjob",
        queryRoute: "trainingjobdashboard",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="createTrainingJob" />
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
