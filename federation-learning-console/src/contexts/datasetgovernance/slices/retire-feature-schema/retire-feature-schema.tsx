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
import { RetireFeatureSchemaCommandSchema, type RetireFeatureSchemaCommandInput } from "@/contexts/domain/schemas";

export const FeatureSchemaCatalogRetireFeatureSchema = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    featureDomain: searchParams.get("featureDomain") ?? undefined,
    version: searchParams.get("version") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
  } as unknown as Partial<RetireFeatureSchemaCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RetireFeatureSchemaCommandInput, RetireFeatureSchemaCommandInput>({
    resource: "feature_schema_catalog",
    command: "retireFeatureSchema",
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
      resolver: zodResolver(RetireFeatureSchemaCommandSchema) as never,
    },
  });

  async function onSubmit(values: RetireFeatureSchemaCommandInput) {
    const result = await runFormBehavior<RetireFeatureSchemaCommandInput>(
      frontendComposition,
      "behavior:feature-schema-catalog:retireFeatureSchema",
      {
      ...defaultValues,
      ...values,
      } as RetireFeatureSchemaCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/feature-schema-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.feature_schema_catalog.commands.retireFeatureSchema.label", "Retire Feature Schema")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RetireFeatureSchema validation failed", errors))} className="space-y-8">
          {defaultValues.featureDomain !== undefined && defaultValues.featureDomain !== null ? (
            <input type="hidden" {...form.register("featureDomain" as never)} />
          ) : null}
          {defaultValues.version !== undefined && defaultValues.version !== null ? (
            <input type="hidden" {...form.register("version" as never)} />
          ) : null}
          {defaultValues.featureSchemaId !== undefined && defaultValues.featureSchemaId !== null ? (
            <input type="hidden" {...form.register("featureSchemaId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="retirementReason"
            rules={{ required: "Retirement Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.feature_schema_catalog.commands.retireFeatureSchema.fields.retirementReason.label", "Retirement Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Retirement Reason"}
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
