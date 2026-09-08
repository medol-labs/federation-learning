import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { cn } from "@/lib/utils";
import {
  AUTH_STATE_CHANGE_EVENT,
  authFetch,
  cachedCurrentUser,
  type CurrentUser,
} from "@/providers/api-auth";
import { backendModules } from "@/providers/resources";
import { Check, Network } from "lucide-react";
import {
  createContext,
  type PropsWithChildren,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
} from "react";
import { Navigate, useLocation } from "react-router";

const RUNTIME_AGENT_MODULE_NAME = "federation-learning-runtime-agent";
const PLATFORM_MODULE_NAME = "federation-learning-platform";
const SELECTION_KEY_PREFIX = "federation-learning-runtime-agent";

export type RuntimeAgentEndpoint = {
  runtimeAgentId: string;
  runtimeId?: string | null;
  organizationId: string;
  runtimeName: string;
  runtimeAgentEndpoint: string;
  endpointScope: string;
  connectionStatus: string;
};

type RuntimeAgentAccess = {
  currentUser: CurrentUser | null;
  endpoints: RuntimeAgentEndpoint[];
  selectedEndpoint: RuntimeAgentEndpoint | null;
  loading: boolean;
  runtimeAgentAccessAllowed: boolean;
  selectEndpoint: (runtimeAgentId: string) => void;
};

const RuntimeAgentAccessContext = createContext<RuntimeAgentAccess | null>(null);

const platformModule = backendModules.find((module) => module.name === PLATFORM_MODULE_NAME);
const runtimeAgentModule = backendModules.find((module) => module.name === RUNTIME_AGENT_MODULE_NAME);

const normalizeState = (value: string) =>
  value.replace(/[^A-Za-z0-9]/g, "").toLowerCase();

const selectionKey = (user: CurrentUser) => `${SELECTION_KEY_PREFIX}:${user.id}`;

const readEndpointPage = (value: unknown): RuntimeAgentEndpoint[] => {
  if (Array.isArray(value)) {
    return value as RuntimeAgentEndpoint[];
  }

  if (value && typeof value === "object" && Array.isArray((value as { content?: unknown }).content)) {
    return (value as { content: RuntimeAgentEndpoint[] }).content;
  }

  return [];
};

