// Generated from config.json by the refine generator.
import { Route } from "react-router";

import { resolvePageOverride } from "@/domain/page-overrides";
import {
  AgentDatasetAccessValidationCatalogList,
  AgentDatasetAccessValidationCatalogShow,
} from "./runtimeagentoperations/read-models/agent-dataset-access-validation-catalog";
import {
  AgentDictionaryValueCatalogList,
  AgentDictionaryValueCatalogShow,
} from "./runtimeagentoperations/read-models/agent-dictionary-value-catalog";
import {
  AgentFeatureSchemaCatalogList,
  AgentFeatureSchemaCatalogShow,
} from "./runtimeagentoperations/read-models/agent-feature-schema-catalog";
import {
  AgentOrganizationDirectoryList,
  AgentOrganizationDirectoryShow,
} from "./runtimeagentoperations/read-models/agent-organization-directory";
import {
  AgentRuntimeIdentityCatalogList,
  AgentRuntimeIdentityCatalogShow,
} from "./runtimeagentoperations/read-models/agent-runtime-identity-catalog";
import {
  AgentRuntimeInfrastructureConnectionCatalogList,
  AgentRuntimeInfrastructureConnectionCatalogShow,
} from "./runtimeagentoperations/read-models/agent-runtime-infrastructure-connection-catalog";
import {
  AgentRuntimeNodeInventoryCatalogList,
  AgentRuntimeNodeInventoryCatalogShow,
} from "./runtimeagentoperations/read-models/agent-runtime-node-inventory-catalog";
import {
  AgentRuntimeNodeResourceLatestList,
  AgentRuntimeNodeResourceLatestShow,
} from "./runtimeagentoperations/read-models/agent-runtime-node-resource-latest";
import {
  AgentRuntimeTelemetryLatestList,
  AgentRuntimeTelemetryLatestShow,
} from "./runtimeagentoperations/read-models/agent-runtime-telemetry-latest";
import {
  DatasetCapabilityList,
  DatasetCapabilityShow,
  DatasetCapabilityDeclareDataset,
  DatasetCapabilityRejectDatasetForTraining,
  DatasetCapabilityRevokeDatasetTrainingApproval,
  DatasetCapabilityConfigureRuntimeDatasetBinding,
} from "./runtimeagentoperations/read-models/dataset-capability";
import {
  DatasetReadinessList,
  DatasetReadinessShow,
  DatasetReadinessConfigureRuntimeDatasetBinding,
  DatasetReadinessRejectDatasetForTraining,
  DatasetReadinessRevokeDatasetTrainingApproval,
} from "./runtimeagentoperations/read-models/dataset-readiness";
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
  RoundExecutionCatalogList,
  RoundExecutionCatalogShow,
  RoundExecutionCatalogRetryRoundExecutionAfterStartFailure,
  RoundExecutionCatalogRetryRoundExecutionAfterRuntimeFailure,
} from "./runtimeagentoperations/read-models/round-execution-catalog";
import {
  RuntimeAgentLifecycleCatalogList,
  RuntimeAgentLifecycleCatalogShow,
} from "./runtimeagentoperations/read-models/runtime-agent-lifecycle-catalog";
import {
  RuntimeDatasetBindingCatalogList,
  RuntimeDatasetBindingCatalogShow,
  RuntimeDatasetBindingCatalogConfigureRuntimeDatasetBinding,
} from "./runtimeagentoperations/read-models/runtime-dataset-binding-catalog";
import {
  ServiceAccountApiTokenCatalogList,
  ServiceAccountApiTokenCatalogShow,
  ServiceAccountApiTokenCatalogIssueServiceAccountApiToken,
} from "./identityaccessmanagement/read-models/service-account-api-token-catalog";
import {
  UserAccountCatalogList,
  UserAccountCatalogShow,
  UserAccountCatalogRegisterUserAccount,
  UserAccountCatalogGenerateUserAccountLoginPassword,
  UserAccountCatalogDeactivateUserAccount,
  UserAccountCatalogAssignRoleToUser,
} from "./identityaccessmanagement/read-models/user-account-catalog";
import {
  UserRoleAssignmentCatalogList,
  UserRoleAssignmentCatalogShow,
} from "./identityaccessmanagement/read-models/user-role-assignment-catalog";

