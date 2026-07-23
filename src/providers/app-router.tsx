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
import {
  AgentDatasetAccessValidationCatalogList,
  AgentDatasetAccessValidationCatalogShow,
  AgentDatasetAccessValidationCatalogValidateAgentDatasetAccess,
  AgentDatasetAccessValidationCatalogConfigureRuntimeDatasetBinding,
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
} from "../pages/current-recommended-feature-schema-catalog";
import {
  DatasetCapabilityList,
  DatasetCapabilityShow,
  DatasetCapabilityDeclareDataset,
  DatasetCapabilityConfigureRuntimeDatasetBinding,
} from "../pages/dataset-capability";
import {
  DatasetReadinessList,
  DatasetReadinessShow,
  DatasetReadinessDeclareDataset,
  DatasetReadinessConfigureRuntimeDatasetBinding,
} from "../pages/dataset-readiness";
import {
  DictionaryCatalogList,
  DictionaryCatalogShow,
  DictionaryCatalogRegisterDictionary,
  DictionaryCatalogAddDictionaryValue,
} from "../pages/dictionary-catalog";
import {
  DictionaryValueCatalogList,
  DictionaryValueCatalogShow,
  DictionaryValueCatalogAddDictionaryValue,
} from "../pages/dictionary-value-catalog";
import {
  FeatureSchemaCatalogList,
  FeatureSchemaCatalogShow,
  FeatureSchemaCatalogDefineFeatureSchema,
  FeatureSchemaCatalogSupersedeFeatureSchemaVersion,
  FeatureSchemaCatalogDeclareDataset,
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
  FederationMembershipDirectoryCreateRuntimeInstallationPlan,
} from "../pages/federation-membership-directory";
import {
  FederationOverviewList,
  FederationOverviewShow,
  FederationOverviewCreateFederation,
} from "../pages/federation-overview";
import {
  ModelArtifactCatalogList,
  ModelArtifactCatalogShow,
  ModelArtifactCatalogRegisterModelArtifact,
  ModelArtifactCatalogSubmitGlobalModelEvaluation,
} from "../pages/model-artifact-catalog";
import {
  ModelVersionCatalogList,
  ModelVersionCatalogShow,
  ModelVersionCatalogRecordModelEvaluationPackage,
  ModelVersionCatalogRollbackModelVersion,
} from "../pages/model-version-catalog";
import {
  OrganizationDirectoryList,
  OrganizationDirectoryShow,
  OrganizationDirectoryRegisterOrganization,
  OrganizationDirectoryCreateRuntimeInstallationPlan,
} from "../pages/organization-directory";
import {
  RoundExecutionCatalogList,
  RoundExecutionCatalogShow,
  RoundExecutionCatalogRetryRoundExecutionAfterStartFailure,
  RoundExecutionCatalogCompleteRoundExecution,
  RoundExecutionCatalogFailRoundExecution,
  RoundExecutionCatalogRetryRoundExecutionAfterRuntimeFailure,
  RoundExecutionCatalogSubmitModelUpdateSubmission,
} from "../pages/round-execution-catalog";
import {
  RuntimeAgentLifecycleCatalogList,
  RuntimeAgentLifecycleCatalogShow,
  RuntimeAgentLifecycleCatalogReportRuntimeAgentStarted,
} from "../pages/runtime-agent-lifecycle-catalog";
import {
  RuntimeCapabilityCatalogList,
  RuntimeCapabilityCatalogShow,
} from "../pages/runtime-capability-catalog";
import {
  RuntimeDatasetBindingCatalogList,
  RuntimeDatasetBindingCatalogShow,
  RuntimeDatasetBindingCatalogConfigureRuntimeDatasetBinding,
  RuntimeDatasetBindingCatalogValidateAgentDatasetAccess,
} from "../pages/runtime-dataset-binding-catalog";
import {
  RuntimeDatasetMetadataCatalogList,
  RuntimeDatasetMetadataCatalogShow,
  RuntimeDatasetMetadataCatalogConfigureRuntimeDatasetBinding,
} from "../pages/runtime-dataset-metadata-catalog";
import {
  RuntimeHealthDashboardList,
  RuntimeHealthDashboardShow,
} from "../pages/runtime-health-dashboard";
import {
  RuntimeIdentityCatalogList,
  RuntimeIdentityCatalogShow,
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
  RuntimeInstallationGuideCreateRuntimeInstallationPlan,
  RuntimeInstallationGuideRegisterRuntimeInfrastructure,
} from "../pages/runtime-installation-guide";
import {
  RuntimeInstallationPlanCatalogList,
  RuntimeInstallationPlanCatalogShow,
  RuntimeInstallationPlanCatalogCreateRuntimeInstallationPlan,
  RuntimeInstallationPlanCatalogRegisterRuntimeInfrastructure,
} from "../pages/runtime-installation-plan-catalog";
import {
  RuntimeNodeInventoryViewList,
  RuntimeNodeInventoryViewShow,
  RuntimeNodeInventoryViewRegisterRuntimeInfrastructure,
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
  SecureAggregationSessionCatalogCompleteSecureAggregation,
} from "../pages/secure-aggregation-session-catalog";
import {
  TrainingAlertCatalogList,
  TrainingAlertCatalogShow,
} from "../pages/training-alert-catalog";
import {
  TrainingJobDashboardList,
  TrainingJobDashboardShow,
  TrainingJobDashboardCreateTrainingJob,
} from "../pages/training-job-dashboard";
import {
  TrainingParticipantEligibilityList,
  TrainingParticipantEligibilityShow,
  TrainingParticipantEligibilityCreateTrainingJob,
  TrainingParticipantEligibilitySuspendParticipant,
  TrainingParticipantEligibilityRemoveParticipant,
  TrainingParticipantEligibilityInviteParticipant,
  TrainingParticipantEligibilityConfigureRuntimeDatasetBinding,
} from "../pages/training-participant-eligibility";
import {
  TrainingRoundProgressList,
  TrainingRoundProgressShow,
  TrainingRoundProgressRetryRoundExecutionAfterStartFailure,
  TrainingRoundProgressCompleteRoundExecution,
  TrainingRoundProgressFailRoundExecution,
  TrainingRoundProgressRetryRoundExecutionAfterRuntimeFailure,
  TrainingRoundProgressSubmitGlobalModelEvaluation,
} from "../pages/training-round-progress";
import {
  TrainingRunConfigurationCatalogList,
  TrainingRunConfigurationCatalogShow,
  TrainingRunConfigurationCatalogDefineTrainingRunConfiguration,
  TrainingRunConfigurationCatalogUpdateTrainingRunConfiguration,
  TrainingRunConfigurationCatalogCreateTrainingJob,
} from "../pages/training-run-configuration-catalog";

