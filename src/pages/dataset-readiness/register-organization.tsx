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
import { RegisterOrganizationCommandSchema, type RegisterOrganizationCommandInput } from "@/domain/schemas";

export const DatasetReadinessRegisterOrganization = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    organizationName: searchParams.get("organizationName") ?? undefined,
  } as Partial<RegisterOrganizationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterOrganizationCommandInput, RegisterOrganizationCommandInput>({
    resource: "dataset_readiness",
    command: "registerOrganization",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "dataset_readiness_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_readiness.label", "Dataset Readiness"),
      aggregateRoute: "organization",
      queryRoute: "datasetreadiness",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "dataset_readiness_read_model_entity",
      idField: "datasetId",
      label: t("resources.dataset_readiness.label", "Dataset Readiness"),
      aggregateRoute: "dataset",
      queryRoute: "datasetreadiness",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RegisterOrganizationCommandSchema) as never,
    },
  });

  async function onSubmit(values: RegisterOrganizationCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/dataset-readiness");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dataset_readiness.commands.registerOrganization.label", "Register Organization")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterOrganization validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="organizationName"
            rules={{ required: "Organization Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_readiness.commands.registerOrganization.fields.organizationName.label", "Organization Name")}</FormLabel>
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
            name="organizationType"
            rules={{ required: "Organization Type is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_readiness.commands.registerOrganization.fields.organizationType.label", "Organization Type")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={field.onChange}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.dataset_readiness.commands.registerOrganization.fields.organizationType.placeholder", "Select Organization Type")} />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="HOSPITAL">{t("resources.dataset_readiness.commands.registerOrganization.fields.organizationType.options.HOSPITAL", "Hospital")}</SelectItem>
                    <SelectItem value="RESEARCH_INSTITUTE">{t("resources.dataset_readiness.commands.registerOrganization.fields.organizationType.options.RESEARCH_INSTITUTE", "Research Institute")}</SelectItem>
                    <SelectItem value="PUBLIC_HEALTH_AGENCY">{t("resources.dataset_readiness.commands.registerOrganization.fields.organizationType.options.PUBLIC_HEALTH_AGENCY", "Public Health Agency")}</SelectItem>
                    <SelectItem value="LABORATORY">{t("resources.dataset_readiness.commands.registerOrganization.fields.organizationType.options.LABORATORY", "Laboratory")}</SelectItem>
                    <SelectItem value="REHABILITATION_CENTER">{t("resources.dataset_readiness.commands.registerOrganization.fields.organizationType.options.REHABILITATION_CENTER", "Rehabilitation Center")}</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="contactEmail"
            rules={{ required: "Contact Email is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dataset_readiness.commands.registerOrganization.fields.contactEmail.label", "Contact Email")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Contact Email"}
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
