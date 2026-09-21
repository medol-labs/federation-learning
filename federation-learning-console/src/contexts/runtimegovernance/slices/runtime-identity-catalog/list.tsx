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

type RuntimeIdentityCatalogRecord = {
  runtimeId: string;
  runtimeInfrastructureId: string;
  runtimeAgentId: string;
  organizationId: string;
  organizationName?: string;
  runtimeName: string;
  identityStatus: string;
  activatedAt?: string;
  revokedAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeIdentityCatalogRecord,
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

export const RuntimeIdentityCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeIdentityCatalogRecord>();
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
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_identity_catalog.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_identity_catalog.fields.runtimeId.label", "Runtime Id"),
          placeholder: "Enter Runtime Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeIdentityCatalogRecord>(
            frontendComposition,
            "field:runtime-identity-catalog:display:runtimeId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-identity-catalog",
              field: "runtimeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_identity_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_identity_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id"),
          placeholder: "Enter Runtime Infrastructure Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeIdentityCatalogRecord>(
            frontendComposition,
            "field:runtime-identity-catalog:display:runtimeInfrastructureId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-identity-catalog",
              field: "runtimeInfrastructureId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_identity_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_identity_catalog.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeIdentityCatalogRecord>(
            frontendComposition,
            "field:runtime-identity-catalog:display:runtimeAgentId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-identity-catalog",
              field: "runtimeAgentId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_identity_catalog.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_identity_catalog.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeIdentityCatalogRecord>(
            frontendComposition,
            "field:runtime-identity-catalog:display:organizationId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-identity-catalog",
              field: "organizationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_identity_catalog.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_identity_catalog.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeIdentityCatalogRecord>(
            frontendComposition,
            "field:runtime-identity-catalog:display:organizationName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-identity-catalog",
              field: "organizationName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeName", {
        id: "runtimeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_identity_catalog.fields.runtimeName.label", "Runtime Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_identity_catalog.fields.runtimeName.label", "Runtime Name"),
          placeholder: "Enter Runtime Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeIdentityCatalogRecord>(
            frontendComposition,
            "field:runtime-identity-catalog:display:runtimeName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-identity-catalog",
              field: "runtimeName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("identityStatus", {
        id: "identityStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_identity_catalog.fields.identityStatus.label", "Identity Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_identity_catalog.fields.identityStatus.label", "Identity Status"),
          placeholder: "Enter Identity Status",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeIdentityCatalogRecord>(
            frontendComposition,
            "field:runtime-identity-catalog:display:identityStatus",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-identity-catalog",
              field: "identityStatus",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("activatedAt", {
        id: "activatedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_identity_catalog.fields.activatedAt.label", "Activated At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_identity_catalog.fields.activatedAt.label", "Activated At"),
          placeholder: "Enter Activated At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeIdentityCatalogRecord>(
            frontendComposition,
            "field:runtime-identity-catalog:display:activatedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-identity-catalog",
              field: "activatedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("revokedAt", {
        id: "revokedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_identity_catalog.fields.revokedAt.label", "Revoked At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_identity_catalog.fields.revokedAt.label", "Revoked At"),
          placeholder: "Enter Revoked At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeIdentityCatalogRecord>(
            frontendComposition,
            "field:runtime-identity-catalog:display:revokedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-identity-catalog",
              field: "revokedAt",
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
              {renderSlotExtensions<RuntimeIdentityCatalogRecord>(
                frontendComposition,
                "row-actions:runtime-identity-catalog:list",
                "rowActions.before",
                { resource: "runtime-identity-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "identityStatus", ["Active"]) && (
                  <CommandButton
                    variant="ghost"
                    command="revokeRuntimeIdentity"
                    recordItemId={row.original.runtimeId}
                    size="sm"
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.runtimeId} size="sm" />
              {renderSlotExtensions<RuntimeIdentityCatalogRecord>(
                frontendComposition,
                "row-actions:runtime-identity-catalog:list",
                "rowActions.after",
                { resource: "runtime-identity-catalog", record: row.original },
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
    getRowId: (row) => String(row.runtimeId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_identity_catalog_read_model_entity",
        idField: "runtimeId",
        idFields: ["runtimeId"],
        queryFields: ["runtimeId","runtimeInfrastructureId","runtimeAgentId","organizationId","organizationName","runtimeName","identityStatus","activatedAt","revokedAt"],
        label: t("resources.runtime_identity_catalog.label", "Runtime Identity Catalog"),
        aggregateRoute: "runtimeidentity",
        queryRoute: "runtimeidentitycatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-identity-catalog:list", "toolbar.before", { resource: "runtime-identity-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-identity-catalog:list", "toolbar.actions", { resource: "runtime-identity-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-identity-catalog:list", "toolbar.after", { resource: "runtime-identity-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
