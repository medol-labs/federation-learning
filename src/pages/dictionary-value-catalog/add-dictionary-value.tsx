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
import { AddDictionaryValueCommandSchema, type AddDictionaryValueCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";


export const DictionaryValueCatalogAddDictionaryValue = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    dictionaryId: searchParams.get("dictionaryId") ?? undefined,
    dictionaryCode: searchParams.get("dictionaryCode") ?? undefined,
    valueCode: searchParams.get("valueCode") ?? undefined,
    displayName: searchParams.get("displayName") ?? undefined,
    displayOrder: (() => { const value = searchParams.get("displayOrder"); return value === null ? undefined : Number(value); })(),
    description: searchParams.get("description") ?? undefined,
    active: (() => { const value = searchParams.get("active"); return value === null ? undefined : value === "true"; })(),
  } as Partial<AddDictionaryValueCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<AddDictionaryValueCommandInput, AddDictionaryValueCommandInput>({
    resource: "dictionary_value_catalog",
    command: "addDictionaryValue",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "fldictionary-backend",
    queryDataProviderName: "fldictionary-backend",
    meta: {
      tableName: "dictionary_value_catalog_read_model_entity",
      idField: "dictionaryValueId",
      label: t("resources.dictionary_value_catalog.label", "Dictionary Value Catalog"),
      aggregateRoute: "dictionaryvalue",
      queryRoute: "dictionaryvaluecatalog",
      dataProviderName: "fldictionary-backend",
    },
    queryMeta: {
      tableName: "dictionary_value_catalog_read_model_entity",
      idField: "dictionaryValueId",
      label: t("resources.dictionary_value_catalog.label", "Dictionary Value Catalog"),
      aggregateRoute: "dictionaryvalue",
      queryRoute: "dictionaryvaluecatalog",
      dataProviderName: "fldictionary-backend",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(AddDictionaryValueCommandSchema) as never,
    },
  });

  function onSubmit(values: AddDictionaryValueCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.dictionary_value_catalog.commands.addDictionaryValue.label", "Add Dictionary Value")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="dictionaryId"
            rules={{ required: "Dictionary Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.dictionaryId.label", "Dictionary Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_catalog"
                  dataProviderName="fldictionary-backend"
                  optionLabel="dictionaryName"
                  optionValue="dictionaryId"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.dictionaryId.placeholder", "Select Dictionary Id")}
                  meta={{
                    idField: "dictionaryId",
                    label: t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.dictionaryId.label", "Dictionary Catalog"),
                    aggregateRoute: "dictionary",
                    queryRoute: "dictionarycatalog",
                    queryFields: ["dictionaryCode"],
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
                <FormLabel>{t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.dictionaryCode.label", "Dictionary Code")}</FormLabel>
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
                <FormLabel>{t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.valueCode.label", "Value Code")}</FormLabel>
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
            name="displayName"
            rules={{ required: "Display Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.displayName.label", "Display Name")}</FormLabel>
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
            name="displayOrder"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.displayOrder.label", "Display Order")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Display Order"}
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
                <FormLabel>{t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.description.label", "Description")}</FormLabel>
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
          <FormField
            control={form.control}
            name="active"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.active.label", "Active")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.dictionary_value_catalog.commands.addDictionaryValue.fields.active.placeholder", "Select Active")} />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="true">{t("values.boolean.true", "True")}</SelectItem>
                    <SelectItem value="false">{t("values.boolean.false", "False")}</SelectItem>
                  </SelectContent>
                </Select>
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
