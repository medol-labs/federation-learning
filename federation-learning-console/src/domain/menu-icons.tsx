import type { ReactNode } from "react";
import {
  Activity,
  BookOpen,
  Boxes,
  Brain,
  Database,
  FileUp,
  Gauge,
  KeyRound,
  Network,
  Server,
  Shield,
  Users,
  Workflow,
} from "lucide-react";

type MenuIconType = "dashboard" | "chapter" | "resource";

type MenuIconRequest = {
  type: MenuIconType;
  name: string;
  label?: string;
  parent?: string;
  fallback: ReactNode;
};

const chapterIcons: Record<string, ReactNode> = {
  datasetgovernance: <Database />,
  dictionarymaintenance: <BookOpen />,
  federationmanagement: <Network />,
  fileupload: <FileUp />,
  identityaccessmanagement: <Shield />,
  modellifecycle: <Brain />,
  modelrepository: <Boxes />,
  organizationmanagement: <Users />,
  runtimegovernance: <Network />,
  runtimemonitoring: <Gauge />,
  runtimeprovisioning: <Server />,
  secureaggregation: <KeyRound />,
  trainingorchestration: <Workflow />,
};

const resourceIcons: Record<string, ReactNode> = {
  auditrecordlog: <Activity />,
  dictionarycatalog: <BookOpen />,
  dictionaryvaluecatalog: <BookOpen />,
  featureschemacatalog: <Database />,
  modelartifactcatalog: <Boxes />,
  modelcatalog: <Brain />,
  organizationdirectory: <Users />,
  permissioncatalog: <Shield />,
  rolecatalog: <Shield />,
  runtimeagentendpointcatalog: <Server />,
  runtimecapabilitycatalog: <Gauge />,
  runtimehealthdashboard: <Activity />,
  runtimeidentitycatalog: <Server />,
  runtimeinfrastructureaccessview: <Server />,
  runtimenodeinventoryview: <Server />,
  secureaggregationsessioncatalog: <KeyRound />,
  serviceaccountapitokencatalog: <KeyRound />,
  trainingjobdashboard: <Workflow />,
  trainingroundprogress: <Workflow />,
  trainingrunconfigurationcatalog: <Workflow />,
  uploadedfilecatalog: <FileUp />,
  useraccountcatalog: <Users />,
};

export function resolveMenuIcon({
  type,
  name,
  label,
  parent,
  fallback,
}: MenuIconRequest): ReactNode {
  if (type === "dashboard") {
    return <Gauge />;
  }

  const key = normalizeIconKey(name);
  const labelKey = normalizeIconKey(label);
  const parentKey = normalizeIconKey(parent);

  if (type === "chapter") {
    return chapterIcons[key] ?? chapterIcons[labelKey] ?? fallback;
  }

  return (
    resourceIcons[key]
    ?? resourceIcons[labelKey]
    ?? chapterIcons[parentKey]
    ?? fallback
  );
}

function normalizeIconKey(value?: string): string {
  return (value ?? "").replace(/[\s_-]/g, "").toLowerCase();
}
