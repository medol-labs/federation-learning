// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
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

export const AgentDictionaryValueTranslationCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_dictionary_value_translation_catalog_read_model_entity",
      idField: "dictionaryValueTranslationId",
      label: t("resources.agent_dictionary_value_translation_catalog.label", "Agent Dictionary Value Translation Catalog"),
      aggregateRoute: "agentdictionaryvaluetranslationcatalog",
      queryRoute: "agentdictionaryvaluetranslationcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.dictionaryValueTranslationId ?? t("resources.agent_dictionary_value_translation_catalog.label", "Agent Dictionary Value Translation Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_translation_catalog.fields.dictionaryValueTranslationId.label", "Dictionary Value Translation Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dictionary-value-translation-catalog:display:dictionaryValueTranslationId", { value: record?.dictionaryValueTranslationId, record, resource: "agent-dictionary-value-translation-catalog", field: "dictionaryValueTranslationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryValueTranslationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_translation_catalog.fields.dictionaryValueId.label", "Dictionary Value Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dictionary-value-translation-catalog:display:dictionaryValueId", { value: record?.dictionaryValueId, record, resource: "agent-dictionary-value-translation-catalog", field: "dictionaryValueId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryValueId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_translation_catalog.fields.dictionaryCode.label", "Dictionary Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dictionary-value-translation-catalog:display:dictionaryCode", { value: record?.dictionaryCode, record, resource: "agent-dictionary-value-translation-catalog", field: "dictionaryCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryCode, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_translation_catalog.fields.valueCode.label", "Value Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dictionary-value-translation-catalog:display:valueCode", { value: record?.valueCode, record, resource: "agent-dictionary-value-translation-catalog", field: "valueCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.valueCode, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_translation_catalog.fields.locale.label", "Locale")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dictionary-value-translation-catalog:display:locale", { value: record?.locale, record, resource: "agent-dictionary-value-translation-catalog", field: "locale", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.locale, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_translation_catalog.fields.displayName.label", "Display Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dictionary-value-translation-catalog:display:displayName", { value: record?.displayName, record, resource: "agent-dictionary-value-translation-catalog", field: "displayName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.displayName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_translation_catalog.fields.description.label", "Description")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dictionary-value-translation-catalog:display:description", { value: record?.description, record, resource: "agent-dictionary-value-translation-catalog", field: "description", view: "display" }) ?? <CopyableText value={record?.description} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_translation_catalog.fields.syncedAt.label", "Synced At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-dictionary-value-translation-catalog:display:syncedAt", { value: record?.syncedAt, record, resource: "agent-dictionary-value-translation-catalog", field: "syncedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.syncedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
