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
import { ConfirmRuntimeInfrastructurePreparedCommandSchema, type ConfirmRuntimeInfrastructurePreparedCommandInput } from "@/domain/schemas";

export const RuntimeAgentEndpointCatalogConfirmRuntimeInfrastructurePrepared = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeInfrastructureId: searchParams.get("runtimeInfrastructureId") ?? undefined,
    runtimeAgentId: searchParams.get("runtimeAgentId") ?? undefined,
    runtimeInstallationPlanId: searchParams.get("runtimeInstallationPlanId") ?? undefined,
  } as unknown as Partial<ConfirmRuntimeInfrastructurePreparedCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ConfirmRuntimeInfrastructurePreparedCommandInput, ConfirmRuntimeInfrastructurePreparedCommandInput>({
    resource: "runtime_agent_endpoint_catalog",
    command: "confirmRuntimeInfrastructurePrepared",
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
      resolver: zodResolver(ConfirmRuntimeInfrastructurePreparedCommandSchema) as never,
    },
  });

  async function onSubmit(values: ConfirmRuntimeInfrastructurePreparedCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-agent-endpoint-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_agent_endpoint_catalog.commands.confirmRuntimeInfrastructurePrepared.label", "Confirm Runtime Infrastructure Prepared")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ConfirmRuntimeInfrastructurePrepared validation failed", errors))} className="space-y-8">
          {defaultValues.runtimeInfrastructureId !== undefined && defaultValues.runtimeInfrastructureId !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructureId" as never)} />
          ) : null}
          {defaultValues.runtimeAgentId !== undefined && defaultValues.runtimeAgentId !== null ? (
            <input type="hidden" {...form.register("runtimeAgentId" as never)} />
          ) : null}
          {defaultValues.runtimeInstallationPlanId !== undefined && defaultValues.runtimeInstallationPlanId !== null ? (
            <input type="hidden" {...form.register("runtimeInstallationPlanId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="preparedNodeCount"
            rules={{ required: "Prepared Node Count is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_agent_endpoint_catalog.commands.confirmRuntimeInfrastructurePrepared.fields.preparedNodeCount.label", "Prepared Node Count")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Prepared Node Count"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="preparationNotes"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_agent_endpoint_catalog.commands.confirmRuntimeInfrastructurePrepared.fields.preparationNotes.label", "Preparation Notes")}</FormLabel>
                <FormControl>
                  <Textarea
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Preparation Notes"}
                    rows={8}
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
