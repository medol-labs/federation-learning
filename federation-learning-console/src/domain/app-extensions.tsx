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
import { getAppConfig } from "@/providers/app-config";

const PARTICIPANT_FRONTEND_APP = "FederationLearningParticipantConsole";
const PLATFORM_FRONTEND_APP = "FederationLearningConsole";
const SUPPORT_MODULE_NAME = "federation-learning-support";
const PLATFORM_MODULE_NAME = "federation-learning-platform";

function frontendAppName() {
  return getAppConfig("VITE_FRONTEND_APP", PLATFORM_FRONTEND_APP);
}

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
    filterBackendModules: (modules: BackendModule[]) => {
      if (frontendAppName() === PARTICIPANT_FRONTEND_APP) {
        return modules.filter((module) =>
          module.name === runtimeAgentModuleName,
        );
      }

      return modules.filter((module) =>
        module.name === SUPPORT_MODULE_NAME ||
        module.name === PLATFORM_MODULE_NAME,
      );
    },
    filterResources: (resources: IResourceItem[]) => {
      if (frontendAppName() === PARTICIPANT_FRONTEND_APP) {
        return resources.filter((resource) =>
          resource.name === "dashboard" || isRuntimeAgentResource(resource.name),
        );
      }

      return resources.filter((resource) => !isRuntimeAgentResource(resource.name));
    },
    resolveBackendBaseUrl: (module: BackendModule) => {
      if (module.name !== runtimeAgentModuleName) {
        return module.apiUrl;
      }

      if (frontendAppName() === PARTICIPANT_FRONTEND_APP) {
        return module.apiUrl;
      }

      return selectedEndpoint?.runtimeAgentEndpoint.replace(/\/+$/u, "") ??
        "http://runtime-agent-not-selected.invalid";
    },
  };
}

export function HeaderExtensionActions({ compact = false }: { compact?: boolean }) {
  if (frontendAppName() === PARTICIPANT_FRONTEND_APP) {
    return null;
  }

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
