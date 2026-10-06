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
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

const formatValue = (
  value: unknown,
  t: ReturnType<typeof useTranslate>,
  dictionaryLabel: ReturnType<typeof useDictionaryTranslation>["dictionaryLabel"],
  options?: Array<{ label: string; value: string }>,
  dictionaryCode?: string,
): string => {
  if (value === null || value === undefined || value === "") return "-";
  if (Array.isArray(value)) {
    const formatted: string[] = value.map((item) => formatValue(item, t, dictionaryLabel, options, dictionaryCode)).filter((item) => item !== "-");
    return formatted.length > 0 ? formatted.join(", ") : "-";
  }
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  const stringValue = String(value);
  if (dictionaryCode) return dictionaryLabel(dictionaryCode, stringValue, t(`dictionaries.${dictionaryCode}.${stringValue}`, stringValue));
  return options?.find((option) => option.value === stringValue)?.label ?? stringValue;
};

export const DatasetCapabilityShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "dataset_capability_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_capability.label", "Dataset Capability"),
      aggregateRoute: "dataset",
      queryRoute: "datasetcapability",
      dataProviderName: "federation-learning-runtime-agent",
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
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:datasetId", { value: record?.datasetId, record, resource: "dataset-capability", field: "datasetId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:organizationId", { value: record?.organizationId, record, resource: "dataset-capability", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:runtimeId", { value: record?.runtimeId, record, resource: "dataset-capability", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "dataset-capability", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.features.label", "Features")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:features", { value: record?.features, record, resource: "dataset-capability", field: "features", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.features, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.labels.label", "Labels")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:labels", { value: record?.labels, record, resource: "dataset-capability", field: "labels", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.labels, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:organizationName", { value: record?.organizationName, record, resource: "dataset-capability", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:featureDomain", { value: record?.featureDomain, record, resource: "dataset-capability", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "dataset-capability", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.datasetName.label", "Dataset Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:datasetName", { value: record?.datasetName, record, resource: "dataset-capability", field: "datasetName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.datasetUsage.label", "Dataset Usage")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:datasetUsage", { value: record?.datasetUsage, record, resource: "dataset-capability", field: "datasetUsage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetUsage, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.sampleCount.label", "Sample Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:sampleCount", { value: record?.sampleCount, record, resource: "dataset-capability", field: "sampleCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.sampleCount, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.featureCount.label", "Feature Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:featureCount", { value: record?.featureCount, record, resource: "dataset-capability", field: "featureCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureCount, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.schemaCompatible.label", "Schema Compatible")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:schemaCompatible", { value: record?.schemaCompatible, record, resource: "dataset-capability", field: "schemaCompatible", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.schemaCompatible, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.labelCompatible.label", "Label Compatible")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:labelCompatible", { value: record?.labelCompatible, record, resource: "dataset-capability", field: "labelCompatible", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.labelCompatible, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.qualityScore.label", "Quality Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:qualityScore", { value: record?.qualityScore, record, resource: "dataset-capability", field: "qualityScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.qualityScore, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.nonIidScore.label", "Non Iid Score")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:nonIidScore", { value: record?.nonIidScore, record, resource: "dataset-capability", field: "nonIidScore", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nonIidScore, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.metadataReportId.label", "Metadata Report Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:metadataReportId", { value: record?.metadataReportId, record, resource: "dataset-capability", field: "metadataReportId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.metadataReportId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.metadataStatus.label", "Metadata Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:metadataStatus", { value: record?.metadataStatus, record, resource: "dataset-capability", field: "metadataStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.metadataStatus, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.contractStatus.label", "Contract Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:contractStatus", { value: record?.contractStatus, record, resource: "dataset-capability", field: "contractStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.contractStatus, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.approvalStatus.label", "Approval Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:approvalStatus", { value: record?.approvalStatus, record, resource: "dataset-capability", field: "approvalStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.approvalStatus, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.approved.label", "Approved")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:approved", { value: record?.approved, record, resource: "dataset-capability", field: "approved", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.approved, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dataset_capability.fields.lastProfiledAt.label", "Last Profiled At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dataset-capability:display:lastProfiledAt", { value: record?.lastProfiledAt, record, resource: "dataset-capability", field: "lastProfiledAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.lastProfiledAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
