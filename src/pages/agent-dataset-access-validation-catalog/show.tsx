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
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetAccessValidationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDatasetBindingId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.datasetId.label", "Dataset Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.datasetName.label", "Dataset Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.readable.label", "Readable")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.readable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.schemaReadable.label", "Schema Readable")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.schemaReadable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.sampleBatchReadable.label", "Sample Batch Readable")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sampleBatchReadable, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.validationStatus.label", "Validation Status")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.validationStatus, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.failureReason.label", "Failure Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.failureReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dataset_access_validation_catalog.fields.validatedAt.label", "Validated At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.validatedAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
