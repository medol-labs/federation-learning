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

export const DictionaryCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-support",
    meta: {
      tableName: "dictionary_catalog_read_model_entity",
      idField: "dictionaryId",
      label: t("resources.dictionary_catalog.label", "Dictionary Catalog"),
      aggregateRoute: "dictionary",
      queryRoute: "dictionarycatalog",
      dataProviderName: "federation-learning-support",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.dictionaryId ?? t("resources.dictionary_catalog.label", "Dictionary Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.dictionaryId.label", "Dictionary Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.dictionaryCode.label", "Dictionary Code")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryCode, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.dictionaryName.label", "Dictionary Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.description.label", "Description")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.description, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.registeredAt.label", "Registered At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.registeredAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.updatedAt.label", "Updated At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.updatedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.archivedAt.label", "Archived At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.archivedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.archiveReason.label", "Archive Reason")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.archiveReason, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
