<!-- Generated from config.json by the refine generator. -->

# Frontend Extension Manifest

This document is generated for **Federation Learning Participant Console** (`FederationLearningParticipantConsole`). It is the
contract between MEDOL/codegen output and hand-written frontend customizations.

Generated files under `src/contexts/**`, `src/contexts/resources.tsx`, and
`src/contexts/routes.tsx` are fallback implementations and may be
overwritten by regeneration. Business customizations should live under
`src/domain/**`.

## Composition Model

```text
           MEDOL / Codegen Model
                    |
          Generator / Build time
                    |
      +-------------+-------------+
      |             |             |
  Blueprint     Extension      Override
      |             |             |
      +-------------+-------------+
                    |
            Static Composition
                    |
                 React App
```

- **Blueprint** describes planned typed composition units such as a resource page
  blueprint, toolbar blueprint, row-action blueprint, or field-renderer
  blueprint.
- **Extension** changes application behavior around generated defaults, such as
  filtering backend modules, selecting resources, resolving backend base URLs,
  adding route guards, and adding header actions.
- **Override** replaces a specific generated fallback, such as a resource page,
  menu icon, resource metadata entry, row action, or field renderer.

## Stable Hand-Written Locations

| Area | Stable path | Notes |
| --- | --- | --- |
| App extensions | `src/domain/app-extensions.tsx` | Provider, route guard, backend/module/resource filtering, header actions, access decisions |
| Page overrides | `src/domain/page-overrides.tsx` | Replace generated list/show/command pages by override key |
| Resource metadata overrides | `src/domain/resource-overrides.tsx` | Refine resource metadata overrides |
| Menu icons | `src/domain/menu-icons.tsx` | Dashboard/chapter/resource icon resolver |
| Custom composition | `src/app/composition/composition.custom.ts` | Typed extension, override, and blueprint registrations |
| Resolved composition | `src/app/composition/composition.resolved.ts` | Final generated + custom composition consumed by runtime code |
| Domain extensions | `src/domain/extensions/**` | Business-owned additive UI and behavior extensions |
| Domain overrides | `src/domain/overrides/**` | Business-owned replacement components and pages |
| Future blueprints | `src/domain/blueprints/**` | Planned typed resource/page/toolbar/field composition |
| Future override registry | `src/domain/overrides/**` | Planned typed override registry for static composition |

## Application Extension Points

| ID | Layer | Type signature | Default | Override path |
| --- | --- | --- | --- | --- |
| `app.provider` | Extension | `AppExtensionProvider(props: PropsWithChildren): ReactNode` | `src/domain/app-extensions.tsx` | `src/domain/app-extensions.tsx` |
| `app.backendModules` | Extension | `filterBackendModules(modules: BackendModule[]): BackendModule[]` | `src/domain/app-extensions.tsx` | `src/domain/app-extensions.tsx` |
| `app.resources` | Extension | `filterResources(resources: IResourceItem[]): IResourceItem[]` | `src/domain/app-extensions.tsx` | `src/domain/app-extensions.tsx` |
| `app.backendBaseUrl` | Extension | `resolveBackendBaseUrl(module: BackendModule): string` | `src/domain/app-extensions.tsx` | `src/domain/app-extensions.tsx` |
| `layout.headerActions` | Extension | `HeaderExtensionActions(props: { compact?: boolean }): ReactNode` | `src/domain/app-extensions.tsx` | `src/domain/app-extensions.tsx` |
| `layout.authenticatedRoute` | Extension | `AuthenticatedRouteExtension(props: PropsWithChildren): ReactNode` | `src/domain/app-extensions.tsx` | `src/domain/app-extensions.tsx` |
| `access.additionalDecision` | Extension | `evaluateAdditionalAccess(params: AdditionalAccessParams): Promise<AdditionalAccessDecision \| undefined>` | `src/domain/app-extensions.tsx` | `src/domain/app-extensions.tsx` |
| `navigation.menuIcon` | Override | `resolveMenuIcon(request: MenuIconRequest): ReactNode` | `src/domain/menu-icons.tsx` | `src/domain/menu-icons.tsx` |
| `resource.metadata` | Override | `ResourceOverride = Partial<IResourceItem> & { name: string }` | `src/domain/resource-overrides.tsx` | `src/domain/resource-overrides.tsx` |
| `resource.page` | Override | `pageOverrides: Partial<Record<`${resourceRoute}:${view}`, ReactElement>>` | `src/domain/page-overrides.tsx` | `src/domain/page-overrides.tsx` |
| `blueprint.resource` | Blueprint | `future: ResourceBlueprint<ResourceRecord>` | `generated resource metadata` | `src/domain/blueprints/**` |
| `blueprint.overrideRegistry` | Blueprint | `future: OverrideRegistry.register(extensionPoint, implementation)` | `generated fallback registry` | `src/domain/overrides/**` |

## Backend Modules

| Module | Label | Data provider | Home route |
| --- | --- | --- | --- |
| `federation-learning-support` | Federation Learning Support | `federation-learning-support` | `/dashboard` |
| `federation-learning-platform` | Federation Learning Platform | `federation-learning-platform` | `/dashboard` |
| `federation-learning-runtime-agent` | Federation Learning Runtime Agent | `federation-learning-runtime-agent` | `/agent-dataset-access-validation-catalog` |

## Resource Extension Points

### Agent Dataset Access Validation Catalog

| Property | Value |
| --- | --- |
| Resource name | `agent_dataset_access_validation_catalog` |
| Route | `/agent-dataset-access-validation-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/agent-dataset-access-validation-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/agent-dataset-access-validation-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "agent_dataset_access_validation_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "agent_dataset_access_validation_catalog", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `agent-dataset-access-validation-catalog:list` | `src/contexts/runtimeagentoperations/slices/agent-dataset-access-validation-catalog/list.tsx` |
| `show` | `agent-dataset-access-validation-catalog:show` | `src/contexts/runtimeagentoperations/slices/agent-dataset-access-validation-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAgentDatasetAccessValidationCatalogList } from "./pages/my-agent-dataset-access-validation-catalog-list";

