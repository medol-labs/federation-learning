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
import { MarkFileReferencedCommandSchema, type MarkFileReferencedCommandInput } from "@/domain/schemas";

export const UploadedFileCatalogMarkFileReferenced = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    referencedByContext: searchParams.get("referencedByContext") ?? undefined,
    referencedByCommand: searchParams.get("referencedByCommand") ?? undefined,
    referencedByCommandId: searchParams.get("referencedByCommandId") ?? undefined,
    fileId: searchParams.get("fileId") ?? undefined,
  } as unknown as Partial<MarkFileReferencedCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<MarkFileReferencedCommandInput, MarkFileReferencedCommandInput>({
    resource: "uploaded_file_catalog",
    command: "markFileReferenced",
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
      resolver: zodResolver(MarkFileReferencedCommandSchema) as never,
    },
  });

  async function onSubmit(values: MarkFileReferencedCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/uploaded-file-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.uploaded_file_catalog.commands.markFileReferenced.label", "Mark File Referenced")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("MarkFileReferenced validation failed", errors))} className="space-y-8">
          {defaultValues.fileId !== undefined && defaultValues.fileId !== null ? (
            <input type="hidden" {...form.register("fileId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="referencedByContext"
            rules={{ required: "Referenced By Context is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.uploaded_file_catalog.commands.markFileReferenced.fields.referencedByContext.label", "Referenced By Context")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Referenced By Context"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="referencedByCommand"
            rules={{ required: "Referenced By Command is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.uploaded_file_catalog.commands.markFileReferenced.fields.referencedByCommand.label", "Referenced By Command")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Referenced By Command"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="referencedByCommandId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.uploaded_file_catalog.commands.markFileReferenced.fields.referencedByCommandId.label", "Referenced By Command Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Referenced By Command Id"}
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
