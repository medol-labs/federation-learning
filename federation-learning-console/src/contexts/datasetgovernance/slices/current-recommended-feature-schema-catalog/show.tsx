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

export const CurrentRecommendedFeatureSchemaCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "current_recommended_feature_schema_catalog_read_model_entity",
      idField: "featureDomain",
      label: t("resources.current_recommended_feature_schema_catalog.label", "Current Recommended Feature Schema Catalog"),
      aggregateRoute: "featureschema",
      queryRoute: "currentrecommendedfeatureschemacatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.featureDomain ?? t("resources.current_recommended_feature_schema_catalog.label", "Current Recommended Feature Schema Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.current_recommended_feature_schema_catalog.fields.featureDomain.label", "Feature Domain")}</h4>
              {renderFieldOverride(frontendComposition, "field:current-recommended-feature-schema-catalog:display:featureDomain", { value: record?.featureDomain, record, resource: "current-recommended-feature-schema-catalog", field: "featureDomain", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.featureDomain, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.current_recommended_feature_schema_catalog.fields.recommendedFeatureSchemaId.label", "Recommended Feature Schema Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:current-recommended-feature-schema-catalog:display:recommendedFeatureSchemaId", { value: record?.recommendedFeatureSchemaId, record, resource: "current-recommended-feature-schema-catalog", field: "recommendedFeatureSchemaId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recommendedFeatureSchemaId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.current_recommended_feature_schema_catalog.fields.recommendedVersion.label", "Recommended Version")}</h4>
              {renderFieldOverride(frontendComposition, "field:current-recommended-feature-schema-catalog:display:recommendedVersion", { value: record?.recommendedVersion, record, resource: "current-recommended-feature-schema-catalog", field: "recommendedVersion", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recommendedVersion, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.current_recommended_feature_schema_catalog.fields.recommendedAt.label", "Recommended At")}</h4>
              {renderFieldOverride(frontendComposition, "field:current-recommended-feature-schema-catalog:display:recommendedAt", { value: record?.recommendedAt, record, resource: "current-recommended-feature-schema-catalog", field: "recommendedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recommendedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.current_recommended_feature_schema_catalog.fields.recommendationNote.label", "Recommendation Note")}</h4>
              {renderFieldOverride(frontendComposition, "field:current-recommended-feature-schema-catalog:display:recommendationNote", { value: record?.recommendationNote, record, resource: "current-recommended-feature-schema-catalog", field: "recommendationNote", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.recommendationNote, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
