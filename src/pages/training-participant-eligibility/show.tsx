// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";

const formatValue = (value: unknown, t: ReturnType<typeof useTranslate>) => {
  if (value === null || value === undefined || value === "") return "-";
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  return String(value);
};

export const TrainingParticipantEligibilityShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flplatform-backend",
    meta: {
      tableName: "training_participant_eligibility_read_model_entity",
      idField: "trainingJobId",
      label: t("resources.training_participant_eligibility.label", "Training Participant Eligibility"),
      aggregateRoute: "trainingjob",
      queryRoute: "trainingparticipanteligibility",
      dataProviderName: "flplatform-backend",
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.federationId.label", "Federation Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeId.label", "Runtime Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.federationName.label", "Federation Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.featureDomain.label", "Feature Domain")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.participantStatus.label", "Participant Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.participantStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.readinessStatus.label", "Readiness Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.readinessStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.readinessStage.label", "Readiness Stage")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.readinessStage, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.eligibilityScore.label", "Eligibility Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.eligibilityScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeIdentityActive.label", "Runtime Identity Active")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeIdentityActive, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeCapabilitySatisfied.label", "Runtime Capability Satisfied")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeCapabilitySatisfied, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeConnectionEstablished.label", "Runtime Connection Established")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeConnectionEstablished, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.runtimeHealthy.label", "Runtime Healthy")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeHealthy, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetId.label", "Dataset Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetName.label", "Dataset Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetReady.label", "Dataset Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetReadinessStatus.label", "Dataset Readiness Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetReadinessStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.matchedDatasetMetadataReady.label", "Matched Dataset Metadata Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.matchedDatasetMetadataReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetAccessValidated.label", "Dataset Access Validated")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetAccessValidated, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.datasetApprovedForTraining.label", "Dataset Approved For Training")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetApprovedForTraining, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.schemaCompatible.label", "Schema Compatible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.schemaCompatible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.labelCompatible.label", "Label Compatible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.labelCompatible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.qualityScore.label", "Quality Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.qualityScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.securityReady.label", "Security Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.securityReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.eligible.label", "Eligible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.eligible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.eligibleRuntimeCount.label", "Eligible Runtime Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.eligibleRuntimeCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.minimumNodesPerRound, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.selectionReady.label", "Selection Ready")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.selectionReady, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.eligibilityReason.label", "Eligibility Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.eligibilityReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.ineligibleReasons.label", "Ineligible Reasons")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.ineligibleReasons, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.warningReasons.label", "Warning Reasons")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.warningReasons, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_participant_eligibility.fields.nextRequiredAction.label", "Next Required Action")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nextRequiredAction, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
