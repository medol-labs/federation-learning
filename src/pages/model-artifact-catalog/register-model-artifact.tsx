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
    modelArtifactRef: searchParams.get("modelArtifactRef") ?? undefined,
    modelRepositoryRef: searchParams.get("modelRepositoryRef") ?? undefined,
    modelFormat: searchParams.get("modelFormat") ?? undefined,
    modelHash: searchParams.get("modelHash") ?? undefined,
    modelSignatureRef: searchParams.get("modelSignatureRef") ?? undefined,
    modelSizeBytes: (() => { const value = searchParams.get("modelSizeBytes"); return value === null ? undefined : Number(value); })(),
    sourceType: searchParams.get("sourceType") ?? undefined,
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
      idField: "modelVersionId",
      label: t("resources.model_artifact_catalog.label", "Model Artifact Catalog"),
      aggregateRoute: "modelartifact",
      queryRoute: "modelartifactcatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "model_artifact_catalog_read_model_entity",
      idField: "modelVersionId",
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
            name="modelArtifactRef"
            rules={{ required: "Model Artifact Ref is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelArtifactRef.label", "Model Artifact Ref")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Artifact Ref"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelRepositoryRef"
            rules={{ required: "Model Repository Ref is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelRepositoryRef.label", "Model Repository Ref")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Repository Ref"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelFormat"
            rules={{ required: "Model Format is required" }}
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
          <FormField
            control={form.control}
            name="modelHash"
            rules={{ required: "Model Hash is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelHash.label", "Model Hash")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Hash"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelSignatureRef"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelSignatureRef.label", "Model Signature Ref")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Signature Ref"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="modelSizeBytes"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.model_artifact_catalog.commands.registerModelArtifact.fields.modelSizeBytes.label", "Model Size Bytes")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Model Size Bytes"}
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
