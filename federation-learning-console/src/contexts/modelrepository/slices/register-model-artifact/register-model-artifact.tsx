// Generated from config.json by the refine generator.
import { useParsed } from "@refinedev/core";
import { useTranslate } from "@refinedev/core";
import { useNotification } from "@refinedev/core";
import { useState } from "react";
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
import { RegisterModelArtifactCommandSchema, type RegisterModelArtifactCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";
import { uploadFile, type PendingFileUpload } from "@/lib/upload-file";

export const ModelArtifactCatalogRegisterModelArtifact = () => {
  const t = useTranslate();
  const { open } = useNotification();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const [pendingFileUploads, setPendingFileUploads] = useState<Record<string, PendingFileUpload | undefined>>({});
  const defaultValues = {
    modelName: searchParams.get("modelName") ?? undefined,
    modelPlugin: searchParams.get("modelPlugin") ?? undefined,
    modelVersion: searchParams.get("modelVersion") ?? undefined,
    modelDescription: searchParams.get("modelDescription") ?? undefined,
    sourceType: searchParams.get("sourceType") ?? undefined,
    modelFormat: searchParams.get("modelFormat") ?? undefined,
  } as unknown as Partial<RegisterModelArtifactCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterModelArtifactCommandInput, RegisterModelArtifactCommandInput>({
    resource: "model_artifact_catalog",
    command: "registerModelArtifact",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "model_artifact_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_artifact_catalog.label", "Model Artifact Catalog"),
      aggregateRoute: "modelartifact",
      queryRoute: "modelartifactcatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "model_artifact_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_artifact_catalog.label", "Model Artifact Catalog"),
      aggregateRoute: "modelartifact",
      queryRoute: "modelartifactcatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RegisterModelArtifactCommandSchema) as never,
    },
  });

  function setPendingFile(fieldName: string, file: File | undefined, onChange: (value: string) => void) {
    const uploadId = file ? crypto.randomUUID() : "";
    setPendingFileUploads((current) => ({
      ...current,
      [fieldName]: file ? { file, uploadId } : undefined,
    }));
    onChange(uploadId);
  }

  async function onSubmit(values: RegisterModelArtifactCommandInput) {
    const nextValues = {
      ...defaultValues,
      ...values,
    } as RegisterModelArtifactCommandInput;
    if (pendingFileUploads.fileId) {
      let uploadedId: string;
      try {
        uploadedId = await uploadFile({
          file: pendingFileUploads.fileId!.file,
          uploadId: pendingFileUploads.fileId!.uploadId,
          source: "model_artifact_catalog.registerModelArtifact.fileId",
          values: {
            ...nextValues,
            fileId: pendingFileUploads.fileId!.uploadId,
          } as Record<string, unknown>,
        });
      } catch (error) {
        open?.({
          type: "error",
          message: t("notifications.fileUpload.failed", "File upload failed"),
          description: error instanceof Error ? error.message : String(error),
        });
        return;
      }
      nextValues.fileId = uploadedId as never;
    }
    const result = await onFinish(nextValues);
    navigate("/model-artifact-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.model_artifact_catalog.commands.registerModelArtifact.label", "Register Model Artifact")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterModelArtifact validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="modelName"
            rules={{ required: "Model Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelName.label", "Model Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelPlugin"
            rules={{ required: "Model Plugin is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelPlugin.label", "Model Plugin")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelPlugin.placeholder", "Select Model Plugin")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"MODEL_PLUGIN"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelPlugin.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelVersion"
            rules={{ required: "Model Version is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelVersion.label", "Model Version")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Version"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelDescription"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelDescription.label", "Model Description")}</FormLabel>
                <FormControl>
                  <Textarea
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Description"}
                    rows={8}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="sourceType"
            rules={{ required: "Source Type is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.sourceType.label", "Source Type")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.sourceType.placeholder", "Select Source Type")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"MODEL_ARTIFACT_SOURCE_TYPE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.sourceType.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="fileId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.fileId.label", "File Id")}</FormLabel>
                <FormControl>
                  <Input
                    type="file"
                    onChange={(event) => {
                      const file = event.target.files?.[0];
                      setPendingFile("fileId", file, field.onChange);
                    }}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelFormat"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelFormat.label", "Model Format")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelFormat.placeholder", "Select Model Format")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"MODEL_FORMAT"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelFormat.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
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
