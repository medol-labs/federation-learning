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
        meta: {
          label: t("resources.training_participant_eligibility.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationId", {
        id: "federationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.federationId.label", "Federation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.federationId.label", "Federation Id"),
          placeholder: "Enter Federation Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.runtimeId.label", "Runtime Id"),
          placeholder: "Enter Runtime Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationName", {
        id: "federationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.federationName.label", "Federation Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.federationName.label", "Federation Name"),
          placeholder: "Enter Federation Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureDomain", {
        id: "featureDomain",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.featureDomain.label", "Feature Domain")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.featureDomain.label", "Feature Domain"),
          placeholder: "Enter Feature Domain",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaVersion", {
        id: "featureSchemaVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.featureSchemaVersion.label", "Feature Schema Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.featureSchemaVersion.label", "Feature Schema Version"),
          placeholder: "Enter Feature Schema Version",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("participantStatus", {
        id: "participantStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.participantStatus.label", "Participant Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.participantStatus.label", "Participant Status"),
          placeholder: "Enter Participant Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("readinessStatus", {
        id: "readinessStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.readinessStatus.label", "Readiness Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.readinessStatus.label", "Readiness Status"),
          placeholder: "Enter Readiness Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("readinessStage", {
        id: "readinessStage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.readinessStage.label", "Readiness Stage")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.readinessStage.label", "Readiness Stage"),
          placeholder: "Enter Readiness Stage",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("eligibilityScore", {
        id: "eligibilityScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.eligibilityScore.label", "Eligibility Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.eligibilityScore.label", "Eligibility Score"),
          placeholder: "Enter Eligibility Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeIdentityActive", {
        id: "runtimeIdentityActive",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeIdentityActive.label", "Runtime Identity Active")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.runtimeIdentityActive.label", "Runtime Identity Active"),
          placeholder: "Enter Runtime Identity Active",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeCapabilitySatisfied", {
        id: "runtimeCapabilitySatisfied",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeCapabilitySatisfied.label", "Runtime Capability Satisfied")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.runtimeCapabilitySatisfied.label", "Runtime Capability Satisfied"),
          placeholder: "Enter Runtime Capability Satisfied",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeConnectionEstablished", {
        id: "runtimeConnectionEstablished",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeConnectionEstablished.label", "Runtime Connection Established")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.runtimeConnectionEstablished.label", "Runtime Connection Established"),
          placeholder: "Enter Runtime Connection Established",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("runtimeHealthy", {
        id: "runtimeHealthy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.runtimeHealthy.label", "Runtime Healthy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.runtimeHealthy.label", "Runtime Healthy"),
          placeholder: "Enter Runtime Healthy",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetId", {
        id: "datasetId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetId.label", "Dataset Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.datasetId.label", "Dataset Id"),
          placeholder: "Enter Dataset Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetName", {
        id: "datasetName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetName.label", "Dataset Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.datasetName.label", "Dataset Name"),
          placeholder: "Enter Dataset Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("datasetReady", {
        id: "datasetReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetReady.label", "Dataset Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.datasetReady.label", "Dataset Ready"),
          placeholder: "Enter Dataset Ready",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetReadinessStatus", {
        id: "datasetReadinessStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetReadinessStatus.label", "Dataset Readiness Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.datasetReadinessStatus.label", "Dataset Readiness Status"),
          placeholder: "Enter Dataset Readiness Status",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("matchedDatasetMetadataReady", {
        id: "matchedDatasetMetadataReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.matchedDatasetMetadataReady.label", "Matched Dataset Metadata Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.matchedDatasetMetadataReady.label", "Matched Dataset Metadata Ready"),
          placeholder: "Enter Matched Dataset Metadata Ready",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetAccessValidated", {
        id: "datasetAccessValidated",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetAccessValidated.label", "Dataset Access Validated")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.datasetAccessValidated.label", "Dataset Access Validated"),
          placeholder: "Enter Dataset Access Validated",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("datasetApprovedForTraining", {
        id: "datasetApprovedForTraining",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.datasetApprovedForTraining.label", "Dataset Approved For Training")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.datasetApprovedForTraining.label", "Dataset Approved For Training"),
          placeholder: "Enter Dataset Approved For Training",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("schemaCompatible", {
        id: "schemaCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.schemaCompatible.label", "Schema Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.schemaCompatible.label", "Schema Compatible"),
          placeholder: "Enter Schema Compatible",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("labelCompatible", {
        id: "labelCompatible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.labelCompatible.label", "Label Compatible")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.labelCompatible.label", "Label Compatible"),
          placeholder: "Enter Label Compatible",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("qualityScore", {
        id: "qualityScore",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.qualityScore.label", "Quality Score")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.qualityScore.label", "Quality Score"),
          placeholder: "Enter Quality Score",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("securityReady", {
        id: "securityReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.securityReady.label", "Security Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.securityReady.label", "Security Ready"),
          placeholder: "Enter Security Ready",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("eligible", {
        id: "eligible",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.eligible.label", "Eligible")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.eligible.label", "Eligible"),
          placeholder: "Enter Eligible",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("eligibleRuntimeCount", {
        id: "eligibleRuntimeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.eligibleRuntimeCount.label", "Eligible Runtime Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.eligibleRuntimeCount.label", "Eligible Runtime Count"),
          placeholder: "Enter Eligible Runtime Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("minimumNodesPerRound", {
        id: "minimumNodesPerRound",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round"),
          placeholder: "Enter Minimum Nodes Per Round",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("selectionReady", {
        id: "selectionReady",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.selectionReady.label", "Selection Ready")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.selectionReady.label", "Selection Ready"),
          placeholder: "Enter Selection Ready",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("eligibilityReason", {
        id: "eligibilityReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.eligibilityReason.label", "Eligibility Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.eligibilityReason.label", "Eligibility Reason"),
          placeholder: "Enter Eligibility Reason",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("ineligibleReasons", {
        id: "ineligibleReasons",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.ineligibleReasons.label", "Ineligible Reasons")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.training_participant_eligibility.fields.ineligibleReasons.label", "Ineligible Reasons"),
          placeholder: "Enter Ineligible Reasons",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("warningReasons", {
        id: "warningReasons",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.warningReasons.label", "Warning Reasons")} />
        ),
        enableSorting: true,
        enableColumnFilter: false,
        meta: {
          label: t("resources.training_participant_eligibility.fields.warningReasons.label", "Warning Reasons"),
          placeholder: "Enter Warning Reasons",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("nextRequiredAction", {
        id: "nextRequiredAction",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.training_participant_eligibility.fields.nextRequiredAction.label", "Next Required Action")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.training_participant_eligibility.fields.nextRequiredAction.label", "Next Required Action"),
          placeholder: "Enter Next Required Action",
          variant: "text",
        },
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
                {isCommandVisible(row.original, "", "participantStatus", ["Draft"]) && (
                <DropdownMenuItem>
                  <CommandButton
                    variant="ghost"
                    command="submitTrainingJob"
                    recordItemId={row.original.trainingJobId}
                    size="sm"
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
      syncWithLocation: false,
      meta: {
        tableName: "training_participant_eligibility_read_model_entity",
        idField: "trainingJobId",
        idFields: ["trainingJobId"],
        queryFields: ["trainingJobId","federationId","organizationId","runtimeId","featureSchemaId","federationName","organizationName","featureDomain","featureSchemaVersion","participantStatus","readinessStatus","readinessStage","eligibilityScore","runtimeIdentityActive","runtimeCapabilitySatisfied","runtimeConnectionEstablished","runtimeHealthy","datasetId","datasetName","datasetReady","datasetReadinessStatus","matchedDatasetMetadataReady","datasetAccessValidated","datasetApprovedForTraining","schemaCompatible","labelCompatible","qualityScore","securityReady","eligible","eligibleRuntimeCount","minimumNodesPerRound","selectionReady","eligibilityReason","nextRequiredAction"],
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
