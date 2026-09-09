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
import { ArchiveDictionaryCommandSchema, type ArchiveDictionaryCommandInput } from "@/contexts/domain/schemas";

export const DictionaryCatalogArchiveDictionary = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    archiveReason: searchParams.get("archiveReason") ?? undefined,
    dictionaryId: searchParams.get("dictionaryId") ?? undefined,
  } as unknown as Partial<ArchiveDictionaryCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ArchiveDictionaryCommandInput, ArchiveDictionaryCommandInput>({
    resource: "dictionary_catalog",
    command: "archiveDictionary",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "dictionary_catalog_read_model_entity",
      idField: "dictionaryId",
      label: t("resources.dictionary_catalog.label", "Dictionary Catalog"),
      aggregateRoute: "dictionary",
      queryRoute: "dictionarycatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "dictionary_catalog_read_model_entity",
      idField: "dictionaryId",
      label: t("resources.dictionary_catalog.label", "Dictionary Catalog"),
      aggregateRoute: "dictionary",
      queryRoute: "dictionarycatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(ArchiveDictionaryCommandSchema) as never,
    },
  });

  async function onSubmit(values: ArchiveDictionaryCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/dictionary-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dictionary_catalog.commands.archiveDictionary.label", "Archive Dictionary")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ArchiveDictionary validation failed", errors))} className="space-y-8">
          {defaultValues.dictionaryId !== undefined && defaultValues.dictionaryId !== null ? (
            <input type="hidden" {...form.register("dictionaryId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="archiveReason"
            rules={{ required: "Archive Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_catalog.commands.archiveDictionary.fields.archiveReason.label", "Archive Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Archive Reason"}
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
