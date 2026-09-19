// Generated from config.json by the refine generator.
import { Route } from "react-router";

import { resolvePageOverride } from "@/domain/page-overrides";
import {
  AuditRecordLogList,
  AuditRecordLogShow,
} from "./runtimemonitoring/read-models/audit-record-log";
import {
  CurrentRecommendedFeatureSchemaCatalogList,
  CurrentRecommendedFeatureSchemaCatalogShow,
} from "./datasetgovernance/read-models/current-recommended-feature-schema-catalog";
import {
  DictionaryCatalogList,
  DictionaryCatalogShow,
  DictionaryCatalogRegisterDictionary,
  DictionaryCatalogUpdateDictionary,
  DictionaryCatalogArchiveDictionary,
  DictionaryCatalogAddDictionaryValue,
} from "./dictionarymaintenance/read-models/dictionary-catalog";
import {
  DictionaryValueCatalogList,
  DictionaryValueCatalogShow,
  DictionaryValueCatalogAddDictionaryValue,
  DictionaryValueCatalogDisableDictionaryValue,
  DictionaryValueCatalogEnableDictionaryValue,
} from "./dictionarymaintenance/read-models/dictionary-value-catalog";
import {
  FeatureSchemaCatalogList,
  FeatureSchemaCatalogShow,
  FeatureSchemaCatalogDefineFeatureSchema,
  FeatureSchemaCatalogPublishFeatureSchema,
  FeatureSchemaCatalogDeprecateFeatureSchema,
  FeatureSchemaCatalogRetireFeatureSchema,
  FeatureSchemaCatalogSupersedeFeatureSchemaVersion,
  FeatureSchemaCatalogMarkCurrentRecommendedFeatureSchemaVersion,
} from "./datasetgovernance/read-models/feature-schema-catalog";
import {
  FederationMembershipDirectoryList,
  FederationMembershipDirectoryShow,
  FederationMembershipDirectoryInviteParticipant,
  FederationMembershipDirectoryRemoveParticipant,
  FederationMembershipDirectoryApproveParticipant,
  FederationMembershipDirectoryRejectParticipant,
  FederationMembershipDirectoryRevokeParticipantInvitation,
  FederationMembershipDirectorySuspendParticipant,
  FederationMembershipDirectoryActivateFederation,
} from "./federationmanagement/read-models/federation-membership-directory";
import {
  FederationOverviewList,
  FederationOverviewShow,
  FederationOverviewCreateFederation,
  FederationOverviewRemoveParticipant,
  FederationOverviewActivateFederation,
  FederationOverviewSuspendFederation,
  FederationOverviewReactivateFederation,
  FederationOverviewInviteParticipant,
  FederationOverviewApproveParticipant,
  FederationOverviewRejectParticipant,
  FederationOverviewRevokeParticipantInvitation,
  FederationOverviewSuspendParticipant,
} from "./federationmanagement/read-models/federation-overview";
import {
  ModelArtifactCatalogList,
  ModelArtifactCatalogShow,
  ModelArtifactCatalogRegisterModelArtifact,
  ModelArtifactCatalogDownloadModelArtifact,
} from "./modelrepository/read-models/model-artifact-catalog";
import {
  ModelCatalogList,
  ModelCatalogShow,
  ModelCatalogRecordModelEvaluationPackage,
  ModelCatalogApproveModel,
  ModelCatalogPromoteModelToProduction,
  ModelCatalogRollbackModel,
  ModelCatalogRetireModel,
} from "./modellifecycle/read-models/model-catalog";
import {
  OrganizationDirectoryList,
  OrganizationDirectoryShow,
  OrganizationDirectoryRegisterOrganization,
  OrganizationDirectoryActivateOrganization,
  OrganizationDirectoryDeactivateOrganization,
  OrganizationDirectoryCreateRuntimeInstallationPlan,
  OrganizationDirectoryReactivateOrganization,
} from "./organizationmanagement/read-models/organization-directory";
import {
  PermissionCatalogList,
  PermissionCatalogShow,
  PermissionCatalogRegisterPermission,
} from "./identityaccessmanagement/read-models/permission-catalog";
import {
  RoleCatalogList,
  RoleCatalogShow,
  RoleCatalogRegisterRole,
  RoleCatalogGrantPermissionToRole,
} from "./identityaccessmanagement/read-models/role-catalog";
import {
  RolePermissionGrantCatalogList,
  RolePermissionGrantCatalogShow,
} from "./identityaccessmanagement/read-models/role-permission-grant-catalog";
import {
  RuntimeAgentEndpointCatalogList,
  RuntimeAgentEndpointCatalogShow,
  RuntimeAgentEndpointCatalogRecordRuntimeConnectionEstablished,
} from "./runtimeprovisioning/read-models/runtime-agent-endpoint-catalog";
import {
  RuntimeCapabilityCatalogList,
  RuntimeCapabilityCatalogShow,
} from "./runtimegovernance/read-models/runtime-capability-catalog";
import {
  RuntimeDatasetMetadataCatalogList,
  RuntimeDatasetMetadataCatalogShow,
} from "./datasetgovernance/read-models/runtime-dataset-metadata-catalog";
import {
  RuntimeEngineProfileCatalogList,
  RuntimeEngineProfileCatalogShow,
  RuntimeEngineProfileCatalogRegisterRuntimeEngineProfile,
} from "./trainingorchestration/read-models/runtime-engine-profile-catalog";
import {
  RuntimeHealthDashboardList,
  RuntimeHealthDashboardShow,
} from "./runtimemonitoring/read-models/runtime-health-dashboard";
import {
  RuntimeIdentityCatalogList,
  RuntimeIdentityCatalogShow,
  RuntimeIdentityCatalogRevokeRuntimeIdentity,
} from "./runtimegovernance/read-models/runtime-identity-catalog";
import {
  RuntimeInfrastructureAccessViewList,
  RuntimeInfrastructureAccessViewShow,
  RuntimeInfrastructureAccessViewRegisterRuntimeInfrastructure,
  RuntimeInfrastructureAccessViewConfirmRuntimeInfrastructurePrepared,
} from "./runtimeprovisioning/read-models/runtime-infrastructure-access-view";
import {
  RuntimeInfrastructurePackageCatalogList,
  RuntimeInfrastructurePackageCatalogShow,
  RuntimeInfrastructurePackageCatalogRegisterRuntimeInfrastructurePackage,
} from "./runtimeprovisioning/read-models/runtime-infrastructure-package-catalog";
import {
  RuntimeInstallationGuideList,
  RuntimeInstallationGuideShow,
  RuntimeInstallationGuideConfirmRuntimeInfrastructurePrepared,
  RuntimeInstallationGuideRegisterRuntimeInfrastructure,
} from "./runtimeprovisioning/read-models/runtime-installation-guide";
import {
  RuntimeInstallationPlanCatalogList,
  RuntimeInstallationPlanCatalogShow,
  RuntimeInstallationPlanCatalogCreateRuntimeInstallationPlan,
  RuntimeInstallationPlanCatalogRetryRuntimeInfrastructureVerification,
  RuntimeInstallationPlanCatalogRetryRuntimeAgentDeployment,
  RuntimeInstallationPlanCatalogRegisterRuntimeInfrastructure,
  RuntimeInstallationPlanCatalogConfirmRuntimeInfrastructurePrepared,
} from "./runtimeprovisioning/read-models/runtime-installation-plan-catalog";
import {
  RuntimeNodeInventoryViewList,
  RuntimeNodeInventoryViewShow,
} from "./runtimemonitoring/read-models/runtime-node-inventory-view";
import {
  RuntimeNodeResourceLatestList,
  RuntimeNodeResourceLatestShow,
} from "./runtimemonitoring/read-models/runtime-node-resource-latest";
import {
  RuntimeTelemetryLatestList,
  RuntimeTelemetryLatestShow,
} from "./runtimemonitoring/read-models/runtime-telemetry-latest";
import {
  SecureAggregationSessionCatalogList,
  SecureAggregationSessionCatalogShow,
  SecureAggregationSessionCatalogFailSecureAggregationSession,
} from "./secureaggregation/read-models/secure-aggregation-session-catalog";
import {
  ServiceAccountApiTokenCatalogList,
  ServiceAccountApiTokenCatalogShow,
  ServiceAccountApiTokenCatalogIssueServiceAccountApiToken,
} from "./identityaccessmanagement/read-models/service-account-api-token-catalog";
import {
  TrainingAlertCatalogList,
  TrainingAlertCatalogShow,
  TrainingAlertCatalogAcknowledgeTrainingAlert,
  TrainingAlertCatalogResolveTrainingAlert,
} from "./runtimemonitoring/read-models/training-alert-catalog";
import {
  TrainingJobDashboardList,
  TrainingJobDashboardShow,
  TrainingJobDashboardCreateTrainingJob,
  TrainingJobDashboardCancelTrainingJob,
  TrainingJobDashboardSubmitTrainingJob,
  TrainingJobDashboardPauseTrainingJob,
  TrainingJobDashboardResumeTrainingJob,
  TrainingJobDashboardRetryTrainingRoundParticipantSelection,
} from "./trainingorchestration/read-models/training-job-dashboard";
import {
  TrainingParticipantEligibilityList,
  TrainingParticipantEligibilityShow,
  TrainingParticipantEligibilitySubmitTrainingJob,
} from "./trainingorchestration/read-models/training-participant-eligibility";
import {
  TrainingRoundProgressList,
  TrainingRoundProgressShow,
  TrainingRoundProgressCancelTrainingJob,
  TrainingRoundProgressSubmitModelUpdateSubmission,
  TrainingRoundProgressSubmitTrainingJob,
  TrainingRoundProgressRetryTrainingRoundParticipantSelection,
  TrainingRoundProgressPauseTrainingJob,
} from "./trainingorchestration/read-models/training-round-progress";
import {
  TrainingRunConfigurationCatalogList,
  TrainingRunConfigurationCatalogShow,
  TrainingRunConfigurationCatalogDefineTrainingRunConfiguration,
  TrainingRunConfigurationCatalogUpdateTrainingRunConfiguration,
  TrainingRunConfigurationCatalogCreateTrainingJob,
} from "./trainingorchestration/read-models/training-run-configuration-catalog";
import {
  UploadedFileCatalogList,
  UploadedFileCatalogShow,
  UploadedFileCatalogUploadFile,
  UploadedFileCatalogMarkFileReferenced,
  UploadedFileCatalogDiscardFile,
  UploadedFileCatalogDownloadFile,
} from "./fileupload/read-models/uploaded-file-catalog";
import {
  UserAccountCatalogList,
  UserAccountCatalogShow,
  UserAccountCatalogRegisterUserAccount,
  UserAccountCatalogGenerateUserAccountLoginPassword,
  UserAccountCatalogDeactivateUserAccount,
  UserAccountCatalogAssignRoleToUser,
} from "./identityaccessmanagement/read-models/user-account-catalog";
import {
  UserOrganizationMembershipDirectoryList,
  UserOrganizationMembershipDirectoryShow,
  UserOrganizationMembershipDirectoryBindUserAccountToOrganization,
} from "./organizationmanagement/read-models/user-organization-membership-directory";
import {
  UserRoleAssignmentCatalogList,
  UserRoleAssignmentCatalogShow,
} from "./identityaccessmanagement/read-models/user-role-assignment-catalog";

