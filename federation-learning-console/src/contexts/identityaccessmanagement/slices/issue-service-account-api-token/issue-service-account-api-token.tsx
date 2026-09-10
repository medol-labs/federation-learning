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
import { IssueServiceAccountApiTokenCommandSchema, type IssueServiceAccountApiTokenCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const ServiceAccountApiTokenCatalogIssueServiceAccountApiToken = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const [commandResult, setCommandResult] = useState<Record<string, unknown> | null>(null);
  const defaultValues = {
    userAccountId: searchParams.get("userAccountId") ?? undefined,
    tokenName: searchParams.get("tokenName") ?? undefined,
  } as unknown as Partial<IssueServiceAccountApiTokenCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<IssueServiceAccountApiTokenCommandInput, IssueServiceAccountApiTokenCommandInput>({
    resource: "service_account_api_token_catalog",
    command: "issueServiceAccountApiToken",
    aggregateId: id?.toString(),
    redirect: false,
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "service_account_api_token_catalog_read_model_entity",
      idField: "apiTokenId",
      label: t("resources.service_account_api_token_catalog.label", "Service Account Api Token Catalog"),
      aggregateRoute: "serviceaccountapitoken",
      queryRoute: "serviceaccountapitokencatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "service_account_api_token_catalog_read_model_entity",
      idField: "apiTokenId",
      label: t("resources.service_account_api_token_catalog.label", "Service Account Api Token Catalog"),
      aggregateRoute: "serviceaccountapitoken",
      queryRoute: "serviceaccountapitokencatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(IssueServiceAccountApiTokenCommandSchema) as never,
    },
  });

  async function onSubmit(values: IssueServiceAccountApiTokenCommandInput) {
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
      <CreateViewHeader title={t("resources.service_account_api_token_catalog.commands.issueServiceAccountApiToken.label", "Issue Service Account Api Token")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("IssueServiceAccountApiToken validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="userAccountId"
            rules={{ required: "User Account Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.service_account_api_token_catalog.commands.issueServiceAccountApiToken.fields.userAccountId.label", "User Account Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="user_account_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="username"
                  optionValue="userAccountId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.service_account_api_token_catalog.commands.issueServiceAccountApiToken.fields.userAccountId.placeholder", "Select User Account Id")}
                  meta={{
                    idField: "userAccountId",
                    label: t("resources.service_account_api_token_catalog.commands.issueServiceAccountApiToken.fields.userAccountId.label", "User Account Catalog"),
                    aggregateRoute: "useraccount",
                    queryRoute: "useraccountcatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="tokenName"
            rules={{ required: "Token Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.service_account_api_token_catalog.commands.issueServiceAccountApiToken.fields.tokenName.label", "Token Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Token Name"}
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
      <Dialog open={commandResult !== null} onOpenChange={(open) => {
        if (!open) {
          setCommandResult(null);
          navigate(-1);
        }
      }}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>{t("resources.service_account_api_token_catalog.commands.issueServiceAccountApiToken.label.result.title", "Issue Service Account Api Token Result")}</DialogTitle>
            <DialogDescription>
              {t("resources.service_account_api_token_catalog.commands.issueServiceAccountApiToken.label.result.description", "Copy the returned values now. Sensitive values may not be shown again.")}
            </DialogDescription>
          </DialogHeader>
          <div className="space-y-3">
            <div className="space-y-1">
              <div className="text-sm font-medium">{t("resources.service_account_api_token_catalog.commands.issueServiceAccountApiToken.result.fields.apiToken.label", "Api Token")}</div>
              <div className="rounded-md border bg-muted/40 px-3 py-2 font-mono text-sm break-all">
                {commandResult?.apiToken == null ? "-" : String(commandResult.apiToken)}
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
