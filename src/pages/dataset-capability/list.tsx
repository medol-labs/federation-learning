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

type DatasetCapabilityRecord = {
  datasetId: string;
  organizationId: string;
  runtimeId?: string;
  featureSchemaId: string;
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
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetName", {
        id: "datasetName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.datasetName.label", "Dataset Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetUsage", {
        id: "datasetUsage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.datasetUsage.label", "Dataset Usage")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("sampleCount", {
        id: "sampleCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.sampleCount.label", "Sample Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureCount", {
        id: "featureCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.featureCount.label", "Feature Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("schemaCompatible", {
        id: "schemaCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.schemaCompatible.label", "Schema Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("labelCompatible", {
        id: "labelCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.labelCompatible.label", "Label Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("qualityScore", {
        id: "qualityScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.qualityScore.label", "Quality Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nonIidScore", {
        id: "nonIidScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.nonIidScore.label", "Non Iid Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("metadataReportId", {
        id: "metadataReportId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.metadataReportId.label", "Metadata Report Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("metadataStatus", {
        id: "metadataStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.metadataStatus.label", "Metadata Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("approvalStatus", {
        id: "approvalStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.approvalStatus.label", "Approval Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("approved", {
        id: "approved",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.approved.label", "Approved")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("lastProfiledAt", {
        id: "lastProfiledAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dataset_capability.fields.lastProfiledAt.label", "Last Profiled At")} />
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
                    recordItemId={row.original.datasetId}
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
                  <ShowButton variant="ghost" recordItemId={row.original.datasetId} size="sm" />
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
    getRowId: (row) => String(row.datasetId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: true,
      meta: {
        tableName: "dataset_capability_read_model_entity",
        idField: "datasetId",
        idFields: ["datasetId"],
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
