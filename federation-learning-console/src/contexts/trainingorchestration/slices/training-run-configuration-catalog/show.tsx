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
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:trainingRunConfigurationId", { value: record?.trainingRunConfigurationId, record, resource: "training-run-configuration-catalog", field: "trainingRunConfigurationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.configurationName.label", "Configuration Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:configurationName", { value: record?.configurationName, record, resource: "training-run-configuration-catalog", field: "configurationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.configurationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.federationId.label", "Federation Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:federationId", { value: record?.federationId, record, resource: "training-run-configuration-catalog", field: "federationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "training-run-configuration-catalog", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelId.label", "Initial Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:initialModelId", { value: record?.initialModelId, record, resource: "training-run-configuration-catalog", field: "initialModelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelName.label", "Initial Model Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:initialModelName", { value: record?.initialModelName, record, resource: "training-run-configuration-catalog", field: "initialModelName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelPlugin.label", "Initial Model Plugin")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:initialModelPlugin", { value: record?.initialModelPlugin, record, resource: "training-run-configuration-catalog", field: "initialModelPlugin", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelPlugin, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelVersion.label", "Initial Model Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:initialModelVersion", { value: record?.initialModelVersion, record, resource: "training-run-configuration-catalog", field: "initialModelVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.federationName.label", "Federation Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:federationName", { value: record?.federationName, record, resource: "training-run-configuration-catalog", field: "federationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.federationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:featureDomain", { value: record?.featureDomain, record, resource: "training-run-configuration-catalog", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "training-run-configuration-catalog", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelArtifactUri.label", "Initial Model Artifact Uri")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:initialModelArtifactUri", { value: record?.initialModelArtifactUri, record, resource: "training-run-configuration-catalog", field: "initialModelArtifactUri", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelArtifactUri, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelRegistryRef.label", "Initial Model Registry Ref")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:initialModelRegistryRef", { value: record?.initialModelRegistryRef, record, resource: "training-run-configuration-catalog", field: "initialModelRegistryRef", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelRegistryRef, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelFormat.label", "Initial Model Format")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:initialModelFormat", { value: record?.initialModelFormat, record, resource: "training-run-configuration-catalog", field: "initialModelFormat", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelFormat, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelArtifactDigest.label", "Initial Model Artifact Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:initialModelArtifactDigest", { value: record?.initialModelArtifactDigest, record, resource: "training-run-configuration-catalog", field: "initialModelArtifactDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelArtifactDigest, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.initialModelSignatureUri.label", "Initial Model Signature Uri")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:initialModelSignatureUri", { value: record?.initialModelSignatureUri, record, resource: "training-run-configuration-catalog", field: "initialModelSignatureUri", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.initialModelSignatureUri, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEngineProfileId.label", "Runtime Engine Profile Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:runtimeEngineProfileId", { value: record?.runtimeEngineProfileId, record, resource: "training-run-configuration-catalog", field: "runtimeEngineProfileId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineProfileId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEngineProfileName.label", "Runtime Engine Profile Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:runtimeEngineProfileName", { value: record?.runtimeEngineProfileName, record, resource: "training-run-configuration-catalog", field: "runtimeEngineProfileName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineProfileName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEnginePluginProfile.label", "Runtime Engine Plugin Profile")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:runtimeEnginePluginProfile", { value: record?.runtimeEnginePluginProfile, record, resource: "training-run-configuration-catalog", field: "runtimeEnginePluginProfile", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEnginePluginProfile, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEngineImage.label", "Runtime Engine Image")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:runtimeEngineImage", { value: record?.runtimeEngineImage, record, resource: "training-run-configuration-catalog", field: "runtimeEngineImage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineImage, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.runtimeEngineImageDigest.label", "Runtime Engine Image Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:runtimeEngineImageDigest", { value: record?.runtimeEngineImageDigest, record, resource: "training-run-configuration-catalog", field: "runtimeEngineImageDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineImageDigest, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.strategyName.label", "Strategy Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:strategyName", { value: record?.strategyName, record, resource: "training-run-configuration-catalog", field: "strategyName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.strategyName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.aggregationAlgorithm.label", "Aggregation Algorithm")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:aggregationAlgorithm", { value: record?.aggregationAlgorithm, record, resource: "training-run-configuration-catalog", field: "aggregationAlgorithm", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.aggregationAlgorithm, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.maxRounds.label", "Max Rounds")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:maxRounds", { value: record?.maxRounds, record, resource: "training-run-configuration-catalog", field: "maxRounds", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.maxRounds, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:minimumNodesPerRound", { value: record?.minimumNodesPerRound, record, resource: "training-run-configuration-catalog", field: "minimumNodesPerRound", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.minimumNodesPerRound, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.roundTimeoutSeconds.label", "Round Timeout Seconds")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:roundTimeoutSeconds", { value: record?.roundTimeoutSeconds, record, resource: "training-run-configuration-catalog", field: "roundTimeoutSeconds", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundTimeoutSeconds, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.nodeResponseTimeoutSeconds.label", "Node Response Timeout Seconds")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:nodeResponseTimeoutSeconds", { value: record?.nodeResponseTimeoutSeconds, record, resource: "training-run-configuration-catalog", field: "nodeResponseTimeoutSeconds", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nodeResponseTimeoutSeconds, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.localEpochs.label", "Local Epochs")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:localEpochs", { value: record?.localEpochs, record, resource: "training-run-configuration-catalog", field: "localEpochs", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.localEpochs, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.batchSize.label", "Batch Size")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:batchSize", { value: record?.batchSize, record, resource: "training-run-configuration-catalog", field: "batchSize", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.batchSize, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.learningRate.label", "Learning Rate")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:learningRate", { value: record?.learningRate, record, resource: "training-run-configuration-catalog", field: "learningRate", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.learningRate, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.optimizer.label", "Optimizer")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:optimizer", { value: record?.optimizer, record, resource: "training-run-configuration-catalog", field: "optimizer", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.optimizer, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.lossFunction.label", "Loss Function")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:lossFunction", { value: record?.lossFunction, record, resource: "training-run-configuration-catalog", field: "lossFunction", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lossFunction, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.gradientClippingNorm.label", "Gradient Clipping Norm")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:gradientClippingNorm", { value: record?.gradientClippingNorm, record, resource: "training-run-configuration-catalog", field: "gradientClippingNorm", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.gradientClippingNorm, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.secureAggregationRequired.label", "Secure Aggregation Required")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:secureAggregationRequired", { value: record?.secureAggregationRequired, record, resource: "training-run-configuration-catalog", field: "secureAggregationRequired", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationRequired, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.minimumAccuracy.label", "Minimum Accuracy")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:minimumAccuracy", { value: record?.minimumAccuracy, record, resource: "training-run-configuration-catalog", field: "minimumAccuracy", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.minimumAccuracy, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.minimumFairnessScore.label", "Minimum Fairness Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:minimumFairnessScore", { value: record?.minimumFairnessScore, record, resource: "training-run-configuration-catalog", field: "minimumFairnessScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.minimumFairnessScore, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.updateReason.label", "Update Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:updateReason", { value: record?.updateReason, record, resource: "training-run-configuration-catalog", field: "updateReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.updateReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.lockedByTrainingJobId.label", "Locked By Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:lockedByTrainingJobId", { value: record?.lockedByTrainingJobId, record, resource: "training-run-configuration-catalog", field: "lockedByTrainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lockedByTrainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_run_configuration_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-run-configuration-catalog:display:state", { value: record?.state, record, resource: "training-run-configuration-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
