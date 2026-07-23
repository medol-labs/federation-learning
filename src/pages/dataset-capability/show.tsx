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

export const DatasetCapabilityShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "dataset_capability_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_capability.label", "Dataset Capability"),
      aggregateRoute: "dataset",
      queryRoute: "datasetcapability",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.datasetId ?? t("resources.dataset_capability.label", "Dataset Capability")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.datasetId.label", "Dataset Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.runtimeId.label", "Runtime Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.featureDomain.label", "Feature Domain")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.datasetName.label", "Dataset Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.datasetUsage.label", "Dataset Usage")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetUsage, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.sampleCount.label", "Sample Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sampleCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.featureCount.label", "Feature Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.schemaCompatible.label", "Schema Compatible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.schemaCompatible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.labelCompatible.label", "Label Compatible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.labelCompatible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.qualityScore.label", "Quality Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.qualityScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.nonIidScore.label", "Non Iid Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nonIidScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.metadataReportId.label", "Metadata Report Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.metadataReportId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.metadataStatus.label", "Metadata Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.metadataStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.approvalStatus.label", "Approval Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.approvalStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.approved.label", "Approved")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.approved, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.lastProfiledAt.label", "Last Profiled At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.lastProfiledAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
