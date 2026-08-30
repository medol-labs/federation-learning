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
import { RegisterPermissionCommandSchema, type RegisterPermissionCommandInput } from "@/domain/schemas";

export const PermissionCatalogRegisterPermission = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    permissionCode: searchParams.get("permissionCode") ?? undefined,
    permissionName: searchParams.get("permissionName") ?? undefined,
    description: searchParams.get("description") ?? undefined,
  } as Partial<RegisterPermissionCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterPermissionCommandInput, RegisterPermissionCommandInput>({
    resource: "permission_catalog",
    command: "registerPermission",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "permission_catalog_read_model_entity",
      idField: "permissionCode",
      label: t("resources.permission_catalog.label", "Permission Catalog"),
      aggregateRoute: "permission",
      queryRoute: "permissioncatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "permission_catalog_read_model_entity",
      idField: "permissionCode",
      label: t("resources.permission_catalog.label", "Permission Catalog"),
      aggregateRoute: "useraccount",
      queryRoute: "permissioncatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RegisterPermissionCommandSchema) as never,
    },
  });

  async function onSubmit(values: RegisterPermissionCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/permission-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.permission_catalog.commands.registerPermission.label", "Register Permission")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterPermission validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="permissionCode"
            rules={{ required: "Permission Code is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.permission_catalog.commands.registerPermission.fields.permissionCode.label", "Permission Code")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Permission Code"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="permissionName"
            rules={{ required: "Permission Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.permission_catalog.commands.registerPermission.fields.permissionName.label", "Permission Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Permission Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="description"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.permission_catalog.commands.registerPermission.fields.description.label", "Description")}</FormLabel>
                <FormControl>
                  <Textarea
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Description"}
                    rows={8}
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
