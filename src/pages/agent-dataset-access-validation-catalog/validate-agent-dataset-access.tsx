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
import { ValidateAgentDatasetAccessCommandSchema, type ValidateAgentDatasetAccessCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const AgentDatasetAccessValidationCatalogValidateAgentDatasetAccess = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeDatasetBindingId: searchParams.get("runtimeDatasetBindingId") ?? undefined,
  } as Partial<ValidateAgentDatasetAccessCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ValidateAgentDatasetAccessCommandInput, ValidateAgentDatasetAccessCommandInput>({
    resource: "agent_dataset_access_validation_catalog",
    command: "validateAgentDatasetAccess",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "agent_dataset_access_validation_catalog_read_model_entity",
      idField: "datasetAccessValidationId",
      label: t("resources.agent_dataset_access_validation_catalog.label", "Agent Dataset Access Validation Catalog"),
      aggregateRoute: "agentdatasetaccessvalidation",
      queryRoute: "agentdatasetaccessvalidationcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "agent_dataset_access_validation_catalog_read_model_entity",
      idField: "datasetAccessValidationId",
      label: t("resources.agent_dataset_access_validation_catalog.label", "Agent Dataset Access Validation Catalog"),
      aggregateRoute: "agentdatasetaccessvalidation",
      queryRoute: "agentdatasetaccessvalidationcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ValidateAgentDatasetAccessCommandSchema) as never,
    },
  });

  function onSubmit(values: ValidateAgentDatasetAccessCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.agent_dataset_access_validation_catalog.commands.validateAgentDatasetAccess.label", "Validate Agent Dataset Access")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="runtimeDatasetBindingId"
            rules={{ required: "Runtime Dataset Binding Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.agent_dataset_access_validation_catalog.commands.validateAgentDatasetAccess.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_dataset_binding_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="datasetName"
                  optionValue="runtimeDatasetBindingId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.agent_dataset_access_validation_catalog.commands.validateAgentDatasetAccess.fields.runtimeDatasetBindingId.placeholder", "Select Runtime Dataset Binding Id")}
                  meta={{
                    idField: "runtimeDatasetBindingId",
                    label: t("resources.agent_dataset_access_validation_catalog.commands.validateAgentDatasetAccess.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Catalog"),
                    aggregateRoute: "runtimedatasetbinding",
                    queryRoute: "runtimedatasetbindingcatalog",
                  }}
                />
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
