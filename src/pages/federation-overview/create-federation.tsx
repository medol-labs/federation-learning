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
import { CreateFederationCommandSchema, type CreateFederationCommandInput } from "@/domain/schemas";


export const FederationOverviewCreateFederation = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    federationName: searchParams.get("federationName") ?? undefined,
    minimumParticipantCount: (() => { const value = searchParams.get("minimumParticipantCount"); return value === null ? undefined : Number(value); })(),
  } as Partial<CreateFederationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<CreateFederationCommandInput, CreateFederationCommandInput>({
    resource: "federation_overview",
    command: "createFederation",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "flplatform-backend",
    queryDataProviderName: "flplatform-backend",
    meta: {
      tableName: "federation_overview_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_overview.label", "Federation Overview"),
      aggregateRoute: "federation",
      queryRoute: "federationoverview",
      dataProviderName: "flplatform-backend",
    },
    queryMeta: {
      tableName: "federation_overview_read_model_entity",
      idField: "federationId",
      label: t("resources.federation_overview.label", "Federation Overview"),
      aggregateRoute: "federation",
      queryRoute: "federationoverview",
      dataProviderName: "flplatform-backend",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(CreateFederationCommandSchema) as never,
    },
  });

  function onSubmit(values: CreateFederationCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.federation_overview.commands.createFederation.label", "Create Federation")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="federationName"
            rules={{ required: "Federation Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_overview.commands.createFederation.fields.federationName.label", "Federation Name")}</FormLabel>
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
            name="description"
            rules={{ required: "Description is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_overview.commands.createFederation.fields.description.label", "Description")}</FormLabel>
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
          <FormField
            control={form.control}
            name="minimumParticipantCount"
            rules={{ required: "Minimum Participant Count is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.federation_overview.commands.createFederation.fields.minimumParticipantCount.label", "Minimum Participant Count")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Minimum Participant Count"}
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