export function RuntimeAgentAccessProvider({ children }: PropsWithChildren) {
  const [currentUser, setCurrentUser] = useState<CurrentUser | null>(() => cachedCurrentUser());
  const [endpoints, setEndpoints] = useState<RuntimeAgentEndpoint[]>([]);
  const [selectedEndpoint, setSelectedEndpoint] = useState<RuntimeAgentEndpoint | null>(null);
  const [loading, setLoading] = useState(false);

  const loadEndpoints = useCallback(async (user: CurrentUser | null) => {
    setCurrentUser(user);

    if (!user?.organizationId || !platformModule) {
      setEndpoints([]);
      setSelectedEndpoint(null);
      setLoading(false);
      return;
    }

    setLoading(true);
    try {
      const query = new URLSearchParams({
        "organizationId.equals": user.organizationId,
        size: "100",
      });
      const response = await authFetch(
        `${platformModule.apiUrl}/runtimeinfrastructure/runtimeagentendpointcatalog?${query}`,
      );
      if (!response.ok) {
        throw new Error(`Runtime Agent endpoints could not be loaded (${response.status}).`);
      }

      const records = readEndpointPage(await response.json())
        .filter((endpoint) => endpoint.organizationId === user.organizationId)
        .filter((endpoint) => normalizeState(endpoint.connectionStatus) === "connected")
        .filter((endpoint) => endpoint.runtimeAgentEndpoint?.trim())
        .sort((left, right) => left.runtimeName.localeCompare(right.runtimeName));
      const persistedRuntimeAgentId = sessionStorage.getItem(selectionKey(user));
      const selected =
        records.find((endpoint) => endpoint.runtimeAgentId === persistedRuntimeAgentId) ??
        records[0] ??
        null;

      if (selected) {
        sessionStorage.setItem(selectionKey(user), selected.runtimeAgentId);
      } else {
        sessionStorage.removeItem(selectionKey(user));
      }
      setEndpoints(records);
      setSelectedEndpoint(selected);
    } catch (error) {
      console.error("Failed to load Runtime Agent endpoints.", error);
      setEndpoints([]);
      setSelectedEndpoint(null);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    void loadEndpoints(cachedCurrentUser());

    const handleAuthStateChange = () => void loadEndpoints(cachedCurrentUser());
    window.addEventListener(AUTH_STATE_CHANGE_EVENT, handleAuthStateChange);
    return () => window.removeEventListener(AUTH_STATE_CHANGE_EVENT, handleAuthStateChange);
  }, [loadEndpoints]);

  const selectEndpoint = useCallback((runtimeAgentId: string) => {
    if (!currentUser?.organizationId) {
      return;
    }

    const selected = endpoints.find((endpoint) => endpoint.runtimeAgentId === runtimeAgentId);
    if (!selected) {
      return;
    }

    sessionStorage.setItem(selectionKey(currentUser), selected.runtimeAgentId);
    setSelectedEndpoint(selected);
  }, [currentUser, endpoints]);

  const value = useMemo<RuntimeAgentAccess>(() => ({
    currentUser,
    endpoints,
    selectedEndpoint,
    loading,
    runtimeAgentAccessAllowed: Boolean(currentUser?.organizationId),
    selectEndpoint,
  }), [currentUser, endpoints, loading, selectEndpoint, selectedEndpoint]);

  return (
    <RuntimeAgentAccessContext.Provider value={value}>
      {children}
    </RuntimeAgentAccessContext.Provider>
  );
}

export function useRuntimeAgentAccess(): RuntimeAgentAccess {
  const context = useContext(RuntimeAgentAccessContext);
  if (!context) {
    throw new Error("useRuntimeAgentAccess must be used inside RuntimeAgentAccessProvider.");
  }
  return context;
}

export function isRuntimeAgentResource(resource?: string): boolean {
  return Boolean(
    resource &&
    (resource === "runtimeagentoperations" || runtimeAgentModule?.resources.includes(resource)),
  );
}

export function RuntimeAgentSwitcher({ compact = false }: { compact?: boolean }) {
  const {
    endpoints,
    loading,
    runtimeAgentAccessAllowed,
    selectedEndpoint,
    selectEndpoint,
  } = useRuntimeAgentAccess();

  if (!runtimeAgentAccessAllowed || endpoints.length === 0) {
    return null;
  }

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button
          type="button"
          variant="outline"
          size="sm"
          disabled={loading}
          className={cn(
            "h-9 gap-2 border-border bg-background px-2 text-foreground shadow-sm",
            "hover:bg-accent hover:text-accent-foreground",
            compact ? "w-9" : "max-w-56 min-w-40",
          )}
          aria-label="Select local Runtime Agent"
          title={selectedEndpoint?.runtimeName ?? "Select local Runtime Agent"}
        >
          <Network className="h-4 w-4 shrink-0" />
          {!compact && (
            <span className="truncate text-xs font-semibold">
              {selectedEndpoint?.runtimeName ?? "Select Runtime"}
            </span>
          )}
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end" className="min-w-64">
        {endpoints.map((endpoint) => (
          <DropdownMenuItem
            key={endpoint.runtimeAgentId}
            className="cursor-pointer gap-2"
            onClick={() => selectEndpoint(endpoint.runtimeAgentId)}
          >
            <span className="min-w-0 flex-1">
              <span className="block truncate font-medium">{endpoint.runtimeName}</span>
              <span className="block truncate text-xs text-muted-foreground">
                {endpoint.endpointScope} / {endpoint.runtimeAgentEndpoint}
              </span>
            </span>
            {endpoint.runtimeAgentId === selectedEndpoint?.runtimeAgentId && (
              <Check className="ml-auto h-4 w-4 shrink-0" />
            )}
          </DropdownMenuItem>
        ))}
      </DropdownMenuContent>
    </DropdownMenu>
  );
}

export function RuntimeAgentRouteGuard({ children }: PropsWithChildren) {
  const location = useLocation();
  const {
    loading,
    runtimeAgentAccessAllowed,
    selectedEndpoint,
  } = useRuntimeAgentAccess();
  const runtimeAgentRoute = Boolean(runtimeAgentModule?.resources.some(
    (route) => location.pathname === `/${route}` || location.pathname.startsWith(`/${route}/`),
  ));

  if (!runtimeAgentRoute) {
    return children;
  }

  if (loading) {
    return null;
  }

  if (!runtimeAgentAccessAllowed || !selectedEndpoint) {
    return <Navigate to="/dashboard" replace />;
  }

  return children;
}

export const runtimeAgentModuleName = RUNTIME_AGENT_MODULE_NAME;
