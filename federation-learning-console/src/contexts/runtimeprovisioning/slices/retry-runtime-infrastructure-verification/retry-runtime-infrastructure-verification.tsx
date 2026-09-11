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
import { RetryRuntimeInfrastructureVerificationCommandSchema, type RetryRuntimeInfrastructureVerificationCommandInput } from "@/contexts/domain/schemas";

export const RuntimeInstallationPlanCatalogRetryRuntimeInfrastructureVerification = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeInstallationPlanId: searchParams.get("runtimeInstallationPlanId") ?? undefined,
    runtimeAgentId: searchParams.get("runtimeAgentId") ?? undefined,
    runtimeInfrastructureId: searchParams.get("runtimeInfrastructureId") ?? undefined,
    organizationId: searchParams.get("organizationId") ?? undefined,
    organizationName: searchParams.get("organizationName") ?? undefined,
    runtimeInfrastructurePackageId: searchParams.get("runtimeInfrastructurePackageId") ?? undefined,
    runtimeInfrastructurePackageName: searchParams.get("runtimeInfrastructurePackageName") ?? undefined,
    runtimeInfrastructurePackageVersion: searchParams.get("runtimeInfrastructurePackageVersion") ?? undefined,
    runtimeName: searchParams.get("runtimeName") ?? undefined,
    agentInstallMode: searchParams.get("agentInstallMode") ?? undefined,
    expectedNodeCount: (() => { const value = searchParams.get("expectedNodeCount"); return value === null ? undefined : Number(value); })(),
  } as unknown as Partial<RetryRuntimeInfrastructureVerificationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RetryRuntimeInfrastructureVerificationCommandInput, RetryRuntimeInfrastructureVerificationCommandInput>({
    resource: "runtime_installation_plan_catalog",
    command: "retryRuntimeInfrastructureVerification",
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
      resolver: zodResolver(RetryRuntimeInfrastructureVerificationCommandSchema) as never,
    },
  });

  async function onSubmit(values: RetryRuntimeInfrastructureVerificationCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-installation-plan-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_installation_plan_catalog.commands.retryRuntimeInfrastructureVerification.label", "Retry Runtime Infrastructure Verification")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RetryRuntimeInfrastructureVerification validation failed", errors))} className="space-y-8">
          {defaultValues.runtimeInstallationPlanId !== undefined && defaultValues.runtimeInstallationPlanId !== null ? (
            <input type="hidden" {...form.register("runtimeInstallationPlanId" as never)} />
          ) : null}
          {defaultValues.runtimeAgentId !== undefined && defaultValues.runtimeAgentId !== null ? (
            <input type="hidden" {...form.register("runtimeAgentId" as never)} />
          ) : null}
          {defaultValues.runtimeInfrastructureId !== undefined && defaultValues.runtimeInfrastructureId !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructureId" as never)} />
          ) : null}
          {defaultValues.organizationId !== undefined && defaultValues.organizationId !== null ? (
            <input type="hidden" {...form.register("organizationId" as never)} />
          ) : null}
          {defaultValues.organizationName !== undefined && defaultValues.organizationName !== null ? (
            <input type="hidden" {...form.register("organizationName" as never)} />
          ) : null}
          {defaultValues.runtimeInfrastructurePackageId !== undefined && defaultValues.runtimeInfrastructurePackageId !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructurePackageId" as never)} />
          ) : null}
          {defaultValues.runtimeInfrastructurePackageName !== undefined && defaultValues.runtimeInfrastructurePackageName !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructurePackageName" as never)} />
          ) : null}
          {defaultValues.runtimeInfrastructurePackageVersion !== undefined && defaultValues.runtimeInfrastructurePackageVersion !== null ? (
            <input type="hidden" {...form.register("runtimeInfrastructurePackageVersion" as never)} />
          ) : null}
          {defaultValues.runtimeName !== undefined && defaultValues.runtimeName !== null ? (
            <input type="hidden" {...form.register("runtimeName" as never)} />
          ) : null}
          {defaultValues.agentInstallMode !== undefined && defaultValues.agentInstallMode !== null ? (
            <input type="hidden" {...form.register("agentInstallMode" as never)} />
          ) : null}
          {defaultValues.expectedNodeCount !== undefined && defaultValues.expectedNodeCount !== null ? (
            <input type="hidden" {...form.register("expectedNodeCount" as never)} />
          ) : null}
          <input type="hidden" {...form.register("runtimeEnvironmentType" as never)} />
          <input type="hidden" {...form.register("currentRuntimeInfrastructureState" as never)} />
          <FormField
            control={form.control}
            name="retryReason"
            rules={{ required: "Retry Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_installation_plan_catalog.commands.retryRuntimeInfrastructureVerification.fields.retryReason.label", "Retry Reason")}</FormLabel>
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
