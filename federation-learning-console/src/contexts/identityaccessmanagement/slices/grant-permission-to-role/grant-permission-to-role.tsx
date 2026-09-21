// Generated from config.json by the refine generator.
import { useParsed, useList } from "@refinedev/core";
import { useTranslate } from "@refinedev/core";
import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router";
import type { Control } from "react-hook-form";
import { useFieldArray } from "react-hook-form";
import { Plus, Trash2 } from "lucide-react";

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
import { GrantPermissionToRoleCommandSchema, type GrantPermissionToRoleCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";
type ScalarArrayFieldProps = {
  control: Control<any>;
  name: string;
  label: string;
  inputType?: string | null;
  itemDefaultValue: string | number | boolean;
  options?: Array<{ value: string; label: string }>;
};

function ScalarArrayField({
  control,
  name,
  label,
  inputType,
  itemDefaultValue,
  options,
}: ScalarArrayFieldProps) {
  const optionItems = options ?? [];

  return (
    <FormField
      control={control}
      name={name as never}
      render={({ field }) => {
        const values = (Array.isArray(field.value) ? field.value : []) as Array<string | number | boolean>;

        return (
          <FormItem>
            <div className="flex items-center justify-between gap-2">
              <FormLabel>{label}</FormLabel>
              <Button
                type="button"
                variant="outline"
                size="sm"
                onClick={() => field.onChange([...values, itemDefaultValue])}
              >
                <Plus className="size-4" />
              </Button>
            </div>
            <div className="space-y-2">
              {values.map((value, index) => (
                <div key={index} className="flex items-center gap-2">
                  {optionItems.length > 0 ? (
                    <Select
                      value={value === undefined || value === null ? undefined : String(value)}
                      onValueChange={(nextValue) => {
                        const next = [...values];
                        next[index] = nextValue;
                        field.onChange(next);
                      }}
                    >
                      <SelectTrigger>
                        <SelectValue placeholder={label} />
                      </SelectTrigger>
                      <SelectContent>
                        {optionItems.map((option) => (
                          <SelectItem key={option.value} value={option.value}>
                            {option.label}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  ) : (
                  <Input
                    type={inputType ?? undefined}
                    value={typeof value === "boolean" ? String(value) : value ?? ""}
                    onChange={(event) => {
                      const next = [...values];
                      next[index] = inputType === "number"
                        ? Number(event.target.value)
                        : event.target.value;
                      field.onChange(next);
                    }}
                  />
                  )}
                  <Button
                    type="button"
                    variant="ghost"
                    size="icon"
                    onClick={() => field.onChange(values.filter((_, itemIndex) => itemIndex !== index))}
                  >
                    <Trash2 className="size-4" />
                  </Button>
                </div>
              ))}
            </div>
            <FormMessage />
          </FormItem>
        );
      }}
    />
  );
}

export const RoleCatalogGrantPermissionToRole = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    roleId: searchParams.get("roleId") ?? undefined,
    roleCode: searchParams.get("roleCode") ?? undefined,
    permissionCodes: [],
  } as unknown as Partial<GrantPermissionToRoleCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<GrantPermissionToRoleCommandInput, GrantPermissionToRoleCommandInput>({
    resource: "role_catalog",
    command: "grantPermissionToRole",
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
      resolver: zodResolver(GrantPermissionToRoleCommandSchema) as never,
    },
  });
  const permissionCodesHistory = useList<Record<string, unknown>>({
    resource: "role_permission_grant_catalog",
    dataProviderName: "federation-learning-support",
    pagination: { currentPage: 1, pageSize: 1000, mode: "server" },
    filters: defaultValues.roleId
      ? [{ field: "roleId", operator: "eq", value: defaultValues.roleId }]
      : [],
    meta: {
      tableName: "role_permission_grant_catalog_read_model_entity",
      idField: "roleCode",
      label: "Role Permission Grant Catalog",
      aggregateRoute: "rolepermissiongrant",
      queryRoute: "rolepermissiongrantcatalog",
      queryFields: ["roleId","permissionCode"],
    },
    queryOptions: {
      enabled: Boolean(defaultValues.roleId),
    },
  });

  useEffect(() => {
    const values = (permissionCodesHistory.result.data ?? [])
      .map((item: Record<string, unknown>) => item.permissionCode)
      .filter((value: unknown): value is string => typeof value === "string" && value.trim().length > 0);
    const current = form.getValues("permissionCodes" as never) as unknown;
    if (values.length > 0 && (!Array.isArray(current) || current.length === 0)) {
      form.setValue("permissionCodes" as never, Array.from(new Set(values)) as never, { shouldDirty: false });
    }
  }, [permissionCodesHistory.result.data, form]);

  async function onSubmit(values: GrantPermissionToRoleCommandInput) {
    const result = await runFormBehavior<GrantPermissionToRoleCommandInput>(
      frontendComposition,
      "behavior:role-catalog:grantPermissionToRole",
      {
      ...defaultValues,
      ...values,
      } as GrantPermissionToRoleCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/role-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.role_catalog.commands.grantPermissionToRole.label", "Grant Permission To Role")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("GrantPermissionToRole validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="roleId"
            rules={{ required: "Role Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.role_catalog.commands.grantPermissionToRole.fields.roleId.label", "Role Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Role Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="roleCode"
            rules={{ required: "Role Code is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.role_catalog.commands.grantPermissionToRole.fields.roleCode.label", "Role Code")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="role_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="roleName"
                  optionValue="roleCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.role_catalog.commands.grantPermissionToRole.fields.roleCode.placeholder", "Select Role Code")}
                  meta={{
                    idField: "roleId",
                    label: t("resources.role_catalog.commands.grantPermissionToRole.fields.roleCode.label", "Role Catalog"),
                    aggregateRoute: "role",
                    queryRoute: "rolecatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="permissionCodes"
            rules={{ required: "Permission Codes is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.role_catalog.commands.grantPermissionToRole.fields.permissionCodes.label", "Permission Codes")}</FormLabel>
                <ResourceMultiSelect
                  withFormControl
                  resource="permission_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="permissionName"
                  optionValue="permissionCode"
                  value={Array.isArray(field.value) ? field.value : []}
                  onValueChange={field.onChange}
                  placeholder={t("resources.role_catalog.commands.grantPermissionToRole.fields.permissionCodes.placeholder", "Select Permission Codes")}
                  searchPlaceholder={t("resources.role_catalog.commands.grantPermissionToRole.fields.permissionCodes.placeholder.search", "Search Permission Codes")}
                  meta={{
                    idField: "permissionId",
                    label: t("resources.role_catalog.commands.grantPermissionToRole.fields.permissionCodes.label", "Permission Catalog"),
                    aggregateRoute: "permission",
                    queryRoute: "permissioncatalog",
                  }}
                />
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
