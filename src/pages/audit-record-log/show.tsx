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

export const AuditRecordLogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "flplatform-backend",
    meta: {
      tableName: "audit_record_log_read_model_entity",
      idField: "auditRecordId",
      label: t("resources.audit_record_log.label", "Audit Record Log"),
      aggregateRoute: "auditrecord",
      queryRoute: "auditrecordlog",
      dataProviderName: "flplatform-backend",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.auditRecordId ?? t("resources.audit_record_log.label", "Audit Record Log")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.audit_record_log.fields.auditRecordId.label", "Audit Record Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.auditRecordId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.audit_record_log.fields.sourceEventName.label", "Source Event Name")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sourceEventName, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.audit_record_log.fields.sourceEntityId.label", "Source Entity Id")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.sourceEntityId, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.audit_record_log.fields.severity.label", "Severity")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.severity, t)}</p>
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.audit_record_log.fields.payloadHash.label", "Payload Hash")}</h4>
              <p className="text-sm text-muted-foreground">{formatValue(record?.payloadHash, t)}</p>
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
