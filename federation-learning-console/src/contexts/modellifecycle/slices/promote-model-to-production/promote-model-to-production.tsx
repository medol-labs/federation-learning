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
import { PromoteModelToProductionCommandSchema, type PromoteModelToProductionCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const ModelCatalogPromoteModelToProduction = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    releaseChannel: searchParams.get("releaseChannel") ?? undefined,
    productionStage: searchParams.get("productionStage") ?? undefined,
    modelId: searchParams.get("modelId") ?? undefined,
  } as unknown as Partial<PromoteModelToProductionCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<PromoteModelToProductionCommandInput, PromoteModelToProductionCommandInput>({
    resource: "model_catalog",
    command: "promoteModelToProduction",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "model_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_catalog.label", "Model Catalog"),
      aggregateRoute: "model",
      queryRoute: "modelcatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "model_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_catalog.label", "Model Catalog"),
      aggregateRoute: "model",
      queryRoute: "modelcatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(PromoteModelToProductionCommandSchema) as never,
    },
  });

  async function onSubmit(values: PromoteModelToProductionCommandInput) {
    const result = await runFormBehavior<PromoteModelToProductionCommandInput>(
      frontendComposition,
      "behavior:model-catalog:promoteModelToProduction",
      {
      ...defaultValues,
      ...values,
      } as PromoteModelToProductionCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/model-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.model_catalog.commands.promoteModelToProduction.label", "Promote Model To Production")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("PromoteModelToProduction validation failed", errors))} className="space-y-8">
          {defaultValues.modelId !== undefined && defaultValues.modelId !== null ? (
            <input type="hidden" {...form.register("modelId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="releaseChannel"
            rules={{ required: "Release Channel is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.promoteModelToProduction.fields.releaseChannel.label", "Release Channel")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.model_catalog.commands.promoteModelToProduction.fields.releaseChannel.placeholder", "Select Release Channel")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"MODEL_RELEASE_CHANNEL"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.model_catalog.commands.promoteModelToProduction.fields.releaseChannel.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="productionStage"
            rules={{ required: "Production Stage is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.promoteModelToProduction.fields.productionStage.label", "Production Stage")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="dictionary_value_catalog"
                  dataProviderName="federation-learning-support"
                  optionLabel="displayName"
                  optionValue="valueCode"
                  value={field.value || ""}
                  onValueChange={(value) => {
                    field.onChange(value);
                  }}
                  placeholder={t("resources.model_catalog.commands.promoteModelToProduction.fields.productionStage.placeholder", "Select Production Stage")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"MODEL_PRODUCTION_STAGE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.model_catalog.commands.promoteModelToProduction.fields.productionStage.label", "Dictionary Value Catalog"),
                    aggregateRoute: "dictionaryvalue",
                    queryRoute: "dictionaryvaluecatalog",
                    queryFields: ["dictionaryCode","active","state"],
                  }}
                />
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
