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
import { RollbackModelVersionCommandSchema, type RollbackModelVersionCommandInput } from "@/domain/schemas";


export const ModelVersionCatalogRollbackModelVersion = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    previousModelVersionId: searchParams.get("previousModelVersionId") ?? undefined,
    modelVersionId: searchParams.get("modelVersionId") ?? undefined,
  } as Partial<RollbackModelVersionCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RollbackModelVersionCommandInput, RollbackModelVersionCommandInput>({
    resource: "model_version_catalog",
    command: "rollbackModelVersion",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "model_version_catalog_read_model_entity",
      idField: "modelVersionId",
      label: t("resources.model_version_catalog.label", "Model Version Catalog"),
      aggregateRoute: "modelversion",
      queryRoute: "modelversioncatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "model_version_catalog_read_model_entity",
      idField: "modelVersionId",
      label: t("resources.model_version_catalog.label", "Model Version Catalog"),
      aggregateRoute: "modelversion",
      queryRoute: "modelversioncatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RollbackModelVersionCommandSchema) as never,
    },
  });

  function onSubmit(values: RollbackModelVersionCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.model_version_catalog.commands.rollbackModelVersion.label", "Rollback Model Version")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <input type="hidden" {...form.register("modelVersionId" as never)} />
          <FormField
            control={form.control}
            name="previousModelVersionId"
            rules={{ required: "Previous Model Version Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_version_catalog.commands.rollbackModelVersion.fields.previousModelVersionId.label", "Previous Model Version Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Previous Model Version Id"}
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
                <FormLabel>{t("resources.model_version_catalog.commands.rollbackModelVersion.fields.rollbackReason.label", "Rollback Reason")}</FormLabel>
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
