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

export const RuntimeDatasetMetadataCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_dataset_metadata_catalog_read_model_entity",
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_metadata_catalog.label", "Runtime Dataset Metadata Catalog"),
      aggregateRoute: "runtimedatasetmetadata",
      queryRoute: "runtimedatasetmetadatacatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeDatasetBindingId ?? t("resources.runtime_dataset_metadata_catalog.label", "Runtime Dataset Metadata Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDatasetBindingId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.metadataReportId.label", "Metadata Report Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.metadataReportId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.datasetId.label", "Dataset Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.datasetName.label", "Dataset Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.organizationName.label", "Organization Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.featureDomain.label", "Feature Domain")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.sampleCount.label", "Sample Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sampleCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.featureCount.label", "Feature Count")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.featureCount, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.schemaCompatible.label", "Schema Compatible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.schemaCompatible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.labelCompatible.label", "Label Compatible")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.labelCompatible, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.missingValueRate.label", "Missing Value Rate")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.missingValueRate, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.duplicateRate.label", "Duplicate Rate")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.duplicateRate, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.qualityScore.label", "Quality Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.qualityScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.nonIidScore.label", "Non Iid Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nonIidScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.classBalanceScore.label", "Class Balance Score")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.classBalanceScore, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.profilingStatus.label", "Profiling Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.profilingStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.failureReason.label", "Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.profiledAt.label", "Profiled At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.profiledAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
