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

export const ModelArtifactCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "model_artifact_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_artifact_catalog.label", "Model Artifact Catalog"),
      aggregateRoute: "modelartifact",
      queryRoute: "modelartifactcatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.modelId ?? t("resources.model_artifact_catalog.label", "Model Artifact Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelId.label", "Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelId", { value: record?.modelId, record, resource: "model-artifact-catalog", field: "modelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelName.label", "Model Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelName", { value: record?.modelName, record, resource: "model-artifact-catalog", field: "modelName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelPlugin.label", "Model Plugin")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelPlugin", { value: record?.modelPlugin, record, resource: "model-artifact-catalog", field: "modelPlugin", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelPlugin, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelVersion.label", "Model Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelVersion", { value: record?.modelVersion, record, resource: "model-artifact-catalog", field: "modelVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelDescription.label", "Model Description")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelDescription", { value: record?.modelDescription, record, resource: "model-artifact-catalog", field: "modelDescription", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelDescription, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.sourceType.label", "Source Type")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:sourceType", { value: record?.sourceType, record, resource: "model-artifact-catalog", field: "sourceType", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.sourceType, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelArtifactUri.label", "Model Artifact Uri")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelArtifactUri", { value: record?.modelArtifactUri, record, resource: "model-artifact-catalog", field: "modelArtifactUri", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactUri, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelRegistryRef.label", "Model Registry Ref")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelRegistryRef", { value: record?.modelRegistryRef, record, resource: "model-artifact-catalog", field: "modelRegistryRef", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelRegistryRef, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelFormat.label", "Model Format")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelFormat", { value: record?.modelFormat, record, resource: "model-artifact-catalog", field: "modelFormat", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelFormat, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelArtifactDigest", { value: record?.modelArtifactDigest, record, resource: "model-artifact-catalog", field: "modelArtifactDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactDigest, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelSignatureUri.label", "Model Signature Uri")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelSignatureUri", { value: record?.modelSignatureUri, record, resource: "model-artifact-catalog", field: "modelSignatureUri", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelSignatureUri, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelSizeBytes.label", "Model Size Bytes")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:modelSizeBytes", { value: record?.modelSizeBytes, record, resource: "model-artifact-catalog", field: "modelSizeBytes", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelSizeBytes, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:trainingJobId", { value: record?.trainingJobId, record, resource: "model-artifact-catalog", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.roundId.label", "Round Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:roundId", { value: record?.roundId, record, resource: "model-artifact-catalog", field: "roundId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:trainingJobObjective", { value: record?.trainingJobObjective, record, resource: "model-artifact-catalog", field: "trainingJobObjective", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:state", { value: record?.state, record, resource: "model-artifact-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.registeredAt.label", "Registered At")}</h4>
              {renderFieldOverride(frontendComposition, "field:model-artifact-catalog:display:registeredAt", { value: record?.registeredAt, record, resource: "model-artifact-catalog", field: "registeredAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.registeredAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
