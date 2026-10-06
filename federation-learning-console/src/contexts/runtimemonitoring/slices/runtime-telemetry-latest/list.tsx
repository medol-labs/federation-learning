// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { DataTableColumnHeader } from "@/components/data-table/data-table-column-header";
import { CommandButton } from "@/components/refine-ui/buttons/command";
import { ShowButton } from "@/components/refine-ui/buttons/show";
import { RefineDataTable } from "@/components/refine-ui/data-table/refine-data-table";
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";
import { useDictionaryTranslation } from "@/lib/dictionary-i18n";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";

type RuntimeTelemetryLatestRecord = {
  nodeId: string;
  runtimeAgentId: string;
  federationId?: string;
  federationName?: string;
  trainingJobId?: string;
  trainingJobObjective?: string;
  roundExecutionId?: string;
  runtimeNodeName?: string;
  cpuLoad?: string;
  gpuLoad?: string;
  memoryLoad?: string;
  lastHeartbeatAt?: string;
  lastRecoveredAt?: string;
  offlineDetectionPending: boolean;
  recoveryDetectionPending: boolean;
  resourcePressureDetectionPending: boolean;
  offlineReason?: string;
  recoveryReason?: string;
  pressureType?: string;
  observedValue?: string;
  thresholdValue?: string;
  alertSeverity?: string;
  alertMessage?: string;
  healthStatus: string;
  telemetryRetentionPolicy: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeTelemetryLatestRecord,
  enabledField?: string,
  stateField?: string,
  allowedStates: string[] = [],
) => {
  const row = record as Record<string, unknown>;
  if (enabledField && row[enabledField] === false) return false;
  if (allowedStates.length === 0) return true;
  if (!stateField) return false;
  const currentState = normalizeWorkflowState(row[stateField]);
  return allowedStates.map(normalizeWorkflowState).includes(currentState);
};

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

