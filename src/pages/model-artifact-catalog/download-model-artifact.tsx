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
import { DownloadModelArtifactCommandSchema, type DownloadModelArtifactCommandInput } from "@/domain/schemas";

export const ModelArtifactCatalogDownloadModelArtifact = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    modelId: searchParams.get("modelId") ?? undefined,
  } as unknown as Partial<DownloadModelArtifactCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<DownloadModelArtifactCommandInput, DownloadModelArtifactCommandInput>({
    resource: "model_artifact_catalog",
    command: "downloadModelArtifact",
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
      resolver: zodResolver(DownloadModelArtifactCommandSchema) as never,
    },
  });

  async function onSubmit(values: DownloadModelArtifactCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/model-artifact-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.model_artifact_catalog.commands.downloadModelArtifact.label", "Download Model Artifact")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("DownloadModelArtifact validation failed", errors))} className="space-y-8">
          {defaultValues.modelId !== undefined && defaultValues.modelId !== null ? (
            <input type="hidden" {...form.register("modelId" as never)} />
          ) : null}
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
