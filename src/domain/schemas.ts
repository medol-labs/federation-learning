// Generated runtime schemas for domain value types and commands.
import { z } from "zod";

const dateTimeLocalSchema = z.preprocess((value) => {
  if (typeof value !== "string") return value;
  return /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}$/.test(value) ? `${value}:00` : value;
}, z.string().datetime({ local: true }));

export const OrganizationTypeSchema = z.enum(["HOSPITAL", "RESEARCH_INSTITUTE", "PUBLIC_HEALTH_AGENCY", "LABORATORY", "REHABILITATION_CENTER"]);
export const DictionaryCodeSchema = z.string();
export const DictionaryValueCodeSchema = z.string();
export const DisplayOrderSchema = z.coerce.number().int().min(0).max(999999);
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

export const RegisterOrganizationCommandSchema = z.object({
  organizationName: z.string(),
  organizationType: OrganizationTypeSchema,
  contactEmail: z.string(),
});
export type RegisterOrganizationCommandInput = z.infer<typeof RegisterOrganizationCommandSchema>;

export const ActivateOrganizationCommandSchema = z.object({
  organizationId: z.string().uuid(),
  activationNote: z.string().optional().nullable(),
});
export type ActivateOrganizationCommandInput = z.infer<typeof ActivateOrganizationCommandSchema>;

export const DeactivateOrganizationCommandSchema = z.object({
  organizationId: z.string().uuid(),
  deactivationReason: z.string(),
});
export type DeactivateOrganizationCommandInput = z.infer<typeof DeactivateOrganizationCommandSchema>;

export const ReactivateOrganizationCommandSchema = z.object({
  organizationId: z.string().uuid(),
  reactivationReason: z.string(),
});
export type ReactivateOrganizationCommandInput = z.infer<typeof ReactivateOrganizationCommandSchema>;

export const CreateFederationCommandSchema = z.object({
  federationName: z.string(),
  description: z.string(),
  minimumParticipantCount: z.coerce.number().int(),
});
export type CreateFederationCommandInput = z.infer<typeof CreateFederationCommandSchema>;

export const ActivateFederationCommandSchema = z.object({
  federationId: z.string().uuid(),
  activationNote: z.string().optional().nullable(),
});
export type ActivateFederationCommandInput = z.infer<typeof ActivateFederationCommandSchema>;

export const SuspendFederationCommandSchema = z.object({
  federationId: z.string().uuid(),
  suspensionReason: z.string(),
});
export type SuspendFederationCommandInput = z.infer<typeof SuspendFederationCommandSchema>;

export const ReactivateFederationCommandSchema = z.object({
  federationId: z.string().uuid(),
  reactivationReason: z.string(),
});
export type ReactivateFederationCommandInput = z.infer<typeof ReactivateFederationCommandSchema>;

export const InviteParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  organizationId: z.string().uuid(),
  invitationNote: z.string(),
});
export type InviteParticipantCommandInput = z.infer<typeof InviteParticipantCommandSchema>;

export const ApproveParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  organizationId: z.string().uuid(),
  approvalNote: z.string().optional().nullable(),
});
export type ApproveParticipantCommandInput = z.infer<typeof ApproveParticipantCommandSchema>;

export const RejectParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  organizationId: z.string().uuid(),
  rejectionReason: z.string(),
});
export type RejectParticipantCommandInput = z.infer<typeof RejectParticipantCommandSchema>;

export const RevokeParticipantInvitationCommandSchema = z.object({
  federationId: z.string().uuid(),
  organizationId: z.string().uuid(),
  revokeReason: z.string(),
});
export type RevokeParticipantInvitationCommandInput = z.infer<typeof RevokeParticipantInvitationCommandSchema>;

export const SuspendParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  organizationId: z.string().uuid(),
  suspensionReason: z.string(),
});
export type SuspendParticipantCommandInput = z.infer<typeof SuspendParticipantCommandSchema>;

export const RemoveParticipantCommandSchema = z.object({
  federationId: z.string().uuid(),
  organizationId: z.string().uuid(),
  removalReason: z.string(),
});
export type RemoveParticipantCommandInput = z.infer<typeof RemoveParticipantCommandSchema>;

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
});
export type UpdateDictionaryCommandInput = z.infer<typeof UpdateDictionaryCommandSchema>;

