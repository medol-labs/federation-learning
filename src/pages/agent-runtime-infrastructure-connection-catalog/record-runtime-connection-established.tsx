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
import { RecordRuntimeConnectionEstablishedCommandSchema, type RecordRuntimeConnectionEstablishedCommandInput } from "@/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const AgentRuntimeInfrastructureConnectionCatalogRecordRuntimeConnectionEstablished = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeAgentId: searchParams.get("runtimeAgentId") ?? undefined,
    runtimeInfrastructureId: searchParams.get("runtimeInfrastructureId") ?? undefined,
    runtimeAgentEndpoint: searchParams.get("runtimeAgentEndpoint") ?? undefined,
    endpointScope: searchParams.get("endpointScope") ?? undefined,
  } as unknown as Partial<RecordRuntimeConnectionEstablishedCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RecordRuntimeConnectionEstablishedCommandInput, RecordRuntimeConnectionEstablishedCommandInput>({
    resource: "agent_runtime_infrastructure_connection_catalog",
    command: "recordRuntimeConnectionEstablished",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_runtime_infrastructure_connection_catalog_read_model_entity",
      idField: "runtimeInfrastructureId",
      label: t("resources.agent_runtime_infrastructure_connection_catalog.label", "Agent Runtime Infrastructure Connection Catalog"),
      aggregateRoute: "runtimeinfrastructure",
      queryRoute: "agentruntimeinfrastructureconnectioncatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "agent_runtime_infrastructure_connection_catalog_read_model_entity",
      idField: "runtimeInfrastructureId",
      label: t("resources.agent_runtime_infrastructure_connection_catalog.label", "Agent Runtime Infrastructure Connection Catalog"),
      aggregateRoute: "agentruntimeinfrastructureconnection",
      queryRoute: "agentruntimeinfrastructureconnectioncatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RecordRuntimeConnectionEstablishedCommandSchema) as never,
    },
  });

  async function onSubmit(values: RecordRuntimeConnectionEstablishedCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/agent-runtime-infrastructure-connection-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.label", "Record Runtime Connection Established")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RecordRuntimeConnectionEstablished validation failed", errors))} className="space-y-8">
          {defaultValues.runtimeInfrastructureId !== undefined && defaultValues.runtimeInfrastructureId !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructureId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="runtimeAgentId"
            rules={{ required: "Runtime Agent Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.runtimeAgentId.label", "Runtime Agent Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_agent_endpoint_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="runtimeName"
                  optionValue="runtimeAgentId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.runtimeAgentId.placeholder", "Select Runtime Agent Id")}
                  meta={{
                    idField: "runtimeAgentId",
                    label: t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.runtimeAgentId.label", "Runtime Agent Endpoint Catalog"),
                    aggregateRoute: "runtimeinfrastructure",
                    queryRoute: "runtimeagentendpointcatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="agentInstallMode"
            rules={{ required: "Agent Install Mode is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.agentInstallMode.label", "Agent Install Mode")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.agentInstallMode.placeholder", "Select Agent Install Mode")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_AGENT_INSTALL_MODE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.agentInstallMode.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.organizationId.label", "Organization Directory"),
                    aggregateRoute: "organization",
                    queryRoute: "organizationdirectory",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeName"
            rules={{ required: "Runtime Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.runtimeName.label", "Runtime Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Runtime Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeAgentEndpoint"
            rules={{ required: "Runtime Agent Endpoint is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.runtimeAgentEndpoint.label", "Runtime Agent Endpoint")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Runtime Agent Endpoint"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="endpointScope"
            rules={{ required: "Endpoint Scope is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.endpointScope.label", "Endpoint Scope")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.endpointScope.placeholder", "Select Endpoint Scope")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_AGENT_ENDPOINT_SCOPE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.agent_runtime_infrastructure_connection_catalog.commands.recordRuntimeConnectionEstablished.fields.endpointScope.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
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
