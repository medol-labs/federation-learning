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
import { EnableDictionaryValueCommandSchema, type EnableDictionaryValueCommandInput } from "@/domain/schemas";

export const DictionaryValueCatalogEnableDictionaryValue = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    dictionaryValueId: searchParams.get("dictionaryValueId") ?? undefined,
  } as unknown as Partial<EnableDictionaryValueCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<EnableDictionaryValueCommandInput, EnableDictionaryValueCommandInput>({
    resource: "dictionary_value_catalog",
    command: "enableDictionaryValue",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "dictionary_value_catalog_read_model_entity",
      idField: "dictionaryValueId",
      label: t("resources.dictionary_value_catalog.label", "Dictionary Value Catalog"),
      aggregateRoute: "dictionaryvalue",
      queryRoute: "dictionaryvaluecatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "dictionary_value_catalog_read_model_entity",
      idField: "dictionaryValueId",
      label: t("resources.dictionary_value_catalog.label", "Dictionary Value Catalog"),
      aggregateRoute: "dictionaryvalue",
      queryRoute: "dictionaryvaluecatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(EnableDictionaryValueCommandSchema) as never,
    },
  });

  async function onSubmit(values: EnableDictionaryValueCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/dictionary-value-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dictionary_value_catalog.commands.enableDictionaryValue.label", "Enable Dictionary Value")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("EnableDictionaryValue validation failed", errors))} className="space-y-8">
          {defaultValues.dictionaryValueId !== undefined && defaultValues.dictionaryValueId !== null ? (
            <input type="hidden" {...form.register("dictionaryValueId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="enableReason"
            rules={{ required: "Enable Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.enableDictionaryValue.fields.enableReason.label", "Enable Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Enable Reason"}
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