export const RuntimeTelemetryLatestList = () => {
  const t = useTranslate();
  const { dictionaryLabel } = useDictionaryTranslation();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeTelemetryLatestRecord>();
    return [
      columnHelper.display({
        id: "select",
        header: ({ table }) => (
          <Checkbox
            checked={table.getIsAllPageRowsSelected() || (table.getIsSomePageRowsSelected() && "indeterminate")}
            onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
            aria-label={t("table.selectAll", "Select all")}
          />
        ),
        cell: ({ row }) => (
          <Checkbox
            checked={row.getIsSelected()}
            onCheckedChange={(value) => row.toggleSelected(!!value)}
            aria-label={t("table.selectRow", "Select row")}
          />
        ),
        size: 32,
        enableSorting: false,
        enableHiding: false,
      }),
      columnHelper.accessor("nodeId", {
        id: "nodeId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.nodeId.label", "Node Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.nodeId.label", "Node Id"),
          placeholder: "Enter Node Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:nodeId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "nodeId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:runtimeAgentId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "runtimeAgentId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationId", {
        id: "federationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.federationId.label", "Federation Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.federationId.label", "Federation Id"),
          placeholder: "Enter Federation Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:federationId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "federationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("federationName", {
        id: "federationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.federationName.label", "Federation Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.federationName.label", "Federation Name"),
          placeholder: "Enter Federation Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:federationName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "federationName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobId", {
        id: "trainingJobId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.trainingJobId.label", "Training Job Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.trainingJobId.label", "Training Job Id"),
          placeholder: "Enter Training Job Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:trainingJobId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "trainingJobId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("trainingJobObjective", {
        id: "trainingJobObjective",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.trainingJobObjective.label", "Training Job Objective")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.trainingJobObjective.label", "Training Job Objective"),
          placeholder: "Enter Training Job Objective",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:trainingJobObjective",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "trainingJobObjective",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("roundExecutionId", {
        id: "roundExecutionId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.roundExecutionId.label", "Round Execution Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.roundExecutionId.label", "Round Execution Id"),
          placeholder: "Enter Round Execution Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:roundExecutionId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "roundExecutionId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeNodeName", {
        id: "runtimeNodeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.runtimeNodeName.label", "Runtime Node Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.runtimeNodeName.label", "Runtime Node Name"),
          placeholder: "Enter Runtime Node Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:runtimeNodeName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "runtimeNodeName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("cpuLoad", {
        id: "cpuLoad",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.cpuLoad.label", "Cpu Load")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.cpuLoad.label", "Cpu Load"),
          placeholder: "Enter Cpu Load",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:cpuLoad",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "cpuLoad",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("gpuLoad", {
        id: "gpuLoad",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.gpuLoad.label", "Gpu Load")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.gpuLoad.label", "Gpu Load"),
          placeholder: "Enter Gpu Load",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:gpuLoad",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "gpuLoad",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("memoryLoad", {
        id: "memoryLoad",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.memoryLoad.label", "Memory Load")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.memoryLoad.label", "Memory Load"),
          placeholder: "Enter Memory Load",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:memoryLoad",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "memoryLoad",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("lastHeartbeatAt", {
        id: "lastHeartbeatAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.lastHeartbeatAt.label", "Last Heartbeat At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.lastHeartbeatAt.label", "Last Heartbeat At"),
          placeholder: "Enter Last Heartbeat At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:lastHeartbeatAt",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "lastHeartbeatAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("lastRecoveredAt", {
        id: "lastRecoveredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.lastRecoveredAt.label", "Last Recovered At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.lastRecoveredAt.label", "Last Recovered At"),
          placeholder: "Enter Last Recovered At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:lastRecoveredAt",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "lastRecoveredAt",
              view: "display",
              compact: true,
            },
          ) ?? getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.accessor("offlineDetectionPending", {
        id: "offlineDetectionPending",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.offlineDetectionPending.label", "Offline Detection Pending")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.offlineDetectionPending.label", "Offline Detection Pending"),
          placeholder: "Enter Offline Detection Pending",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:offlineDetectionPending",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "offlineDetectionPending",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel),
      }),
      columnHelper.accessor("recoveryDetectionPending", {
        id: "recoveryDetectionPending",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.recoveryDetectionPending.label", "Recovery Detection Pending")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.recoveryDetectionPending.label", "Recovery Detection Pending"),
          placeholder: "Enter Recovery Detection Pending",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:recoveryDetectionPending",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "recoveryDetectionPending",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel),
      }),
      columnHelper.accessor("resourcePressureDetectionPending", {
        id: "resourcePressureDetectionPending",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.resourcePressureDetectionPending.label", "Resource Pressure Detection Pending")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.resourcePressureDetectionPending.label", "Resource Pressure Detection Pending"),
          placeholder: "Enter Resource Pressure Detection Pending",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:resourcePressureDetectionPending",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "resourcePressureDetectionPending",
              view: "display",
              compact: true,
            },
          ) ?? formatValue(getValue(), t, dictionaryLabel),
      }),
      columnHelper.accessor("offlineReason", {
        id: "offlineReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.offlineReason.label", "Offline Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.offlineReason.label", "Offline Reason"),
          placeholder: "Enter Offline Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:offlineReason",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "offlineReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("recoveryReason", {
        id: "recoveryReason",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.recoveryReason.label", "Recovery Reason")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.recoveryReason.label", "Recovery Reason"),
          placeholder: "Enter Recovery Reason",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:recoveryReason",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "recoveryReason",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("pressureType", {
        id: "pressureType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.pressureType.label", "Pressure Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.pressureType.label", "Pressure Type"),
          placeholder: "Enter Pressure Type",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:pressureType",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "pressureType",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("observedValue", {
        id: "observedValue",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.observedValue.label", "Observed Value")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.observedValue.label", "Observed Value"),
          placeholder: "Enter Observed Value",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:observedValue",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "observedValue",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("thresholdValue", {
        id: "thresholdValue",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.thresholdValue.label", "Threshold Value")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.thresholdValue.label", "Threshold Value"),
          placeholder: "Enter Threshold Value",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:thresholdValue",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "thresholdValue",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("alertSeverity", {
        id: "alertSeverity",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.alertSeverity.label", "Alert Severity")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.alertSeverity.label", "Alert Severity"),
          placeholder: "Enter Alert Severity",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:alertSeverity",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "alertSeverity",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("alertMessage", {
        id: "alertMessage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.alertMessage.label", "Alert Message")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.alertMessage.label", "Alert Message"),
          placeholder: "Enter Alert Message",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:alertMessage",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "alertMessage",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("healthStatus", {
        id: "healthStatus",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.healthStatus.label", "Health Status")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.healthStatus.label", "Health Status"),
          placeholder: "Enter Health Status",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:healthStatus",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "healthStatus",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("telemetryRetentionPolicy", {
        id: "telemetryRetentionPolicy",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_telemetry_latest.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_telemetry_latest.fields.telemetryRetentionPolicy.label", "Telemetry Retention Policy"),
          placeholder: "Enter Telemetry Retention Policy",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeTelemetryLatestRecord>(
            frontendComposition,
            "field:runtime-telemetry-latest:display:telemetryRetentionPolicy",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-telemetry-latest",
              field: "telemetryRetentionPolicy",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              {renderSlotExtensions<RuntimeTelemetryLatestRecord>(
                frontendComposition,
                "row-actions:runtime-telemetry-latest:list",
                "rowActions.before",
                { resource: "runtime-telemetry-latest", record: row.original },
              )}
                {isCommandVisible(row.original, "", "", []) && (
                  <CommandButton
                    variant="ghost"
                    command="recordRuntimeTelemetry"
                    recordItemId={row.original.nodeId}
                    size="sm"
                    query={{
                      runtimeAgentId: row.original.runtimeAgentId,
                      federationId: row.original.federationId,
                      federationName: row.original.federationName,
                      trainingJobId: row.original.trainingJobId,
                      trainingJobObjective: row.original.trainingJobObjective,
                      roundExecutionId: row.original.roundExecutionId,
                      runtimeNodeName: row.original.runtimeNodeName,
                      cpuLoad: row.original.cpuLoad,
                      gpuLoad: row.original.gpuLoad,
                      memoryLoad: row.original.memoryLoad,
                      lastHeartbeatAt: row.original.lastHeartbeatAt,
                      lastRecoveredAt: row.original.lastRecoveredAt,
                      offlineDetectionPending: row.original.offlineDetectionPending,
                      recoveryDetectionPending: row.original.recoveryDetectionPending,
                      resourcePressureDetectionPending: row.original.resourcePressureDetectionPending,
                      offlineReason: row.original.offlineReason,
                      recoveryReason: row.original.recoveryReason,
                      pressureType: row.original.pressureType,
                      observedValue: row.original.observedValue,
                      thresholdValue: row.original.thresholdValue,
                      alertSeverity: row.original.alertSeverity,
                      alertMessage: row.original.alertMessage,
                      healthStatus: row.original.healthStatus,
                      telemetryRetentionPolicy: row.original.telemetryRetentionPolicy,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.nodeId} size="sm" />
              {renderSlotExtensions<RuntimeTelemetryLatestRecord>(
                frontendComposition,
                "row-actions:runtime-telemetry-latest:list",
                "rowActions.after",
                { resource: "runtime-telemetry-latest", record: row.original },
              )}
            </RowActionMenu>
          </div>
        ),
        enableSorting: false,
        size: 32,
      }),
    ];
  }, [dictionaryLabel, t]);

  const table = useTable({
    columns,
    initialState: {
      columnPinning: { right: ["actions"], left: ["select"] },
    },
    getRowId: (row) => String(row.nodeId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_telemetry_latest_read_model_entity",
        idField: "nodeId",
        idFields: ["nodeId"],
        queryFields: ["nodeId","runtimeAgentId","federationId","federationName","trainingJobId","trainingJobObjective","roundExecutionId","runtimeNodeName","cpuLoad","gpuLoad","memoryLoad","lastHeartbeatAt","lastRecoveredAt","offlineDetectionPending","recoveryDetectionPending","resourcePressureDetectionPending","offlineReason","recoveryReason","pressureType","observedValue","thresholdValue","alertSeverity","alertMessage","healthStatus","telemetryRetentionPolicy"],
        label: t("resources.runtime_telemetry_latest.label", "Runtime Telemetry Latest"),
        aggregateRoute: "noderuntimehealth",
        queryRoute: "runtimetelemetrylatest",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-telemetry-latest:list", "toolbar.before", { resource: "runtime-telemetry-latest", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-telemetry-latest:list", "toolbar.actions", { resource: "runtime-telemetry-latest", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-telemetry-latest:list", "toolbar.after", { resource: "runtime-telemetry-latest", table })}
      </RefineDataTable>
    </ListView>
  );
};
