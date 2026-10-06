// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

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

export const TrainingParticipantEligibilityShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_participant_eligibility_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_participant_eligibility.label", "Training Participant Eligibility"),
      aggregateRoute: "trainingjob",
      queryRoute: "trainingparticipanteligibility",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.trainingJobId ?? t("resources.training_participant_eligibility.label", "Training Participant Eligibility")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:trainingJobId", { value: record?.trainingJobId, record, resource: "training-participant-eligibility", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.federationId.label", "Federation Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:federationId", { value: record?.federationId, record, resource: "training-participant-eligibility", field: "federationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:organizationId", { value: record?.organizationId, record, resource: "training-participant-eligibility", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:runtimeId", { value: record?.runtimeId, record, resource: "training-participant-eligibility", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "training-participant-eligibility", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.federationName.label", "Federation Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:federationName", { value: record?.federationName, record, resource: "training-participant-eligibility", field: "federationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:organizationName", { value: record?.organizationName, record, resource: "training-participant-eligibility", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:featureDomain", { value: record?.featureDomain, record, resource: "training-participant-eligibility", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "training-participant-eligibility", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.participantStatus.label", "Participant Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:participantStatus", { value: record?.participantStatus, record, resource: "training-participant-eligibility", field: "participantStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.participantStatus, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.readinessStatus.label", "Readiness Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:readinessStatus", { value: record?.readinessStatus, record, resource: "training-participant-eligibility", field: "readinessStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.readinessStatus, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.readinessStage.label", "Readiness Stage")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:readinessStage", { value: record?.readinessStage, record, resource: "training-participant-eligibility", field: "readinessStage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.readinessStage, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.eligibilityScore.label", "Eligibility Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:eligibilityScore", { value: record?.eligibilityScore, record, resource: "training-participant-eligibility", field: "eligibilityScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.eligibilityScore, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeIdentityActive.label", "Runtime Identity Active")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:runtimeIdentityActive", { value: record?.runtimeIdentityActive, record, resource: "training-participant-eligibility", field: "runtimeIdentityActive", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeIdentityActive, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeCapabilitySatisfied.label", "Runtime Capability Satisfied")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:runtimeCapabilitySatisfied", { value: record?.runtimeCapabilitySatisfied, record, resource: "training-participant-eligibility", field: "runtimeCapabilitySatisfied", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeCapabilitySatisfied, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeConnectionEstablished.label", "Runtime Connection Established")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:runtimeConnectionEstablished", { value: record?.runtimeConnectionEstablished, record, resource: "training-participant-eligibility", field: "runtimeConnectionEstablished", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeConnectionEstablished, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeHealthy.label", "Runtime Healthy")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:runtimeHealthy", { value: record?.runtimeHealthy, record, resource: "training-participant-eligibility", field: "runtimeHealthy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeHealthy, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetId.label", "Dataset Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:datasetId", { value: record?.datasetId, record, resource: "training-participant-eligibility", field: "datasetId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetName.label", "Dataset Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:datasetName", { value: record?.datasetName, record, resource: "training-participant-eligibility", field: "datasetName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetReady.label", "Dataset Ready")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:datasetReady", { value: record?.datasetReady, record, resource: "training-participant-eligibility", field: "datasetReady", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetReady, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetReadinessStatus.label", "Dataset Readiness Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:datasetReadinessStatus", { value: record?.datasetReadinessStatus, record, resource: "training-participant-eligibility", field: "datasetReadinessStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetReadinessStatus, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.matchedDatasetMetadataReady.label", "Matched Dataset Metadata Ready")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:matchedDatasetMetadataReady", { value: record?.matchedDatasetMetadataReady, record, resource: "training-participant-eligibility", field: "matchedDatasetMetadataReady", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.matchedDatasetMetadataReady, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetAccessValidated.label", "Dataset Access Validated")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:datasetAccessValidated", { value: record?.datasetAccessValidated, record, resource: "training-participant-eligibility", field: "datasetAccessValidated", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetAccessValidated, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetApprovedForTraining.label", "Dataset Approved For Training")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:datasetApprovedForTraining", { value: record?.datasetApprovedForTraining, record, resource: "training-participant-eligibility", field: "datasetApprovedForTraining", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetApprovedForTraining, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.schemaCompatible.label", "Schema Compatible")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:schemaCompatible", { value: record?.schemaCompatible, record, resource: "training-participant-eligibility", field: "schemaCompatible", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.schemaCompatible, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.labelCompatible.label", "Label Compatible")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:labelCompatible", { value: record?.labelCompatible, record, resource: "training-participant-eligibility", field: "labelCompatible", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.labelCompatible, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.qualityScore.label", "Quality Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:qualityScore", { value: record?.qualityScore, record, resource: "training-participant-eligibility", field: "qualityScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.qualityScore, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.securityReady.label", "Security Ready")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:securityReady", { value: record?.securityReady, record, resource: "training-participant-eligibility", field: "securityReady", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.securityReady, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.eligible.label", "Eligible")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:eligible", { value: record?.eligible, record, resource: "training-participant-eligibility", field: "eligible", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.eligible, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.eligibleRuntimeCount.label", "Eligible Runtime Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:eligibleRuntimeCount", { value: record?.eligibleRuntimeCount, record, resource: "training-participant-eligibility", field: "eligibleRuntimeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.eligibleRuntimeCount, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:minimumNodesPerRound", { value: record?.minimumNodesPerRound, record, resource: "training-participant-eligibility", field: "minimumNodesPerRound", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.minimumNodesPerRound, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.selectionReady.label", "Selection Ready")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:selectionReady", { value: record?.selectionReady, record, resource: "training-participant-eligibility", field: "selectionReady", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectionReady, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.eligibilityReason.label", "Eligibility Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:eligibilityReason", { value: record?.eligibilityReason, record, resource: "training-participant-eligibility", field: "eligibilityReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.eligibilityReason, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.ineligibleReasons.label", "Ineligible Reasons")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:ineligibleReasons", { value: record?.ineligibleReasons, record, resource: "training-participant-eligibility", field: "ineligibleReasons", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.ineligibleReasons, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.warningReasons.label", "Warning Reasons")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:warningReasons", { value: record?.warningReasons, record, resource: "training-participant-eligibility", field: "warningReasons", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.warningReasons, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.nextRequiredAction.label", "Next Required Action")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-participant-eligibility:display:nextRequiredAction", { value: record?.nextRequiredAction, record, resource: "training-participant-eligibility", field: "nextRequiredAction", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nextRequiredAction, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