export const AppRouter = () => {
  return (
    <Routes>
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
          <Route path="command/validate-agent-dataset-access" element={<AgentDatasetAccessValidationCatalogValidateAgentDatasetAccess />} />
          <Route path="show/:id" element={<AgentDatasetAccessValidationCatalogShow />} />
          <Route path=":id/command/configure-runtime-dataset-binding" element={<AgentDatasetAccessValidationCatalogConfigureRuntimeDatasetBinding />} />
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
          <Route path="show/:id" element={<CurrentRecommendedFeatureSchemaCatalogShow />} />
        </Route>
        <Route path="/dataset-capability">
          <Route index element={<DatasetCapabilityList />} />
          <Route path="command/declare-dataset" element={<DatasetCapabilityDeclareDataset />} />
          <Route path="show/:id" element={<DatasetCapabilityShow />} />
          <Route path=":id/command/configure-runtime-dataset-binding" element={<DatasetCapabilityConfigureRuntimeDatasetBinding />} />
        </Route>
        <Route path="/dataset-readiness">
          <Route index element={<DatasetReadinessList />} />
          <Route path="command/declare-dataset" element={<DatasetReadinessDeclareDataset />} />
          <Route path="show/:id" element={<DatasetReadinessShow />} />
          <Route path=":id/command/configure-runtime-dataset-binding" element={<DatasetReadinessConfigureRuntimeDatasetBinding />} />
        </Route>
        <Route path="/dictionary-catalog">
          <Route index element={<DictionaryCatalogList />} />
          <Route path="command/register-dictionary" element={<DictionaryCatalogRegisterDictionary />} />
          <Route path="show/:id" element={<DictionaryCatalogShow />} />
          <Route path=":id/command/add-dictionary-value" element={<DictionaryCatalogAddDictionaryValue />} />
        </Route>
        <Route path="/dictionary-value-catalog">
          <Route index element={<DictionaryValueCatalogList />} />
          <Route path="command/add-dictionary-value" element={<DictionaryValueCatalogAddDictionaryValue />} />
          <Route path="show/:id" element={<DictionaryValueCatalogShow />} />
        </Route>
        <Route path="/feature-schema-catalog">
          <Route index element={<FeatureSchemaCatalogList />} />
          <Route path="command/define-feature-schema" element={<FeatureSchemaCatalogDefineFeatureSchema />} />
          <Route path="show/:id" element={<FeatureSchemaCatalogShow />} />
          <Route path=":id/command/supersede-feature-schema-version" element={<FeatureSchemaCatalogSupersedeFeatureSchemaVersion />} />
          <Route path=":id/command/declare-dataset" element={<FeatureSchemaCatalogDeclareDataset />} />
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
          <Route path=":id/command/create-runtime-installation-plan" element={<FederationMembershipDirectoryCreateRuntimeInstallationPlan />} />
        </Route>
        <Route path="/federation-overview">
          <Route index element={<FederationOverviewList />} />
          <Route path="command/create-federation" element={<FederationOverviewCreateFederation />} />
          <Route path="show/:id" element={<FederationOverviewShow />} />
        </Route>
        <Route path="/model-artifact-catalog">
          <Route index element={<ModelArtifactCatalogList />} />
          <Route path="command/register-model-artifact" element={<ModelArtifactCatalogRegisterModelArtifact />} />
          <Route path="show/:id" element={<ModelArtifactCatalogShow />} />
          <Route path=":id/command/submit-global-model-evaluation" element={<ModelArtifactCatalogSubmitGlobalModelEvaluation />} />
        </Route>
        <Route path="/model-version-catalog">
          <Route index element={<ModelVersionCatalogList />} />
          <Route path="show/:id" element={<ModelVersionCatalogShow />} />
          <Route path=":id/command/record-model-evaluation-package" element={<ModelVersionCatalogRecordModelEvaluationPackage />} />
          <Route path=":id/command/rollback-model-version" element={<ModelVersionCatalogRollbackModelVersion />} />
        </Route>
        <Route path="/organization-directory">
          <Route index element={<OrganizationDirectoryList />} />
          <Route path="command/register-organization" element={<OrganizationDirectoryRegisterOrganization />} />
          <Route path="show/:id" element={<OrganizationDirectoryShow />} />
          <Route path=":id/command/create-runtime-installation-plan" element={<OrganizationDirectoryCreateRuntimeInstallationPlan />} />
        </Route>
        <Route path="/round-execution-catalog">
          <Route index element={<RoundExecutionCatalogList />} />
          <Route path="show/:id" element={<RoundExecutionCatalogShow />} />
          <Route path=":id/command/retry-round-execution-after-start-failure" element={<RoundExecutionCatalogRetryRoundExecutionAfterStartFailure />} />
          <Route path=":id/command/complete-round-execution" element={<RoundExecutionCatalogCompleteRoundExecution />} />
          <Route path=":id/command/fail-round-execution" element={<RoundExecutionCatalogFailRoundExecution />} />
          <Route path=":id/command/retry-round-execution-after-runtime-failure" element={<RoundExecutionCatalogRetryRoundExecutionAfterRuntimeFailure />} />
          <Route path=":id/command/submit-model-update-submission" element={<RoundExecutionCatalogSubmitModelUpdateSubmission />} />
        </Route>
        <Route path="/runtime-agent-lifecycle-catalog">
          <Route index element={<RuntimeAgentLifecycleCatalogList />} />
          <Route path="command/report-runtime-agent-started" element={<RuntimeAgentLifecycleCatalogReportRuntimeAgentStarted />} />
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
          <Route path=":id/command/validate-agent-dataset-access" element={<RuntimeDatasetBindingCatalogValidateAgentDatasetAccess />} />
        </Route>
        <Route path="/runtime-dataset-metadata-catalog">
          <Route index element={<RuntimeDatasetMetadataCatalogList />} />
          <Route path="show/:id" element={<RuntimeDatasetMetadataCatalogShow />} />
          <Route path=":id/command/configure-runtime-dataset-binding" element={<RuntimeDatasetMetadataCatalogConfigureRuntimeDatasetBinding />} />
        </Route>
        <Route path="/runtime-health-dashboard">
          <Route index element={<RuntimeHealthDashboardList />} />
          <Route path="show/:id" element={<RuntimeHealthDashboardShow />} />
        </Route>
        <Route path="/runtime-identity-catalog">
          <Route index element={<RuntimeIdentityCatalogList />} />
          <Route path="show/:id" element={<RuntimeIdentityCatalogShow />} />
        </Route>
        <Route path="/runtime-infrastructure-access-view">
          <Route index element={<RuntimeInfrastructureAccessViewList />} />
          <Route path="command/register-runtime-infrastructure" element={<RuntimeInfrastructureAccessViewRegisterRuntimeInfrastructure />} />
          <Route path="show/:id" element={<RuntimeInfrastructureAccessViewShow />} />
        </Route>
        <Route path="/runtime-infrastructure-package-catalog">
          <Route index element={<RuntimeInfrastructurePackageCatalogList />} />
          <Route path="command/register-runtime-infrastructure-package" element={<RuntimeInfrastructurePackageCatalogRegisterRuntimeInfrastructurePackage />} />
          <Route path="show/:id" element={<RuntimeInfrastructurePackageCatalogShow />} />
        </Route>
        <Route path="/runtime-installation-guide">
          <Route index element={<RuntimeInstallationGuideList />} />
          <Route path="command/create-runtime-installation-plan" element={<RuntimeInstallationGuideCreateRuntimeInstallationPlan />} />
          <Route path="show/:id" element={<RuntimeInstallationGuideShow />} />
          <Route path=":id/command/register-runtime-infrastructure" element={<RuntimeInstallationGuideRegisterRuntimeInfrastructure />} />
        </Route>
        <Route path="/runtime-installation-plan-catalog">
          <Route index element={<RuntimeInstallationPlanCatalogList />} />
          <Route path="command/create-runtime-installation-plan" element={<RuntimeInstallationPlanCatalogCreateRuntimeInstallationPlan />} />
          <Route path="show/:id" element={<RuntimeInstallationPlanCatalogShow />} />
          <Route path=":id/command/register-runtime-infrastructure" element={<RuntimeInstallationPlanCatalogRegisterRuntimeInfrastructure />} />
        </Route>
        <Route path="/runtime-node-inventory-view">
          <Route index element={<RuntimeNodeInventoryViewList />} />
          <Route path="show/:id" element={<RuntimeNodeInventoryViewShow />} />
          <Route path=":id/command/register-runtime-infrastructure" element={<RuntimeNodeInventoryViewRegisterRuntimeInfrastructure />} />
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
          <Route path=":id/command/complete-secure-aggregation" element={<SecureAggregationSessionCatalogCompleteSecureAggregation />} />
        </Route>
        <Route path="/training-alert-catalog">
          <Route index element={<TrainingAlertCatalogList />} />
          <Route path="show/:id" element={<TrainingAlertCatalogShow />} />
        </Route>
        <Route path="/training-job-dashboard">
          <Route index element={<TrainingJobDashboardList />} />
          <Route path="command/create-training-job" element={<TrainingJobDashboardCreateTrainingJob />} />
          <Route path="show/:id" element={<TrainingJobDashboardShow />} />
        </Route>
        <Route path="/training-participant-eligibility">
          <Route index element={<TrainingParticipantEligibilityList />} />
          <Route path="command/create-training-job" element={<TrainingParticipantEligibilityCreateTrainingJob />} />
          <Route path="show/:id" element={<TrainingParticipantEligibilityShow />} />
          <Route path=":id/command/suspend-participant" element={<TrainingParticipantEligibilitySuspendParticipant />} />
          <Route path=":id/command/remove-participant" element={<TrainingParticipantEligibilityRemoveParticipant />} />
          <Route path=":id/command/invite-participant" element={<TrainingParticipantEligibilityInviteParticipant />} />
          <Route path=":id/command/configure-runtime-dataset-binding" element={<TrainingParticipantEligibilityConfigureRuntimeDatasetBinding />} />
        </Route>
        <Route path="/training-round-progress">
          <Route index element={<TrainingRoundProgressList />} />
          <Route path="show/:id" element={<TrainingRoundProgressShow />} />
          <Route path=":id/command/retry-round-execution-after-start-failure" element={<TrainingRoundProgressRetryRoundExecutionAfterStartFailure />} />
          <Route path=":id/command/complete-round-execution" element={<TrainingRoundProgressCompleteRoundExecution />} />
          <Route path=":id/command/fail-round-execution" element={<TrainingRoundProgressFailRoundExecution />} />
          <Route path=":id/command/retry-round-execution-after-runtime-failure" element={<TrainingRoundProgressRetryRoundExecutionAfterRuntimeFailure />} />
          <Route path=":id/command/submit-global-model-evaluation" element={<TrainingRoundProgressSubmitGlobalModelEvaluation />} />
        </Route>
        <Route path="/training-run-configuration-catalog">
          <Route index element={<TrainingRunConfigurationCatalogList />} />
          <Route path="command/define-training-run-configuration" element={<TrainingRunConfigurationCatalogDefineTrainingRunConfiguration />} />
          <Route path="edit/:id" element={<TrainingRunConfigurationCatalogUpdateTrainingRunConfiguration />} />
          <Route path="show/:id" element={<TrainingRunConfigurationCatalogShow />} />
          <Route path=":id/command/create-training-job" element={<TrainingRunConfigurationCatalogCreateTrainingJob />} />
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
