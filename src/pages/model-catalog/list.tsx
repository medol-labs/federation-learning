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

type ModelCatalogRecord = {
  modelId: string;
  trainingJobId: string;
  finalRoundId: string;
  modelArtifactId: string;
  trainingJobObjective?: string;
  modelArtifactDigest: string;
  evaluationReportId: string;
  finalGlobalAccuracy: string;
  state: string;
  releaseChannel?: string;
  productionStage?: string;
  previousModelId?: string;
  experimentId?: string;
  hyperparameterSnapshotId?: string;
  reproducibilityManifestId?: string;
  modelCardId?: string;
  baselineModelId?: string;
  hasEvaluationPackage: boolean;
  approvalStatus: string;
  releaseStatus: string;
  isProduction: boolean;
  canRecordEvaluationPackage: boolean;
  canApprove: boolean;
  canPromoteToProduction: boolean;
  canRollback: boolean;
  canRetire: boolean;
  blockedReason?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: ModelCatalogRecord,
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

export const ModelCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<ModelCatalogRecord>();
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
      columnHelper.accessor("modelId", {
        id: "modelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.modelId.label", "Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.modelId.label", "Model Id"),
          placeholder: "Enter Model Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("finalRoundId", {
        id: "finalRoundId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.finalRoundId.label", "Final Round Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.finalRoundId.label", "Final Round Id"),
          placeholder: "Enter Final Round Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelArtifactId", {
        id: "modelArtifactId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.modelArtifactId.label", "Model Artifact Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.modelArtifactId.label", "Model Artifact Id"),
          placeholder: "Enter Model Artifact Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobObjective", {
        id: "trainingJobObjective",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.trainingJobObjective.label", "Training Job Objective")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.trainingJobObjective.label", "Training Job Objective"),
          placeholder: "Enter Training Job Objective",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelArtifactDigest", {
        id: "modelArtifactDigest",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest"),
          placeholder: "Enter Model Artifact Digest",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("evaluationReportId", {
        id: "evaluationReportId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.evaluationReportId.label", "Evaluation Report Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.evaluationReportId.label", "Evaluation Report Id"),
          placeholder: "Enter Evaluation Report Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("finalGlobalAccuracy", {
        id: "finalGlobalAccuracy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.finalGlobalAccuracy.label", "Final Global Accuracy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.finalGlobalAccuracy.label", "Final Global Accuracy"),
          placeholder: "Enter Final Global Accuracy",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.state.label", "State"),
          placeholder: "Enter State",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("releaseChannel", {
        id: "releaseChannel",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.releaseChannel.label", "Release Channel")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.releaseChannel.label", "Release Channel"),
          placeholder: "Enter Release Channel",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("productionStage", {
        id: "productionStage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.productionStage.label", "Production Stage")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.productionStage.label", "Production Stage"),
          placeholder: "Enter Production Stage",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("previousModelId", {
        id: "previousModelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.previousModelId.label", "Previous Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.previousModelId.label", "Previous Model Id"),
          placeholder: "Enter Previous Model Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("experimentId", {
        id: "experimentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.experimentId.label", "Experiment Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.experimentId.label", "Experiment Id"),
          placeholder: "Enter Experiment Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("hyperparameterSnapshotId", {
        id: "hyperparameterSnapshotId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.hyperparameterSnapshotId.label", "Hyperparameter Snapshot Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.hyperparameterSnapshotId.label", "Hyperparameter Snapshot Id"),
          placeholder: "Enter Hyperparameter Snapshot Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("reproducibilityManifestId", {
        id: "reproducibilityManifestId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.reproducibilityManifestId.label", "Reproducibility Manifest Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.reproducibilityManifestId.label", "Reproducibility Manifest Id"),
          placeholder: "Enter Reproducibility Manifest Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelCardId", {
        id: "modelCardId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.modelCardId.label", "Model Card Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.modelCardId.label", "Model Card Id"),
          placeholder: "Enter Model Card Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("baselineModelId", {
        id: "baselineModelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.baselineModelId.label", "Baseline Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.baselineModelId.label", "Baseline Model Id"),
          placeholder: "Enter Baseline Model Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("hasEvaluationPackage", {
        id: "hasEvaluationPackage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.hasEvaluationPackage.label", "Has Evaluation Package")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.hasEvaluationPackage.label", "Has Evaluation Package"),
          placeholder: "Enter Has Evaluation Package",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("approvalStatus", {
        id: "approvalStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.approvalStatus.label", "Approval Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.approvalStatus.label", "Approval Status"),
          placeholder: "Enter Approval Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("releaseStatus", {
        id: "releaseStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.releaseStatus.label", "Release Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.releaseStatus.label", "Release Status"),
          placeholder: "Enter Release Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("isProduction", {
        id: "isProduction",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.isProduction.label", "Is Production")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.isProduction.label", "Is Production"),
          placeholder: "Enter Is Production",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canRecordEvaluationPackage", {
        id: "canRecordEvaluationPackage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.canRecordEvaluationPackage.label", "Can Record Evaluation Package")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.canRecordEvaluationPackage.label", "Can Record Evaluation Package"),
          placeholder: "Enter Can Record Evaluation Package",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canApprove", {
        id: "canApprove",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.canApprove.label", "Can Approve")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.canApprove.label", "Can Approve"),
          placeholder: "Enter Can Approve",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canPromoteToProduction", {
        id: "canPromoteToProduction",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.canPromoteToProduction.label", "Can Promote To Production")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.canPromoteToProduction.label", "Can Promote To Production"),
          placeholder: "Enter Can Promote To Production",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canRollback", {
        id: "canRollback",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.canRollback.label", "Can Rollback")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.canRollback.label", "Can Rollback"),
          placeholder: "Enter Can Rollback",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canRetire", {
        id: "canRetire",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.canRetire.label", "Can Retire")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.canRetire.label", "Can Retire"),
          placeholder: "Enter Can Retire",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("blockedReason", {
        id: "blockedReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_catalog.fields.blockedReason.label", "Blocked Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_catalog.fields.blockedReason.label", "Blocked Reason"),
          placeholder: "Enter Blocked Reason",
          variant: "text",
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
                {isCommandVisible(row.original, "", "state", ["Candidate"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="recordModelEvaluationPackage"
                    recordItemId={row.original.modelId}
                    size="sm"
                    query={{
                      trainingJobId: row.original.trainingJobId,
                      evaluationReportId: row.original.evaluationReportId,
                      experimentId: row.original.experimentId,
                      hyperparameterSnapshotId: row.original.hyperparameterSnapshotId,
                      reproducibilityManifestId: row.original.reproducibilityManifestId,
                      modelCardId: row.original.modelCardId,
                      baselineModelId: row.original.baselineModelId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "canApprove", "state", ["EvaluationPackaged"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="approveModel"
                    recordItemId={row.original.modelId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Approved"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="promoteModelToProduction"
                    recordItemId={row.original.modelId}
                    size="sm"
                    query={{
                      releaseChannel: row.original.releaseChannel,
                      productionStage: row.original.productionStage,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "canRollback", "state", ["Production"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="rollbackModel"
                    recordItemId={row.original.modelId}
                    size="sm"
                    query={{
                      previousModelId: row.original.previousModelId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "canRetire", "state", ["Production"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="retireModel"
                    recordItemId={row.original.modelId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.modelId} size="sm" />
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
    getRowId: (row) => String(row.modelId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "model_catalog_read_model_entity",
        idField: "modelId",
        idFields: ["modelId"],
        queryFields: ["modelId","trainingJobId","finalRoundId","modelArtifactId","trainingJobObjective","modelArtifactDigest","evaluationReportId","finalGlobalAccuracy","state","releaseChannel","productionStage","previousModelId","experimentId","hyperparameterSnapshotId","reproducibilityManifestId","modelCardId","baselineModelId","hasEvaluationPackage","approvalStatus","releaseStatus","isProduction","canRecordEvaluationPackage","canApprove","canPromoteToProduction","canRollback","canRetire","blockedReason"],
        label: t("resources.model_catalog.label", "Model Catalog"),
        aggregateRoute: "model",
        queryRoute: "modelcatalog",
        dataProviderName: "federation-learning-platform",
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
