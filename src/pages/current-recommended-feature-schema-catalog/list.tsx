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

type CurrentRecommendedFeatureSchemaCatalogRecord = {
  featureDomain: string;
  recommendedFeatureSchemaId: string;
  recommendedVersion: string;
  recommendedAt: string;
  recommendationNote?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: CurrentRecommendedFeatureSchemaCatalogRecord,
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

export const CurrentRecommendedFeatureSchemaCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<CurrentRecommendedFeatureSchemaCatalogRecord>();
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
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.current_recommended_feature_schema_catalog.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("recommendedFeatureSchemaId", {
        id: "recommendedFeatureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.current_recommended_feature_schema_catalog.fields.recommendedFeatureSchemaId.label", "Recommended Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("recommendedVersion", {
        id: "recommendedVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.current_recommended_feature_schema_catalog.fields.recommendedVersion.label", "Recommended Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("recommendedAt", {
        id: "recommendedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.current_recommended_feature_schema_catalog.fields.recommendedAt.label", "Recommended At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("recommendationNote", {
        id: "recommendationNote",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.current_recommended_feature_schema_catalog.fields.recommendationNote.label", "Recommendation Note")} />
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
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.featureDomain} size="sm" />
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
    getRowId: (row) => String(row.featureDomain),
    refineCoreProps: {
      dataProviderName: "flplatform-backend",
      syncWithLocation: true,
      meta: {
        tableName: "current_recommended_feature_schema_catalog_read_model_entity",
        idField: "featureDomain",
        idFields: ["featureDomain"],
        label: t("resources.current_recommended_feature_schema_catalog.label", "Current Recommended Feature Schema Catalog"),
        aggregateRoute: "featureschema",
        queryRoute: "currentrecommendedfeatureschemacatalog",
        dataProviderName: "flplatform-backend",
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
        <ListToolbar table={table.reactTable} />
      </RefineDataTable>
    </ListView>
  );
};
