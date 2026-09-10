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
import { UpdateTrainingRunConfigurationCommandSchema, type UpdateTrainingRunConfigurationCommandInput } from "@/contexts/domain/schemas";
import { ResourceMultiSelect, ResourceSelect } from "@/components/refine-ui/form/resource-select";

export const TrainingRunConfigurationCatalogUpdateTrainingRunConfiguration = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    configurationName: searchParams.get("configurationName") ?? undefined,
    federationId: searchParams.get("federationId") ?? undefined,
    featureSchemaId: searchParams.get("featureSchemaId") ?? undefined,
    initialModelId: searchParams.get("initialModelId") ?? undefined,
    strategyName: searchParams.get("strategyName") ?? undefined,
    aggregationAlgorithm: searchParams.get("aggregationAlgorithm") ?? undefined,
    maxRounds: (() => { const value = searchParams.get("maxRounds"); return value === null ? undefined : Number(value); })(),
    minimumNodesPerRound: (() => { const value = searchParams.get("minimumNodesPerRound"); return value === null ? undefined : Number(value); })(),
    roundTimeoutSeconds: (() => { const value = searchParams.get("roundTimeoutSeconds"); return value === null ? undefined : Number(value); })(),
    nodeResponseTimeoutSeconds: (() => { const value = searchParams.get("nodeResponseTimeoutSeconds"); return value === null ? undefined : Number(value); })(),
    localEpochs: (() => { const value = searchParams.get("localEpochs"); return value === null ? undefined : Number(value); })(),
    batchSize: (() => { const value = searchParams.get("batchSize"); return value === null ? undefined : Number(value); })(),
    learningRate: (() => { const value = searchParams.get("learningRate"); return value === null ? undefined : Number(value); })(),
    optimizer: searchParams.get("optimizer") ?? undefined,
    lossFunction: searchParams.get("lossFunction") ?? undefined,
    gradientClippingNorm: (() => { const value = searchParams.get("gradientClippingNorm"); return value === null ? undefined : Number(value); })(),
    secureAggregationRequired: (() => { const value = searchParams.get("secureAggregationRequired"); return value === null ? undefined : value === "true"; })(),
    minimumAccuracy: (() => { const value = searchParams.get("minimumAccuracy"); return value === null ? undefined : Number(value); })(),
    minimumFairnessScore: (() => { const value = searchParams.get("minimumFairnessScore"); return value === null ? undefined : Number(value); })(),
    updateReason: searchParams.get("updateReason") ?? undefined,
    trainingRunConfigurationId: searchParams.get("trainingRunConfigurationId") ?? undefined,
    federationName: searchParams.get("federationName") ?? undefined,
    featureDomain: searchParams.get("featureDomain") ?? undefined,
    featureSchemaVersion: searchParams.get("featureSchemaVersion") ?? undefined,
    initialModelName: searchParams.get("initialModelName") ?? undefined,
    initialModelVersion: searchParams.get("initialModelVersion") ?? undefined,
  } as unknown as Partial<UpdateTrainingRunConfigurationCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<UpdateTrainingRunConfigurationCommandInput, UpdateTrainingRunConfigurationCommandInput>({
    resource: "training_run_configuration_catalog",
    command: "updateTrainingRunConfiguration",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "training_run_configuration_catalog_read_model_entity",
      idField: "trainingRunConfigurationId",
      label: t("resources.training_run_configuration_catalog.label", "Training Run Configuration Catalog"),
      aggregateRoute: "trainingrunconfiguration",
      queryRoute: "trainingrunconfigurationcatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "training_run_configuration_catalog_read_model_entity",
      idField: "trainingRunConfigurationId",
      label: t("resources.training_run_configuration_catalog.label", "Training Run Configuration Catalog"),
      aggregateRoute: "trainingrunconfiguration",
      queryRoute: "trainingrunconfigurationcatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(UpdateTrainingRunConfigurationCommandSchema) as never,
    },
  });

  async function onSubmit(values: UpdateTrainingRunConfigurationCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/training-run-configuration-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.label", "Update Training Run Configuration")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("UpdateTrainingRunConfiguration validation failed", errors))} className="space-y-8">
          {defaultValues.trainingRunConfigurationId !== undefined && defaultValues.trainingRunConfigurationId !== null ? (
            <input type="hidden" {...form.register("trainingRunConfigurationId" as never)} />
          ) : null}
          {defaultValues.federationName !== undefined && defaultValues.federationName !== null ? (
            <input type="hidden" {...form.register("federationName" as never)} />
          ) : null}
          {defaultValues.featureDomain !== undefined && defaultValues.featureDomain !== null ? (
            <input type="hidden" {...form.register("featureDomain" as never)} />
          ) : null}
          {defaultValues.featureSchemaVersion !== undefined && defaultValues.featureSchemaVersion !== null ? (
            <input type="hidden" {...form.register("featureSchemaVersion" as never)} />
          ) : null}
          {defaultValues.initialModelName !== undefined && defaultValues.initialModelName !== null ? (
            <input type="hidden" {...form.register("initialModelName" as never)} />
          ) : null}
          {defaultValues.initialModelVersion !== undefined && defaultValues.initialModelVersion !== null ? (
            <input type="hidden" {...form.register("initialModelVersion" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="configurationName"
            rules={{ required: "Configuration Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.configurationName.label", "Configuration Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Configuration Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="federationId"
            rules={{ required: "Federation Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.federationId.label", "Federation Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="federation_overview"
                  dataProviderName="federation-learning-platform"
                  optionLabel="federationName"
                  optionValue="federationId"
                  value={field.value || ""}
                  onValueChange={(value, option) => {
                    field.onChange(value);
                    form.setValue(
                      "federationName" as never,
                      String(option?.record?.["federationName"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                  }}
                  placeholder={t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.federationId.placeholder", "Select Federation Id")}
                  meta={{
                    idField: "federationId",
                    label: t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.federationId.label", "Federation Overview"),
                    aggregateRoute: "federation",
                    queryRoute: "federationoverview",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="featureSchemaId"
            rules={{ required: "Feature Schema Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.featureSchemaId.label", "Feature Schema Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="feature_schema_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="featureDomain"
                  optionValue="featureSchemaId"
                  value={field.value || ""}
                  onValueChange={(value, option) => {
                    field.onChange(value);
                    form.setValue(
                      "featureDomain" as never,
                      String(option?.record?.["featureDomain"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                    form.setValue(
                      "featureSchemaVersion" as never,
                      String(option?.record?.["version"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                  }}
                  placeholder={t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.featureSchemaId.placeholder", "Select Feature Schema Id")}
                  meta={{
                    idField: "featureSchemaId",
                    label: t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.featureSchemaId.label", "Feature Schema Catalog"),
                    aggregateRoute: "featureschema",
                    queryRoute: "featureschemacatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="initialModelId"
            rules={{ required: "Initial Model Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.initialModelId.label", "Initial Model Id")}</FormLabel>
                <ResourceSelect
                  withFormControl
                  resource="model_artifact_catalog"
                  dataProviderName="federation-learning-platform"
                  optionLabel="modelName"
                  optionValue="modelId"
                  value={field.value || ""}
                  onValueChange={(value, option) => {
                    field.onChange(value);
                    form.setValue(
                      "initialModelName" as never,
                      String(option?.record?.["modelName"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                    form.setValue(
                      "initialModelVersion" as never,
                      String(option?.record?.["modelVersion"] ?? option?.label ?? "") as never,
                      { shouldDirty: true, shouldValidate: true },
                    );
                  }}
                  placeholder={t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.initialModelId.placeholder", "Select Initial Model Id")}
                  meta={{
                    idField: "modelId",
                    label: t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.initialModelId.label", "Model Artifact Catalog"),
                    aggregateRoute: "modelartifact",
                    queryRoute: "modelartifactcatalog",
                  }}
                />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="strategyName"
            rules={{ required: "Strategy Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.strategyName.label", "Strategy Name")}</FormLabel>
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
                  placeholder={t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.strategyName.placeholder", "Select Strategy Name")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"TRAINING_STRATEGY"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.strategyName.label", "Dictionary Value Catalog"),
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
            name="aggregationAlgorithm"
            rules={{ required: "Aggregation Algorithm is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.aggregationAlgorithm.label", "Aggregation Algorithm")}</FormLabel>
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
                  placeholder={t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.aggregationAlgorithm.placeholder", "Select Aggregation Algorithm")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"AGGREGATION_ALGORITHM"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.aggregationAlgorithm.label", "Dictionary Value Catalog"),
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
            name="maxRounds"
            rules={{ required: "Max Rounds is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.maxRounds.label", "Max Rounds")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Max Rounds"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="minimumNodesPerRound"
            rules={{ required: "Minimum Nodes Per Round is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.minimumNodesPerRound.label", "Minimum Nodes Per Round")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Minimum Nodes Per Round"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="roundTimeoutSeconds"
            rules={{ required: "Round Timeout Seconds is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.roundTimeoutSeconds.label", "Round Timeout Seconds")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Round Timeout Seconds"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="nodeResponseTimeoutSeconds"
            rules={{ required: "Node Response Timeout Seconds is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.nodeResponseTimeoutSeconds.label", "Node Response Timeout Seconds")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Node Response Timeout Seconds"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="localEpochs"
            rules={{ required: "Local Epochs is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.localEpochs.label", "Local Epochs")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Local Epochs"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="batchSize"
            rules={{ required: "Batch Size is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.batchSize.label", "Batch Size")}</FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Batch Size"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="learningRate"
            rules={{ required: "Learning Rate is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.learningRate.label", "Learning Rate")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Learning Rate"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="optimizer"
            rules={{ required: "Optimizer is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.optimizer.label", "Optimizer")}</FormLabel>
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
                  placeholder={t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.optimizer.placeholder", "Select Optimizer")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"TRAINING_OPTIMIZER"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.optimizer.label", "Dictionary Value Catalog"),
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
            name="lossFunction"
            rules={{ required: "Loss Function is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.lossFunction.label", "Loss Function")}</FormLabel>
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
                  placeholder={t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.lossFunction.placeholder", "Select Loss Function")}
                  filters={[{"field":"dictionaryCode","operator":"eq","value":"TRAINING_LOSS_FUNCTION"},{"field":"state","operator":"eq","value":"ACTIVE"}]}
                  sorters={[{"field":"displayOrder","order":"asc"}]}
                  pagination={{"currentPage":1,"pageSize":100,"mode":"server"}}
                  meta={{
                    idField: "dictionaryValueId",
                    label: t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.lossFunction.label", "Dictionary Value Catalog"),
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
            name="gradientClippingNorm"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.gradientClippingNorm.label", "Gradient Clipping Norm")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Gradient Clipping Norm"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="secureAggregationRequired"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.secureAggregationRequired.label", "Secure Aggregation Required")}</FormLabel>
                <Select
                  value={field.value === undefined || field.value === null ? undefined : String(field.value)}
                  onValueChange={(value) => field.onChange(value === "true")}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder={t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.secureAggregationRequired.placeholder", "Select Secure Aggregation Required")} />
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
          <FormField
            control={form.control}
            name="minimumAccuracy"
            rules={{ required: "Minimum Accuracy is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.minimumAccuracy.label", "Minimum Accuracy")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Minimum Accuracy"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="minimumFairnessScore"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.minimumFairnessScore.label", "Minimum Fairness Score")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Minimum Fairness Score"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="updateReason"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.training_run_configuration_catalog.commands.updateTrainingRunConfiguration.fields.updateReason.label", "Update Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Update Reason"}
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
