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

type FederationMembershipDirectoryRecord = {
  federationId: string;
  organizationId: string;
  federationName?: string;
  organizationName?: string;
  membershipStatus: string;
  invitationNote?: string;
  approvalNote?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: FederationMembershipDirectoryRecord,
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

export const FederationMembershipDirectoryList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<FederationMembershipDirectoryRecord>();
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
      columnHelper.accessor("federationId", {
        id: "federationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_membership_directory.fields.federationId.label", "Federation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_membership_directory.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationName", {
        id: "federationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_membership_directory.fields.federationName.label", "Federation Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_membership_directory.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("membershipStatus", {
        id: "membershipStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_membership_directory.fields.membershipStatus.label", "Membership Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("invitationNote", {
        id: "invitationNote",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_membership_directory.fields.invitationNote.label", "Invitation Note")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("approvalNote", {
        id: "approvalNote",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_membership_directory.fields.approvalNote.label", "Approval Note")} />
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
            {isCommandVisible(row.original, "", "membershipStatus", ["Suspended"]) && (
            <CommandButton
              variant="outline"
              command="removeParticipant"
              recordItemId={row.original.federationId}
              size="sm"
              query={{
                organizationId: row.original.organizationId,
              }}
            />
            )}
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                {isCommandVisible(row.original, "", "membershipStatus", ["Invited"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="approveParticipant"
                    recordItemId={row.original.federationId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                      approvalNote: row.original.approvalNote,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "membershipStatus", ["Invited"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="rejectParticipant"
                    recordItemId={row.original.federationId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "membershipStatus", ["Invited"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="revokeParticipantInvitation"
                    recordItemId={row.original.federationId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "membershipStatus", ["Active"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="suspendParticipant"
                    recordItemId={row.original.federationId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "membershipStatus", ["Draft"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="activateFederation"
                    recordItemId={row.original.federationId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.federationId} size="sm" />
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
    getRowId: (row) => String(row.federationId) + ":" + String(row.organizationId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: true,
      meta: {
        tableName: "federation_membership_directory_read_model_entity",
        idField: "federationId",
        idFields: ["federationId","organizationId"],
        label: t("resources.federation_membership_directory.label", "Federation Membership Directory"),
        aggregateRoute: "federationmembership",
        queryRoute: "federationmembershipdirectory",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="inviteParticipant" />
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        <CommandButton variant="destructive" command="removeParticipant" size="sm" />
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
