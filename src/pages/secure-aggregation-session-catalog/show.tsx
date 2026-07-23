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

export const SecureAggregationSessionCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "secure_aggregation_session_catalog_read_model_entity",
      idField: "secureAggregationSessionId",
      label: t("resources.secure_aggregation_session_catalog.label", "Secure Aggregation Session Catalog"),
      aggregateRoute: "secureaggregationsession",
      queryRoute: "secureaggregationsessioncatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.secureAggregationSessionId ?? t("resources.secure_aggregation_session_catalog.label", "Secure Aggregation Session Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.secureAggregationSessionId.label", "Secure Aggregation Session Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationSessionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.roundId.label", "Round Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.roundId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.requiredParticipantCount.label", "Required Participant Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.requiredParticipantCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.acceptedRuntimeIds.label", "Accepted Runtime Ids")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.acceptedRuntimeIds, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.selectedRuntimeIds.label", "Selected Runtime Ids")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.selectedRuntimeIds, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.selectedParticipantCount.label", "Selected Participant Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.selectedParticipantCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.encryptionContextPrepared.label", "Encryption Context Prepared")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.encryptionContextPrepared, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.receivedEncryptedUpdateCount.label", "Received Encrypted Update Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.receivedEncryptedUpdateCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.encryptionScheme.label", "Encryption Scheme")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.encryptionScheme, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.publicKeyVersion.label", "Public Key Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.publicKeyVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.encryptedParameterScale.label", "Encrypted Parameter Scale")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.encryptedParameterScale, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.aggregatedModelVersionId.label", "Aggregated Model Version Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.aggregatedModelVersionId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.modelFormat.label", "Model Format")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelFormat, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.modelHash.label", "Model Hash")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.modelHash, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.failureReason.label", "Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.createdAt.label", "Created At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.createdAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.selectedAt.label", "Selected At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.selectedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.encryptionContextPreparedAt.label", "Encryption Context Prepared At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.encryptionContextPreparedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.decryptedAt.label", "Decrypted At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.decryptedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.completedAt.label", "Completed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.completedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.failedAt.label", "Failed At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failedAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
