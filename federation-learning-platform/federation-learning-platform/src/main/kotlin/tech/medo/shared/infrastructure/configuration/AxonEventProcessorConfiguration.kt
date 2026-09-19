package tech.medo.shared.infrastructure.configuration

import org.axonframework.extension.spring.config.EventProcessorDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonEventProcessorConfiguration {
    @Bean
    fun auditTrailEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("audit-trail").notCustomized()

    @Bean
    fun automationDatasetGovernanceRecordRuntimeDatasetMetadataEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-dataset-governance-record-runtime-dataset-metadata").notCustomized()

    @Bean
    fun automationDatasetGovernanceRecordRuntimeDatasetReprofiledMetadataEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-dataset-governance-record-runtime-dataset-reprofiled-metadata").notCustomized()

    @Bean
    fun automationModelLifecycleRegisterCandidateModelEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-model-lifecycle-register-candidate-model").notCustomized()

    @Bean
    fun automationModelRepositoryRegisterFederatedModelArtifactEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-model-repository-register-federated-model-artifact").notCustomized()

    @Bean
    fun automationRuntimeGovernanceActivateRuntimeIdentityEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-governance-activate-runtime-identity").notCustomized()

    @Bean
    fun automationRuntimeGovernanceDetectRuntimeCapabilitiesEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-governance-detect-runtime-capabilities").notCustomized()

    @Bean
    fun automationRuntimeMonitoringAppendAuditTrailEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-monitoring-append-audit-trail").notCustomized()

    @Bean
    fun automationRuntimeMonitoringRaiseTrainingAlertEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-monitoring-raise-training-alert").notCustomized()

    @Bean
    fun automationRuntimeMonitoringRecordRuntimeNodeInventoryEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-monitoring-record-runtime-node-inventory").notCustomized()

    @Bean
    fun automationRuntimeProvisioningDeployRuntimeAgentEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-provisioning-deploy-runtime-agent").notCustomized()

    @Bean
    fun automationRuntimeProvisioningPlanRuntimeInfrastructureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-provisioning-plan-runtime-infrastructure").notCustomized()

    @Bean
    fun automationRuntimeProvisioningVerifyRuntimeInfrastructureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-runtime-provisioning-verify-runtime-infrastructure").notCustomized()

    @Bean
    fun automationSecureAggregationCompleteHomomorphicAggregationSessionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-secure-aggregation-complete-homomorphic-aggregation-session").notCustomized()

    @Bean
    fun automationSecureAggregationCreateSecureAggregationSessionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-secure-aggregation-create-secure-aggregation-session").notCustomized()

    @Bean
    fun automationSecureAggregationPrepareHomomorphicEncryptionContextEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-secure-aggregation-prepare-homomorphic-encryption-context").notCustomized()

    @Bean
    fun automationSecureAggregationRecordEncryptedModelUpdateEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-secure-aggregation-record-encrypted-model-update").notCustomized()

    @Bean
    fun automationSecureAggregationSelectSecureAggregationParticipantsEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-secure-aggregation-select-secure-aggregation-participants").notCustomized()

    @Bean
    fun automationTrainingOrchestrationAggregatePlainModelUpdatesEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-aggregate-plain-model-updates").notCustomized()

    @Bean
    fun automationTrainingOrchestrationCompleteModelAggregationEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-complete-model-aggregation").notCustomized()

    @Bean
    fun automationTrainingOrchestrationCompleteTrainingJobEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-complete-training-job").notCustomized()

    @Bean
    fun automationTrainingOrchestrationCompleteTrainingRoundEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-complete-training-round").notCustomized()

    @Bean
    fun automationTrainingOrchestrationDispatchParticipantExecutionPlanEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-dispatch-participant-execution-plan").notCustomized()

    @Bean
    fun automationTrainingOrchestrationEvaluateModelUpdateSubmissionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-evaluate-model-update-submission").notCustomized()

    @Bean
    fun automationTrainingOrchestrationFailTrainingRoundEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-fail-training-round").notCustomized()

    @Bean
    fun automationTrainingOrchestrationGenerateParticipantExecutionPlanEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-generate-participant-execution-plan").notCustomized()

    @Bean
    fun automationTrainingOrchestrationLockTrainingRunConfigurationEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-lock-training-run-configuration").notCustomized()

    @Bean
    fun automationTrainingOrchestrationRequestSecureAggregationEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-request-secure-aggregation").notCustomized()

    @Bean
    fun automationTrainingOrchestrationRetryTrainingRoundParticipantSelectionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-retry-training-round-participant-selection").notCustomized()

    @Bean
    fun automationTrainingOrchestrationScheduleNextTrainingRoundEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-schedule-next-training-round").notCustomized()

    @Bean
    fun automationTrainingOrchestrationSelectTrainingRoundParticipantsEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-select-training-round-participants").notCustomized()

    @Bean
    fun automationTrainingOrchestrationStartTrainingRoundEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-start-training-round").notCustomized()

    @Bean
    fun automationTrainingOrchestrationSubmitGlobalModelEvaluationEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("automation-training-orchestration-submit-global-model-evaluation").notCustomized()

    @Bean
    fun integrationDatasetGovernanceRecordRuntimeDatasetMetadataEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-dataset-governance-record-runtime-dataset-metadata").notCustomized()

    @Bean
    fun integrationDatasetGovernanceRecordRuntimeDatasetReprofiledMetadataEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-dataset-governance-record-runtime-dataset-reprofiled-metadata").notCustomized()

    @Bean
    fun integrationModelLifecycleRegisterCandidateModelEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-model-lifecycle-register-candidate-model").notCustomized()

    @Bean
    fun integrationModelRepositoryRegisterFederatedModelArtifactEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-model-repository-register-federated-model-artifact").notCustomized()

    @Bean
    fun integrationRuntimeGovernanceActivateRuntimeIdentityEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-governance-activate-runtime-identity").notCustomized()

    @Bean
    fun integrationRuntimeGovernanceDetectRuntimeCapabilitiesEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-governance-detect-runtime-capabilities").notCustomized()

    @Bean
    fun integrationRuntimeMonitoringAppendAuditTrailEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-monitoring-append-audit-trail").notCustomized()

    @Bean
    fun integrationRuntimeMonitoringRaiseTrainingAlertEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-monitoring-raise-training-alert").notCustomized()

    @Bean
    fun integrationRuntimeMonitoringRecordRuntimeNodeInventoryEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-monitoring-record-runtime-node-inventory").notCustomized()

    @Bean
    fun integrationRuntimeProvisioningDeployRuntimeAgentEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-provisioning-deploy-runtime-agent").notCustomized()

    @Bean
    fun integrationRuntimeProvisioningPlanRuntimeInfrastructureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-provisioning-plan-runtime-infrastructure").notCustomized()

    @Bean
    fun integrationRuntimeProvisioningVerifyRuntimeInfrastructureEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-runtime-provisioning-verify-runtime-infrastructure").notCustomized()

    @Bean
    fun integrationSecureAggregationCompleteHomomorphicAggregationSessionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-secure-aggregation-complete-homomorphic-aggregation-session").notCustomized()

    @Bean
    fun integrationSecureAggregationCreateSecureAggregationSessionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-secure-aggregation-create-secure-aggregation-session").notCustomized()

    @Bean
    fun integrationSecureAggregationPrepareHomomorphicEncryptionContextEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-secure-aggregation-prepare-homomorphic-encryption-context").notCustomized()

    @Bean
    fun integrationSecureAggregationRecordEncryptedModelUpdateEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-secure-aggregation-record-encrypted-model-update").notCustomized()

    @Bean
    fun integrationSecureAggregationSelectSecureAggregationParticipantsEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-secure-aggregation-select-secure-aggregation-participants").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationAggregatePlainModelUpdatesEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-aggregate-plain-model-updates").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationCompleteModelAggregationEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-complete-model-aggregation").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationCompleteTrainingJobEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-complete-training-job").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationCompleteTrainingRoundEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-complete-training-round").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationDispatchParticipantExecutionPlanEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-dispatch-participant-execution-plan").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationEvaluateModelUpdateSubmissionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-evaluate-model-update-submission").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationFailTrainingRoundEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-fail-training-round").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationGenerateParticipantExecutionPlanEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-generate-participant-execution-plan").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationLockTrainingRunConfigurationEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-lock-training-run-configuration").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationRequestSecureAggregationEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-request-secure-aggregation").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationRetryTrainingRoundParticipantSelectionEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-retry-training-round-participant-selection").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationScheduleNextTrainingRoundEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-schedule-next-training-round").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationSelectTrainingRoundParticipantsEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-select-training-round-participants").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationStartTrainingRoundEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-start-training-round").notCustomized()

    @Bean
    fun integrationTrainingOrchestrationSubmitGlobalModelEvaluationEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("integration-training-orchestration-submit-global-model-evaluation").notCustomized()

    @Bean
    fun readmodelAuditRecordLogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-audit-record-log").notCustomized()

    @Bean
    fun readmodelCurrentRecommendedFeatureSchemaCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-current-recommended-feature-schema-catalog").notCustomized()

    @Bean
    fun readmodelFeatureSchemaCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-feature-schema-catalog").notCustomized()

    @Bean
    fun readmodelFederationMembershipDirectoryEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-federation-membership-directory").notCustomized()

    @Bean
    fun readmodelFederationOverviewEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-federation-overview").notCustomized()

    @Bean
    fun readmodelModelArtifactCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-model-artifact-catalog").notCustomized()

    @Bean
    fun readmodelModelCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-model-catalog").notCustomized()

    @Bean
    fun readmodelOrganizationDirectoryEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-organization-directory").notCustomized()

    @Bean
    fun readmodelRuntimeAgentEndpointCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-agent-endpoint-catalog").notCustomized()

    @Bean
    fun readmodelRuntimeCapabilityCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-capability-catalog").notCustomized()

    @Bean
    fun readmodelRuntimeDatasetMetadataCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-dataset-metadata-catalog").notCustomized()

    @Bean
    fun readmodelRuntimeEngineProfileCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-engine-profile-catalog").notCustomized()

    @Bean
    fun readmodelRuntimeHealthDashboardEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-health-dashboard").notCustomized()

    @Bean
    fun readmodelRuntimeIdentityCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-identity-catalog").notCustomized()

    @Bean
    fun readmodelRuntimeInfrastructureAccessViewEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-infrastructure-access-view").notCustomized()

    @Bean
    fun readmodelRuntimeInfrastructurePackageCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-infrastructure-package-catalog").notCustomized()

    @Bean
    fun readmodelRuntimeInstallationGuideEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-installation-guide").notCustomized()

    @Bean
    fun readmodelRuntimeInstallationPlanCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-installation-plan-catalog").notCustomized()

    @Bean
    fun readmodelRuntimeNodeInventoryViewEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-node-inventory-view").notCustomized()

    @Bean
    fun readmodelRuntimeNodeResourceLatestEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-node-resource-latest").notCustomized()

    @Bean
    fun readmodelRuntimeTelemetryLatestEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-runtime-telemetry-latest").notCustomized()

    @Bean
    fun readmodelSecureAggregationSessionCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-secure-aggregation-session-catalog").notCustomized()

    @Bean
    fun readmodelTrainingAlertCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-training-alert-catalog").notCustomized()

    @Bean
    fun readmodelTrainingJobDashboardEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-training-job-dashboard").notCustomized()

    @Bean
    fun readmodelTrainingParticipantEligibilityEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-training-participant-eligibility").notCustomized()

    @Bean
    fun readmodelTrainingRoundProgressEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-training-round-progress").notCustomized()

    @Bean
    fun readmodelTrainingRunConfigurationCatalogEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-training-run-configuration-catalog").notCustomized()

    @Bean
    fun readmodelUserOrganizationMembershipDirectoryEventProcessorDefinition(): EventProcessorDefinition =
        EventProcessorDefinition.pooledStreamingMatching("readmodel-user-organization-membership-directory").notCustomized()
}
