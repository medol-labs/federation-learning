// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { DataTableColumnHeader } from "@/components/data-table/data-table-column-header";
import { CommandButton } from "@/components/refine-ui/buttons/command";
import { ShowButton } from "@/components/refine-ui/buttons/show";
import { RefineDataTable } from "@/components/refine-ui/data-table/refine-data-table";
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";

type PersonalizedModelCatalogRecord = {
  personalizedModelId: string;
  modelUpdateSubmissionId: string;
  trainingJobId: string;
  trainingRunConfigurationId: string;
  roundId: string;
  roundNumber: number;
  runtimeId: string;
  featureSchemaId: string;
  personalizationMode?: string;
  personalizationScope?: string;
  personalizedArtifactPolicy?: string;
  personalizationMetric?: string;
  minimumPersonalizationLift?: string;
  personalizationTargetType?: string;
  personalizationTargetId?: string;
  personalizationCohortId?: string;
  personalizationProfileId?: string;
  personalizedModelArtifactRef: string;
  personalizedModelArtifactDigest: string;
  personalizedMetricsArtifactRef?: string;
  personalizedAccuracy?: string;
  personalizationLift?: string;
  state: "PersonalizedResultRecorded";
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: PersonalizedModelCatalogRecord,
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

const formatValue = (
  value: unknown,
  t: ReturnType<typeof useTranslate>,
  dictionaryLabel: ReturnType<typeof useDictionaryTranslation>["dictionaryLabel"],
  options?: Array<{ label: string; value: string }>,
  dictionaryCode?: string,
): string => {
  if (value === null || value === undefined || value === "") return "-";
  if (Array.isArray(value)) {
    const formatted: string[] = value.map((item) => formatValue(item, t, dictionaryLabel, options, dictionaryCode)).filter((item) => item !== "-");
    return formatted.length > 0 ? formatted.join(", ") : "-";
  }
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  const stringValue = String(value);
  if (dictionaryCode) return dictionaryLabel(dictionaryCode, stringValue, t(`dictionaries.${dictionaryCode}.${stringValue}`, stringValue));
  return options?.find((option) => option.value === stringValue)?.label ?? stringValue;
};

export const PersonalizedModelCatalogList = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<PersonalizedModelCatalogRecord>();
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
      columnHelper.accessor("personalizedModelId", {
        id: "personalizedModelId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizedModelId.label", "Personalized Model Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizedModelId.label", "Personalized Model Id"),
          placeholder: "Enter Personalized Model Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizedModelId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizedModelId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("modelUpdateSubmissionId", {
        id: "modelUpdateSubmissionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.modelUpdateSubmissionId.label", "Model Update Submission Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.modelUpdateSubmissionId.label", "Model Update Submission Id"),
          placeholder: "Enter Model Update Submission Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:modelUpdateSubmissionId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "modelUpdateSubmissionId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:trainingJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "trainingJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingRunConfigurationId", {
        id: "trainingRunConfigurationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id"),
          placeholder: "Enter Training Run Configuration Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:trainingRunConfigurationId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "trainingRunConfigurationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundId", {
        id: "roundId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.roundId.label", "Round Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.roundId.label", "Round Id"),
          placeholder: "Enter Round Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:roundId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "roundId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundNumber", {
        id: "roundNumber",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.roundNumber.label", "Round Number")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.roundNumber.label", "Round Number"),
          placeholder: "Enter Round Number",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:roundNumber",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "roundNumber",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeId", {
        id: "runtimeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.runtimeId.label", "Runtime Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.runtimeId.label", "Runtime Id"),
          placeholder: "Enter Runtime Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:runtimeId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "runtimeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("featureSchemaId", {
        id: "featureSchemaId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.featureSchemaId.label", "Feature Schema Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.featureSchemaId.label", "Feature Schema Id"),
          placeholder: "Enter Feature Schema Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:featureSchemaId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "featureSchemaId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("personalizationMode", {
        id: "personalizationMode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizationMode.label", "Personalization Mode")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizationMode.label", "Personalization Mode"),
          dictionaryCode: "PERSONALIZATION_MODE",
          placeholder: "Enter Personalization Mode",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizationMode",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizationMode",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, undefined, "PERSONALIZATION_MODE"),
      }),
      columnHelper.accessor("personalizationScope", {
        id: "personalizationScope",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizationScope.label", "Personalization Scope")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizationScope.label", "Personalization Scope"),
          dictionaryCode: "PERSONALIZATION_SCOPE",
          placeholder: "Enter Personalization Scope",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizationScope",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizationScope",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, undefined, "PERSONALIZATION_SCOPE"),
      }),
      columnHelper.accessor("personalizedArtifactPolicy", {
        id: "personalizedArtifactPolicy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizedArtifactPolicy.label", "Personalized Artifact Policy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizedArtifactPolicy.label", "Personalized Artifact Policy"),
          dictionaryCode: "PERSONALIZED_ARTIFACT_POLICY",
          placeholder: "Enter Personalized Artifact Policy",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizedArtifactPolicy",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizedArtifactPolicy",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, undefined, "PERSONALIZED_ARTIFACT_POLICY"),
      }),
      columnHelper.accessor("personalizationMetric", {
        id: "personalizationMetric",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizationMetric.label", "Personalization Metric")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizationMetric.label", "Personalization Metric"),
          dictionaryCode: "MODEL_EVALUATION_METRIC",
          placeholder: "Enter Personalization Metric",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizationMetric",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizationMetric",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, undefined, "MODEL_EVALUATION_METRIC"),
      }),
      columnHelper.accessor("minimumPersonalizationLift", {
        id: "minimumPersonalizationLift",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.minimumPersonalizationLift.label", "Minimum Personalization Lift")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.minimumPersonalizationLift.label", "Minimum Personalization Lift"),
          placeholder: "Enter Minimum Personalization Lift",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:minimumPersonalizationLift",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "minimumPersonalizationLift",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("personalizationTargetType", {
        id: "personalizationTargetType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizationTargetType.label", "Personalization Target Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizationTargetType.label", "Personalization Target Type"),
          dictionaryCode: "PERSONALIZATION_TARGET_TYPE",
          placeholder: "Enter Personalization Target Type",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizationTargetType",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizationTargetType",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, undefined, "PERSONALIZATION_TARGET_TYPE"),
      }),
      columnHelper.accessor("personalizationTargetId", {
        id: "personalizationTargetId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizationTargetId.label", "Personalization Target Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizationTargetId.label", "Personalization Target Id"),
          placeholder: "Enter Personalization Target Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizationTargetId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizationTargetId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("personalizationCohortId", {
        id: "personalizationCohortId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizationCohortId.label", "Personalization Cohort Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizationCohortId.label", "Personalization Cohort Id"),
          placeholder: "Enter Personalization Cohort Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizationCohortId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizationCohortId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("personalizationProfileId", {
        id: "personalizationProfileId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizationProfileId.label", "Personalization Profile Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizationProfileId.label", "Personalization Profile Id"),
          placeholder: "Enter Personalization Profile Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizationProfileId",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizationProfileId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("personalizedModelArtifactRef", {
        id: "personalizedModelArtifactRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizedModelArtifactRef.label", "Personalized Model Artifact Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizedModelArtifactRef.label", "Personalized Model Artifact Ref"),
          placeholder: "Enter Personalized Model Artifact Ref",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizedModelArtifactRef",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizedModelArtifactRef",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("personalizedModelArtifactDigest", {
        id: "personalizedModelArtifactDigest",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizedModelArtifactDigest.label", "Personalized Model Artifact Digest")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizedModelArtifactDigest.label", "Personalized Model Artifact Digest"),
          placeholder: "Enter Personalized Model Artifact Digest",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizedModelArtifactDigest",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizedModelArtifactDigest",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("personalizedMetricsArtifactRef", {
        id: "personalizedMetricsArtifactRef",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizedMetricsArtifactRef.label", "Personalized Metrics Artifact Ref")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizedMetricsArtifactRef.label", "Personalized Metrics Artifact Ref"),
          placeholder: "Enter Personalized Metrics Artifact Ref",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizedMetricsArtifactRef",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizedMetricsArtifactRef",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("personalizedAccuracy", {
        id: "personalizedAccuracy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizedAccuracy.label", "Personalized Accuracy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizedAccuracy.label", "Personalized Accuracy"),
          placeholder: "Enter Personalized Accuracy",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizedAccuracy",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizedAccuracy",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("personalizationLift", {
        id: "personalizationLift",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.personalizationLift.label", "Personalization Lift")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.personalizationLift.label", "Personalization Lift"),
          placeholder: "Enter Personalization Lift",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:personalizationLift",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "personalizationLift",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.personalized_model_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.personalized_model_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: t("resources.personalized_model_catalog.fields.state.options.PersonalizedResultRecorded", "Personalized Result Recorded"), value: "PersonalizedResultRecorded" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<PersonalizedModelCatalogRecord>(
            frontendComposition,
            "field:personalized-model-catalog:display:state",
            {
              value: getValue(),
              record: row.original,
              resource: "personalized-model-catalog",
              field: "state",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel, [
            { label: t("resources.personalized_model_catalog.fields.state.options.PersonalizedResultRecorded", "Personalized Result Recorded"), value: "PersonalizedResultRecorded" },
          ]),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<PersonalizedModelCatalogRecord>(
                frontendComposition,
                "row-actions:personalized-model-catalog:list",
                "rowActions.before",
                { resource: "personalized-model-catalog", record: row.original },
              )}
              <ShowButton variant="ghost" recordItemId={row.original.personalizedModelId} size="sm" />
              {renderSlotExtensions<PersonalizedModelCatalogRecord>(
                frontendComposition,
                "row-actions:personalized-model-catalog:list",
                "rowActions.after",
                { resource: "personalized-model-catalog", record: row.original },
              )}
            </RowActionMenu>
          </div>
        ),
        enableSorting: false,
        size: 32,
      }),
    ];
  }, [dictionaryLabel, t]);

  const table = useTable({
    columns,
    initialState: {
      columnPinning: { right: ["actions"], left: ["select"] },
    },
    getRowId: (row) => String(row.personalizedModelId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "personalized_model_catalog_read_model_entity",
        idField: "personalizedModelId",
        idFields: ["personalizedModelId"],
        queryFields: ["personalizedModelId","modelUpdateSubmissionId","trainingJobId","trainingRunConfigurationId","roundId","roundNumber","runtimeId","featureSchemaId","personalizationMode","personalizationScope","personalizedArtifactPolicy","personalizationMetric","minimumPersonalizationLift","personalizationTargetType","personalizationTargetId","personalizationCohortId","personalizationProfileId","personalizedModelArtifactRef","personalizedModelArtifactDigest","personalizedMetricsArtifactRef","personalizedAccuracy","personalizationLift","state"],
        label: t("resources.personalized_model_catalog.label", "Personalized Model Catalog"),
        aggregateRoute: "personalizedmodelresult",
        queryRoute: "personalizedmodelcatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });


  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:personalized-model-catalog:list", "toolbar.before", { resource: "personalized-model-catalog", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:personalized-model-catalog:list", "toolbar.actions", { resource: "personalized-model-catalog", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        >
        </ListToolbar>
        {renderSlotExtensions(frontendComposition, "toolbar:personalized-model-catalog:list", "toolbar.after", { resource: "personalized-model-catalog", table })}
      </RefineDataTable>
    </ListView>
  );
};