export const contextRoutes = (
  <>
    <Route path="/agent-dataset-access-validation-catalog">
      <Route index element={resolvePageOverride("agent-dataset-access-validation-catalog", "list", <AgentDatasetAccessValidationCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("agent-dataset-access-validation-catalog", "show", <AgentDatasetAccessValidationCatalogShow />)} />
    </Route>
    <Route path="/agent-dictionary-value-catalog">
      <Route index element={resolvePageOverride("agent-dictionary-value-catalog", "list", <AgentDictionaryValueCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("agent-dictionary-value-catalog", "show", <AgentDictionaryValueCatalogShow />)} />
    </Route>
    <Route path="/agent-feature-schema-catalog">
      <Route index element={resolvePageOverride("agent-feature-schema-catalog", "list", <AgentFeatureSchemaCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("agent-feature-schema-catalog", "show", <AgentFeatureSchemaCatalogShow />)} />
    </Route>
    <Route path="/agent-organization-directory">
      <Route index element={resolvePageOverride("agent-organization-directory", "list", <AgentOrganizationDirectoryList />)} />
      <Route path="show/:id" element={resolvePageOverride("agent-organization-directory", "show", <AgentOrganizationDirectoryShow />)} />
    </Route>
    <Route path="/agent-runtime-identity-catalog">
      <Route index element={resolvePageOverride("agent-runtime-identity-catalog", "list", <AgentRuntimeIdentityCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("agent-runtime-identity-catalog", "show", <AgentRuntimeIdentityCatalogShow />)} />
    </Route>
    <Route path="/agent-runtime-infrastructure-connection-catalog">
      <Route index element={resolvePageOverride("agent-runtime-infrastructure-connection-catalog", "list", <AgentRuntimeInfrastructureConnectionCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("agent-runtime-infrastructure-connection-catalog", "show", <AgentRuntimeInfrastructureConnectionCatalogShow />)} />
    </Route>
    <Route path="/agent-runtime-node-inventory-catalog">
      <Route index element={resolvePageOverride("agent-runtime-node-inventory-catalog", "list", <AgentRuntimeNodeInventoryCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("agent-runtime-node-inventory-catalog", "show", <AgentRuntimeNodeInventoryCatalogShow />)} />
    </Route>
    <Route path="/agent-runtime-node-resource-latest">
      <Route index element={resolvePageOverride("agent-runtime-node-resource-latest", "list", <AgentRuntimeNodeResourceLatestList />)} />
      <Route path="show/:id" element={resolvePageOverride("agent-runtime-node-resource-latest", "show", <AgentRuntimeNodeResourceLatestShow />)} />
    </Route>
    <Route path="/agent-runtime-telemetry-latest">
      <Route index element={resolvePageOverride("agent-runtime-telemetry-latest", "list", <AgentRuntimeTelemetryLatestList />)} />
      <Route path="show/:id" element={resolvePageOverride("agent-runtime-telemetry-latest", "show", <AgentRuntimeTelemetryLatestShow />)} />
    </Route>
    <Route path="/dataset-capability">
      <Route index element={resolvePageOverride("dataset-capability", "list", <DatasetCapabilityList />)} />
      <Route path="command/declare-dataset" element={resolvePageOverride("dataset-capability", "declareDataset", <DatasetCapabilityDeclareDataset />)} />
      <Route path="show/:id" element={resolvePageOverride("dataset-capability", "show", <DatasetCapabilityShow />)} />
      <Route path=":id/command/reject-dataset-for-training" element={resolvePageOverride("dataset-capability", "rejectDatasetForTraining", <DatasetCapabilityRejectDatasetForTraining />)} />
      <Route path=":id/command/revoke-dataset-training-approval" element={resolvePageOverride("dataset-capability", "revokeDatasetTrainingApproval", <DatasetCapabilityRevokeDatasetTrainingApproval />)} />
      <Route path=":id/command/configure-runtime-dataset-binding" element={resolvePageOverride("dataset-capability", "configureRuntimeDatasetBinding", <DatasetCapabilityConfigureRuntimeDatasetBinding />)} />
    </Route>
    <Route path="/dataset-readiness">
      <Route index element={resolvePageOverride("dataset-readiness", "list", <DatasetReadinessList />)} />
      <Route path="show/:id" element={resolvePageOverride("dataset-readiness", "show", <DatasetReadinessShow />)} />
      <Route path=":id/command/configure-runtime-dataset-binding" element={resolvePageOverride("dataset-readiness", "configureRuntimeDatasetBinding", <DatasetReadinessConfigureRuntimeDatasetBinding />)} />
      <Route path=":id/command/reject-dataset-for-training" element={resolvePageOverride("dataset-readiness", "rejectDatasetForTraining", <DatasetReadinessRejectDatasetForTraining />)} />
      <Route path=":id/command/revoke-dataset-training-approval" element={resolvePageOverride("dataset-readiness", "revokeDatasetTrainingApproval", <DatasetReadinessRevokeDatasetTrainingApproval />)} />
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
    <Route path="/round-execution-catalog">
      <Route index element={resolvePageOverride("round-execution-catalog", "list", <RoundExecutionCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("round-execution-catalog", "show", <RoundExecutionCatalogShow />)} />
      <Route path=":id/command/retry-round-execution-after-start-failure" element={resolvePageOverride("round-execution-catalog", "retryRoundExecutionAfterStartFailure", <RoundExecutionCatalogRetryRoundExecutionAfterStartFailure />)} />
      <Route path=":id/command/retry-round-execution-after-runtime-failure" element={resolvePageOverride("round-execution-catalog", "retryRoundExecutionAfterRuntimeFailure", <RoundExecutionCatalogRetryRoundExecutionAfterRuntimeFailure />)} />
    </Route>
    <Route path="/runtime-agent-lifecycle-catalog">
      <Route index element={resolvePageOverride("runtime-agent-lifecycle-catalog", "list", <RuntimeAgentLifecycleCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-agent-lifecycle-catalog", "show", <RuntimeAgentLifecycleCatalogShow />)} />
    </Route>
    <Route path="/runtime-dataset-binding-catalog">
      <Route index element={resolvePageOverride("runtime-dataset-binding-catalog", "list", <RuntimeDatasetBindingCatalogList />)} />
      <Route path="command/configure-runtime-dataset-binding" element={resolvePageOverride("runtime-dataset-binding-catalog", "configureRuntimeDatasetBinding", <RuntimeDatasetBindingCatalogConfigureRuntimeDatasetBinding />)} />
      <Route path="show/:id" element={resolvePageOverride("runtime-dataset-binding-catalog", "show", <RuntimeDatasetBindingCatalogShow />)} />
    </Route>
    <Route path="/service-account-api-token-catalog">
      <Route index element={resolvePageOverride("service-account-api-token-catalog", "list", <ServiceAccountApiTokenCatalogList />)} />
      <Route path="command/issue-service-account-api-token" element={resolvePageOverride("service-account-api-token-catalog", "issueServiceAccountApiToken", <ServiceAccountApiTokenCatalogIssueServiceAccountApiToken />)} />
      <Route path="show/:id" element={resolvePageOverride("service-account-api-token-catalog", "show", <ServiceAccountApiTokenCatalogShow />)} />
    </Route>
    <Route path="/user-account-catalog">
      <Route index element={resolvePageOverride("user-account-catalog", "list", <UserAccountCatalogList />)} />
      <Route path="command/register-user-account" element={resolvePageOverride("user-account-catalog", "registerUserAccount", <UserAccountCatalogRegisterUserAccount />)} />
      <Route path="show/:id" element={resolvePageOverride("user-account-catalog", "show", <UserAccountCatalogShow />)} />
      <Route path=":id/command/generate-user-account-login-password" element={resolvePageOverride("user-account-catalog", "generateUserAccountLoginPassword", <UserAccountCatalogGenerateUserAccountLoginPassword />)} />
      <Route path=":id/command/deactivate-user-account" element={resolvePageOverride("user-account-catalog", "deactivateUserAccount", <UserAccountCatalogDeactivateUserAccount />)} />
      <Route path=":id/command/assign-role-to-user" element={resolvePageOverride("user-account-catalog", "assignRoleToUser", <UserAccountCatalogAssignRoleToUser />)} />
    </Route>
    <Route path="/user-role-assignment-catalog">
      <Route index element={resolvePageOverride("user-role-assignment-catalog", "list", <UserRoleAssignmentCatalogList />)} />
      <Route path="show/:id" element={resolvePageOverride("user-role-assignment-catalog", "show", <UserRoleAssignmentCatalogShow />)} />
    </Route>
  </>
);
