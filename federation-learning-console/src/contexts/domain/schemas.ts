// Generated runtime schemas for domain value types and commands.
import { z } from "zod";

const dateTimeLocalSchema = z.preprocess((value) => {
  if (typeof value !== "string") return value;
  return /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}$/.test(value) ? `${value}:00` : value;
}, z.string().datetime({ local: true }));

export const OrganizationTypeSchema = z.enum(["HOSPITAL", "RESEARCH_INSTITUTE", "PUBLIC_HEALTH_AGENCY", "LABORATORY", "REHABILITATION_CENTER"]);
export const FeatureDefinitionSchema = z.object({
  featureName: z.string(),
  dataType: z.string(),
  required: z.boolean(),
  nullable: z.boolean(),
  description: z.string().optional().nullable(),
  validationRules: z.array(z.string()),
  defaultValue: z.string().optional().nullable(),
  isIdentifier: z.boolean(),
  isSensitive: z.boolean(),
  encodingStrategy: z.string().optional().nullable(),
  featureTags: z.array(z.string())
});
export const LabelDefinitionSchema = z.object({
  labelName: z.string(),
  dataType: z.string(),
  cardinality: z.coerce.number().int(),
  classLabels: z.array(z.string()).optional().nullable(),
  isMultilabel: z.boolean(),
  description: z.string().optional().nullable(),
  validationRules: z.array(z.string()),
  defaultValue: z.string().optional().nullable()
});
export const TrainingRoundParticipantSchema = z.object({
  organizationId: z.string().uuid(),
  runtimeId: z.string().uuid(),
  datasetId: z.string().uuid()
});
export const DictionaryCodeSchema = z.string();
export const DictionaryValueCodeSchema = z.string();
export const LocaleCodeSchema = z.string();
export const DisplayOrderSchema = z.coerce.number().int().min(0).max(999999);

export const RegisterOrganizationCommandSchema = z.object({
  organizationName: z.string(),
  organizationType: OrganizationTypeSchema,
  contactEmail: z.string(),
});
export type RegisterOrganizationCommandInput = z.infer<typeof RegisterOrganizationCommandSchema>;

export const ActivateOrganizationCommandSchema = z.object({
  organizationId: z.string().uuid(),
  organizationName: z.string(),
  activationNote: z.string().optional().nullable(),
});
export type ActivateOrganizationCommandInput = z.infer<typeof ActivateOrganizationCommandSchema>;

export const DeactivateOrganizationCommandSchema = z.object({
  organizationId: z.string().uuid(),
  organizationName: z.string(),
  deactivationReason: z.string(),
});
export type DeactivateOrganizationCommandInput = z.infer<typeof DeactivateOrganizationCommandSchema>;

export const ReactivateOrganizationCommandSchema = z.object({
  organizationId: z.string().uuid(),
  organizationName: z.string(),
  reactivationReason: z.string(),
});
export type ReactivateOrganizationCommandInput = z.infer<typeof ReactivateOrganizationCommandSchema>;

export const BindUserAccountToOrganizationCommandSchema = z.object({
  userAccountId: z.string().uuid(),
  username: z.string().optional().nullable(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  organizationUserRole: z.string().optional().nullable(),
});
export type BindUserAccountToOrganizationCommandInput = z.infer<typeof BindUserAccountToOrganizationCommandSchema>;

export const CreateFederationCommandSchema = z.object({
  federationName: z.string(),
  description: z.string(),
  minimumParticipantCount: z.coerce.number().int(),
});
export type CreateFederationCommandInput = z.infer<typeof CreateFederationCommandSchema>;

export const ActivateFederationCommandSchema = z.object({
  federationId: z.string().uuid(),
  activationNote: z.string().optional().nullable(),
  federationName: z.string(),
});
export type ActivateFederationCommandInput = z.infer<typeof ActivateFederationCommandSchema>;

export const SuspendFederationCommandSchema = z.object({
  federationId: z.string().uuid(),
  suspensionReason: z.string(),
  federationName: z.string(),
});
export type SuspendFederationCommandInput = z.infer<typeof SuspendFederationCommandSchema>;

export const ReactivateFederationCommandSchema = z.object({
  federationId: z.string().uuid(),
  reactivationReason: z.string(),
  federationName: z.string(),
});
export type ReactivateFederationCommandInput = z.infer<typeof ReactivateFederationCommandSchema>;

export const InviteParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  federationName: z.string().optional().nullable(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  invitationNote: z.string(),
});
export type InviteParticipantCommandInput = z.infer<typeof InviteParticipantCommandSchema>;

