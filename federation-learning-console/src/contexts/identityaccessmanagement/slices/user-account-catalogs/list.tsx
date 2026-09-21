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

type UserAccountCatalogRecord = {
  userAccountId: string;
  username: string;
  providerSubject?: string;
  userSource?: string;
  passwordHash?: string;
  active: boolean;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: UserAccountCatalogRecord,
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

export const UserAccountCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<UserAccountCatalogRecord>();
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
      columnHelper.accessor("userAccountId", {
        id: "userAccountId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_account_catalog.fields.userAccountId.label", "User Account Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_account_catalog.fields.userAccountId.label", "User Account Id"),
          placeholder: "Enter User Account Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserAccountCatalogRecord>(
            frontendComposition,
            "field:user-account-catalog:display:userAccountId",
            {
              value: getValue(),
              record: row.original,
              resource: "user-account-catalog",
              field: "userAccountId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("username", {
        id: "username",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_account_catalog.fields.username.label", "Username")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_account_catalog.fields.username.label", "Username"),
          placeholder: "Enter Username",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserAccountCatalogRecord>(
            frontendComposition,
            "field:user-account-catalog:display:username",
            {
              value: getValue(),
              record: row.original,
              resource: "user-account-catalog",
              field: "username",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("providerSubject", {
        id: "providerSubject",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_account_catalog.fields.providerSubject.label", "Provider Subject")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_account_catalog.fields.providerSubject.label", "Provider Subject"),
          placeholder: "Enter Provider Subject",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserAccountCatalogRecord>(
            frontendComposition,
            "field:user-account-catalog:display:providerSubject",
            {
              value: getValue(),
              record: row.original,
              resource: "user-account-catalog",
              field: "providerSubject",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("userSource", {
        id: "userSource",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_account_catalog.fields.userSource.label", "User Source")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_account_catalog.fields.userSource.label", "User Source"),
          placeholder: "Enter User Source",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserAccountCatalogRecord>(
            frontendComposition,
            "field:user-account-catalog:display:userSource",
            {
              value: getValue(),
              record: row.original,
              resource: "user-account-catalog",
              field: "userSource",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("passwordHash", {
        id: "passwordHash",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_account_catalog.fields.passwordHash.label", "Password Hash")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_account_catalog.fields.passwordHash.label", "Password Hash"),
          placeholder: "Enter Password Hash",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserAccountCatalogRecord>(
            frontendComposition,
            "field:user-account-catalog:display:passwordHash",
            {
              value: getValue(),
              record: row.original,
              resource: "user-account-catalog",
              field: "passwordHash",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("active", {
        id: "active",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_account_catalog.fields.active.label", "Active")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_account_catalog.fields.active.label", "Active"),
          placeholder: "Enter Active",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserAccountCatalogRecord>(
            frontendComposition,
            "field:user-account-catalog:display:active",
            {
              value: getValue(),
              record: row.original,
              resource: "user-account-catalog",
              field: "active",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<UserAccountCatalogRecord>(
                frontendComposition,
                "row-actions:user-account-catalog:list",
                "rowActions.before",
                { resource: "user-account-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="generateUserAccountLoginPassword"
                    recordItemId={row.original.userAccountId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="deactivateUserAccount"
                    recordItemId={row.original.userAccountId}
                    size="sm"
                  />
                )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="assignRoleToUser"
                    recordItemId={row.original.userAccountId}
                    size="sm"
                    query={{
                      userAccountId: row.original.userAccountId,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.userAccountId} size="sm" />
              {renderSlotExtensions<UserAccountCatalogRecord>(
                frontendComposition,
                "row-actions:user-account-catalog:list",
                "rowActions.after",
                { resource: "user-account-catalog", record: row.original },
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
    getRowId: (row) => String(row.userAccountId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "user_account_catalog_read_model_entity",
        idField: "userAccountId",
        idFields: ["userAccountId"],
        queryFields: ["userAccountId","username","providerSubject","userSource","passwordHash","active"],
        label: t("resources.user_account_catalog.label", "User Account Catalog"),
        aggregateRoute: "useraccount",
        queryRoute: "useraccountcatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:user-account-catalog:list", "toolbar.before", { resource: "user-account-catalog", table })}
        <CommandButton variant="default" command="registerUserAccount" />
        {renderSlotExtensions(frontendComposition, "toolbar:user-account-catalog:list", "toolbar.actions", { resource: "user-account-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:user-account-catalog:list", "toolbar.after", { resource: "user-account-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
