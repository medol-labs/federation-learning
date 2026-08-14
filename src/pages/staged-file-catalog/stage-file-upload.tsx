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
import { StageFileUploadCommandSchema, type StageFileUploadCommandInput } from "@/domain/schemas";
import { uploadFile, type PendingFileUpload } from "@/lib/upload-file";

export const StagedFileCatalogStageFileUpload = () => {
  const t = useTranslate();
  const { open } = useNotification();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const [pendingFileUploads, setPendingFileUploads] = useState<Record<string, PendingFileUpload | undefined>>({});
  const defaultValues = {
    purpose: searchParams.get("purpose") ?? undefined,
    originalFileName: searchParams.get("originalFileName") ?? undefined,
    contentType: searchParams.get("contentType") ?? undefined,
    sizeBytes: (() => { const value = searchParams.get("sizeBytes"); return value === null ? undefined : Number(value); })(),
    stagedFileLocation: searchParams.get("stagedFileLocation") ?? undefined,
    checksum: searchParams.get("checksum") ?? undefined,
    expiresAt: searchParams.get("expiresAt") ?? undefined,
  } as Partial<StageFileUploadCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<StageFileUploadCommandInput, StageFileUploadCommandInput>({
    resource: "staged_file_catalog",
    command: "stageFileUpload",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "staged_file_catalog_read_model_entity",
      idField: "stagedFileId",
      label: t("resources.staged_file_catalog.label", "Staged File Catalog"),
      aggregateRoute: "stagedfile",
      queryRoute: "stagedfilecatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "staged_file_catalog_read_model_entity",
      idField: "stagedFileId",
      label: t("resources.staged_file_catalog.label", "Staged File Catalog"),
      aggregateRoute: "stagedfile",
      queryRoute: "stagedfilecatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(StageFileUploadCommandSchema.pick({
        uploadedFile: true,
        purpose: true,
      })) as never,
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

  async function onSubmit(values: StageFileUploadCommandInput) {
    const nextValues = {
      ...defaultValues,
      ...values,
    } as StageFileUploadCommandInput;
    if (!pendingFileUploads.uploadedFile) {
      open?.({
        type: "error",
        message: t("notifications.fileUpload.required", "File is required"),
        description: t("notifications.fileUpload.chooseFile", "Choose a file before submitting."),
      });
      return;
    }
    if (pendingFileUploads.uploadedFile) {
      let uploadedId: string;
      try {
        uploadedId = await uploadFile({
          file: pendingFileUploads.uploadedFile!.file,
          uploadId: pendingFileUploads.uploadedFile!.uploadId,
          source: "staged_file_catalog.stageFileUpload.uploadedFile",
          values: {
            ...nextValues,
            uploadedFile: pendingFileUploads.uploadedFile!.uploadId,
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
      nextValues.uploadedFile = uploadedId as never;
      open?.({
        type: "success",
        message: t("notifications.fileUpload.staged", "File staged"),
        description: uploadedId,
      });
    }
    navigate(-1);
    return;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.staged_file_catalog.commands.stageFileUpload.label", "Stage File Upload")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("StageFileUpload validation failed", errors))} className="space-y-8">
          {defaultValues.originalFileName !== undefined && defaultValues.originalFileName !== null ? (
            <input type="hidden" {...form.register("originalFileName" as never)} />
          ) : null}
          {defaultValues.contentType !== undefined && defaultValues.contentType !== null ? (
            <input type="hidden" {...form.register("contentType" as never)} />
          ) : null}
          {defaultValues.sizeBytes !== undefined && defaultValues.sizeBytes !== null ? (
            <input type="hidden" {...form.register("sizeBytes" as never)} />
          ) : null}
          {defaultValues.stagedFileLocation !== undefined && defaultValues.stagedFileLocation !== null ? (
            <input type="hidden" {...form.register("stagedFileLocation" as never)} />
          ) : null}
          {defaultValues.checksum !== undefined && defaultValues.checksum !== null ? (
            <input type="hidden" {...form.register("checksum" as never)} />
          ) : null}
          {defaultValues.expiresAt !== undefined && defaultValues.expiresAt !== null ? (
            <input type="hidden" {...form.register("expiresAt" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="uploadedFile"
            rules={{ required: "Uploaded File is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.staged_file_catalog.commands.stageFileUpload.fields.uploadedFile.label", "Uploaded File")}</FormLabel>
                <FormControl>
                  <Input
                    type="file"
                    onChange={(event) => {
                      const file = event.target.files?.[0];
                      setPendingFile("uploadedFile", file, field.onChange);
                    }}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="purpose"
            rules={{ required: "Purpose is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.staged_file_catalog.commands.stageFileUpload.fields.purpose.label", "Purpose")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Purpose"}
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
