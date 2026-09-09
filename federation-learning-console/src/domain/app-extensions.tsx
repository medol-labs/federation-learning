import type { IResourceItem } from "@refinedev/core";
import type { PropsWithChildren } from "react";

import type {
  AdditionalAccessDecision,
  AdditionalAccessParams,
  AppExtensionState,
  BackendModule,
} from "@/providers/app-extension-contract";
import {
  isRuntimeAgentResource,
  RuntimeAgentAccessProvider,
  RuntimeAgentRouteGuard,
  RuntimeAgentSwitcher,
  runtimeAgentModuleName,
  useRuntimeAgentAccess,
} from "./runtime-agent-access";

export function AppExtensionProvider({ children }: PropsWithChildren) {
  return <RuntimeAgentAccessProvider>{children}</RuntimeAgentAccessProvider>;
}

export function useAppExtensions(): AppExtensionState {
  const {
    currentUser,
    runtimeAgentAccessAllowed,
    selectedEndpoint,
  } = useRuntimeAgentAccess();

  return {
    dataProviderKey: selectedEndpoint?.runtimeAgentId ?? "no-runtime-agent",
    filterBackendModules: (modules: BackendModule[]) => modules.filter((module) =>
      module.name !== runtimeAgentModuleName ||
      (runtimeAgentAccessAllowed && selectedEndpoint !== null),
    ),
    filterResources: (resources: IResourceItem[]) => currentUser?.organizationId
      ? resources
      : resources.filter((resource) => !isRuntimeAgentResource(resource.name)),
    resolveBackendBaseUrl: (module: BackendModule) => module.name === runtimeAgentModuleName
      ? selectedEndpoint?.runtimeAgentEndpoint.replace(/\/+$/u, "") ??
        "http://runtime-agent-not-selected.invalid"
      : module.apiUrl,
  };
}

export function HeaderExtensionActions({ compact = false }: { compact?: boolean }) {
  return <RuntimeAgentSwitcher compact={compact} />;
}

export function AuthenticatedRouteExtension({ children }: PropsWithChildren) {
  return <RuntimeAgentRouteGuard>{children}</RuntimeAgentRouteGuard>;
}

export async function evaluateAdditionalAccess({
  user,
  resource,
}: AdditionalAccessParams): Promise<AdditionalAccessDecision | undefined> {
  if (isRuntimeAgentResource(resource) && !user.organizationId) {
    return {
      can: false,
      reason: "A user organization is required to access a local Runtime Agent.",
    };
  }

  return undefined;
}
