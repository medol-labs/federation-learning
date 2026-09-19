// Generated from config.json by the refine generator.
import { useTable } from "@refinedev/react-table";
import { useTranslate } from "@refinedev/core";
import { createColumnHelper } from "@tanstack/react-table";
import React from "react";

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
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";

type RuntimeEngineProfileCatalogRecord = {
  runtimeEngineProfileId: string;
  profileName: string;
  pluginProfile: string;
  runtimeEngineImage: string;
  imageDigest?: string;
  supportedModelPluginsDescription?: string;
  supportedAggregationAlgorithmsDescription?: string;
  active: boolean;
  state: "REGISTERED";
  registeredAt?: string;
};

const normalizeWorkflowState = (value: unknown) =>
  String(value ?? "").replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const isCommandVisible = (
  record: RuntimeEngineProfileCatalogRecord,
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

export const RuntimeEngineProfileCatalogList = () => {
  const t = useTranslate();
  const columns = React.useMemo(() => {
    const columnHelper = createColumnHelper<RuntimeEngineProfileCatalogRecord>();
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
      columnHelper.accessor("runtimeEngineProfileId", {
        id: "runtimeEngineProfileId",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.runtimeEngineProfileId.label", "Runtime Engine Profile Id")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.runtimeEngineProfileId.label", "Runtime Engine Profile Id"),
          placeholder: "Enter Runtime Engine Profile Id",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("profileName", {
        id: "profileName",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.profileName.label", "Profile Name")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.profileName.label", "Profile Name"),
          placeholder: "Enter Profile Name",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("pluginProfile", {
        id: "pluginProfile",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.pluginProfile.label", "Plugin Profile")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.pluginProfile.label", "Plugin Profile"),
          placeholder: "Enter Plugin Profile",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("runtimeEngineImage", {
        id: "runtimeEngineImage",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.runtimeEngineImage.label", "Runtime Engine Image")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.runtimeEngineImage.label", "Runtime Engine Image"),
          placeholder: "Enter Runtime Engine Image",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("imageDigest", {
        id: "imageDigest",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.imageDigest.label", "Image Digest")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.imageDigest.label", "Image Digest"),
          placeholder: "Enter Image Digest",
          variant: "text",
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("supportedModelPluginsDescription", {
        id: "supportedModelPluginsDescription",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.supportedModelPluginsDescription.label", "Supported Model Plugins Description")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.supportedModelPluginsDescription.label", "Supported Model Plugins Description"),
          placeholder: "Enter Supported Model Plugins Description",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("supportedAggregationAlgorithmsDescription", {
        id: "supportedAggregationAlgorithmsDescription",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.supportedAggregationAlgorithmsDescription.label", "Supported Aggregation Algorithms Description")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.supportedAggregationAlgorithmsDescription.label", "Supported Aggregation Algorithms Description"),
          placeholder: "Enter Supported Aggregation Algorithms Description",
          variant: "text",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => <CopyableText value={getValue()} compact />,
      }),
      columnHelper.accessor("active", {
        id: "active",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.active.label", "Active")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.active.label", "Active"),
          placeholder: "Enter Active",
          variant: "boolean",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? "Yes" : "No",
      }),
      columnHelper.accessor("state", {
        id: "state",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.state.label", "State")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.state.label", "State"),
          placeholder: "Select State",
          variant: "multiSelect",
          filterOperator: "inArray",
          options: [
            { label: "Registered", value: "REGISTERED" },
          ],
        },
        cell: ({ getValue }) => String(getValue() ?? "-"),
      }),
      columnHelper.accessor("registeredAt", {
        id: "registeredAt",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} label={t("resources.runtime_engine_profile_catalog.fields.registeredAt.label", "Registered At")} />
        ),
        enableSorting: true,
        enableColumnFilter: true,
        meta: {
          label: t("resources.runtime_engine_profile_catalog.fields.registeredAt.label", "Registered At"),
          placeholder: "Enter Registered At",
          variant: "date",
          filterOperator: "eq",
        },
        cell: ({ getValue }) => getValue() ? new Date(String(getValue())).toLocaleString() : "-",
      }),
      columnHelper.display({
        id: "actions",
        header: t("table.actions", "Actions"),
        cell: ({ row }) => (
          <div className="flex gap-2">
            <RowActionMenu>
              <ShowButton variant="ghost" recordItemId={row.original.runtimeEngineProfileId} size="sm" />
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
    getRowId: (row) => String(row.runtimeEngineProfileId),
    refineCoreProps: {
      dataProviderName: "federation-learning-platform",
      syncWithLocation: false,
      meta: {
        tableName: "runtime_engine_profile_catalog_read_model_entity",
        idField: "runtimeEngineProfileId",
        idFields: ["runtimeEngineProfileId"],
        queryFields: ["runtimeEngineProfileId","profileName","pluginProfile","runtimeEngineImage","imageDigest","supportedModelPluginsDescription","supportedAggregationAlgorithmsDescription","active","state","registeredAt"],
        label: t("resources.runtime_engine_profile_catalog.label", "Runtime Engine Profile Catalog"),
        aggregateRoute: "runtimeengineprofile",
        queryRoute: "runtimeengineprofilecatalog",
        dataProviderName: "federation-learning-platform",
      },
    },
  });

  return (
    <ListView>
      <ListViewHeader canCreate={false}>
        <CommandButton variant="default" command="registerRuntimeEngineProfile" />
      </ListViewHeader>
      <RefineDataTable table={table} actionBar={
        null
      }>
        <ListToolbar
          table={table.reactTable}
          isQuerying={table.refineCore.tableQuery.isFetching}
          onQuery={() => table.refineCore.tableQuery.refetch()}
        />
      </RefineDataTable>
    </ListView>
  );
};
