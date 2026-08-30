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
import { DeactivateUserAccountCommandSchema, type DeactivateUserAccountCommandInput } from "@/domain/schemas";

export const UserAccountCatalogDeactivateUserAccount = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    userAccountId: searchParams.get("userAccountId") ?? undefined,
  } as Partial<DeactivateUserAccountCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<DeactivateUserAccountCommandInput, DeactivateUserAccountCommandInput>({
    resource: "user_account_catalog",
    command: "deactivateUserAccount",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "user_account_catalog_read_model_entity",
      idField: "userAccountId",
      label: t("resources.user_account_catalog.label", "User Account Catalog"),
      aggregateRoute: "useraccount",
      queryRoute: "useraccountcatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "user_account_catalog_read_model_entity",
      idField: "userAccountId",
      label: t("resources.user_account_catalog.label", "User Account Catalog"),
      aggregateRoute: "useraccount",
      queryRoute: "useraccountcatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(DeactivateUserAccountCommandSchema) as never,
    },
  });

  async function onSubmit(values: DeactivateUserAccountCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/user-account-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.user_account_catalog.commands.deactivateUserAccount.label", "Deactivate User Account")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("DeactivateUserAccount validation failed", errors))} className="space-y-8">
          {defaultValues.userAccountId !== undefined && defaultValues.userAccountId !== null ? (
            <input type="hidden" {...form.register("userAccountId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="reason"
            rules={{ required: "Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.user_account_catalog.commands.deactivateUserAccount.fields.reason.label", "Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Reason"}
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
