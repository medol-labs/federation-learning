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

type SecureAggregationSessionCatalogRecord = {
  secureAggregationSessionId: string;
  trainingJobId: string;
  trainingRunConfigurationId: string;
  featureSchemaId: string;
  roundId: string;
  requiredParticipantCount: number;
  acceptedRuntimeIds: string[];
  selectedRuntimeIds: string[];
  selectedParticipantCount?: number;
  encryptionContextPrepared: boolean;
  receivedEncryptedUpdateCount: number;
  encryptionScheme?: string;
  publicKeyVersion?: string;
  encryptedParameterScale?: number;
  aggregatedModelVersionId?: string;
  modelFormat?: string;
  modelHash?: string;
  state: string;
  failureReason?: string;
  createdAt: string;
  selectedAt?: string;
  encryptionContextPreparedAt?: string;
  decryptedAt?: string;
  completedAt?: string;
  failedAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: SecureAggregationSessionCatalogRecord,
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

export const SecureAggregationSessionCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<SecureAggregationSessionCatalogRecord>();
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
      columnHelper.accessor("secureAggregationSessionId", {
        id: "secureAggregationSessionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.secureAggregationSessionId.label", "Secure Aggregation Session Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingRunConfigurationId", {
        id: "trainingRunConfigurationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundId", {
        id: "roundId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.roundId.label", "Round Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("requiredParticipantCount", {
        id: "requiredParticipantCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.requiredParticipantCount.label", "Required Participant Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("acceptedRuntimeIds", {
        id: "acceptedRuntimeIds",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.acceptedRuntimeIds.label", "Accepted Runtime Ids")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedRuntimeIds", {
        id: "selectedRuntimeIds",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.selectedRuntimeIds.label", "Selected Runtime Ids")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedParticipantCount", {
        id: "selectedParticipantCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.selectedParticipantCount.label", "Selected Participant Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("encryptionContextPrepared", {
        id: "encryptionContextPrepared",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.encryptionContextPrepared.label", "Encryption Context Prepared")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("receivedEncryptedUpdateCount", {
        id: "receivedEncryptedUpdateCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.receivedEncryptedUpdateCount.label", "Received Encrypted Update Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("encryptionScheme", {
        id: "encryptionScheme",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.encryptionScheme.label", "Encryption Scheme")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("publicKeyVersion", {
        id: "publicKeyVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.publicKeyVersion.label", "Public Key Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("encryptedParameterScale", {
        id: "encryptedParameterScale",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.encryptedParameterScale.label", "Encrypted Parameter Scale")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("aggregatedModelVersionId", {
        id: "aggregatedModelVersionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.aggregatedModelVersionId.label", "Aggregated Model Version Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelFormat", {
        id: "modelFormat",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.modelFormat.label", "Model Format")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelHash", {
        id: "modelHash",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.modelHash.label", "Model Hash")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failureReason", {
        id: "failureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.failureReason.label", "Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("createdAt", {
        id: "createdAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.createdAt.label", "Created At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("selectedAt", {
        id: "selectedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.selectedAt.label", "Selected At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("encryptionContextPreparedAt", {
        id: "encryptionContextPreparedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.encryptionContextPreparedAt.label", "Encryption Context Prepared At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("decryptedAt", {
        id: "decryptedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.decryptedAt.label", "Decrypted At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("completedAt", {
        id: "completedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.completedAt.label", "Completed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("failedAt", {
        id: "failedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.failedAt.label", "Failed At")} />
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
                    command="completeSecureAggregation"
                    recordItemId={row.original.secureAggregationSessionId}
                    size="sm"
                    query={{
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      featureSchemaId: row.original.featureSchemaId,
                      roundId: row.original.roundId,
                      secureAggregationSessionId: row.original.secureAggregationSessionId,
                      aggregatedModelVersionId: row.original.aggregatedModelVersionId,
                      modelFormat: row.original.modelFormat,
                      modelHash: row.original.modelHash,
                      trainingJobId: row.original.trainingJobId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.secureAggregationSessionId} size="sm" />
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
    getRowId: (row) => String(row.secureAggregationSessionId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: true,
      meta: {
        tableName: "secure_aggregation_session_catalog_read_model_entity",
        idField: "secureAggregationSessionId",
        idFields: ["secureAggregationSessionId"],
        label: t("resources.secure_aggregation_session_catalog.label", "Secure Aggregation Session Catalog"),
        aggregateRoute: "secureaggregationsession",
        queryRoute: "secureaggregationsessioncatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
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
