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

export const RuntimeHealthDashboardCreateFederation = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    federationName: searchParams.get("federationName") ?? undefined,
  } as Partial<CreateFederationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<CreateFederationCommandInput, CreateFederationCommandInput>({
    resource: "runtime_health_dashboard",
    command: "createFederation",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_health_dashboard_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_health_dashboard.label", "Runtime Health Dashboard"),
      aggregateRoute: "federation",
      queryRoute: "runtimehealthdashboard",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_health_dashboard_read_model_entity",
      idField: "nodeId",
      label: t("resources.runtime_health_dashboard.label", "Runtime Health Dashboard"),
      aggregateRoute: "noderuntimehealth",
      queryRoute: "runtimehealthdashboard",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(CreateFederationCommandSchema) as never,
    },
  });

  async function onSubmit(values: CreateFederationCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-health-dashboard");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_health_dashboard.commands.createFederation.label", "Create Federation")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("CreateFederation validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="federationName"
            rules={{ required: "Federation Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_health_dashboard.commands.createFederation.fields.federationName.label", "Federation Name")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_health_dashboard.commands.createFederation.fields.description.label", "Description")}</FormLabel>
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
                <FormLabel>{t("resources.runtime_health_dashboard.commands.createFederation.fields.minimumParticipantCount.label", "Minimum Participant Count")}</FormLabel>
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
