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

export const TrainingAlertCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_alert_catalog_read_model_entity",
      idField: "alertId",
      label: t("resources.training_alert_catalog.label", "Training Alert Catalog"),
      aggregateRoute: "trainingalert",
      queryRoute: "trainingalertcatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.alertId ?? t("resources.training_alert_catalog.label", "Training Alert Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.alertId.label", "Alert Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:alertId", { value: record?.alertId, record, resource: "training-alert-catalog", field: "alertId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.alertId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.nodeId.label", "Node Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:nodeId", { value: record?.nodeId, record, resource: "training-alert-catalog", field: "nodeId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.nodeId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:trainingJobId", { value: record?.trainingJobId, record, resource: "training-alert-catalog", field: "trainingJobId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.runtimeNodeName.label", "Runtime Node Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:runtimeNodeName", { value: record?.runtimeNodeName, record, resource: "training-alert-catalog", field: "runtimeNodeName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:trainingJobObjective", { value: record?.trainingJobObjective, record, resource: "training-alert-catalog", field: "trainingJobObjective", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.severity.label", "Severity")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:severity", { value: record?.severity, record, resource: "training-alert-catalog", field: "severity", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.severity, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.message.label", "Message")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:message", { value: record?.message, record, resource: "training-alert-catalog", field: "message", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.message, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:state", { value: record?.state, record, resource: "training-alert-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.acknowledgedAt.label", "Acknowledged At")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:acknowledgedAt", { value: record?.acknowledgedAt, record, resource: "training-alert-catalog", field: "acknowledgedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.acknowledgedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.resolvedAt.label", "Resolved At")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:resolvedAt", { value: record?.resolvedAt, record, resource: "training-alert-catalog", field: "resolvedAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.resolvedAt, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.resolutionSummary.label", "Resolution Summary")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:resolutionSummary", { value: record?.resolutionSummary, record, resource: "training-alert-catalog", field: "resolutionSummary", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.resolutionSummary, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.canAcknowledge.label", "Can Acknowledge")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:canAcknowledge", { value: record?.canAcknowledge, record, resource: "training-alert-catalog", field: "canAcknowledge", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canAcknowledge, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.canResolve.label", "Can Resolve")}</h4>
              {renderFieldOverride(frontendComposition, "field:training-alert-catalog:display:canResolve", { value: record?.canResolve, record, resource: "training-alert-catalog", field: "canResolve", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.canResolve, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
