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
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

const formatValue = (value: unknown, t: ReturnType<typeof useTranslate>) => {
  if (value === null || value === undefined || value === "") return "-";
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  return String(value);
};

export const DictionaryValueCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "dictionary_value_catalog_read_model_entity",
      idField: "dictionaryValueId",
      label: t("resources.dictionary_value_catalog.label", "Dictionary Value Catalog"),
      aggregateRoute: "dictionaryvalue",
      queryRoute: "dictionaryvaluecatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.dictionaryValueId ?? t("resources.dictionary_value_catalog.label", "Dictionary Value Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.dictionaryValueId.label", "Dictionary Value Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:dictionaryValueId", { value: record?.dictionaryValueId, record, resource: "dictionary-value-catalog", field: "dictionaryValueId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryValueId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.dictionaryId.label", "Dictionary Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:dictionaryId", { value: record?.dictionaryId, record, resource: "dictionary-value-catalog", field: "dictionaryId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.dictionaryCode.label", "Dictionary Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:dictionaryCode", { value: record?.dictionaryCode, record, resource: "dictionary-value-catalog", field: "dictionaryCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryCode, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.valueCode.label", "Value Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:valueCode", { value: record?.valueCode, record, resource: "dictionary-value-catalog", field: "valueCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.valueCode, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.displayName.label", "Display Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:displayName", { value: record?.displayName, record, resource: "dictionary-value-catalog", field: "displayName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.displayName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.displayOrder.label", "Display Order")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:displayOrder", { value: record?.displayOrder, record, resource: "dictionary-value-catalog", field: "displayOrder", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.displayOrder, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.description.label", "Description")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:description", { value: record?.description, record, resource: "dictionary-value-catalog", field: "description", view: "display" }) ?? <CopyableText value={record?.description} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.active.label", "Active")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:active", { value: record?.active, record, resource: "dictionary-value-catalog", field: "active", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.active, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:state", { value: record?.state, record, resource: "dictionary-value-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.addedAt.label", "Added At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:addedAt", { value: record?.addedAt, record, resource: "dictionary-value-catalog", field: "addedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.addedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.updatedAt.label", "Updated At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:updatedAt", { value: record?.updatedAt, record, resource: "dictionary-value-catalog", field: "updatedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.updatedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.disabledAt.label", "Disabled At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:disabledAt", { value: record?.disabledAt, record, resource: "dictionary-value-catalog", field: "disabledAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.disabledAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.disabledReason.label", "Disabled Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:disabledReason", { value: record?.disabledReason, record, resource: "dictionary-value-catalog", field: "disabledReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.disabledReason, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.enabledAt.label", "Enabled At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-value-catalog:display:enabledAt", { value: record?.enabledAt, record, resource: "dictionary-value-catalog", field: "enabledAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.enabledAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
