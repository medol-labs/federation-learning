// Generated from config.json by the refine generator.
import { useParsed } from "@refinedev/core";
import { useTranslate } from "@refinedev/core";
import { useNavigate, useSearchParams } from "react-router";

import {
  CreateView,
  CreateViewHeader,
} from "@/components/refine-ui/views/create-view";
import { Button } from "@/components/ui/button";
import {
  Form,
} from "@/components/ui/form";
import { useCommandForm } from "@/hooks/command/useCommandForm";
import { zodResolver } from "@hookform/resolvers/zod";
import { LoadRuntimeAgentBootstrapConfigurationCommandSchema, type LoadRuntimeAgentBootstrapConfigurationCommandInput } from "@/domain/schemas";


export const RuntimeAgentLifecycleCatalogLoadRuntimeAgentBootstrapConfiguration = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    bootstrapRequestId: searchParams.get("bootstrapRequestId") ?? undefined,
  } as Partial<LoadRuntimeAgentBootstrapConfigurationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<LoadRuntimeAgentBootstrapConfigurationCommandInput, LoadRuntimeAgentBootstrapConfigurationCommandInput>({
    resource: "runtime_agent_lifecycle_catalog",
    command: "loadRuntimeAgentBootstrapConfiguration",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "runtime_agent_lifecycle_catalog_read_model_entity",
      idField: "runtimeAgentId",
      label: t("resources.runtime_agent_lifecycle_catalog.label", "Runtime Agent Lifecycle Catalog"),
      aggregateRoute: "runtimeagentlifecycle",
      queryRoute: "runtimeagentlifecyclecatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "runtime_agent_lifecycle_catalog_read_model_entity",
      idField: "runtimeAgentId",
      label: t("resources.runtime_agent_lifecycle_catalog.label", "Runtime Agent Lifecycle Catalog"),
      aggregateRoute: "runtimeagentlifecycle",
      queryRoute: "runtimeagentlifecyclecatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(LoadRuntimeAgentBootstrapConfigurationCommandSchema) as never,
    },
  });

  function onSubmit(values: LoadRuntimeAgentBootstrapConfigurationCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_agent_lifecycle_catalog.commands.loadRuntimeAgentBootstrapConfiguration.label", "Load Runtime Agent Bootstrap Configuration")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("LoadRuntimeAgentBootstrapConfiguration validation failed", errors))} className="space-y-8">
          {defaultValues.bootstrapRequestId !== undefined && defaultValues.bootstrapRequestId !== null ? (
            <input type="hidden" {...form.register("bootstrapRequestId" as never)} />
          ) : null}
          <div className="flex gap-2">
            <Button
              type="submit"
              {...form.saveButtonProps}
              disabled={form.formState.isSubmitting}
            >
              {form.formState.isSubmitting ? t("buttons.submitting", "Submitting...") : t("buttons.submit", "Submit")}
            </Button>
            <Button
              type="button"
              variant="outline"
              onClick={() => navigate(-1)}
            >
              {t("buttons.cancel", "Cancel")}
            </Button>
          </div>
        </form>
      </Form>
    </CreateView>
  );
};
