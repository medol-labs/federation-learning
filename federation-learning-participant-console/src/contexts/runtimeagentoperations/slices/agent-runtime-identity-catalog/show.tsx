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

export const AgentRuntimeIdentityCatalogShow = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_runtime_identity_catalog_read_model_entity",
      idField: "runtimeId",
      label: t("resources.agent_runtime_identity_catalog.label", "Agent Runtime Identity Catalog"),
      aggregateRoute: "agentruntimeidentitycatalog",
      queryRoute: "agentruntimeidentitycatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeId ?? t("resources.agent_runtime_identity_catalog.label", "Agent Runtime Identity Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:runtimeId", { value: record?.runtimeId, record, resource: "agent-runtime-identity-catalog", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:runtimeInfrastructureId", { value: record?.runtimeInfrastructureId, record, resource: "agent-runtime-identity-catalog", field: "runtimeInfrastructureId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "agent-runtime-identity-catalog", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:organizationId", { value: record?.organizationId, record, resource: "agent-runtime-identity-catalog", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.organizationName.label", "Organization Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:organizationName", { value: record?.organizationName, record, resource: "agent-runtime-identity-catalog", field: "organizationName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:runtimeName", { value: record?.runtimeName, record, resource: "agent-runtime-identity-catalog", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.identityStatus.label", "Identity Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:identityStatus", { value: record?.identityStatus, record, resource: "agent-runtime-identity-catalog", field: "identityStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.identityStatus, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.activatedAt.label", "Activated At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:activatedAt", { value: record?.activatedAt, record, resource: "agent-runtime-identity-catalog", field: "activatedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.activatedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.revokedAt.label", "Revoked At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:revokedAt", { value: record?.revokedAt, record, resource: "agent-runtime-identity-catalog", field: "revokedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.revokedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.agent_runtime_identity_catalog.fields.syncedAt.label", "Synced At")}</h4>
              {renderFieldOverride(frontendComposition, "field:agent-runtime-identity-catalog:display:syncedAt", { value: record?.syncedAt, record, resource: "agent-runtime-identity-catalog", field: "syncedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.syncedAt, t, dictionaryLabel, undefined)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
