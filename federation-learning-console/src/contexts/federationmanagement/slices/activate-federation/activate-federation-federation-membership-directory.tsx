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
import { ActivateFederationCommandSchema, type ActivateFederationCommandInput } from "@/contexts/domain/schemas";

export const FederationMembershipDirectoryActivateFederation = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    federationName: searchParams.get("federationName") ?? undefined,
    federationId: searchParams.get("federationId") ?? undefined,
  } as unknown as Partial<ActivateFederationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ActivateFederationCommandInput, ActivateFederationCommandInput>({
    resource: "federation_membership_directory",
    command: "activateFederation",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "federation_membership_directory_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_membership_directory.label", "Federation Membership Directory"),
      aggregateRoute: "federation",
      queryRoute: "federationmembershipdirectory",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "federation_membership_directory_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_membership_directory.label", "Federation Membership Directory"),
      aggregateRoute: "federationmembership",
      queryRoute: "federationmembershipdirectory",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ActivateFederationCommandSchema) as never,
    },
  });

  async function onSubmit(values: ActivateFederationCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/federation-membership-directory");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.federation_membership_directory.commands.activateFederation.label", "Activate Federation")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ActivateFederation validation failed", errors))} className="space-y-8">
          {defaultValues.federationName !== undefined && defaultValues.federationName !== null ? (
            <input type="hidden" {...form.register("federationName" as never)} />
          ) : null}
          {defaultValues.federationId !== undefined && defaultValues.federationId !== null ? (
            <input type="hidden" {...form.register("federationId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="activationNote"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_membership_directory.commands.activateFederation.fields.activationNote.label", "Activation Note")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Activation Note"}
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
