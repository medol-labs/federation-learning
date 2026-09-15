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
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";
import type { FeatureDefinition, LabelDefinition } from "@/contexts/domain/value-types";

type DatasetCapabilityRecord = {
  datasetId: string;
  organizationId: string;
  runtimeId?: string;
  featureSchemaId: string;
  features: FeatureDefinition[];
  labels?: LabelDefinition[];
  organizationName?: string;
  featureDomain?: string;
  featureSchemaVersion?: string;
  datasetName: string;
  datasetUsage: string;
  sampleCount?: number;
  featureCount?: number;
  schemaCompatible?: boolean;
  labelCompatible?: boolean;
  qualityScore?: string;
  nonIidScore?: string;
  metadataReportId?: string;
  metadataStatus: string;
  contractStatus: string;
  approvalStatus: string;
  approved: boolean;
  lastProfiledAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: DatasetCapabilityRecord,
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

export const DatasetCapabilityList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<DatasetCapabilityRecord>();
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
      columnHelper.accessor("datasetId", {
        id: "datasetId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.datasetId.label", "Dataset Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.datasetId.label", "Dataset Id"),
          placeholder: "Enter Dataset Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.runtimeId.label", "Runtime Id"),
          placeholder: "Enter Runtime Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("features", {
        id: "features",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.features.label", "Features")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.dataset_capability.fields.features.label", "Features"),
          placeholder: "[\n  {\n    \"featureName\": \"\",\n    \"dataType\": \"\",\n    \"required\": false,\n    \"nullable\": false,\n    \"description\": \"\",\n    \"validationRules\": [],\n    \"defaultValue\": \"\",\n    \"isIdentifier\": false,\n    \"isSensitive\": false,\n    \"encodingStrategy\": \"\",\n    \"featureTags\": []\n  }\n]",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("labels", {
        id: "labels",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.labels.label", "Labels")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.dataset_capability.fields.labels.label", "Labels"),
          placeholder: "[\n  {\n    \"labelName\": \"\",\n    \"dataType\": \"\",\n    \"cardinality\": 0,\n    \"classLabels\": [],\n    \"isMultilabel\": false,\n    \"description\": \"\",\n    \"validationRules\": [],\n    \"defaultValue\": \"\"\n  }\n]",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.featureDomain.label", "Feature Domain"),
          placeholder: "Enter Feature Domain",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.featureSchemaVersion.label", "Feature Schema Version"),
          placeholder: "Enter Feature Schema Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetName", {
        id: "datasetName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.datasetName.label", "Dataset Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.datasetName.label", "Dataset Name"),
          placeholder: "Enter Dataset Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetUsage", {
        id: "datasetUsage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.datasetUsage.label", "Dataset Usage")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.datasetUsage.label", "Dataset Usage"),
          placeholder: "Enter Dataset Usage",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("sampleCount", {
        id: "sampleCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.sampleCount.label", "Sample Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.sampleCount.label", "Sample Count"),
          placeholder: "Enter Sample Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureCount", {
        id: "featureCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.featureCount.label", "Feature Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.featureCount.label", "Feature Count"),
          placeholder: "Enter Feature Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("schemaCompatible", {
        id: "schemaCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.schemaCompatible.label", "Schema Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.schemaCompatible.label", "Schema Compatible"),
          placeholder: "Enter Schema Compatible",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("labelCompatible", {
        id: "labelCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.labelCompatible.label", "Label Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.labelCompatible.label", "Label Compatible"),
          placeholder: "Enter Label Compatible",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("qualityScore", {
        id: "qualityScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.qualityScore.label", "Quality Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.qualityScore.label", "Quality Score"),
          placeholder: "Enter Quality Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nonIidScore", {
        id: "nonIidScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.nonIidScore.label", "Non Iid Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.nonIidScore.label", "Non Iid Score"),
          placeholder: "Enter Non Iid Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("metadataReportId", {
        id: "metadataReportId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.metadataReportId.label", "Metadata Report Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.metadataReportId.label", "Metadata Report Id"),
          placeholder: "Enter Metadata Report Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("metadataStatus", {
        id: "metadataStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.metadataStatus.label", "Metadata Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.metadataStatus.label", "Metadata Status"),
          placeholder: "Enter Metadata Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("contractStatus", {
        id: "contractStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.contractStatus.label", "Contract Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.contractStatus.label", "Contract Status"),
          placeholder: "Enter Contract Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("approvalStatus", {
        id: "approvalStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.approvalStatus.label", "Approval Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.approvalStatus.label", "Approval Status"),
          placeholder: "Enter Approval Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("approved", {
        id: "approved",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.approved.label", "Approved")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.approved.label", "Approved"),
          placeholder: "Enter Approved",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("lastProfiledAt", {
        id: "lastProfiledAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.lastProfiledAt.label", "Last Profiled At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_capability.fields.lastProfiledAt.label", "Last Profiled At"),
          placeholder: "Enter Last Profiled At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
                {isCommandVisible(row.original, "", "contractStatus", ["ContractValidationCompleted"]) && (
                  <CommandButton
                    variant="ghost"
                    command="retryDatasetContractValidation"
                    recordItemId={row.original.datasetId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                      featureSchemaId: row.original.featureSchemaId,
                      datasetName: row.original.datasetName,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "contractStatus", ["ContractValidationCompleted"]) && (
                  <CommandButton
                    variant="ghost"
                    command="rejectDatasetForTraining"
                    recordItemId={row.original.datasetId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                      featureSchemaId: row.original.featureSchemaId,
                      datasetName: row.original.datasetName,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "contractStatus", ["ContractValidationCompleted"]) && (
                  <CommandButton
                    variant="ghost"
                    command="approveDatasetForTraining"
                    recordItemId={row.original.datasetId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                      featureSchemaId: row.original.featureSchemaId,
                      datasetName: row.original.datasetName,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "approvalStatus", ["Approved"]) && (
                  <CommandButton
                    variant="ghost"
                    command="revokeDatasetTrainingApproval"
                    recordItemId={row.original.datasetId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                      featureSchemaId: row.original.featureSchemaId,
                      datasetName: row.original.datasetName,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="configureRuntimeDatasetBinding"
                    recordItemId={row.original.datasetId}
                    size="sm"
                    query={{
                      datasetId: row.original.datasetId,
                      runtimeId: row.original.runtimeId,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.datasetId} size="sm" />
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
    getRowId: (row) => String(row.datasetId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "dataset_capability_read_model_entity",
        idField: "datasetId",
        idFields: ["datasetId"],
        queryFields: ["datasetId","organizationId","runtimeId","featureSchemaId","organizationName","featureDomain","featureSchemaVersion","datasetName","datasetUsage","sampleCount","featureCount","schemaCompatible","labelCompatible","qualityScore","nonIidScore","metadataReportId","metadataStatus","contractStatus","approvalStatus","approved","lastProfiledAt"],
        label: t("resources.dataset_capability.label", "Dataset Capability"),
        aggregateRoute: "dataset",
        queryRoute: "datasetcapability",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="declareDataset" />
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
