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
import { ApproveModelCommandSchema, type ApproveModelCommandInput } from "@/domain/schemas";


export const ModelVersionCatalogApproveModel = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    modelVersionId: searchParams.get("modelVersionId") ?? undefined,
  } as Partial<ApproveModelCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<ApproveModelCommandInput, ApproveModelCommandInput>({
    resource: "model_version_catalog",
    command: "approveModel",
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
      resolver: zodResolver(ApproveModelCommandSchema) as never,
    },
  });

  function onSubmit(values: ApproveModelCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.model_version_catalog.commands.approveModel.label", "Approve Model")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("ApproveModel validation failed", errors))} className="space-y-8">
          {defaultValues.modelVersionId !== undefined && defaultValues.modelVersionId !== null ? (
            <input type="hidden" {...form.register("modelVersionId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="approvalNote"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_version_catalog.commands.approveModel.fields.approvalNote.label", "Approval Note")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Approval Note"}
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
