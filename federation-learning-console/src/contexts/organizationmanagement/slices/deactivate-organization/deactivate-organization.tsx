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
import { DeactivateOrganizationCommandSchema, type DeactivateOrganizationCommandInput } from "@/contexts/domain/schemas";

export const OrganizationDirectoryDeactivateOrganization = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationName: searchParams.get("organizationName") ?? undefined,
    organizationId: searchParams.get("organizationId") ?? undefined,
  } as unknown as Partial<DeactivateOrganizationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<DeactivateOrganizationCommandInput, DeactivateOrganizationCommandInput>({
    resource: "organization_directory",
    command: "deactivateOrganization",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "organization_directory_read_model_entity",
      idField: "organizationId",
      label: t("resources.organization_directory.label", "Organization Directory"),
      aggregateRoute: "organization",
      queryRoute: "organizationdirectory",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "organization_directory_read_model_entity",
      idField: "organizationId",
      label: t("resources.organization_directory.label", "Organization Directory"),
      aggregateRoute: "organization",
      queryRoute: "organizationdirectory",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(DeactivateOrganizationCommandSchema) as never,
    },
  });

  async function onSubmit(values: DeactivateOrganizationCommandInput) {
    const result = await runFormBehavior<DeactivateOrganizationCommandInput>(
      frontendComposition,
      "behavior:organization-directory:deactivateOrganization",
      {
      ...defaultValues,
      ...values,
      } as DeactivateOrganizationCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/organization-directory");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.organization_directory.commands.deactivateOrganization.label", "Deactivate Organization")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("DeactivateOrganization validation failed", errors))} className="space-y-8">
          {defaultValues.organizationName !== undefined && defaultValues.organizationName !== null ? (
            <input type="hidden" {...form.register("organizationName" as never)} />
          ) : null}
          {defaultValues.organizationId !== undefined && defaultValues.organizationId !== null ? (
            <input type="hidden" {...form.register("organizationId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="deactivationReason"
            rules={{ required: "Deactivation Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.organization_directory.commands.deactivateOrganization.fields.deactivationReason.label", "Deactivation Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Deactivation Reason"}
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
