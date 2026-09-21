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
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

const formatValue = (value: unknown, t: ReturnType<typeof useTranslate>) => {
  if (value === null || value === undefined || value === "") return "-";
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  return String(value);
};

export const ModelCatalogShow = () => {
  const t = useTranslate();
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
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:modelId", { value: record?.modelId, record, resource: "model-catalog", field: "modelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:trainingJobId", { value: record?.trainingJobId, record, resource: "model-catalog", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.finalRoundId.label", "Final Round Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:finalRoundId", { value: record?.finalRoundId, record, resource: "model-catalog", field: "finalRoundId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.finalRoundId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.modelArtifactId.label", "Model Artifact Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:modelArtifactId", { value: record?.modelArtifactId, record, resource: "model-catalog", field: "modelArtifactId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:trainingJobObjective", { value: record?.trainingJobObjective, record, resource: "model-catalog", field: "trainingJobObjective", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:modelArtifactDigest", { value: record?.modelArtifactDigest, record, resource: "model-catalog", field: "modelArtifactDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactDigest, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.evaluationReportId.label", "Evaluation Report Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:evaluationReportId", { value: record?.evaluationReportId, record, resource: "model-catalog", field: "evaluationReportId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.evaluationReportId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.finalGlobalAccuracy.label", "Final Global Accuracy")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:finalGlobalAccuracy", { value: record?.finalGlobalAccuracy, record, resource: "model-catalog", field: "finalGlobalAccuracy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.finalGlobalAccuracy, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:state", { value: record?.state, record, resource: "model-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.releaseChannel.label", "Release Channel")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:releaseChannel", { value: record?.releaseChannel, record, resource: "model-catalog", field: "releaseChannel", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.releaseChannel, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.productionStage.label", "Production Stage")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:productionStage", { value: record?.productionStage, record, resource: "model-catalog", field: "productionStage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.productionStage, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.previousModelId.label", "Previous Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:previousModelId", { value: record?.previousModelId, record, resource: "model-catalog", field: "previousModelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.previousModelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.experimentId.label", "Experiment Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:experimentId", { value: record?.experimentId, record, resource: "model-catalog", field: "experimentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.experimentId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.hyperparameterSnapshotId.label", "Hyperparameter Snapshot Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:hyperparameterSnapshotId", { value: record?.hyperparameterSnapshotId, record, resource: "model-catalog", field: "hyperparameterSnapshotId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.hyperparameterSnapshotId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.reproducibilityManifestId.label", "Reproducibility Manifest Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:reproducibilityManifestId", { value: record?.reproducibilityManifestId, record, resource: "model-catalog", field: "reproducibilityManifestId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.reproducibilityManifestId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.modelCardId.label", "Model Card Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:modelCardId", { value: record?.modelCardId, record, resource: "model-catalog", field: "modelCardId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelCardId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.baselineModelId.label", "Baseline Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:baselineModelId", { value: record?.baselineModelId, record, resource: "model-catalog", field: "baselineModelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.baselineModelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.hasEvaluationPackage.label", "Has Evaluation Package")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:hasEvaluationPackage", { value: record?.hasEvaluationPackage, record, resource: "model-catalog", field: "hasEvaluationPackage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.hasEvaluationPackage, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.approvalStatus.label", "Approval Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:approvalStatus", { value: record?.approvalStatus, record, resource: "model-catalog", field: "approvalStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.approvalStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.releaseStatus.label", "Release Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:releaseStatus", { value: record?.releaseStatus, record, resource: "model-catalog", field: "releaseStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.releaseStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.isProduction.label", "Is Production")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:isProduction", { value: record?.isProduction, record, resource: "model-catalog", field: "isProduction", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.isProduction, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.canRecordEvaluationPackage.label", "Can Record Evaluation Package")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:canRecordEvaluationPackage", { value: record?.canRecordEvaluationPackage, record, resource: "model-catalog", field: "canRecordEvaluationPackage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canRecordEvaluationPackage, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.canApprove.label", "Can Approve")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:canApprove", { value: record?.canApprove, record, resource: "model-catalog", field: "canApprove", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canApprove, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.canPromoteToProduction.label", "Can Promote To Production")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:canPromoteToProduction", { value: record?.canPromoteToProduction, record, resource: "model-catalog", field: "canPromoteToProduction", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canPromoteToProduction, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.canRollback.label", "Can Rollback")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:canRollback", { value: record?.canRollback, record, resource: "model-catalog", field: "canRollback", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canRollback, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.canRetire.label", "Can Retire")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:canRetire", { value: record?.canRetire, record, resource: "model-catalog", field: "canRetire", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canRetire, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_catalog.fields.blockedReason.label", "Blocked Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-catalog:display:blockedReason", { value: record?.blockedReason, record, resource: "model-catalog", field: "blockedReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.blockedReason, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
