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

export const DeclareDatasetCommandSchema = z.object({
  organizationId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  featureSchemaId: z.string().uuid(),
  featureDomain: z.string().optional().nullable(),
  featureSchemaVersion: z.string().optional().nullable(),
  datasetName: z.string(),
  datasetUsage: z.string(),
});
export type DeclareDatasetCommandInput = z.infer<typeof DeclareDatasetCommandSchema>;

export const ConfigureRuntimeDatasetBindingCommandSchema = z.object({
  datasetId: z.string().uuid(),
  organizationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  organizationName: z.string().optional().nullable(),
  featureDomain: z.string().optional().nullable(),
  featureSchemaVersion: z.string().optional().nullable(),
  datasetName: z.string(),
  runtimeId: z.string().uuid(),
  runtimeName: z.string().optional().nullable(),
  filePath: z.string(),
  dataFormat: z.string(),
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
  organizationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  datasetName: z.string(),
});
export type RetryDatasetContractValidationCommandInput = z.infer<typeof RetryDatasetContractValidationCommandSchema>;

export const RejectDatasetForTrainingCommandSchema = z.object({
  datasetId: z.string().uuid(),
  rejectionReason: z.string(),
  organizationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  datasetName: z.string(),
});
export type RejectDatasetForTrainingCommandInput = z.infer<typeof RejectDatasetForTrainingCommandSchema>;

export const ApproveDatasetForTrainingCommandSchema = z.object({
  datasetId: z.string().uuid(),
  organizationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  datasetName: z.string(),
});
export type ApproveDatasetForTrainingCommandInput = z.infer<typeof ApproveDatasetForTrainingCommandSchema>;

export const RevokeDatasetTrainingApprovalCommandSchema = z.object({
  datasetId: z.string().uuid(),
  revokeReason: z.string(),
  organizationId: z.string().uuid(),
  featureSchemaId: z.string().uuid(),
  datasetName: z.string(),
});
export type RevokeDatasetTrainingApprovalCommandInput = z.infer<typeof RevokeDatasetTrainingApprovalCommandSchema>;

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

