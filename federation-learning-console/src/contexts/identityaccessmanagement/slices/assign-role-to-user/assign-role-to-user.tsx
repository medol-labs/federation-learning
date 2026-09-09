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
import { AssignRoleToUserCommandSchema, type AssignRoleToUserCommandInput } from "@/contexts/domain/schemas";
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

export const UserAccountCatalogAssignRoleToUser = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    userAccountId: searchParams.get("userAccountId") ?? undefined,
    roleCodes: [],
  } as unknown as Partial<AssignRoleToUserCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<AssignRoleToUserCommandInput, AssignRoleToUserCommandInput>({
    resource: "user_account_catalog",
    command: "assignRoleToUser",
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
      resolver: zodResolver(AssignRoleToUserCommandSchema) as never,
    },
  });
  const roleCodesHistory = useList<Record<string, unknown>>({
    resource: "user_role_assignment_catalog",
    dataProviderName: "federation-learning-support",
    pagination: { currentPage: 1, pageSize: 1000, mode: "server" },
    filters: defaultValues.userAccountId
      ? [{ field: "userAccountId", operator: "eq", value: defaultValues.userAccountId }]
      : [],
    meta: {
      tableName: "user_role_assignment_catalog_read_model_entity",
      idField: "userAccountId",
      label: "User Role Assignment Catalog",
      aggregateRoute: "userroleassignment",
      queryRoute: "userroleassignmentcatalog",
      queryFields: ["userAccountId","roleCode"],
    },
    queryOptions: {
      enabled: Boolean(defaultValues.userAccountId),
    },
  });

  useEffect(() => {
    const values = (roleCodesHistory.result.data ?? [])
      .map((item: Record<string, unknown>) => item.roleCode)
      .filter((value: unknown): value is string => typeof value === "string" && value.trim().length > 0);
    const current = form.getValues("roleCodes" as never) as unknown;
    if (values.length > 0 && (!Array.isArray(current) || current.length === 0)) {
      form.setValue("roleCodes" as never, Array.from(new Set(values)) as never, { shouldDirty: false });
    }
  }, [roleCodesHistory.result.data, form]);

  async function onSubmit(values: AssignRoleToUserCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/user-account-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.user_account_catalog.commands.assignRoleToUser.label", "Assign Role To User")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("AssignRoleToUser validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="userAccountId"
            rules={{ required: "User Account Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.user_account_catalog.commands.assignRoleToUser.fields.userAccountId.label", "User Account Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter User Account Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="roleCodes"
            rules={{ required: "Role Codes is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.user_account_catalog.commands.assignRoleToUser.fields.roleCodes.label", "Role Codes")}</FormLabel>
                <ResourceMultiSelect
                  withFormControl
                  resource="role_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="roleName"
                  optionValue="roleCode"
                  value={Array.isArray(field.value) ? field.value : []}
                  onValueChange={field.onChange}
                  placeholder={t("resources.user_account_catalog.commands.assignRoleToUser.fields.roleCodes.placeholder", "Select Role Codes")}
                  searchPlaceholder={t("resources.user_account_catalog.commands.assignRoleToUser.fields.roleCodes.placeholder.search", "Search Role Codes")}
                  meta={{
                    idField: "roleId",
                    label: t("resources.user_account_catalog.commands.assignRoleToUser.fields.roleCodes.label", "Role Catalog"),
                    aggregateRoute: "role",
                    queryRoute: "rolecatalog",
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
