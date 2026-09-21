// Generated from config.json by the refine generator.
import { useParsed } from "@refinedev/core";
import { useTranslate } from "@refinedev/core";
import { useNavigate, useSearchParams } from "react-router";

import {
  CreateView,
  CreateViewHeader,
} from "@/components/refine-ui/views/create-view";
import { frontendComposition } from "@/app/composition/composition.resolved";
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
import { runFormBehavior } from "@/platform/composition";
import { zodResolver } from "@hookform/resolvers/zod";
import { ConfirmRuntimeInfrastructurePreparedCommandSchema, type ConfirmRuntimeInfrastructurePreparedCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RuntimeInstallationGuideConfirmRuntimeInfrastructurePrepared = () => {
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
    runtimeAgentId: searchParams.get("runtimeAgentId") ?? undefined,
  } as unknown as Partial<ConfirmRuntimeInfrastructurePreparedCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ConfirmRuntimeInfrastructurePreparedCommandInput, ConfirmRuntimeInfrastructurePreparedCommandInput>({
    resource: "runtime_installation_guide",
    command: "confirmRuntimeInfrastructurePrepared",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_installation_guide_read_model_entity",
      idField: "runtimeInstallationPlanId",
      label: t("resources.runtime_installation_guide.label", "Runtime Installation Guide"),
      aggregateRoute: "runtimeinfrastructure",
      queryRoute: "runtimeinstallationguide",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_installation_guide_read_model_entity",
      idField: "runtimeInstallationPlanId",
      label: t("resources.runtime_installation_guide.label", "Runtime Installation Guide"),
      aggregateRoute: "runtimeinstallationplan",
      queryRoute: "runtimeinstallationguide",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ConfirmRuntimeInfrastructurePreparedCommandSchema) as never,
    },
  });

  async function onSubmit(values: ConfirmRuntimeInfrastructurePreparedCommandInput) {
    const result = await runFormBehavior<ConfirmRuntimeInfrastructurePreparedCommandInput>(
      frontendComposition,
      "behavior:runtime-installation-guide:confirmRuntimeInfrastructurePrepared",
      {
      ...defaultValues,
      ...values,
      } as ConfirmRuntimeInfrastructurePreparedCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/runtime-installation-guide");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.label", "Confirm Runtime Infrastructure Prepared")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ConfirmRuntimeInfrastructurePrepared validation failed", errors))} className="space-y-8">
          {defaultValues.runtimeInfrastructureId !== undefined && defaultValues.runtimeInfrastructureId !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructureId" as never)} />
          ) : null}
          {defaultValues.runtimeInstallationPlanId !== undefined && defaultValues.runtimeInstallationPlanId !== null ? (
            <input type="hidden" {...form.register("runtimeInstallationPlanId" as never)} />
          ) : null}
          {defaultValues.runtimeAgentId !== undefined && defaultValues.runtimeAgentId !== null ? (
            <input type="hidden" {...form.register("runtimeAgentId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.organizationId.label", "Organization Id")}</FormLabel>
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
                  placeholder={t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.organizationId.label", "Organization Directory"),
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
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.organizationName.label", "Organization Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Id")}</FormLabel>
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
                  placeholder={t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.runtimeInfrastructurePackageId.placeholder", "Select Runtime Infrastructure Package Id")}
                  meta={{
                    idField: "runtimeInfrastructurePackageId",
                    label: t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.runtimeInfrastructurePackageId.label", "Runtime Infrastructure Package Catalog"),
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
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.runtimeInfrastructurePackageName.label", "Runtime Infrastructure Package Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.runtimeInfrastructurePackageVersion.label", "Runtime Infrastructure Package Version")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</FormLabel>
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
                  placeholder={t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.runtimeEnvironmentType.placeholder", "Select Runtime Environment Type")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_ENVIRONMENT_TYPE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.runtimeEnvironmentType.label", "Dictionary Value Catalog"),
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
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.runtimeName.label", "Runtime Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.agentInstallMode.label", "Agent Install Mode")}</FormLabel>
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
                  placeholder={t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.agentInstallMode.placeholder", "Select Agent Install Mode")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_AGENT_INSTALL_MODE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.agentInstallMode.label", "Dictionary Value Catalog"),
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
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.expectedNodeCount.label", "Expected Node Count")}</FormLabel>
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
          <FormField
            control={form.control}
            name="preparedNodeCount"
            rules={{ required: "Prepared Node Count is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.preparedNodeCount.label", "Prepared Node Count")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_installation_guide.commands.confirmRuntimeInfrastructurePrepared.fields.preparationNotes.label", "Preparation Notes")}</FormLabel>
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
