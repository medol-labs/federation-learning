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

type RoundExecutionCatalogRecord = {
  roundExecutionId: string;
  executionSessionId?: string;
  executionPlanId?: string;
  trainingJobId: string;
  trainingRunConfigurationId: string;
  roundId: string;
  roundNumber?: number;
  organizationId?: string;
  runtimeId: string;
  state: "PLAN_RECEIVED" | "PLAN_ACCEPTED" | "PLAN_REJECTED" | "RUNNING" | "START_FAILED" | "RETRIED" | "COMPLETED" | "FAILED" | "UPDATE_SUBMITTED" | "RUNTIME_ENGINE_RELEASED" | "RUNTIME_ENGINE_RELEASE_HANDLED";
  featureSchemaId?: string;
  baseModelId?: string;
  runtimeEngineProfileId?: string;
  runtimeEngineProfileName?: string;
  runtimeEnginePluginProfile?: string;
  runtimeEngineImage?: string;
  runtimeEngineImageDigest?: string;
  runtimeEngineJobId?: string;
  runtimeEngineObservedStatus?: string;
  runtimeEngineObservationAt?: string;
  localUpdateArtifactRef?: string;
  metricsArtifactRef?: string;
  localExecutionRequirementsSatisfied?: boolean;
  runtimeIdentityMatched?: boolean;
  runtimeDatasetBindingAvailable?: boolean;
  datasetAccessValidated?: boolean;
  baseModelAvailable?: boolean;
  trainingConfigurationSupported?: boolean;
  runtimeResourceAvailable?: boolean;
  runtimeAgentIdle?: boolean;
  updateArtifactId?: string;
  artifactRef?: string;
  artifactDigest?: string;
  trainingLoss?: string;
  receivedAt?: string;
  acceptedAt?: string;
  rejectedAt?: string;
  startedAt?: string;
  completedAt?: string;
  failedAt?: string;
  submittedAt?: string;
  failureReason?: string;
  retryReason?: string;
  runtimeEngineReleased?: boolean;
  runtimeEngineReleaseFailureReason?: string;
  rejectionReasons: string[];
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RoundExecutionCatalogRecord,
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

export const RoundExecutionCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RoundExecutionCatalogRecord>();
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
      columnHelper.accessor("roundExecutionId", {
        id: "roundExecutionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.roundExecutionId.label", "Round Execution Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.roundExecutionId.label", "Round Execution Id"),
          placeholder: "Enter Round Execution Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:roundExecutionId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "roundExecutionId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("executionSessionId", {
        id: "executionSessionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.executionSessionId.label", "Execution Session Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.executionSessionId.label", "Execution Session Id"),
          placeholder: "Enter Execution Session Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:executionSessionId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "executionSessionId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("executionPlanId", {
        id: "executionPlanId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.executionPlanId.label", "Execution Plan Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.executionPlanId.label", "Execution Plan Id"),
          placeholder: "Enter Execution Plan Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:executionPlanId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "executionPlanId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:trainingJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "trainingJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingRunConfigurationId", {
        id: "trainingRunConfigurationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id"),
          placeholder: "Enter Training Run Configuration Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:trainingRunConfigurationId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "trainingRunConfigurationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundId", {
        id: "roundId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.roundId.label", "Round Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.roundId.label", "Round Id"),
          placeholder: "Enter Round Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:roundId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "roundId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundNumber", {
        id: "roundNumber",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.roundNumber.label", "Round Number")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.roundNumber.label", "Round Number"),
          placeholder: "Enter Round Number",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:roundNumber",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "roundNumber",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:organizationId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "organizationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeId.label", "Runtime Id"),
          placeholder: "Enter Runtime Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Plan Received", value: "PLAN_RECEIVED" },
            { label: "Plan Accepted", value: "PLAN_ACCEPTED" },
            { label: "Plan Rejected", value: "PLAN_REJECTED" },
            { label: "Running", value: "RUNNING" },
            { label: "Start Failed", value: "START_FAILED" },
            { label: "Retried", value: "RETRIED" },
            { label: "Completed", value: "COMPLETED" },
            { label: "Failed", value: "FAILED" },
            { label: "Update Submitted", value: "UPDATE_SUBMITTED" },
            { label: "Runtime Engine Released", value: "RUNTIME_ENGINE_RELEASED" },
            { label: "Runtime Engine Release Handled", value: "RUNTIME_ENGINE_RELEASE_HANDLED" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:featureSchemaId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "featureSchemaId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("baseModelId", {
        id: "baseModelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.baseModelId.label", "Base Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.baseModelId.label", "Base Model Id"),
          placeholder: "Enter Base Model Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:baseModelId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "baseModelId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineProfileId", {
        id: "runtimeEngineProfileId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineProfileId.label", "Runtime Engine Profile Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEngineProfileId.label", "Runtime Engine Profile Id"),
          placeholder: "Enter Runtime Engine Profile Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEngineProfileId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEngineProfileId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineProfileName", {
        id: "runtimeEngineProfileName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineProfileName.label", "Runtime Engine Profile Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEngineProfileName.label", "Runtime Engine Profile Name"),
          placeholder: "Enter Runtime Engine Profile Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEngineProfileName",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEngineProfileName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEnginePluginProfile", {
        id: "runtimeEnginePluginProfile",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEnginePluginProfile.label", "Runtime Engine Plugin Profile")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEnginePluginProfile.label", "Runtime Engine Plugin Profile"),
          placeholder: "Enter Runtime Engine Plugin Profile",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEnginePluginProfile",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEnginePluginProfile",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineImage", {
        id: "runtimeEngineImage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineImage.label", "Runtime Engine Image")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEngineImage.label", "Runtime Engine Image"),
          placeholder: "Enter Runtime Engine Image",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEngineImage",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEngineImage",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineImageDigest", {
        id: "runtimeEngineImageDigest",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineImageDigest.label", "Runtime Engine Image Digest")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEngineImageDigest.label", "Runtime Engine Image Digest"),
          placeholder: "Enter Runtime Engine Image Digest",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEngineImageDigest",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEngineImageDigest",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineJobId", {
        id: "runtimeEngineJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineJobId.label", "Runtime Engine Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEngineJobId.label", "Runtime Engine Job Id"),
          placeholder: "Enter Runtime Engine Job Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEngineJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEngineJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineObservedStatus", {
        id: "runtimeEngineObservedStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineObservedStatus.label", "Runtime Engine Observed Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEngineObservedStatus.label", "Runtime Engine Observed Status"),
          placeholder: "Enter Runtime Engine Observed Status",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEngineObservedStatus",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEngineObservedStatus",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineObservationAt", {
        id: "runtimeEngineObservationAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineObservationAt.label", "Runtime Engine Observation At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEngineObservationAt.label", "Runtime Engine Observation At"),
          placeholder: "Enter Runtime Engine Observation At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEngineObservationAt",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEngineObservationAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("localUpdateArtifactRef", {
        id: "localUpdateArtifactRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.localUpdateArtifactRef.label", "Local Update Artifact Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.localUpdateArtifactRef.label", "Local Update Artifact Ref"),
          placeholder: "Enter Local Update Artifact Ref",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:localUpdateArtifactRef",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "localUpdateArtifactRef",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("metricsArtifactRef", {
        id: "metricsArtifactRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.metricsArtifactRef.label", "Metrics Artifact Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.metricsArtifactRef.label", "Metrics Artifact Ref"),
          placeholder: "Enter Metrics Artifact Ref",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:metricsArtifactRef",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "metricsArtifactRef",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("localExecutionRequirementsSatisfied", {
        id: "localExecutionRequirementsSatisfied",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.localExecutionRequirementsSatisfied.label", "Local Execution Requirements Satisfied")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.localExecutionRequirementsSatisfied.label", "Local Execution Requirements Satisfied"),
          placeholder: "Enter Local Execution Requirements Satisfied",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:localExecutionRequirementsSatisfied",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "localExecutionRequirementsSatisfied",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeIdentityMatched", {
        id: "runtimeIdentityMatched",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeIdentityMatched.label", "Runtime Identity Matched")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeIdentityMatched.label", "Runtime Identity Matched"),
          placeholder: "Enter Runtime Identity Matched",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeIdentityMatched",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeIdentityMatched",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeDatasetBindingAvailable", {
        id: "runtimeDatasetBindingAvailable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeDatasetBindingAvailable.label", "Runtime Dataset Binding Available")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeDatasetBindingAvailable.label", "Runtime Dataset Binding Available"),
          placeholder: "Enter Runtime Dataset Binding Available",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeDatasetBindingAvailable",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeDatasetBindingAvailable",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetAccessValidated", {
        id: "datasetAccessValidated",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.datasetAccessValidated.label", "Dataset Access Validated")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.datasetAccessValidated.label", "Dataset Access Validated"),
          placeholder: "Enter Dataset Access Validated",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:datasetAccessValidated",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "datasetAccessValidated",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("baseModelAvailable", {
        id: "baseModelAvailable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.baseModelAvailable.label", "Base Model Available")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.baseModelAvailable.label", "Base Model Available"),
          placeholder: "Enter Base Model Available",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:baseModelAvailable",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "baseModelAvailable",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("trainingConfigurationSupported", {
        id: "trainingConfigurationSupported",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.trainingConfigurationSupported.label", "Training Configuration Supported")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.trainingConfigurationSupported.label", "Training Configuration Supported"),
          placeholder: "Enter Training Configuration Supported",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:trainingConfigurationSupported",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "trainingConfigurationSupported",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeResourceAvailable", {
        id: "runtimeResourceAvailable",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeResourceAvailable.label", "Runtime Resource Available")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeResourceAvailable.label", "Runtime Resource Available"),
          placeholder: "Enter Runtime Resource Available",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeResourceAvailable",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeResourceAvailable",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeAgentIdle", {
        id: "runtimeAgentIdle",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeAgentIdle.label", "Runtime Agent Idle")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeAgentIdle.label", "Runtime Agent Idle"),
          placeholder: "Enter Runtime Agent Idle",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeAgentIdle",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeAgentIdle",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("updateArtifactId", {
        id: "updateArtifactId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.updateArtifactId.label", "Update Artifact Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.updateArtifactId.label", "Update Artifact Id"),
          placeholder: "Enter Update Artifact Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:updateArtifactId",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "updateArtifactId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("artifactRef", {
        id: "artifactRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.artifactRef.label", "Artifact Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.artifactRef.label", "Artifact Ref"),
          placeholder: "Enter Artifact Ref",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:artifactRef",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "artifactRef",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("artifactDigest", {
        id: "artifactDigest",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.artifactDigest.label", "Artifact Digest")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.artifactDigest.label", "Artifact Digest"),
          placeholder: "Enter Artifact Digest",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:artifactDigest",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "artifactDigest",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingLoss", {
        id: "trainingLoss",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.trainingLoss.label", "Training Loss")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.trainingLoss.label", "Training Loss"),
          placeholder: "Enter Training Loss",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:trainingLoss",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "trainingLoss",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("receivedAt", {
        id: "receivedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.receivedAt.label", "Received At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.receivedAt.label", "Received At"),
          placeholder: "Enter Received At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:receivedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "receivedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("acceptedAt", {
        id: "acceptedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.acceptedAt.label", "Accepted At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.acceptedAt.label", "Accepted At"),
          placeholder: "Enter Accepted At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:acceptedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "acceptedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("rejectedAt", {
        id: "rejectedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.rejectedAt.label", "Rejected At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.rejectedAt.label", "Rejected At"),
          placeholder: "Enter Rejected At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:rejectedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "rejectedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("startedAt", {
        id: "startedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.startedAt.label", "Started At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.startedAt.label", "Started At"),
          placeholder: "Enter Started At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:startedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "startedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("completedAt", {
        id: "completedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.completedAt.label", "Completed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.completedAt.label", "Completed At"),
          placeholder: "Enter Completed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:completedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "completedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("failedAt", {
        id: "failedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.failedAt.label", "Failed At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.failedAt.label", "Failed At"),
          placeholder: "Enter Failed At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:failedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "failedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("submittedAt", {
        id: "submittedAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.submittedAt.label", "Submitted At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.submittedAt.label", "Submitted At"),
          placeholder: "Enter Submitted At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:submittedAt",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "submittedAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("failureReason", {
        id: "failureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.failureReason.label", "Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.failureReason.label", "Failure Reason"),
          placeholder: "Enter Failure Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:failureReason",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "failureReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("retryReason", {
        id: "retryReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.retryReason.label", "Retry Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.retryReason.label", "Retry Reason"),
          placeholder: "Enter Retry Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:retryReason",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "retryReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineReleased", {
        id: "runtimeEngineReleased",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineReleased.label", "Runtime Engine Released")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEngineReleased.label", "Runtime Engine Released"),
          placeholder: "Enter Runtime Engine Released",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEngineReleased",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEngineReleased",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeEngineReleaseFailureReason", {
        id: "runtimeEngineReleaseFailureReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.runtimeEngineReleaseFailureReason.label", "Runtime Engine Release Failure Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.round_execution_catalog.fields.runtimeEngineReleaseFailureReason.label", "Runtime Engine Release Failure Reason"),
          placeholder: "Enter Runtime Engine Release Failure Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:runtimeEngineReleaseFailureReason",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "runtimeEngineReleaseFailureReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("rejectionReasons", {
        id: "rejectionReasons",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.round_execution_catalog.fields.rejectionReasons.label", "Rejection Reasons")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.round_execution_catalog.fields.rejectionReasons.label", "Rejection Reasons"),
          placeholder: "Enter Rejection Reasons",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RoundExecutionCatalogRecord>(
            frontendComposition,
            "field:round-execution-catalog:display:rejectionReasons",
            {
              value: getValue(),
              record: row.original,
              resource: "round-execution-catalog",
              field: "rejectionReasons",
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
              {renderSlotExtensions<RoundExecutionCatalogRecord>(
                frontendComposition,
                "row-actions:round-execution-catalog:list",
                "rowActions.before",
                { resource: "round-execution-catalog", record: row.original },
              )}
                {isCommandVisible(row.original, "", "state", ["StartFailed"]) && (
                  <CommandButton
                    variant="ghost"
                    command="retryRoundExecutionAfterStartFailure"
                    recordItemId={row.original.roundExecutionId}
                    size="sm"
                    query={{
                      executionSessionId: row.original.executionSessionId,
                      executionPlanId: row.original.executionPlanId,
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                      roundNumber: row.original.roundNumber,
                      runtimeId: row.original.runtimeId,
                      organizationId: row.original.organizationId,
                      featureSchemaId: row.original.featureSchemaId,
                      baseModelId: row.original.baseModelId,
                      runtimeEngineJobId: row.original.runtimeEngineJobId,
                      retryReason: row.original.retryReason,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "state", ["Failed"]) && (
                  <CommandButton
                    variant="ghost"
                    command="retryRoundExecutionAfterRuntimeFailure"
                    recordItemId={row.original.roundExecutionId}
                    size="sm"
                    query={{
                      executionSessionId: row.original.executionSessionId,
                      executionPlanId: row.original.executionPlanId,
                      trainingJobId: row.original.trainingJobId,
                      trainingRunConfigurationId: row.original.trainingRunConfigurationId,
                      roundId: row.original.roundId,
                      runtimeId: row.original.runtimeId,
                      runtimeEngineJobId: row.original.runtimeEngineJobId,
                      retryReason: row.original.retryReason,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.roundExecutionId} size="sm" />
              {renderSlotExtensions<RoundExecutionCatalogRecord>(
                frontendComposition,
                "row-actions:round-execution-catalog:list",
                "rowActions.after",
                { resource: "round-execution-catalog", record: row.original },
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
    getRowId: (row) => String(row.roundExecutionId),
    refineCoreProps: {
      dataProviderName: "federation-learning-runtime-agent",
      syncWithLocation: false,
      meta: {
        tableName: "round_execution_catalog_read_model_entity",
        idField: "roundExecutionId",
        idFields: ["roundExecutionId"],
        queryFields: ["roundExecutionId","executionSessionId","executionPlanId","trainingJobId","trainingRunConfigurationId","roundId","roundNumber","organizationId","runtimeId","state","featureSchemaId","baseModelId","runtimeEngineProfileId","runtimeEngineProfileName","runtimeEnginePluginProfile","runtimeEngineImage","runtimeEngineImageDigest","runtimeEngineJobId","runtimeEngineObservedStatus","runtimeEngineObservationAt","localUpdateArtifactRef","metricsArtifactRef","localExecutionRequirementsSatisfied","runtimeIdentityMatched","runtimeDatasetBindingAvailable","datasetAccessValidated","baseModelAvailable","trainingConfigurationSupported","runtimeResourceAvailable","runtimeAgentIdle","updateArtifactId","artifactRef","artifactDigest","trainingLoss","receivedAt","acceptedAt","rejectedAt","startedAt","completedAt","failedAt","submittedAt","failureReason","retryReason","runtimeEngineReleased","runtimeEngineReleaseFailureReason"],
        label: t("resources.round_execution_catalog.label", "Round Execution Catalog"),
        aggregateRoute: "roundexecution",
        queryRoute: "roundexecutioncatalog",
        dataProviderName: "federation-learning-runtime-agent",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:round-execution-catalog:list", "toolbar.before", { resource: "round-execution-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:round-execution-catalog:list", "toolbar.actions", { resource: "round-execution-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:round-execution-catalog:list", "toolbar.after", { resource: "round-execution-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
