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
              {renderFieldOverride(frontendComposition, "field:dictionary-catalog:display:dictionaryId", { value: record?.dictionaryId, record, resource: "dictionary-catalog", field: "dictionaryId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.dictionaryCode.label", "Dictionary Code")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-catalog:display:dictionaryCode", { value: record?.dictionaryCode, record, resource: "dictionary-catalog", field: "dictionaryCode", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryCode, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.dictionaryName.label", "Dictionary Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-catalog:display:dictionaryName", { value: record?.dictionaryName, record, resource: "dictionary-catalog", field: "dictionaryName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.dictionaryName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.description.label", "Description")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-catalog:display:description", { value: record?.description, record, resource: "dictionary-catalog", field: "description", view: "display" }) ?? <CopyableText value={record?.description} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-catalog:display:state", { value: record?.state, record, resource: "dictionary-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.registeredAt.label", "Registered At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-catalog:display:registeredAt", { value: record?.registeredAt, record, resource: "dictionary-catalog", field: "registeredAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.registeredAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.updatedAt.label", "Updated At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-catalog:display:updatedAt", { value: record?.updatedAt, record, resource: "dictionary-catalog", field: "updatedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.updatedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.archivedAt.label", "Archived At")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-catalog:display:archivedAt", { value: record?.archivedAt, record, resource: "dictionary-catalog", field: "archivedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.archivedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.dictionary_catalog.fields.archiveReason.label", "Archive Reason")}</h4>
              {renderFieldOverride(frontendComposition, "field:dictionary-catalog:display:archiveReason", { value: record?.archiveReason, record, resource: "dictionary-catalog", field: "archiveReason", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.archiveReason, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
