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

type TrainingRunConfigurationCatalogRecord = {
  trainingRunConfigurationId: string;
  federationId: string;
  featureSchemaId: string;
  initialModelId: string;
  initialModelName: string;
  initialModelVersion: string;
  federationName?: string;
  featureDomain?: string;
  featureSchemaVersion?: string;
  initialModelArtifactUri: string;
  initialModelRegistryRef: string;
  initialModelFormat: string;
  initialModelArtifactDigest: string;
  initialModelSignatureUri?: string;
  strategyName: string;
  aggregationAlgorithm: string;
  maxRounds: number;
  minimumNodesPerRound: number;
  roundTimeoutSeconds: number;
  nodeResponseTimeoutSeconds: number;
  localEpochs: number;
  batchSize: number;
  learningRate: string;
  optimizer: string;
  lossFunction: string;
  gradientClippingNorm?: string;
  secureAggregationRequired: boolean;
  differentialPrivacyEnabled: boolean;
  dpNoiseMultiplier?: string;
  dpClipNorm?: string;
  minimumAccuracy: string;
  minimumFairnessScore?: string;
  failureToleranceRatio: string;
  updateReason?: string;
  lockedByTrainingJobId?: string;
  state: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: TrainingRunConfigurationCatalogRecord,
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

export const TrainingRunConfigurationCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<TrainingRunConfigurationCatalogRecord>();
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
      columnHelper.accessor("trainingRunConfigurationId", {
        id: "trainingRunConfigurationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id"),
          placeholder: "Enter Training Run Configuration Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationId", {
        id: "federationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.federationId.label", "Federation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.federationId.label", "Federation Id"),
          placeholder: "Enter Federation Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("initialModelId", {
        id: "initialModelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.initialModelId.label", "Initial Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.initialModelId.label", "Initial Model Id"),
          placeholder: "Enter Initial Model Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("initialModelName", {
        id: "initialModelName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.initialModelName.label", "Initial Model Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.initialModelName.label", "Initial Model Name"),
          placeholder: "Enter Initial Model Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("initialModelVersion", {
        id: "initialModelVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.initialModelVersion.label", "Initial Model Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.initialModelVersion.label", "Initial Model Version"),
          placeholder: "Enter Initial Model Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationName", {
        id: "federationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.federationName.label", "Federation Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.federationName.label", "Federation Name"),
          placeholder: "Enter Federation Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.featureDomain.label", "Feature Domain"),
          placeholder: "Enter Feature Domain",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.featureSchemaVersion.label", "Feature Schema Version"),
          placeholder: "Enter Feature Schema Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("initialModelArtifactUri", {
        id: "initialModelArtifactUri",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.initialModelArtifactUri.label", "Initial Model Artifact Uri")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.initialModelArtifactUri.label", "Initial Model Artifact Uri"),
          placeholder: "Enter Initial Model Artifact Uri",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("initialModelRegistryRef", {
        id: "initialModelRegistryRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.initialModelRegistryRef.label", "Initial Model Registry Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.initialModelRegistryRef.label", "Initial Model Registry Ref"),
          placeholder: "Enter Initial Model Registry Ref",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("initialModelFormat", {
        id: "initialModelFormat",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.initialModelFormat.label", "Initial Model Format")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.initialModelFormat.label", "Initial Model Format"),
          placeholder: "Enter Initial Model Format",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("initialModelArtifactDigest", {
        id: "initialModelArtifactDigest",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.initialModelArtifactDigest.label", "Initial Model Artifact Digest")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.initialModelArtifactDigest.label", "Initial Model Artifact Digest"),
          placeholder: "Enter Initial Model Artifact Digest",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("initialModelSignatureUri", {
        id: "initialModelSignatureUri",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.initialModelSignatureUri.label", "Initial Model Signature Uri")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.initialModelSignatureUri.label", "Initial Model Signature Uri"),
          placeholder: "Enter Initial Model Signature Uri",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("strategyName", {
        id: "strategyName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.strategyName.label", "Strategy Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.strategyName.label", "Strategy Name"),
          placeholder: "Enter Strategy Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("aggregationAlgorithm", {
        id: "aggregationAlgorithm",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.aggregationAlgorithm.label", "Aggregation Algorithm")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.aggregationAlgorithm.label", "Aggregation Algorithm"),
          placeholder: "Enter Aggregation Algorithm",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("maxRounds", {
        id: "maxRounds",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.maxRounds.label", "Max Rounds")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.maxRounds.label", "Max Rounds"),
          placeholder: "Enter Max Rounds",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("minimumNodesPerRound", {
        id: "minimumNodesPerRound",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round"),
          placeholder: "Enter Minimum Nodes Per Round",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundTimeoutSeconds", {
        id: "roundTimeoutSeconds",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.roundTimeoutSeconds.label", "Round Timeout Seconds")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.roundTimeoutSeconds.label", "Round Timeout Seconds"),
          placeholder: "Enter Round Timeout Seconds",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nodeResponseTimeoutSeconds", {
        id: "nodeResponseTimeoutSeconds",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.nodeResponseTimeoutSeconds.label", "Node Response Timeout Seconds")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.nodeResponseTimeoutSeconds.label", "Node Response Timeout Seconds"),
          placeholder: "Enter Node Response Timeout Seconds",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("localEpochs", {
        id: "localEpochs",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.localEpochs.label", "Local Epochs")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.localEpochs.label", "Local Epochs"),
          placeholder: "Enter Local Epochs",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("batchSize", {
        id: "batchSize",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.batchSize.label", "Batch Size")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.batchSize.label", "Batch Size"),
          placeholder: "Enter Batch Size",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("learningRate", {
        id: "learningRate",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.learningRate.label", "Learning Rate")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.learningRate.label", "Learning Rate"),
          placeholder: "Enter Learning Rate",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("optimizer", {
        id: "optimizer",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.optimizer.label", "Optimizer")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.optimizer.label", "Optimizer"),
          placeholder: "Enter Optimizer",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("lossFunction", {
        id: "lossFunction",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.lossFunction.label", "Loss Function")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.lossFunction.label", "Loss Function"),
          placeholder: "Enter Loss Function",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("gradientClippingNorm", {
        id: "gradientClippingNorm",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.gradientClippingNorm.label", "Gradient Clipping Norm")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.gradientClippingNorm.label", "Gradient Clipping Norm"),
          placeholder: "Enter Gradient Clipping Norm",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("secureAggregationRequired", {
        id: "secureAggregationRequired",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.secureAggregationRequired.label", "Secure Aggregation Required")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.secureAggregationRequired.label", "Secure Aggregation Required"),
          placeholder: "Enter Secure Aggregation Required",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("differentialPrivacyEnabled", {
        id: "differentialPrivacyEnabled",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.differentialPrivacyEnabled.label", "Differential Privacy Enabled")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.differentialPrivacyEnabled.label", "Differential Privacy Enabled"),
          placeholder: "Enter Differential Privacy Enabled",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("dpNoiseMultiplier", {
        id: "dpNoiseMultiplier",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.dpNoiseMultiplier.label", "Dp Noise Multiplier")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.dpNoiseMultiplier.label", "Dp Noise Multiplier"),
          placeholder: "Enter Dp Noise Multiplier",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dpClipNorm", {
        id: "dpClipNorm",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.dpClipNorm.label", "Dp Clip Norm")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.dpClipNorm.label", "Dp Clip Norm"),
          placeholder: "Enter Dp Clip Norm",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("minimumAccuracy", {
        id: "minimumAccuracy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.minimumAccuracy.label", "Minimum Accuracy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.minimumAccuracy.label", "Minimum Accuracy"),
          placeholder: "Enter Minimum Accuracy",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("minimumFairnessScore", {
        id: "minimumFairnessScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.minimumFairnessScore.label", "Minimum Fairness Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.minimumFairnessScore.label", "Minimum Fairness Score"),
          placeholder: "Enter Minimum Fairness Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failureToleranceRatio", {
        id: "failureToleranceRatio",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.failureToleranceRatio.label", "Failure Tolerance Ratio")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.failureToleranceRatio.label", "Failure Tolerance Ratio"),
          placeholder: "Enter Failure Tolerance Ratio",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("updateReason", {
        id: "updateReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.updateReason.label", "Update Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.updateReason.label", "Update Reason"),
          placeholder: "Enter Update Reason",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("lockedByTrainingJobId", {
        id: "lockedByTrainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.lockedByTrainingJobId.label", "Locked By Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.lockedByTrainingJobId.label", "Locked By Training Job Id"),
          placeholder: "Enter Locked By Training Job Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_run_configuration_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_run_configuration_catalog.fields.state.label", "State"),
          placeholder: "Enter State",
          variant: "text",
          filterOperator: "eq",
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
                {isCommandVisible(row.original, "", "state", ["Draft"]) && (
                <DropdownMenuItem>
                  <EditButton variant="ghost" recordItemId={row.original.trainingRunConfigurationId} size="sm" />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="createTrainingJob"
                    recordItemId={row.original.trainingRunConfigurationId}
                    size="sm"
                    query={{
                      federationId: row.original.federationId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.trainingRunConfigurationId} size="sm" />
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
    getRowId: (row) => String(row.trainingRunConfigurationId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "training_run_configuration_catalog_read_model_entity",
        idField: "trainingRunConfigurationId",
        idFields: ["trainingRunConfigurationId"],
        queryFields: ["trainingRunConfigurationId","federationId","featureSchemaId","initialModelId","initialModelName","initialModelVersion","federationName","featureDomain","featureSchemaVersion","initialModelArtifactUri","initialModelRegistryRef","initialModelFormat","initialModelArtifactDigest","initialModelSignatureUri","strategyName","aggregationAlgorithm","maxRounds","minimumNodesPerRound","roundTimeoutSeconds","nodeResponseTimeoutSeconds","localEpochs","batchSize","learningRate","optimizer","lossFunction","gradientClippingNorm","secureAggregationRequired","differentialPrivacyEnabled","dpNoiseMultiplier","dpClipNorm","minimumAccuracy","minimumFairnessScore","failureToleranceRatio","updateReason","lockedByTrainingJobId","state"],
        label: t("resources.training_run_configuration_catalog.label", "Training Run Configuration Catalog"),
        aggregateRoute: "trainingrunconfiguration",
        queryRoute: "trainingrunconfigurationcatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="defineTrainingRunConfiguration" />
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
