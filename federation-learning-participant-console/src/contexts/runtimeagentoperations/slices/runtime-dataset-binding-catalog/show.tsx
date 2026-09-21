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

export const RuntimeDatasetBindingCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "runtime_dataset_binding_catalog_read_model_entity",
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_binding_catalog.label", "Runtime Dataset Binding Catalog"),
      aggregateRoute: "runtimedatasetbinding",
      queryRoute: "runtimedatasetbindingcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeDatasetBindingId ?? t("resources.runtime_dataset_binding_catalog.label", "Runtime Dataset Binding Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:runtimeDatasetBindingId", { value: record?.runtimeDatasetBindingId, record, resource: "runtime-dataset-binding-catalog", field: "runtimeDatasetBindingId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDatasetBindingId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.datasetId.label", "Dataset Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:datasetId", { value: record?.datasetId, record, resource: "runtime-dataset-binding-catalog", field: "datasetId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:organizationId", { value: record?.organizationId, record, resource: "runtime-dataset-binding-catalog", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:runtimeId", { value: record?.runtimeId, record, resource: "runtime-dataset-binding-catalog", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:organizationName", { value: record?.organizationName, record, resource: "runtime-dataset-binding-catalog", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "runtime-dataset-binding-catalog", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:featureDomain", { value: record?.featureDomain, record, resource: "runtime-dataset-binding-catalog", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "runtime-dataset-binding-catalog", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.datasetName.label", "Dataset Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:datasetName", { value: record?.datasetName, record, resource: "runtime-dataset-binding-catalog", field: "datasetName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:runtimeName", { value: record?.runtimeName, record, resource: "runtime-dataset-binding-catalog", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.filePath.label", "File Path")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:filePath", { value: record?.filePath, record, resource: "runtime-dataset-binding-catalog", field: "filePath", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.filePath, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.dataFormat.label", "Data Format")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:dataFormat", { value: record?.dataFormat, record, resource: "runtime-dataset-binding-catalog", field: "dataFormat", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dataFormat, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.configuredAt.label", "Configured At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-dataset-binding-catalog:display:configuredAt", { value: record?.configuredAt, record, resource: "runtime-dataset-binding-catalog", field: "configuredAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.configuredAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
