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
import { UpdateDictionaryValueTranslationCommandSchema, type UpdateDictionaryValueTranslationCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const DictionaryValueCatalogUpdateDictionaryValueTranslation = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    dictionaryValueId: searchParams.get("dictionaryValueId") ?? undefined,
    dictionaryCode: searchParams.get("dictionaryCode") ?? undefined,
    valueCode: searchParams.get("valueCode") ?? undefined,
    description: searchParams.get("description") ?? undefined,
    dictionaryValueTranslationId: searchParams.get("dictionaryValueTranslationId") ?? undefined,
    locale: searchParams.get("locale") ?? undefined,
    displayName: searchParams.get("displayName") ?? undefined,
  } as unknown as Partial<UpdateDictionaryValueTranslationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<UpdateDictionaryValueTranslationCommandInput, UpdateDictionaryValueTranslationCommandInput>({
    resource: "dictionary_value_catalog",
    command: "updateDictionaryValueTranslation",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "dictionary_value_catalog_read_model_entity",
      idField: "dictionaryValueId",
      label: t("resources.dictionary_value_catalog.label", "Dictionary Value Catalog"),
      aggregateRoute: "dictionaryvaluetranslation",
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
      resolver: zodResolver(UpdateDictionaryValueTranslationCommandSchema) as never,
    },
  });

  async function onSubmit(values: UpdateDictionaryValueTranslationCommandInput) {
    const result = await runFormBehavior<UpdateDictionaryValueTranslationCommandInput>(
      frontendComposition,
      "behavior:dictionary-value-catalog:updateDictionaryValueTranslation",
      {
      ...defaultValues,
      ...values,
      } as UpdateDictionaryValueTranslationCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/dictionary-value-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dictionary_value_catalog.commands.updateDictionaryValueTranslation.label", "Update Dictionary Value Translation")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("UpdateDictionaryValueTranslation validation failed", errors))} className="space-y-8">
          {defaultValues.dictionaryValueTranslationId !== undefined && defaultValues.dictionaryValueTranslationId !== null ? (
            <input type="hidden" {...form.register("dictionaryValueTranslationId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="dictionaryValueId"
            rules={{ required: "Dictionary Value Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.updateDictionaryValueTranslation.fields.dictionaryValueId.label", "Dictionary Value Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="defaultDisplayName"
                  optionValue="dictionaryValueId"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.dictionary_value_catalog.commands.updateDictionaryValueTranslation.fields.dictionaryValueId.placeholder", "Select Dictionary Value Id")}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.dictionary_value_catalog.commands.updateDictionaryValueTranslation.fields.dictionaryValueId.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active"],
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="dictionaryCode"
            rules={{ required: "Dictionary Code is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.updateDictionaryValueTranslation.fields.dictionaryCode.label", "Dictionary Code")}</FormLabel>
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
            name="valueCode"
            rules={{ required: "Value Code is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.updateDictionaryValueTranslation.fields.valueCode.label", "Value Code")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Value Code"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="locale"
            rules={{ required: "Locale is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.updateDictionaryValueTranslation.fields.locale.label", "Locale")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Locale"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="displayName"
            rules={{ required: "Display Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.updateDictionaryValueTranslation.fields.displayName.label", "Display Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Display Name"}
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
                <FormLabel>{t("resources.dictionary_value_catalog.commands.updateDictionaryValueTranslation.fields.description.label", "Description")}</FormLabel>
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
