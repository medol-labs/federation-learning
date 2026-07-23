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


export const RuntimeInstallationGuideRegisterRuntimeInfrastructure = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeInfrastructureId: searchParams.get("runtimeInfrastructureId") ?? undefined,
  } as Partial<RegisterRuntimeInfrastructureCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterRuntimeInfrastructureCommandInput, RegisterRuntimeInfrastructureCommandInput>({
    resource: "runtime_installation_guide",
    command: "registerRuntimeInfrastructure",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "flplatform-backend",
    queryDataProviderName: "flplatform-backend",
    meta: {
      tableName: "runtime_installation_guide_read_model_entity",
      idField: "runtimeInstallationPlanId",
      label: t("resources.runtime_installation_guide.label", "Runtime Installation Guide"),
      aggregateRoute: "runtimeinfrastructure",
      queryRoute: "runtimeinstallationguide",
      dataProviderName: "flplatform-backend",
    },
    queryMeta: {
      tableName: "runtime_installation_guide_read_model_entity",
      idField: "runtimeInstallationPlanId",
      label: t("resources.runtime_installation_guide.label", "Runtime Installation Guide"),
      aggregateRoute: "runtimeinstallationplan",
      queryRoute: "runtimeinstallationguide",
      dataProviderName: "flplatform-backend",
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
      <CreateViewHeader title={t("resources.runtime_installation_guide.commands.registerRuntimeInfrastructure.label", "Register Runtime Infrastructure")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <input type="hidden" {...form.register("runtimeInfrastructureId" as never)} />
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
