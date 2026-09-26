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
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";

type ModelArtifactCatalogRecord = {
  modelId: string;
  modelName: string;
  modelPlugin: string;
  modelVersion: string;
  modelDescription?: string;
  sourceType?: string;
  modelArtifactUri: string;
  modelRegistryRef: string;
  modelFormat: string;
  modelArtifactDigest: string;
  modelSignatureUri?: string;
  modelSizeBytes?: number;
  trainingJobId?: string;
  roundId?: string;
  trainingJobObjective?: string;
  state: "REGISTERED";
  registeredAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: ModelArtifactCatalogRecord,
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

export const ModelArtifactCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<ModelArtifactCatalogRecord>();
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
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelId.label", "Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelId.label", "Model Id"),
          placeholder: "Enter Model Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelName", {
        id: "modelName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelName.label", "Model Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelName.label", "Model Name"),
          placeholder: "Enter Model Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelName",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelPlugin", {
        id: "modelPlugin",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelPlugin.label", "Model Plugin")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelPlugin.label", "Model Plugin"),
          placeholder: "Enter Model Plugin",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelPlugin",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelPlugin",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelVersion", {
        id: "modelVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelVersion.label", "Model Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelVersion.label", "Model Version"),
          placeholder: "Enter Model Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelVersion",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelVersion",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelDescription", {
        id: "modelDescription",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelDescription.label", "Model Description")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelDescription.label", "Model Description"),
          placeholder: "Enter Model Description",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelDescription",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelDescription",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("sourceType", {
        id: "sourceType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.sourceType.label", "Source Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.sourceType.label", "Source Type"),
          placeholder: "Enter Source Type",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:sourceType",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "sourceType",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelArtifactUri", {
        id: "modelArtifactUri",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelArtifactUri.label", "Model Artifact Uri")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelArtifactUri.label", "Model Artifact Uri"),
          placeholder: "Enter Model Artifact Uri",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelArtifactUri",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelArtifactUri",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelRegistryRef", {
        id: "modelRegistryRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelRegistryRef.label", "Model Registry Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelRegistryRef.label", "Model Registry Ref"),
          placeholder: "Enter Model Registry Ref",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelRegistryRef",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelRegistryRef",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelFormat", {
        id: "modelFormat",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelFormat.label", "Model Format")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelFormat.label", "Model Format"),
          placeholder: "Enter Model Format",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelFormat",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelFormat",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelArtifactDigest", {
        id: "modelArtifactDigest",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest"),
          placeholder: "Enter Model Artifact Digest",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelArtifactDigest",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelArtifactDigest",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelSignatureUri", {
        id: "modelSignatureUri",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelSignatureUri.label", "Model Signature Uri")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelSignatureUri.label", "Model Signature Uri"),
          placeholder: "Enter Model Signature Uri",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelSignatureUri",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelSignatureUri",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelSizeBytes", {
        id: "modelSizeBytes",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelSizeBytes.label", "Model Size Bytes")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.modelSizeBytes.label", "Model Size Bytes"),
          placeholder: "Enter Model Size Bytes",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:modelSizeBytes",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "modelSizeBytes",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:trainingJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "trainingJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundId", {
        id: "roundId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.roundId.label", "Round Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.roundId.label", "Round Id"),
          placeholder: "Enter Round Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:roundId",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "roundId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobObjective", {
        id: "trainingJobObjective",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.trainingJobObjective.label", "Training Job Objective")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.trainingJobObjective.label", "Training Job Objective"),
          placeholder: "Enter Training Job Objective",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:trainingJobObjective",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "trainingJobObjective",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Registered", value: "REGISTERED" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("registeredAt", {
        id: "registeredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.registeredAt.label", "Registered At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.model_artifact_catalog.fields.registeredAt.label", "Registered At"),
          placeholder: "Enter Registered At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<ModelArtifactCatalogRecord>(
            frontendComposition,
            "field:model-artifact-catalog:display:registeredAt",
            {
              value: getValue(),
              record: row.original,
              resource: "model-artifact-catalog",
              field: "registeredAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<ModelArtifactCatalogRecord>(
                frontendComposition,
                "row-actions:model-artifact-catalog:list",
                "rowActions.before",
                { resource: "model-artifact-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="downloadModelArtifact"
                    recordItemId={row.original.modelId}
                    size="sm"
                    query={{
                      modelName: row.original.modelName,
                      modelVersion: row.original.modelVersion,
                      modelId: row.original.modelId,
                      modelArtifactUri: row.original.modelArtifactUri,
                      modelFormat: row.original.modelFormat,
                      modelSignatureUri: row.original.modelSignatureUri,
                      trainingJobId: row.original.trainingJobId,
                      roundId: row.original.roundId,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.modelId} size="sm" />
              {renderSlotExtensions<ModelArtifactCatalogRecord>(
                frontendComposition,
                "row-actions:model-artifact-catalog:list",
                "rowActions.after",
                { resource: "model-artifact-catalog", record: row.original },
              )}
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
    getRowId: (row) => String(row.modelId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "model_artifact_catalog_read_model_entity",
        idField: "modelId",
        idFields: ["modelId"],
        queryFields: ["modelId","modelName","modelPlugin","modelVersion","modelDescription","sourceType","modelArtifactUri","modelRegistryRef","modelFormat","modelArtifactDigest","modelSignatureUri","modelSizeBytes","trainingJobId","roundId","trainingJobObjective","state","registeredAt"],
        label: t("resources.model_artifact_catalog.label", "Model Artifact Catalog"),
        aggregateRoute: "modelartifact",
        queryRoute: "modelartifactcatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:model-artifact-catalog:list", "toolbar.before", { resource: "model-artifact-catalog", table })}
        <CommandButton variant="default" command="registerModelArtifact" />
        {renderSlotExtensions(frontendComposition, "toolbar:model-artifact-catalog:list", "toolbar.actions", { resource: "model-artifact-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:model-artifact-catalog:list", "toolbar.after", { resource: "model-artifact-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
