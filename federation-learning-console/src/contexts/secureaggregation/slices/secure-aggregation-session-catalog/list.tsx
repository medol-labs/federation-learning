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

type SecureAggregationSessionCatalogRecord = {
  secureAggregationSessionId: string;
  trainingJobId: string;
  trainingRunConfigurationId: string;
  featureSchemaId: string;
  roundId: string;
  roundNumber: number;
  requiredParticipantCount: number;
  selectedOrganizationIds: string[];
  selectedRuntimeIds: string[];
  selectedOrganizationCount?: number;
  selectedRuntimeCount?: number;
  selectedParticipantCount?: number;
  encryptionContextPrepared: boolean;
  receivedEncryptedUpdateCount: number;
  encryptionScheme?: string;
  publicKeyVersion?: string;
  publicKeyRef?: string;
  encryptedParameterScale?: number;
  aggregatedModelId?: string;
  modelFormat?: string;
  modelArtifactDigest?: string;
  state: "PLANNED" | "PARTICIPANTS_SELECTED" | "ENCRYPTION_CONTEXT_PREPARED" | "COMPLETED" | "FAILED";
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
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.secureAggregationSessionId.label", "Secure Aggregation Session Id"),
          placeholder: "Enter Secure Aggregation Session Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:secureAggregationSessionId",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "secureAggregationSessionId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:trainingJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "trainingJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingRunConfigurationId", {
        id: "trainingRunConfigurationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id"),
          placeholder: "Enter Training Run Configuration Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:trainingRunConfigurationId",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "trainingRunConfigurationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:featureSchemaId",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "featureSchemaId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundId", {
        id: "roundId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.roundId.label", "Round Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.roundId.label", "Round Id"),
          placeholder: "Enter Round Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:roundId",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "roundId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundNumber", {
        id: "roundNumber",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.roundNumber.label", "Round Number")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.roundNumber.label", "Round Number"),
          placeholder: "Enter Round Number",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:roundNumber",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "roundNumber",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("requiredParticipantCount", {
        id: "requiredParticipantCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.requiredParticipantCount.label", "Required Participant Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.requiredParticipantCount.label", "Required Participant Count"),
          placeholder: "Enter Required Participant Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:requiredParticipantCount",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "requiredParticipantCount",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedOrganizationIds", {
        id: "selectedOrganizationIds",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.selectedOrganizationIds.label", "Selected Organization Ids")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.selectedOrganizationIds.label", "Selected Organization Ids"),
          placeholder: "Enter Selected Organization Ids",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:selectedOrganizationIds",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "selectedOrganizationIds",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedRuntimeIds", {
        id: "selectedRuntimeIds",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.selectedRuntimeIds.label", "Selected Runtime Ids")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.selectedRuntimeIds.label", "Selected Runtime Ids"),
          placeholder: "Enter Selected Runtime Ids",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:selectedRuntimeIds",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "selectedRuntimeIds",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedOrganizationCount", {
        id: "selectedOrganizationCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.selectedOrganizationCount.label", "Selected Organization Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.selectedOrganizationCount.label", "Selected Organization Count"),
          placeholder: "Enter Selected Organization Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:selectedOrganizationCount",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "selectedOrganizationCount",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedRuntimeCount", {
        id: "selectedRuntimeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.selectedRuntimeCount.label", "Selected Runtime Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.selectedRuntimeCount.label", "Selected Runtime Count"),
          placeholder: "Enter Selected Runtime Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:selectedRuntimeCount",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "selectedRuntimeCount",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectedParticipantCount", {
        id: "selectedParticipantCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.selectedParticipantCount.label", "Selected Participant Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.selectedParticipantCount.label", "Selected Participant Count"),
          placeholder: "Enter Selected Participant Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:selectedParticipantCount",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "selectedParticipantCount",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("encryptionContextPrepared", {
        id: "encryptionContextPrepared",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.encryptionContextPrepared.label", "Encryption Context Prepared")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.encryptionContextPrepared.label", "Encryption Context Prepared"),
          placeholder: "Enter Encryption Context Prepared",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:encryptionContextPrepared",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "encryptionContextPrepared",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("receivedEncryptedUpdateCount", {
        id: "receivedEncryptedUpdateCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.receivedEncryptedUpdateCount.label", "Received Encrypted Update Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.receivedEncryptedUpdateCount.label", "Received Encrypted Update Count"),
          placeholder: "Enter Received Encrypted Update Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:receivedEncryptedUpdateCount",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "receivedEncryptedUpdateCount",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("encryptionScheme", {
        id: "encryptionScheme",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.encryptionScheme.label", "Encryption Scheme")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.encryptionScheme.label", "Encryption Scheme"),
          placeholder: "Enter Encryption Scheme",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:encryptionScheme",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "encryptionScheme",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("publicKeyVersion", {
        id: "publicKeyVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.publicKeyVersion.label", "Public Key Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.publicKeyVersion.label", "Public Key Version"),
          placeholder: "Enter Public Key Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:publicKeyVersion",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "publicKeyVersion",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("publicKeyRef", {
        id: "publicKeyRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.publicKeyRef.label", "Public Key Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.publicKeyRef.label", "Public Key Ref"),
          placeholder: "Enter Public Key Ref",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:publicKeyRef",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "publicKeyRef",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("encryptedParameterScale", {
        id: "encryptedParameterScale",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.encryptedParameterScale.label", "Encrypted Parameter Scale")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.encryptedParameterScale.label", "Encrypted Parameter Scale"),
          placeholder: "Enter Encrypted Parameter Scale",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:encryptedParameterScale",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "encryptedParameterScale",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("aggregatedModelId", {
        id: "aggregatedModelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.aggregatedModelId.label", "Aggregated Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.aggregatedModelId.label", "Aggregated Model Id"),
          placeholder: "Enter Aggregated Model Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:aggregatedModelId",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "aggregatedModelId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelFormat", {
        id: "modelFormat",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.modelFormat.label", "Model Format")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.modelFormat.label", "Model Format"),
          placeholder: "Enter Model Format",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:modelFormat",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "modelFormat",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelArtifactDigest", {
        id: "modelArtifactDigest",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest"),
          placeholder: "Enter Model Artifact Digest",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:modelArtifactDigest",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "modelArtifactDigest",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Planned", value: "PLANNED" },
            { label: "Participants Selected", value: "PARTICIPANTS_SELECTED" },
            { label: "Encryption Context Prepared", value: "ENCRYPTION_CONTEXT_PREPARED" },
            { label: "Completed", value: "COMPLETED" },
            { label: "Failed", value: "FAILED" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("failureReason", {
        id: "failureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.failureReason.label", "Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.failureReason.label", "Failure Reason"),
          placeholder: "Enter Failure Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:failureReason",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "failureReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("createdAt", {
        id: "createdAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.createdAt.label", "Created At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.createdAt.label", "Created At"),
          placeholder: "Enter Created At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:createdAt",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "createdAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("selectedAt", {
        id: "selectedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.selectedAt.label", "Selected At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.selectedAt.label", "Selected At"),
          placeholder: "Enter Selected At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:selectedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "selectedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("encryptionContextPreparedAt", {
        id: "encryptionContextPreparedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.encryptionContextPreparedAt.label", "Encryption Context Prepared At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.encryptionContextPreparedAt.label", "Encryption Context Prepared At"),
          placeholder: "Enter Encryption Context Prepared At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:encryptionContextPreparedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "encryptionContextPreparedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("decryptedAt", {
        id: "decryptedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.decryptedAt.label", "Decrypted At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.decryptedAt.label", "Decrypted At"),
          placeholder: "Enter Decrypted At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:decryptedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "decryptedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("completedAt", {
        id: "completedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.completedAt.label", "Completed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.completedAt.label", "Completed At"),
          placeholder: "Enter Completed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:completedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "completedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("failedAt", {
        id: "failedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.secure_aggregation_session_catalog.fields.failedAt.label", "Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.secure_aggregation_session_catalog.fields.failedAt.label", "Failed At"),
          placeholder: "Enter Failed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<SecureAggregationSessionCatalogRecord>(
            frontendComposition,
            "field:secure-aggregation-session-catalog:display:failedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "secure-aggregation-session-catalog",
              field: "failedAt",
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
              {renderSlotExtensions<SecureAggregationSessionCatalogRecord>(
                frontendComposition,
                "row-actions:secure-aggregation-session-catalog:list",
                "rowActions.before",
                { resource: "secure-aggregation-session-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "state", ["Planned"]) && (
                  <CommandButton
                    variant="ghost"
                    command="failSecureAggregationSession"
                    recordItemId={row.original.secureAggregationSessionId}
                    size="sm"
                    query={{
                      failureReason: row.original.failureReason,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.secureAggregationSessionId} size="sm" />
              {renderSlotExtensions<SecureAggregationSessionCatalogRecord>(
                frontendComposition,
                "row-actions:secure-aggregation-session-catalog:list",
                "rowActions.after",
                { resource: "secure-aggregation-session-catalog", record: row.original },
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
    getRowId: (row) => String(row.secureAggregationSessionId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "secure_aggregation_session_catalog_read_model_entity",
        idField: "secureAggregationSessionId",
        idFields: ["secureAggregationSessionId"],
        queryFields: ["secureAggregationSessionId","trainingJobId","trainingRunConfigurationId","featureSchemaId","roundId","roundNumber","requiredParticipantCount","selectedOrganizationCount","selectedRuntimeCount","selectedParticipantCount","encryptionContextPrepared","receivedEncryptedUpdateCount","encryptionScheme","publicKeyVersion","publicKeyRef","encryptedParameterScale","aggregatedModelId","modelFormat","modelArtifactDigest","state","failureReason","createdAt","selectedAt","encryptionContextPreparedAt","decryptedAt","completedAt","failedAt"],
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
        {renderSlotExtensions(frontendComposition, "toolbar:secure-aggregation-session-catalog:list", "toolbar.before", { resource: "secure-aggregation-session-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:secure-aggregation-session-catalog:list", "toolbar.actions", { resource: "secure-aggregation-session-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:secure-aggregation-session-catalog:list", "toolbar.after", { resource: "secure-aggregation-session-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
