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
import { RevokeRuntimeIdentityCommandSchema, type RevokeRuntimeIdentityCommandInput } from "@/domain/schemas";

export const RuntimeIdentityCatalogRevokeRuntimeIdentity = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeId: searchParams.get("runtimeId") ?? undefined,
  } as Partial<RevokeRuntimeIdentityCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RevokeRuntimeIdentityCommandInput, RevokeRuntimeIdentityCommandInput>({
    resource: "runtime_identity_catalog",
    command: "revokeRuntimeIdentity",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_identity_catalog_read_model_entity",
      idField: "runtimeId",
      label: t("resources.runtime_identity_catalog.label", "Runtime Identity Catalog"),
      aggregateRoute: "runtimeidentity",
      queryRoute: "runtimeidentitycatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_identity_catalog_read_model_entity",
      idField: "runtimeId",
      label: t("resources.runtime_identity_catalog.label", "Runtime Identity Catalog"),
      aggregateRoute: "runtimeidentity",
      queryRoute: "runtimeidentitycatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RevokeRuntimeIdentityCommandSchema) as never,
    },
  });

  async function onSubmit(values: RevokeRuntimeIdentityCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-identity-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_identity_catalog.commands.revokeRuntimeIdentity.label", "Revoke Runtime Identity")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RevokeRuntimeIdentity validation failed", errors))} className="space-y-8">
          {defaultValues.runtimeId !== undefined && defaultValues.runtimeId !== null ? (
            <input type="hidden" {...form.register("runtimeId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="revocationReason"
            rules={{ required: "Revocation Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_identity_catalog.commands.revokeRuntimeIdentity.fields.revocationReason.label", "Revocation Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Revocation Reason"}
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
