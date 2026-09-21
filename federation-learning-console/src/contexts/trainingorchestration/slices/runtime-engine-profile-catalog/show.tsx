// Generated from config.json by the refine generator.
import { useShow, useTranslate } from "@refinedev/core";

import { frontendComposition } from "@/app/composition/composition.resolved";
import { ShowView, ShowViewHeader } from "@/components/refine-ui/views/show-view";
import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { renderFieldOverride } from "@/platform/composition";

const formatValue = (value: unknown, t: ReturnType<typeof useTranslate>) => {
  if (value === null || value === undefined || value === "") return "-";
  if (typeof value === "boolean") return value ? t("values.boolean.true", "True") : t("values.boolean.false", "False");
  return String(value);
};

export const RuntimeEngineProfileCatalogShow = () => {
  const t = useTranslate();
  const { result: record } = useShow({
    dataProviderName: "federation-learning-platform",
    meta: {
      tableName: "runtime_engine_profile_catalog_read_model_entity",
      idField: "runtimeEngineProfileId",
      label: t("resources.runtime_engine_profile_catalog.label", "Runtime Engine Profile Catalog"),
      aggregateRoute: "runtimeengineprofile",
      queryRoute: "runtimeengineprofilecatalog",
      dataProviderName: "federation-learning-platform",
    },
  });

  return (
    <ShowView>
      <ShowViewHeader />
      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>{record?.runtimeEngineProfileId ?? t("resources.runtime_engine_profile_catalog.label", "Runtime Engine Profile Catalog")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.runtimeEngineProfileId.label", "Runtime Engine Profile Id")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:runtimeEngineProfileId", { value: record?.runtimeEngineProfileId, record, resource: "runtime-engine-profile-catalog", field: "runtimeEngineProfileId", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineProfileId, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.profileName.label", "Profile Name")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:profileName", { value: record?.profileName, record, resource: "runtime-engine-profile-catalog", field: "profileName", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.profileName, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.pluginProfile.label", "Plugin Profile")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:pluginProfile", { value: record?.pluginProfile, record, resource: "runtime-engine-profile-catalog", field: "pluginProfile", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.pluginProfile, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.runtimeEngineImage.label", "Runtime Engine Image")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:runtimeEngineImage", { value: record?.runtimeEngineImage, record, resource: "runtime-engine-profile-catalog", field: "runtimeEngineImage", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.runtimeEngineImage, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.imageDigest.label", "Image Digest")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:imageDigest", { value: record?.imageDigest, record, resource: "runtime-engine-profile-catalog", field: "imageDigest", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.imageDigest, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.supportedModelPluginsDescription.label", "Supported Model Plugins Description")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:supportedModelPluginsDescription", { value: record?.supportedModelPluginsDescription, record, resource: "runtime-engine-profile-catalog", field: "supportedModelPluginsDescription", view: "display" }) ?? <CopyableText value={record?.supportedModelPluginsDescription} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.supportedAggregationAlgorithmsDescription.label", "Supported Aggregation Algorithms Description")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:supportedAggregationAlgorithmsDescription", { value: record?.supportedAggregationAlgorithmsDescription, record, resource: "runtime-engine-profile-catalog", field: "supportedAggregationAlgorithmsDescription", view: "display" }) ?? <CopyableText value={record?.supportedAggregationAlgorithmsDescription} />}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.active.label", "Active")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:active", { value: record?.active, record, resource: "runtime-engine-profile-catalog", field: "active", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.active, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.state.label", "State")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:state", { value: record?.state, record, resource: "runtime-engine-profile-catalog", field: "state", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.state, t)}</p>}
            </div>
            <Separator />
            <div>
              <h4 className="mb-2 text-sm font-medium">{t("resources.runtime_engine_profile_catalog.fields.registeredAt.label", "Registered At")}</h4>
              {renderFieldOverride(frontendComposition, "field:runtime-engine-profile-catalog:display:registeredAt", { value: record?.registeredAt, record, resource: "runtime-engine-profile-catalog", field: "registeredAt", view: "display" }) ?? <p className="text-sm text-muted-foreground">{formatValue(record?.registeredAt, t)}</p>}
            </div>
            <Separator />
          </CardContent>
        </Card>
      </div>
    </ShowView>
  );
};
