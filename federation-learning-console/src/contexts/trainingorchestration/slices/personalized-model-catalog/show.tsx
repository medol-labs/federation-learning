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

export const PersonalizedModelCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "personalized_model_catalog_read_model_entity",
      idField: "personalizedModelId",
      label: t("resources.personalized_model_catalog.label", "Personalized Model Catalog"),
      aggregateRoute: "personalizedmodelresult",
      queryRoute: "personalizedmodelcatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.personalizedModelId ?? t("resources.personalized_model_catalog.label", "Personalized Model Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizedModelId.label", "Personalized Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizedModelId", { value: record?.personalizedModelId, record, resource: "personalized-model-catalog", field: "personalizedModelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizedModelId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.modelUpdateSubmissionId.label", "Model Update Submission Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:modelUpdateSubmissionId", { value: record?.modelUpdateSubmissionId, record, resource: "personalized-model-catalog", field: "modelUpdateSubmissionId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelUpdateSubmissionId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:trainingJobId", { value: record?.trainingJobId, record, resource: "personalized-model-catalog", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:trainingRunConfigurationId", { value: record?.trainingRunConfigurationId, record, resource: "personalized-model-catalog", field: "trainingRunConfigurationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.roundId.label", "Round Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:roundId", { value: record?.roundId, record, resource: "personalized-model-catalog", field: "roundId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.roundNumber.label", "Round Number")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:roundNumber", { value: record?.roundNumber, record, resource: "personalized-model-catalog", field: "roundNumber", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundNumber, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:runtimeId", { value: record?.runtimeId, record, resource: "personalized-model-catalog", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "personalized-model-catalog", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizationMode.label", "Personalization Mode")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizationMode", { value: record?.personalizationMode, record, resource: "personalized-model-catalog", field: "personalizationMode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizationMode, t, dictionaryLabel, undefined, "PERSONALIZATION_MODE")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizationScope.label", "Personalization Scope")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizationScope", { value: record?.personalizationScope, record, resource: "personalized-model-catalog", field: "personalizationScope", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizationScope, t, dictionaryLabel, undefined, "PERSONALIZATION_SCOPE")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizedArtifactPolicy.label", "Personalized Artifact Policy")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizedArtifactPolicy", { value: record?.personalizedArtifactPolicy, record, resource: "personalized-model-catalog", field: "personalizedArtifactPolicy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizedArtifactPolicy, t, dictionaryLabel, undefined, "PERSONALIZED_ARTIFACT_POLICY")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizationMetric.label", "Personalization Metric")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizationMetric", { value: record?.personalizationMetric, record, resource: "personalized-model-catalog", field: "personalizationMetric", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizationMetric, t, dictionaryLabel, undefined, "MODEL_EVALUATION_METRIC")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.minimumPersonalizationLift.label", "Minimum Personalization Lift")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:minimumPersonalizationLift", { value: record?.minimumPersonalizationLift, record, resource: "personalized-model-catalog", field: "minimumPersonalizationLift", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.minimumPersonalizationLift, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizationTargetType.label", "Personalization Target Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizationTargetType", { value: record?.personalizationTargetType, record, resource: "personalized-model-catalog", field: "personalizationTargetType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizationTargetType, t, dictionaryLabel, undefined, "PERSONALIZATION_TARGET_TYPE")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizationTargetId.label", "Personalization Target Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizationTargetId", { value: record?.personalizationTargetId, record, resource: "personalized-model-catalog", field: "personalizationTargetId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizationTargetId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizationCohortId.label", "Personalization Cohort Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizationCohortId", { value: record?.personalizationCohortId, record, resource: "personalized-model-catalog", field: "personalizationCohortId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizationCohortId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizationProfileId.label", "Personalization Profile Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizationProfileId", { value: record?.personalizationProfileId, record, resource: "personalized-model-catalog", field: "personalizationProfileId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizationProfileId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizedModelArtifactRef.label", "Personalized Model Artifact Ref")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizedModelArtifactRef", { value: record?.personalizedModelArtifactRef, record, resource: "personalized-model-catalog", field: "personalizedModelArtifactRef", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizedModelArtifactRef, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizedModelArtifactDigest.label", "Personalized Model Artifact Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizedModelArtifactDigest", { value: record?.personalizedModelArtifactDigest, record, resource: "personalized-model-catalog", field: "personalizedModelArtifactDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizedModelArtifactDigest, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizedMetricsArtifactRef.label", "Personalized Metrics Artifact Ref")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizedMetricsArtifactRef", { value: record?.personalizedMetricsArtifactRef, record, resource: "personalized-model-catalog", field: "personalizedMetricsArtifactRef", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizedMetricsArtifactRef, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizedAccuracy.label", "Personalized Accuracy")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizedAccuracy", { value: record?.personalizedAccuracy, record, resource: "personalized-model-catalog", field: "personalizedAccuracy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizedAccuracy, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.personalizationLift.label", "Personalization Lift")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:personalizationLift", { value: record?.personalizationLift, record, resource: "personalized-model-catalog", field: "personalizationLift", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.personalizationLift, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.personalized_model_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:personalized-model-catalog:display:state", { value: record?.state, record, resource: "personalized-model-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t, dictionaryLabel, [
                { label: t("resources.personalized_model_catalog.fields.state.options.PersonalizedResultRecorded", "Personalized Result Recorded"), value: "PersonalizedResultRecorded" },
              ])}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
