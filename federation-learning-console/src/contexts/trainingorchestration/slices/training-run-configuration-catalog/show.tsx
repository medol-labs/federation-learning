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

export const TrainingRunConfigurationCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_run_configuration_catalog_read_model_entity",
      idField: "trainingRunConfigurationId",
      label: t("resources.training_run_configuration_catalog.label", "Training Run Configuration Catalog"),
      aggregateRoute: "trainingrunconfiguration",
      queryRoute: "trainingrunconfigurationcatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.trainingRunConfigurationId ?? t("resources.training_run_configuration_catalog.label", "Training Run Configuration Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.configurationName.label", "Configuration Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.configurationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.federationId.label", "Federation Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelId.label", "Initial Model Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelName.label", "Initial Model Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelPlugin.label", "Initial Model Plugin")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelPlugin, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelVersion.label", "Initial Model Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.federationName.label", "Federation Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.featureDomain.label", "Feature Domain")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelArtifactUri.label", "Initial Model Artifact Uri")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelArtifactUri, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelRegistryRef.label", "Initial Model Registry Ref")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelRegistryRef, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelFormat.label", "Initial Model Format")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelFormat, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelArtifactDigest.label", "Initial Model Artifact Digest")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelArtifactDigest, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelSignatureUri.label", "Initial Model Signature Uri")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelSignatureUri, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEngineProfileId.label", "Runtime Engine Profile Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineProfileId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEngineProfileName.label", "Runtime Engine Profile Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineProfileName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEnginePluginProfile.label", "Runtime Engine Plugin Profile")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnginePluginProfile, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEngineImage.label", "Runtime Engine Image")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineImage, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEngineImageDigest.label", "Runtime Engine Image Digest")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineImageDigest, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.strategyName.label", "Strategy Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.strategyName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.aggregationAlgorithm.label", "Aggregation Algorithm")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.aggregationAlgorithm, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.maxRounds.label", "Max Rounds")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.maxRounds, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.minimumNodesPerRound, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.roundTimeoutSeconds.label", "Round Timeout Seconds")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundTimeoutSeconds, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.nodeResponseTimeoutSeconds.label", "Node Response Timeout Seconds")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeResponseTimeoutSeconds, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.localEpochs.label", "Local Epochs")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.localEpochs, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.batchSize.label", "Batch Size")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.batchSize, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.learningRate.label", "Learning Rate")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.learningRate, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.optimizer.label", "Optimizer")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.optimizer, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.lossFunction.label", "Loss Function")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lossFunction, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.gradientClippingNorm.label", "Gradient Clipping Norm")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.gradientClippingNorm, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.secureAggregationRequired.label", "Secure Aggregation Required")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationRequired, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.minimumAccuracy.label", "Minimum Accuracy")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.minimumAccuracy, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.minimumFairnessScore.label", "Minimum Fairness Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.minimumFairnessScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.updateReason.label", "Update Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.updateReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.lockedByTrainingJobId.label", "Locked By Training Job Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lockedByTrainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
