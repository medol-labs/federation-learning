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

type TrainingParticipantEligibilityRecord = {
  trainingJobId: string;
  federationId: string;
  organizationId: string;
  runtimeId?: string;
  featureSchemaId: string;
  federationName?: string;
  organizationName?: string;
  featureDomain?: string;
  featureSchemaVersion?: string;
  participantStatus: string;
  readinessStatus: string;
  readinessStage: string;
  eligibilityScore: number;
  runtimeIdentityActive: boolean;
  runtimeCapabilitySatisfied: boolean;
  runtimeConnectionEstablished: boolean;
  runtimeHealthy: boolean;
  datasetId?: string;
  datasetName?: string;
  datasetReady: boolean;
  datasetReadinessStatus: string;
  matchedDatasetMetadataReady: boolean;
  datasetAccessValidated: boolean;
  datasetApprovedForTraining: boolean;
  schemaCompatible?: boolean;
  labelCompatible?: boolean;
  qualityScore?: string;
  securityReady: boolean;
  eligible: boolean;
  eligibleRuntimeCount: number;
  minimumNodesPerRound: number;
  selectionReady: boolean;
  eligibilityReason?: string;
  ineligibleReasons: string[];
  warningReasons: string[];
  nextRequiredAction?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: TrainingParticipantEligibilityRecord,
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

export const TrainingParticipantEligibilityList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<TrainingParticipantEligibilityRecord>();
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
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationId", {
        id: "federationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.federationId.label", "Federation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationName", {
        id: "federationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.federationName.label", "Federation Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("participantStatus", {
        id: "participantStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.participantStatus.label", "Participant Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("readinessStatus", {
        id: "readinessStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.readinessStatus.label", "Readiness Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("readinessStage", {
        id: "readinessStage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.readinessStage.label", "Readiness Stage")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("eligibilityScore", {
        id: "eligibilityScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.eligibilityScore.label", "Eligibility Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeIdentityActive", {
        id: "runtimeIdentityActive",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeIdentityActive.label", "Runtime Identity Active")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeCapabilitySatisfied", {
        id: "runtimeCapabilitySatisfied",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeCapabilitySatisfied.label", "Runtime Capability Satisfied")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeConnectionEstablished", {
        id: "runtimeConnectionEstablished",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeConnectionEstablished.label", "Runtime Connection Established")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeHealthy", {
        id: "runtimeHealthy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeHealthy.label", "Runtime Healthy")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetId", {
        id: "datasetId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetId.label", "Dataset Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetName", {
        id: "datasetName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetName.label", "Dataset Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetReady", {
        id: "datasetReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetReady.label", "Dataset Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetReadinessStatus", {
        id: "datasetReadinessStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetReadinessStatus.label", "Dataset Readiness Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("matchedDatasetMetadataReady", {
        id: "matchedDatasetMetadataReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.matchedDatasetMetadataReady.label", "Matched Dataset Metadata Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetAccessValidated", {
        id: "datasetAccessValidated",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetAccessValidated.label", "Dataset Access Validated")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetApprovedForTraining", {
        id: "datasetApprovedForTraining",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetApprovedForTraining.label", "Dataset Approved For Training")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("schemaCompatible", {
        id: "schemaCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.schemaCompatible.label", "Schema Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("labelCompatible", {
        id: "labelCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.labelCompatible.label", "Label Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("qualityScore", {
        id: "qualityScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.qualityScore.label", "Quality Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("securityReady", {
        id: "securityReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.securityReady.label", "Security Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("eligible", {
        id: "eligible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.eligible.label", "Eligible")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("eligibleRuntimeCount", {
        id: "eligibleRuntimeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.eligibleRuntimeCount.label", "Eligible Runtime Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("minimumNodesPerRound", {
        id: "minimumNodesPerRound",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectionReady", {
        id: "selectionReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.selectionReady.label", "Selection Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("eligibilityReason", {
        id: "eligibilityReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.eligibilityReason.label", "Eligibility Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("ineligibleReasons", {
        id: "ineligibleReasons",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.ineligibleReasons.label", "Ineligible Reasons")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("warningReasons", {
        id: "warningReasons",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.warningReasons.label", "Warning Reasons")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nextRequiredAction", {
        id: "nextRequiredAction",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.nextRequiredAction.label", "Next Required Action")} />
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
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                {isCommandVisible(row.original, "", "participantStatus", ["Active"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="suspendParticipant"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "participantStatus", ["Suspended"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="removeParticipant"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="inviteParticipant"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                {isCommandVisible(row.original, "", "", []) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="configureRuntimeDatasetBinding"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
                    query={{
                      datasetId: row.original.datasetId,
                      organizationId: row.original.organizationId,
                      runtimeId: row.original.runtimeId,
                    }}
                  />
                </DropdownMenuItem>
                )}
                <DropdownMenuItem>
                  <ShowButton variant="ghost" recordItemId={row.original.trainingJobId} size="sm" />
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
    getRowId: (row) => String(row.trainingJobId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: true,
      meta: {
        tableName: "training_participant_eligibility_read_model_entity",
        idField: "trainingJobId",
        idFields: ["trainingJobId"],
        label: t("resources.training_participant_eligibility.label", "Training Participant Eligibility"),
        aggregateRoute: "trainingjob",
        queryRoute: "trainingparticipanteligibility",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="createTrainingJob" />
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
