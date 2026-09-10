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

type DatasetReadinessRecord = {
  datasetId: string;
  organizationId: string;
  runtimeId?: string;
  featureSchemaId: string;
  datasetName: string;
  organizationName?: string;
  featureDomain?: string;
  featureSchemaVersion?: string;
  datasetUsage: string;
  metadataStatus: string;
  contractStatus: string;
  approvalStatus: string;
  accessStatus: string;
  runtimeStatus: string;
  overallReadiness: string;
  readyForTraining: boolean;
  canBeSelectedForTraining: boolean;
  readinessScore: number;
  missingRequirements: string[];
  blockingReasons: string[];
  warnings: string[];
  sampleCount?: number;
  featureCount?: number;
  schemaCompatible?: boolean;
  labelCompatible?: boolean;
  qualityScore?: string;
  nonIidScore?: string;
  classBalanceScore?: string;
  metadataReportId?: string;
  datasetAccessValidationId?: string;
  readable?: boolean;
  schemaReadable?: boolean;
  sampleBatchReadable?: boolean;
  lastProfiledAt?: string;
  lastAccessValidatedAt?: string;
  lastRuntimeHeartbeatAt?: string;
  lastUpdatedAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: DatasetReadinessRecord,
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

export const DatasetReadinessList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<DatasetReadinessRecord>();
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
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.datasetId.label", "Dataset Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.datasetId.label", "Dataset Id"),
          placeholder: "Enter Dataset Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.runtimeId.label", "Runtime Id"),
          placeholder: "Enter Runtime Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetName", {
        id: "datasetName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.datasetName.label", "Dataset Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.datasetName.label", "Dataset Name"),
          placeholder: "Enter Dataset Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.featureDomain.label", "Feature Domain"),
          placeholder: "Enter Feature Domain",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.featureSchemaVersion.label", "Feature Schema Version"),
          placeholder: "Enter Feature Schema Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetUsage", {
        id: "datasetUsage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.datasetUsage.label", "Dataset Usage")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.datasetUsage.label", "Dataset Usage"),
          placeholder: "Enter Dataset Usage",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("metadataStatus", {
        id: "metadataStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.metadataStatus.label", "Metadata Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.metadataStatus.label", "Metadata Status"),
          placeholder: "Enter Metadata Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("contractStatus", {
        id: "contractStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.contractStatus.label", "Contract Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.contractStatus.label", "Contract Status"),
          placeholder: "Enter Contract Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("approvalStatus", {
        id: "approvalStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.approvalStatus.label", "Approval Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.approvalStatus.label", "Approval Status"),
          placeholder: "Enter Approval Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("accessStatus", {
        id: "accessStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.accessStatus.label", "Access Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.accessStatus.label", "Access Status"),
          placeholder: "Enter Access Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeStatus", {
        id: "runtimeStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.runtimeStatus.label", "Runtime Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.runtimeStatus.label", "Runtime Status"),
          placeholder: "Enter Runtime Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("overallReadiness", {
        id: "overallReadiness",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.overallReadiness.label", "Overall Readiness")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.overallReadiness.label", "Overall Readiness"),
          placeholder: "Enter Overall Readiness",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("readyForTraining", {
        id: "readyForTraining",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.readyForTraining.label", "Ready For Training")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.readyForTraining.label", "Ready For Training"),
          placeholder: "Enter Ready For Training",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("canBeSelectedForTraining", {
        id: "canBeSelectedForTraining",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.canBeSelectedForTraining.label", "Can Be Selected For Training")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.canBeSelectedForTraining.label", "Can Be Selected For Training"),
          placeholder: "Enter Can Be Selected For Training",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("readinessScore", {
        id: "readinessScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.readinessScore.label", "Readiness Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.readinessScore.label", "Readiness Score"),
          placeholder: "Enter Readiness Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("missingRequirements", {
        id: "missingRequirements",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.missingRequirements.label", "Missing Requirements")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.dataset_readiness.fields.missingRequirements.label", "Missing Requirements"),
          placeholder: "Enter Missing Requirements",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("blockingReasons", {
        id: "blockingReasons",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.blockingReasons.label", "Blocking Reasons")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.dataset_readiness.fields.blockingReasons.label", "Blocking Reasons"),
          placeholder: "Enter Blocking Reasons",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("warnings", {
        id: "warnings",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.warnings.label", "Warnings")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.dataset_readiness.fields.warnings.label", "Warnings"),
          placeholder: "Enter Warnings",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("sampleCount", {
        id: "sampleCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.sampleCount.label", "Sample Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.sampleCount.label", "Sample Count"),
          placeholder: "Enter Sample Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureCount", {
        id: "featureCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.featureCount.label", "Feature Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.featureCount.label", "Feature Count"),
          placeholder: "Enter Feature Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("schemaCompatible", {
        id: "schemaCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.schemaCompatible.label", "Schema Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.schemaCompatible.label", "Schema Compatible"),
          placeholder: "Enter Schema Compatible",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("labelCompatible", {
        id: "labelCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.labelCompatible.label", "Label Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.labelCompatible.label", "Label Compatible"),
          placeholder: "Enter Label Compatible",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("qualityScore", {
        id: "qualityScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.qualityScore.label", "Quality Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.qualityScore.label", "Quality Score"),
          placeholder: "Enter Quality Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nonIidScore", {
        id: "nonIidScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.nonIidScore.label", "Non Iid Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.nonIidScore.label", "Non Iid Score"),
          placeholder: "Enter Non Iid Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("classBalanceScore", {
        id: "classBalanceScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.classBalanceScore.label", "Class Balance Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.classBalanceScore.label", "Class Balance Score"),
          placeholder: "Enter Class Balance Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("metadataReportId", {
        id: "metadataReportId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.metadataReportId.label", "Metadata Report Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.metadataReportId.label", "Metadata Report Id"),
          placeholder: "Enter Metadata Report Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetAccessValidationId", {
        id: "datasetAccessValidationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.datasetAccessValidationId.label", "Dataset Access Validation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.datasetAccessValidationId.label", "Dataset Access Validation Id"),
          placeholder: "Enter Dataset Access Validation Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("readable", {
        id: "readable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.readable.label", "Readable")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.readable.label", "Readable"),
          placeholder: "Enter Readable",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("schemaReadable", {
        id: "schemaReadable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.schemaReadable.label", "Schema Readable")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.schemaReadable.label", "Schema Readable"),
          placeholder: "Enter Schema Readable",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("sampleBatchReadable", {
        id: "sampleBatchReadable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.sampleBatchReadable.label", "Sample Batch Readable")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.sampleBatchReadable.label", "Sample Batch Readable"),
          placeholder: "Enter Sample Batch Readable",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("lastProfiledAt", {
        id: "lastProfiledAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.lastProfiledAt.label", "Last Profiled At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.lastProfiledAt.label", "Last Profiled At"),
          placeholder: "Enter Last Profiled At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("lastAccessValidatedAt", {
        id: "lastAccessValidatedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.lastAccessValidatedAt.label", "Last Access Validated At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.lastAccessValidatedAt.label", "Last Access Validated At"),
          placeholder: "Enter Last Access Validated At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("lastRuntimeHeartbeatAt", {
        id: "lastRuntimeHeartbeatAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.lastRuntimeHeartbeatAt.label", "Last Runtime Heartbeat At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.lastRuntimeHeartbeatAt.label", "Last Runtime Heartbeat At"),
          placeholder: "Enter Last Runtime Heartbeat At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("lastUpdatedAt", {
        id: "lastUpdatedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_readiness.fields.lastUpdatedAt.label", "Last Updated At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dataset_readiness.fields.lastUpdatedAt.label", "Last Updated At"),
          placeholder: "Enter Last Updated At",
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
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="configureRuntimeDatasetBinding"
                    recordItemId={row.original.datasetId}
                    size="sm"
                    query={{
                      datasetId: row.original.datasetId,
                      organizationId: row.original.organizationId,
                      featureSchemaId: row.original.featureSchemaId,
                      organizationName: row.original.organizationName,
                      featureDomain: row.original.featureDomain,
                      featureSchemaVersion: row.original.featureSchemaVersion,
                      datasetName: row.original.datasetName,
                      runtimeId: row.original.runtimeId,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "contractStatus", ["ContractValidationCompleted"]) && (
                  <CommandButton
                    variant="ghost"
                    command="rejectDatasetForTraining"
                    recordItemId={row.original.datasetId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "", "contractStatus", ["ContractValidationCompleted"]) && (
                  <CommandButton
                    variant="ghost"
                    command="approveDatasetForTraining"
                    recordItemId={row.original.datasetId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "", "contractStatus", ["ContractValidationCompleted"]) && (
                  <CommandButton
                    variant="ghost"
                    command="retryDatasetContractValidation"
                    recordItemId={row.original.datasetId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "", "approvalStatus", ["Approved"]) && (
                  <CommandButton
                    variant="ghost"
                    command="revokeDatasetTrainingApproval"
                    recordItemId={row.original.datasetId}
                    size="sm"
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
        tableName: "dataset_readiness_read_model_entity",
        idField: "datasetId",
        idFields: ["datasetId"],
        queryFields: ["datasetId","organizationId","runtimeId","featureSchemaId","datasetName","organizationName","featureDomain","featureSchemaVersion","datasetUsage","metadataStatus","contractStatus","approvalStatus","accessStatus","runtimeStatus","overallReadiness","readyForTraining","canBeSelectedForTraining","readinessScore","sampleCount","featureCount","schemaCompatible","labelCompatible","qualityScore","nonIidScore","classBalanceScore","metadataReportId","datasetAccessValidationId","readable","schemaReadable","sampleBatchReadable","lastProfiledAt","lastAccessValidatedAt","lastRuntimeHeartbeatAt","lastUpdatedAt"],
        label: t("resources.dataset_readiness.label", "Dataset Readiness"),
        aggregateRoute: "dataset",
        queryRoute: "datasetreadiness",
        dataProviderName: "federation-learning-runtime-agent",
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
