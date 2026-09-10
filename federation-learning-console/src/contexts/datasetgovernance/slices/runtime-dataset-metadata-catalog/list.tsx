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

type RuntimeDatasetMetadataCatalogRecord = {
  runtimeDatasetBindingId: string;
  metadataReportId: string;
  datasetId: string;
  organizationId: string;
  organizationName?: string;
  runtimeId: string;
  runtimeName?: string;
  featureSchemaId: string;
  featureDomain?: string;
  featureSchemaVersion?: string;
  datasetName?: string;
  sampleCount: number;
  featureCount: number;
  schemaCompatible?: boolean;
  labelCompatible?: boolean;
  missingValueRate?: string;
  duplicateRate?: string;
  qualityScore?: string;
  nonIidScore?: string;
  classBalanceScore?: string;
  profilingStatus: string;
  failureReason?: string;
  profiledAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeDatasetMetadataCatalogRecord,
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

export const RuntimeDatasetMetadataCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeDatasetMetadataCatalogRecord>();
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
      columnHelper.accessor("runtimeDatasetBindingId", {
        id: "runtimeDatasetBindingId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id"),
          placeholder: "Enter Runtime Dataset Binding Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("metadataReportId", {
        id: "metadataReportId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.metadataReportId.label", "Metadata Report Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.metadataReportId.label", "Metadata Report Id"),
          placeholder: "Enter Metadata Report Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetId", {
        id: "datasetId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.datasetId.label", "Dataset Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.datasetId.label", "Dataset Id"),
          placeholder: "Enter Dataset Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.runtimeId.label", "Runtime Id"),
          placeholder: "Enter Runtime Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeName", {
        id: "runtimeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.runtimeName.label", "Runtime Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.runtimeName.label", "Runtime Name"),
          placeholder: "Enter Runtime Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.featureDomain.label", "Feature Domain"),
          placeholder: "Enter Feature Domain",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaVersion.label", "Feature Schema Version"),
          placeholder: "Enter Feature Schema Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetName", {
        id: "datasetName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.datasetName.label", "Dataset Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.datasetName.label", "Dataset Name"),
          placeholder: "Enter Dataset Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("sampleCount", {
        id: "sampleCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.sampleCount.label", "Sample Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.sampleCount.label", "Sample Count"),
          placeholder: "Enter Sample Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureCount", {
        id: "featureCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.featureCount.label", "Feature Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.featureCount.label", "Feature Count"),
          placeholder: "Enter Feature Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("schemaCompatible", {
        id: "schemaCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.schemaCompatible.label", "Schema Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.schemaCompatible.label", "Schema Compatible"),
          placeholder: "Enter Schema Compatible",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("labelCompatible", {
        id: "labelCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.labelCompatible.label", "Label Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.labelCompatible.label", "Label Compatible"),
          placeholder: "Enter Label Compatible",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("missingValueRate", {
        id: "missingValueRate",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.missingValueRate.label", "Missing Value Rate")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.missingValueRate.label", "Missing Value Rate"),
          placeholder: "Enter Missing Value Rate",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("duplicateRate", {
        id: "duplicateRate",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.duplicateRate.label", "Duplicate Rate")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.duplicateRate.label", "Duplicate Rate"),
          placeholder: "Enter Duplicate Rate",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("qualityScore", {
        id: "qualityScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.qualityScore.label", "Quality Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.qualityScore.label", "Quality Score"),
          placeholder: "Enter Quality Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nonIidScore", {
        id: "nonIidScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.nonIidScore.label", "Non Iid Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.nonIidScore.label", "Non Iid Score"),
          placeholder: "Enter Non Iid Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("classBalanceScore", {
        id: "classBalanceScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.classBalanceScore.label", "Class Balance Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.classBalanceScore.label", "Class Balance Score"),
          placeholder: "Enter Class Balance Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("profilingStatus", {
        id: "profilingStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.profilingStatus.label", "Profiling Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.profilingStatus.label", "Profiling Status"),
          placeholder: "Enter Profiling Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failureReason", {
        id: "failureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.failureReason.label", "Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.failureReason.label", "Failure Reason"),
          placeholder: "Enter Failure Reason",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("profiledAt", {
        id: "profiledAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.profiledAt.label", "Profiled At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_dataset_metadata_catalog.fields.profiledAt.label", "Profiled At"),
          placeholder: "Enter Profiled At",
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
                {isCommandVisible(row.original, "", "profilingStatus", ["Reported"]) && (
                  <CommandButton
                    variant="ghost"
                    command="reprofileAgentDataset"
                    recordItemId={row.original.runtimeDatasetBindingId}
                    size="sm"
                    query={{
                      runtimeDatasetBindingId: row.original.runtimeDatasetBindingId,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.runtimeDatasetBindingId} size="sm" />
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
    getRowId: (row) => String(row.runtimeDatasetBindingId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_dataset_metadata_catalog_read_model_entity",
        idField: "runtimeDatasetBindingId",
        idFields: ["runtimeDatasetBindingId"],
        queryFields: ["runtimeDatasetBindingId","metadataReportId","datasetId","organizationId","organizationName","runtimeId","runtimeName","featureSchemaId","featureDomain","featureSchemaVersion","datasetName","sampleCount","featureCount","schemaCompatible","labelCompatible","missingValueRate","duplicateRate","qualityScore","nonIidScore","classBalanceScore","profilingStatus","failureReason","profiledAt"],
        label: t("resources.runtime_dataset_metadata_catalog.label", "Runtime Dataset Metadata Catalog"),
        aggregateRoute: "runtimedatasetmetadata",
        queryRoute: "runtimedatasetmetadatacatalog",
        dataProviderName: "federation-learning-platform",
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
