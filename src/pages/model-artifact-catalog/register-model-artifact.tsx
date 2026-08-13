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
import { RegisterModelArtifactCommandSchema, type RegisterModelArtifactCommandInput } from "@/domain/schemas";


export const ModelArtifactCatalogRegisterModelArtifact = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    modelName: searchParams.get("modelName") ?? undefined,
    modelVersion: searchParams.get("modelVersion") ?? undefined,
    sourceType: searchParams.get("sourceType") ?? undefined,
    modelFormat: searchParams.get("modelFormat") ?? undefined,
  } as Partial<RegisterModelArtifactCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterModelArtifactCommandInput, RegisterModelArtifactCommandInput>({
    resource: "model_artifact_catalog",
    command: "registerModelArtifact",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "model_artifact_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_artifact_catalog.label", "Model Artifact Catalog"),
      aggregateRoute: "modelartifact",
      queryRoute: "modelartifactcatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "model_artifact_catalog_read_model_entity",
      idField: "modelId",
      label: t("resources.model_artifact_catalog.label", "Model Artifact Catalog"),
      aggregateRoute: "modelartifact",
      queryRoute: "modelartifactcatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RegisterModelArtifactCommandSchema) as never,
    },
  });

  function onSubmit(values: RegisterModelArtifactCommandInput) {
    return onFinish({
      ...defaultValues,
      ...values,
    });
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.model_artifact_catalog.commands.registerModelArtifact.label", "Register Model Artifact")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterModelArtifact validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="modelName"
            rules={{ required: "Model Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelName.label", "Model Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelVersion"
            rules={{ required: "Model Version is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelVersion.label", "Model Version")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Version"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="sourceType"
            rules={{ required: "Source Type is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.sourceType.label", "Source Type")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Source Type"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="sourceLocation"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.sourceLocation.label", "Source Location")}</FormLabel>
                <FormControl>
                  <Input
                    type="file"
                    onChange={(event) => {
                      const file = event.target.files?.[0];
                      field.onChange(file ? `file://${file.name}` : "");
                    }}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelFormat"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelFormat.label", "Model Format")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Format"}
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
