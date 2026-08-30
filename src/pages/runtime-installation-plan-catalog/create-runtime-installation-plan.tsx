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
import { CreateRuntimeInstallationPlanCommandSchema, type CreateRuntimeInstallationPlanCommandInput } from "@/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RuntimeInstallationPlanCatalogCreateRuntimeInstallationPlan = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationId: searchParams.get("organizationId") ?? undefined,
    runtimeInfrastructurePackageId: searchParams.get("runtimeInfrastructurePackageId") ?? undefined,
    runtimeName: searchParams.get("runtimeName") ?? undefined,
    agentInstallMode: searchParams.get("agentInstallMode") ?? undefined,
    expectedNodeCount: (() => { const value = searchParams.get("expectedNodeCount"); return value === null ? undefined : Number(value); })(),
  } as unknown as Partial<CreateRuntimeInstallationPlanCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<CreateRuntimeInstallationPlanCommandInput, CreateRuntimeInstallationPlanCommandInput>({
    resource: "runtime_installation_plan_catalog",
    command: "createRuntimeInstallationPlan",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_installation_plan_catalog_read_model_entity",
      idField: "runtimeInstallationPlanId",
      label: t("resources.runtime_installation_plan_catalog.label", "Runtime Installation Plan Catalog"),
      aggregateRoute: "runtimeinstallationplan",
      queryRoute: "runtimeinstallationplancatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_installation_plan_catalog_read_model_entity",
      idField: "runtimeInstallationPlanId",
      label: t("resources.runtime_installation_plan_catalog.label", "Runtime Installation Plan Catalog"),
      aggregateRoute: "runtimeinstallationplan",
      queryRoute: "runtimeinstallationplancatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(CreateRuntimeInstallationPlanCommandSchema) as never,
    },
  });

  async function onSubmit(values: CreateRuntimeInstallationPlanCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-installation-plan-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.label", "Create Runtime Installation Plan")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("CreateRuntimeInstallationPlan validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.organizationId.label", "Organization Directory"),
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
            name="runtimeInfrastructurePackageId"
            rules={{ required: "Runtime Infrastructure Package Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_infrastructure_package_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="packageName"
                  optionValue="runtimeInfrastructurePackageId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.runtimeInfrastructurePackageId.placeholder", "Select Runtime Infrastructure Package Id")}
                  meta={{
                    idField: "runtimeInfrastructurePackageId",
                    label: t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Catalog"),
                    aggregateRoute: "runtimeinfrastructurepackage",
                    queryRoute: "runtimeinfrastructurepackagecatalog",
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
                <FormLabel>{t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.runtimeName.label", "Runtime Name")}</FormLabel>
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
            name="agentInstallMode"
            rules={{ required: "Agent Install Mode is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.agentInstallMode.label", "Agent Install Mode")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.agentInstallMode.placeholder", "Select Agent Install Mode")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_AGENT_INSTALL_MODE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.agentInstallMode.label", "Dictionary Value Catalog"),
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
            name="expectedNodeCount"
            rules={{ required: "Expected Node Count is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_installation_plan_catalog.commands.createRuntimeInstallationPlan.fields.expectedNodeCount.label", "Expected Node Count")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Expected Node Count"}
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
