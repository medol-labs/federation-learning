// Generated from config.json by the refine generator.
import { Authenticated } from "@refinedev/core";
import { NuqsAdapter } from "nuqs/adapters/react-router/v7";

import {
  CatchAllNavigate,
  NavigateToResource
} from "@refinedev/react-router";
import { Outlet, Route, Routes } from "react-router";
import { ErrorComponent } from "../components/refine-ui/layout/error-component";
import { Layout } from "../components/refine-ui/layout/layout";
import { Dashboard } from "../pages/dashboard";
import { ForgotPassword } from "../pages/forgot-password";
import { Login } from "../pages/login";
import { Register } from "../pages/register";
import { PortalSso } from "../pages/sso/portal";
import {
  AgentDatasetAccessValidationCatalogList,
  AgentDatasetAccessValidationCatalogShow,
} from "../pages/agent-dataset-access-validation-catalog";
import {
  AgentRuntimeInfrastructureConnectionCatalogList,
  AgentRuntimeInfrastructureConnectionCatalogShow,
  AgentRuntimeInfrastructureConnectionCatalogRecordRuntimeConnectionEstablished,
} from "../pages/agent-runtime-infrastructure-connection-catalog";
import {
  AgentRuntimeNodeInventoryCatalogList,
  AgentRuntimeNodeInventoryCatalogShow,
} from "../pages/agent-runtime-node-inventory-catalog";
import {
  AgentRuntimeNodeResourceLatestList,
  AgentRuntimeNodeResourceLatestShow,
} from "../pages/agent-runtime-node-resource-latest";
import {
  AgentRuntimeTelemetryLatestList,
  AgentRuntimeTelemetryLatestShow,
} from "../pages/agent-runtime-telemetry-latest";
import {
  AuditRecordLogList,
  AuditRecordLogShow,
} from "../pages/audit-record-log";
import {
  CurrentRecommendedFeatureSchemaCatalogList,
  CurrentRecommendedFeatureSchemaCatalogShow,
  CurrentRecommendedFeatureSchemaCatalogDefineFeatureSchema,
} from "../pages/current-recommended-feature-schema-catalog";
import {
  DatasetCapabilityList,
  DatasetCapabilityShow,
  DatasetCapabilityDeclareDataset,
  DatasetCapabilityRetryDatasetContractValidation,
  DatasetCapabilityRejectDatasetForTraining,
  DatasetCapabilityApproveDatasetForTraining,
  DatasetCapabilityRevokeDatasetTrainingApproval,
  DatasetCapabilityConfigureRuntimeDatasetBinding,
} from "../pages/dataset-capability";
import {
  DatasetReadinessList,
  DatasetReadinessShow,
  DatasetReadinessConfigureRuntimeDatasetBinding,
  DatasetReadinessRejectDatasetForTraining,
  DatasetReadinessApproveDatasetForTraining,
  DatasetReadinessRetryDatasetContractValidation,
  DatasetReadinessRevokeDatasetTrainingApproval,
} from "../pages/dataset-readiness";
import {
  DictionaryCatalogList,
  DictionaryCatalogShow,
  DictionaryCatalogRegisterDictionary,
  DictionaryCatalogUpdateDictionary,
  DictionaryCatalogArchiveDictionary,
  DictionaryCatalogAddDictionaryValue,
} from "../pages/dictionary-catalog";
import {
  DictionaryValueCatalogList,
  DictionaryValueCatalogShow,
  DictionaryValueCatalogAddDictionaryValue,
  DictionaryValueCatalogDisableDictionaryValue,
  DictionaryValueCatalogEnableDictionaryValue,
} from "../pages/dictionary-value-catalog";
import {
  FeatureSchemaCatalogList,
  FeatureSchemaCatalogShow,
  FeatureSchemaCatalogPublishFeatureSchema,
  FeatureSchemaCatalogDeprecateFeatureSchema,
  FeatureSchemaCatalogSupersedeFeatureSchemaVersion,
  FeatureSchemaCatalogMarkCurrentRecommendedFeatureSchemaVersion,
  FeatureSchemaCatalogDeclareDataset,
  FeatureSchemaCatalogRetireFeatureSchema,
} from "../pages/feature-schema-catalog";
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
} from "../pages/federation-membership-directory";
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
} from "../pages/federation-overview";
import {
  ModelArtifactCatalogList,
  ModelArtifactCatalogShow,
  ModelArtifactCatalogRegisterModelArtifact,
  ModelArtifactCatalogDownloadModelArtifact,
} from "../pages/model-artifact-catalog";
import {
  ModelCatalogList,
  ModelCatalogShow,
  ModelCatalogRecordModelEvaluationPackage,
  ModelCatalogApproveModel,
  ModelCatalogPromoteModelToProduction,
  ModelCatalogRollbackModel,
  ModelCatalogRetireModel,
} from "../pages/model-catalog";
import {
  OrganizationDirectoryList,
  OrganizationDirectoryShow,
  OrganizationDirectoryRegisterOrganization,
  OrganizationDirectoryActivateOrganization,
  OrganizationDirectoryDeactivateOrganization,
  OrganizationDirectoryReactivateOrganization,
  OrganizationDirectoryCreateRuntimeInstallationPlan,
} from "../pages/organization-directory";
import {
  PermissionCatalogList,
  PermissionCatalogShow,
  PermissionCatalogRegisterPermission,
} from "../pages/permission-catalog";
import {
  RoleCatalogList,
  RoleCatalogShow,
  RoleCatalogRegisterRole,
  RoleCatalogGrantPermissionToRole,
} from "../pages/role-catalog";
import {
  RolePermissionGrantCatalogList,
  RolePermissionGrantCatalogShow,
} from "../pages/role-permission-grant-catalog";
import {
  RoundExecutionCatalogList,
  RoundExecutionCatalogShow,
  RoundExecutionCatalogRetryRoundExecutionAfterStartFailure,
  RoundExecutionCatalogRetryRoundExecutionAfterRuntimeFailure,
  RoundExecutionCatalogSubmitModelUpdateSubmission,
} from "../pages/round-execution-catalog";
import {
  RuntimeAgentEndpointCatalogList,
  RuntimeAgentEndpointCatalogShow,
  RuntimeAgentEndpointCatalogRecordRuntimeConnectionEstablished,
  RuntimeAgentEndpointCatalogRetryRuntimeAgentDeployment,
} from "../pages/runtime-agent-endpoint-catalog";
import {
  RuntimeAgentLifecycleCatalogList,
  RuntimeAgentLifecycleCatalogShow,
  RuntimeAgentLifecycleCatalogLoadRuntimeAgentBootstrapConfiguration,
} from "../pages/runtime-agent-lifecycle-catalog";
import {
  RuntimeCapabilityCatalogList,
  RuntimeCapabilityCatalogShow,
} from "../pages/runtime-capability-catalog";
import {
  RuntimeDatasetBindingCatalogList,
  RuntimeDatasetBindingCatalogShow,
  RuntimeDatasetBindingCatalogConfigureRuntimeDatasetBinding,
} from "../pages/runtime-dataset-binding-catalog";
import {
  RuntimeDatasetMetadataCatalogList,
  RuntimeDatasetMetadataCatalogShow,
  RuntimeDatasetMetadataCatalogReprofileAgentDataset,
} from "../pages/runtime-dataset-metadata-catalog";
import {
  RuntimeHealthDashboardList,
  RuntimeHealthDashboardShow,
} from "../pages/runtime-health-dashboard";
import {
  RuntimeIdentityCatalogList,
  RuntimeIdentityCatalogShow,
  RuntimeIdentityCatalogRevokeRuntimeIdentity,
} from "../pages/runtime-identity-catalog";
import {
  RuntimeInfrastructureAccessViewList,
  RuntimeInfrastructureAccessViewShow,
  RuntimeInfrastructureAccessViewRegisterRuntimeInfrastructure,
} from "../pages/runtime-infrastructure-access-view";
import {
  RuntimeInfrastructurePackageCatalogList,
  RuntimeInfrastructurePackageCatalogShow,
  RuntimeInfrastructurePackageCatalogRegisterRuntimeInfrastructurePackage,
} from "../pages/runtime-infrastructure-package-catalog";
import {
  RuntimeInstallationGuideList,
  RuntimeInstallationGuideShow,
} from "../pages/runtime-installation-guide";
import {
  RuntimeInstallationPlanCatalogList,
  RuntimeInstallationPlanCatalogShow,
  RuntimeInstallationPlanCatalogCreateRuntimeInstallationPlan,
} from "../pages/runtime-installation-plan-catalog";
import {
  RuntimeNodeInventoryViewList,
  RuntimeNodeInventoryViewShow,
} from "../pages/runtime-node-inventory-view";
import {
  RuntimeNodeResourceLatestList,
  RuntimeNodeResourceLatestShow,
} from "../pages/runtime-node-resource-latest";
import {
  RuntimeTelemetryLatestList,
  RuntimeTelemetryLatestShow,
} from "../pages/runtime-telemetry-latest";
import {
  SecureAggregationSessionCatalogList,
  SecureAggregationSessionCatalogShow,
  SecureAggregationSessionCatalogFailSecureAggregationSession,
} from "../pages/secure-aggregation-session-catalog";
import {
  ServiceAccountApiTokenCatalogList,
  ServiceAccountApiTokenCatalogShow,
  ServiceAccountApiTokenCatalogIssueServiceAccountApiToken,
} from "../pages/service-account-api-token-catalog";
import {
  TrainingAlertCatalogList,
  TrainingAlertCatalogShow,
  TrainingAlertCatalogAcknowledgeTrainingAlert,
  TrainingAlertCatalogResolveTrainingAlert,
} from "../pages/training-alert-catalog";
import {
  TrainingJobDashboardList,
  TrainingJobDashboardShow,
  TrainingJobDashboardCreateTrainingJob,
  TrainingJobDashboardCancelTrainingJob,
  TrainingJobDashboardSubmitTrainingJob,
  TrainingJobDashboardPauseTrainingJob,
  TrainingJobDashboardResumeTrainingJob,
} from "../pages/training-job-dashboard";
import {
  TrainingParticipantEligibilityList,
  TrainingParticipantEligibilityShow,
  TrainingParticipantEligibilitySubmitTrainingJob,
} from "../pages/training-participant-eligibility";
import {
  TrainingRoundProgressList,
  TrainingRoundProgressShow,
  TrainingRoundProgressCancelTrainingJob,
  TrainingRoundProgressSubmitModelUpdateSubmission,
  TrainingRoundProgressRetryTrainingRoundParticipantSelection,
  TrainingRoundProgressSubmitTrainingJob,
  TrainingRoundProgressPauseTrainingJob,
  TrainingRoundProgressRetryRoundExecutionAfterStartFailure,
  TrainingRoundProgressRetryRoundExecutionAfterRuntimeFailure,
} from "../pages/training-round-progress";
import {
  TrainingRunConfigurationCatalogList,
  TrainingRunConfigurationCatalogShow,
  TrainingRunConfigurationCatalogDefineTrainingRunConfiguration,
  TrainingRunConfigurationCatalogUpdateTrainingRunConfiguration,
  TrainingRunConfigurationCatalogCreateTrainingJob,
} from "../pages/training-run-configuration-catalog";
import {
  UploadedFileCatalogList,
  UploadedFileCatalogShow,
  UploadedFileCatalogUploadFile,
  UploadedFileCatalogMarkFileReferenced,
  UploadedFileCatalogDiscardFile,
  UploadedFileCatalogDownloadFile,
} from "../pages/uploaded-file-catalog";
import {
  UserAccountCatalogList,
  UserAccountCatalogShow,
  UserAccountCatalogRegisterUserAccount,
  UserAccountCatalogGenerateUserAccountLoginPassword,
  UserAccountCatalogDeactivateUserAccount,
  UserAccountCatalogAssignRoleToUser,
} from "../pages/user-account-catalog";
import {
  UserOrganizationMembershipDirectoryList,
  UserOrganizationMembershipDirectoryShow,
  UserOrganizationMembershipDirectoryBindUserAccountToOrganization,
} from "../pages/user-organization-membership-directory";
import {
  UserRoleAssignmentCatalogList,
  UserRoleAssignmentCatalogShow,
} from "../pages/user-role-assignment-catalog";

