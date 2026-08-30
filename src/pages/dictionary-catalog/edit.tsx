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
import { UpdateDictionaryCommandSchema, type UpdateDictionaryCommandInput } from "@/domain/schemas";

export const DictionaryCatalogUpdateDictionary = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    dictionaryName: searchParams.get("dictionaryName") ?? undefined,
    description: searchParams.get("description") ?? undefined,
    dictionaryId: searchParams.get("dictionaryId") ?? undefined,
  } as unknown as Partial<UpdateDictionaryCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<UpdateDictionaryCommandInput, UpdateDictionaryCommandInput>({
    resource: "dictionary_catalog",
    command: "updateDictionary",
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
      resolver: zodResolver(UpdateDictionaryCommandSchema) as never,
    },
  });

  async function onSubmit(values: UpdateDictionaryCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/dictionary-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dictionary_catalog.commands.updateDictionary.label", "Update Dictionary")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("UpdateDictionary validation failed", errors))} className="space-y-8">
          {defaultValues.dictionaryId !== undefined && defaultValues.dictionaryId !== null ? (
            <input type="hidden" {...form.register("dictionaryId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="dictionaryName"
            rules={{ required: "Dictionary Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_catalog.commands.updateDictionary.fields.dictionaryName.label", "Dictionary Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Dictionary Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="description"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_catalog.commands.updateDictionary.fields.description.label", "Description")}</FormLabel>
                <FormControl>
                  <Textarea
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Description"}
                    rows={8}
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
