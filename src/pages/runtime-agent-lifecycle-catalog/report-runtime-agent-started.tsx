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
import { ReportRuntimeAgentStartedCommandSchema, type ReportRuntimeAgentStartedCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const RuntimeAgentLifecycleCatalogReportRuntimeAgentStarted = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeInfrastructureId: searchParams.get("runtimeInfrastructureId") ?? undefined,
    agentVersion: searchParams.get("agentVersion") ?? undefined,
    runtimeAgentId: searchParams.get("runtimeAgentId") ?? undefined,
  } as Partial<ReportRuntimeAgentStartedCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ReportRuntimeAgentStartedCommandInput, ReportRuntimeAgentStartedCommandInput>({
    resource: "runtime_agent_lifecycle_catalog",
    command: "reportRuntimeAgentStarted",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-console",
    queryDataProviderName: "federation-learning-console",
    meta: {
      tableName: "runtime_agent_lifecycle_catalog_read_model_entity",
      idField: "runtimeAgentId",
      label: t("resources.runtime_agent_lifecycle_catalog.label", "Runtime Agent Lifecycle Catalog"),
      aggregateRoute: "runtimeagentlifecycle",
      queryRoute: "runtimeagentlifecyclecatalog",
      dataProviderName: "federation-learning-console",
    },
    queryMeta: {
      tableName: "runtime_agent_lifecycle_catalog_read_model_entity",
      idField: "runtimeAgentId",
      label: t("resources.runtime_agent_lifecycle_catalog.label", "Runtime Agent Lifecycle Catalog"),
      aggregateRoute: "runtimeagentlifecycle",
      queryRoute: "runtimeagentlifecyclecatalog",
      dataProviderName: "federation-learning-console",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ReportRuntimeAgentStartedCommandSchema) as never,
    },
  });

  function onSubmit(values: ReportRuntimeAgentStartedCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_agent_lifecycle_catalog.commands.reportRuntimeAgentStarted.label", "Report Runtime Agent Started")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <input type="hidden" {...form.register("runtimeAgentId" as never)} />
          <FormField
            control={form.control}
            name="runtimeInfrastructureId"
            rules={{ required: "Runtime Infrastructure Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_agent_lifecycle_catalog.commands.reportRuntimeAgentStarted.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_infrastructure_access_view"
                  dataProviderName="federation-learning-platform"
                  optionLabel="runtimeInfrastructurePackageName"
                  optionValue="runtimeInfrastructureId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.runtime_agent_lifecycle_catalog.commands.reportRuntimeAgentStarted.fields.runtimeInfrastructureId.placeholder", "Select Runtime Infrastructure Id")}
                  meta={{
                    idField: "runtimeInfrastructureId",
                    label: t("resources.runtime_agent_lifecycle_catalog.commands.reportRuntimeAgentStarted.fields.runtimeInfrastructureId.label", "Runtime Infrastructure Access View"),
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
            name="agentVersion"
            rules={{ required: "Agent Version is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_agent_lifecycle_catalog.commands.reportRuntimeAgentStarted.fields.agentVersion.label", "Agent Version")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Agent Version"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
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