export const contextRoutes = (
  <>
    <Route path="/audit-record-log">
      <Route index element={resolvePageOverride("audit-record-log", "list", <AuditRecordLogList />)} />
      <Route path="show/:id" element={resolvePageOverride("audit-record-log", "show", <AuditRecordLogShow />)} />
    </Route>
    <Route path="/current-recommended-feature-schema-catalog">
      <Route index element={resolvePageOverride("current-recommended-feature-schema-catalog", "list", <CurrentRecommendedFeatureSchemaCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("current-recommended-feature-schema-catalog", "show", <CurrentRecommendedFeatureSchemaCatalogShow />)} />
    </Route>
    <Route path="/dictionary-catalog">
      <Route index element={resolvePageOverride("dictionary-catalog", "list", <DictionaryCatalogList />)} />
      <Route path="command/register-dictionary" element={resolvePageOverride("dictionary-catalog", "registerDictionary", <DictionaryCatalogRegisterDictionary />)} />
      <Route path="edit/:id" element={resolvePageOverride("dictionary-catalog", "edit", <DictionaryCatalogUpdateDictionary />)} />
      <Route path="show/:id" element={resolvePageOverride("dictionary-catalog", "show", <DictionaryCatalogShow />)} />
      <Route path=":id/command/archive-dictionary" element={resolvePageOverride("dictionary-catalog", "archiveDictionary", <DictionaryCatalogArchiveDictionary />)} />
      <Route path=":id/command/add-dictionary-value" element={resolvePageOverride("dictionary-catalog", "addDictionaryValue", <DictionaryCatalogAddDictionaryValue />)} />
    </Route>
    <Route path="/dictionary-value-catalog">
      <Route index element={resolvePageOverride("dictionary-value-catalog", "list", <DictionaryValueCatalogList />)} />
      <Route path="command/add-dictionary-value" element={resolvePageOverride("dictionary-value-catalog", "addDictionaryValue", <DictionaryValueCatalogAddDictionaryValue />)} />
      <Route path="show/:id" element={resolvePageOverride("dictionary-value-catalog", "show", <DictionaryValueCatalogShow />)} />
      <Route path=":id/command/disable-dictionary-value" element={resolvePageOverride("dictionary-value-catalog", "disableDictionaryValue", <DictionaryValueCatalogDisableDictionaryValue />)} />
      <Route path=":id/command/enable-dictionary-value" element={resolvePageOverride("dictionary-value-catalog", "enableDictionaryValue", <DictionaryValueCatalogEnableDictionaryValue />)} />
    </Route>
    <Route path="/feature-schema-catalog">
      <Route index element={resolvePageOverride("feature-schema-catalog", "list", <FeatureSchemaCatalogList />)} />
      <Route path="command/define-feature-schema" element={resolvePageOverride("feature-schema-catalog", "defineFeatureSchema", <FeatureSchemaCatalogDefineFeatureSchema />)} />
      <Route path="show/:id" element={resolvePageOverride("feature-schema-catalog", "show", <FeatureSchemaCatalogShow />)} />
      <Route path=":id/command/publish-feature-schema" element={resolvePageOverride("feature-schema-catalog", "publishFeatureSchema", <FeatureSchemaCatalogPublishFeatureSchema />)} />
      <Route path=":id/command/deprecate-feature-schema" element={resolvePageOverride("feature-schema-catalog", "deprecateFeatureSchema", <FeatureSchemaCatalogDeprecateFeatureSchema />)} />
      <Route path=":id/command/retire-feature-schema" element={resolvePageOverride("feature-schema-catalog", "retireFeatureSchema", <FeatureSchemaCatalogRetireFeatureSchema />)} />
      <Route path=":id/command/supersede-feature-schema-version" element={resolvePageOverride("feature-schema-catalog", "supersedeFeatureSchemaVersion", <FeatureSchemaCatalogSupersedeFeatureSchemaVersion />)} />
      <Route path=":id/command/mark-current-recommended-feature-schema-version" element={resolvePageOverride("feature-schema-catalog", "markCurrentRecommendedFeatureSchemaVersion", <FeatureSchemaCatalogMarkCurrentRecommendedFeatureSchemaVersion />)} />
    </Route>
    <Route path="/federation-membership-directory">
      <Route index element={resolvePageOverride("federation-membership-directory", "list", <FederationMembershipDirectoryList />)} />
      <Route path="command/invite-participant" element={resolvePageOverride("federation-membership-directory", "inviteParticipant", <FederationMembershipDirectoryInviteParticipant />)} />
      <Route path="show/:id" element={resolvePageOverride("federation-membership-directory", "show", <FederationMembershipDirectoryShow />)} />
      <Route path=":id/command/remove-participant" element={resolvePageOverride("federation-membership-directory", "removeParticipant", <FederationMembershipDirectoryRemoveParticipant />)} />
      <Route path=":id/command/approve-participant" element={resolvePageOverride("federation-membership-directory", "approveParticipant", <FederationMembershipDirectoryApproveParticipant />)} />
      <Route path=":id/command/reject-participant" element={resolvePageOverride("federation-membership-directory", "rejectParticipant", <FederationMembershipDirectoryRejectParticipant />)} />
      <Route path=":id/command/revoke-participant-invitation" element={resolvePageOverride("federation-membership-directory", "revokeParticipantInvitation", <FederationMembershipDirectoryRevokeParticipantInvitation />)} />
      <Route path=":id/command/suspend-participant" element={resolvePageOverride("federation-membership-directory", "suspendParticipant", <FederationMembershipDirectorySuspendParticipant />)} />
      <Route path=":id/command/activate-federation" element={resolvePageOverride("federation-membership-directory", "activateFederation", <FederationMembershipDirectoryActivateFederation />)} />
    </Route>
    <Route path="/federation-overview">
      <Route index element={resolvePageOverride("federation-overview", "list", <FederationOverviewList />)} />
      <Route path="command/create-federation" element={resolvePageOverride("federation-overview", "createFederation", <FederationOverviewCreateFederation />)} />
      <Route path="show/:id" element={resolvePageOverride("federation-overview", "show", <FederationOverviewShow />)} />
      <Route path=":id/command/remove-participant" element={resolvePageOverride("federation-overview", "removeParticipant", <FederationOverviewRemoveParticipant />)} />
      <Route path=":id/command/activate-federation" element={resolvePageOverride("federation-overview", "activateFederation", <FederationOverviewActivateFederation />)} />
      <Route path=":id/command/suspend-federation" element={resolvePageOverride("federation-overview", "suspendFederation", <FederationOverviewSuspendFederation />)} />
      <Route path=":id/command/reactivate-federation" element={resolvePageOverride("federation-overview", "reactivateFederation", <FederationOverviewReactivateFederation />)} />
      <Route path=":id/command/invite-participant" element={resolvePageOverride("federation-overview", "inviteParticipant", <FederationOverviewInviteParticipant />)} />
      <Route path=":id/command/approve-participant" element={resolvePageOverride("federation-overview", "approveParticipant", <FederationOverviewApproveParticipant />)} />
      <Route path=":id/command/reject-participant" element={resolvePageOverride("federation-overview", "rejectParticipant", <FederationOverviewRejectParticipant />)} />
      <Route path=":id/command/revoke-participant-invitation" element={resolvePageOverride("federation-overview", "revokeParticipantInvitation", <FederationOverviewRevokeParticipantInvitation />)} />
      <Route path=":id/command/suspend-participant" element={resolvePageOverride("federation-overview", "suspendParticipant", <FederationOverviewSuspendParticipant />)} />
    </Route>
    <Route path="/model-artifact-catalog">
      <Route index element={resolvePageOverride("model-artifact-catalog", "list", <ModelArtifactCatalogList />)} />
      <Route path="command/register-model-artifact" element={resolvePageOverride("model-artifact-catalog", "registerModelArtifact", <ModelArtifactCatalogRegisterModelArtifact />)} />
      <Route path="show/:id" element={resolvePageOverride("model-artifact-catalog", "show", <ModelArtifactCatalogShow />)} />
      <Route path=":id/command/download-model-artifact" element={resolvePageOverride("model-artifact-catalog", "downloadModelArtifact", <ModelArtifactCatalogDownloadModelArtifact />)} />
    </Route>
    <Route path="/model-catalog">
      <Route index element={resolvePageOverride("model-catalog", "list", <ModelCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("model-catalog", "show", <ModelCatalogShow />)} />
      <Route path=":id/command/record-model-evaluation-package" element={resolvePageOverride("model-catalog", "recordModelEvaluationPackage", <ModelCatalogRecordModelEvaluationPackage />)} />
      <Route path=":id/command/approve-model" element={resolvePageOverride("model-catalog", "approveModel", <ModelCatalogApproveModel />)} />
      <Route path=":id/command/promote-model-to-production" element={resolvePageOverride("model-catalog", "promoteModelToProduction", <ModelCatalogPromoteModelToProduction />)} />
      <Route path=":id/command/rollback-model" element={resolvePageOverride("model-catalog", "rollbackModel", <ModelCatalogRollbackModel />)} />
      <Route path=":id/command/retire-model" element={resolvePageOverride("model-catalog", "retireModel", <ModelCatalogRetireModel />)} />
    </Route>
    <Route path="/organization-directory">
      <Route index element={resolvePageOverride("organization-directory", "list", <OrganizationDirectoryList />)} />
      <Route path="command/register-organization" element={resolvePageOverride("organization-directory", "registerOrganization", <OrganizationDirectoryRegisterOrganization />)} />
      <Route path="show/:id" element={resolvePageOverride("organization-directory", "show", <OrganizationDirectoryShow />)} />
      <Route path=":id/command/activate-organization" element={resolvePageOverride("organization-directory", "activateOrganization", <OrganizationDirectoryActivateOrganization />)} />
      <Route path=":id/command/deactivate-organization" element={resolvePageOverride("organization-directory", "deactivateOrganization", <OrganizationDirectoryDeactivateOrganization />)} />
      <Route path=":id/command/create-runtime-installation-plan" element={resolvePageOverride("organization-directory", "createRuntimeInstallationPlan", <OrganizationDirectoryCreateRuntimeInstallationPlan />)} />
      <Route path=":id/command/reactivate-organization" element={resolvePageOverride("organization-directory", "reactivateOrganization", <OrganizationDirectoryReactivateOrganization />)} />
    </Route>
    <Route path="/permission-catalog">
      <Route index element={resolvePageOverride("permission-catalog", "list", <PermissionCatalogList />)} />
      <Route path="command/register-permission" element={resolvePageOverride("permission-catalog", "registerPermission", <PermissionCatalogRegisterPermission />)} />
      <Route path="show/:id" element={resolvePageOverride("permission-catalog", "show", <PermissionCatalogShow />)} />
    </Route>
    <Route path="/role-catalog">
      <Route index element={resolvePageOverride("role-catalog", "list", <RoleCatalogList />)} />
      <Route path="command/register-role" element={resolvePageOverride("role-catalog", "registerRole", <RoleCatalogRegisterRole />)} />
      <Route path="show/:id" element={resolvePageOverride("role-catalog", "show", <RoleCatalogShow />)} />
      <Route path=":id/command/grant-permission-to-role" element={resolvePageOverride("role-catalog", "grantPermissionToRole", <RoleCatalogGrantPermissionToRole />)} />
    </Route>
    <Route path="/role-permission-grant-catalog">
      <Route index element={resolvePageOverride("role-permission-grant-catalog", "list", <RolePermissionGrantCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("role-permission-grant-catalog", "show", <RolePermissionGrantCatalogShow />)} />
    </Route>
    <Route path="/runtime-agent-endpoint-catalog">
      <Route index element={resolvePageOverride("runtime-agent-endpoint-catalog", "list", <RuntimeAgentEndpointCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-agent-endpoint-catalog", "show", <RuntimeAgentEndpointCatalogShow />)} />
      <Route path=":id/command/record-runtime-connection-established" element={resolvePageOverride("runtime-agent-endpoint-catalog", "recordRuntimeConnectionEstablished", <RuntimeAgentEndpointCatalogRecordRuntimeConnectionEstablished />)} />
    </Route>
    <Route path="/runtime-capability-catalog">
      <Route index element={resolvePageOverride("runtime-capability-catalog", "list", <RuntimeCapabilityCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-capability-catalog", "show", <RuntimeCapabilityCatalogShow />)} />
    </Route>
    <Route path="/runtime-dataset-metadata-catalog">
      <Route index element={resolvePageOverride("runtime-dataset-metadata-catalog", "list", <RuntimeDatasetMetadataCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-dataset-metadata-catalog", "show", <RuntimeDatasetMetadataCatalogShow />)} />
    </Route>
    <Route path="/runtime-engine-profile-catalog">
      <Route index element={resolvePageOverride("runtime-engine-profile-catalog", "list", <RuntimeEngineProfileCatalogList />)} />
      <Route path="command/register-runtime-engine-profile" element={resolvePageOverride("runtime-engine-profile-catalog", "registerRuntimeEngineProfile", <RuntimeEngineProfileCatalogRegisterRuntimeEngineProfile />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-engine-profile-catalog", "show", <RuntimeEngineProfileCatalogShow />)} />
    </Route>
    <Route path="/runtime-health-dashboard">
      <Route index element={resolvePageOverride("runtime-health-dashboard", "list", <RuntimeHealthDashboardList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-health-dashboard", "show", <RuntimeHealthDashboardShow />)} />
    </Route>
    <Route path="/runtime-identity-catalog">
      <Route index element={resolvePageOverride("runtime-identity-catalog", "list", <RuntimeIdentityCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-identity-catalog", "show", <RuntimeIdentityCatalogShow />)} />
      <Route path=":id/command/revoke-runtime-identity" element={resolvePageOverride("runtime-identity-catalog", "revokeRuntimeIdentity", <RuntimeIdentityCatalogRevokeRuntimeIdentity />)} />
    </Route>
    <Route path="/runtime-infrastructure-access-view">
      <Route index element={resolvePageOverride("runtime-infrastructure-access-view", "list", <RuntimeInfrastructureAccessViewList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-infrastructure-access-view", "show", <RuntimeInfrastructureAccessViewShow />)} />
      <Route path=":id/command/register-runtime-infrastructure" element={resolvePageOverride("runtime-infrastructure-access-view", "registerRuntimeInfrastructure", <RuntimeInfrastructureAccessViewRegisterRuntimeInfrastructure />)} />
      <Route path=":id/command/confirm-runtime-infrastructure-prepared" element={resolvePageOverride("runtime-infrastructure-access-view", "confirmRuntimeInfrastructurePrepared", <RuntimeInfrastructureAccessViewConfirmRuntimeInfrastructurePrepared />)} />
    </Route>
    <Route path="/runtime-infrastructure-package-catalog">
      <Route index element={resolvePageOverride("runtime-infrastructure-package-catalog", "list", <RuntimeInfrastructurePackageCatalogList />)} />
      <Route path="command/register-runtime-infrastructure-package" element={resolvePageOverride("runtime-infrastructure-package-catalog", "registerRuntimeInfrastructurePackage", <RuntimeInfrastructurePackageCatalogRegisterRuntimeInfrastructurePackage />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-infrastructure-package-catalog", "show", <RuntimeInfrastructurePackageCatalogShow />)} />
    </Route>
    <Route path="/runtime-installation-guide">
      <Route index element={resolvePageOverride("runtime-installation-guide", "list", <RuntimeInstallationGuideList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-installation-guide", "show", <RuntimeInstallationGuideShow />)} />
      <Route path=":id/command/confirm-runtime-infrastructure-prepared" element={resolvePageOverride("runtime-installation-guide", "confirmRuntimeInfrastructurePrepared", <RuntimeInstallationGuideConfirmRuntimeInfrastructurePrepared />)} />
      <Route path=":id/command/register-runtime-infrastructure" element={resolvePageOverride("runtime-installation-guide", "registerRuntimeInfrastructure", <RuntimeInstallationGuideRegisterRuntimeInfrastructure />)} />
    </Route>
    <Route path="/runtime-installation-plan-catalog">
      <Route index element={resolvePageOverride("runtime-installation-plan-catalog", "list", <RuntimeInstallationPlanCatalogList />)} />
      <Route path="command/create-runtime-installation-plan" element={resolvePageOverride("runtime-installation-plan-catalog", "createRuntimeInstallationPlan", <RuntimeInstallationPlanCatalogCreateRuntimeInstallationPlan />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-installation-plan-catalog", "show", <RuntimeInstallationPlanCatalogShow />)} />
      <Route path=":id/command/retry-runtime-infrastructure-verification" element={resolvePageOverride("runtime-installation-plan-catalog", "retryRuntimeInfrastructureVerification", <RuntimeInstallationPlanCatalogRetryRuntimeInfrastructureVerification />)} />
      <Route path=":id/command/retry-runtime-agent-deployment" element={resolvePageOverride("runtime-installation-plan-catalog", "retryRuntimeAgentDeployment", <RuntimeInstallationPlanCatalogRetryRuntimeAgentDeployment />)} />
      <Route path=":id/command/register-runtime-infrastructure" element={resolvePageOverride("runtime-installation-plan-catalog", "registerRuntimeInfrastructure", <RuntimeInstallationPlanCatalogRegisterRuntimeInfrastructure />)} />
      <Route path=":id/command/confirm-runtime-infrastructure-prepared" element={resolvePageOverride("runtime-installation-plan-catalog", "confirmRuntimeInfrastructurePrepared", <RuntimeInstallationPlanCatalogConfirmRuntimeInfrastructurePrepared />)} />
    </Route>
    <Route path="/runtime-node-inventory-view">
      <Route index element={resolvePageOverride("runtime-node-inventory-view", "list", <RuntimeNodeInventoryViewList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-node-inventory-view", "show", <RuntimeNodeInventoryViewShow />)} />
    </Route>
    <Route path="/runtime-node-resource-latest">
      <Route index element={resolvePageOverride("runtime-node-resource-latest", "list", <RuntimeNodeResourceLatestList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-node-resource-latest", "show", <RuntimeNodeResourceLatestShow />)} />
    </Route>
    <Route path="/runtime-telemetry-latest">
      <Route index element={resolvePageOverride("runtime-telemetry-latest", "list", <RuntimeTelemetryLatestList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-telemetry-latest", "show", <RuntimeTelemetryLatestShow />)} />
    </Route>
    <Route path="/secure-aggregation-session-catalog">
      <Route index element={resolvePageOverride("secure-aggregation-session-catalog", "list", <SecureAggregationSessionCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("secure-aggregation-session-catalog", "show", <SecureAggregationSessionCatalogShow />)} />
      <Route path=":id/command/fail-secure-aggregation-session" element={resolvePageOverride("secure-aggregation-session-catalog", "failSecureAggregationSession", <SecureAggregationSessionCatalogFailSecureAggregationSession />)} />
    </Route>
    <Route path="/service-account-api-token-catalog">
      <Route index element={resolvePageOverride("service-account-api-token-catalog", "list", <ServiceAccountApiTokenCatalogList />)} />
      <Route path="command/issue-service-account-api-token" element={resolvePageOverride("service-account-api-token-catalog", "issueServiceAccountApiToken", <ServiceAccountApiTokenCatalogIssueServiceAccountApiToken />)} />
      <Route path="show/:id" element={resolvePageOverride("service-account-api-token-catalog", "show", <ServiceAccountApiTokenCatalogShow />)} />
    </Route>
    <Route path="/training-alert-catalog">
      <Route index element={resolvePageOverride("training-alert-catalog", "list", <TrainingAlertCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("training-alert-catalog", "show", <TrainingAlertCatalogShow />)} />
      <Route path=":id/command/acknowledge-training-alert" element={resolvePageOverride("training-alert-catalog", "acknowledgeTrainingAlert", <TrainingAlertCatalogAcknowledgeTrainingAlert />)} />
      <Route path=":id/command/resolve-training-alert" element={resolvePageOverride("training-alert-catalog", "resolveTrainingAlert", <TrainingAlertCatalogResolveTrainingAlert />)} />
    </Route>
    <Route path="/training-job-dashboard">
      <Route index element={resolvePageOverride("training-job-dashboard", "list", <TrainingJobDashboardList />)} />
      <Route path="command/create-training-job" element={resolvePageOverride("training-job-dashboard", "createTrainingJob", <TrainingJobDashboardCreateTrainingJob />)} />
      <Route path="show/:id" element={resolvePageOverride("training-job-dashboard", "show", <TrainingJobDashboardShow />)} />
      <Route path=":id/command/cancel-training-job" element={resolvePageOverride("training-job-dashboard", "cancelTrainingJob", <TrainingJobDashboardCancelTrainingJob />)} />
      <Route path=":id/command/submit-training-job" element={resolvePageOverride("training-job-dashboard", "submitTrainingJob", <TrainingJobDashboardSubmitTrainingJob />)} />
      <Route path=":id/command/pause-training-job" element={resolvePageOverride("training-job-dashboard", "pauseTrainingJob", <TrainingJobDashboardPauseTrainingJob />)} />
      <Route path=":id/command/resume-training-job" element={resolvePageOverride("training-job-dashboard", "resumeTrainingJob", <TrainingJobDashboardResumeTrainingJob />)} />
      <Route path=":id/command/retry-training-round-participant-selection" element={resolvePageOverride("training-job-dashboard", "retryTrainingRoundParticipantSelection", <TrainingJobDashboardRetryTrainingRoundParticipantSelection />)} />
    </Route>
    <Route path="/training-participant-eligibility">
      <Route index element={resolvePageOverride("training-participant-eligibility", "list", <TrainingParticipantEligibilityList />)} />
      <Route path="show/:id" element={resolvePageOverride("training-participant-eligibility", "show", <TrainingParticipantEligibilityShow />)} />
      <Route path=":id/command/submit-training-job" element={resolvePageOverride("training-participant-eligibility", "submitTrainingJob", <TrainingParticipantEligibilitySubmitTrainingJob />)} />
    </Route>
    <Route path="/training-round-progress">
      <Route index element={resolvePageOverride("training-round-progress", "list", <TrainingRoundProgressList />)} />
      <Route path="show/:id" element={resolvePageOverride("training-round-progress", "show", <TrainingRoundProgressShow />)} />
      <Route path=":id/command/cancel-training-job" element={resolvePageOverride("training-round-progress", "cancelTrainingJob", <TrainingRoundProgressCancelTrainingJob />)} />
      <Route path=":id/command/submit-model-update-submission" element={resolvePageOverride("training-round-progress", "submitModelUpdateSubmission", <TrainingRoundProgressSubmitModelUpdateSubmission />)} />
      <Route path=":id/command/submit-training-job" element={resolvePageOverride("training-round-progress", "submitTrainingJob", <TrainingRoundProgressSubmitTrainingJob />)} />
      <Route path=":id/command/retry-training-round-participant-selection" element={resolvePageOverride("training-round-progress", "retryTrainingRoundParticipantSelection", <TrainingRoundProgressRetryTrainingRoundParticipantSelection />)} />
      <Route path=":id/command/pause-training-job" element={resolvePageOverride("training-round-progress", "pauseTrainingJob", <TrainingRoundProgressPauseTrainingJob />)} />
    </Route>
    <Route path="/training-run-configuration-catalog">
      <Route index element={resolvePageOverride("training-run-configuration-catalog", "list", <TrainingRunConfigurationCatalogList />)} />
      <Route path="command/define-training-run-configuration" element={resolvePageOverride("training-run-configuration-catalog", "defineTrainingRunConfiguration", <TrainingRunConfigurationCatalogDefineTrainingRunConfiguration />)} />
      <Route path="edit/:id" element={resolvePageOverride("training-run-configuration-catalog", "edit", <TrainingRunConfigurationCatalogUpdateTrainingRunConfiguration />)} />
      <Route path="show/:id" element={resolvePageOverride("training-run-configuration-catalog", "show", <TrainingRunConfigurationCatalogShow />)} />
      <Route path=":id/command/create-training-job" element={resolvePageOverride("training-run-configuration-catalog", "createTrainingJob", <TrainingRunConfigurationCatalogCreateTrainingJob />)} />
    </Route>
    <Route path="/uploaded-file-catalog">
      <Route index element={resolvePageOverride("uploaded-file-catalog", "list", <UploadedFileCatalogList />)} />
      <Route path="command/upload-file" element={resolvePageOverride("uploaded-file-catalog", "uploadFile", <UploadedFileCatalogUploadFile />)} />
      <Route path="show/:id" element={resolvePageOverride("uploaded-file-catalog", "show", <UploadedFileCatalogShow />)} />
      <Route path=":id/command/mark-file-referenced" element={resolvePageOverride("uploaded-file-catalog", "markFileReferenced", <UploadedFileCatalogMarkFileReferenced />)} />
      <Route path=":id/command/discard-file" element={resolvePageOverride("uploaded-file-catalog", "discardFile", <UploadedFileCatalogDiscardFile />)} />
      <Route path=":id/command/download-file" element={resolvePageOverride("uploaded-file-catalog", "downloadFile", <UploadedFileCatalogDownloadFile />)} />
    </Route>
    <Route path="/user-account-catalog">
      <Route index element={resolvePageOverride("user-account-catalog", "list", <UserAccountCatalogList />)} />
      <Route path="command/register-user-account" element={resolvePageOverride("user-account-catalog", "registerUserAccount", <UserAccountCatalogRegisterUserAccount />)} />
      <Route path="show/:id" element={resolvePageOverride("user-account-catalog", "show", <UserAccountCatalogShow />)} />
      <Route path=":id/command/generate-user-account-login-password" element={resolvePageOverride("user-account-catalog", "generateUserAccountLoginPassword", <UserAccountCatalogGenerateUserAccountLoginPassword />)} />
      <Route path=":id/command/deactivate-user-account" element={resolvePageOverride("user-account-catalog", "deactivateUserAccount", <UserAccountCatalogDeactivateUserAccount />)} />
      <Route path=":id/command/assign-role-to-user" element={resolvePageOverride("user-account-catalog", "assignRoleToUser", <UserAccountCatalogAssignRoleToUser />)} />
    </Route>
    <Route path="/user-organization-membership-directory">
      <Route index element={resolvePageOverride("user-organization-membership-directory", "list", <UserOrganizationMembershipDirectoryList />)} />
      <Route path="command/bind-user-account-to-organization" element={resolvePageOverride("user-organization-membership-directory", "bindUserAccountToOrganization", <UserOrganizationMembershipDirectoryBindUserAccountToOrganization />)} />
      <Route path="show/:id" element={resolvePageOverride("user-organization-membership-directory", "show", <UserOrganizationMembershipDirectoryShow />)} />
    </Route>
    <Route path="/user-role-assignment-catalog">
      <Route index element={resolvePageOverride("user-role-assignment-catalog", "list", <UserRoleAssignmentCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("user-role-assignment-catalog", "show", <UserRoleAssignmentCatalogShow />)} />
    </Route>
  </>
);
