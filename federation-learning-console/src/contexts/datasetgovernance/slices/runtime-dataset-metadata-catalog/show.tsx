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
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:runtimeDatasetBindingId", { value: record?.runtimeDatasetBindingId, record, resource: "runtime-dataset-metadata-catalog", field: "runtimeDatasetBindingId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDatasetBindingId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.metadataReportId.label", "Metadata Report Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:metadataReportId", { value: record?.metadataReportId, record, resource: "runtime-dataset-metadata-catalog", field: "metadataReportId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.metadataReportId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.datasetId.label", "Dataset Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:datasetId", { value: record?.datasetId, record, resource: "runtime-dataset-metadata-catalog", field: "datasetId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:organizationId", { value: record?.organizationId, record, resource: "runtime-dataset-metadata-catalog", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:organizationName", { value: record?.organizationName, record, resource: "runtime-dataset-metadata-catalog", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:runtimeId", { value: record?.runtimeId, record, resource: "runtime-dataset-metadata-catalog", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:runtimeName", { value: record?.runtimeName, record, resource: "runtime-dataset-metadata-catalog", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "runtime-dataset-metadata-catalog", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:featureDomain", { value: record?.featureDomain, record, resource: "runtime-dataset-metadata-catalog", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "runtime-dataset-metadata-catalog", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.datasetName.label", "Dataset Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:datasetName", { value: record?.datasetName, record, resource: "runtime-dataset-metadata-catalog", field: "datasetName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.sampleCount.label", "Sample Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:sampleCount", { value: record?.sampleCount, record, resource: "runtime-dataset-metadata-catalog", field: "sampleCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.sampleCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.featureCount.label", "Feature Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:featureCount", { value: record?.featureCount, record, resource: "runtime-dataset-metadata-catalog", field: "featureCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.schemaCompatible.label", "Schema Compatible")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:schemaCompatible", { value: record?.schemaCompatible, record, resource: "runtime-dataset-metadata-catalog", field: "schemaCompatible", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.schemaCompatible, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.labelCompatible.label", "Label Compatible")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:labelCompatible", { value: record?.labelCompatible, record, resource: "runtime-dataset-metadata-catalog", field: "labelCompatible", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.labelCompatible, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.missingValueRate.label", "Missing Value Rate")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:missingValueRate", { value: record?.missingValueRate, record, resource: "runtime-dataset-metadata-catalog", field: "missingValueRate", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.missingValueRate, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.duplicateRate.label", "Duplicate Rate")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:duplicateRate", { value: record?.duplicateRate, record, resource: "runtime-dataset-metadata-catalog", field: "duplicateRate", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.duplicateRate, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.qualityScore.label", "Quality Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:qualityScore", { value: record?.qualityScore, record, resource: "runtime-dataset-metadata-catalog", field: "qualityScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.qualityScore, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.nonIidScore.label", "Non Iid Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:nonIidScore", { value: record?.nonIidScore, record, resource: "runtime-dataset-metadata-catalog", field: "nonIidScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nonIidScore, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.classBalanceScore.label", "Class Balance Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:classBalanceScore", { value: record?.classBalanceScore, record, resource: "runtime-dataset-metadata-catalog", field: "classBalanceScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.classBalanceScore, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.profilingStatus.label", "Profiling Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:profilingStatus", { value: record?.profilingStatus, record, resource: "runtime-dataset-metadata-catalog", field: "profilingStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.profilingStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.failureReason.label", "Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:failureReason", { value: record?.failureReason, record, resource: "runtime-dataset-metadata-catalog", field: "failureReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_metadata_catalog.fields.profiledAt.label", "Profiled At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-metadata-catalog:display:profiledAt", { value: record?.profiledAt, record, resource: "runtime-dataset-metadata-catalog", field: "profiledAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.profiledAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
