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

type ModelArtifactCatalogRecord = {
  modelVersionId: string;
  modelArtifactRef: string;
  modelRepositoryRef: string;
  modelFormat: string;
  modelHash: string;
  modelSignatureRef?: string;
  modelSizeBytes?: number;
  sourceType?: string;
  trainingJobId?: string;
  roundId?: string;
  trainingJobObjective?: string;
  state: string;
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
      columnHelper.accessor("modelVersionId", {
        id: "modelVersionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelVersionId.label", "Model Version Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelArtifactRef", {
        id: "modelArtifactRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelArtifactRef.label", "Model Artifact Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelRepositoryRef", {
        id: "modelRepositoryRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelRepositoryRef.label", "Model Repository Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelFormat", {
        id: "modelFormat",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelFormat.label", "Model Format")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelHash", {
        id: "modelHash",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelHash.label", "Model Hash")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelSignatureRef", {
        id: "modelSignatureRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelSignatureRef.label", "Model Signature Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelSizeBytes", {
        id: "modelSizeBytes",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.modelSizeBytes.label", "Model Size Bytes")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("sourceType", {
        id: "sourceType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.sourceType.label", "Source Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundId", {
        id: "roundId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.roundId.label", "Round Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobObjective", {
        id: "trainingJobObjective",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.trainingJobObjective.label", "Training Job Objective")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("registeredAt", {
        id: "registeredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.model_artifact_catalog.fields.registeredAt.label", "Registered At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
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
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="submitGlobalModelEvaluation"
                    recordItemId={row.original.modelVersionId}
                    size="sm"
                    query={{
                      roundId: row.original.roundId,
                      modelFormat: row.original.modelFormat,
                      modelHash: row.original.modelHash,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.modelVersionId} size="sm" />
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
    getRowId: (row) => String(row.modelVersionId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: true,
      meta: {
        tableName: "model_artifact_catalog_read_model_entity",
        idField: "modelVersionId",
        idFields: ["modelVersionId"],
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
        <CommandButton variant="default" command="registerModelArtifact" />
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar table={table.reactTable} />
      </RefineDataTable>
    </ListView>
  );
};
