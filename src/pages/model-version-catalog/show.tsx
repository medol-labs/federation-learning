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

export const ModelVersionCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "model_version_catalog_read_model_entity",
      idField: "modelVersionId",
      label: t("resources.model_version_catalog.label", "Model Version Catalog"),
      aggregateRoute: "modelversion",
      queryRoute: "modelversioncatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.modelVersionId ?? t("resources.model_version_catalog.label", "Model Version Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.modelVersionId.label", "Model Version Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelVersionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.finalRoundId.label", "Final Round Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.finalRoundId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.modelArtifactId.label", "Model Artifact Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.modelHash.label", "Model Hash")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelHash, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.evaluationReportId.label", "Evaluation Report Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.evaluationReportId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.finalGlobalAccuracy.label", "Final Global Accuracy")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.finalGlobalAccuracy, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.releaseChannel.label", "Release Channel")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.releaseChannel, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.productionStage.label", "Production Stage")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.productionStage, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.previousModelVersionId.label", "Previous Model Version Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.previousModelVersionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.experimentId.label", "Experiment Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.experimentId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.hyperparameterSnapshotId.label", "Hyperparameter Snapshot Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.hyperparameterSnapshotId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.reproducibilityManifestId.label", "Reproducibility Manifest Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.reproducibilityManifestId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.modelCardId.label", "Model Card Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelCardId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.baselineModelVersionId.label", "Baseline Model Version Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.baselineModelVersionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.hasEvaluationPackage.label", "Has Evaluation Package")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.hasEvaluationPackage, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.approvalStatus.label", "Approval Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.approvalStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.releaseStatus.label", "Release Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.releaseStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.isProduction.label", "Is Production")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.isProduction, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.canRecordEvaluationPackage.label", "Can Record Evaluation Package")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canRecordEvaluationPackage, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.canApprove.label", "Can Approve")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canApprove, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.canPromoteToProduction.label", "Can Promote To Production")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canPromoteToProduction, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.canRollback.label", "Can Rollback")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canRollback, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.canRetire.label", "Can Retire")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canRetire, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_version_catalog.fields.blockedReason.label", "Blocked Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.blockedReason, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
