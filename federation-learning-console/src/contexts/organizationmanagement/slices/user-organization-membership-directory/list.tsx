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

type UserOrganizationMembershipDirectoryRecord = {
  userOrganizationMembershipId: string;
  userAccountId: string;
  username?: string;
  organizationId: string;
  organizationName?: string;
  organizationUserRole?: string;
  state: "ACTIVE";
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: UserOrganizationMembershipDirectoryRecord,
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

export const UserOrganizationMembershipDirectoryList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<UserOrganizationMembershipDirectoryRecord>();
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
      columnHelper.accessor("userOrganizationMembershipId", {
        id: "userOrganizationMembershipId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_organization_membership_directory.fields.userOrganizationMembershipId.label", "User Organization Membership Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_organization_membership_directory.fields.userOrganizationMembershipId.label", "User Organization Membership Id"),
          placeholder: "Enter User Organization Membership Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserOrganizationMembershipDirectoryRecord>(
            frontendComposition,
            "field:user-organization-membership-directory:display:userOrganizationMembershipId",
            {
              value: getValue(),
              record: row.original,
              resource: "user-organization-membership-directory",
              field: "userOrganizationMembershipId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("userAccountId", {
        id: "userAccountId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_organization_membership_directory.fields.userAccountId.label", "User Account Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_organization_membership_directory.fields.userAccountId.label", "User Account Id"),
          placeholder: "Enter User Account Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserOrganizationMembershipDirectoryRecord>(
            frontendComposition,
            "field:user-organization-membership-directory:display:userAccountId",
            {
              value: getValue(),
              record: row.original,
              resource: "user-organization-membership-directory",
              field: "userAccountId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("username", {
        id: "username",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_organization_membership_directory.fields.username.label", "Username")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_organization_membership_directory.fields.username.label", "Username"),
          placeholder: "Enter Username",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserOrganizationMembershipDirectoryRecord>(
            frontendComposition,
            "field:user-organization-membership-directory:display:username",
            {
              value: getValue(),
              record: row.original,
              resource: "user-organization-membership-directory",
              field: "username",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_organization_membership_directory.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_organization_membership_directory.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserOrganizationMembershipDirectoryRecord>(
            frontendComposition,
            "field:user-organization-membership-directory:display:organizationId",
            {
              value: getValue(),
              record: row.original,
              resource: "user-organization-membership-directory",
              field: "organizationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_organization_membership_directory.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_organization_membership_directory.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserOrganizationMembershipDirectoryRecord>(
            frontendComposition,
            "field:user-organization-membership-directory:display:organizationName",
            {
              value: getValue(),
              record: row.original,
              resource: "user-organization-membership-directory",
              field: "organizationName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationUserRole", {
        id: "organizationUserRole",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_organization_membership_directory.fields.organizationUserRole.label", "Organization User Role")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_organization_membership_directory.fields.organizationUserRole.label", "Organization User Role"),
          placeholder: "Enter Organization User Role",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserOrganizationMembershipDirectoryRecord>(
            frontendComposition,
            "field:user-organization-membership-directory:display:organizationUserRole",
            {
              value: getValue(),
              record: row.original,
              resource: "user-organization-membership-directory",
              field: "organizationUserRole",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.user_organization_membership_directory.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.user_organization_membership_directory.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Active", value: "ACTIVE" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<UserOrganizationMembershipDirectoryRecord>(
            frontendComposition,
            "field:user-organization-membership-directory:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "user-organization-membership-directory",
              field: "state",
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
              {renderSlotExtensions<UserOrganizationMembershipDirectoryRecord>(
                frontendComposition,
                "row-actions:user-organization-membership-directory:list",
                "rowActions.before",
                { resource: "user-organization-membership-directory", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.userOrganizationMembershipId} size="sm" />
              {renderSlotExtensions<UserOrganizationMembershipDirectoryRecord>(
                frontendComposition,
                "row-actions:user-organization-membership-directory:list",
                "rowActions.after",
                { resource: "user-organization-membership-directory", record: row.original },
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
    getRowId: (row) => String(row.userOrganizationMembershipId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "user_organization_membership_directory_read_model_entity",
        idField: "userOrganizationMembershipId",
        idFields: ["userOrganizationMembershipId"],
        queryFields: ["userOrganizationMembershipId","userAccountId","username","organizationId","organizationName","organizationUserRole","state"],
        label: t("resources.user_organization_membership_directory.label", "User Organization Membership Directory"),
        aggregateRoute: "userorganizationmembership",
        queryRoute: "userorganizationmembershipdirectory",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:user-organization-membership-directory:list", "toolbar.before", { resource: "user-organization-membership-directory", table })}
        <CommandButton variant="default" command="bindUserAccountToOrganization" />
        {renderSlotExtensions(frontendComposition, "toolbar:user-organization-membership-directory:list", "toolbar.actions", { resource: "user-organization-membership-directory", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:user-organization-membership-directory:list", "toolbar.after", { resource: "user-organization-membership-directory", table })}
      </RefineDataTable>
    </ListView>
  );
};
