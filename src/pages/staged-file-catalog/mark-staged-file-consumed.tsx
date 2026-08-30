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
import { MarkStagedFileConsumedCommandSchema, type MarkStagedFileConsumedCommandInput } from "@/domain/schemas";

export const StagedFileCatalogMarkStagedFileConsumed = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    consumedByContext: searchParams.get("consumedByContext") ?? undefined,
    consumedByCommand: searchParams.get("consumedByCommand") ?? undefined,
    consumedByCommandId: searchParams.get("consumedByCommandId") ?? undefined,
    stagedFileId: searchParams.get("stagedFileId") ?? undefined,
  } as unknown as Partial<MarkStagedFileConsumedCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<MarkStagedFileConsumedCommandInput, MarkStagedFileConsumedCommandInput>({
    resource: "staged_file_catalog",
    command: "markStagedFileConsumed",
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
      resolver: zodResolver(MarkStagedFileConsumedCommandSchema) as never,
    },
  });

  async function onSubmit(values: MarkStagedFileConsumedCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/staged-file-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.staged_file_catalog.commands.markStagedFileConsumed.label", "Mark Staged File Consumed")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("MarkStagedFileConsumed validation failed", errors))} className="space-y-8">
          {defaultValues.stagedFileId !== undefined && defaultValues.stagedFileId !== null ? (
            <input type="hidden" {...form.register("stagedFileId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="consumedByContext"
            rules={{ required: "Consumed By Context is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.staged_file_catalog.commands.markStagedFileConsumed.fields.consumedByContext.label", "Consumed By Context")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Consumed By Context"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="consumedByCommand"
            rules={{ required: "Consumed By Command is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.staged_file_catalog.commands.markStagedFileConsumed.fields.consumedByCommand.label", "Consumed By Command")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Consumed By Command"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="consumedByCommandId"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.staged_file_catalog.commands.markStagedFileConsumed.fields.consumedByCommandId.label", "Consumed By Command Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Consumed By Command Id"}
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
