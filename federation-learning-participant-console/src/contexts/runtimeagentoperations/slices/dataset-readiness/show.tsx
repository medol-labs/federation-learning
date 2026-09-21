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

export const DatasetReadinessShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "dataset_readiness_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_readiness.label", "Dataset Readiness"),
      aggregateRoute: "dataset",
      queryRoute: "datasetreadiness",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.datasetId ?? t("resources.dataset_readiness.label", "Dataset Readiness")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.datasetId.label", "Dataset Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:datasetId", { value: record?.datasetId, record, resource: "dataset-readiness", field: "datasetId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:organizationId", { value: record?.organizationId, record, resource: "dataset-readiness", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:runtimeId", { value: record?.runtimeId, record, resource: "dataset-readiness", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "dataset-readiness", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.datasetName.label", "Dataset Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:datasetName", { value: record?.datasetName, record, resource: "dataset-readiness", field: "datasetName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:organizationName", { value: record?.organizationName, record, resource: "dataset-readiness", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:featureDomain", { value: record?.featureDomain, record, resource: "dataset-readiness", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "dataset-readiness", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.datasetUsage.label", "Dataset Usage")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:datasetUsage", { value: record?.datasetUsage, record, resource: "dataset-readiness", field: "datasetUsage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetUsage, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.metadataStatus.label", "Metadata Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:metadataStatus", { value: record?.metadataStatus, record, resource: "dataset-readiness", field: "metadataStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.metadataStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.contractStatus.label", "Contract Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:contractStatus", { value: record?.contractStatus, record, resource: "dataset-readiness", field: "contractStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.contractStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.approvalStatus.label", "Approval Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:approvalStatus", { value: record?.approvalStatus, record, resource: "dataset-readiness", field: "approvalStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.approvalStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.accessStatus.label", "Access Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:accessStatus", { value: record?.accessStatus, record, resource: "dataset-readiness", field: "accessStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.accessStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.runtimeStatus.label", "Runtime Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:runtimeStatus", { value: record?.runtimeStatus, record, resource: "dataset-readiness", field: "runtimeStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.overallReadiness.label", "Overall Readiness")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:overallReadiness", { value: record?.overallReadiness, record, resource: "dataset-readiness", field: "overallReadiness", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.overallReadiness, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.readyForTraining.label", "Ready For Training")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:readyForTraining", { value: record?.readyForTraining, record, resource: "dataset-readiness", field: "readyForTraining", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.readyForTraining, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.canBeSelectedForTraining.label", "Can Be Selected For Training")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:canBeSelectedForTraining", { value: record?.canBeSelectedForTraining, record, resource: "dataset-readiness", field: "canBeSelectedForTraining", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canBeSelectedForTraining, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.readinessScore.label", "Readiness Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:readinessScore", { value: record?.readinessScore, record, resource: "dataset-readiness", field: "readinessScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.readinessScore, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.missingRequirements.label", "Missing Requirements")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:missingRequirements", { value: record?.missingRequirements, record, resource: "dataset-readiness", field: "missingRequirements", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.missingRequirements, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.blockingReasons.label", "Blocking Reasons")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:blockingReasons", { value: record?.blockingReasons, record, resource: "dataset-readiness", field: "blockingReasons", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.blockingReasons, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.warnings.label", "Warnings")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:warnings", { value: record?.warnings, record, resource: "dataset-readiness", field: "warnings", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.warnings, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.sampleCount.label", "Sample Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:sampleCount", { value: record?.sampleCount, record, resource: "dataset-readiness", field: "sampleCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.sampleCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.featureCount.label", "Feature Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:featureCount", { value: record?.featureCount, record, resource: "dataset-readiness", field: "featureCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.schemaCompatible.label", "Schema Compatible")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:schemaCompatible", { value: record?.schemaCompatible, record, resource: "dataset-readiness", field: "schemaCompatible", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.schemaCompatible, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.labelCompatible.label", "Label Compatible")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:labelCompatible", { value: record?.labelCompatible, record, resource: "dataset-readiness", field: "labelCompatible", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.labelCompatible, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.qualityScore.label", "Quality Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:qualityScore", { value: record?.qualityScore, record, resource: "dataset-readiness", field: "qualityScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.qualityScore, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.nonIidScore.label", "Non Iid Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:nonIidScore", { value: record?.nonIidScore, record, resource: "dataset-readiness", field: "nonIidScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nonIidScore, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.classBalanceScore.label", "Class Balance Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:classBalanceScore", { value: record?.classBalanceScore, record, resource: "dataset-readiness", field: "classBalanceScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.classBalanceScore, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.metadataReportId.label", "Metadata Report Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:metadataReportId", { value: record?.metadataReportId, record, resource: "dataset-readiness", field: "metadataReportId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.metadataReportId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.datasetAccessValidationId.label", "Dataset Access Validation Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:datasetAccessValidationId", { value: record?.datasetAccessValidationId, record, resource: "dataset-readiness", field: "datasetAccessValidationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetAccessValidationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.readable.label", "Readable")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:readable", { value: record?.readable, record, resource: "dataset-readiness", field: "readable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.readable, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.schemaReadable.label", "Schema Readable")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:schemaReadable", { value: record?.schemaReadable, record, resource: "dataset-readiness", field: "schemaReadable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.schemaReadable, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.sampleBatchReadable.label", "Sample Batch Readable")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:sampleBatchReadable", { value: record?.sampleBatchReadable, record, resource: "dataset-readiness", field: "sampleBatchReadable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.sampleBatchReadable, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.lastProfiledAt.label", "Last Profiled At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:lastProfiledAt", { value: record?.lastProfiledAt, record, resource: "dataset-readiness", field: "lastProfiledAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastProfiledAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.lastAccessValidatedAt.label", "Last Access Validated At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:lastAccessValidatedAt", { value: record?.lastAccessValidatedAt, record, resource: "dataset-readiness", field: "lastAccessValidatedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastAccessValidatedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.lastRuntimeHeartbeatAt.label", "Last Runtime Heartbeat At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:lastRuntimeHeartbeatAt", { value: record?.lastRuntimeHeartbeatAt, record, resource: "dataset-readiness", field: "lastRuntimeHeartbeatAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastRuntimeHeartbeatAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.lastUpdatedAt.label", "Last Updated At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-readiness:display:lastUpdatedAt", { value: record?.lastUpdatedAt, record, resource: "dataset-readiness", field: "lastUpdatedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastUpdatedAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
