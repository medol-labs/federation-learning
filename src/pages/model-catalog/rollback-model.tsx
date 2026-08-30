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
import { RollbackModelCommandSchema, type RollbackModelCommandInput } from "@/domain/schemas";

export const ModelCatalogRollbackModel = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    previousModelId: searchParams.get("previousModelId") ?? undefined,
    modelId: searchParams.get("modelId") ?? undefined,
  } as Partial<RollbackModelCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RollbackModelCommandInput, RollbackModelCommandInput>({
    resource: "model_catalog",
    command: "rollbackModel",
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
      resolver: zodResolver(RollbackModelCommandSchema) as never,
    },
  });

  async function onSubmit(values: RollbackModelCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/model-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.model_catalog.commands.rollbackModel.label", "Rollback Model")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RollbackModel validation failed", errors))} className="space-y-8">
          {defaultValues.modelId !== undefined && defaultValues.modelId !== null ? (
            <input type="hidden" {...form.register("modelId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="previousModelId"
            rules={{ required: "Previous Model Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.rollbackModel.fields.previousModelId.label", "Previous Model Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Previous Model Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="rollbackReason"
            rules={{ required: "Rollback Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_catalog.commands.rollbackModel.fields.rollbackReason.label", "Rollback Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Rollback Reason"}
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
