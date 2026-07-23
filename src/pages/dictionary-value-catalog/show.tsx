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

export const DictionaryValueCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "fldictionary-backend",
    meta: {
      tableName: "dictionary_value_catalog_read_model_entity",
      idField: "dictionaryValueId",
      label: t("resources.dictionary_value_catalog.label", "Dictionary Value Catalog"),
      aggregateRoute: "dictionaryvalue",
      queryRoute: "dictionaryvaluecatalog",
      dataProviderName: "fldictionary-backend",
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryValueId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.dictionaryId.label", "Dictionary Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.dictionaryCode.label", "Dictionary Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.valueCode.label", "Value Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.valueCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.displayName.label", "Display Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.displayName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.displayOrder.label", "Display Order")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.displayOrder, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.description.label", "Description")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.description, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.active.label", "Active")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.active, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.addedAt.label", "Added At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.addedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.updatedAt.label", "Updated At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.updatedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.disabledAt.label", "Disabled At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.disabledAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.disabledReason.label", "Disabled Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.disabledReason, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_value_catalog.fields.enabledAt.label", "Enabled At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.enabledAt, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
