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

export const AgentFeatureSchemaCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_feature_schema_catalog_read_model_entity",
      idField: "featureSchemaId",
      label: t("resources.agent_feature_schema_catalog.label", "Agent Feature Schema Catalog"),
      aggregateRoute: "agentfeatureschemacatalog",
      queryRoute: "agentfeatureschemacatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.featureSchemaId ?? t("resources.agent_feature_schema_catalog.label", "Agent Feature Schema Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_feature_schema_catalog.fields.featureSchemaId.label", "Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-feature-schema-catalog:display:featureSchemaId", { value: record?.featureSchemaId, record, resource: "agent-feature-schema-catalog", field: "featureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_feature_schema_catalog.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-feature-schema-catalog:display:featureDomain", { value: record?.featureDomain, record, resource: "agent-feature-schema-catalog", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_feature_schema_catalog.fields.featureSchemaVersion.label", "Feature Schema Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-feature-schema-catalog:display:featureSchemaVersion", { value: record?.featureSchemaVersion, record, resource: "agent-feature-schema-catalog", field: "featureSchemaVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureSchemaVersion, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_feature_schema_catalog.fields.schemaStatus.label", "Schema Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-feature-schema-catalog:display:schemaStatus", { value: record?.schemaStatus, record, resource: "agent-feature-schema-catalog", field: "schemaStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.schemaStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_feature_schema_catalog.fields.syncedAt.label", "Synced At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-feature-schema-catalog:display:syncedAt", { value: record?.syncedAt, record, resource: "agent-feature-schema-catalog", field: "syncedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.syncedAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
