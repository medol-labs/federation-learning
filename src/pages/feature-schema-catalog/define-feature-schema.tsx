// Generated from config.json by the refine generator.
import { useParsed } from "@refinedev/core";
import { useTranslate } from "@refinedev/core";
import { useNavigate, useSearchParams } from "react-router";
import type { Control } from "react-hook-form";
import { useFieldArray } from "react-hook-form";
import { Plus, Trash2 } from "lucide-react";

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
import { DefineFeatureSchemaCommandSchema, type DefineFeatureSchemaCommandInput } from "@/domain/schemas";
import { ResourceSelect } from "@/components/refine-ui/form/resource-select";

type ScalarArrayFieldProps = {
  control: Control<any>;
  name: string;
  label: string;
  inputType?: string | null;
  itemDefaultValue: string | number | boolean;
  options?: Array<{ value: string; label: string }>;
};

function ScalarArrayField({
  control,
  name,
  label,
  inputType,
  itemDefaultValue,
  options,
}: ScalarArrayFieldProps) {
  const optionItems = options ?? [];

  return (
    <FormField
      control={control}
      name={name as never}
      render={({ field }) => {
        const values = (Array.isArray(field.value) ? field.value : []) as Array<string | number | boolean>;

        return (
          <FormItem>
            <div className="flex items-center justify-between gap-2">
              <FormLabel>{label}</FormLabel>
              <Button
                type="button"
                variant="outline"
                size="sm"
                onClick={() => field.onChange([...values, itemDefaultValue])}
              >
                <Plus className="size-4" />
              </Button>
            </div>
            <div className="space-y-2">
              {values.map((value, index) => (
                <div key={index} className="flex items-center gap-2">
                  {optionItems.length > 0 ? (
                    <Select
                      value={value === undefined || value === null ? undefined : String(value)}
                      onValueChange={(nextValue) => {
                        const next = [...values];
                        next[index] = nextValue;
                        field.onChange(next);
                      }}
                    >
                      <SelectTrigger>
                        <SelectValue placeholder={label} />
                      </SelectTrigger>
                      <SelectContent>
                        {optionItems.map((option) => (
                          <SelectItem key={option.value} value={option.value}>
                            {option.label}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  ) : (
                  <Input
                    type={inputType ?? undefined}
                    value={typeof value === "boolean" ? String(value) : value ?? ""}
                    onChange={(event) => {
                      const next = [...values];
                      next[index] = inputType === "number"
                        ? Number(event.target.value)
                        : event.target.value;
                      field.onChange(next);
                    }}
                  />
                  )}
                  <Button
                    type="button"
                    variant="ghost"
                    size="icon"
                    onClick={() => field.onChange(values.filter((_, itemIndex) => itemIndex !== index))}
                  >
                    <Trash2 className="size-4" />
                  </Button>
                </div>
              ))}
            </div>
            <FormMessage />
          </FormItem>
        );
      }}
    />
  );
}

export const FeatureSchemaCatalogDefineFeatureSchema = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    featureDomain: searchParams.get("featureDomain") ?? undefined,
    dataModality: searchParams.get("dataModality") ?? undefined,
    features: searchParams.get("features")?.split(",").map((value) => value.trim()).filter(Boolean) ?? undefined,
    labels: searchParams.get("labels")?.split(",").map((value) => value.trim()).filter(Boolean) ?? undefined,
  } as Partial<DefineFeatureSchemaCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<DefineFeatureSchemaCommandInput, DefineFeatureSchemaCommandInput>({
    resource: "feature_schema_catalog",
    command: "defineFeatureSchema",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "feature_schema_catalog_read_model_entity",
      idField: "featureSchemaId",
      label: t("resources.feature_schema_catalog.label", "Feature Schema Catalog"),
      aggregateRoute: "featureschema",
      queryRoute: "featureschemacatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "feature_schema_catalog_read_model_entity",
      idField: "featureSchemaId",
      label: t("resources.feature_schema_catalog.label", "Feature Schema Catalog"),
      aggregateRoute: "featureschema",
      queryRoute: "featureschemacatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(DefineFeatureSchemaCommandSchema) as never,
    },
  });
  const featuresFields = useFieldArray({
    control: form.control,
    name: "features" as never,
  });
  const labelsFields = useFieldArray({
    control: form.control,
    name: "labels" as never,
  });

  function onSubmit(values: DefineFeatureSchemaCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.feature_schema_catalog.commands.defineFeatureSchema.label", "Define Feature Schema")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="featureDomain"
            rules={{ required: "Feature Domain is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.featureDomain.label", "Feature Domain")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Feature Domain"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="dataModality"
            rules={{ required: "Data Modality is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.dataModality.label", "Data Modality")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-dictionary"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={field.onChange}
                  placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.dataModality.placeholder", "Select Data Modality")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"FEATURE_SCHEMA_DATA_MODALITY"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"current":1,"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.dataModality.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="space-y-4 rounded-md border p-4">
            <div className="flex items-center justify-between gap-2">
              <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.label", "Features")}</FormLabel>
              <Button
                type="button"
                variant="outline"
                size="sm"
                onClick={() => featuresFields.append([{
  featureName: "",
  dataType: "",
  required: false,
  nullable: false,
  description: "",
  validationRules: [""],
  defaultValue: "",
  isIdentifier: false,
  isSensitive: false,
  encodingStrategy: "",
  featureTags: [""]
}] as never)}
              >
                <Plus className="size-4" />
              </Button>
            </div>
            {featuresFields.fields.map((item, index) => (
              <div key={item.id} className="space-y-4 rounded-md border p-4">
                <div className="flex items-center justify-between gap-2">
                  <div className="text-sm font-medium">{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.label", "Features")} {index + 1}</div>
                  <Button
                    type="button"
                    variant="ghost"
                    size="icon"
                    onClick={() => featuresFields.remove(index)}
                  >
                    <Trash2 className="size-4" />
                  </Button>
                </div>
                <div className="grid gap-4 md:grid-cols-2">
                  <FormField
                    control={form.control}
                    name={`features.${index}.featureName` as never}
                    rules={{ required: "Feature Name is required" }}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.featureName.label", "Feature Name")}</FormLabel>
                        <FormControl>
                          <Input
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.featureName.placeholder", "Enter Feature Name")}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name={`features.${index}.dataType` as never}
                    rules={{ required: "Data Type is required" }}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.dataType.label", "Data Type")}</FormLabel>
                        <FormControl>
                          <Input
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.dataType.placeholder", "Enter Data Type")}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name={`features.${index}.required` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.required.label", "Required")}</FormLabel>
                        <Select
                          value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                          onValueChange={(value) => field.onChange(value === "true")}
                        >
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.required.placeholder", "Select Required")} />
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
                  <FormField
                    control={form.control}
                    name={`features.${index}.nullable` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.nullable.label", "Nullable")}</FormLabel>
                        <Select
                          value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                          onValueChange={(value) => field.onChange(value === "true")}
                        >
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.nullable.placeholder", "Select Nullable")} />
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
                  <FormField
                    control={form.control}
                    name={`features.${index}.description` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.description.label", "Description")}</FormLabel>
                        <FormControl>
                          <Textarea
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.description.placeholder", "Enter Description")}
                            rows={8}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <ScalarArrayField
                    control={form.control}
                    name={`features.${index}.validationRules`}
                    label={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.validationRules.label", "Validation Rules")}
                    inputType={null}
                    itemDefaultValue={""}
                    options={undefined}
                  />
                  <FormField
                    control={form.control}
                    name={`features.${index}.defaultValue` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.defaultValue.label", "Default Value")}</FormLabel>
                        <FormControl>
                          <Input
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.defaultValue.placeholder", "Enter Default Value")}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name={`features.${index}.isIdentifier` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.isIdentifier.label", "Is Identifier")}</FormLabel>
                        <Select
                          value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                          onValueChange={(value) => field.onChange(value === "true")}
                        >
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.isIdentifier.placeholder", "Select Is Identifier")} />
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
                  <FormField
                    control={form.control}
                    name={`features.${index}.isSensitive` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.isSensitive.label", "Is Sensitive")}</FormLabel>
                        <Select
                          value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                          onValueChange={(value) => field.onChange(value === "true")}
                        >
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.isSensitive.placeholder", "Select Is Sensitive")} />
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
                  <FormField
                    control={form.control}
                    name={`features.${index}.encodingStrategy` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.encodingStrategy.label", "Encoding Strategy")}</FormLabel>
                        <FormControl>
                          <Input
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.encodingStrategy.placeholder", "Enter Encoding Strategy")}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <ScalarArrayField
                    control={form.control}
                    name={`features.${index}.featureTags`}
                    label={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.features.fields.featureTags.label", "Feature Tags")}
                    inputType={null}
                    itemDefaultValue={""}
                    options={undefined}
                  />
                </div>
              </div>
            ))}
          </div>
          <div className="space-y-4 rounded-md border p-4">
            <div className="flex items-center justify-between gap-2">
              <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.label", "Labels")}</FormLabel>
              <Button
                type="button"
                variant="outline"
                size="sm"
                onClick={() => labelsFields.append([{
  labelName: "",
  dataType: "",
  cardinality: undefined,
  classLabels: [""],
  isMultilabel: false,
  description: "",
  validationRules: [""],
  defaultValue: ""
}] as never)}
              >
                <Plus className="size-4" />
              </Button>
            </div>
            {labelsFields.fields.map((item, index) => (
              <div key={item.id} className="space-y-4 rounded-md border p-4">
                <div className="flex items-center justify-between gap-2">
                  <div className="text-sm font-medium">{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.label", "Labels")} {index + 1}</div>
                  <Button
                    type="button"
                    variant="ghost"
                    size="icon"
                    onClick={() => labelsFields.remove(index)}
                  >
                    <Trash2 className="size-4" />
                  </Button>
                </div>
                <div className="grid gap-4 md:grid-cols-2">
                  <FormField
                    control={form.control}
                    name={`labels.${index}.labelName` as never}
                    rules={{ required: "Label Name is required" }}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.labelName.label", "Label Name")}</FormLabel>
                        <FormControl>
                          <Input
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.labelName.placeholder", "Enter Label Name")}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name={`labels.${index}.dataType` as never}
                    rules={{ required: "Data Type is required" }}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.dataType.label", "Data Type")}</FormLabel>
                        <FormControl>
                          <Input
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.dataType.placeholder", "Enter Data Type")}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name={`labels.${index}.cardinality` as never}
                    rules={{ required: "Cardinality is required" }}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.cardinality.label", "Cardinality")}</FormLabel>
                        <FormControl>
                          <Input
                            type="number"
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.cardinality.placeholder", "Enter Cardinality")}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <ScalarArrayField
                    control={form.control}
                    name={`labels.${index}.classLabels`}
                    label={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.classLabels.label", "Class Labels")}
                    inputType={null}
                    itemDefaultValue={""}
                    options={undefined}
                  />
                  <FormField
                    control={form.control}
                    name={`labels.${index}.isMultilabel` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.isMultilabel.label", "Is Multilabel")}</FormLabel>
                        <Select
                          value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                          onValueChange={(value) => field.onChange(value === "true")}
                        >
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.isMultilabel.placeholder", "Select Is Multilabel")} />
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
                  <FormField
                    control={form.control}
                    name={`labels.${index}.description` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.description.label", "Description")}</FormLabel>
                        <FormControl>
                          <Textarea
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.description.placeholder", "Enter Description")}
                            rows={8}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <ScalarArrayField
                    control={form.control}
                    name={`labels.${index}.validationRules`}
                    label={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.validationRules.label", "Validation Rules")}
                    inputType={null}
                    itemDefaultValue={""}
                    options={undefined}
                  />
                  <FormField
                    control={form.control}
                    name={`labels.${index}.defaultValue` as never}
                    rules={{}}
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>{t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.defaultValue.label", "Default Value")}</FormLabel>
                        <FormControl>
                          <Input
                            {...field}
                            value={field.value ?? ""}
                            placeholder={t("resources.feature_schema_catalog.commands.defineFeatureSchema.fields.labels.fields.defaultValue.placeholder", "Enter Default Value")}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
              </div>
            ))}
          </div>
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
