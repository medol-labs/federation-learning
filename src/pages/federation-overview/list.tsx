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

type FederationOverviewRecord = {
  federationId: string;
  federationName: string;
  state: string;
  minimumParticipantCount: number;
  activeMemberCount: number;
  pendingInvitationCount: number;
  activeRuntimeCount: number;
  activeTrainingJobCount: number;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: FederationOverviewRecord,
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

export const FederationOverviewList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<FederationOverviewRecord>();
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
          <DataTableColumnHeader column={column} label={t("resources.federation_overview.fields.federationId.label", "Federation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.federation_overview.fields.federationId.label", "Federation Id"),
          placeholder: "Enter Federation Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationName", {
        id: "federationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_overview.fields.federationName.label", "Federation Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.federation_overview.fields.federationName.label", "Federation Name"),
          placeholder: "Enter Federation Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_overview.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.federation_overview.fields.state.label", "State"),
          placeholder: "Enter State",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("minimumParticipantCount", {
        id: "minimumParticipantCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_overview.fields.minimumParticipantCount.label", "Minimum Participant Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.federation_overview.fields.minimumParticipantCount.label", "Minimum Participant Count"),
          placeholder: "Enter Minimum Participant Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("activeMemberCount", {
        id: "activeMemberCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_overview.fields.activeMemberCount.label", "Active Member Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.federation_overview.fields.activeMemberCount.label", "Active Member Count"),
          placeholder: "Enter Active Member Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("pendingInvitationCount", {
        id: "pendingInvitationCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_overview.fields.pendingInvitationCount.label", "Pending Invitation Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.federation_overview.fields.pendingInvitationCount.label", "Pending Invitation Count"),
          placeholder: "Enter Pending Invitation Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("activeRuntimeCount", {
        id: "activeRuntimeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_overview.fields.activeRuntimeCount.label", "Active Runtime Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.federation_overview.fields.activeRuntimeCount.label", "Active Runtime Count"),
          placeholder: "Enter Active Runtime Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("activeTrainingJobCount", {
        id: "activeTrainingJobCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.federation_overview.fields.activeTrainingJobCount.label", "Active Training Job Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.federation_overview.fields.activeTrainingJobCount.label", "Active Training Job Count"),
          placeholder: "Enter Active Training Job Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            {isCommandVisible(row.original, "", "state", ["Suspended"]) && (
            <CommandButton
              variant="outline"
              command="removeParticipant"
              recordItemId={row.original.federationId}
              size="sm"
            />
            )}
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                {isCommandVisible(row.original, "", "state", ["Draft"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="activateFederation"
                    recordItemId={row.original.federationId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Active"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="suspendFederation"
                    recordItemId={row.original.federationId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Suspended"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="reactivateFederation"
                    recordItemId={row.original.federationId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="inviteParticipant"
                    recordItemId={row.original.federationId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Invited"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="approveParticipant"
                    recordItemId={row.original.federationId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Invited"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="rejectParticipant"
                    recordItemId={row.original.federationId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Invited"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="revokeParticipantInvitation"
                    recordItemId={row.original.federationId}
                    size="sm"
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "state", ["Active"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="suspendParticipant"
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
    getRowId: (row) => String(row.federationId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "federation_overview_read_model_entity",
        idField: "federationId",
        idFields: ["federationId"],
        queryFields: ["federationId","federationName","state","minimumParticipantCount","activeMemberCount","pendingInvitationCount","activeRuntimeCount","activeTrainingJobCount"],
        label: t("resources.federation_overview.label", "Federation Overview"),
        aggregateRoute: "federation",
        queryRoute: "federationoverview",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="createFederation" />
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
