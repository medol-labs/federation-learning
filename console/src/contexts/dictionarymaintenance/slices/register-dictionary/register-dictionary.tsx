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
import { RegisterDictionaryCommandSchema, type RegisterDictionaryCommandInput } from "@/contexts/domain/schemas";

export const DictionaryCatalogRegisterDictionary = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    dictionaryCode: searchParams.get("dictionaryCode") ?? undefined,
    dictionaryName: searchParams.get("dictionaryName") ?? undefined,
    description: searchParams.get("description") ?? undefined,
  } as unknown as Partial<RegisterDictionaryCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterDictionaryCommandInput, RegisterDictionaryCommandInput>({
    resource: "dictionary_catalog",
    command: "registerDictionary",
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
      resolver: zodResolver(RegisterDictionaryCommandSchema) as never,
    },
  });

  async function onSubmit(values: RegisterDictionaryCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/dictionary-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dictionary_catalog.commands.registerDictionary.label", "Register Dictionary")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterDictionary validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="dictionaryCode"
            rules={{ required: "Dictionary Code is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_catalog.commands.registerDictionary.fields.dictionaryCode.label", "Dictionary Code")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Dictionary Code"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="dictionaryName"
            rules={{ required: "Dictionary Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_catalog.commands.registerDictionary.fields.dictionaryName.label", "Dictionary Name")}</FormLabel>
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
                <FormLabel>{t("resources.dictionary_catalog.commands.registerDictionary.fields.description.label", "Description")}</FormLabel>
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
