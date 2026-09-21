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
import { InviteParticipantCommandSchema, type InviteParticipantCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const FederationMembershipDirectoryInviteParticipant = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationId: searchParams.get("organizationId") ?? undefined,
    invitationNote: searchParams.get("invitationNote") ?? undefined,
    federationId: searchParams.get("federationId") ?? undefined,
    federationName: searchParams.get("federationName") ?? undefined,
    organizationName: searchParams.get("organizationName") ?? undefined,
  } as unknown as Partial<InviteParticipantCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<InviteParticipantCommandInput, InviteParticipantCommandInput>({
    resource: "federation_membership_directory",
    command: "inviteParticipant",
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
      resolver: zodResolver(InviteParticipantCommandSchema) as never,
    },
  });

  async function onSubmit(values: InviteParticipantCommandInput) {
    const result = await runFormBehavior<InviteParticipantCommandInput>(
      frontendComposition,
      "behavior:federation-membership-directory:inviteParticipant",
      {
      ...defaultValues,
      ...values,
      } as InviteParticipantCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/federation-membership-directory");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.federation_membership_directory.commands.inviteParticipant.label", "Invite Participant")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("InviteParticipant validation failed", errors))} className="space-y-8">
          {defaultValues.federationId !== undefined && defaultValues.federationId !== null ? (
            <input type="hidden" {...form.register("federationId" as never)} />
          ) : null}
          {defaultValues.federationName !== undefined && defaultValues.federationName !== null ? (
            <input type="hidden" {...form.register("federationName" as never)} />
          ) : null}
          {defaultValues.organizationName !== undefined && defaultValues.organizationName !== null ? (
            <input type="hidden" {...form.register("organizationName" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_membership_directory.commands.inviteParticipant.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="federation-learning-platform"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={(value, option) => {
                    field.onChange(value);
                    form.setValue(
                      "organizationName" as never,
                      String(option?.record?.["organizationName"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                  }}
                  placeholder={t("resources.federation_membership_directory.commands.inviteParticipant.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.federation_membership_directory.commands.inviteParticipant.fields.organizationId.label", "Organization Directory"),
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
            name="invitationNote"
            rules={{ required: "Invitation Note is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_membership_directory.commands.inviteParticipant.fields.invitationNote.label", "Invitation Note")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Invitation Note"}
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
