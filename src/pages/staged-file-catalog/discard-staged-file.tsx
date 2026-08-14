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
import { DiscardStagedFileCommandSchema, type DiscardStagedFileCommandInput } from "@/domain/schemas";

export const StagedFileCatalogDiscardStagedFile = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    discardReason: searchParams.get("discardReason") ?? undefined,
    stagedFileId: searchParams.get("stagedFileId") ?? undefined,
  } as Partial<DiscardStagedFileCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<DiscardStagedFileCommandInput, DiscardStagedFileCommandInput>({
    resource: "staged_file_catalog",
    command: "discardStagedFile",
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
      resolver: zodResolver(DiscardStagedFileCommandSchema) as never,
    },
  });

  async function onSubmit(values: DiscardStagedFileCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.staged_file_catalog.commands.discardStagedFile.label", "Discard Staged File")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("DiscardStagedFile validation failed", errors))} className="space-y-8">
          {defaultValues.stagedFileId !== undefined && defaultValues.stagedFileId !== null ? (
            <input type="hidden" {...form.register("stagedFileId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="discardReason"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.staged_file_catalog.commands.discardStagedFile.fields.discardReason.label", "Discard Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Discard Reason"}
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
