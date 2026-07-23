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

export const TrainingAlertCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flplatform-backend",
    meta: {
      tableName: "training_alert_catalog_read_model_entity",
      idField: "alertId",
      label: t("resources.training_alert_catalog.label", "Training Alert Catalog"),
      aggregateRoute: "trainingalert",
      queryRoute: "trainingalertcatalog",
      dataProviderName: "flplatform-backend",
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
              <p className="text-sm text-muted-foreground">{formatValue(record?.alertId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.nodeId.label", "Node Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.nodeId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.trainingJobId.label", "Training Job Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.runtimeNodeName.label", "Runtime Node Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeNodeName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.trainingJobObjective.label", "Training Job Objective")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.trainingJobObjective, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.severity.label", "Severity")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.severity, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.message.label", "Message")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.message, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.state.label", "State")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.acknowledgedAt.label", "Acknowledged At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.acknowledgedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.resolvedAt.label", "Resolved At")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.resolvedAt, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.resolutionSummary.label", "Resolution Summary")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.resolutionSummary, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.canAcknowledge.label", "Can Acknowledge")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canAcknowledge, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.training_alert_catalog.fields.canResolve.label", "Can Resolve")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.canResolve, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
