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
import { CreateTrainingJobCommandSchema, type CreateTrainingJobCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const TrainingRunConfigurationCatalogCreateTrainingJob = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    federationId: searchParams.get("federationId") ?? undefined,
    trainingRunConfigurationId: searchParams.get("trainingRunConfigurationId") ?? undefined,
  } as Partial<CreateTrainingJobCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<CreateTrainingJobCommandInput, CreateTrainingJobCommandInput>({
    resource: "training_run_configuration_catalog",
    command: "createTrainingJob",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_run_configuration_catalog_read_model_entity",
      idField: "trainingRunConfigurationId",
      label: t("resources.training_run_configuration_catalog.label", "Training Run Configuration Catalog"),
      aggregateRoute: "trainingjob",
      queryRoute: "trainingrunconfigurationcatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "training_run_configuration_catalog_read_model_entity",
      idField: "trainingRunConfigurationId",
      label: t("resources.training_run_configuration_catalog.label", "Training Run Configuration Catalog"),
      aggregateRoute: "trainingrunconfiguration",
      queryRoute: "trainingrunconfigurationcatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(CreateTrainingJobCommandSchema) as never,
    },
  });

  function onSubmit(values: CreateTrainingJobCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.training_run_configuration_catalog.commands.createTrainingJob.label", "Create Training Job")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("CreateTrainingJob validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="federationId"
            rules={{ required: "Federation Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.createTrainingJob.fields.federationId.label", "Federation Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="federation_overview"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="federationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_run_configuration_catalog.commands.createTrainingJob.fields.federationId.placeholder", "Select Federation Id")}
                  meta={{
                    idField: "federationId",
                    label: t("resources.training_run_configuration_catalog.commands.createTrainingJob.fields.federationId.label", "Federation Overview"),
                    aggregateRoute: "federation",
                    queryRoute: "federationoverview",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="trainingRunConfigurationId"
            rules={{ required: "Training Run Configuration Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.createTrainingJob.fields.trainingRunConfigurationId.label", "Training Run Configuration Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="training_run_configuration_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="trainingRunConfigurationId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.training_run_configuration_catalog.commands.createTrainingJob.fields.trainingRunConfigurationId.placeholder", "Select Training Run Configuration Id")}
                  meta={{
                    idField: "trainingRunConfigurationId",
                    label: t("resources.training_run_configuration_catalog.commands.createTrainingJob.fields.trainingRunConfigurationId.label", "Training Run Configuration Catalog"),
                    aggregateRoute: "trainingrunconfiguration",
                    queryRoute: "trainingrunconfigurationcatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="objective"
            rules={{ required: "Objective is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.createTrainingJob.fields.objective.label", "Objective")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Objective"}
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
