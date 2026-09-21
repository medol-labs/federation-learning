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

export const RuntimeCapabilityCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_capability_catalog_read_model_entity",
      idField: "runtimeId",
      label: t("resources.runtime_capability_catalog.label", "Runtime Capability Catalog"),
      aggregateRoute: "runtimecapability",
      queryRoute: "runtimecapabilitycatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeId ?? t("resources.runtime_capability_catalog.label", "Runtime Capability Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_capability_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-capability-catalog:display:runtimeId", { value: record?.runtimeId, record, resource: "runtime-capability-catalog", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_capability_catalog.fields.capabilityTypes.label", "Capability Types")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-capability-catalog:display:capabilityTypes", { value: record?.capabilityTypes, record, resource: "runtime-capability-catalog", field: "capabilityTypes", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.capabilityTypes, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_capability_catalog.fields.capabilityStatus.label", "Capability Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-capability-catalog:display:capabilityStatus", { value: record?.capabilityStatus, record, resource: "runtime-capability-catalog", field: "capabilityStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.capabilityStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_capability_catalog.fields.detectedAt.label", "Detected At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-capability-catalog:display:detectedAt", { value: record?.detectedAt, record, resource: "runtime-capability-catalog", field: "detectedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.detectedAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
