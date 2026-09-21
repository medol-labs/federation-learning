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

export const FeatureSchemaCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "feature_schema_catalog_read_model_entity",
      idField: "featureSchemaId",
      label: t("resources.feature_schema_catalog.label", "Feature Schema Catalog"),
      aggregateRoute: "featureschema",
      queryRoute: "featureschemacatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.featureSchemaId ?? t("resources.feature_schema_catalog.label", "Feature Schema Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "feature-schema-catalog", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:featureDomain", { value: record?.featureDomain, record, resource: "feature-schema-catalog", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.version.label", "Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:version", { value: record?.version, record, resource: "feature-schema-catalog", field: "version", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.version, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.dataModality.label", "Data Modality")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:dataModality", { value: record?.dataModality, record, resource: "feature-schema-catalog", field: "dataModality", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dataModality, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.features.label", "Features")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:features", { value: record?.features, record, resource: "feature-schema-catalog", field: "features", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.features, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.labels.label", "Labels")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:labels", { value: record?.labels, record, resource: "feature-schema-catalog", field: "labels", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.labels, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.featureCount.label", "Feature Count")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:featureCount", { value: record?.featureCount, record, resource: "feature-schema-catalog", field: "featureCount", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureCount, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.schemaStatus.label", "Schema Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:schemaStatus", { value: record?.schemaStatus, record, resource: "feature-schema-catalog", field: "schemaStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.schemaStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.supersededByFeatureSchemaId.label", "Superseded By Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:supersededByFeatureSchemaId", { value: record?.supersededByFeatureSchemaId, record, resource: "feature-schema-catalog", field: "supersededByFeatureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.supersededByFeatureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.feature_schema_catalog.fields.recommendedForDomain.label", "Recommended For Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:feature-schema-catalog:display:recommendedForDomain", { value: record?.recommendedForDomain, record, resource: "feature-schema-catalog", field: "recommendedForDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recommendedForDomain, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
