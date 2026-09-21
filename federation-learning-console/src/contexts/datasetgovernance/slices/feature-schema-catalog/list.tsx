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
import type { FeatureDefinition, LabelDefinition } from "@/contexts/domain/value-types";

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
        meta: {
          label: t("resources.feature_schema_catalog.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:featureSchemaId",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "featureSchemaId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.feature_schema_catalog.fields.featureDomain.label", "Feature Domain"),
          placeholder: "Enter Feature Domain",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:featureDomain",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "featureDomain",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("version", {
        id: "version",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.version.label", "Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.feature_schema_catalog.fields.version.label", "Version"),
          placeholder: "Enter Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:version",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "version",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dataModality", {
        id: "dataModality",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.dataModality.label", "Data Modality")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.feature_schema_catalog.fields.dataModality.label", "Data Modality"),
          placeholder: "Enter Data Modality",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:dataModality",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "dataModality",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("features", {
        id: "features",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.features.label", "Features")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.feature_schema_catalog.fields.features.label", "Features"),
          placeholder: "[\n  {\n    \"featureName\": \"\",\n    \"dataType\": \"\",\n    \"required\": false,\n    \"nullable\": false,\n    \"description\": \"\",\n    \"validationRules\": [],\n    \"defaultValue\": \"\",\n    \"isIdentifier\": false,\n    \"isSensitive\": false,\n    \"encodingStrategy\": \"\",\n    \"featureTags\": []\n  }\n]",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:features",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "features",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("labels", {
        id: "labels",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.labels.label", "Labels")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.feature_schema_catalog.fields.labels.label", "Labels"),
          placeholder: "[\n  {\n    \"labelName\": \"\",\n    \"dataType\": \"\",\n    \"cardinality\": 0,\n    \"classLabels\": [],\n    \"isMultilabel\": false,\n    \"description\": \"\",\n    \"validationRules\": [],\n    \"defaultValue\": \"\"\n  }\n]",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:labels",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "labels",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureCount", {
        id: "featureCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.featureCount.label", "Feature Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.feature_schema_catalog.fields.featureCount.label", "Feature Count"),
          placeholder: "Enter Feature Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:featureCount",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "featureCount",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("schemaStatus", {
        id: "schemaStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.schemaStatus.label", "Schema Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.feature_schema_catalog.fields.schemaStatus.label", "Schema Status"),
          placeholder: "Enter Schema Status",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:schemaStatus",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "schemaStatus",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("supersededByFeatureSchemaId", {
        id: "supersededByFeatureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.supersededByFeatureSchemaId.label", "Superseded By Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.feature_schema_catalog.fields.supersededByFeatureSchemaId.label", "Superseded By Feature Schema Id"),
          placeholder: "Enter Superseded By Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:supersededByFeatureSchemaId",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "supersededByFeatureSchemaId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("recommendedForDomain", {
        id: "recommendedForDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.feature_schema_catalog.fields.recommendedForDomain.label", "Recommended For Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.feature_schema_catalog.fields.recommendedForDomain.label", "Recommended For Domain"),
          placeholder: "Enter Recommended For Domain",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<FeatureSchemaCatalogRecord>(
            frontendComposition,
            "field:feature-schema-catalog:display:recommendedForDomain",
            {
              value: getValue(),
              record: row.original,
              resource: "feature-schema-catalog",
              field: "recommendedForDomain",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<FeatureSchemaCatalogRecord>(
                frontendComposition,
                "row-actions:feature-schema-catalog:list",
                "rowActions.before",
                { resource: "feature-schema-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "schemaStatus", ["Draft"]) && (
                  <CommandButton
                    variant="ghost"
                    command="publishFeatureSchema"
                    recordItemId={row.original.featureSchemaId}
                    size="sm"
                    query={{
                      featureDomain: row.original.featureDomain,
                      version: row.original.version,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "schemaStatus", ["Published"]) && (
                  <CommandButton
                    variant="ghost"
                    command="deprecateFeatureSchema"
                    recordItemId={row.original.featureSchemaId}
                    size="sm"
                    query={{
                      featureDomain: row.original.featureDomain,
                      version: row.original.version,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "schemaStatus", ["Deprecated"]) && (
                  <CommandButton
                    variant="ghost"
                    command="retireFeatureSchema"
                    recordItemId={row.original.featureSchemaId}
                    size="sm"
                    query={{
                      featureDomain: row.original.featureDomain,
                      version: row.original.version,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="supersedeFeatureSchemaVersion"
                    recordItemId={row.original.featureSchemaId}
                    size="sm"
                    query={{
                      supersededByFeatureSchemaId: row.original.supersededByFeatureSchemaId,
                      featureDomain: row.original.featureDomain,
                      version: row.original.version,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="markCurrentRecommendedFeatureSchemaVersion"
                    recordItemId={row.original.featureSchemaId}
                    size="sm"
                    query={{
                      featureDomain: row.original.featureDomain,
                      version: row.original.version,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.featureSchemaId} size="sm" />
              {renderSlotExtensions<FeatureSchemaCatalogRecord>(
                frontendComposition,
                "row-actions:feature-schema-catalog:list",
                "rowActions.after",
                { resource: "feature-schema-catalog", record: row.original },
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
    getRowId: (row) => String(row.featureSchemaId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "feature_schema_catalog_read_model_entity",
        idField: "featureSchemaId",
        idFields: ["featureSchemaId"],
        queryFields: ["featureSchemaId","featureDomain","version","dataModality","featureCount","schemaStatus","supersededByFeatureSchemaId","recommendedForDomain"],
        label: t("resources.feature_schema_catalog.label", "Feature Schema Catalog"),
        aggregateRoute: "featureschema",
        queryRoute: "featureschemacatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:feature-schema-catalog:list", "toolbar.before", { resource: "feature-schema-catalog", table })}
        <CommandButton variant="default" command="defineFeatureSchema" />
        {renderSlotExtensions(frontendComposition, "toolbar:feature-schema-catalog:list", "toolbar.actions", { resource: "feature-schema-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:feature-schema-catalog:list", "toolbar.after", { resource: "feature-schema-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
