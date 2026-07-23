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
import type { FeatureDefinition, LabelDefinition } from "@/domain/value-types";

type FeatureSchemaCatalogRecord = {
  featureSchemaId: string;
  featureDomain: string;
  version: string;
  dataModality: string;
  features: FeatureDefinition[];
  labels?: LabelDefinition[];
  featureCount: number;
  schemaStatus: string;
  supersededByFeatureSchemaId?: string;
  recommendedForDomain: boolean;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: FeatureSchemaCatalogRecord,
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

export const FeatureSchemaCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<FeatureSchemaCatalogRecord>();
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
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("version", {
        id: "version",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.version.label", "Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dataModality", {
        id: "dataModality",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.dataModality.label", "Data Modality")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("features", {
        id: "features",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.features.label", "Features")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("labels", {
        id: "labels",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.labels.label", "Labels")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureCount", {
        id: "featureCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.featureCount.label", "Feature Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("schemaStatus", {
        id: "schemaStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.schemaStatus.label", "Schema Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("supersededByFeatureSchemaId", {
        id: "supersededByFeatureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.supersededByFeatureSchemaId.label", "Superseded By Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("recommendedForDomain", {
        id: "recommendedForDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.recommendedForDomain.label", "Recommended For Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
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
                    command="supersedeFeatureSchemaVersion"
                    recordItemId={row.original.featureSchemaId}
                    size="sm"
                    query={{
                      supersededByFeatureSchemaId: row.original.supersededByFeatureSchemaId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="declareDataset"
                    recordItemId={row.original.featureSchemaId}
                    size="sm"
                    query={{
                      featureSchemaId: row.original.featureSchemaId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.featureSchemaId} size="sm" />
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
    getRowId: (row) => String(row.featureSchemaId),
    refineCoreProps: {
      dataProviderName: "flplatform-backend",
      syncWithLocation: true,
      meta: {
        tableName: "feature_schema_catalog_read_model_entity",
        idField: "featureSchemaId",
        idFields: ["featureSchemaId"],
        label: t("resources.feature_schema_catalog.label", "Feature Schema Catalog"),
        aggregateRoute: "featureschema",
        queryRoute: "featureschemacatalog",
        dataProviderName: "flplatform-backend",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="defineFeatureSchema" />
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar table={table.reactTable} />
      </RefineDataTable>
    </ListView>
  );
};
