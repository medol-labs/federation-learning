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
import { DownloadFileCommandSchema, type DownloadFileCommandInput } from "@/contexts/domain/schemas";

export const UploadedFileCatalogDownloadFile = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    fileId: searchParams.get("fileId") ?? undefined,
  } as unknown as Partial<DownloadFileCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<DownloadFileCommandInput, DownloadFileCommandInput>({
    resource: "uploaded_file_catalog",
    command: "downloadFile",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "uploaded_file_catalog_read_model_entity",
      idField: "fileId",
      label: t("resources.uploaded_file_catalog.label", "Uploaded File Catalog"),
      aggregateRoute: "uploadedfile",
      queryRoute: "uploadedfilecatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "uploaded_file_catalog_read_model_entity",
      idField: "fileId",
      label: t("resources.uploaded_file_catalog.label", "Uploaded File Catalog"),
      aggregateRoute: "uploadedfile",
      queryRoute: "uploadedfilecatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(DownloadFileCommandSchema) as never,
    },
  });

  async function onSubmit(values: DownloadFileCommandInput) {
    const result = await runFormBehavior<DownloadFileCommandInput>(
      frontendComposition,
      "behavior:uploaded-file-catalog:downloadFile",
      {
      ...defaultValues,
      ...values,
      } as DownloadFileCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/uploaded-file-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.uploaded_file_catalog.commands.downloadFile.label", "Download File")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("DownloadFile validation failed", errors))} className="space-y-8">
          {defaultValues.fileId !== undefined && defaultValues.fileId !== null ? (
            <input type="hidden" {...form.register("fileId" as never)} />
          ) : null}
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