export const ArchiveDictionaryCommandSchema = z.object({
  dictionaryId: z.string().uuid(),
  archiveReason: z.string(),
});
export type ArchiveDictionaryCommandInput = z.infer<typeof ArchiveDictionaryCommandSchema>;

export const AddDictionaryValueCommandSchema = z.object({
  dictionaryId: z.string().uuid(),
  dictionaryCode: DictionaryCodeSchema,
  valueCode: DictionaryValueCodeSchema,
  displayName: z.string(),
  displayOrder: DisplayOrderSchema.optional().nullable(),
  description: z.string().optional().nullable(),
  active: z.boolean(),
});
export type AddDictionaryValueCommandInput = z.infer<typeof AddDictionaryValueCommandSchema>;

export const DisableDictionaryValueCommandSchema = z.object({
  dictionaryValueId: z.string().uuid(),
  disabledReason: z.string(),
});
export type DisableDictionaryValueCommandInput = z.infer<typeof DisableDictionaryValueCommandSchema>;

export const EnableDictionaryValueCommandSchema = z.object({
  dictionaryValueId: z.string().uuid(),
  enableReason: z.string(),
});
export type EnableDictionaryValueCommandInput = z.infer<typeof EnableDictionaryValueCommandSchema>;

export const StageFileUploadCommandSchema = z.object({
  uploadedFile: z.string(),
  originalFileName: z.string(),
  contentType: z.string().optional().nullable(),
  sizeBytes: z.coerce.number().optional().nullable(),
  stagedFileLocation: z.string(),
  checksum: z.string().optional().nullable(),
  expiresAt: dateTimeLocalSchema,
  purpose: z.string(),
});
export type StageFileUploadCommandInput = z.infer<typeof StageFileUploadCommandSchema>;

export const MarkStagedFileConsumedCommandSchema = z.object({
  stagedFileId: z.string().uuid(),
  consumedByContext: z.string(),
  consumedByCommand: z.string(),
  consumedByCommandId: z.string().uuid().optional().nullable(),
});
export type MarkStagedFileConsumedCommandInput = z.infer<typeof MarkStagedFileConsumedCommandSchema>;

export const DiscardStagedFileCommandSchema = z.object({
  stagedFileId: z.string().uuid(),
  discardReason: z.string().optional().nullable(),
});
export type DiscardStagedFileCommandInput = z.infer<typeof DiscardStagedFileCommandSchema>;

export const RegisterRuntimeInfrastructurePackageCommandSchema = z.object({
  packageName: z.string(),
  packageVersion: z.string(),
  runtimeEnvironmentType: z.string(),
  runtimeDeploymentTargetType: z.string(),
  installProfile: z.string(),
  architecture: z.string(),
  installGuide: z.string(),
});
export type RegisterRuntimeInfrastructurePackageCommandInput = z.infer<typeof RegisterRuntimeInfrastructurePackageCommandSchema>;

export const CreateRuntimeInstallationPlanCommandSchema = z.object({
  organizationId: z.string().uuid(),
  runtimeInfrastructurePackageId: z.string().uuid(),
  runtimeName: z.string(),
  agentInstallMode: z.string(),
  expectedNodeCount: z.coerce.number().int(),
});
export type CreateRuntimeInstallationPlanCommandInput = z.infer<typeof CreateRuntimeInstallationPlanCommandSchema>;

export const RegisterRuntimeInfrastructureCommandSchema = z.object({
  runtimeInfrastructureId: z.string().uuid(),
});
export type RegisterRuntimeInfrastructureCommandInput = z.infer<typeof RegisterRuntimeInfrastructureCommandSchema>;

export const RetryRuntimeAgentDeploymentCommandSchema = z.object({
  runtimeAgentId: z.string().uuid(),
  runtimeInfrastructureId: z.string().uuid(),
  currentRuntimeInfrastructureState: z.string(),
  retryReason: z.string(),
});
export type RetryRuntimeAgentDeploymentCommandInput = z.infer<typeof RetryRuntimeAgentDeploymentCommandSchema>;

