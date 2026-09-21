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

type UploadedFileCatalogRecord = {
  fileId: string;
  originalFileName: string;
  contentType?: string;
  sizeBytes?: number;
  purpose: string;
  fileLocation: string;
  checksum?: string;
  state: "AVAILABLE" | "REFERENCED" | "DISCARDED" | "EXPIRED";
  uploadedAt?: string;
  referencedAt?: string;
  referencedByContext?: string;
  referencedByCommand?: string;
  referencedByCommandId?: string;
  discardedAt?: string;
  discardReason?: string;
  expiresAt?: string;
  expiredAt?: string;
  expirationReason?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: UploadedFileCatalogRecord,
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

export const UploadedFileCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<UploadedFileCatalogRecord>();
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
      columnHelper.accessor("fileId", {
        id: "fileId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.fileId.label", "File Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.fileId.label", "File Id"),
          placeholder: "Enter File Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:fileId",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "fileId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("originalFileName", {
        id: "originalFileName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.originalFileName.label", "Original File Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.originalFileName.label", "Original File Name"),
          placeholder: "Enter Original File Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:originalFileName",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "originalFileName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("contentType", {
        id: "contentType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.contentType.label", "Content Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.contentType.label", "Content Type"),
          placeholder: "Enter Content Type",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:contentType",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "contentType",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("sizeBytes", {
        id: "sizeBytes",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.sizeBytes.label", "Size Bytes")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.sizeBytes.label", "Size Bytes"),
          placeholder: "Enter Size Bytes",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:sizeBytes",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "sizeBytes",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("purpose", {
        id: "purpose",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.purpose.label", "Purpose")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.purpose.label", "Purpose"),
          placeholder: "Enter Purpose",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:purpose",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "purpose",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("fileLocation", {
        id: "fileLocation",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.fileLocation.label", "File Location")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.fileLocation.label", "File Location"),
          placeholder: "Enter File Location",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:fileLocation",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "fileLocation",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("checksum", {
        id: "checksum",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.checksum.label", "Checksum")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.checksum.label", "Checksum"),
          placeholder: "Enter Checksum",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:checksum",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "checksum",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Available", value: "AVAILABLE" },
            { label: "Referenced", value: "REFERENCED" },
            { label: "Discarded", value: "DISCARDED" },
            { label: "Expired", value: "EXPIRED" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("uploadedAt", {
        id: "uploadedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.uploadedAt.label", "Uploaded At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.uploadedAt.label", "Uploaded At"),
          placeholder: "Enter Uploaded At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:uploadedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "uploadedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("referencedAt", {
        id: "referencedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.referencedAt.label", "Referenced At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.referencedAt.label", "Referenced At"),
          placeholder: "Enter Referenced At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:referencedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "referencedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("referencedByContext", {
        id: "referencedByContext",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.referencedByContext.label", "Referenced By Context")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.referencedByContext.label", "Referenced By Context"),
          placeholder: "Enter Referenced By Context",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:referencedByContext",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "referencedByContext",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("referencedByCommand", {
        id: "referencedByCommand",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.referencedByCommand.label", "Referenced By Command")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.referencedByCommand.label", "Referenced By Command"),
          placeholder: "Enter Referenced By Command",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:referencedByCommand",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "referencedByCommand",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("referencedByCommandId", {
        id: "referencedByCommandId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.referencedByCommandId.label", "Referenced By Command Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.referencedByCommandId.label", "Referenced By Command Id"),
          placeholder: "Enter Referenced By Command Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:referencedByCommandId",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "referencedByCommandId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("discardedAt", {
        id: "discardedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.discardedAt.label", "Discarded At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.discardedAt.label", "Discarded At"),
          placeholder: "Enter Discarded At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:discardedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "discardedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("discardReason", {
        id: "discardReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.discardReason.label", "Discard Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.discardReason.label", "Discard Reason"),
          placeholder: "Enter Discard Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:discardReason",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "discardReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("expiresAt", {
        id: "expiresAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.expiresAt.label", "Expires At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.expiresAt.label", "Expires At"),
          placeholder: "Enter Expires At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:expiresAt",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "expiresAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("expiredAt", {
        id: "expiredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.expiredAt.label", "Expired At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.expiredAt.label", "Expired At"),
          placeholder: "Enter Expired At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:expiredAt",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "expiredAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("expirationReason", {
        id: "expirationReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.uploaded_file_catalog.fields.expirationReason.label", "Expiration Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.uploaded_file_catalog.fields.expirationReason.label", "Expiration Reason"),
          placeholder: "Enter Expiration Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UploadedFileCatalogRecord>(
            frontendComposition,
            "field:uploaded-file-catalog:display:expirationReason",
            {
              value: getValue(),
              record: row.original,
              resource: "uploaded-file-catalog",
              field: "expirationReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<UploadedFileCatalogRecord>(
                frontendComposition,
                "row-actions:uploaded-file-catalog:list",
                "rowActions.before",
                { resource: "uploaded-file-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "state", ["Available"]) && (
                  <CommandButton
                    variant="ghost"
                    command="markFileReferenced"
                    recordItemId={row.original.fileId}
                    size="sm"
                    query={{
                      referencedByContext: row.original.referencedByContext,
                      referencedByCommand: row.original.referencedByCommand,
                      referencedByCommandId: row.original.referencedByCommandId,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "state", ["Available"]) && (
                  <CommandButton
                    variant="ghost"
                    command="discardFile"
                    recordItemId={row.original.fileId}
                    size="sm"
                    query={{
                      discardReason: row.original.discardReason,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="downloadFile"
                    recordItemId={row.original.fileId}
                    size="sm"
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.fileId} size="sm" />
              {renderSlotExtensions<UploadedFileCatalogRecord>(
                frontendComposition,
                "row-actions:uploaded-file-catalog:list",
                "rowActions.after",
                { resource: "uploaded-file-catalog", record: row.original },
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
    getRowId: (row) => String(row.fileId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "uploaded_file_catalog_read_model_entity",
        idField: "fileId",
        idFields: ["fileId"],
        queryFields: ["fileId","originalFileName","contentType","sizeBytes","purpose","fileLocation","checksum","state","uploadedAt","referencedAt","referencedByContext","referencedByCommand","referencedByCommandId","discardedAt","discardReason","expiresAt","expiredAt","expirationReason"],
        label: t("resources.uploaded_file_catalog.label", "Uploaded File Catalog"),
        aggregateRoute: "uploadedfile",
        queryRoute: "uploadedfilecatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:uploaded-file-catalog:list", "toolbar.before", { resource: "uploaded-file-catalog", table })}
        <CommandButton variant="default" command="uploadFile" />
        {renderSlotExtensions(frontendComposition, "toolbar:uploaded-file-catalog:list", "toolbar.actions", { resource: "uploaded-file-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:uploaded-file-catalog:list", "toolbar.after", { resource: "uploaded-file-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
