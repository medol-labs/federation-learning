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
import { RegisterRuntimeInfrastructureCommandSchema, type RegisterRuntimeInfrastructureCommandInput } from "@/domain/schemas";


export const RuntimeInstallationPlanCatalogRegisterRuntimeInfrastructure = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeInfrastructureId: searchParams.get("runtimeInfrastructureId") ?? undefined,
  } as Partial<RegisterRuntimeInfrastructureCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterRuntimeInfrastructureCommandInput, RegisterRuntimeInfrastructureCommandInput>({
    resource: "runtime_installation_plan_catalog",
    command: "registerRuntimeInfrastructure",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_installation_plan_catalog_read_model_entity",
      idField: "runtimeInstallationPlanId",
      label: t("resources.runtime_installation_plan_catalog.label", "Runtime Installation Plan Catalog"),
      aggregateRoute: "runtimeinfrastructure",
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
      resolver: zodResolver(RegisterRuntimeInfrastructureCommandSchema) as never,
    },
  });

  function onSubmit(values: RegisterRuntimeInfrastructureCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_installation_plan_catalog.commands.registerRuntimeInfrastructure.label", "Register Runtime Infrastructure")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterRuntimeInfrastructure validation failed", errors))} className="space-y-8">
          {defaultValues.runtimeInfrastructureId !== undefined && defaultValues.runtimeInfrastructureId !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructureId" as never)} />
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
