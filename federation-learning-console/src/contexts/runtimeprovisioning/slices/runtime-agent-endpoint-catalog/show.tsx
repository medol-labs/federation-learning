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

export const RuntimeAgentEndpointCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_agent_endpoint_catalog_read_model_entity",
      idField: "runtimeAgentId",
      label: t("resources.runtime_agent_endpoint_catalog.label", "Runtime Agent Endpoint Catalog"),
      aggregateRoute: "runtimeinfrastructure",
      queryRoute: "runtimeagentendpointcatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeAgentId ?? t("resources.runtime_agent_endpoint_catalog.label", "Runtime Agent Endpoint Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.runtimeAgentId.label", "Runtime Agent Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:runtimeAgentId", { value: record?.runtimeAgentId, record, resource: "runtime-agent-endpoint-catalog", field: "runtimeAgentId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.runtimeId.label", "Runtime Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:runtimeId", { value: record?.runtimeId, record, resource: "runtime-agent-endpoint-catalog", field: "runtimeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:runtimeInfrastructureId", { value: record?.runtimeInfrastructureId, record, resource: "runtime-agent-endpoint-catalog", field: "runtimeInfrastructureId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeInfrastructureId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.organizationId.label", "Organization Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:organizationId", { value: record?.organizationId, record, resource: "runtime-agent-endpoint-catalog", field: "organizationId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.organizationId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.runtimeName.label", "Runtime Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:runtimeName", { value: record?.runtimeName, record, resource: "runtime-agent-endpoint-catalog", field: "runtimeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.runtimeAgentEndpoint.label", "Runtime Agent Endpoint")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:runtimeAgentEndpoint", { value: record?.runtimeAgentEndpoint, record, resource: "runtime-agent-endpoint-catalog", field: "runtimeAgentEndpoint", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeAgentEndpoint, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.endpointScope.label", "Endpoint Scope")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:endpointScope", { value: record?.endpointScope, record, resource: "runtime-agent-endpoint-catalog", field: "endpointScope", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.endpointScope, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.connectionStatus.label", "Connection Status")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:connectionStatus", { value: record?.connectionStatus, record, resource: "runtime-agent-endpoint-catalog", field: "connectionStatus", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.connectionStatus, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.connectedAt.label", "Connected At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:connectedAt", { value: record?.connectedAt, record, resource: "runtime-agent-endpoint-catalog", field: "connectedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.connectedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_agent_endpoint_catalog.fields.activatedAt.label", "Activated At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-agent-endpoint-catalog:display:activatedAt", { value: record?.activatedAt, record, resource: "runtime-agent-endpoint-catalog", field: "activatedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.activatedAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
