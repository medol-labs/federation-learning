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
import { RegisterPermissionCommandSchema, type RegisterPermissionCommandInput } from "@/contexts/domain/schemas";

export const PermissionCatalogRegisterPermission = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    permissionCode: searchParams.get("permissionCode") ?? undefined,
    permissionName: searchParams.get("permissionName") ?? undefined,
    description: searchParams.get("description") ?? undefined,
  } as unknown as Partial<RegisterPermissionCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterPermissionCommandInput, RegisterPermissionCommandInput>({
    resource: "permission_catalog",
    command: "registerPermission",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "permission_catalog_read_model_entity",
      idField: "permissionId",
      label: t("resources.permission_catalog.label", "Permission Catalog"),
      aggregateRoute: "permission",
      queryRoute: "permissioncatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "permission_catalog_read_model_entity",
      idField: "permissionId",
      label: t("resources.permission_catalog.label", "Permission Catalog"),
      aggregateRoute: "permission",
      queryRoute: "permissioncatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RegisterPermissionCommandSchema) as never,
    },
  });

  async function onSubmit(values: RegisterPermissionCommandInput) {
    const result = await runFormBehavior<RegisterPermissionCommandInput>(
      frontendComposition,
      "behavior:permission-catalog:registerPermission",
      {
      ...defaultValues,
      ...values,
      } as RegisterPermissionCommandInput,
      (payload) => onFinish(payload),
    );
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
