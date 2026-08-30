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
import { RegisterRoleCommandSchema, type RegisterRoleCommandInput } from "@/domain/schemas";

export const RoleCatalogRegisterRole = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    roleCode: searchParams.get("roleCode") ?? undefined,
    roleName: searchParams.get("roleName") ?? undefined,
  } as unknown as Partial<RegisterRoleCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterRoleCommandInput, RegisterRoleCommandInput>({
    resource: "role_catalog",
    command: "registerRole",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "role_catalog_read_model_entity",
      idField: "roleId",
      label: t("resources.role_catalog.label", "Role Catalog"),
      aggregateRoute: "role",
      queryRoute: "rolecatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "role_catalog_read_model_entity",
      idField: "roleId",
      label: t("resources.role_catalog.label", "Role Catalog"),
      aggregateRoute: "role",
      queryRoute: "rolecatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RegisterRoleCommandSchema) as never,
    },
  });

  async function onSubmit(values: RegisterRoleCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/role-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.role_catalog.commands.registerRole.label", "Register Role")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterRole validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="roleCode"
            rules={{ required: "Role Code is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.role_catalog.commands.registerRole.fields.roleCode.label", "Role Code")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Role Code"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="roleName"
            rules={{ required: "Role Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.role_catalog.commands.registerRole.fields.roleName.label", "Role Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Role Name"}
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
