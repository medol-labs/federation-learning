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

type RuntimeDatasetMetadataCatalogRecord = {
  metadataReportId: string;
  datasetId: string;
  organizationId: string;
  runtimeId: string;
  featureSchemaId: string;
  datasetName?: string;
  organizationName?: string;
  featureDomain?: string;
  featureSchemaVersion?: string;
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
      columnHelper.accessor("metadataReportId", {
        id: "metadataReportId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.metadataReportId.label", "Metadata Report Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetId", {
        id: "datasetId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.datasetId.label", "Dataset Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetName", {
        id: "datasetName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.datasetName.label", "Dataset Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("sampleCount", {
        id: "sampleCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.sampleCount.label", "Sample Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureCount", {
        id: "featureCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.featureCount.label", "Feature Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("schemaCompatible", {
        id: "schemaCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.schemaCompatible.label", "Schema Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("labelCompatible", {
        id: "labelCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.labelCompatible.label", "Label Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("missingValueRate", {
        id: "missingValueRate",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.missingValueRate.label", "Missing Value Rate")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("duplicateRate", {
        id: "duplicateRate",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.duplicateRate.label", "Duplicate Rate")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("qualityScore", {
        id: "qualityScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.qualityScore.label", "Quality Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nonIidScore", {
        id: "nonIidScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.nonIidScore.label", "Non Iid Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("classBalanceScore", {
        id: "classBalanceScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.classBalanceScore.label", "Class Balance Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("profilingStatus", {
        id: "profilingStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.profilingStatus.label", "Profiling Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failureReason", {
        id: "failureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.failureReason.label", "Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("profiledAt", {
        id: "profiledAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_dataset_metadata_catalog.fields.profiledAt.label", "Profiled At")} />
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
                    command="configureRuntimeDatasetBinding"
                    recordItemId={row.original.metadataReportId}
                    size="sm"
                    query={{
                      datasetId: row.original.datasetId,
                      organizationId: row.original.organizationId,
                      runtimeId: row.original.runtimeId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.metadataReportId} size="sm" />
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
    getRowId: (row) => String(row.metadataReportId),
    refineCoreProps: {
      dataProviderName: "flruntime-agent",
      syncWithLocation: true,
      meta: {
        tableName: "runtime_dataset_metadata_catalog_read_model_entity",
        idField: "metadataReportId",
        idFields: ["metadataReportId"],
        label: t("resources.runtime_dataset_metadata_catalog.label", "Runtime Dataset Metadata Catalog"),
        aggregateRoute: "runtimedatasetmetadata",
        queryRoute: "runtimedatasetmetadatacatalog",
        dataProviderName: "flruntime-agent",
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