export const pageOverrides = {
  "agent-dataset-access-validation-catalog:list": <MyAgentDatasetAccessValidationCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `datasetAccessValidationId` | `string` | `agent-dataset-access-validation-catalog:field:datasetAccessValidationId` | `formatValue/display text` |
| `runtimeDatasetBindingId` | `string` | `agent-dataset-access-validation-catalog:field:runtimeDatasetBindingId` | `formatValue/display text` |
| `datasetId` | `string` | `agent-dataset-access-validation-catalog:field:datasetId` | `formatValue/display text` |
| `organizationId` | `string` | `agent-dataset-access-validation-catalog:field:organizationId` | `formatValue/display text` |
| `organizationName` | `string` | `agent-dataset-access-validation-catalog:field:organizationName` | `formatValue/display text` |
| `featureSchemaId` | `string` | `agent-dataset-access-validation-catalog:field:featureSchemaId` | `formatValue/display text` |
| `featureDomain` | `string` | `agent-dataset-access-validation-catalog:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `agent-dataset-access-validation-catalog:field:featureSchemaVersion` | `formatValue/display text` |
| `runtimeId` | `string` | `agent-dataset-access-validation-catalog:field:runtimeId` | `formatValue/display text` |
| `datasetName` | `string` | `agent-dataset-access-validation-catalog:field:datasetName` | `formatValue/display text` |
| `runtimeName` | `string` | `agent-dataset-access-validation-catalog:field:runtimeName` | `formatValue/display text` |
| `readable` | `boolean` | `agent-dataset-access-validation-catalog:field:readable` | `formatValue/display text` |
| `schemaReadable` | `boolean` | `agent-dataset-access-validation-catalog:field:schemaReadable` | `formatValue/display text` |
| `sampleBatchReadable` | `boolean` | `agent-dataset-access-validation-catalog:field:sampleBatchReadable` | `formatValue/display text` |
| `validationStatus` | `string` | `agent-dataset-access-validation-catalog:field:validationStatus` | `formatValue/display text` |
| `failureReason` | `string` | `agent-dataset-access-validation-catalog:field:failureReason` | `formatValue/display text` |
| `validatedAt` | `string` | `agent-dataset-access-validation-catalog:field:validatedAt` | `formatValue/display text` |

### Agent Dictionary Value Catalog

| Property | Value |
| --- | --- |
| Resource name | `agent_dictionary_value_catalog` |
| Route | `/agent-dictionary-value-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/agent-dictionary-value-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/agent-dictionary-value-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "agent_dictionary_value_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "agent_dictionary_value_catalog", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `agent-dictionary-value-catalog:list` | `src/contexts/runtimeagentoperations/slices/agent-dictionary-value-catalog/list.tsx` |
| `show` | `agent-dictionary-value-catalog:show` | `src/contexts/runtimeagentoperations/slices/agent-dictionary-value-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAgentDictionaryValueCatalogList } from "./pages/my-agent-dictionary-value-catalog-list";

export const pageOverrides = {
  "agent-dictionary-value-catalog:list": <MyAgentDictionaryValueCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `dictionaryValueId` | `string` | `agent-dictionary-value-catalog:field:dictionaryValueId` | `formatValue/display text` |
| `dictionaryId` | `string` | `agent-dictionary-value-catalog:field:dictionaryId` | `formatValue/display text` |
| `dictionaryCode` | `string` | `agent-dictionary-value-catalog:field:dictionaryCode` | `formatValue/display text` |
| `valueCode` | `string` | `agent-dictionary-value-catalog:field:valueCode` | `formatValue/display text` |
| `displayName` | `string` | `agent-dictionary-value-catalog:field:displayName` | `formatValue/display text` |
| `displayOrder` | `number` | `agent-dictionary-value-catalog:field:displayOrder` | `formatValue/display text` |
| `active` | `boolean` | `agent-dictionary-value-catalog:field:active` | `formatValue/display text` |
| `state` | `string` | `agent-dictionary-value-catalog:field:state` | `formatValue/display text` |
| `syncedAt` | `string` | `agent-dictionary-value-catalog:field:syncedAt` | `formatValue/display text` |

### Agent Feature Schema Catalog

| Property | Value |
| --- | --- |
| Resource name | `agent_feature_schema_catalog` |
| Route | `/agent-feature-schema-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/agent-feature-schema-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/agent-feature-schema-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "agent_feature_schema_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "agent_feature_schema_catalog", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `agent-feature-schema-catalog:list` | `src/contexts/runtimeagentoperations/slices/agent-feature-schema-catalog/list.tsx` |
| `show` | `agent-feature-schema-catalog:show` | `src/contexts/runtimeagentoperations/slices/agent-feature-schema-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAgentFeatureSchemaCatalogList } from "./pages/my-agent-feature-schema-catalog-list";

export const pageOverrides = {
  "agent-feature-schema-catalog:list": <MyAgentFeatureSchemaCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `featureSchemaId` | `string` | `agent-feature-schema-catalog:field:featureSchemaId` | `formatValue/display text` |
| `featureDomain` | `string` | `agent-feature-schema-catalog:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `agent-feature-schema-catalog:field:featureSchemaVersion` | `formatValue/display text` |
| `schemaStatus` | `string` | `agent-feature-schema-catalog:field:schemaStatus` | `formatValue/display text` |
| `syncedAt` | `string` | `agent-feature-schema-catalog:field:syncedAt` | `formatValue/display text` |

### Agent Organization Directory

| Property | Value |
| --- | --- |
| Resource name | `agent_organization_directory` |
| Route | `/agent-organization-directory` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/agent-organization-directory/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/agent-organization-directory/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "agent_organization_directory" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "agent_organization_directory", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `agent-organization-directory:list` | `src/contexts/runtimeagentoperations/slices/agent-organization-directory/list.tsx` |
| `show` | `agent-organization-directory:show` | `src/contexts/runtimeagentoperations/slices/agent-organization-directory/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAgentOrganizationDirectoryList } from "./pages/my-agent-organization-directory-list";

export const pageOverrides = {
  "agent-organization-directory:list": <MyAgentOrganizationDirectoryList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `organizationId` | `string` | `agent-organization-directory:field:organizationId` | `formatValue/display text` |
| `organizationName` | `string` | `agent-organization-directory:field:organizationName` | `formatValue/display text` |
| `organizationType` | `string` | `agent-organization-directory:field:organizationType` | `formatValue/display text` |
| `state` | `string` | `agent-organization-directory:field:state` | `formatValue/display text` |
| `syncedAt` | `string` | `agent-organization-directory:field:syncedAt` | `formatValue/display text` |

### Agent Runtime Identity Catalog

| Property | Value |
| --- | --- |
| Resource name | `agent_runtime_identity_catalog` |
| Route | `/agent-runtime-identity-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/agent-runtime-identity-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/agent-runtime-identity-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "agent_runtime_identity_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "agent_runtime_identity_catalog", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `agent-runtime-identity-catalog:list` | `src/contexts/runtimeagentoperations/slices/agent-runtime-identity-catalog/list.tsx` |
| `show` | `agent-runtime-identity-catalog:show` | `src/contexts/runtimeagentoperations/slices/agent-runtime-identity-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAgentRuntimeIdentityCatalogList } from "./pages/my-agent-runtime-identity-catalog-list";

export const pageOverrides = {
  "agent-runtime-identity-catalog:list": <MyAgentRuntimeIdentityCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeId` | `string` | `agent-runtime-identity-catalog:field:runtimeId` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `agent-runtime-identity-catalog:field:runtimeInfrastructureId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `agent-runtime-identity-catalog:field:runtimeAgentId` | `formatValue/display text` |
| `organizationId` | `string` | `agent-runtime-identity-catalog:field:organizationId` | `formatValue/display text` |
| `organizationName` | `string` | `agent-runtime-identity-catalog:field:organizationName` | `formatValue/display text` |
| `runtimeName` | `string` | `agent-runtime-identity-catalog:field:runtimeName` | `formatValue/display text` |
| `identityStatus` | `string` | `agent-runtime-identity-catalog:field:identityStatus` | `formatValue/display text` |
| `activatedAt` | `string` | `agent-runtime-identity-catalog:field:activatedAt` | `formatValue/display text` |
| `revokedAt` | `string` | `agent-runtime-identity-catalog:field:revokedAt` | `formatValue/display text` |
| `syncedAt` | `string` | `agent-runtime-identity-catalog:field:syncedAt` | `formatValue/display text` |

### Agent Runtime Infrastructure Connection Catalog

| Property | Value |
| --- | --- |
| Resource name | `agent_runtime_infrastructure_connection_catalog` |
| Route | `/agent-runtime-infrastructure-connection-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/agent-runtime-infrastructure-connection-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/agent-runtime-infrastructure-connection-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "agent_runtime_infrastructure_connection_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "agent_runtime_infrastructure_connection_catalog", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `agent-runtime-infrastructure-connection-catalog:list` | `src/contexts/runtimeagentoperations/slices/agent-runtime-infrastructure-connection-catalog/list.tsx` |
| `show` | `agent-runtime-infrastructure-connection-catalog:show` | `src/contexts/runtimeagentoperations/slices/agent-runtime-infrastructure-connection-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAgentRuntimeInfrastructureConnectionCatalogList } from "./pages/my-agent-runtime-infrastructure-connection-catalog-list";

export const pageOverrides = {
  "agent-runtime-infrastructure-connection-catalog:list": <MyAgentRuntimeInfrastructureConnectionCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeInfrastructureId` | `string` | `agent-runtime-infrastructure-connection-catalog:field:runtimeInfrastructureId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `agent-runtime-infrastructure-connection-catalog:field:runtimeAgentId` | `formatValue/display text` |
| `runtimePlatformConnectionReady` | `boolean` | `agent-runtime-infrastructure-connection-catalog:field:runtimePlatformConnectionReady` | `formatValue/display text` |
| `platformApiReachable` | `boolean` | `agent-runtime-infrastructure-connection-catalog:field:platformApiReachable` | `formatValue/display text` |
| `agentAuthenticationSucceeded` | `boolean` | `agent-runtime-infrastructure-connection-catalog:field:agentAuthenticationSucceeded` | `formatValue/display text` |
| `controlChannelEstablished` | `boolean` | `agent-runtime-infrastructure-connection-catalog:field:controlChannelEstablished` | `formatValue/display text` |
| `heartbeatAccepted` | `boolean` | `agent-runtime-infrastructure-connection-catalog:field:heartbeatAccepted` | `formatValue/display text` |
| `connectedAt` | `string` | `agent-runtime-infrastructure-connection-catalog:field:connectedAt` | `formatValue/display text` |
| `connectionReportFailedAt` | `string` | `agent-runtime-infrastructure-connection-catalog:field:connectionReportFailedAt` | `formatValue/display text` |
| `connectionReportFailureReason` | `string` | `agent-runtime-infrastructure-connection-catalog:field:connectionReportFailureReason` | `formatValue/display text` |
| `connectionReportRetryable` | `boolean` | `agent-runtime-infrastructure-connection-catalog:field:connectionReportRetryable` | `formatValue/display text` |

### Agent Runtime Node Inventory Catalog

| Property | Value |
| --- | --- |
| Resource name | `agent_runtime_node_inventory_catalog` |
| Route | `/agent-runtime-node-inventory-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/agent-runtime-node-inventory-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/agent-runtime-node-inventory-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "agent_runtime_node_inventory_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "agent_runtime_node_inventory_catalog", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `agent-runtime-node-inventory-catalog:list` | `src/contexts/runtimeagentoperations/slices/agent-runtime-node-inventory-catalog/list.tsx` |
| `show` | `agent-runtime-node-inventory-catalog:show` | `src/contexts/runtimeagentoperations/slices/agent-runtime-node-inventory-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAgentRuntimeNodeInventoryCatalogList } from "./pages/my-agent-runtime-node-inventory-catalog-list";

export const pageOverrides = {
  "agent-runtime-node-inventory-catalog:list": <MyAgentRuntimeNodeInventoryCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeNodeInventoryReportId` | `string` | `agent-runtime-node-inventory-catalog:field:runtimeNodeInventoryReportId` | `formatValue/display text` |
| `organizationId` | `string` | `agent-runtime-node-inventory-catalog:field:organizationId` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `agent-runtime-node-inventory-catalog:field:runtimeInfrastructureId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `agent-runtime-node-inventory-catalog:field:runtimeAgentId` | `formatValue/display text` |
| `organizationName` | `string` | `agent-runtime-node-inventory-catalog:field:organizationName` | `formatValue/display text` |
| `runtimeNodeName` | `string` | `agent-runtime-node-inventory-catalog:field:runtimeNodeName` | `formatValue/display text` |
| `infrastructureNodeId` | `string` | `agent-runtime-node-inventory-catalog:field:infrastructureNodeId` | `formatValue/display text` |
| `runtimeNodeRole` | `string` | `agent-runtime-node-inventory-catalog:field:runtimeNodeRole` | `formatValue/display text` |
| `nodeReady` | `boolean` | `agent-runtime-node-inventory-catalog:field:nodeReady` | `formatValue/display text` |
| `runtimeEngineVersion` | `string` | `agent-runtime-node-inventory-catalog:field:runtimeEngineVersion` | `formatValue/display text` |
| `containerEngineVersion` | `string` | `agent-runtime-node-inventory-catalog:field:containerEngineVersion` | `formatValue/display text` |
| `operatingSystem` | `string` | `agent-runtime-node-inventory-catalog:field:operatingSystem` | `formatValue/display text` |
| `architecture` | `string` | `agent-runtime-node-inventory-catalog:field:architecture` | `formatValue/display text` |
| `inventoryHash` | `string` | `agent-runtime-node-inventory-catalog:field:inventoryHash` | `formatValue/display text` |
| `discoveredAt` | `string` | `agent-runtime-node-inventory-catalog:field:discoveredAt` | `formatValue/display text` |

### Agent Runtime Node Resource Latest

| Property | Value |
| --- | --- |
| Resource name | `agent_runtime_node_resource_latest` |
| Route | `/agent-runtime-node-resource-latest` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/agent-runtime-node-resource-latest/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/agent-runtime-node-resource-latest/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "agent_runtime_node_resource_latest" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "agent_runtime_node_resource_latest", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `agent-runtime-node-resource-latest:list` | `src/contexts/runtimeagentoperations/slices/agent-runtime-node-resource-latest/list.tsx` |
| `show` | `agent-runtime-node-resource-latest:show` | `src/contexts/runtimeagentoperations/slices/agent-runtime-node-resource-latest/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAgentRuntimeNodeResourceLatestList } from "./pages/my-agent-runtime-node-resource-latest-list";

