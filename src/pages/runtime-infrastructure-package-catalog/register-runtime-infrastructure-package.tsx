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
import { RegisterRuntimeInfrastructurePackageCommandSchema, type RegisterRuntimeInfrastructurePackageCommandInput } from "@/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RuntimeInfrastructurePackageCatalogRegisterRuntimeInfrastructurePackage = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    packageName: searchParams.get("packageName") ?? undefined,
    packageVersion: searchParams.get("packageVersion") ?? undefined,
    runtimeEnvironmentType: searchParams.get("runtimeEnvironmentType") ?? undefined,
  } as Partial<RegisterRuntimeInfrastructurePackageCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterRuntimeInfrastructurePackageCommandInput, RegisterRuntimeInfrastructurePackageCommandInput>({
    resource: "runtime_infrastructure_package_catalog",
    command: "registerRuntimeInfrastructurePackage",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_infrastructure_package_catalog_read_model_entity",
      idField: "runtimeInfrastructurePackageId",
      label: t("resources.runtime_infrastructure_package_catalog.label", "Runtime Infrastructure Package Catalog"),
      aggregateRoute: "runtimeinfrastructurepackage",
      queryRoute: "runtimeinfrastructurepackagecatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_infrastructure_package_catalog_read_model_entity",
      idField: "runtimeInfrastructurePackageId",
      label: t("resources.runtime_infrastructure_package_catalog.label", "Runtime Infrastructure Package Catalog"),
      aggregateRoute: "runtimeinfrastructurepackage",
      queryRoute: "runtimeinfrastructurepackagecatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RegisterRuntimeInfrastructurePackageCommandSchema) as never,
    },
  });

  async function onSubmit(values: RegisterRuntimeInfrastructurePackageCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-infrastructure-package-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_infrastructure_package_catalog.commands.registerRuntimeInfrastructurePackage.label", "Register Runtime Infrastructure Package")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterRuntimeInfrastructurePackage validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="packageName"
            rules={{ required: "Package Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_infrastructure_package_catalog.commands.registerRuntimeInfrastructurePackage.fields.packageName.label", "Package Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Package Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="packageVersion"
            rules={{ required: "Package Version is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_infrastructure_package_catalog.commands.registerRuntimeInfrastructurePackage.fields.packageVersion.label", "Package Version")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Package Version"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="runtimeEnvironmentType"
            rules={{ required: "Runtime Environment Type is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_infrastructure_package_catalog.commands.registerRuntimeInfrastructurePackage.fields.runtimeEnvironmentType.label", "Runtime Environment Type")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.runtime_infrastructure_package_catalog.commands.registerRuntimeInfrastructurePackage.fields.runtimeEnvironmentType.placeholder", "Select Runtime Environment Type")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_ENVIRONMENT_TYPE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.runtime_infrastructure_package_catalog.commands.registerRuntimeInfrastructurePackage.fields.runtimeEnvironmentType.label", "Dictionary Value Catalog"),
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