export const RecordRuntimeConnectionEstablishedCommandSchema = z.object({
  runtimeInfrastructureId: z.string().uuid(),
  runtimeAgentId: z.string().uuid(),
  agentInstallMode: z.string(),
  organizationId: z.string().uuid(),
  runtimeName: z.string(),
});
export type RecordRuntimeConnectionEstablishedCommandInput = z.infer<typeof RecordRuntimeConnectionEstablishedCommandSchema>;

export const DefineFeatureSchemaCommandSchema = z.object({
  featureDomain: z.string(),
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
});
export type PublishFeatureSchemaCommandInput = z.infer<typeof PublishFeatureSchemaCommandSchema>;

export const DeprecateFeatureSchemaCommandSchema = z.object({
  featureSchemaId: z.string().uuid(),
  deprecationReason: z.string(),
});
export type DeprecateFeatureSchemaCommandInput = z.infer<typeof DeprecateFeatureSchemaCommandSchema>;

export const RetireFeatureSchemaCommandSchema = z.object({
  featureSchemaId: z.string().uuid(),
  retirementReason: z.string(),
});
export type RetireFeatureSchemaCommandInput = z.infer<typeof RetireFeatureSchemaCommandSchema>;

export const SupersedeFeatureSchemaVersionCommandSchema = z.object({
  featureSchemaId: z.string().uuid(),
  supersededByFeatureSchemaId: z.string().uuid(),
  supersessionReason: z.string().optional().nullable(),
});
export type SupersedeFeatureSchemaVersionCommandInput = z.infer<typeof SupersedeFeatureSchemaVersionCommandSchema>;

export const MarkCurrentRecommendedFeatureSchemaVersionCommandSchema = z.object({
  featureSchemaId: z.string().uuid(),
  recommendationNote: z.string().optional().nullable(),
});
export type MarkCurrentRecommendedFeatureSchemaVersionCommandInput = z.infer<typeof MarkCurrentRecommendedFeatureSchemaVersionCommandSchema>;

export const RegisterModelArtifactCommandSchema = z.object({
  modelName: z.string(),
  modelVersion: z.string(),
  sourceType: z.string(),
  stagedFileId: z.string().uuid().optional().nullable(),
  modelFormat: z.string().optional().nullable(),
});
export type RegisterModelArtifactCommandInput = z.infer<typeof RegisterModelArtifactCommandSchema>;

export const DefineTrainingRunConfigurationCommandSchema = z.object({
  federationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  initialModelId: z.string().uuid(),
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
  differentialPrivacyEnabled: z.boolean(),
  dpNoiseMultiplier: z.coerce.number().optional().nullable(),
  dpClipNorm: z.coerce.number().optional().nullable(),
  minimumAccuracy: z.coerce.number(),
  minimumFairnessScore: z.coerce.number().optional().nullable(),
  failureToleranceRatio: z.coerce.number(),
});
export type DefineTrainingRunConfigurationCommandInput = z.infer<typeof DefineTrainingRunConfigurationCommandSchema>;

export const UpdateTrainingRunConfigurationCommandSchema = z.object({
  trainingRunConfigurationId: z.string().uuid(),
  federationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  initialModelId: z.string().uuid(),
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
  differentialPrivacyEnabled: z.boolean(),
  dpNoiseMultiplier: z.coerce.number().optional().nullable(),
  dpClipNorm: z.coerce.number().optional().nullable(),
  minimumAccuracy: z.coerce.number(),
  minimumFairnessScore: z.coerce.number().optional().nullable(),
  failureToleranceRatio: z.coerce.number(),
  updateReason: z.string().optional().nullable(),
});
export type UpdateTrainingRunConfigurationCommandInput = z.infer<typeof UpdateTrainingRunConfigurationCommandSchema>;