export const pageOverrides = {
  "agent-runtime-node-resource-latest:list": <MyAgentRuntimeNodeResourceLatestList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `nodeId` | `string` | `agent-runtime-node-resource-latest:field:nodeId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `agent-runtime-node-resource-latest:field:runtimeAgentId` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `agent-runtime-node-resource-latest:field:runtimeInfrastructureId` | `formatValue/display text` |
| `runtimeNodeName` | `string` | `agent-runtime-node-resource-latest:field:runtimeNodeName` | `formatValue/display text` |
| `nodeReady` | `boolean` | `agent-runtime-node-resource-latest:field:nodeReady` | `formatValue/display text` |
| `allocatableCpuCores` | `number` | `agent-runtime-node-resource-latest:field:allocatableCpuCores` | `formatValue/display text` |
| `allocatableMemoryGb` | `number` | `agent-runtime-node-resource-latest:field:allocatableMemoryGb` | `formatValue/display text` |
| `allocatableGpuCount` | `number` | `agent-runtime-node-resource-latest:field:allocatableGpuCount` | `formatValue/display text` |
| `allocatedCpuCores` | `number` | `agent-runtime-node-resource-latest:field:allocatedCpuCores` | `formatValue/display text` |
| `allocatedMemoryGb` | `number` | `agent-runtime-node-resource-latest:field:allocatedMemoryGb` | `formatValue/display text` |
| `allocatedGpuCount` | `number` | `agent-runtime-node-resource-latest:field:allocatedGpuCount` | `formatValue/display text` |
| `availableCpuCores` | `number` | `agent-runtime-node-resource-latest:field:availableCpuCores` | `formatValue/display text` |
| `availableMemoryGb` | `number` | `agent-runtime-node-resource-latest:field:availableMemoryGb` | `formatValue/display text` |
| `availableGpuCount` | `number` | `agent-runtime-node-resource-latest:field:availableGpuCount` | `formatValue/display text` |
| `runningWorkloadCount` | `number` | `agent-runtime-node-resource-latest:field:runningWorkloadCount` | `formatValue/display text` |
| `workloadCapacity` | `number` | `agent-runtime-node-resource-latest:field:workloadCapacity` | `formatValue/display text` |
| `observedAt` | `string` | `agent-runtime-node-resource-latest:field:observedAt` | `formatValue/display text` |
| `telemetryRetentionPolicy` | `string` | `agent-runtime-node-resource-latest:field:telemetryRetentionPolicy` | `formatValue/display text` |

### Agent Runtime Telemetry Latest

| Property | Value |
| --- | --- |
| Resource name | `agent_runtime_telemetry_latest` |
| Route | `/agent-runtime-telemetry-latest` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/agent-runtime-telemetry-latest/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/agent-runtime-telemetry-latest/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "agent_runtime_telemetry_latest" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "agent_runtime_telemetry_latest", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `agent-runtime-telemetry-latest:list` | `src/contexts/runtimeagentoperations/slices/agent-runtime-telemetry-latest/list.tsx` |
| `show` | `agent-runtime-telemetry-latest:show` | `src/contexts/runtimeagentoperations/slices/agent-runtime-telemetry-latest/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAgentRuntimeTelemetryLatestList } from "./pages/my-agent-runtime-telemetry-latest-list";

