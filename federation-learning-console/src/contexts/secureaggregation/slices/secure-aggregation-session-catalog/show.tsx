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
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:secureAggregationSessionId", { value: record?.secureAggregationSessionId, record, resource: "secure-aggregation-session-catalog", field: "secureAggregationSessionId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.secureAggregationSessionId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:trainingJobId", { value: record?.trainingJobId, record, resource: "secure-aggregation-session-catalog", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:trainingRunConfigurationId", { value: record?.trainingRunConfigurationId, record, resource: "secure-aggregation-session-catalog", field: "trainingRunConfigurationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingRunConfigurationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "secure-aggregation-session-catalog", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.roundId.label", "Round Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:roundId", { value: record?.roundId, record, resource: "secure-aggregation-session-catalog", field: "roundId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.roundNumber.label", "Round Number")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:roundNumber", { value: record?.roundNumber, record, resource: "secure-aggregation-session-catalog", field: "roundNumber", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.roundNumber, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.requiredParticipantCount.label", "Required Participant Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:requiredParticipantCount", { value: record?.requiredParticipantCount, record, resource: "secure-aggregation-session-catalog", field: "requiredParticipantCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.requiredParticipantCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.selectedOrganizationIds.label", "Selected Organization Ids")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:selectedOrganizationIds", { value: record?.selectedOrganizationIds, record, resource: "secure-aggregation-session-catalog", field: "selectedOrganizationIds", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedOrganizationIds, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.selectedRuntimeIds.label", "Selected Runtime Ids")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:selectedRuntimeIds", { value: record?.selectedRuntimeIds, record, resource: "secure-aggregation-session-catalog", field: "selectedRuntimeIds", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedRuntimeIds, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.selectedOrganizationCount.label", "Selected Organization Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:selectedOrganizationCount", { value: record?.selectedOrganizationCount, record, resource: "secure-aggregation-session-catalog", field: "selectedOrganizationCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedOrganizationCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.selectedRuntimeCount.label", "Selected Runtime Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:selectedRuntimeCount", { value: record?.selectedRuntimeCount, record, resource: "secure-aggregation-session-catalog", field: "selectedRuntimeCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedRuntimeCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.selectedParticipantCount.label", "Selected Participant Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:selectedParticipantCount", { value: record?.selectedParticipantCount, record, resource: "secure-aggregation-session-catalog", field: "selectedParticipantCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedParticipantCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.encryptionContextPrepared.label", "Encryption Context Prepared")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:encryptionContextPrepared", { value: record?.encryptionContextPrepared, record, resource: "secure-aggregation-session-catalog", field: "encryptionContextPrepared", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.encryptionContextPrepared, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.receivedEncryptedUpdateCount.label", "Received Encrypted Update Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:receivedEncryptedUpdateCount", { value: record?.receivedEncryptedUpdateCount, record, resource: "secure-aggregation-session-catalog", field: "receivedEncryptedUpdateCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.receivedEncryptedUpdateCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.encryptionScheme.label", "Encryption Scheme")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:encryptionScheme", { value: record?.encryptionScheme, record, resource: "secure-aggregation-session-catalog", field: "encryptionScheme", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.encryptionScheme, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.publicKeyVersion.label", "Public Key Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:publicKeyVersion", { value: record?.publicKeyVersion, record, resource: "secure-aggregation-session-catalog", field: "publicKeyVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.publicKeyVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.publicKeyRef.label", "Public Key Ref")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:publicKeyRef", { value: record?.publicKeyRef, record, resource: "secure-aggregation-session-catalog", field: "publicKeyRef", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.publicKeyRef, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.encryptedParameterScale.label", "Encrypted Parameter Scale")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:encryptedParameterScale", { value: record?.encryptedParameterScale, record, resource: "secure-aggregation-session-catalog", field: "encryptedParameterScale", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.encryptedParameterScale, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.aggregatedModelId.label", "Aggregated Model Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:aggregatedModelId", { value: record?.aggregatedModelId, record, resource: "secure-aggregation-session-catalog", field: "aggregatedModelId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.aggregatedModelId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.modelFormat.label", "Model Format")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:modelFormat", { value: record?.modelFormat, record, resource: "secure-aggregation-session-catalog", field: "modelFormat", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelFormat, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.modelArtifactDigest.label", "Model Artifact Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:modelArtifactDigest", { value: record?.modelArtifactDigest, record, resource: "secure-aggregation-session-catalog", field: "modelArtifactDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.modelArtifactDigest, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:state", { value: record?.state, record, resource: "secure-aggregation-session-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.failureReason.label", "Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:failureReason", { value: record?.failureReason, record, resource: "secure-aggregation-session-catalog", field: "failureReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.createdAt.label", "Created At")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:createdAt", { value: record?.createdAt, record, resource: "secure-aggregation-session-catalog", field: "createdAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.createdAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.selectedAt.label", "Selected At")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:selectedAt", { value: record?.selectedAt, record, resource: "secure-aggregation-session-catalog", field: "selectedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.selectedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.encryptionContextPreparedAt.label", "Encryption Context Prepared At")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:encryptionContextPreparedAt", { value: record?.encryptionContextPreparedAt, record, resource: "secure-aggregation-session-catalog", field: "encryptionContextPreparedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.encryptionContextPreparedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.decryptedAt.label", "Decrypted At")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:decryptedAt", { value: record?.decryptedAt, record, resource: "secure-aggregation-session-catalog", field: "decryptedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.decryptedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.completedAt.label", "Completed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:completedAt", { value: record?.completedAt, record, resource: "secure-aggregation-session-catalog", field: "completedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.completedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.secure_aggregation_session_catalog.fields.failedAt.label", "Failed At")}</h4>
              {renderFieldOverride(frontendComposition, "field:secure-aggregation-session-catalog:display:failedAt", { value: record?.failedAt, record, resource: "secure-aggregation-session-catalog", field: "failedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failedAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
