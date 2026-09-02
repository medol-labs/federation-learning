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

type ServiceAccountApiTokenCatalogRecord = {
  apiTokenId: string;
  userAccountId: string;
  username: string;
  tokenName: string;
  tokenPrefix: string;
  issuedAt: string;
  roles: string[];
  permissions: string[];
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: ServiceAccountApiTokenCatalogRecord,
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

export const ServiceAccountApiTokenCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<ServiceAccountApiTokenCatalogRecord>();
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
      columnHelper.accessor("apiTokenId", {
        id: "apiTokenId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.service_account_api_token_catalog.fields.apiTokenId.label", "Api Token Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.service_account_api_token_catalog.fields.apiTokenId.label", "Api Token Id"),
          placeholder: "Enter Api Token Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("userAccountId", {
        id: "userAccountId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.service_account_api_token_catalog.fields.userAccountId.label", "User Account Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.service_account_api_token_catalog.fields.userAccountId.label", "User Account Id"),
          placeholder: "Enter User Account Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("username", {
        id: "username",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.service_account_api_token_catalog.fields.username.label", "Username")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.service_account_api_token_catalog.fields.username.label", "Username"),
          placeholder: "Enter Username",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("tokenName", {
        id: "tokenName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.service_account_api_token_catalog.fields.tokenName.label", "Token Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.service_account_api_token_catalog.fields.tokenName.label", "Token Name"),
          placeholder: "Enter Token Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("tokenPrefix", {
        id: "tokenPrefix",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.service_account_api_token_catalog.fields.tokenPrefix.label", "Token Prefix")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.service_account_api_token_catalog.fields.tokenPrefix.label", "Token Prefix"),
          placeholder: "Enter Token Prefix",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("issuedAt", {
        id: "issuedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.service_account_api_token_catalog.fields.issuedAt.label", "Issued At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.service_account_api_token_catalog.fields.issuedAt.label", "Issued At"),
          placeholder: "Enter Issued At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roles", {
        id: "roles",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.service_account_api_token_catalog.fields.roles.label", "Roles")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.service_account_api_token_catalog.fields.roles.label", "Roles"),
          placeholder: "Enter Roles",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("permissions", {
        id: "permissions",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.service_account_api_token_catalog.fields.permissions.label", "Permissions")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.service_account_api_token_catalog.fields.permissions.label", "Permissions"),
          placeholder: "Enter Permissions",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              <ShowButton variant="ghost" recordItemId={row.original.apiTokenId} size="sm" />
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
    getRowId: (row) => String(row.apiTokenId),
    refineCoreProps: {
      dataProviderName: "federation-learning-support",
      syncWithLocation: false,
      meta: {
        tableName: "service_account_api_token_catalog_read_model_entity",
        idField: "apiTokenId",
        idFields: ["apiTokenId"],
        queryFields: ["apiTokenId","userAccountId","username","tokenName","tokenPrefix","issuedAt"],
        label: t("resources.service_account_api_token_catalog.label", "Service Account Api Token Catalog"),
        aggregateRoute: "serviceaccountapitoken",
        queryRoute: "serviceaccountapitokencatalog",
        dataProviderName: "federation-learning-support",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="issueServiceAccountApiToken" />
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