export const pageOverrides = {
  "agent-runtime-telemetry-latest:list": <MyAgentRuntimeTelemetryLatestList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `nodeId` | `string` | `agent-runtime-telemetry-latest:field:nodeId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `agent-runtime-telemetry-latest:field:runtimeAgentId` | `formatValue/display text` |
| `federationId` | `string` | `agent-runtime-telemetry-latest:field:federationId` | `formatValue/display text` |
| `trainingJobId` | `string` | `agent-runtime-telemetry-latest:field:trainingJobId` | `formatValue/display text` |
| `roundExecutionId` | `string` | `agent-runtime-telemetry-latest:field:roundExecutionId` | `formatValue/display text` |
| `cpuLoad` | `string` | `agent-runtime-telemetry-latest:field:cpuLoad` | `formatValue/display text` |
| `gpuLoad` | `string` | `agent-runtime-telemetry-latest:field:gpuLoad` | `formatValue/display text` |
| `memoryLoad` | `string` | `agent-runtime-telemetry-latest:field:memoryLoad` | `formatValue/display text` |
| `lastHeartbeatAt` | `string` | `agent-runtime-telemetry-latest:field:lastHeartbeatAt` | `formatValue/display text` |
| `telemetryRetentionPolicy` | `string` | `agent-runtime-telemetry-latest:field:telemetryRetentionPolicy` | `formatValue/display text` |

### Dataset Capability

| Property | Value |
| --- | --- |
| Resource name | `dataset_capability` |
| Route | `/dataset-capability` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/dataset-capability/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/dataset-capability/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "dataset_capability" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "dataset_capability", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `dataset-capability:list` | `src/contexts/runtimeagentoperations/slices/dataset-capability/list.tsx` |
| `show` | `dataset-capability:show` | `src/contexts/runtimeagentoperations/slices/dataset-capability/show.tsx` |
| `declareDataset` | `dataset-capability:declareDataset` | `src/contexts/runtimeagentoperations/slices/declare-dataset/declare-dataset.tsx` |
| `retryDatasetContractValidation` | `dataset-capability:retryDatasetContractValidation` | `src/contexts/runtimeagentoperations/slices/retry-dataset-contract-validation/retry-dataset-contract-validation-dataset-capability.tsx` |
| `rejectDatasetForTraining` | `dataset-capability:rejectDatasetForTraining` | `src/contexts/runtimeagentoperations/slices/reject-dataset-for-training/reject-dataset-for-training-dataset-capability.tsx` |
| `approveDatasetForTraining` | `dataset-capability:approveDatasetForTraining` | `src/contexts/runtimeagentoperations/slices/approve-dataset-for-training/approve-dataset-for-training-dataset-capability.tsx` |
| `revokeDatasetTrainingApproval` | `dataset-capability:revokeDatasetTrainingApproval` | `src/contexts/runtimeagentoperations/slices/revoke-dataset-training-approval/revoke-dataset-training-approval-dataset-capability.tsx` |
| `configureRuntimeDatasetBinding` | `dataset-capability:configureRuntimeDatasetBinding` | `src/contexts/runtimeagentoperations/slices/configure-runtime-dataset-binding/configure-runtime-dataset-binding-dataset-capability.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyDatasetCapabilityList } from "./pages/my-dataset-capability-list";

export const pageOverrides = {
  "dataset-capability:list": <MyDatasetCapabilityList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `declareDataset` | `dataset-capability:declareDataset` | `src/contexts/runtimeagentoperations/slices/declare-dataset/declare-dataset.tsx` | `organizationId`, `featureSchemaId`, `datasetName`, `datasetUsage` |
| `retryDatasetContractValidation` | `dataset-capability:retryDatasetContractValidation` | `src/contexts/runtimeagentoperations/slices/retry-dataset-contract-validation/retry-dataset-contract-validation-dataset-capability.tsx` | _(none)_ |
| `rejectDatasetForTraining` | `dataset-capability:rejectDatasetForTraining` | `src/contexts/runtimeagentoperations/slices/reject-dataset-for-training/reject-dataset-for-training-dataset-capability.tsx` | `rejectionReason` |
| `approveDatasetForTraining` | `dataset-capability:approveDatasetForTraining` | `src/contexts/runtimeagentoperations/slices/approve-dataset-for-training/approve-dataset-for-training-dataset-capability.tsx` | _(none)_ |
| `revokeDatasetTrainingApproval` | `dataset-capability:revokeDatasetTrainingApproval` | `src/contexts/runtimeagentoperations/slices/revoke-dataset-training-approval/revoke-dataset-training-approval-dataset-capability.tsx` | `revokeReason` |
| `configureRuntimeDatasetBinding` | `dataset-capability:configureRuntimeDatasetBinding` | `src/contexts/runtimeagentoperations/slices/configure-runtime-dataset-binding/configure-runtime-dataset-binding-dataset-capability.tsx` | `datasetId`, `runtimeId`, `filePath`, `dataFormat` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `datasetId` | `string` | `dataset-capability:field:datasetId` | `formatValue/display text` |
| `organizationId` | `string` | `dataset-capability:field:organizationId` | `formatValue/display text` |
| `runtimeId` | `string` | `dataset-capability:field:runtimeId` | `formatValue/display text` |
| `featureSchemaId` | `string` | `dataset-capability:field:featureSchemaId` | `formatValue/display text` |
| `features` | `FeatureDefinition[]` | `dataset-capability:field:features` | `formatValue/display text` |
| `labels` | `LabelDefinition[]` | `dataset-capability:field:labels` | `formatValue/display text` |
| `organizationName` | `string` | `dataset-capability:field:organizationName` | `formatValue/display text` |
| `featureDomain` | `string` | `dataset-capability:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `dataset-capability:field:featureSchemaVersion` | `formatValue/display text` |
| `datasetName` | `string` | `dataset-capability:field:datasetName` | `formatValue/display text` |
| `datasetUsage` | `string` | `dataset-capability:field:datasetUsage` | `formatValue/display text` |
| `sampleCount` | `number` | `dataset-capability:field:sampleCount` | `formatValue/display text` |
| `featureCount` | `number` | `dataset-capability:field:featureCount` | `formatValue/display text` |
| `schemaCompatible` | `boolean` | `dataset-capability:field:schemaCompatible` | `formatValue/display text` |
| `labelCompatible` | `boolean` | `dataset-capability:field:labelCompatible` | `formatValue/display text` |
| `qualityScore` | `string` | `dataset-capability:field:qualityScore` | `formatValue/display text` |
| `nonIidScore` | `string` | `dataset-capability:field:nonIidScore` | `formatValue/display text` |
| `metadataReportId` | `string` | `dataset-capability:field:metadataReportId` | `formatValue/display text` |
| `metadataStatus` | `string` | `dataset-capability:field:metadataStatus` | `formatValue/display text` |
| `contractStatus` | `string` | `dataset-capability:field:contractStatus` | `formatValue/display text` |
| `approvalStatus` | `string` | `dataset-capability:field:approvalStatus` | `formatValue/display text` |
| `approved` | `boolean` | `dataset-capability:field:approved` | `formatValue/display text` |
| `lastProfiledAt` | `string` | `dataset-capability:field:lastProfiledAt` | `formatValue/display text` |

### Dataset Readiness

| Property | Value |
| --- | --- |
| Resource name | `dataset_readiness` |
| Route | `/dataset-readiness` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/dataset-readiness/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/dataset-readiness/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "dataset_readiness" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "dataset_readiness", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `dataset-readiness:list` | `src/contexts/runtimeagentoperations/slices/dataset-readiness/list.tsx` |
| `show` | `dataset-readiness:show` | `src/contexts/runtimeagentoperations/slices/dataset-readiness/show.tsx` |
| `configureRuntimeDatasetBinding` | `dataset-readiness:configureRuntimeDatasetBinding` | `src/contexts/runtimeagentoperations/slices/configure-runtime-dataset-binding/configure-runtime-dataset-binding-dataset-readiness.tsx` |
| `rejectDatasetForTraining` | `dataset-readiness:rejectDatasetForTraining` | `src/contexts/runtimeagentoperations/slices/reject-dataset-for-training/reject-dataset-for-training-dataset-readiness.tsx` |
| `approveDatasetForTraining` | `dataset-readiness:approveDatasetForTraining` | `src/contexts/runtimeagentoperations/slices/approve-dataset-for-training/approve-dataset-for-training-dataset-readiness.tsx` |
| `retryDatasetContractValidation` | `dataset-readiness:retryDatasetContractValidation` | `src/contexts/runtimeagentoperations/slices/retry-dataset-contract-validation/retry-dataset-contract-validation-dataset-readiness.tsx` |
| `revokeDatasetTrainingApproval` | `dataset-readiness:revokeDatasetTrainingApproval` | `src/contexts/runtimeagentoperations/slices/revoke-dataset-training-approval/revoke-dataset-training-approval-dataset-readiness.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyDatasetReadinessList } from "./pages/my-dataset-readiness-list";

