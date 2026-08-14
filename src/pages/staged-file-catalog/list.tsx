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

type StagedFileCatalogRecord = {
  stagedFileId: string;
  originalFileName: string;
  contentType?: string;
  sizeBytes?: number;
  purpose: string;
  stagedFileLocation: string;
  checksum?: string;
  state: string;
  stagedAt?: string;
  consumedAt?: string;
  consumedByContext?: string;
  consumedByCommand?: string;
  consumedByCommandId?: string;
  discardedAt?: string;
  discardReason?: string;
  expiresAt?: string;
  expiredAt?: string;
  expirationReason?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: StagedFileCatalogRecord,
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

export const StagedFileCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<StagedFileCatalogRecord>();
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
      columnHelper.accessor("stagedFileId", {
        id: "stagedFileId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.stagedFileId.label", "Staged File Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("originalFileName", {
        id: "originalFileName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.originalFileName.label", "Original File Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("contentType", {
        id: "contentType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.contentType.label", "Content Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("sizeBytes", {
        id: "sizeBytes",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.sizeBytes.label", "Size Bytes")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("purpose", {
        id: "purpose",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.purpose.label", "Purpose")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("stagedFileLocation", {
        id: "stagedFileLocation",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.stagedFileLocation.label", "Staged File Location")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("checksum", {
        id: "checksum",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.checksum.label", "Checksum")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("stagedAt", {
        id: "stagedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.stagedAt.label", "Staged At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("consumedAt", {
        id: "consumedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.consumedAt.label", "Consumed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("consumedByContext", {
        id: "consumedByContext",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.consumedByContext.label", "Consumed By Context")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("consumedByCommand", {
        id: "consumedByCommand",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.consumedByCommand.label", "Consumed By Command")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("consumedByCommandId", {
        id: "consumedByCommandId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.consumedByCommandId.label", "Consumed By Command Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("discardedAt", {
        id: "discardedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.discardedAt.label", "Discarded At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("discardReason", {
        id: "discardReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.discardReason.label", "Discard Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("expiresAt", {
        id: "expiresAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.expiresAt.label", "Expires At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("expiredAt", {
        id: "expiredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.expiredAt.label", "Expired At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("expirationReason", {
        id: "expirationReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.staged_file_catalog.fields.expirationReason.label", "Expiration Reason")} />
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
                {isCommandVisible(row.original, "", "state", ["Staged"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="markStagedFileConsumed"
                    recordItemId={row.original.stagedFileId}
                    size="sm"
                    query={{
                      consumedByContext: row.original.consumedByContext,
                      consumedByCommand: row.original.consumedByCommand,
                      consumedByCommandId: row.original.consumedByCommandId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Staged"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="discardStagedFile"
                    recordItemId={row.original.stagedFileId}
                    size="sm"
                    query={{
                      discardReason: row.original.discardReason,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.stagedFileId} size="sm" />
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
    getRowId: (row) => String(row.stagedFileId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: true,
      meta: {
        tableName: "staged_file_catalog_read_model_entity",
        idField: "stagedFileId",
        idFields: ["stagedFileId"],
        label: t("resources.staged_file_catalog.label", "Staged File Catalog"),
        aggregateRoute: "stagedfile",
        queryRoute: "stagedfilecatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="stageFileUpload" />
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