export const ApproveParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  federationName: z.string().optional().nullable(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  approvalNote: z.string().optional().nullable(),
});
export type ApproveParticipantCommandInput = z.infer<typeof ApproveParticipantCommandSchema>;

export const RejectParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  federationName: z.string().optional().nullable(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  rejectionReason: z.string(),
});
export type RejectParticipantCommandInput = z.infer<typeof RejectParticipantCommandSchema>;

export const RevokeParticipantInvitationCommandSchema = z.object({
  federationId: z.string().uuid(),
  federationName: z.string().optional().nullable(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  revokeReason: z.string(),
});
export type RevokeParticipantInvitationCommandInput = z.infer<typeof RevokeParticipantInvitationCommandSchema>;

export const SuspendParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  federationName: z.string().optional().nullable(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  suspensionReason: z.string(),
});
export type SuspendParticipantCommandInput = z.infer<typeof SuspendParticipantCommandSchema>;

export const RemoveParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  federationName: z.string().optional().nullable(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  removalReason: z.string(),
});
export type RemoveParticipantCommandInput = z.infer<typeof RemoveParticipantCommandSchema>;

export const RegisterRuntimeInfrastructurePackageCommandSchema = z.object({
  packageName: z.string(),
  packageVersion: z.string(),
  runtimeEnvironmentType: z.string(),
});
export type RegisterRuntimeInfrastructurePackageCommandInput = z.infer<typeof RegisterRuntimeInfrastructurePackageCommandSchema>;

export const CreateRuntimeInstallationPlanCommandSchema = z.object({
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  runtimeInfrastructurePackageId: z.string().uuid(),
  runtimeInfrastructurePackageName: z.string().optional().nullable(),
  runtimeInfrastructurePackageVersion: z.string().optional().nullable(),
  runtimeEnvironmentType: z.string().optional().nullable(),
  runtimeName: z.string(),
  agentInstallMode: z.string(),
  expectedNodeCount: z.coerce.number().int(),
});
export type CreateRuntimeInstallationPlanCommandInput = z.infer<typeof CreateRuntimeInstallationPlanCommandSchema>;