export const pageOverrides = {
  "dataset-readiness:list": <MyDatasetReadinessList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `configureRuntimeDatasetBinding` | `dataset-readiness:configureRuntimeDatasetBinding` | `src/contexts/runtimeagentoperations/slices/configure-runtime-dataset-binding/configure-runtime-dataset-binding-dataset-readiness.tsx` | `datasetId`, `runtimeId`, `filePath`, `dataFormat` |
| `rejectDatasetForTraining` | `dataset-readiness:rejectDatasetForTraining` | `src/contexts/runtimeagentoperations/slices/reject-dataset-for-training/reject-dataset-for-training-dataset-readiness.tsx` | `rejectionReason` |
| `approveDatasetForTraining` | `dataset-readiness:approveDatasetForTraining` | `src/contexts/runtimeagentoperations/slices/approve-dataset-for-training/approve-dataset-for-training-dataset-readiness.tsx` | _(none)_ |
| `retryDatasetContractValidation` | `dataset-readiness:retryDatasetContractValidation` | `src/contexts/runtimeagentoperations/slices/retry-dataset-contract-validation/retry-dataset-contract-validation-dataset-readiness.tsx` | _(none)_ |
| `revokeDatasetTrainingApproval` | `dataset-readiness:revokeDatasetTrainingApproval` | `src/contexts/runtimeagentoperations/slices/revoke-dataset-training-approval/revoke-dataset-training-approval-dataset-readiness.tsx` | `revokeReason` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `datasetId` | `string` | `dataset-readiness:field:datasetId` | `formatValue/display text` |
| `organizationId` | `string` | `dataset-readiness:field:organizationId` | `formatValue/display text` |
| `runtimeId` | `string` | `dataset-readiness:field:runtimeId` | `formatValue/display text` |
| `featureSchemaId` | `string` | `dataset-readiness:field:featureSchemaId` | `formatValue/display text` |
| `datasetName` | `string` | `dataset-readiness:field:datasetName` | `formatValue/display text` |
| `organizationName` | `string` | `dataset-readiness:field:organizationName` | `formatValue/display text` |
| `featureDomain` | `string` | `dataset-readiness:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `dataset-readiness:field:featureSchemaVersion` | `formatValue/display text` |
| `datasetUsage` | `string` | `dataset-readiness:field:datasetUsage` | `formatValue/display text` |
| `metadataStatus` | `string` | `dataset-readiness:field:metadataStatus` | `formatValue/display text` |
| `contractStatus` | `string` | `dataset-readiness:field:contractStatus` | `formatValue/display text` |
| `approvalStatus` | `string` | `dataset-readiness:field:approvalStatus` | `formatValue/display text` |
| `accessStatus` | `string` | `dataset-readiness:field:accessStatus` | `formatValue/display text` |
| `runtimeStatus` | `string` | `dataset-readiness:field:runtimeStatus` | `formatValue/display text` |
| `overallReadiness` | `string` | `dataset-readiness:field:overallReadiness` | `formatValue/display text` |
| `readyForTraining` | `boolean` | `dataset-readiness:field:readyForTraining` | `formatValue/display text` |
| `canBeSelectedForTraining` | `boolean` | `dataset-readiness:field:canBeSelectedForTraining` | `formatValue/display text` |
| `readinessScore` | `number` | `dataset-readiness:field:readinessScore` | `formatValue/display text` |
| `missingRequirements` | `string[]` | `dataset-readiness:field:missingRequirements` | `formatValue/display text` |
| `blockingReasons` | `string[]` | `dataset-readiness:field:blockingReasons` | `formatValue/display text` |
| `warnings` | `string[]` | `dataset-readiness:field:warnings` | `formatValue/display text` |
| `sampleCount` | `number` | `dataset-readiness:field:sampleCount` | `formatValue/display text` |
| `featureCount` | `number` | `dataset-readiness:field:featureCount` | `formatValue/display text` |
| `schemaCompatible` | `boolean` | `dataset-readiness:field:schemaCompatible` | `formatValue/display text` |
| `labelCompatible` | `boolean` | `dataset-readiness:field:labelCompatible` | `formatValue/display text` |
| `qualityScore` | `string` | `dataset-readiness:field:qualityScore` | `formatValue/display text` |
| `nonIidScore` | `string` | `dataset-readiness:field:nonIidScore` | `formatValue/display text` |
| `classBalanceScore` | `string` | `dataset-readiness:field:classBalanceScore` | `formatValue/display text` |
| `metadataReportId` | `string` | `dataset-readiness:field:metadataReportId` | `formatValue/display text` |
| `datasetAccessValidationId` | `string` | `dataset-readiness:field:datasetAccessValidationId` | `formatValue/display text` |
| `readable` | `boolean` | `dataset-readiness:field:readable` | `formatValue/display text` |
| `schemaReadable` | `boolean` | `dataset-readiness:field:schemaReadable` | `formatValue/display text` |
| `sampleBatchReadable` | `boolean` | `dataset-readiness:field:sampleBatchReadable` | `formatValue/display text` |
| `lastProfiledAt` | `string` | `dataset-readiness:field:lastProfiledAt` | `formatValue/display text` |
| `lastAccessValidatedAt` | `string` | `dataset-readiness:field:lastAccessValidatedAt` | `formatValue/display text` |
| `lastRuntimeHeartbeatAt` | `string` | `dataset-readiness:field:lastRuntimeHeartbeatAt` | `formatValue/display text` |
| `lastUpdatedAt` | `string` | `dataset-readiness:field:lastUpdatedAt` | `formatValue/display text` |

### Permission Catalog

| Property | Value |
| --- | --- |
| Resource name | `permission_catalog` |
| Route | `/permission-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/identityaccessmanagement/slices/permission-catalogs/list.tsx` |
| Generated show page | `src/contexts/identityaccessmanagement/slices/permission-catalogs/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "permission_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "permission_catalog", parent: "identityaccessmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `permission-catalog:list` | `src/contexts/identityaccessmanagement/slices/permission-catalogs/list.tsx` |
| `show` | `permission-catalog:show` | `src/contexts/identityaccessmanagement/slices/permission-catalogs/show.tsx` |
| `registerPermission` | `permission-catalog:registerPermission` | `src/contexts/identityaccessmanagement/slices/register-permission/register-permission.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyPermissionCatalogList } from "./pages/my-permission-catalog-list";

export const pageOverrides = {
  "permission-catalog:list": <MyPermissionCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `registerPermission` | `permission-catalog:registerPermission` | `src/contexts/identityaccessmanagement/slices/register-permission/register-permission.tsx` | `permissionCode`, `permissionName`, `description` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `permissionId` | `string` | `permission-catalog:field:permissionId` | `formatValue/display text` |
| `permissionCode` | `string` | `permission-catalog:field:permissionCode` | `formatValue/display text` |
| `permissionName` | `string` | `permission-catalog:field:permissionName` | `formatValue/display text` |
| `description` | `string` | `permission-catalog:field:description` | `formatValue/display text` |

### Role Catalog

| Property | Value |
| --- | --- |
| Resource name | `role_catalog` |
| Route | `/role-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/identityaccessmanagement/slices/role-catalogs/list.tsx` |
| Generated show page | `src/contexts/identityaccessmanagement/slices/role-catalogs/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "role_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "role_catalog", parent: "identityaccessmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `role-catalog:list` | `src/contexts/identityaccessmanagement/slices/role-catalogs/list.tsx` |
| `show` | `role-catalog:show` | `src/contexts/identityaccessmanagement/slices/role-catalogs/show.tsx` |
| `registerRole` | `role-catalog:registerRole` | `src/contexts/identityaccessmanagement/slices/register-role/register-role.tsx` |
| `grantPermissionToRole` | `role-catalog:grantPermissionToRole` | `src/contexts/identityaccessmanagement/slices/grant-permission-to-role/grant-permission-to-role.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRoleCatalogList } from "./pages/my-role-catalog-list";

export const pageOverrides = {
  "role-catalog:list": <MyRoleCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `registerRole` | `role-catalog:registerRole` | `src/contexts/identityaccessmanagement/slices/register-role/register-role.tsx` | `roleCode`, `roleName` |
| `grantPermissionToRole` | `role-catalog:grantPermissionToRole` | `src/contexts/identityaccessmanagement/slices/grant-permission-to-role/grant-permission-to-role.tsx` | `roleId`, `roleCode`, `permissionCodes` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `roleId` | `string` | `role-catalog:field:roleId` | `formatValue/display text` |
| `roleCode` | `string` | `role-catalog:field:roleCode` | `formatValue/display text` |
| `roleName` | `string` | `role-catalog:field:roleName` | `formatValue/display text` |

### Role Permission Grant Catalog

| Property | Value |
| --- | --- |
| Resource name | `role_permission_grant_catalog` |
| Route | `/role-permission-grant-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/identityaccessmanagement/slices/role-permission-grant-catalog/list.tsx` |
| Generated show page | `src/contexts/identityaccessmanagement/slices/role-permission-grant-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "role_permission_grant_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "role_permission_grant_catalog", parent: "identityaccessmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `role-permission-grant-catalog:list` | `src/contexts/identityaccessmanagement/slices/role-permission-grant-catalog/list.tsx` |
| `show` | `role-permission-grant-catalog:show` | `src/contexts/identityaccessmanagement/slices/role-permission-grant-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRolePermissionGrantCatalogList } from "./pages/my-role-permission-grant-catalog-list";

export const pageOverrides = {
  "role-permission-grant-catalog:list": <MyRolePermissionGrantCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `roleId` | `string` | `role-permission-grant-catalog:field:roleId` | `formatValue/display text` |
| `roleCode` | `string` | `role-permission-grant-catalog:field:roleCode` | `formatValue/display text` |
| `roleName` | `string` | `role-permission-grant-catalog:field:roleName` | `formatValue/display text` |
| `permissionCode` | `string` | `role-permission-grant-catalog:field:permissionCode` | `formatValue/display text` |
| `permissionName` | `string` | `role-permission-grant-catalog:field:permissionName` | `formatValue/display text` |

### Round Execution Catalog

| Property | Value |
| --- | --- |
| Resource name | `round_execution_catalog` |
| Route | `/round-execution-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/round-execution-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/round-execution-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "round_execution_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "round_execution_catalog", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `round-execution-catalog:list` | `src/contexts/runtimeagentoperations/slices/round-execution-catalog/list.tsx` |
| `show` | `round-execution-catalog:show` | `src/contexts/runtimeagentoperations/slices/round-execution-catalog/show.tsx` |
| `retryRoundExecutionAfterStartFailure` | `round-execution-catalog:retryRoundExecutionAfterStartFailure` | `src/contexts/runtimeagentoperations/slices/retry-round-execution-after-start-failure/retry-round-execution-after-start-failure.tsx` |
| `retryRoundExecutionAfterRuntimeFailure` | `round-execution-catalog:retryRoundExecutionAfterRuntimeFailure` | `src/contexts/runtimeagentoperations/slices/retry-round-execution-after-runtime-failure/retry-round-execution-after-runtime-failure.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRoundExecutionCatalogList } from "./pages/my-round-execution-catalog-list";

export const pageOverrides = {
  "round-execution-catalog:list": <MyRoundExecutionCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `retryRoundExecutionAfterStartFailure` | `round-execution-catalog:retryRoundExecutionAfterStartFailure` | `src/contexts/runtimeagentoperations/slices/retry-round-execution-after-start-failure/retry-round-execution-after-start-failure.tsx` | `executionSessionId`, `executionPlanId`, `trainingJobId`, `trainingRunConfigurationId`, `roundId`, `roundNumber`, `runtimeId`, `organizationId`, `featureSchemaId`, `baseModelId`, `runtimeEngineJobId`, `retryReason` |
| `retryRoundExecutionAfterRuntimeFailure` | `round-execution-catalog:retryRoundExecutionAfterRuntimeFailure` | `src/contexts/runtimeagentoperations/slices/retry-round-execution-after-runtime-failure/retry-round-execution-after-runtime-failure.tsx` | `executionSessionId`, `executionPlanId`, `trainingJobId`, `trainingRunConfigurationId`, `roundId`, `runtimeId`, `runtimeEngineJobId`, `retryReason` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `roundExecutionId` | `string` | `round-execution-catalog:field:roundExecutionId` | `formatValue/display text` |
| `executionSessionId` | `string` | `round-execution-catalog:field:executionSessionId` | `formatValue/display text` |
| `executionPlanId` | `string` | `round-execution-catalog:field:executionPlanId` | `formatValue/display text` |
| `trainingJobId` | `string` | `round-execution-catalog:field:trainingJobId` | `formatValue/display text` |
| `trainingRunConfigurationId` | `string` | `round-execution-catalog:field:trainingRunConfigurationId` | `formatValue/display text` |
| `roundId` | `string` | `round-execution-catalog:field:roundId` | `formatValue/display text` |
| `roundNumber` | `number` | `round-execution-catalog:field:roundNumber` | `formatValue/display text` |
| `organizationId` | `string` | `round-execution-catalog:field:organizationId` | `formatValue/display text` |
| `runtimeId` | `string` | `round-execution-catalog:field:runtimeId` | `formatValue/display text` |
| `state` | `&#34;PLAN_RECEIVED&#34; | &#34;PLAN_ACCEPTED&#34; | &#34;PLAN_REJECTED&#34; | &#34;RUNNING&#34; | &#34;START_FAILED&#34; | &#34;RETRIED&#34; | &#34;COMPLETED&#34; | &#34;FAILED&#34; | &#34;UPDATE_SUBMITTED&#34; | &#34;RUNTIME_ENGINE_RELEASED&#34; | &#34;RUNTIME_ENGINE_RELEASE_HANDLED&#34;` | `round-execution-catalog:field:state` | `Select/display text` |
| `featureSchemaId` | `string` | `round-execution-catalog:field:featureSchemaId` | `formatValue/display text` |
| `baseModelId` | `string` | `round-execution-catalog:field:baseModelId` | `formatValue/display text` |
| `runtimeEngineProfileId` | `string` | `round-execution-catalog:field:runtimeEngineProfileId` | `formatValue/display text` |
| `runtimeEngineProfileName` | `string` | `round-execution-catalog:field:runtimeEngineProfileName` | `formatValue/display text` |
| `runtimeEnginePluginProfile` | `string` | `round-execution-catalog:field:runtimeEnginePluginProfile` | `formatValue/display text` |
| `runtimeEngineImage` | `string` | `round-execution-catalog:field:runtimeEngineImage` | `formatValue/display text` |
| `runtimeEngineImageDigest` | `string` | `round-execution-catalog:field:runtimeEngineImageDigest` | `formatValue/display text` |
| `runtimeEngineJobId` | `string` | `round-execution-catalog:field:runtimeEngineJobId` | `formatValue/display text` |
| `runtimeEngineObservedStatus` | `string` | `round-execution-catalog:field:runtimeEngineObservedStatus` | `formatValue/display text` |
| `runtimeEngineObservationAt` | `string` | `round-execution-catalog:field:runtimeEngineObservationAt` | `formatValue/display text` |
| `localUpdateArtifactRef` | `string` | `round-execution-catalog:field:localUpdateArtifactRef` | `formatValue/display text` |
| `metricsArtifactRef` | `string` | `round-execution-catalog:field:metricsArtifactRef` | `formatValue/display text` |
| `localExecutionRequirementsSatisfied` | `boolean` | `round-execution-catalog:field:localExecutionRequirementsSatisfied` | `formatValue/display text` |
| `runtimeIdentityMatched` | `boolean` | `round-execution-catalog:field:runtimeIdentityMatched` | `formatValue/display text` |
| `runtimeDatasetBindingAvailable` | `boolean` | `round-execution-catalog:field:runtimeDatasetBindingAvailable` | `formatValue/display text` |
| `datasetAccessValidated` | `boolean` | `round-execution-catalog:field:datasetAccessValidated` | `formatValue/display text` |
| `baseModelAvailable` | `boolean` | `round-execution-catalog:field:baseModelAvailable` | `formatValue/display text` |
| `trainingConfigurationSupported` | `boolean` | `round-execution-catalog:field:trainingConfigurationSupported` | `formatValue/display text` |
| `runtimeResourceAvailable` | `boolean` | `round-execution-catalog:field:runtimeResourceAvailable` | `formatValue/display text` |
| `runtimeAgentIdle` | `boolean` | `round-execution-catalog:field:runtimeAgentIdle` | `formatValue/display text` |
| `updateArtifactId` | `string` | `round-execution-catalog:field:updateArtifactId` | `formatValue/display text` |
| `artifactRef` | `string` | `round-execution-catalog:field:artifactRef` | `formatValue/display text` |
| `artifactDigest` | `string` | `round-execution-catalog:field:artifactDigest` | `formatValue/display text` |
| `trainingLoss` | `string` | `round-execution-catalog:field:trainingLoss` | `formatValue/display text` |
| `receivedAt` | `string` | `round-execution-catalog:field:receivedAt` | `formatValue/display text` |
| `acceptedAt` | `string` | `round-execution-catalog:field:acceptedAt` | `formatValue/display text` |
| `rejectedAt` | `string` | `round-execution-catalog:field:rejectedAt` | `formatValue/display text` |
| `startedAt` | `string` | `round-execution-catalog:field:startedAt` | `formatValue/display text` |
| `completedAt` | `string` | `round-execution-catalog:field:completedAt` | `formatValue/display text` |
| `failedAt` | `string` | `round-execution-catalog:field:failedAt` | `formatValue/display text` |
| `submittedAt` | `string` | `round-execution-catalog:field:submittedAt` | `formatValue/display text` |
| `failureReason` | `string` | `round-execution-catalog:field:failureReason` | `formatValue/display text` |
| `retryReason` | `string` | `round-execution-catalog:field:retryReason` | `formatValue/display text` |
| `runtimeEngineReleased` | `boolean` | `round-execution-catalog:field:runtimeEngineReleased` | `formatValue/display text` |
| `runtimeEngineReleaseFailureReason` | `string` | `round-execution-catalog:field:runtimeEngineReleaseFailureReason` | `formatValue/display text` |
| `rejectionReasons` | `string[]` | `round-execution-catalog:field:rejectionReasons` | `formatValue/display text` |

### Runtime Agent Lifecycle Catalog

| Property | Value |
| --- | --- |
| Resource name | `runtime_agent_lifecycle_catalog` |
| Route | `/runtime-agent-lifecycle-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/runtime-agent-lifecycle-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/runtime-agent-lifecycle-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_agent_lifecycle_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_agent_lifecycle_catalog", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-agent-lifecycle-catalog:list` | `src/contexts/runtimeagentoperations/slices/runtime-agent-lifecycle-catalog/list.tsx` |
| `show` | `runtime-agent-lifecycle-catalog:show` | `src/contexts/runtimeagentoperations/slices/runtime-agent-lifecycle-catalog/show.tsx` |
| `loadRuntimeAgentBootstrapConfiguration` | `runtime-agent-lifecycle-catalog:loadRuntimeAgentBootstrapConfiguration` | `src/contexts/runtimeagentoperations/slices/load-runtime-agent-bootstrap-configuration/load-runtime-agent-bootstrap-configuration.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeAgentLifecycleCatalogList } from "./pages/my-runtime-agent-lifecycle-catalog-list";

export const pageOverrides = {
  "runtime-agent-lifecycle-catalog:list": <MyRuntimeAgentLifecycleCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `loadRuntimeAgentBootstrapConfiguration` | `runtime-agent-lifecycle-catalog:loadRuntimeAgentBootstrapConfiguration` | `src/contexts/runtimeagentoperations/slices/load-runtime-agent-bootstrap-configuration/load-runtime-agent-bootstrap-configuration.tsx` | _(none)_ |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeAgentId` | `string` | `runtime-agent-lifecycle-catalog:field:runtimeAgentId` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `runtime-agent-lifecycle-catalog:field:runtimeInfrastructureId` | `formatValue/display text` |
| `agentVersion` | `string` | `runtime-agent-lifecycle-catalog:field:agentVersion` | `formatValue/display text` |
| `runtimeAgentEndpoint` | `string` | `runtime-agent-lifecycle-catalog:field:runtimeAgentEndpoint` | `formatValue/display text` |
| `endpointScope` | `string` | `runtime-agent-lifecycle-catalog:field:endpointScope` | `formatValue/display text` |
| `lifecycleStatus` | `string` | `runtime-agent-lifecycle-catalog:field:lifecycleStatus` | `formatValue/display text` |
| `bootstrapConfigurationLoaded` | `boolean` | `runtime-agent-lifecycle-catalog:field:bootstrapConfigurationLoaded` | `formatValue/display text` |
| `bootstrapFailureReason` | `string` | `runtime-agent-lifecycle-catalog:field:bootstrapFailureReason` | `formatValue/display text` |
| `runtimeAgentSelfCheckPassed` | `boolean` | `runtime-agent-lifecycle-catalog:field:runtimeAgentSelfCheckPassed` | `formatValue/display text` |
| `configurationLoaded` | `boolean` | `runtime-agent-lifecycle-catalog:field:configurationLoaded` | `formatValue/display text` |
| `secretStoreAccessible` | `boolean` | `runtime-agent-lifecycle-catalog:field:secretStoreAccessible` | `formatValue/display text` |
| `runtimeEngineAdapterReady` | `boolean` | `runtime-agent-lifecycle-catalog:field:runtimeEngineAdapterReady` | `formatValue/display text` |
| `modelRepositoryClientReady` | `boolean` | `runtime-agent-lifecycle-catalog:field:modelRepositoryClientReady` | `formatValue/display text` |
| `localDatasetBindingStoreReady` | `boolean` | `runtime-agent-lifecycle-catalog:field:localDatasetBindingStoreReady` | `formatValue/display text` |
| `workingDirectoryWritable` | `boolean` | `runtime-agent-lifecycle-catalog:field:workingDirectoryWritable` | `formatValue/display text` |
| `bootstrappedAt` | `string` | `runtime-agent-lifecycle-catalog:field:bootstrappedAt` | `formatValue/display text` |
| `bootstrapFailedAt` | `string` | `runtime-agent-lifecycle-catalog:field:bootstrapFailedAt` | `formatValue/display text` |
| `startedAt` | `string` | `runtime-agent-lifecycle-catalog:field:startedAt` | `formatValue/display text` |
| `readyAt` | `string` | `runtime-agent-lifecycle-catalog:field:readyAt` | `formatValue/display text` |

### Runtime Dataset Binding Catalog

| Property | Value |
| --- | --- |
| Resource name | `runtime_dataset_binding_catalog` |
| Route | `/runtime-dataset-binding-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/runtimeagentoperations/slices/runtime-dataset-binding-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeagentoperations/slices/runtime-dataset-binding-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_dataset_binding_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_dataset_binding_catalog", parent: "runtimeagentoperations" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-dataset-binding-catalog:list` | `src/contexts/runtimeagentoperations/slices/runtime-dataset-binding-catalog/list.tsx` |
| `show` | `runtime-dataset-binding-catalog:show` | `src/contexts/runtimeagentoperations/slices/runtime-dataset-binding-catalog/show.tsx` |
| `configureRuntimeDatasetBinding` | `runtime-dataset-binding-catalog:configureRuntimeDatasetBinding` | `src/contexts/runtimeagentoperations/slices/configure-runtime-dataset-binding/configure-runtime-dataset-binding-runtime-dataset-binding-catalog.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeDatasetBindingCatalogList } from "./pages/my-runtime-dataset-binding-catalog-list";

export const pageOverrides = {
  "runtime-dataset-binding-catalog:list": <MyRuntimeDatasetBindingCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `configureRuntimeDatasetBinding` | `runtime-dataset-binding-catalog:configureRuntimeDatasetBinding` | `src/contexts/runtimeagentoperations/slices/configure-runtime-dataset-binding/configure-runtime-dataset-binding-runtime-dataset-binding-catalog.tsx` | `datasetId`, `runtimeId`, `filePath`, `dataFormat` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeDatasetBindingId` | `string` | `runtime-dataset-binding-catalog:field:runtimeDatasetBindingId` | `formatValue/display text` |
| `datasetId` | `string` | `runtime-dataset-binding-catalog:field:datasetId` | `formatValue/display text` |
| `organizationId` | `string` | `runtime-dataset-binding-catalog:field:organizationId` | `formatValue/display text` |
| `runtimeId` | `string` | `runtime-dataset-binding-catalog:field:runtimeId` | `formatValue/display text` |
| `organizationName` | `string` | `runtime-dataset-binding-catalog:field:organizationName` | `formatValue/display text` |
| `featureSchemaId` | `string` | `runtime-dataset-binding-catalog:field:featureSchemaId` | `formatValue/display text` |
| `featureDomain` | `string` | `runtime-dataset-binding-catalog:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `runtime-dataset-binding-catalog:field:featureSchemaVersion` | `formatValue/display text` |
| `datasetName` | `string` | `runtime-dataset-binding-catalog:field:datasetName` | `formatValue/display text` |
| `runtimeName` | `string` | `runtime-dataset-binding-catalog:field:runtimeName` | `formatValue/display text` |
| `filePath` | `string` | `runtime-dataset-binding-catalog:field:filePath` | `formatValue/display text` |
| `dataFormat` | `string` | `runtime-dataset-binding-catalog:field:dataFormat` | `formatValue/display text` |
| `configuredAt` | `string` | `runtime-dataset-binding-catalog:field:configuredAt` | `formatValue/display text` |

### Service Account Api Token Catalog

| Property | Value |
| --- | --- |
| Resource name | `service_account_api_token_catalog` |
| Route | `/service-account-api-token-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/identityaccessmanagement/slices/service-account-api-token-catalogs/list.tsx` |
| Generated show page | `src/contexts/identityaccessmanagement/slices/service-account-api-token-catalogs/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "service_account_api_token_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "service_account_api_token_catalog", parent: "identityaccessmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `service-account-api-token-catalog:list` | `src/contexts/identityaccessmanagement/slices/service-account-api-token-catalogs/list.tsx` |
| `show` | `service-account-api-token-catalog:show` | `src/contexts/identityaccessmanagement/slices/service-account-api-token-catalogs/show.tsx` |
| `issueServiceAccountApiToken` | `service-account-api-token-catalog:issueServiceAccountApiToken` | `src/contexts/identityaccessmanagement/slices/issue-service-account-api-token/issue-service-account-api-token.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyServiceAccountApiTokenCatalogList } from "./pages/my-service-account-api-token-catalog-list";

export const pageOverrides = {
  "service-account-api-token-catalog:list": <MyServiceAccountApiTokenCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `issueServiceAccountApiToken` | `service-account-api-token-catalog:issueServiceAccountApiToken` | `src/contexts/identityaccessmanagement/slices/issue-service-account-api-token/issue-service-account-api-token.tsx` | `userAccountId`, `tokenName` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `apiTokenId` | `string` | `service-account-api-token-catalog:field:apiTokenId` | `formatValue/display text` |
| `userAccountId` | `string` | `service-account-api-token-catalog:field:userAccountId` | `formatValue/display text` |
| `username` | `string` | `service-account-api-token-catalog:field:username` | `formatValue/display text` |
| `tokenName` | `string` | `service-account-api-token-catalog:field:tokenName` | `formatValue/display text` |
| `tokenPrefix` | `string` | `service-account-api-token-catalog:field:tokenPrefix` | `formatValue/display text` |
| `issuedAt` | `string` | `service-account-api-token-catalog:field:issuedAt` | `formatValue/display text` |
| `roles` | `string[]` | `service-account-api-token-catalog:field:roles` | `formatValue/display text` |
| `permissions` | `string[]` | `service-account-api-token-catalog:field:permissions` | `formatValue/display text` |

### User Account Catalog

| Property | Value |
| --- | --- |
| Resource name | `user_account_catalog` |
| Route | `/user-account-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/identityaccessmanagement/slices/user-account-catalogs/list.tsx` |
| Generated show page | `src/contexts/identityaccessmanagement/slices/user-account-catalogs/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "user_account_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "user_account_catalog", parent: "identityaccessmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `user-account-catalog:list` | `src/contexts/identityaccessmanagement/slices/user-account-catalogs/list.tsx` |
| `show` | `user-account-catalog:show` | `src/contexts/identityaccessmanagement/slices/user-account-catalogs/show.tsx` |
| `registerUserAccount` | `user-account-catalog:registerUserAccount` | `src/contexts/identityaccessmanagement/slices/register-user-account/register-user-account.tsx` |
| `generateUserAccountLoginPassword` | `user-account-catalog:generateUserAccountLoginPassword` | `src/contexts/identityaccessmanagement/slices/generate-user-account-login-password/generate-user-account-login-password.tsx` |
| `deactivateUserAccount` | `user-account-catalog:deactivateUserAccount` | `src/contexts/identityaccessmanagement/slices/deactivate-user-account/deactivate-user-account.tsx` |
| `assignRoleToUser` | `user-account-catalog:assignRoleToUser` | `src/contexts/identityaccessmanagement/slices/assign-role-to-user/assign-role-to-user.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyUserAccountCatalogList } from "./pages/my-user-account-catalog-list";

export const pageOverrides = {
  "user-account-catalog:list": <MyUserAccountCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `registerUserAccount` | `user-account-catalog:registerUserAccount` | `src/contexts/identityaccessmanagement/slices/register-user-account/register-user-account.tsx` | `username` |
| `generateUserAccountLoginPassword` | `user-account-catalog:generateUserAccountLoginPassword` | `src/contexts/identityaccessmanagement/slices/generate-user-account-login-password/generate-user-account-login-password.tsx` | `passwordResetRequired` |
| `deactivateUserAccount` | `user-account-catalog:deactivateUserAccount` | `src/contexts/identityaccessmanagement/slices/deactivate-user-account/deactivate-user-account.tsx` | `reason` |
| `assignRoleToUser` | `user-account-catalog:assignRoleToUser` | `src/contexts/identityaccessmanagement/slices/assign-role-to-user/assign-role-to-user.tsx` | `userAccountId`, `roleCodes` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `userAccountId` | `string` | `user-account-catalog:field:userAccountId` | `formatValue/display text` |
| `username` | `string` | `user-account-catalog:field:username` | `formatValue/display text` |
| `providerSubject` | `string` | `user-account-catalog:field:providerSubject` | `formatValue/display text` |
| `userSource` | `string` | `user-account-catalog:field:userSource` | `formatValue/display text` |
| `passwordHash` | `string` | `user-account-catalog:field:passwordHash` | `formatValue/display text` |
| `active` | `boolean` | `user-account-catalog:field:active` | `formatValue/display text` |

### User Role Assignment Catalog

| Property | Value |
| --- | --- |
| Resource name | `user_role_assignment_catalog` |
| Route | `/user-role-assignment-catalog` |
| Backend module | `federation-learning-runtime-agent` |
| Data provider | `federation-learning-runtime-agent` |
| Generated list page | `src/contexts/identityaccessmanagement/slices/user-role-assignment-catalog/list.tsx` |
| Generated show page | `src/contexts/identityaccessmanagement/slices/user-role-assignment-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "user_role_assignment_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "user_role_assignment_catalog", parent: "identityaccessmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `user-role-assignment-catalog:list` | `src/contexts/identityaccessmanagement/slices/user-role-assignment-catalog/list.tsx` |
| `show` | `user-role-assignment-catalog:show` | `src/contexts/identityaccessmanagement/slices/user-role-assignment-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyUserRoleAssignmentCatalogList } from "./pages/my-user-role-assignment-catalog-list";

export const pageOverrides = {
  "user-role-assignment-catalog:list": <MyUserRoleAssignmentCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `userAccountId` | `string` | `user-role-assignment-catalog:field:userAccountId` | `formatValue/display text` |
| `username` | `string` | `user-role-assignment-catalog:field:username` | `formatValue/display text` |
| `roleCode` | `string` | `user-role-assignment-catalog:field:roleCode` | `formatValue/display text` |
| `roleName` | `string` | `user-role-assignment-catalog:field:roleName` | `formatValue/display text` |


## Development Rules

- Keep generated fallback files under `src/contexts/**` reproducible.
- Put business-specific UI behavior in `src/domain/**`.
- If a customization should apply to every generated frontend, change the
  generator template instead of editing a generated project.
- Prefer typed extension points and explicit override keys over path-based
  imports from generated pages.
- Treat this manifest as the local checklist for what can be safely customized.
