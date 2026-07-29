// Generated from config.json by the refine generator.
import { useParsed } from "@refinedev/core";
import { useTranslate } from "@refinedev/core";
import { useOne } from "@refinedev/core";
import { useNavigate, useSearchParams } from "react-router";

import {
  CreateView,
  CreateViewHeader,
} from "@/components/refine-ui/views/create-view";
import { AlertCircle, CheckCircle2 } from "lucide-react";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
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

type RuntimeDatasetBindingRecord = Partial<ValidateAgentDatasetAccessCommandInput> & {
  runtimeDatasetBindingId?: string;
};

function missingValidationFields(values: Partial<ValidateAgentDatasetAccessCommandInput>) {
  const missing: string[] = [];
  const hasText = (value: unknown) => typeof value === "string" && value.trim().length > 0;
  const normalizedSource = values.dataSourceType?.trim().toLowerCase().replace(/-/g, "_") ?? "";
  const normalizedFormat = values.dataFormat?.trim().toLowerCase().replace(/-/g, "_") ?? "";

  if (!hasText(values.runtimeDatasetBindingId)) missing.push("Runtime Dataset Binding Id");
  if (!hasText(values.datasetId)) missing.push("Dataset Id");
  if (!hasText(values.runtimeId)) missing.push("Runtime Id");
  if (!hasText(values.dataSourceType)) missing.push("Data Source Type");
  if (!hasText(values.dataFormat)) missing.push("Data Format");

  const isCsvFile =
    normalizedFormat === "csv" ||
    normalizedSource === "csv" ||
    normalizedSource === "file_csv" ||
    (normalizedSource === "file" && normalizedFormat === "csv");

  if (isCsvFile && !hasText(values.filePath)) missing.push("File Path");
  return missing;
}

export const RuntimeDatasetBindingCatalogValidateAgentDatasetAccess = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    runtimeDatasetBindingId: searchParams.get("runtimeDatasetBindingId") ?? undefined,
  } as Partial<ValidateAgentDatasetAccessCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ValidateAgentDatasetAccessCommandInput, ValidateAgentDatasetAccessCommandInput>({
    resource: "runtime_dataset_binding_catalog",
    command: "validateAgentDatasetAccess",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-runtime-agent",
    queryDataProviderName: "federation-learning-runtime-agent",
    meta: {
      tableName: "runtime_dataset_binding_catalog_read_model_entity",
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_binding_catalog.label", "Runtime Dataset Binding Catalog"),
      aggregateRoute: "agentdatasetaccessvalidation",
      queryRoute: "runtimedatasetbindingcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    queryMeta: {
      tableName: "runtime_dataset_binding_catalog_read_model_entity",
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_binding_catalog.label", "Runtime Dataset Binding Catalog"),
      aggregateRoute: "runtimedatasetbinding",
      queryRoute: "runtimedatasetbindingcatalog",
      dataProviderName: "federation-learning-runtime-agent",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ValidateAgentDatasetAccessCommandSchema) as never,
    },
  });

  function onSubmit(values: ValidateAgentDatasetAccessCommandInput) {
    const payload = {
      ...values,
      ...selectedBinding,
      runtimeDatasetBindingId: values.runtimeDatasetBindingId,
    };
    return onFinish({
      ...defaultValues,
      ...payload,
    });
  }

  const runtimeDatasetBindingId = form.watch("runtimeDatasetBindingId");
  const bindingQuery = useOne<RuntimeDatasetBindingRecord>({
    resource: "runtime_dataset_binding_catalog",
    id: runtimeDatasetBindingId,
    dataProviderName: "federation-learning-runtime-agent",
    meta: {
      idField: "runtimeDatasetBindingId",
      label: t("resources.runtime_dataset_binding_catalog.commands.validateAgentDatasetAccess.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Catalog"),
      aggregateRoute: "runtimedatasetbinding",
      queryRoute: "runtimedatasetbindingcatalog",
    },
    queryOptions: {
      enabled: !!runtimeDatasetBindingId,
    },
  });
  const selectedBinding = bindingQuery.result as RuntimeDatasetBindingRecord | undefined;
  const missingFields = missingValidationFields({
    ...selectedBinding,
    runtimeDatasetBindingId,
  });
  const fieldsComplete = missingFields.length === 0;

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_dataset_binding_catalog.commands.validateAgentDatasetAccess.label", "Validate Agent Dataset Access")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="runtimeDatasetBindingId"
            rules={{ required: "Runtime Dataset Binding Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_dataset_binding_catalog.commands.validateAgentDatasetAccess.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="runtime_dataset_binding_catalog"
                  dataProviderName="federation-learning-runtime-agent"
                  optionLabel="datasetName"
                  optionValue="runtimeDatasetBindingId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.runtime_dataset_binding_catalog.commands.validateAgentDatasetAccess.fields.runtimeDatasetBindingId.placeholder", "Select Runtime Dataset Binding Id")}
                  meta={{
                    idField: "runtimeDatasetBindingId",
                    label: t("resources.runtime_dataset_binding_catalog.commands.validateAgentDatasetAccess.fields.runtimeDatasetBindingId.label", "Runtime Dataset Binding Catalog"),
                    aggregateRoute: "runtimedatasetbinding",
                    queryRoute: "runtimedatasetbindingcatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <Alert variant={fieldsComplete ? "default" : "destructive"}>
            {fieldsComplete ? <CheckCircle2 /> : <AlertCircle />}
            <AlertTitle>
              {fieldsComplete ? "Validation fields complete" : "Validation fields incomplete"}
            </AlertTitle>
            <AlertDescription>
              {fieldsComplete
                ? "Binding details are ready to submit."
                : `Missing: ${missingFields.join(", ")}`}
            </AlertDescription>
          </Alert>
          <div className="flex gap-2">
            <Button
              type="submit"
              {...form.saveButtonProps}
              disabled={form.formState.isSubmitting || !fieldsComplete}
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
