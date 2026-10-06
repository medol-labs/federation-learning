// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

import { frontendComposition } from "@/app/composition/composition.resolved";
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
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";

type ModelCatalogRecord = {
  modelId: string;
  trainingJobId: string;
  finalRoundId: string;
  modelArtifactId: string;
  trainingJobObjective?: string;
  modelArtifactDigest: string;
  evaluationReportId: string;
  finalGlobalAccuracy: string;
  state: "Candidate" | "EvaluationPackaged" | "Approved" | "Production" | "RolledBack" | "Retired";
  releaseChannel?: string;
  productionStage?: string;
  experimentId?: string;
  hyperparameterSnapshotId?: string;
  reproducibilityManifestId?: string;
  modelCardId?: string;
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

const formatValue = (
  value: unknown,
  t: ReturnType<typeof useTranslate>,
  dictionaryLabel: ReturnType<typeof useDictionaryTranslation>["dictionaryLabel"],
  options?: Array<{ label: string; value: string }>,
  dictionaryCode?: string,
): string => {
  if (value === null || value === undefined || value === "") return "-";
  if (Array.isArray(value)) {
    const formatted: string[] = value.map((item) => formatValue(item, t, dictionaryLabel, options, dictionaryCode)).filter((item) => item !== "-");
    return formatted.length > 0 ? formatted.join(", ") : "-";
  }
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  const stringValue = String(value);
  if (dictionaryCode) return dictionaryLabel(dictionaryCode, stringValue, t(`dictionaries.${dictionaryCode}.${stringValue}`, stringValue));
  return options?.find((option) => option.value === stringValue)?.label ?? stringValue;
};

export const ModelCatalogList = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:modelId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "modelId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:trainingJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "trainingJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:finalRoundId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "finalRoundId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:modelArtifactId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "modelArtifactId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:trainingJobObjective",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "trainingJobObjective",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:modelArtifactDigest",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "modelArtifactDigest",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:evaluationReportId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "evaluationReportId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:finalGlobalAccuracy",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "finalGlobalAccuracy",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.model_catalog.fields.state.options.Candidate", "Candidate"), value: "Candidate" },
            { label: t("resources.model_catalog.fields.state.options.EvaluationPackaged", "Evaluation Packaged"), value: "EvaluationPackaged" },
            { label: t("resources.model_catalog.fields.state.options.Approved", "Approved"), value: "Approved" },
            { label: t("resources.model_catalog.fields.state.options.Production", "Production"), value: "Production" },
            { label: t("resources.model_catalog.fields.state.options.RolledBack", "Rolled Back"), value: "RolledBack" },
            { label: t("resources.model_catalog.fields.state.options.Retired", "Retired"), value: "Retired" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.model_catalog.fields.state.options.Candidate", "Candidate"), value: "Candidate" },
            { label: t("resources.model_catalog.fields.state.options.EvaluationPackaged", "Evaluation Packaged"), value: "EvaluationPackaged" },
            { label: t("resources.model_catalog.fields.state.options.Approved", "Approved"), value: "Approved" },
            { label: t("resources.model_catalog.fields.state.options.Production", "Production"), value: "Production" },
            { label: t("resources.model_catalog.fields.state.options.RolledBack", "Rolled Back"), value: "RolledBack" },
            { label: t("resources.model_catalog.fields.state.options.Retired", "Retired"), value: "Retired" },
          ]),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:releaseChannel",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "releaseChannel",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, undefined, "MODEL_RELEASE_CHANNEL"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:productionStage",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "productionStage",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, undefined, "MODEL_PRODUCTION_STAGE"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:experimentId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "experimentId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:hyperparameterSnapshotId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "hyperparameterSnapshotId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:reproducibilityManifestId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "reproducibilityManifestId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
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
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelCatalogRecord>(
            frontendComposition,
            "field:model-catalog:display:modelCardId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-catalog",
              field: "modelCardId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<ModelCatalogRecord>(
                frontendComposition,
                "row-actions:model-catalog:list",
                "rowActions.before",
                { resource: "model-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "state", ["Candidate"]) && (
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
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "state", ["EvaluationPackaged"]) && (
                  <CommandButton
                    variant="ghost"
                    command="approveModel"
                    recordItemId={row.original.modelId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "", "state", ["Approved"]) && (
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
                )}
                {isCommandVisible(row.original, "", "state", ["Production"]) && (
                  <CommandButton
                    variant="ghost"
                    command="rollbackModel"
                    recordItemId={row.original.modelId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "", "state", ["Production"]) && (
                  <CommandButton
                    variant="ghost"
                    command="retireModel"
                    recordItemId={row.original.modelId}
                    size="sm"
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.modelId} size="sm" />
              {renderSlotExtensions<ModelCatalogRecord>(
                frontendComposition,
                "row-actions:model-catalog:list",
                "rowActions.after",
                { resource: "model-catalog", record: row.original },
              )}
            </RowActionMenu>
          </div>
        ),
        enableSorting: false,
        size: 32,
      }),
    ];
  }, [dictionaryLabel, t]);

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
        queryFields: ["modelId","trainingJobId","finalRoundId","modelArtifactId","trainingJobObjective","modelArtifactDigest","evaluationReportId","finalGlobalAccuracy","state","releaseChannel","productionStage","experimentId","hyperparameterSnapshotId","reproducibilityManifestId","modelCardId"],
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
        {renderSlotExtensions(frontendComposition, "toolbar:model-catalog:list", "toolbar.before", { resource: "model-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:model-catalog:list", "toolbar.actions", { resource: "model-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:model-catalog:list", "toolbar.after", { resource: "model-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
