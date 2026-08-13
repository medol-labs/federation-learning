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
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelName.label", "Model Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelVersion.label", "Model Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.sourceType.label", "Source Type")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sourceType, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelArtifactUri.label", "Model Artifact Uri")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactUri, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelRegistryRef.label", "Model Registry Ref")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelRegistryRef, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelFormat.label", "Model Format")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelFormat, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactDigest, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelSignatureUri.label", "Model Signature Uri")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelSignatureUri, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelSizeBytes.label", "Model Size Bytes")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelSizeBytes, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.roundId.label", "Round Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.registeredAt.label", "Registered At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.registeredAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
