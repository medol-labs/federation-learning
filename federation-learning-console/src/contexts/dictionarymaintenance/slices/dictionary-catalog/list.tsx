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
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
import type { DictionaryCode } from "@/contexts/domain/value-types";

type DictionaryCatalogRecord = {
  dictionaryId: string;
  dictionaryCode: DictionaryCode;
  dictionaryName: string;
  description?: string;
  state: "REGISTERED" | "ARCHIVED";
  registeredAt: string;
  updatedAt?: string;
  archivedAt?: string;
  archiveReason?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: DictionaryCatalogRecord,
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

export const DictionaryCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<DictionaryCatalogRecord>();
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
      columnHelper.accessor("dictionaryId", {
        id: "dictionaryId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.dictionaryId.label", "Dictionary Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.dictionaryId.label", "Dictionary Id"),
          placeholder: "Enter Dictionary Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:dictionaryId",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "dictionaryId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryCode", {
        id: "dictionaryCode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.dictionaryCode.label", "Dictionary Code")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.dictionaryCode.label", "Dictionary Code"),
          placeholder: "Enter Dictionary Code",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:dictionaryCode",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "dictionaryCode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("dictionaryName", {
        id: "dictionaryName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.dictionaryName.label", "Dictionary Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.dictionaryName.label", "Dictionary Name"),
          placeholder: "Enter Dictionary Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:dictionaryName",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "dictionaryName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("description", {
        id: "description",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.description.label", "Description")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.description.label", "Description"),
          placeholder: "Enter Description",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:description",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "description",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Registered", value: "REGISTERED" },
            { label: "Archived", value: "ARCHIVED" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("registeredAt", {
        id: "registeredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.registeredAt.label", "Registered At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.registeredAt.label", "Registered At"),
          placeholder: "Enter Registered At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:registeredAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "registeredAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("updatedAt", {
        id: "updatedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.updatedAt.label", "Updated At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.updatedAt.label", "Updated At"),
          placeholder: "Enter Updated At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:updatedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "updatedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("archivedAt", {
        id: "archivedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.archivedAt.label", "Archived At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.archivedAt.label", "Archived At"),
          placeholder: "Enter Archived At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:archivedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "archivedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("archiveReason", {
        id: "archiveReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.dictionary_catalog.fields.archiveReason.label", "Archive Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.dictionary_catalog.fields.archiveReason.label", "Archive Reason"),
          placeholder: "Enter Archive Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<DictionaryCatalogRecord>(
            frontendComposition,
            "field:dictionary-catalog:display:archiveReason",
            {
              value: getValue(),
              record: row.original,
              resource: "dictionary-catalog",
              field: "archiveReason",
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
            {isCommandVisible(row.original, "", "state", ["Registered"]) && (
            <CommandButton
              variant="outline"
              command="archiveDictionary"
              recordItemId={row.original.dictionaryId}
              size="sm"
              query={{
                archiveReason: row.original.archiveReason,
                dictionaryCode: row.original.dictionaryCode,
              }}
            />
            )}
            <RowActionMenu>
              {renderSlotExtensions<DictionaryCatalogRecord>(
                frontendComposition,
                "row-actions:dictionary-catalog:list",
                "rowActions.before",
                { resource: "dictionary-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "", []) && (
                  <EditButton variant="ghost" recordItemId={row.original.dictionaryId} size="sm" />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="addDictionaryValue"
                    recordItemId={row.original.dictionaryId}
                    size="sm"
                    query={{
                      dictionaryId: row.original.dictionaryId,
                      dictionaryCode: row.original.dictionaryCode,
                      description: row.original.description,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.dictionaryId} size="sm" />
              {renderSlotExtensions<DictionaryCatalogRecord>(
                frontendComposition,
                "row-actions:dictionary-catalog:list",
                "rowActions.after",
                { resource: "dictionary-catalog", record: row.original },
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
    getRowId: (row) => String(row.dictionaryId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "dictionary_catalog_read_model_entity",
        idField: "dictionaryId",
        idFields: ["dictionaryId"],
        queryFields: ["dictionaryId","dictionaryCode","dictionaryName","description","state","registeredAt","updatedAt","archivedAt","archiveReason"],
        label: t("resources.dictionary_catalog.label", "Dictionary Catalog"),
        aggregateRoute: "dictionary",
        queryRoute: "dictionarycatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-catalog:list", "toolbar.before", { resource: "dictionary-catalog", table })}
        <CommandButton variant="default" command="registerDictionary" />
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-catalog:list", "toolbar.actions", { resource: "dictionary-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        <CommandButton variant="destructive" command="archiveDictionary" size="sm" />
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:dictionary-catalog:list", "toolbar.after", { resource: "dictionary-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
