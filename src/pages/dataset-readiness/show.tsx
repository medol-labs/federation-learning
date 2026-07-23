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

export const DatasetReadinessShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "dataset_readiness_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_readiness.label", "Dataset Readiness"),
      aggregateRoute: "dataset",
      queryRoute: "datasetreadiness",
      dataProviderName: "federation-learning-platform",
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.runtimeId.label", "Runtime Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.datasetName.label", "Dataset Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.featureDomain.label", "Feature Domain")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.datasetUsage.label", "Dataset Usage")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetUsage, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.metadataStatus.label", "Metadata Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.metadataStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.contractStatus.label", "Contract Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.contractStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.approvalStatus.label", "Approval Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.approvalStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.accessStatus.label", "Access Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.accessStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.runtimeStatus.label", "Runtime Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.overallReadiness.label", "Overall Readiness")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.overallReadiness, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.readyForTraining.label", "Ready For Training")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.readyForTraining, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.canBeSelectedForTraining.label", "Can Be Selected For Training")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canBeSelectedForTraining, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.readinessScore.label", "Readiness Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.readinessScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.missingRequirements.label", "Missing Requirements")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.missingRequirements, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.blockingReasons.label", "Blocking Reasons")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.blockingReasons, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.warnings.label", "Warnings")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.warnings, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.sampleCount.label", "Sample Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sampleCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.featureCount.label", "Feature Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.schemaCompatible.label", "Schema Compatible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.schemaCompatible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.labelCompatible.label", "Label Compatible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.labelCompatible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.qualityScore.label", "Quality Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.qualityScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.nonIidScore.label", "Non Iid Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nonIidScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.classBalanceScore.label", "Class Balance Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.classBalanceScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.metadataReportId.label", "Metadata Report Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.metadataReportId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.datasetAccessValidationId.label", "Dataset Access Validation Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetAccessValidationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.readable.label", "Readable")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.readable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.schemaReadable.label", "Schema Readable")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.schemaReadable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.sampleBatchReadable.label", "Sample Batch Readable")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sampleBatchReadable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.lastProfiledAt.label", "Last Profiled At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lastProfiledAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.lastAccessValidatedAt.label", "Last Access Validated At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lastAccessValidatedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.lastRuntimeHeartbeatAt.label", "Last Runtime Heartbeat At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lastRuntimeHeartbeatAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_readiness.fields.lastUpdatedAt.label", "Last Updated At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lastUpdatedAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
