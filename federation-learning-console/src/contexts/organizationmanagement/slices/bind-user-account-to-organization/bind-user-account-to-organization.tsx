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
import { BindUserAccountToOrganizationCommandSchema, type BindUserAccountToOrganizationCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const UserOrganizationMembershipDirectoryBindUserAccountToOrganization = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    userAccountId: searchParams.get("userAccountId") ?? undefined,
    organizationId: searchParams.get("organizationId") ?? undefined,
    organizationUserRole: searchParams.get("organizationUserRole") ?? undefined,
  } as unknown as Partial<BindUserAccountToOrganizationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<BindUserAccountToOrganizationCommandInput, BindUserAccountToOrganizationCommandInput>({
    resource: "user_organization_membership_directory",
    command: "bindUserAccountToOrganization",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "user_organization_membership_directory_read_model_entity",
      idField: "userOrganizationMembershipId",
      label: t("resources.user_organization_membership_directory.label", "User Organization Membership Directory"),
      aggregateRoute: "userorganizationmembership",
      queryRoute: "userorganizationmembershipdirectory",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "user_organization_membership_directory_read_model_entity",
      idField: "userOrganizationMembershipId",
      label: t("resources.user_organization_membership_directory.label", "User Organization Membership Directory"),
      aggregateRoute: "userorganizationmembership",
      queryRoute: "userorganizationmembershipdirectory",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(BindUserAccountToOrganizationCommandSchema) as never,
    },
  });

  async function onSubmit(values: BindUserAccountToOrganizationCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/user-organization-membership-directory");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.user_organization_membership_directory.commands.bindUserAccountToOrganization.label", "Bind User Account To Organization")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("BindUserAccountToOrganization validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="userAccountId"
            rules={{ required: "User Account Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.user_organization_membership_directory.commands.bindUserAccountToOrganization.fields.userAccountId.label", "User Account Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="user_account_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="username"
                  optionValue="userAccountId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.user_organization_membership_directory.commands.bindUserAccountToOrganization.fields.userAccountId.placeholder", "Select User Account Id")}
                  meta={{
                    idField: "userAccountId",
                    label: t("resources.user_organization_membership_directory.commands.bindUserAccountToOrganization.fields.userAccountId.label", "User Account Catalog"),
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
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.user_organization_membership_directory.commands.bindUserAccountToOrganization.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.user_organization_membership_directory.commands.bindUserAccountToOrganization.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.user_organization_membership_directory.commands.bindUserAccountToOrganization.fields.organizationId.label", "Organization Directory"),
                    aggregateRoute: "organization",
                    queryRoute: "organizationdirectory",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="organizationUserRole"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.user_organization_membership_directory.commands.bindUserAccountToOrganization.fields.organizationUserRole.label", "Organization User Role")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Organization User Role"}
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
