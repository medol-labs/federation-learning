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
    dataProviderName: "flplatform-backend",
    meta: {
      tableName: "model_artifact_catalog_read_model_entity",
      idField: "modelVersionId",
      label: t("resources.model_artifact_catalog.label", "Model Artifact Catalog"),
      aggregateRoute: "modelartifact",
      queryRoute: "modelartifactcatalog",
      dataProviderName: "flplatform-backend",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.modelVersionId ?? t("resources.model_artifact_catalog.label", "Model Artifact Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelVersionId.label", "Model Version Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelVersionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelArtifactRef.label", "Model Artifact Ref")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactRef, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelRepositoryRef.label", "Model Repository Ref")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelRepositoryRef, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelFormat.label", "Model Format")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelFormat, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelHash.label", "Model Hash")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelHash, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelSignatureRef.label", "Model Signature Ref")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelSignatureRef, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.modelSizeBytes.label", "Model Size Bytes")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelSizeBytes, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.model_artifact_catalog.fields.sourceType.label", "Source Type")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sourceType, t)}</p>
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