export const RegisterRuntimeInfrastructureCommandSchema = z.object({
  runtimeInfrastructureId: z.string().uuid(),
  runtimeInstallationPlanId: z.string().uuid(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  runtimeInfrastructurePackageId: z.string().uuid(),
  runtimeInfrastructurePackageName: z.string().optional().nullable(),
  runtimeInfrastructurePackageVersion: z.string().optional().nullable(),
  runtimeEnvironmentType: z.string().optional().nullable(),
  runtimeName: z.string(),
  agentInstallMode: z.string(),
  expectedNodeCount: z.coerce.number().int(),
});
export type RegisterRuntimeInfrastructureCommandInput = z.infer<typeof RegisterRuntimeInfrastructureCommandSchema>;

export const ConfirmRuntimeInfrastructurePreparedCommandSchema = z.object({
  runtimeInfrastructureId: z.string().uuid(),
  runtimeInstallationPlanId: z.string().uuid(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  runtimeInfrastructurePackageId: z.string().uuid(),
  runtimeInfrastructurePackageName: z.string().optional().nullable(),
  runtimeInfrastructurePackageVersion: z.string().optional().nullable(),
  runtimeEnvironmentType: z.string().optional().nullable(),
  runtimeName: z.string(),
  agentInstallMode: z.string(),
  expectedNodeCount: z.coerce.number().int(),
  runtimeAgentId: z.string().uuid(),
  preparedNodeCount: z.coerce.number().int(),
  preparationNotes: z.string().optional().nullable(),
});
export type ConfirmRuntimeInfrastructurePreparedCommandInput = z.infer<typeof ConfirmRuntimeInfrastructurePreparedCommandSchema>;

export const RetryRuntimeInfrastructureVerificationCommandSchema = z.object({
  runtimeInfrastructureId: z.string().uuid(),
  runtimeInstallationPlanId: z.string().uuid(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  runtimeInfrastructurePackageId: z.string().uuid(),
  runtimeInfrastructurePackageName: z.string().optional().nullable(),
  runtimeInfrastructurePackageVersion: z.string().optional().nullable(),
  runtimeEnvironmentType: z.string().optional().nullable(),
  runtimeName: z.string(),
  runtimeAgentId: z.string().uuid(),
  agentInstallMode: z.string(),
  expectedNodeCount: z.coerce.number().int(),
  currentRuntimeInfrastructureState: z.enum(["Planned", "Registered", "Prepared", "Verified", "VerificationFailed", "AgentReady", "RuntimeAgentFailed", "Offline", "Connected"]),
  retryReason: z.string(),
});
export type RetryRuntimeInfrastructureVerificationCommandInput = z.infer<typeof RetryRuntimeInfrastructureVerificationCommandSchema>;

export const RetryRuntimeAgentDeploymentCommandSchema = z.object({
  runtimeAgentId: z.string().uuid(),
  runtimeInfrastructureId: z.string().uuid(),
  runtimeInstallationPlanId: z.string().uuid(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  runtimeInfrastructurePackageId: z.string().uuid(),
  runtimeInfrastructurePackageName: z.string().optional().nullable(),
  runtimeInfrastructurePackageVersion: z.string().optional().nullable(),
  runtimeEnvironmentType: z.string().optional().nullable(),
  runtimeName: z.string(),
  agentInstallMode: z.string(),
  expectedNodeCount: z.coerce.number().int(),
  retryReason: z.string(),
});
export type RetryRuntimeAgentDeploymentCommandInput = z.infer<typeof RetryRuntimeAgentDeploymentCommandSchema>;

export const RecordRuntimeConnectionEstablishedCommandSchema = z.object({
  runtimeInfrastructureId: z.string().uuid(),
  runtimeAgentId: z.string().uuid(),
  agentInstallMode: z.string(),
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  runtimeName: z.string(),
  runtimeAgentEndpoint: z.string(),
  endpointScope: z.string(),
});
export type RecordRuntimeConnectionEstablishedCommandInput = z.infer<typeof RecordRuntimeConnectionEstablishedCommandSchema>;

export const DefineFeatureSchemaCommandSchema = z.object({
  featureDomain: z.string(),
  version: z.string(),
  dataModality: z.string(),
  features: z.preprocess((value) => {
    if (typeof value !== "string") return value;
    if (!value.trim()) return [];
    try {
      return JSON.parse(value);
    } catch {
      return value;
    }
  }, z.array(FeatureDefinitionSchema)),
  labels: z.preprocess((value) => {
    if (typeof value !== "string") return value;
    if (!value.trim()) return [];
    try {
      return JSON.parse(value);
    } catch {
      return value;
    }
  }, z.array(LabelDefinitionSchema)).optional().nullable(),
});
export type DefineFeatureSchemaCommandInput = z.infer<typeof DefineFeatureSchemaCommandSchema>;

export const PublishFeatureSchemaCommandSchema = z.object({
  featureSchemaId: z.string().uuid(),
  publishNote: z.string().optional().nullable(),
  featureDomain: z.string(),
  version: z.string(),
});
export type PublishFeatureSchemaCommandInput = z.infer<typeof PublishFeatureSchemaCommandSchema>;

export const DeprecateFeatureSchemaCommandSchema = z.object({
  featureSchemaId: z.string().uuid(),
  deprecationReason: z.string(),
  featureDomain: z.string(),
  version: z.string(),
});
export type DeprecateFeatureSchemaCommandInput = z.infer<typeof DeprecateFeatureSchemaCommandSchema>;

export const RetireFeatureSchemaCommandSchema = z.object({
  featureSchemaId: z.string().uuid(),
  retirementReason: z.string(),
  featureDomain: z.string(),
  version: z.string(),
});
export type RetireFeatureSchemaCommandInput = z.infer<typeof RetireFeatureSchemaCommandSchema>;

export const SupersedeFeatureSchemaVersionCommandSchema = z.object({
  featureSchemaId: z.string().uuid(),
  supersededByFeatureSchemaId: z.string().uuid(),
  supersessionReason: z.string().optional().nullable(),
  featureDomain: z.string(),
  version: z.string(),
});
export type SupersedeFeatureSchemaVersionCommandInput = z.infer<typeof SupersedeFeatureSchemaVersionCommandSchema>;

export const MarkCurrentRecommendedFeatureSchemaVersionCommandSchema = z.object({
  featureSchemaId: z.string().uuid(),
  recommendationNote: z.string().optional().nullable(),
  featureDomain: z.string(),
  version: z.string(),
});
export type MarkCurrentRecommendedFeatureSchemaVersionCommandInput = z.infer<typeof MarkCurrentRecommendedFeatureSchemaVersionCommandSchema>;

export const RegisterModelArtifactCommandSchema = z.object({
  modelName: z.string(),
  modelPlugin: z.string(),
  modelVersion: z.string(),
  modelDescription: z.string().optional().nullable(),
  sourceType: z.string(),
  fileId: z.string().uuid().optional().nullable(),
  modelFormat: z.string().optional().nullable(),
});
export type RegisterModelArtifactCommandInput = z.infer<typeof RegisterModelArtifactCommandSchema>;

export const DownloadModelArtifactCommandSchema = z.object({
  modelId: z.string().uuid(),
  modelName: z.string(),
  modelVersion: z.string(),
});
export type DownloadModelArtifactCommandInput = z.infer<typeof DownloadModelArtifactCommandSchema>;

export const RegisterRuntimeEngineProfileCommandSchema = z.object({
  profileName: z.string(),
  pluginProfile: z.string(),
  runtimeEngineImage: z.string(),
  imageDigest: z.string().optional().nullable(),
  supportedModelPluginsDescription: z.string().optional().nullable(),
  supportedAggregationAlgorithmsDescription: z.string().optional().nullable(),
  active: z.boolean(),
});
export type RegisterRuntimeEngineProfileCommandInput = z.infer<typeof RegisterRuntimeEngineProfileCommandSchema>;

export const DefineTrainingRunConfigurationCommandSchema = z.object({
  configurationName: z.string(),
  federationId: z.string().uuid(),
  federationName: z.string().optional().nullable(),
  featureSchemaId: z.string().uuid(),
  featureDomain: z.string().optional().nullable(),
  featureSchemaVersion: z.string().optional().nullable(),
  initialModelId: z.string().uuid(),
  initialModelName: z.string().optional().nullable(),
  initialModelPlugin: z.string().optional().nullable(),
  initialModelVersion: z.string().optional().nullable(),
  runtimeEngineProfileId: z.string().uuid(),
  runtimeEngineProfileName: z.string().optional().nullable(),
  runtimeEnginePluginProfile: z.string(),
  runtimeEngineImage: z.string(),
  runtimeEngineImageDigest: z.string().optional().nullable(),
  strategyName: z.string(),
  aggregationAlgorithm: z.string(),
  maxRounds: z.coerce.number().int(),
  minimumNodesPerRound: z.coerce.number().int(),
  roundTimeoutSeconds: z.coerce.number().int(),
  nodeResponseTimeoutSeconds: z.coerce.number().int(),
  localEpochs: z.coerce.number().int(),
  batchSize: z.coerce.number().int(),
  learningRate: z.coerce.number(),
  optimizer: z.string(),
  lossFunction: z.string(),
  gradientClippingNorm: z.coerce.number().optional().nullable(),
  secureAggregationRequired: z.boolean(),
  minimumAccuracy: z.coerce.number(),
  minimumFairnessScore: z.coerce.number().optional().nullable(),
});
export type DefineTrainingRunConfigurationCommandInput = z.infer<typeof DefineTrainingRunConfigurationCommandSchema>;

export const UpdateTrainingRunConfigurationCommandSchema = z.object({
  trainingRunConfigurationId: z.string().uuid(),
  configurationName: z.string(),
  federationId: z.string().uuid(),
  federationName: z.string().optional().nullable(),
  featureSchemaId: z.string().uuid(),
  featureDomain: z.string().optional().nullable(),
  featureSchemaVersion: z.string().optional().nullable(),
  initialModelId: z.string().uuid(),
  initialModelName: z.string().optional().nullable(),
  initialModelPlugin: z.string().optional().nullable(),
  initialModelVersion: z.string().optional().nullable(),
  runtimeEngineProfileId: z.string().uuid(),
  runtimeEngineProfileName: z.string().optional().nullable(),
  runtimeEnginePluginProfile: z.string(),
  runtimeEngineImage: z.string(),
  runtimeEngineImageDigest: z.string().optional().nullable(),
  strategyName: z.string(),
  aggregationAlgorithm: z.string(),
  maxRounds: z.coerce.number().int(),
  minimumNodesPerRound: z.coerce.number().int(),
  roundTimeoutSeconds: z.coerce.number().int(),
  nodeResponseTimeoutSeconds: z.coerce.number().int(),
  localEpochs: z.coerce.number().int(),
  batchSize: z.coerce.number().int(),
  learningRate: z.coerce.number(),
  optimizer: z.string(),
  lossFunction: z.string(),
  gradientClippingNorm: z.coerce.number().optional().nullable(),
  secureAggregationRequired: z.boolean(),
  minimumAccuracy: z.coerce.number(),
  minimumFairnessScore: z.coerce.number().optional().nullable(),
  updateReason: z.string().optional().nullable(),
});
export type UpdateTrainingRunConfigurationCommandInput = z.infer<typeof UpdateTrainingRunConfigurationCommandSchema>;

export const CreateTrainingJobCommandSchema = z.object({
  federationId: z.string().uuid(),
  federationName: z.string().optional().nullable(),
  trainingRunConfigurationId: z.string().uuid(),
  configurationName: z.string().optional().nullable(),
  featureDomain: z.string().optional().nullable(),
  featureSchemaVersion: z.string().optional().nullable(),
  objective: z.string(),
});
export type CreateTrainingJobCommandInput = z.infer<typeof CreateTrainingJobCommandSchema>;

export const SubmitTrainingJobCommandSchema = z.object({
  trainingJobId: z.string().uuid(),
});
export type SubmitTrainingJobCommandInput = z.infer<typeof SubmitTrainingJobCommandSchema>;

export const PauseTrainingJobCommandSchema = z.object({
  trainingJobId: z.string().uuid(),
  pauseReason: z.string(),
});
export type PauseTrainingJobCommandInput = z.infer<typeof PauseTrainingJobCommandSchema>;

export const ResumeTrainingJobCommandSchema = z.object({
  trainingJobId: z.string().uuid(),
  resumeReason: z.string().optional().nullable(),
});
export type ResumeTrainingJobCommandInput = z.infer<typeof ResumeTrainingJobCommandSchema>;

export const CancelTrainingJobCommandSchema = z.object({
  trainingJobId: z.string().uuid(),
  cancelReason: z.string().optional().nullable(),
});
export type CancelTrainingJobCommandInput = z.infer<typeof CancelTrainingJobCommandSchema>;

export const RetryTrainingRoundParticipantSelectionCommandSchema = z.object({
  trainingJobId: z.string().uuid(),
});
export type RetryTrainingRoundParticipantSelectionCommandInput = z.infer<typeof RetryTrainingRoundParticipantSelectionCommandSchema>;

export const SubmitModelUpdateSubmissionCommandSchema = z.object({
  executionSessionId: z.string().uuid(),
  executionPlanId: z.string().uuid(),
  trainingJobId: z.string().uuid(),
  trainingRunConfigurationId: z.string().uuid(),
  roundId: z.string().uuid(),
  roundExecutionId: z.string().uuid(),
  runtimeId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  secureAggregationRequired: z.boolean(),
  secureAggregationSessionId: z.string().uuid().optional().nullable(),
  encryptionScheme: z.string().optional().nullable(),
  publicKeyVersion: z.string().optional().nullable(),
  localModelId: z.string().uuid(),
  updateArtifactId: z.string().uuid(),
  artifactRef: z.string(),
  artifactDigest: z.string(),
  updateProtectionType: z.string(),
  trainingLoss: z.coerce.number(),
});
export type SubmitModelUpdateSubmissionCommandInput = z.infer<typeof SubmitModelUpdateSubmissionCommandSchema>;

export const RecordModelEvaluationPackageCommandSchema = z.object({
  modelId: z.string().uuid(),
  trainingJobId: z.string().uuid(),
  evaluationReportId: z.string().uuid(),
  experimentId: z.string().uuid(),
  hyperparameterSnapshotId: z.string().uuid(),
  reproducibilityManifestId: z.string().uuid(),
  modelCardId: z.string().uuid(),
  baselineModelId: z.string().uuid().optional().nullable(),
});
export type RecordModelEvaluationPackageCommandInput = z.infer<typeof RecordModelEvaluationPackageCommandSchema>;

export const ApproveModelCommandSchema = z.object({
  modelId: z.string().uuid(),
  approvalNote: z.string().optional().nullable(),
});
export type ApproveModelCommandInput = z.infer<typeof ApproveModelCommandSchema>;

export const PromoteModelToProductionCommandSchema = z.object({
  modelId: z.string().uuid(),
  releaseChannel: z.string(),
  productionStage: z.string(),
});
export type PromoteModelToProductionCommandInput = z.infer<typeof PromoteModelToProductionCommandSchema>;

export const RollbackModelCommandSchema = z.object({
  modelId: z.string().uuid(),
  previousModelId: z.string().uuid(),
  rollbackReason: z.string(),
});
export type RollbackModelCommandInput = z.infer<typeof RollbackModelCommandSchema>;

export const RetireModelCommandSchema = z.object({
  modelId: z.string().uuid(),
  retirementReason: z.string(),
});
export type RetireModelCommandInput = z.infer<typeof RetireModelCommandSchema>;

export const RecordRuntimeTelemetryCommandSchema = z.object({
  nodeId: z.string().uuid(),
  runtimeAgentId: z.string().uuid(),
  federationId: z.string().uuid().optional().nullable(),
  federationName: z.string().optional().nullable(),
  trainingJobId: z.string().uuid().optional().nullable(),
  trainingJobObjective: z.string().optional().nullable(),
  roundExecutionId: z.string().uuid().optional().nullable(),
  runtimeNodeName: z.string().optional().nullable(),
  cpuLoad: z.coerce.number().optional().nullable(),
  gpuLoad: z.coerce.number().optional().nullable(),
  memoryLoad: z.coerce.number().optional().nullable(),
  lastHeartbeatAt: dateTimeLocalSchema.optional().nullable(),
  lastRecoveredAt: dateTimeLocalSchema.optional().nullable(),
  offlineDetectionPending: z.boolean(),
  recoveryDetectionPending: z.boolean(),
  resourcePressureDetectionPending: z.boolean(),
  offlineReason: z.string().optional().nullable(),
  recoveryReason: z.string().optional().nullable(),
  pressureType: z.string().optional().nullable(),
  observedValue: z.coerce.number().optional().nullable(),
  thresholdValue: z.coerce.number().optional().nullable(),
  alertSeverity: z.string().optional().nullable(),
  alertMessage: z.string().optional().nullable(),
  healthStatus: z.string(),
  telemetryRetentionPolicy: z.string(),
});
export type RecordRuntimeTelemetryCommandInput = z.infer<typeof RecordRuntimeTelemetryCommandSchema>;

export const RecordRuntimeNodeResourceTelemetryCommandSchema = z.object({
  nodeId: z.string().uuid(),
  runtimeAgentId: z.string().uuid(),
  runtimeInfrastructureId: z.string().uuid().optional().nullable(),
  runtimeNodeName: z.string().optional().nullable(),
  nodeReady: z.boolean(),
  allocatableCpuCores: z.coerce.number().int(),
  allocatableMemoryGb: z.coerce.number().int(),
  allocatableGpuCount: z.coerce.number().int(),
  allocatedCpuCores: z.coerce.number().int(),
  allocatedMemoryGb: z.coerce.number().int(),
  allocatedGpuCount: z.coerce.number().int(),
  availableCpuCores: z.coerce.number().int(),
  availableMemoryGb: z.coerce.number().int(),
  availableGpuCount: z.coerce.number().int(),
  runningWorkloadCount: z.coerce.number().int(),
  workloadCapacity: z.coerce.number().int(),
  observedAt: dateTimeLocalSchema,
  lastResourceSnapshotAt: dateTimeLocalSchema,
  telemetryRetentionPolicy: z.string(),
});
export type RecordRuntimeNodeResourceTelemetryCommandInput = z.infer<typeof RecordRuntimeNodeResourceTelemetryCommandSchema>;

export const AcknowledgeTrainingAlertCommandSchema = z.object({
  alertId: z.string().uuid(),
  acknowledgementNote: z.string().optional().nullable(),
});
export type AcknowledgeTrainingAlertCommandInput = z.infer<typeof AcknowledgeTrainingAlertCommandSchema>;

export const ResolveTrainingAlertCommandSchema = z.object({
  alertId: z.string().uuid(),
  resolutionSummary: z.string(),
});
export type ResolveTrainingAlertCommandInput = z.infer<typeof ResolveTrainingAlertCommandSchema>;

export const RevokeRuntimeIdentityCommandSchema = z.object({
  runtimeId: z.string().uuid(),
  revocationReason: z.string(),
});
export type RevokeRuntimeIdentityCommandInput = z.infer<typeof RevokeRuntimeIdentityCommandSchema>;

export const FailSecureAggregationSessionCommandSchema = z.object({
  secureAggregationSessionId: z.string().uuid(),
  failureReason: z.string(),
});
export type FailSecureAggregationSessionCommandInput = z.infer<typeof FailSecureAggregationSessionCommandSchema>;

export const UploadFileCommandSchema = z.object({
  uploadedFile: z.string(),
  originalFileName: z.string(),
  contentType: z.string().optional().nullable(),
  sizeBytes: z.coerce.number().optional().nullable(),
  fileLocation: z.string(),
  checksum: z.string().optional().nullable(),
  expiresAt: dateTimeLocalSchema,
  purpose: z.string(),
});
export type UploadFileCommandInput = z.infer<typeof UploadFileCommandSchema>;

export const MarkFileReferencedCommandSchema = z.object({
  fileId: z.string().uuid(),
  referencedByContext: z.string(),
  referencedByCommand: z.string(),
  referencedByCommandId: z.string().uuid().optional().nullable(),
});
export type MarkFileReferencedCommandInput = z.infer<typeof MarkFileReferencedCommandSchema>;

export const DownloadFileCommandSchema = z.object({
  fileId: z.string().uuid(),
});
export type DownloadFileCommandInput = z.infer<typeof DownloadFileCommandSchema>;

export const DiscardFileCommandSchema = z.object({
  fileId: z.string().uuid(),
  discardReason: z.string().optional().nullable(),
});
export type DiscardFileCommandInput = z.infer<typeof DiscardFileCommandSchema>;

export const RegisterDictionaryCommandSchema = z.object({
  dictionaryCode: DictionaryCodeSchema,
  dictionaryName: z.string(),
  description: z.string().optional().nullable(),
});
export type RegisterDictionaryCommandInput = z.infer<typeof RegisterDictionaryCommandSchema>;

export const UpdateDictionaryCommandSchema = z.object({
  dictionaryId: z.string().uuid(),
  dictionaryName: z.string(),
  description: z.string().optional().nullable(),
  dictionaryCode: DictionaryCodeSchema,
});
export type UpdateDictionaryCommandInput = z.infer<typeof UpdateDictionaryCommandSchema>;

export const ArchiveDictionaryCommandSchema = z.object({
  dictionaryId: z.string().uuid(),
  archiveReason: z.string(),
  dictionaryCode: DictionaryCodeSchema,
});
export type ArchiveDictionaryCommandInput = z.infer<typeof ArchiveDictionaryCommandSchema>;

export const AddDictionaryValueCommandSchema = z.object({
  dictionaryId: z.string().uuid(),
  dictionaryCode: DictionaryCodeSchema,
  valueCode: DictionaryValueCodeSchema,
  defaultDisplayName: z.string(),
  displayOrder: DisplayOrderSchema.optional().nullable(),
  description: z.string().optional().nullable(),
  active: z.boolean(),
});
export type AddDictionaryValueCommandInput = z.infer<typeof AddDictionaryValueCommandSchema>;

export const DisableDictionaryValueCommandSchema = z.object({
  dictionaryValueId: z.string().uuid(),
  disabledReason: z.string(),
  dictionaryCode: DictionaryCodeSchema,
  valueCode: DictionaryValueCodeSchema,
});
export type DisableDictionaryValueCommandInput = z.infer<typeof DisableDictionaryValueCommandSchema>;

export const EnableDictionaryValueCommandSchema = z.object({
  dictionaryValueId: z.string().uuid(),
  enableReason: z.string(),
  dictionaryCode: DictionaryCodeSchema,
  valueCode: DictionaryValueCodeSchema,
});
export type EnableDictionaryValueCommandInput = z.infer<typeof EnableDictionaryValueCommandSchema>;

export const SetDictionaryValueTranslationCommandSchema = z.object({
  dictionaryValueTranslationId: z.string().uuid(),
  dictionaryValueId: z.string().uuid(),
  dictionaryCode: DictionaryCodeSchema,
  valueCode: DictionaryValueCodeSchema,
  locale: LocaleCodeSchema,
  displayName: z.string(),
  description: z.string().optional().nullable(),
});
export type SetDictionaryValueTranslationCommandInput = z.infer<typeof SetDictionaryValueTranslationCommandSchema>;

export const UpdateDictionaryValueTranslationCommandSchema = z.object({
  dictionaryValueTranslationId: z.string().uuid(),
  dictionaryValueId: z.string().uuid(),
  dictionaryCode: DictionaryCodeSchema,
  valueCode: DictionaryValueCodeSchema,
  locale: LocaleCodeSchema,
  displayName: z.string(),
  description: z.string().optional().nullable(),
});
export type UpdateDictionaryValueTranslationCommandInput = z.infer<typeof UpdateDictionaryValueTranslationCommandSchema>;

export const RegisterUserAccountCommandSchema = z.object({
  username: z.string(),
  providerSubject: z.string().optional().nullable(),
  userSource: z.string().optional().nullable(),
  passwordHash: z.string().optional().nullable(),
});
export type RegisterUserAccountCommandInput = z.infer<typeof RegisterUserAccountCommandSchema>;

export const DeactivateUserAccountCommandSchema = z.object({
  userAccountId: z.string().uuid(),
  reason: z.string(),
});
export type DeactivateUserAccountCommandInput = z.infer<typeof DeactivateUserAccountCommandSchema>;

export const GenerateUserAccountLoginPasswordCommandSchema = z.object({
  userAccountId: z.string().uuid(),
  passwordResetRequired: z.boolean(),
});
export type GenerateUserAccountLoginPasswordCommandInput = z.infer<typeof GenerateUserAccountLoginPasswordCommandSchema>;

export const RegisterRoleCommandSchema = z.object({
  roleCode: z.string(),
  roleName: z.string(),
});
export type RegisterRoleCommandInput = z.infer<typeof RegisterRoleCommandSchema>;

export const RegisterPermissionCommandSchema = z.object({
  permissionCode: z.string(),
  permissionName: z.string(),
  description: z.string().optional().nullable(),
});
export type RegisterPermissionCommandInput = z.infer<typeof RegisterPermissionCommandSchema>;

export const GrantPermissionToRoleCommandSchema = z.object({
  roleId: z.string().uuid(),
  roleCode: z.string(),
  permissionCodes: z.array(z.string()),
});
export type GrantPermissionToRoleCommandInput = z.infer<typeof GrantPermissionToRoleCommandSchema>;

export const AssignRoleToUserCommandSchema = z.object({
  userAccountId: z.string().uuid(),
  roleCodes: z.array(z.string()),
});
export type AssignRoleToUserCommandInput = z.infer<typeof AssignRoleToUserCommandSchema>;

export const IssueServiceAccountApiTokenCommandSchema = z.object({
  userAccountId: z.string().uuid(),
  tokenName: z.string(),
});
export type IssueServiceAccountApiTokenCommandInput = z.infer<typeof IssueServiceAccountApiTokenCommandSchema>;

export const RequestDataExportCommandSchema = z.object({
  dataExportJobId: z.string().uuid(),
  resourceName: z.string(),
  criteriaJson: z.string(),
  sortJson: z.string(),
  columnsJson: z.string(),
  requestedLocale: z.string().optional().nullable(),
  requestedAt: dateTimeLocalSchema,
  snapshotUpperBound: dateTimeLocalSchema,
  requestHash: z.string(),
  fileName: z.string(),
  status: z.string(),
});
export type RequestDataExportCommandInput = z.infer<typeof RequestDataExportCommandSchema>;

