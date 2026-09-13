// Generated from config.json by the refine generator.
import { useParsed } from "@refinedev/core";
import { useTranslate } from "@refinedev/core";
import { useState } from "react";
import { useNavigate, useSearchParams } from "react-router";

import {
  CreateView,
  CreateViewHeader,
} from "@/components/refine-ui/views/create-view";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
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
import { GenerateUserAccountLoginPasswordCommandSchema, type GenerateUserAccountLoginPasswordCommandInput } from "@/contexts/domain/schemas";

export const UserAccountCatalogGenerateUserAccountLoginPassword = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const [commandResult, setCommandResult] = useState<Record<string, unknown> | null>(null);
  const defaultValues = {
    userAccountId: searchParams.get("userAccountId") ?? undefined,
  } as unknown as Partial<GenerateUserAccountLoginPasswordCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<GenerateUserAccountLoginPasswordCommandInput, GenerateUserAccountLoginPasswordCommandInput>({
    resource: "user_account_catalog",
    command: "generateUserAccountLoginPassword",
    aggregateId: id?.toString(),
    redirect: false,
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "user_account_catalog_read_model_entity",
      idField: "userAccountId",
      label: t("resources.user_account_catalog.label", "User Account Catalog"),
      aggregateRoute: "useraccount",
      queryRoute: "useraccountcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "user_account_catalog_read_model_entity",
      idField: "userAccountId",
      label: t("resources.user_account_catalog.label", "User Account Catalog"),
      aggregateRoute: "useraccount",
      queryRoute: "useraccountcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(GenerateUserAccountLoginPasswordCommandSchema) as never,
    },
  });

  async function onSubmit(values: GenerateUserAccountLoginPasswordCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    }) as { data?: Record<string, unknown> } | Record<string, unknown> | void;
    const data = result && typeof result === "object" && "data" in result
      ? result.data
      : result;
    if (data && typeof data === "object") {
      setCommandResult(data as Record<string, unknown>);
    }
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.user_account_catalog.commands.generateUserAccountLoginPassword.label", "Generate User Account Login Password")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("GenerateUserAccountLoginPassword validation failed", errors))} className="space-y-8">
          {defaultValues.userAccountId !== undefined && defaultValues.userAccountId !== null ? (
            <input type="hidden" {...form.register("userAccountId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="passwordResetRequired"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.user_account_catalog.commands.generateUserAccountLoginPassword.fields.passwordResetRequired.label", "Password Reset Required")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.user_account_catalog.commands.generateUserAccountLoginPassword.fields.passwordResetRequired.placeholder", "Select Password Reset Required")} />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="true">{t("values.boolean.true", "True")}</SelectItem>
                    <SelectItem value="false">{t("values.boolean.false", "False")}</SelectItem>
                  </SelectContent>
                </Select>
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
      <Dialog open={commandResult !== null} onOpenChange={(open) => {
        if (!open) {
          setCommandResult(null);
          navigate(-1);
        }
      }}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>{t("resources.user_account_catalog.commands.generateUserAccountLoginPassword.label.result.title", "Generate User Account Login Password Result")}</DialogTitle>
            <DialogDescription>
              {t("resources.user_account_catalog.commands.generateUserAccountLoginPassword.label.result.description", "Copy the returned values now. Sensitive values may not be shown again.")}
            </DialogDescription>
          </DialogHeader>
          <div className="space-y-3">
            <div className="space-y-1">
              <div className="text-sm font-medium">{t("resources.user_account_catalog.commands.generateUserAccountLoginPassword.result.fields.temporaryPassword.label", "Temporary Password")}</div>
              <div className="rounded-md border bg-muted/40 px-3 py-2 font-mono text-sm break-all">
                {commandResult?.temporaryPassword == null ? "-" : String(commandResult.temporaryPassword)}
              </div>
            </div>
          </div>
          <DialogFooter>
            <Button type="button" onClick={() => {
              setCommandResult(null);
              navigate(-1);
            }}>
              {t("buttons.done", "Done")}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </CreateView>
  );
};