export const CreateTrainingJobCommandSchema = z.object({
  federationId: z.string().uuid(),
  trainingRunConfigurationId: z.string().uuid(),
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

export const SubmitModelUpdateSubmissionCommandSchema = z.object({
  executionSessionId: z.string().uuid(),
  executionPlanId: z.string().uuid(),
  trainingJobId: z.string().uuid(),
  trainingRunConfigurationId: z.string().uuid(),
  roundId: z.string().uuid(),
  roundExecutionId: z.string().uuid(),
  runtimeId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  localModelId: z.string().uuid(),
  updateArtifactId: z.string().uuid(),
  artifactRef: z.string(),
  artifactDigest: z.string(),
  trainingLoss: z.coerce.number(),
});
export type SubmitModelUpdateSubmissionCommandInput = z.infer<typeof SubmitModelUpdateSubmissionCommandSchema>;

export const CompleteSecureAggregationCommandSchema = z.object({
  trainingJobId: z.string().uuid(),
  trainingRunConfigurationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  roundId: z.string().uuid(),
  secureAggregationSessionId: z.string().uuid(),
  aggregatedModelId: z.string().uuid(),
  aggregatedModelArtifactUri: z.string(),
  aggregatedModelRegistryRef: z.string(),
  modelFormat: z.string(),
  modelArtifactDigest: z.string(),
  aggregatedModelSignatureUri: z.string().optional().nullable(),
});
export type CompleteSecureAggregationCommandInput = z.infer<typeof CompleteSecureAggregationCommandSchema>;

export const SubmitGlobalModelEvaluationCommandSchema = z.object({
  trainingJobId: z.string().uuid(),
  trainingRunConfigurationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  roundId: z.string().uuid(),
  aggregatedModelId: z.string().uuid(),
  aggregatedModelArtifactUri: z.string(),
  aggregatedModelRegistryRef: z.string(),
  modelFormat: z.string(),
  modelArtifactDigest: z.string(),
  aggregatedModelSignatureUri: z.string().optional().nullable(),
  globalAccuracy: z.coerce.number(),
  globalFairnessScore: z.coerce.number(),
});
export type SubmitGlobalModelEvaluationCommandInput = z.infer<typeof SubmitGlobalModelEvaluationCommandSchema>;

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

export const RecordEncryptedModelUpdateCommandSchema = z.object({
  secureAggregationSessionId: z.string().uuid(),
  submissionId: z.string().uuid(),
  runtimeId: z.string().uuid(),
  encryptedUpdateDigest: z.string(),
});
export type RecordEncryptedModelUpdateCommandInput = z.infer<typeof RecordEncryptedModelUpdateCommandSchema>;

export const FailSecureAggregationSessionCommandSchema = z.object({
  secureAggregationSessionId: z.string().uuid(),
  failureReason: z.string(),
});
export type FailSecureAggregationSessionCommandInput = z.infer<typeof FailSecureAggregationSessionCommandSchema>;

export const DeclareDatasetCommandSchema = z.object({
  organizationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  datasetName: z.string(),
  datasetType: z.string(),
  datasetUsage: z.string(),
});
export type DeclareDatasetCommandInput = z.infer<typeof DeclareDatasetCommandSchema>;

export const ConfigureRuntimeDatasetBindingCommandSchema = z.object({
  datasetId: z.string().uuid(),
  organizationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  runtimeId: z.string().uuid(),
  dataSourceType: z.string(),
  host: z.string().optional().nullable(),
  port: z.coerce.number().int().optional().nullable(),
  url: z.string().optional().nullable(),
  databaseName: z.string().optional().nullable(),
  schemaName: z.string().optional().nullable(),
  tableName: z.string().optional().nullable(),
  filePath: z.string().optional().nullable(),
  objectBucket: z.string().optional().nullable(),
  objectPrefix: z.string().optional().nullable(),
  dataFormat: z.string(),
  credentialSecretName: z.string().optional().nullable(),
});
export type ConfigureRuntimeDatasetBindingCommandInput = z.infer<typeof ConfigureRuntimeDatasetBindingCommandSchema>;

export const RevalidateAgentDatasetAccessCommandSchema = z.object({
  runtimeDatasetBindingId: z.string().uuid(),
});
export type RevalidateAgentDatasetAccessCommandInput = z.infer<typeof RevalidateAgentDatasetAccessCommandSchema>;

export const ReprofileAgentDatasetCommandSchema = z.object({
  runtimeDatasetBindingId: z.string().uuid(),
});
export type ReprofileAgentDatasetCommandInput = z.infer<typeof ReprofileAgentDatasetCommandSchema>;

export const RetryDatasetContractValidationCommandSchema = z.object({
  datasetId: z.string().uuid(),
});
export type RetryDatasetContractValidationCommandInput = z.infer<typeof RetryDatasetContractValidationCommandSchema>;

export const RejectDatasetForTrainingCommandSchema = z.object({
  datasetId: z.string().uuid(),
  rejectionReason: z.string(),
});
export type RejectDatasetForTrainingCommandInput = z.infer<typeof RejectDatasetForTrainingCommandSchema>;

export const ApproveDatasetForTrainingCommandSchema = z.object({
  datasetId: z.string().uuid(),
});
export type ApproveDatasetForTrainingCommandInput = z.infer<typeof ApproveDatasetForTrainingCommandSchema>;

export const RevokeDatasetTrainingApprovalCommandSchema = z.object({
  datasetId: z.string().uuid(),
  revokeReason: z.string(),
});
export type RevokeDatasetTrainingApprovalCommandInput = z.infer<typeof RevokeDatasetTrainingApprovalCommandSchema>;

export const CompleteRoundExecutionCommandSchema = z.object({
  roundExecutionId: z.string().uuid(),
  executionSessionId: z.string().uuid(),
  executionPlanId: z.string().uuid(),
  trainingJobId: z.string().uuid(),
  trainingRunConfigurationId: z.string().uuid(),
  roundId: z.string().uuid(),
  runtimeId: z.string().uuid(),
  runtimeEngineJobId: z.string(),
});
export type CompleteRoundExecutionCommandInput = z.infer<typeof CompleteRoundExecutionCommandSchema>;

export const FailRoundExecutionCommandSchema = z.object({
  roundExecutionId: z.string().uuid(),
  executionSessionId: z.string().uuid(),
  executionPlanId: z.string().uuid(),
  trainingJobId: z.string().uuid(),
  trainingRunConfigurationId: z.string().uuid(),
  roundId: z.string().uuid(),
  runtimeId: z.string().uuid(),
  runtimeEngineJobId: z.string().optional().nullable(),
  failureReason: z.string(),
});
export type FailRoundExecutionCommandInput = z.infer<typeof FailRoundExecutionCommandSchema>;

export const RetryRoundExecutionAfterStartFailureCommandSchema = z.object({
  roundExecutionId: z.string().uuid(),
  executionSessionId: z.string().uuid(),
  executionPlanId: z.string().uuid(),
  trainingJobId: z.string().uuid(),
  trainingRunConfigurationId: z.string().uuid(),
  roundId: z.string().uuid(),
  roundNumber: z.coerce.number().int(),
  runtimeId: z.string().uuid(),
  organizationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  baseModelId: z.string().uuid(),
  runtimeEngineJobId: z.string(),
  retryReason: z.string(),
});
export type RetryRoundExecutionAfterStartFailureCommandInput = z.infer<typeof RetryRoundExecutionAfterStartFailureCommandSchema>;

export const RetryRoundExecutionAfterRuntimeFailureCommandSchema = z.object({
  roundExecutionId: z.string().uuid(),
  executionSessionId: z.string().uuid(),
  executionPlanId: z.string().uuid(),
  trainingJobId: z.string().uuid(),
  trainingRunConfigurationId: z.string().uuid(),
  roundId: z.string().uuid(),
  roundNumber: z.coerce.number().int(),
  runtimeId: z.string().uuid(),
  organizationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  baseModelId: z.string().uuid(),
  runtimeEngineJobId: z.string(),
  retryReason: z.string(),
});
export type RetryRoundExecutionAfterRuntimeFailureCommandInput = z.infer<typeof RetryRoundExecutionAfterRuntimeFailureCommandSchema>;

export const LoadRuntimeAgentBootstrapConfigurationCommandSchema = z.object({
});
export type LoadRuntimeAgentBootstrapConfigurationCommandInput = z.infer<typeof LoadRuntimeAgentBootstrapConfigurationCommandSchema>;

