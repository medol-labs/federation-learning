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
import { RegisterRuntimeEngineProfileCommandSchema, type RegisterRuntimeEngineProfileCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const RuntimeEngineProfileCatalogRegisterRuntimeEngineProfile = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    profileName: searchParams.get("profileName") ?? undefined,
    pluginProfile: searchParams.get("pluginProfile") ?? undefined,
    runtimeEngineImage: searchParams.get("runtimeEngineImage") ?? undefined,
    imageDigest: searchParams.get("imageDigest") ?? undefined,
    supportedModelPluginsDescription: searchParams.get("supportedModelPluginsDescription") ?? undefined,
    supportedAggregationAlgorithmsDescription: searchParams.get("supportedAggregationAlgorithmsDescription") ?? undefined,
    active: (() => { const value = searchParams.get("active"); return value === null ? undefined : value === "true"; })(),
  } as unknown as Partial<RegisterRuntimeEngineProfileCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RegisterRuntimeEngineProfileCommandInput, RegisterRuntimeEngineProfileCommandInput>({
    resource: "runtime_engine_profile_catalog",
    command: "registerRuntimeEngineProfile",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_engine_profile_catalog_read_model_entity",
      idField: "runtimeEngineProfileId",
      label: t("resources.runtime_engine_profile_catalog.label", "Runtime Engine Profile Catalog"),
      aggregateRoute: "runtimeengineprofile",
      queryRoute: "runtimeengineprofilecatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "runtime_engine_profile_catalog_read_model_entity",
      idField: "runtimeEngineProfileId",
      label: t("resources.runtime_engine_profile_catalog.label", "Runtime Engine Profile Catalog"),
      aggregateRoute: "runtimeengineprofile",
      queryRoute: "runtimeengineprofilecatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RegisterRuntimeEngineProfileCommandSchema) as never,
    },
  });

  async function onSubmit(values: RegisterRuntimeEngineProfileCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/runtime-engine-profile-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.label", "Register Runtime Engine Profile")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RegisterRuntimeEngineProfile validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="profileName"
            rules={{ required: "Profile Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.profileName.label", "Profile Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Profile Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="pluginProfile"
            rules={{ required: "Plugin Profile is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.pluginProfile.label", "Plugin Profile")}</FormLabel>
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
                  placeholder={t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.pluginProfile.placeholder", "Select Plugin Profile")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"RUNTIME_ENGINE_PLUGIN_PROFILE"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.pluginProfile.label", "Dictionary Value Catalog"),
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
            name="runtimeEngineImage"
            rules={{ required: "Runtime Engine Image is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.runtimeEngineImage.label", "Runtime Engine Image")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Runtime Engine Image"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="imageDigest"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.imageDigest.label", "Image Digest")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Image Digest"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="supportedModelPluginsDescription"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.supportedModelPluginsDescription.label", "Supported Model Plugins Description")}</FormLabel>
                <FormControl>
                  <Textarea
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Supported Model Plugins Description"}
                    rows={8}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="supportedAggregationAlgorithmsDescription"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.supportedAggregationAlgorithmsDescription.label", "Supported Aggregation Algorithms Description")}</FormLabel>
                <FormControl>
                  <Textarea
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Supported Aggregation Algorithms Description"}
                    rows={8}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="active"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.active.label", "Active")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.runtime_engine_profile_catalog.commands.registerRuntimeEngineProfile.fields.active.placeholder", "Select Active")} />
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
