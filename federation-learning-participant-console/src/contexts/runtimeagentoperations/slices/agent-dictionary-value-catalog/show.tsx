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

export const AgentDictionaryValueCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_dictionary_value_catalog_read_model_entity",
      idField: "dictionaryValueId",
      label: t("resources.agent_dictionary_value_catalog.label", "Agent Dictionary Value Catalog"),
      aggregateRoute: "agentdictionaryvaluecatalog",
      queryRoute: "agentdictionaryvaluecatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.dictionaryValueId ?? t("resources.agent_dictionary_value_catalog.label", "Agent Dictionary Value Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_catalog.fields.dictionaryValueId.label", "Dictionary Value Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryValueId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_catalog.fields.dictionaryId.label", "Dictionary Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_catalog.fields.dictionaryCode.label", "Dictionary Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_catalog.fields.valueCode.label", "Value Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.valueCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_catalog.fields.displayName.label", "Display Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.displayName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_catalog.fields.displayOrder.label", "Display Order")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.displayOrder, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_catalog.fields.active.label", "Active")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.active, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_dictionary_value_catalog.fields.syncedAt.label", "Synced At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.syncedAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
