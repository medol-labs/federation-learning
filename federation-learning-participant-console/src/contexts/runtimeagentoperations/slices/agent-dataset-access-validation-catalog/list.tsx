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

type AgentDatasetAccessValidationCatalogRecord = {
  datasetAccessValidationId: string;
  runtimeDatasetBindingId: string;
  datasetId: string;
  organizationId: string;
  organizationName?: string;
  featureSchemaId: string;
  featureDomain?: string;
  featureSchemaVersion?: string;
  runtimeId: string;
  datasetName?: string;
  runtimeName?: string;
  readable: boolean;
  schemaReadable: boolean;
  sampleBatchReadable: boolean;
  validationStatus: string;
  failureReason?: string;
  validatedAt: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: AgentDatasetAccessValidationCatalogRecord,
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

export const AgentDatasetAccessValidationCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<AgentDatasetAccessValidationCatalogRecord>();
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
      columnHelper.accessor("datasetAccessValidationId", {
        id: "datasetAccessValidationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.datasetAccessValidationId.label", "Dataset Access Validation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.datasetAccessValidationId.label", "Dataset Access Validation Id"),
          placeholder: "Enter Dataset Access Validation Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:datasetAccessValidationId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "datasetAccessValidationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeDatasetBindingId", {
        id: "runtimeDatasetBindingId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id"),
          placeholder: "Enter Runtime Dataset Binding Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:runtimeDatasetBindingId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "runtimeDatasetBindingId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetId", {
        id: "datasetId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.datasetId.label", "Dataset Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.datasetId.label", "Dataset Id"),
          placeholder: "Enter Dataset Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:datasetId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "datasetId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:organizationId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "organizationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:organizationName",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "organizationName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:featureSchemaId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "featureSchemaId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.featureDomain.label", "Feature Domain"),
          placeholder: "Enter Feature Domain",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:featureDomain",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "featureDomain",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.featureSchemaVersion.label", "Feature Schema Version"),
          placeholder: "Enter Feature Schema Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:featureSchemaVersion",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "featureSchemaVersion",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.runtimeId.label", "Runtime Id"),
          placeholder: "Enter Runtime Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:runtimeId",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "runtimeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetName", {
        id: "datasetName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.datasetName.label", "Dataset Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.datasetName.label", "Dataset Name"),
          placeholder: "Enter Dataset Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:datasetName",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "datasetName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeName", {
        id: "runtimeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.runtimeName.label", "Runtime Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.runtimeName.label", "Runtime Name"),
          placeholder: "Enter Runtime Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:runtimeName",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "runtimeName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("readable", {
        id: "readable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.readable.label", "Readable")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.readable.label", "Readable"),
          placeholder: "Enter Readable",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:readable",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "readable",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("schemaReadable", {
        id: "schemaReadable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.schemaReadable.label", "Schema Readable")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.schemaReadable.label", "Schema Readable"),
          placeholder: "Enter Schema Readable",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:schemaReadable",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "schemaReadable",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("sampleBatchReadable", {
        id: "sampleBatchReadable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.sampleBatchReadable.label", "Sample Batch Readable")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.sampleBatchReadable.label", "Sample Batch Readable"),
          placeholder: "Enter Sample Batch Readable",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:sampleBatchReadable",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "sampleBatchReadable",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("validationStatus", {
        id: "validationStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.validationStatus.label", "Validation Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.validationStatus.label", "Validation Status"),
          placeholder: "Enter Validation Status",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:validationStatus",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "validationStatus",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failureReason", {
        id: "failureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.failureReason.label", "Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.failureReason.label", "Failure Reason"),
          placeholder: "Enter Failure Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:failureReason",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "failureReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("validatedAt", {
        id: "validatedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.agent_dataset_access_validation_catalog.fields.validatedAt.label", "Validated At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.agent_dataset_access_validation_catalog.fields.validatedAt.label", "Validated At"),
          placeholder: "Enter Validated At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<AgentDatasetAccessValidationCatalogRecord>(
            frontendComposition,
            "field:agent-dataset-access-validation-catalog:display:validatedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "agent-dataset-access-validation-catalog",
              field: "validatedAt",
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
              {renderSlotExtensions<AgentDatasetAccessValidationCatalogRecord>(
                frontendComposition,
                "row-actions:agent-dataset-access-validation-catalog:list",
                "rowActions.before",
                { resource: "agent-dataset-access-validation-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.datasetAccessValidationId} size="sm" />
              {renderSlotExtensions<AgentDatasetAccessValidationCatalogRecord>(
                frontendComposition,
                "row-actions:agent-dataset-access-validation-catalog:list",
                "rowActions.after",
                { resource: "agent-dataset-access-validation-catalog", record: row.original },
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
    getRowId: (row) => String(row.datasetAccessValidationId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "agent_dataset_access_validation_catalog_read_model_entity",
        idField: "datasetAccessValidationId",
        idFields: ["datasetAccessValidationId"],
        queryFields: ["datasetAccessValidationId","runtimeDatasetBindingId","datasetId","organizationId","organizationName","featureSchemaId","featureDomain","featureSchemaVersion","runtimeId","datasetName","runtimeName","readable","schemaReadable","sampleBatchReadable","validationStatus","failureReason","validatedAt"],
        label: t("resources.agent_dataset_access_validation_catalog.label", "Agent Dataset Access Validation Catalog"),
        aggregateRoute: "agentdatasetaccessvalidation",
        queryRoute: "agentdatasetaccessvalidationcatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:agent-dataset-access-validation-catalog:list", "toolbar.before", { resource: "agent-dataset-access-validation-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:agent-dataset-access-validation-catalog:list", "toolbar.actions", { resource: "agent-dataset-access-validation-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:agent-dataset-access-validation-catalog:list", "toolbar.after", { resource: "agent-dataset-access-validation-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
