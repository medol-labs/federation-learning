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

export const RuntimeDatasetBindingCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flruntime-agent",
    meta: {
      tableName: "runtime_dataset_binding_catalog_read_model_entity",
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_binding_catalog.label", "Runtime Dataset Binding Catalog"),
      aggregateRoute: "runtimedatasetbinding",
      queryRoute: "runtimedatasetbindingcatalog",
      dataProviderName: "flruntime-agent",
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeDatasetBindingId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.datasetId.label", "Dataset Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.organizationId.label", "Organization Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.datasetName.label", "Dataset Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.datasetName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.dataSourceType.label", "Data Source Type")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dataSourceType, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.host.label", "Host")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.host, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.port.label", "Port")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.port, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.url.label", "Url")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.url, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.databaseName.label", "Database Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.databaseName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.schemaName.label", "Schema Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.schemaName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.tableName.label", "Table Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.tableName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.filePath.label", "File Path")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.filePath, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.objectBucket.label", "Object Bucket")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.objectBucket, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.objectPrefix.label", "Object Prefix")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.objectPrefix, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.dataFormat.label", "Data Format")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dataFormat, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.credentialSecretName.label", "Credential Secret Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.credentialSecretName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_dataset_binding_catalog.fields.configuredAt.label", "Configured At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.configuredAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
