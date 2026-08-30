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
import { DeprecateFeatureSchemaCommandSchema, type DeprecateFeatureSchemaCommandInput } from "@/domain/schemas";

export const FeatureSchemaCatalogDeprecateFeatureSchema = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
  } as Partial<DeprecateFeatureSchemaCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<DeprecateFeatureSchemaCommandInput, DeprecateFeatureSchemaCommandInput>({
    resource: "feature_schema_catalog",
    command: "deprecateFeatureSchema",
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
      resolver: zodResolver(DeprecateFeatureSchemaCommandSchema) as never,
    },
  });

  async function onSubmit(values: DeprecateFeatureSchemaCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/feature-schema-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.feature_schema_catalog.commands.deprecateFeatureSchema.label", "Deprecate Feature Schema")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("DeprecateFeatureSchema validation failed", errors))} className="space-y-8">
          {defaultValues.featureSchemaId !== undefined && defaultValues.featureSchemaId !== null ? (
            <input type="hidden" {...form.register("featureSchemaId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="deprecationReason"
            rules={{ required: "Deprecation Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.feature_schema_catalog.commands.deprecateFeatureSchema.fields.deprecationReason.label", "Deprecation Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Deprecation Reason"}
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
