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
import { RegisterRuntimeInfrastructureCommandSchema, type RegisterRuntimeInfrastructureCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RuntimeInfrastructureAccessViewRegisterRuntimeInfrastructure = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationId: searchParams.get("organizationId") ?? undefined,
    organizationName: searchParams.get("organizationName") ?? undefined,
    runtimeInfrastructurePackageId: searchParams.get("runtimeInfrastructurePackageId") ?? undefined,
    runtimeInfrastructurePackageName: searchParams.get("runtimeInfrastructurePackageName") ?? undefined,
    runtimeInfrastructurePackageVersion: searchParams.get("runtimeInfrastructurePackageVersion") ?? undefined,
    runtimeEnvironmentType: searchParams.get("runtimeEnvironmentType") ?? undefined,
    runtimeName: searchParams.get("runtimeName") ?? undefined,
    agentInstallMode: searchParams.get("agentInstallMode") ?? undefined,
    expectedNodeCount: (() => { const value = searchParams.get("expectedNodeCount"); return value === null ? undefined : Number(value); })(),
    runtimeInfrastructureId: searchParams.get("runtimeInfrastructureId") ?? undefined,
    runtimeInstallationPlanId: searchParams.get("runtimeInstallationPlanId") ?? undefined,
  } as unknown as Partial<RegisterRuntimeInfrastructureCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterRuntimeInfrastructureCommandInput, RegisterRuntimeInfrastructureCommandInput>({
    resource: "runtime_infrastructure_access_view",
    command: "registerRuntimeInfrastructure",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_infrastructure_access_view_read_model_entity",
      idField: "runtimeInfrastructureId",
      label: t("resources.runtime_infrastructure_access_view.label", "Runtime Infrastructure Access View"),
      aggregateRoute: "runtimeinfrastructure",
      queryRoute: "runtimeinfrastructureaccessview",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_infrastructure_access_view_read_model_entity",
      idField: "runtimeInfrastructureId",
      label: t("resources.runtime_infrastructure_access_view.label", "Runtime Infrastructure Access View"),
      aggregateRoute: "runtimeinfrastructure",
      queryRoute: "runtimeinfrastructureaccessview",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RegisterRuntimeInfrastructureCommandSchema) as never,
    },
  });

  async function onSubmit(values: RegisterRuntimeInfrastructureCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-infrastructure-access-view");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.label", "Register Runtime Infrastructure")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterRuntimeInfrastructure validation failed", errors))} className="space-y-8">
          {defaultValues.runtimeInfrastructureId !== undefined && defaultValues.runtimeInfrastructureId !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructureId" as never)} />
          ) : null}
          {defaultValues.runtimeInstallationPlanId !== undefined && defaultValues.runtimeInstallationPlanId !== null ? (
            <input type="hidden" {...form.register("runtimeInstallationPlanId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.organizationId.label", "Organization Directory"),
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
            name="organizationName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.organizationName.label", "Organization Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Organization Name"}
                  />
                </FormControl>
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
                <FormLabel>{t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_infrastructure_package_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="packageName"
                  optionValue="runtimeInfrastructurePackageId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.runtimeInfrastructurePackageId.placeholder", "Select Runtime Infrastructure Package Id")}
                  meta={{
                    idField: "runtimeInfrastructurePackageId",
                    label: t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Catalog"),
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
            name="runtimeInfrastructurePackageName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Runtime Infrastructure Package Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeInfrastructurePackageVersion"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Runtime Infrastructure Package Version"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeEnvironmentType"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.runtimeEnvironmentType.placeholder", "Select Runtime Environment Type")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_ENVIRONMENT_TYPE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.runtimeEnvironmentType.label", "Dictionary Value Catalog"),
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
            name="runtimeName"
            rules={{ required: "Runtime Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.runtimeName.label", "Runtime Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.agentInstallMode.label", "Agent Install Mode")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.agentInstallMode.placeholder", "Select Agent Install Mode")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_AGENT_INSTALL_MODE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.agentInstallMode.label", "Dictionary Value Catalog"),
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
                <FormLabel>{t("resources.runtime_infrastructure_access_view.commands.registerRuntimeInfrastructure.fields.expectedNodeCount.label", "Expected Node Count")}</FormLabel>
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
