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
import { RegisterUserAccountCommandSchema, type RegisterUserAccountCommandInput } from "@/contexts/domain/schemas";

export const UserAccountCatalogRegisterUserAccount = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    username: searchParams.get("username") ?? undefined,
    providerSubject: searchParams.get("providerSubject") ?? undefined,
    userSource: searchParams.get("userSource") ?? undefined,
    passwordHash: searchParams.get("passwordHash") ?? undefined,
  } as unknown as Partial<RegisterUserAccountCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterUserAccountCommandInput, RegisterUserAccountCommandInput>({
    resource: "user_account_catalog",
    command: "registerUserAccount",
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
      resolver: zodResolver(RegisterUserAccountCommandSchema) as never,
    },
  });

  async function onSubmit(values: RegisterUserAccountCommandInput) {
    const result = await runFormBehavior<RegisterUserAccountCommandInput>(
      frontendComposition,
      "behavior:user-account-catalog:registerUserAccount",
      {
      ...defaultValues,
      ...values,
      } as RegisterUserAccountCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/user-account-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.user_account_catalog.commands.registerUserAccount.label", "Register User Account")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterUserAccount validation failed", errors))} className="space-y-8">
          {defaultValues.providerSubject !== undefined && defaultValues.providerSubject !== null ? (
            <input type="hidden" {...form.register("providerSubject" as never)} />
          ) : null}
          {defaultValues.userSource !== undefined && defaultValues.userSource !== null ? (
            <input type="hidden" {...form.register("userSource" as never)} />
          ) : null}
          {defaultValues.passwordHash !== undefined && defaultValues.passwordHash !== null ? (
            <input type="hidden" {...form.register("passwordHash" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="username"
            rules={{ required: "Username is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.user_account_catalog.commands.registerUserAccount.fields.username.label", "Username")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Username"}
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
