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

export const AgentDatasetAccessValidationCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_dataset_access_validation_catalog_read_model_entity",
      idField: "datasetAccessValidationId",
      label: t("resources.agent_dataset_access_validation_catalog.label", "Agent Dataset Access Validation Catalog"),
      aggregateRoute: "agentdatasetaccessvalidation",
      queryRoute: "agentdatasetaccessvalidationcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.datasetAccessValidationId ?? t("resources.agent_dataset_access_validation_catalog.label", "Agent Dataset Access Validation Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.datasetAccessValidationId.label", "Dataset Access Validation Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:datasetAccessValidationId", { value: record?.datasetAccessValidationId, record, resource: "agent-dataset-access-validation-catalog", field: "datasetAccessValidationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetAccessValidationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:runtimeDatasetBindingId", { value: record?.runtimeDatasetBindingId, record, resource: "agent-dataset-access-validation-catalog", field: "runtimeDatasetBindingId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDatasetBindingId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.datasetId.label", "Dataset Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:datasetId", { value: record?.datasetId, record, resource: "agent-dataset-access-validation-catalog", field: "datasetId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:organizationId", { value: record?.organizationId, record, resource: "agent-dataset-access-validation-catalog", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:organizationName", { value: record?.organizationName, record, resource: "agent-dataset-access-validation-catalog", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "agent-dataset-access-validation-catalog", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:featureDomain", { value: record?.featureDomain, record, resource: "agent-dataset-access-validation-catalog", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "agent-dataset-access-validation-catalog", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:runtimeId", { value: record?.runtimeId, record, resource: "agent-dataset-access-validation-catalog", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.datasetName.label", "Dataset Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:datasetName", { value: record?.datasetName, record, resource: "agent-dataset-access-validation-catalog", field: "datasetName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:runtimeName", { value: record?.runtimeName, record, resource: "agent-dataset-access-validation-catalog", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.readable.label", "Readable")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:readable", { value: record?.readable, record, resource: "agent-dataset-access-validation-catalog", field: "readable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.readable, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.schemaReadable.label", "Schema Readable")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:schemaReadable", { value: record?.schemaReadable, record, resource: "agent-dataset-access-validation-catalog", field: "schemaReadable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.schemaReadable, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.sampleBatchReadable.label", "Sample Batch Readable")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:sampleBatchReadable", { value: record?.sampleBatchReadable, record, resource: "agent-dataset-access-validation-catalog", field: "sampleBatchReadable", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.sampleBatchReadable, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.validationStatus.label", "Validation Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:validationStatus", { value: record?.validationStatus, record, resource: "agent-dataset-access-validation-catalog", field: "validationStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.validationStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.failureReason.label", "Failure Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:failureReason", { value: record?.failureReason, record, resource: "agent-dataset-access-validation-catalog", field: "failureReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.validatedAt.label", "Validated At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dataset-access-validation-catalog:display:validatedAt", { value: record?.validatedAt, record, resource: "agent-dataset-access-validation-catalog", field: "validatedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.validatedAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
