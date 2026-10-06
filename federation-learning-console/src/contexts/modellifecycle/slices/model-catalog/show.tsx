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

export const ModelCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "model_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_catalog.label", "Model Catalog"),
      aggregateRoute: "model",
      queryRoute: "modelcatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.modelId ?? t("resources.model_catalog.label", "Model Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.modelId.label", "Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:modelId", { value: record?.modelId, record, resource: "model-catalog", field: "modelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:trainingJobId", { value: record?.trainingJobId, record, resource: "model-catalog", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.finalRoundId.label", "Final Round Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:finalRoundId", { value: record?.finalRoundId, record, resource: "model-catalog", field: "finalRoundId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.finalRoundId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.modelArtifactId.label", "Model Artifact Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:modelArtifactId", { value: record?.modelArtifactId, record, resource: "model-catalog", field: "modelArtifactId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:trainingJobObjective", { value: record?.trainingJobObjective, record, resource: "model-catalog", field: "trainingJobObjective", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:modelArtifactDigest", { value: record?.modelArtifactDigest, record, resource: "model-catalog", field: "modelArtifactDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactDigest, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.evaluationReportId.label", "Evaluation Report Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:evaluationReportId", { value: record?.evaluationReportId, record, resource: "model-catalog", field: "evaluationReportId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.evaluationReportId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.finalGlobalAccuracy.label", "Final Global Accuracy")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:finalGlobalAccuracy", { value: record?.finalGlobalAccuracy, record, resource: "model-catalog", field: "finalGlobalAccuracy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.finalGlobalAccuracy, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:state", { value: record?.state, record, resource: "model-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t, dictionaryLabel, [
                { label: t("resources.model_catalog.fields.state.options.Candidate", "Candidate"), value: "Candidate" },
                { label: t("resources.model_catalog.fields.state.options.EvaluationPackaged", "Evaluation Packaged"), value: "EvaluationPackaged" },
                { label: t("resources.model_catalog.fields.state.options.Approved", "Approved"), value: "Approved" },
                { label: t("resources.model_catalog.fields.state.options.Production", "Production"), value: "Production" },
                { label: t("resources.model_catalog.fields.state.options.RolledBack", "Rolled Back"), value: "RolledBack" },
                { label: t("resources.model_catalog.fields.state.options.Retired", "Retired"), value: "Retired" },
              ])}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.releaseChannel.label", "Release Channel")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:releaseChannel", { value: record?.releaseChannel, record, resource: "model-catalog", field: "releaseChannel", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.releaseChannel, t, dictionaryLabel, undefined, "MODEL_RELEASE_CHANNEL")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.productionStage.label", "Production Stage")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:productionStage", { value: record?.productionStage, record, resource: "model-catalog", field: "productionStage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.productionStage, t, dictionaryLabel, undefined, "MODEL_PRODUCTION_STAGE")}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.experimentId.label", "Experiment Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:experimentId", { value: record?.experimentId, record, resource: "model-catalog", field: "experimentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.experimentId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.hyperparameterSnapshotId.label", "Hyperparameter Snapshot Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:hyperparameterSnapshotId", { value: record?.hyperparameterSnapshotId, record, resource: "model-catalog", field: "hyperparameterSnapshotId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.hyperparameterSnapshotId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.reproducibilityManifestId.label", "Reproducibility Manifest Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:reproducibilityManifestId", { value: record?.reproducibilityManifestId, record, resource: "model-catalog", field: "reproducibilityManifestId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.reproducibilityManifestId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.modelCardId.label", "Model Card Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:modelCardId", { value: record?.modelCardId, record, resource: "model-catalog", field: "modelCardId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelCardId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
