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
import { RejectParticipantCommandSchema, type RejectParticipantCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const FederationMembershipDirectoryRejectParticipant = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationId: searchParams.get("organizationId") ?? undefined,
    federationId: searchParams.get("federationId") ?? undefined,
  } as Partial<RejectParticipantCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RejectParticipantCommandInput, RejectParticipantCommandInput>({
    resource: "federation_membership_directory",
    command: "rejectParticipant",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "flplatform-backend",
    queryDataProviderName: "flplatform-backend",
    meta: {
      tableName: "federation_membership_directory_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_membership_directory.label", "Federation Membership Directory"),
      aggregateRoute: "federationmembership",
      queryRoute: "federationmembershipdirectory",
      dataProviderName: "flplatform-backend",
    },
    queryMeta: {
      tableName: "federation_membership_directory_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_membership_directory.label", "Federation Membership Directory"),
      aggregateRoute: "federationmembership",
      queryRoute: "federationmembershipdirectory",
      dataProviderName: "flplatform-backend",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RejectParticipantCommandSchema) as never,
    },
  });

  function onSubmit(values: RejectParticipantCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.federation_membership_directory.commands.rejectParticipant.label", "Reject Participant")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <input type="hidden" {...form.register("federationId" as never)} />
          <FormField
            control={form.control}
            name="organizationId"
            rules={{ required: "Organization Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_membership_directory.commands.rejectParticipant.fields.organizationId.label", "Organization Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="organization_directory"
                  dataProviderName="flplatform-backend"
                  optionLabel="organizationName"
                  optionValue="organizationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.federation_membership_directory.commands.rejectParticipant.fields.organizationId.placeholder", "Select Organization Id")}
                  meta={{
                    idField: "organizationId",
                    label: t("resources.federation_membership_directory.commands.rejectParticipant.fields.organizationId.label", "Organization Directory"),
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
            name="rejectionReason"
            rules={{ required: "Rejection Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_membership_directory.commands.rejectParticipant.fields.rejectionReason.label", "Rejection Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Rejection Reason"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="flex gap-2">
            <Button
              type="submit"
              {...form.saveButtonProps}
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
