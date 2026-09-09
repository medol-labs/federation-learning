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
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { useCommandForm } from "@/hooks/command/useCommandForm";
import { zodResolver } from "@hookform/resolvers/zod";
import { RetryRuntimeAgentDeploymentCommandSchema, type RetryRuntimeAgentDeploymentCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RuntimeAgentEndpointCatalogRetryRuntimeAgentDeployment = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeAgentId: searchParams.get("runtimeAgentId") ?? undefined,
    runtimeInfrastructureId: searchParams.get("runtimeInfrastructureId") ?? undefined,
  } as unknown as Partial<RetryRuntimeAgentDeploymentCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RetryRuntimeAgentDeploymentCommandInput, RetryRuntimeAgentDeploymentCommandInput>({
    resource: "runtime_agent_endpoint_catalog",
    command: "retryRuntimeAgentDeployment",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_agent_endpoint_catalog_read_model_entity",
      idField: "runtimeAgentId",
      label: t("resources.runtime_agent_endpoint_catalog.label", "Runtime Agent Endpoint Catalog"),
      aggregateRoute: "runtimeinfrastructure",
      queryRoute: "runtimeagentendpointcatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_agent_endpoint_catalog_read_model_entity",
      idField: "runtimeAgentId",
      label: t("resources.runtime_agent_endpoint_catalog.label", "Runtime Agent Endpoint Catalog"),
      aggregateRoute: "runtimeinfrastructure",
      queryRoute: "runtimeagentendpointcatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RetryRuntimeAgentDeploymentCommandSchema) as never,
    },
  });

  async function onSubmit(values: RetryRuntimeAgentDeploymentCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-agent-endpoint-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_agent_endpoint_catalog.commands.retryRuntimeAgentDeployment.label", "Retry Runtime Agent Deployment")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RetryRuntimeAgentDeployment validation failed", errors))} className="space-y-8">
          {defaultValues.runtimeAgentId !== undefined && defaultValues.runtimeAgentId !== null ? (
            <input type="hidden" {...form.register("runtimeAgentId" as never)} />
          ) : null}
          {defaultValues.runtimeInfrastructureId !== undefined && defaultValues.runtimeInfrastructureId !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructureId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="currentRuntimeInfrastructureState"
            rules={{ required: "Current Runtime Infrastructure State is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_agent_endpoint_catalog.commands.retryRuntimeAgentDeployment.fields.currentRuntimeInfrastructureState.label", "Current Runtime Infrastructure State")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_infrastructure_access_view"
                  dataProviderName="federation-learning-platform"
                  optionLabel="runtimeName"
                  optionValue="state"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.runtime_agent_endpoint_catalog.commands.retryRuntimeAgentDeployment.fields.currentRuntimeInfrastructureState.placeholder", "Select Current Runtime Infrastructure State")}
                  meta={{
                    idField: "runtimeInfrastructureId",
                    label: t("resources.runtime_agent_endpoint_catalog.commands.retryRuntimeAgentDeployment.fields.currentRuntimeInfrastructureState.label", "Runtime Infrastructure Access View"),
                    aggregateRoute: "runtimeinfrastructure",
                    queryRoute: "runtimeinfrastructureaccessview",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="retryReason"
            rules={{ required: "Retry Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_agent_endpoint_catalog.commands.retryRuntimeAgentDeployment.fields.retryReason.label", "Retry Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Retry Reason"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="flex gap-2">
            <Button
              type="submit"
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
