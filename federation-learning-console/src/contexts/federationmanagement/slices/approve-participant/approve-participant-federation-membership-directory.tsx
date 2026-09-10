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
import { ApproveParticipantCommandSchema, type ApproveParticipantCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const FederationMembershipDirectoryApproveParticipant = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    federationName: searchParams.get("federationName") ?? undefined,
    organizationId: searchParams.get("organizationId") ?? undefined,
    organizationName: searchParams.get("organizationName") ?? undefined,
    approvalNote: searchParams.get("approvalNote") ?? undefined,
    federationId: searchParams.get("federationId") ?? undefined,
  } as unknown as Partial<ApproveParticipantCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ApproveParticipantCommandInput, ApproveParticipantCommandInput>({
    resource: "federation_membership_directory",
    command: "approveParticipant",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "federation_membership_directory_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_membership_directory.label", "Federation Membership Directory"),
      aggregateRoute: "federationmembership",
      queryRoute: "federationmembershipdirectory",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "federation_membership_directory_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_membership_directory.label", "Federation Membership Directory"),
      aggregateRoute: "federationmembership",
      queryRoute: "federationmembershipdirectory",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ApproveParticipantCommandSchema) as never,
    },
  });

  async function onSubmit(values: ApproveParticipantCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/federation-membership-directory");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.federation_membership_directory.commands.approveParticipant.label", "Approve Participant")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ApproveParticipant validation failed", errors))} className="space-y-8">
          {defaultValues.federationId !== undefined && defaultValues.federationId !== null ? (
            <input type="hidden" {...form.register("federationId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="federationName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_membership_directory.commands.approveParticipant.fields.federationName.label", "Federation Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Federation Name"}
                  />
                </FormControl>
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
                <FormLabel>{t("resources.federation_membership_directory.commands.approveParticipant.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.federation_membership_directory.commands.approveParticipant.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.federation_membership_directory.commands.approveParticipant.fields.organizationId.label", "Organization Directory"),
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
            name="organizationName"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_membership_directory.commands.approveParticipant.fields.organizationName.label", "Organization Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Organization Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="approvalNote"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_membership_directory.commands.approveParticipant.fields.approvalNote.label", "Approval Note")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Approval Note"}
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
