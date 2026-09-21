// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { DataTableColumnHeader } from "@/components/data-table/data-table-column-header";
import { CommandButton } from "@/components/refine-ui/buttons/command";
import { EditButton } from "@/components/refine-ui/buttons/edit";
import { ShowButton } from "@/components/refine-ui/buttons/show";
import { RefineDataTable } from "@/components/refine-ui/data-table/refine-data-table";
import { RowActionMenu } from "@/components/refine-ui/row-action-menu";
import {
  ListToolbar,
  ListView,
  ListViewHeader
} from "@/components/refine-ui/views/list-view";
import { Checkbox } from "@/components/ui/checkbox";
import { renderFieldOverride, renderSlotExtensions } from "@/platform/composition";
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";

type RuntimeInstallationGuideRecord = {
  runtimeInstallationPlanId: string;
  organizationId: string;
  runtimeInfrastructureId: string;
  runtimeAgentId?: string;
  runtimeInfrastructureState?: "PLANNED" | "REGISTERED" | "PREPARED" | "VERIFIED" | "VERIFICATION_FAILED" | "AGENT_READY" | "RUNTIME_AGENT_FAILED" | "OFFLINE" | "CONNECTED";
  runtimeInfrastructurePackageId?: string;
  runtimeInfrastructurePackageName?: string;
  runtimeInfrastructurePackageVersion?: string;
  organizationName?: string;
  runtimeName?: string;
  bootstrapCommand: string;
  nodeLabelCommand: string;
  nodeTaintCommand: string;
  runtimeAgentNodeSelectorYaml: string;
  runtimeAgentTolerationsYaml: string;
  bootstrapConfigYaml: string;
  runtimeEnvironmentType?: string;
  agentInstallMode?: string;
  expectedNodeCount?: number;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeInstallationGuideRecord,
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

export const RuntimeInstallationGuideList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeInstallationGuideRecord>();
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
      columnHelper.accessor("runtimeInstallationPlanId", {
        id: "runtimeInstallationPlanId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeInstallationPlanId.label", "Runtime Installation Plan Id"),
          placeholder: "Enter Runtime Installation Plan Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeInstallationPlanId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeInstallationPlanId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationId", {
        id: "organizationId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.organizationId.label", "Organization Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.organizationId.label", "Organization Id"),
          placeholder: "Enter Organization Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:organizationId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "organizationId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureId", {
        id: "runtimeInfrastructureId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id"),
          placeholder: "Enter Runtime Infrastructure Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeInfrastructureId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeInfrastructureId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeAgentId", {
        id: "runtimeAgentId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeAgentId.label", "Runtime Agent Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeAgentId.label", "Runtime Agent Id"),
          placeholder: "Enter Runtime Agent Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeAgentId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeAgentId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructureState", {
        id: "runtimeInfrastructureState",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.label", "Runtime Infrastructure State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeInfrastructureState.label", "Runtime Infrastructure State"),
          placeholder: "Select Runtime Infrastructure State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Planned", value: "PLANNED" },
            { label: "Registered", value: "REGISTERED" },
            { label: "Prepared", value: "PREPARED" },
            { label: "Verified", value: "VERIFIED" },
            { label: "Verification Failed", value: "VERIFICATION_FAILED" },
            { label: "Agent Ready", value: "AGENT_READY" },
            { label: "Runtime Agent Failed", value: "RUNTIME_AGENT_FAILED" },
            { label: "Offline", value: "OFFLINE" },
            { label: "Connected", value: "CONNECTED" },
          ],
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeInfrastructureState",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeInfrastructureState",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageId", {
        id: "runtimeInfrastructurePackageId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id"),
          placeholder: "Enter Runtime Infrastructure Package Id",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeInfrastructurePackageId",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeInfrastructurePackageId",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageName", {
        id: "runtimeInfrastructurePackageName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name"),
          placeholder: "Enter Runtime Infrastructure Package Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeInfrastructurePackageName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeInfrastructurePackageName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeInfrastructurePackageVersion", {
        id: "runtimeInfrastructurePackageVersion",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version"),
          placeholder: "Enter Runtime Infrastructure Package Version",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeInfrastructurePackageVersion",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeInfrastructurePackageVersion",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("organizationName", {
        id: "organizationName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.organizationName.label", "Organization Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.organizationName.label", "Organization Name"),
          placeholder: "Enter Organization Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:organizationName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "organizationName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeName", {
        id: "runtimeName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeName.label", "Runtime Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeName.label", "Runtime Name"),
          placeholder: "Enter Runtime Name",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeName",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeName",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("bootstrapCommand", {
        id: "bootstrapCommand",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.bootstrapCommand.label", "Bootstrap Command")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.bootstrapCommand.label", "Bootstrap Command"),
          placeholder: "Enter Bootstrap Command",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:bootstrapCommand",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "bootstrapCommand",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("nodeLabelCommand", {
        id: "nodeLabelCommand",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.nodeLabelCommand.label", "Node Label Command")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.nodeLabelCommand.label", "Node Label Command"),
          placeholder: "Enter Node Label Command",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:nodeLabelCommand",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "nodeLabelCommand",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("nodeTaintCommand", {
        id: "nodeTaintCommand",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.nodeTaintCommand.label", "Node Taint Command")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.nodeTaintCommand.label", "Node Taint Command"),
          placeholder: "Enter Node Taint Command",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:nodeTaintCommand",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "nodeTaintCommand",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("runtimeAgentNodeSelectorYaml", {
        id: "runtimeAgentNodeSelectorYaml",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeAgentNodeSelectorYaml.label", "Runtime Agent Node Selector Yaml")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeAgentNodeSelectorYaml.label", "Runtime Agent Node Selector Yaml"),
          placeholder: "Enter Runtime Agent Node Selector Yaml",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeAgentNodeSelectorYaml",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeAgentNodeSelectorYaml",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("runtimeAgentTolerationsYaml", {
        id: "runtimeAgentTolerationsYaml",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeAgentTolerationsYaml.label", "Runtime Agent Tolerations Yaml")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeAgentTolerationsYaml.label", "Runtime Agent Tolerations Yaml"),
          placeholder: "Enter Runtime Agent Tolerations Yaml",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeAgentTolerationsYaml",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeAgentTolerationsYaml",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("bootstrapConfigYaml", {
        id: "bootstrapConfigYaml",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.bootstrapConfigYaml.label", "Bootstrap Config Yaml")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.bootstrapConfigYaml.label", "Bootstrap Config Yaml"),
          placeholder: "Enter Bootstrap Config Yaml",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:bootstrapConfigYaml",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "bootstrapConfigYaml",
              view: "display",
              compact: true,
            },
          ) ?? <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("runtimeEnvironmentType", {
        id: "runtimeEnvironmentType",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.runtimeEnvironmentType.label", "Runtime Environment Type")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.runtimeEnvironmentType.label", "Runtime Environment Type"),
          placeholder: "Enter Runtime Environment Type",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:runtimeEnvironmentType",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "runtimeEnvironmentType",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("agentInstallMode", {
        id: "agentInstallMode",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.agentInstallMode.label", "Agent Install Mode")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.agentInstallMode.label", "Agent Install Mode"),
          placeholder: "Enter Agent Install Mode",
          variant: "text",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:agentInstallMode",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "agentInstallMode",
              view: "display",
              compact: true,
            },
          ) ?? String(getValue() ?? "-"),
      }),
      columnHelper.accessor("expectedNodeCount", {
        id: "expectedNodeCount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_installation_guide.fields.expectedNodeCount.label", "Expected Node Count")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_installation_guide.fields.expectedNodeCount.label", "Expected Node Count"),
          placeholder: "Enter Expected Node Count",
          variant: "number",
          filterOperator: "eq",
        },
        cell: ({ getValue, row }) =>
          renderFieldOverride<RuntimeInstallationGuideRecord>(
            frontendComposition,
            "field:runtime-installation-guide:display:expectedNodeCount",
            {
              value: getValue(),
              record: row.original,
              resource: "runtime-installation-guide",
              field: "expectedNodeCount",
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
              {renderSlotExtensions<RuntimeInstallationGuideRecord>(
                frontendComposition,
                "row-actions:runtime-installation-guide:list",
                "rowActions.before",
                { resource: "runtime-installation-guide", record: row.original },
              )}
                {isCommandVisible(row.original, "", "runtimeInfrastructureState", ["Registered"]) && (
                  <CommandButton
                    variant="ghost"
                    command="confirmRuntimeInfrastructurePrepared"
                    recordItemId={row.original.runtimeInstallationPlanId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                      organizationName: row.original.organizationName,
                      runtimeInfrastructurePackageId: row.original.runtimeInfrastructurePackageId,
                      runtimeInfrastructurePackageName: row.original.runtimeInfrastructurePackageName,
                      runtimeInfrastructurePackageVersion: row.original.runtimeInfrastructurePackageVersion,
                      runtimeEnvironmentType: row.original.runtimeEnvironmentType,
                      runtimeName: row.original.runtimeName,
                      agentInstallMode: row.original.agentInstallMode,
                      expectedNodeCount: row.original.expectedNodeCount,
                      runtimeInfrastructureId: row.original.runtimeInfrastructureId,
                      runtimeInstallationPlanId: row.original.runtimeInstallationPlanId,
                      runtimeAgentId: row.original.runtimeAgentId,
                    }}
                  />
                )}
                {isCommandVisible(row.original, "", "runtimeInfrastructureState", ["Planned"]) && (
                  <CommandButton
                    variant="ghost"
                    command="registerRuntimeInfrastructure"
                    recordItemId={row.original.runtimeInstallationPlanId}
                    size="sm"
                    query={{
                      organizationId: row.original.organizationId,
                      organizationName: row.original.organizationName,
                      runtimeInfrastructurePackageId: row.original.runtimeInfrastructurePackageId,
                      runtimeInfrastructurePackageName: row.original.runtimeInfrastructurePackageName,
                      runtimeInfrastructurePackageVersion: row.original.runtimeInfrastructurePackageVersion,
                      runtimeEnvironmentType: row.original.runtimeEnvironmentType,
                      runtimeName: row.original.runtimeName,
                      agentInstallMode: row.original.agentInstallMode,
                      expectedNodeCount: row.original.expectedNodeCount,
                      runtimeInfrastructureId: row.original.runtimeInfrastructureId,
                      runtimeInstallationPlanId: row.original.runtimeInstallationPlanId,
                    }}
                  />
                )}
              <ShowButton variant="ghost" recordItemId={row.original.runtimeInstallationPlanId} size="sm" />
              {renderSlotExtensions<RuntimeInstallationGuideRecord>(
                frontendComposition,
                "row-actions:runtime-installation-guide:list",
                "rowActions.after",
                { resource: "runtime-installation-guide", record: row.original },
              )}
            </RowActionMenu>
          </div>
        ),
        enableSorting: false,
        size: 32,
      }),
    ];
  }, [t]);

  const table = useTable({
    columns,
    initialState: {
      columnPinning: { right: ["actions"], left: ["select"] },
    },
    getRowId: (row) => String(row.runtimeInstallationPlanId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_installation_guide_read_model_entity",
        idField: "runtimeInstallationPlanId",
        idFields: ["runtimeInstallationPlanId"],
        queryFields: ["runtimeInstallationPlanId","organizationId","runtimeInfrastructureId","runtimeAgentId","runtimeInfrastructureState","runtimeInfrastructurePackageId","runtimeInfrastructurePackageName","runtimeInfrastructurePackageVersion","organizationName","runtimeName","bootstrapCommand","nodeLabelCommand","nodeTaintCommand","runtimeAgentNodeSelectorYaml","runtimeAgentTolerationsYaml","bootstrapConfigYaml","runtimeEnvironmentType","agentInstallMode","expectedNodeCount"],
        label: t("resources.runtime_installation_guide.label", "Runtime Installation Guide"),
        aggregateRoute: "runtimeinstallationplan",
        queryRoute: "runtimeinstallationguide",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-installation-guide:list", "toolbar.before", { resource: "runtime-installation-guide", table })}
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-installation-guide:list", "toolbar.actions", { resource: "runtime-installation-guide", table })}
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
        {renderSlotExtensions(frontendComposition, "toolbar:runtime-installation-guide:list", "toolbar.after", { resource: "runtime-installation-guide", table })}
      </RefineDataTable>
    </ListView>
  );
};
