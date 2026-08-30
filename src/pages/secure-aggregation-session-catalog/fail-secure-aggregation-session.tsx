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
import { FailSecureAggregationSessionCommandSchema, type FailSecureAggregationSessionCommandInput } from "@/domain/schemas";

export const SecureAggregationSessionCatalogFailSecureAggregationSession = () => {
  const t = useTranslate();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const { id } = useParsed();
  const defaultValues = {
    failureReason: searchParams.get("failureReason") ?? undefined,
    secureAggregationSessionId: searchParams.get("secureAggregationSessionId") ?? undefined,
  } as unknown as Partial<FailSecureAggregationSessionCommandInput>;

  const { refineCore: { onFinish }, ...form } = useCommandForm<FailSecureAggregationSessionCommandInput, FailSecureAggregationSessionCommandInput>({
    resource: "secure_aggregation_session_catalog",
    command: "failSecureAggregationSession",
    aggregateId: id?.toString(),
    redirect: "list",
    dataProviderName: "federation-learning-platform",
    queryDataProviderName: "federation-learning-platform",
    meta: {
      tableName: "secure_aggregation_session_catalog_read_model_entity",
      idField: "secureAggregationSessionId",
      label: t("resources.secure_aggregation_session_catalog.label", "Secure Aggregation Session Catalog"),
      aggregateRoute: "secureaggregationsession",
      queryRoute: "secureaggregationsessioncatalog",
      dataProviderName: "federation-learning-platform",
    },
    queryMeta: {
      tableName: "secure_aggregation_session_catalog_read_model_entity",
      idField: "secureAggregationSessionId",
      label: t("resources.secure_aggregation_session_catalog.label", "Secure Aggregation Session Catalog"),
      aggregateRoute: "secureaggregationsession",
      queryRoute: "secureaggregationsessioncatalog",
      dataProviderName: "federation-learning-platform",
    },
    formProps: {
      defaultValues,
      resolver: zodResolver(FailSecureAggregationSessionCommandSchema) as never,
    },
  });

  async function onSubmit(values: FailSecureAggregationSessionCommandInput) {
    const result = await onFinish({
      ...defaultValues,
      ...values,
    });
    navigate("/secure-aggregation-session-catalog");
    return result;
  }

  return (
    <CreateView>
      <CreateViewHeader title={t("resources.secure_aggregation_session_catalog.commands.failSecureAggregationSession.label", "Fail Secure Aggregation Session")} />
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit, (errors) => console.error("FailSecureAggregationSession validation failed", errors))} className="space-y-8">
          {defaultValues.secureAggregationSessionId !== undefined && defaultValues.secureAggregationSessionId !== null ? (
            <input type="hidden" {...form.register("secureAggregationSessionId" as never)} />
          ) : null}
          <FormField
            control={form.control}
            name="failureReason"
            rules={{ required: "Failure Reason is required" }}
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resources.secure_aggregation_session_catalog.commands.failSecureAggregationSession.fields.failureReason.label", "Failure Reason")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    value={field.value || ""}
                    placeholder={"Enter Failure Reason"}
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