export const AppRouter = () => {
  return (
    <Routes>
      <Route path="/sso/portal" element={<PortalSso />} />
      <Route
        element={
          <Authenticated
            key="authenticated-inner"
            fallback={<CatchAllNavigate to="/login" />}
          >
            <Layout>
              <NuqsAdapter>
                <Outlet />
              </NuqsAdapter>
            </Layout>
          </Authenticated>
        }
      >
        <Route index element={<NavigateToResource resource="dashboard" />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/agent-dataset-access-validation-catalog">
          <Route index element={<AgentDatasetAccessValidationCatalogList />} />
          <Route path="show/:id" element={<AgentDatasetAccessValidationCatalogShow />} />
        </Route>
        <Route path="/agent-runtime-infrastructure-connection-catalog">
          <Route index element={<AgentRuntimeInfrastructureConnectionCatalogList />} />
          <Route path="show/:id" element={<AgentRuntimeInfrastructureConnectionCatalogShow />} />
          <Route path=":id/command/record-runtime-connection-established" element={<AgentRuntimeInfrastructureConnectionCatalogRecordRuntimeConnectionEstablished />} />
        </Route>
        <Route path="/agent-runtime-node-inventory-catalog">
          <Route index element={<AgentRuntimeNodeInventoryCatalogList />} />
          <Route path="show/:id" element={<AgentRuntimeNodeInventoryCatalogShow />} />
        </Route>
        <Route path="/agent-runtime-node-resource-latest">
          <Route index element={<AgentRuntimeNodeResourceLatestList />} />
          <Route path="show/:id" element={<AgentRuntimeNodeResourceLatestShow />} />
        </Route>
        <Route path="/agent-runtime-telemetry-latest">
          <Route index element={<AgentRuntimeTelemetryLatestList />} />
          <Route path="show/:id" element={<AgentRuntimeTelemetryLatestShow />} />
        </Route>
        <Route path="/audit-record-log">
          <Route index element={<AuditRecordLogList />} />
          <Route path="show/:id" element={<AuditRecordLogShow />} />
        </Route>
        <Route path="/current-recommended-feature-schema-catalog">
          <Route index element={<CurrentRecommendedFeatureSchemaCatalogList />} />
          <Route path="command/define-feature-schema" element={<CurrentRecommendedFeatureSchemaCatalogDefineFeatureSchema />} />
          <Route path="show/:id" element={<CurrentRecommendedFeatureSchemaCatalogShow />} />
        </Route>
        <Route path="/dataset-capability">
          <Route index element={<DatasetCapabilityList />} />
          <Route path="command/declare-dataset" element={<DatasetCapabilityDeclareDataset />} />
          <Route path="show/:id" element={<DatasetCapabilityShow />} />
          <Route path=":id/command/retry-dataset-contract-validation" element={<DatasetCapabilityRetryDatasetContractValidation />} />
          <Route path=":id/command/reject-dataset-for-training" element={<DatasetCapabilityRejectDatasetForTraining />} />
          <Route path=":id/command/approve-dataset-for-training" element={<DatasetCapabilityApproveDatasetForTraining />} />
          <Route path=":id/command/revoke-dataset-training-approval" element={<DatasetCapabilityRevokeDatasetTrainingApproval />} />
          <Route path=":id/command/configure-runtime-dataset-binding" element={<DatasetCapabilityConfigureRuntimeDatasetBinding />} />
        </Route>
        <Route path="/dataset-readiness">
          <Route index element={<DatasetReadinessList />} />
          <Route path="show/:id" element={<DatasetReadinessShow />} />
          <Route path=":id/command/configure-runtime-dataset-binding" element={<DatasetReadinessConfigureRuntimeDatasetBinding />} />
          <Route path=":id/command/reject-dataset-for-training" element={<DatasetReadinessRejectDatasetForTraining />} />
          <Route path=":id/command/approve-dataset-for-training" element={<DatasetReadinessApproveDatasetForTraining />} />
          <Route path=":id/command/retry-dataset-contract-validation" element={<DatasetReadinessRetryDatasetContractValidation />} />
          <Route path=":id/command/revoke-dataset-training-approval" element={<DatasetReadinessRevokeDatasetTrainingApproval />} />
        </Route>
        <Route path="/dictionary-catalog">
          <Route index element={<DictionaryCatalogList />} />
          <Route path="command/register-dictionary" element={<DictionaryCatalogRegisterDictionary />} />
          <Route path="edit/:id" element={<DictionaryCatalogUpdateDictionary />} />
          <Route path="show/:id" element={<DictionaryCatalogShow />} />
          <Route path=":id/command/archive-dictionary" element={<DictionaryCatalogArchiveDictionary />} />
          <Route path=":id/command/add-dictionary-value" element={<DictionaryCatalogAddDictionaryValue />} />
        </Route>
        <Route path="/dictionary-value-catalog">
          <Route index element={<DictionaryValueCatalogList />} />
          <Route path="command/add-dictionary-value" element={<DictionaryValueCatalogAddDictionaryValue />} />
          <Route path="show/:id" element={<DictionaryValueCatalogShow />} />
          <Route path=":id/command/disable-dictionary-value" element={<DictionaryValueCatalogDisableDictionaryValue />} />
          <Route path=":id/command/enable-dictionary-value" element={<DictionaryValueCatalogEnableDictionaryValue />} />
        </Route>
        <Route path="/feature-schema-catalog">
          <Route index element={<FeatureSchemaCatalogList />} />
          <Route path="show/:id" element={<FeatureSchemaCatalogShow />} />
          <Route path=":id/command/publish-feature-schema" element={<FeatureSchemaCatalogPublishFeatureSchema />} />
          <Route path=":id/command/deprecate-feature-schema" element={<FeatureSchemaCatalogDeprecateFeatureSchema />} />
          <Route path=":id/command/supersede-feature-schema-version" element={<FeatureSchemaCatalogSupersedeFeatureSchemaVersion />} />
          <Route path=":id/command/mark-current-recommended-feature-schema-version" element={<FeatureSchemaCatalogMarkCurrentRecommendedFeatureSchemaVersion />} />
          <Route path=":id/command/declare-dataset" element={<FeatureSchemaCatalogDeclareDataset />} />
          <Route path=":id/command/retire-feature-schema" element={<FeatureSchemaCatalogRetireFeatureSchema />} />
        </Route>
        <Route path="/federation-membership-directory">
          <Route index element={<FederationMembershipDirectoryList />} />
          <Route path="command/invite-participant" element={<FederationMembershipDirectoryInviteParticipant />} />
          <Route path="show/:id" element={<FederationMembershipDirectoryShow />} />
          <Route path=":id/command/remove-participant" element={<FederationMembershipDirectoryRemoveParticipant />} />
          <Route path=":id/command/approve-participant" element={<FederationMembershipDirectoryApproveParticipant />} />
          <Route path=":id/command/reject-participant" element={<FederationMembershipDirectoryRejectParticipant />} />
          <Route path=":id/command/revoke-participant-invitation" element={<FederationMembershipDirectoryRevokeParticipantInvitation />} />
          <Route path=":id/command/suspend-participant" element={<FederationMembershipDirectorySuspendParticipant />} />
          <Route path=":id/command/activate-federation" element={<FederationMembershipDirectoryActivateFederation />} />
        </Route>
        <Route path="/federation-overview">
          <Route index element={<FederationOverviewList />} />
          <Route path="command/create-federation" element={<FederationOverviewCreateFederation />} />
          <Route path="show/:id" element={<FederationOverviewShow />} />
          <Route path=":id/command/remove-participant" element={<FederationOverviewRemoveParticipant />} />
          <Route path=":id/command/activate-federation" element={<FederationOverviewActivateFederation />} />
          <Route path=":id/command/suspend-federation" element={<FederationOverviewSuspendFederation />} />
          <Route path=":id/command/reactivate-federation" element={<FederationOverviewReactivateFederation />} />
          <Route path=":id/command/invite-participant" element={<FederationOverviewInviteParticipant />} />
          <Route path=":id/command/approve-participant" element={<FederationOverviewApproveParticipant />} />
          <Route path=":id/command/reject-participant" element={<FederationOverviewRejectParticipant />} />
          <Route path=":id/command/revoke-participant-invitation" element={<FederationOverviewRevokeParticipantInvitation />} />
          <Route path=":id/command/suspend-participant" element={<FederationOverviewSuspendParticipant />} />
        </Route>
        <Route path="/model-artifact-catalog">
          <Route index element={<ModelArtifactCatalogList />} />
          <Route path="command/register-model-artifact" element={<ModelArtifactCatalogRegisterModelArtifact />} />
          <Route path="show/:id" element={<ModelArtifactCatalogShow />} />
          <Route path=":id/command/download-model-artifact" element={<ModelArtifactCatalogDownloadModelArtifact />} />
        </Route>
        <Route path="/model-catalog">
          <Route index element={<ModelCatalogList />} />
          <Route path="show/:id" element={<ModelCatalogShow />} />
          <Route path=":id/command/record-model-evaluation-package" element={<ModelCatalogRecordModelEvaluationPackage />} />
          <Route path=":id/command/approve-model" element={<ModelCatalogApproveModel />} />
          <Route path=":id/command/promote-model-to-production" element={<ModelCatalogPromoteModelToProduction />} />
          <Route path=":id/command/rollback-model" element={<ModelCatalogRollbackModel />} />
          <Route path=":id/command/retire-model" element={<ModelCatalogRetireModel />} />
        </Route>
        <Route path="/organization-directory">
          <Route index element={<OrganizationDirectoryList />} />
          <Route path="command/register-organization" element={<OrganizationDirectoryRegisterOrganization />} />
          <Route path="show/:id" element={<OrganizationDirectoryShow />} />
          <Route path=":id/command/activate-organization" element={<OrganizationDirectoryActivateOrganization />} />
          <Route path=":id/command/deactivate-organization" element={<OrganizationDirectoryDeactivateOrganization />} />
          <Route path=":id/command/reactivate-organization" element={<OrganizationDirectoryReactivateOrganization />} />
          <Route path=":id/command/create-runtime-installation-plan" element={<OrganizationDirectoryCreateRuntimeInstallationPlan />} />
        </Route>
        <Route path="/permission-catalog">
          <Route index element={<PermissionCatalogList />} />
          <Route path="command/register-permission" element={<PermissionCatalogRegisterPermission />} />
          <Route path="show/:id" element={<PermissionCatalogShow />} />
        </Route>
        <Route path="/role-catalog">
          <Route index element={<RoleCatalogList />} />
          <Route path="command/register-role" element={<RoleCatalogRegisterRole />} />
          <Route path="show/:id" element={<RoleCatalogShow />} />
          <Route path=":id/command/grant-permission-to-role" element={<RoleCatalogGrantPermissionToRole />} />
        </Route>
        <Route path="/role-permission-grant-catalog">
          <Route index element={<RolePermissionGrantCatalogList />} />
          <Route path="show/:id" element={<RolePermissionGrantCatalogShow />} />
        </Route>
        <Route path="/round-execution-catalog">
          <Route index element={<RoundExecutionCatalogList />} />
          <Route path="show/:id" element={<RoundExecutionCatalogShow />} />
          <Route path=":id/command/retry-round-execution-after-start-failure" element={<RoundExecutionCatalogRetryRoundExecutionAfterStartFailure />} />
          <Route path=":id/command/retry-round-execution-after-runtime-failure" element={<RoundExecutionCatalogRetryRoundExecutionAfterRuntimeFailure />} />
          <Route path=":id/command/submit-model-update-submission" element={<RoundExecutionCatalogSubmitModelUpdateSubmission />} />
        </Route>
        <Route path="/runtime-agent-endpoint-catalog">
          <Route index element={<RuntimeAgentEndpointCatalogList />} />
          <Route path="show/:id" element={<RuntimeAgentEndpointCatalogShow />} />
          <Route path=":id/command/record-runtime-connection-established" element={<RuntimeAgentEndpointCatalogRecordRuntimeConnectionEstablished />} />
          <Route path=":id/command/retry-runtime-agent-deployment" element={<RuntimeAgentEndpointCatalogRetryRuntimeAgentDeployment />} />
        </Route>
        <Route path="/runtime-agent-lifecycle-catalog">
          <Route index element={<RuntimeAgentLifecycleCatalogList />} />
          <Route path="command/load-runtime-agent-bootstrap-configuration" element={<RuntimeAgentLifecycleCatalogLoadRuntimeAgentBootstrapConfiguration />} />
          <Route path="show/:id" element={<RuntimeAgentLifecycleCatalogShow />} />
        </Route>
        <Route path="/runtime-capability-catalog">
          <Route index element={<RuntimeCapabilityCatalogList />} />
          <Route path="show/:id" element={<RuntimeCapabilityCatalogShow />} />
        </Route>
        <Route path="/runtime-dataset-binding-catalog">
          <Route index element={<RuntimeDatasetBindingCatalogList />} />
          <Route path="command/configure-runtime-dataset-binding" element={<RuntimeDatasetBindingCatalogConfigureRuntimeDatasetBinding />} />
          <Route path="show/:id" element={<RuntimeDatasetBindingCatalogShow />} />
        </Route>
        <Route path="/runtime-dataset-metadata-catalog">
          <Route index element={<RuntimeDatasetMetadataCatalogList />} />
          <Route path="show/:id" element={<RuntimeDatasetMetadataCatalogShow />} />
          <Route path=":id/command/reprofile-agent-dataset" element={<RuntimeDatasetMetadataCatalogReprofileAgentDataset />} />
        </Route>
        <Route path="/runtime-health-dashboard">
          <Route index element={<RuntimeHealthDashboardList />} />
          <Route path="show/:id" element={<RuntimeHealthDashboardShow />} />
        </Route>
        <Route path="/runtime-identity-catalog">
          <Route index element={<RuntimeIdentityCatalogList />} />
          <Route path="show/:id" element={<RuntimeIdentityCatalogShow />} />
          <Route path=":id/command/revoke-runtime-identity" element={<RuntimeIdentityCatalogRevokeRuntimeIdentity />} />
        </Route>
        <Route path="/runtime-infrastructure-access-view">
          <Route index element={<RuntimeInfrastructureAccessViewList />} />
          <Route path="show/:id" element={<RuntimeInfrastructureAccessViewShow />} />
          <Route path=":id/command/register-runtime-infrastructure" element={<RuntimeInfrastructureAccessViewRegisterRuntimeInfrastructure />} />
        </Route>
        <Route path="/runtime-infrastructure-package-catalog">
          <Route index element={<RuntimeInfrastructurePackageCatalogList />} />
          <Route path="command/register-runtime-infrastructure-package" element={<RuntimeInfrastructurePackageCatalogRegisterRuntimeInfrastructurePackage />} />
          <Route path="show/:id" element={<RuntimeInfrastructurePackageCatalogShow />} />
        </Route>
        <Route path="/runtime-installation-guide">
          <Route index element={<RuntimeInstallationGuideList />} />
          <Route path="show/:id" element={<RuntimeInstallationGuideShow />} />
        </Route>
        <Route path="/runtime-installation-plan-catalog">
          <Route index element={<RuntimeInstallationPlanCatalogList />} />
          <Route path="command/create-runtime-installation-plan" element={<RuntimeInstallationPlanCatalogCreateRuntimeInstallationPlan />} />
          <Route path="show/:id" element={<RuntimeInstallationPlanCatalogShow />} />
        </Route>
        <Route path="/runtime-node-inventory-view">
          <Route index element={<RuntimeNodeInventoryViewList />} />
          <Route path="show/:id" element={<RuntimeNodeInventoryViewShow />} />
        </Route>
        <Route path="/runtime-node-resource-latest">
          <Route index element={<RuntimeNodeResourceLatestList />} />
          <Route path="show/:id" element={<RuntimeNodeResourceLatestShow />} />
        </Route>
        <Route path="/runtime-telemetry-latest">
          <Route index element={<RuntimeTelemetryLatestList />} />
          <Route path="show/:id" element={<RuntimeTelemetryLatestShow />} />
        </Route>
        <Route path="/secure-aggregation-session-catalog">
          <Route index element={<SecureAggregationSessionCatalogList />} />
          <Route path="show/:id" element={<SecureAggregationSessionCatalogShow />} />
          <Route path=":id/command/fail-secure-aggregation-session" element={<SecureAggregationSessionCatalogFailSecureAggregationSession />} />
        </Route>
        <Route path="/service-account-api-token-catalog">
          <Route index element={<ServiceAccountApiTokenCatalogList />} />
          <Route path="command/issue-service-account-api-token" element={<ServiceAccountApiTokenCatalogIssueServiceAccountApiToken />} />
          <Route path="show/:id" element={<ServiceAccountApiTokenCatalogShow />} />
        </Route>
        <Route path="/training-alert-catalog">
          <Route index element={<TrainingAlertCatalogList />} />
          <Route path="show/:id" element={<TrainingAlertCatalogShow />} />
          <Route path=":id/command/acknowledge-training-alert" element={<TrainingAlertCatalogAcknowledgeTrainingAlert />} />
          <Route path=":id/command/resolve-training-alert" element={<TrainingAlertCatalogResolveTrainingAlert />} />
        </Route>
        <Route path="/training-job-dashboard">
          <Route index element={<TrainingJobDashboardList />} />
          <Route path="command/create-training-job" element={<TrainingJobDashboardCreateTrainingJob />} />
          <Route path="show/:id" element={<TrainingJobDashboardShow />} />
          <Route path=":id/command/cancel-training-job" element={<TrainingJobDashboardCancelTrainingJob />} />
          <Route path=":id/command/submit-training-job" element={<TrainingJobDashboardSubmitTrainingJob />} />
          <Route path=":id/command/pause-training-job" element={<TrainingJobDashboardPauseTrainingJob />} />
          <Route path=":id/command/resume-training-job" element={<TrainingJobDashboardResumeTrainingJob />} />
        </Route>
        <Route path="/training-participant-eligibility">
          <Route index element={<TrainingParticipantEligibilityList />} />
          <Route path="show/:id" element={<TrainingParticipantEligibilityShow />} />
          <Route path=":id/command/submit-training-job" element={<TrainingParticipantEligibilitySubmitTrainingJob />} />
        </Route>
        <Route path="/training-round-progress">
          <Route index element={<TrainingRoundProgressList />} />
          <Route path="show/:id" element={<TrainingRoundProgressShow />} />
          <Route path=":id/command/cancel-training-job" element={<TrainingRoundProgressCancelTrainingJob />} />
          <Route path=":id/command/submit-model-update-submission" element={<TrainingRoundProgressSubmitModelUpdateSubmission />} />
          <Route path=":id/command/retry-training-round-participant-selection" element={<TrainingRoundProgressRetryTrainingRoundParticipantSelection />} />
          <Route path=":id/command/submit-training-job" element={<TrainingRoundProgressSubmitTrainingJob />} />
          <Route path=":id/command/pause-training-job" element={<TrainingRoundProgressPauseTrainingJob />} />
          <Route path=":id/command/retry-round-execution-after-start-failure" element={<TrainingRoundProgressRetryRoundExecutionAfterStartFailure />} />
          <Route path=":id/command/retry-round-execution-after-runtime-failure" element={<TrainingRoundProgressRetryRoundExecutionAfterRuntimeFailure />} />
        </Route>
        <Route path="/training-run-configuration-catalog">
          <Route index element={<TrainingRunConfigurationCatalogList />} />
          <Route path="command/define-training-run-configuration" element={<TrainingRunConfigurationCatalogDefineTrainingRunConfiguration />} />
          <Route path="edit/:id" element={<TrainingRunConfigurationCatalogUpdateTrainingRunConfiguration />} />
          <Route path="show/:id" element={<TrainingRunConfigurationCatalogShow />} />
          <Route path=":id/command/create-training-job" element={<TrainingRunConfigurationCatalogCreateTrainingJob />} />
        </Route>
        <Route path="/uploaded-file-catalog">
          <Route index element={<UploadedFileCatalogList />} />
          <Route path="command/upload-file" element={<UploadedFileCatalogUploadFile />} />
          <Route path="show/:id" element={<UploadedFileCatalogShow />} />
          <Route path=":id/command/mark-file-referenced" element={<UploadedFileCatalogMarkFileReferenced />} />
          <Route path=":id/command/discard-file" element={<UploadedFileCatalogDiscardFile />} />
          <Route path=":id/command/download-file" element={<UploadedFileCatalogDownloadFile />} />
        </Route>
        <Route path="/user-account-catalog">
          <Route index element={<UserAccountCatalogList />} />
          <Route path="command/register-user-account" element={<UserAccountCatalogRegisterUserAccount />} />
          <Route path="show/:id" element={<UserAccountCatalogShow />} />
          <Route path=":id/command/generate-user-account-login-password" element={<UserAccountCatalogGenerateUserAccountLoginPassword />} />
          <Route path=":id/command/deactivate-user-account" element={<UserAccountCatalogDeactivateUserAccount />} />
          <Route path=":id/command/assign-role-to-user" element={<UserAccountCatalogAssignRoleToUser />} />
        </Route>
        <Route path="/user-organization-membership-directory">
          <Route index element={<UserOrganizationMembershipDirectoryList />} />
          <Route path="command/bind-user-account-to-organization" element={<UserOrganizationMembershipDirectoryBindUserAccountToOrganization />} />
          <Route path="show/:id" element={<UserOrganizationMembershipDirectoryShow />} />
        </Route>
        <Route path="/user-role-assignment-catalog">
          <Route index element={<UserRoleAssignmentCatalogList />} />
          <Route path="show/:id" element={<UserRoleAssignmentCatalogShow />} />
        </Route>
        <Route path="*" element={<ErrorComponent />} />
      </Route>
      <Route
        element={
          <Authenticated key="authenticated-outer" fallback={<Outlet />}>
            <NavigateToResource />
          </Authenticated>
        }
      >
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
      </Route>
    </Routes>
  );
};
