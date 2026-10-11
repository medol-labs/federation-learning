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
import { RequestDataExportCommandSchema, type RequestDataExportCommandInput } from "@/contexts/domain/schemas";

export const DataExportJobCatalogRequestDataExport = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    dataExportJobId: searchParams.get("dataExportJobId") ?? undefined,
    resourceName: searchParams.get("resourceName") ?? undefined,
    criteriaJson: searchParams.get("criteriaJson") ?? undefined,
    sortJson: searchParams.get("sortJson") ?? undefined,
    columnsJson: searchParams.get("columnsJson") ?? undefined,
    requestedLocale: searchParams.get("requestedLocale") ?? undefined,
    requestedAt: searchParams.get("requestedAt") ?? undefined,
    snapshotUpperBound: searchParams.get("snapshotUpperBound") ?? undefined,
    requestHash: searchParams.get("requestHash") ?? undefined,
    fileName: searchParams.get("fileName") ?? undefined,
    status: searchParams.get("status") ?? undefined,
  } as unknown as Partial<RequestDataExportCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<RequestDataExportCommandInput, RequestDataExportCommandInput>({
    resource: "data_export_job_catalog",
    command: "requestDataExport",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-support",
    queryDataProviderName: "federation-learning-support",
    meta: {
      tableName: "data_export_job_catalog_read_model_entity",
      idField: "dataExportJobId",
      label: t("resources.data_export_job_catalog.label", "Data Export Job Catalog"),
      aggregateRoute: "dataexportjob",
      queryRoute: "dataexportjobcatalog",
      dataProviderName: "federation-learning-support",
    },
    queryMeta: {
      tableName: "data_export_job_catalog_read_model_entity",
      idField: "dataExportJobId",
      label: t("resources.data_export_job_catalog.label", "Data Export Job Catalog"),
      aggregateRoute: "dataexportjob",
      queryRoute: "dataexportjobcatalog",
      dataProviderName: "federation-learning-support",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(RequestDataExportCommandSchema) as never,
    },
  });

  async function onSubmit(values: RequestDataExportCommandInput) {
    const result = await runFormBehavior<RequestDataExportCommandInput>(
      frontendComposition,
      "behavior:data-export-job-catalog:requestDataExport",
      {
      ...defaultValues,
      ...values,
      } as RequestDataExportCommandInput,
      (payload) => onFinish(payload),
    );
    navigate("/data-export-job-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.data_export_job_catalog.commands.requestDataExport.label", "Request Data Export")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("RequestDataExport validation failed", errors))} className="space-y-8">
          <FormField
            control={form.control}
            name="dataExportJobId"
            rules={{ required: "Data Export Job Id is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.dataExportJobId.label", "Data Export Job Id")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Data Export Job Id"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="resourceName"
            rules={{ required: "Resource Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.resourceName.label", "Resource Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Resource Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="criteriaJson"
            rules={{ required: "Criteria Json is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.criteriaJson.label", "Criteria Json")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Criteria Json"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="sortJson"
            rules={{ required: "Sort Json is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.sortJson.label", "Sort Json")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Sort Json"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="columnsJson"
            rules={{ required: "Columns Json is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.columnsJson.label", "Columns Json")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Columns Json"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="requestedLocale"
            rules={{}}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.requestedLocale.label", "Requested Locale")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Requested Locale"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="requestedAt"
            rules={{ required: "Requested At is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.requestedAt.label", "Requested At")}</FormLabel>
                <FormControl>
                  <Input
                    type="datetime-local"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Requested At"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="snapshotUpperBound"
            rules={{ required: "Snapshot Upper Bound is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.snapshotUpperBound.label", "Snapshot Upper Bound")}</FormLabel>
                <FormControl>
                  <Input
                    type="datetime-local"
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Snapshot Upper Bound"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="requestHash"
            rules={{ required: "Request Hash is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.requestHash.label", "Request Hash")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Request Hash"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="fileName"
            rules={{ required: "File Name is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.fileName.label", "File Name")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter File Name"}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="status"
            rules={{ required: "Status is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.data_export_job_catalog.commands.requestDataExport.fields.status.label", "Status")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Status"}
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
