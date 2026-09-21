<!-- Generated from config.json by the refine generator. -->

# Frontend Extension Manifest

This document is generated for **Federation Learning Console** (`FederationLearningConsole`). It is the
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
| `federation-learning-support` | Federation Learning Support | `federation-learning-support` | `/dictionary-catalog` |
| `federation-learning-platform` | Federation Learning Platform | `federation-learning-platform` | `/audit-record-log` |
| `federation-learning-runtime-agent` | Federation Learning Runtime Agent | `federation-learning-runtime-agent` | `/dashboard` |

## Resource Extension Points

### Audit Record Log

| Property | Value |
| --- | --- |
| Resource name | `audit_record_log` |
| Route | `/audit-record-log` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimemonitoring/slices/audit-record-log/list.tsx` |
| Generated show page | `src/contexts/runtimemonitoring/slices/audit-record-log/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "audit_record_log" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "audit_record_log", parent: "runtimemonitoring" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `audit-record-log:list` | `src/contexts/runtimemonitoring/slices/audit-record-log/list.tsx` |
| `show` | `audit-record-log:show` | `src/contexts/runtimemonitoring/slices/audit-record-log/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyAuditRecordLogList } from "./pages/my-audit-record-log-list";

export const pageOverrides = {
  "audit-record-log:list": <MyAuditRecordLogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `auditRecordId` | `string` | `audit-record-log:field:auditRecordId` | `formatValue/display text` |
| `sourceEventName` | `string` | `audit-record-log:field:sourceEventName` | `formatValue/display text` |
| `sourceEntityId` | `string` | `audit-record-log:field:sourceEntityId` | `formatValue/display text` |
| `severity` | `string` | `audit-record-log:field:severity` | `formatValue/display text` |
| `payloadHash` | `string` | `audit-record-log:field:payloadHash` | `formatValue/display text` |

### Current Recommended Feature Schema Catalog

| Property | Value |
| --- | --- |
| Resource name | `current_recommended_feature_schema_catalog` |
| Route | `/current-recommended-feature-schema-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/datasetgovernance/slices/current-recommended-feature-schema-catalog/list.tsx` |
| Generated show page | `src/contexts/datasetgovernance/slices/current-recommended-feature-schema-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "current_recommended_feature_schema_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "current_recommended_feature_schema_catalog", parent: "datasetgovernance" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `current-recommended-feature-schema-catalog:list` | `src/contexts/datasetgovernance/slices/current-recommended-feature-schema-catalog/list.tsx` |
| `show` | `current-recommended-feature-schema-catalog:show` | `src/contexts/datasetgovernance/slices/current-recommended-feature-schema-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyCurrentRecommendedFeatureSchemaCatalogList } from "./pages/my-current-recommended-feature-schema-catalog-list";

export const pageOverrides = {
  "current-recommended-feature-schema-catalog:list": <MyCurrentRecommendedFeatureSchemaCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `featureDomain` | `string` | `current-recommended-feature-schema-catalog:field:featureDomain` | `formatValue/display text` |
| `recommendedFeatureSchemaId` | `string` | `current-recommended-feature-schema-catalog:field:recommendedFeatureSchemaId` | `formatValue/display text` |
| `recommendedVersion` | `string` | `current-recommended-feature-schema-catalog:field:recommendedVersion` | `formatValue/display text` |
| `recommendedAt` | `string` | `current-recommended-feature-schema-catalog:field:recommendedAt` | `formatValue/display text` |
| `recommendationNote` | `string` | `current-recommended-feature-schema-catalog:field:recommendationNote` | `formatValue/display text` |

### Dictionary Catalog

| Property | Value |
| --- | --- |
| Resource name | `dictionary_catalog` |
| Route | `/dictionary-catalog` |
| Backend module | `federation-learning-support` |
| Data provider | `federation-learning-support` |
| Generated list page | `src/contexts/dictionarymaintenance/slices/dictionary-catalog/list.tsx` |
| Generated show page | `src/contexts/dictionarymaintenance/slices/dictionary-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "dictionary_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "dictionary_catalog", parent: "dictionarymaintenance" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `dictionary-catalog:list` | `src/contexts/dictionarymaintenance/slices/dictionary-catalog/list.tsx` |
| `show` | `dictionary-catalog:show` | `src/contexts/dictionarymaintenance/slices/dictionary-catalog/show.tsx` |
| `registerDictionary` | `dictionary-catalog:registerDictionary` | `src/contexts/dictionarymaintenance/slices/register-dictionary/register-dictionary.tsx` |
| `updateDictionary` | `dictionary-catalog:updateDictionary` | `src/contexts/dictionarymaintenance/slices/update-dictionary/update-dictionary.tsx` |
| `archiveDictionary` | `dictionary-catalog:archiveDictionary` | `src/contexts/dictionarymaintenance/slices/archive-dictionary/archive-dictionary.tsx` |
| `addDictionaryValue` | `dictionary-catalog:addDictionaryValue` | `src/contexts/dictionarymaintenance/slices/add-dictionary-value/add-dictionary-value-dictionary-catalog.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyDictionaryCatalogList } from "./pages/my-dictionary-catalog-list";

export const pageOverrides = {
  "dictionary-catalog:list": <MyDictionaryCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `registerDictionary` | `dictionary-catalog:registerDictionary` | `src/contexts/dictionarymaintenance/slices/register-dictionary/register-dictionary.tsx` | `dictionaryCode`, `dictionaryName`, `description` |
| `updateDictionary` | `dictionary-catalog:updateDictionary` | `src/contexts/dictionarymaintenance/slices/update-dictionary/update-dictionary.tsx` | `dictionaryName`, `description` |
| `archiveDictionary` | `dictionary-catalog:archiveDictionary` | `src/contexts/dictionarymaintenance/slices/archive-dictionary/archive-dictionary.tsx` | `archiveReason` |
| `addDictionaryValue` | `dictionary-catalog:addDictionaryValue` | `src/contexts/dictionarymaintenance/slices/add-dictionary-value/add-dictionary-value-dictionary-catalog.tsx` | `dictionaryId`, `dictionaryCode`, `valueCode`, `displayName`, `displayOrder`, `description`, `active` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `dictionaryId` | `string` | `dictionary-catalog:field:dictionaryId` | `formatValue/display text` |
| `dictionaryCode` | `DictionaryCode` | `dictionary-catalog:field:dictionaryCode` | `formatValue/display text` |
| `dictionaryName` | `string` | `dictionary-catalog:field:dictionaryName` | `formatValue/display text` |
| `description` | `string` | `dictionary-catalog:field:description` | `CopyableText` |
| `state` | `&#34;REGISTERED&#34; | &#34;ARCHIVED&#34;` | `dictionary-catalog:field:state` | `Select/display text` |
| `registeredAt` | `string` | `dictionary-catalog:field:registeredAt` | `formatValue/display text` |
| `updatedAt` | `string` | `dictionary-catalog:field:updatedAt` | `formatValue/display text` |
| `archivedAt` | `string` | `dictionary-catalog:field:archivedAt` | `formatValue/display text` |
| `archiveReason` | `string` | `dictionary-catalog:field:archiveReason` | `formatValue/display text` |

### Dictionary Value Catalog

| Property | Value |
| --- | --- |
| Resource name | `dictionary_value_catalog` |
| Route | `/dictionary-value-catalog` |
| Backend module | `federation-learning-support` |
| Data provider | `federation-learning-support` |
| Generated list page | `src/contexts/dictionarymaintenance/slices/dictionary-value-catalog/list.tsx` |
| Generated show page | `src/contexts/dictionarymaintenance/slices/dictionary-value-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "dictionary_value_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "dictionary_value_catalog", parent: "dictionarymaintenance" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `dictionary-value-catalog:list` | `src/contexts/dictionarymaintenance/slices/dictionary-value-catalog/list.tsx` |
| `show` | `dictionary-value-catalog:show` | `src/contexts/dictionarymaintenance/slices/dictionary-value-catalog/show.tsx` |
| `addDictionaryValue` | `dictionary-value-catalog:addDictionaryValue` | `src/contexts/dictionarymaintenance/slices/add-dictionary-value/add-dictionary-value-dictionary-value-catalog.tsx` |
| `disableDictionaryValue` | `dictionary-value-catalog:disableDictionaryValue` | `src/contexts/dictionarymaintenance/slices/disable-dictionary-value/disable-dictionary-value.tsx` |
| `enableDictionaryValue` | `dictionary-value-catalog:enableDictionaryValue` | `src/contexts/dictionarymaintenance/slices/enable-dictionary-value/enable-dictionary-value.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyDictionaryValueCatalogList } from "./pages/my-dictionary-value-catalog-list";

export const pageOverrides = {
  "dictionary-value-catalog:list": <MyDictionaryValueCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `addDictionaryValue` | `dictionary-value-catalog:addDictionaryValue` | `src/contexts/dictionarymaintenance/slices/add-dictionary-value/add-dictionary-value-dictionary-value-catalog.tsx` | `dictionaryId`, `dictionaryCode`, `valueCode`, `displayName`, `displayOrder`, `description`, `active` |
| `disableDictionaryValue` | `dictionary-value-catalog:disableDictionaryValue` | `src/contexts/dictionarymaintenance/slices/disable-dictionary-value/disable-dictionary-value.tsx` | `disabledReason` |
| `enableDictionaryValue` | `dictionary-value-catalog:enableDictionaryValue` | `src/contexts/dictionarymaintenance/slices/enable-dictionary-value/enable-dictionary-value.tsx` | `enableReason` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `dictionaryValueId` | `string` | `dictionary-value-catalog:field:dictionaryValueId` | `formatValue/display text` |
| `dictionaryId` | `string` | `dictionary-value-catalog:field:dictionaryId` | `formatValue/display text` |
| `dictionaryCode` | `DictionaryCode` | `dictionary-value-catalog:field:dictionaryCode` | `formatValue/display text` |
| `valueCode` | `DictionaryValueCode` | `dictionary-value-catalog:field:valueCode` | `formatValue/display text` |
| `displayName` | `string` | `dictionary-value-catalog:field:displayName` | `formatValue/display text` |
| `displayOrder` | `DisplayOrder` | `dictionary-value-catalog:field:displayOrder` | `formatValue/display text` |
| `description` | `string` | `dictionary-value-catalog:field:description` | `CopyableText` |
| `active` | `boolean` | `dictionary-value-catalog:field:active` | `formatValue/display text` |
| `state` | `&#34;ACTIVE&#34; | &#34;DISABLED&#34;` | `dictionary-value-catalog:field:state` | `Select/display text` |
| `addedAt` | `string` | `dictionary-value-catalog:field:addedAt` | `formatValue/display text` |
| `updatedAt` | `string` | `dictionary-value-catalog:field:updatedAt` | `formatValue/display text` |
| `disabledAt` | `string` | `dictionary-value-catalog:field:disabledAt` | `formatValue/display text` |
| `disabledReason` | `string` | `dictionary-value-catalog:field:disabledReason` | `formatValue/display text` |
| `enabledAt` | `string` | `dictionary-value-catalog:field:enabledAt` | `formatValue/display text` |

### Feature Schema Catalog

| Property | Value |
| --- | --- |
| Resource name | `feature_schema_catalog` |
| Route | `/feature-schema-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/datasetgovernance/slices/feature-schema-catalog/list.tsx` |
| Generated show page | `src/contexts/datasetgovernance/slices/feature-schema-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "feature_schema_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "feature_schema_catalog", parent: "datasetgovernance" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `feature-schema-catalog:list` | `src/contexts/datasetgovernance/slices/feature-schema-catalog/list.tsx` |
| `show` | `feature-schema-catalog:show` | `src/contexts/datasetgovernance/slices/feature-schema-catalog/show.tsx` |
| `defineFeatureSchema` | `feature-schema-catalog:defineFeatureSchema` | `src/contexts/datasetgovernance/slices/define-feature-schema/define-feature-schema.tsx` |
| `publishFeatureSchema` | `feature-schema-catalog:publishFeatureSchema` | `src/contexts/datasetgovernance/slices/publish-feature-schema/publish-feature-schema.tsx` |
| `deprecateFeatureSchema` | `feature-schema-catalog:deprecateFeatureSchema` | `src/contexts/datasetgovernance/slices/deprecate-feature-schema/deprecate-feature-schema.tsx` |
| `retireFeatureSchema` | `feature-schema-catalog:retireFeatureSchema` | `src/contexts/datasetgovernance/slices/retire-feature-schema/retire-feature-schema.tsx` |
| `supersedeFeatureSchemaVersion` | `feature-schema-catalog:supersedeFeatureSchemaVersion` | `src/contexts/datasetgovernance/slices/supersede-feature-schema-version/supersede-feature-schema-version.tsx` |
| `markCurrentRecommendedFeatureSchemaVersion` | `feature-schema-catalog:markCurrentRecommendedFeatureSchemaVersion` | `src/contexts/datasetgovernance/slices/mark-current-recommended-feature-schema-version/mark-current-recommended-feature-schema-version.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyFeatureSchemaCatalogList } from "./pages/my-feature-schema-catalog-list";

export const pageOverrides = {
  "feature-schema-catalog:list": <MyFeatureSchemaCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `defineFeatureSchema` | `feature-schema-catalog:defineFeatureSchema` | `src/contexts/datasetgovernance/slices/define-feature-schema/define-feature-schema.tsx` | `featureDomain`, `version`, `dataModality`, `features`, `labels` |
| `publishFeatureSchema` | `feature-schema-catalog:publishFeatureSchema` | `src/contexts/datasetgovernance/slices/publish-feature-schema/publish-feature-schema.tsx` | `publishNote` |
| `deprecateFeatureSchema` | `feature-schema-catalog:deprecateFeatureSchema` | `src/contexts/datasetgovernance/slices/deprecate-feature-schema/deprecate-feature-schema.tsx` | `deprecationReason` |
| `retireFeatureSchema` | `feature-schema-catalog:retireFeatureSchema` | `src/contexts/datasetgovernance/slices/retire-feature-schema/retire-feature-schema.tsx` | `retirementReason` |
| `supersedeFeatureSchemaVersion` | `feature-schema-catalog:supersedeFeatureSchemaVersion` | `src/contexts/datasetgovernance/slices/supersede-feature-schema-version/supersede-feature-schema-version.tsx` | `supersededByFeatureSchemaId`, `supersessionReason` |
| `markCurrentRecommendedFeatureSchemaVersion` | `feature-schema-catalog:markCurrentRecommendedFeatureSchemaVersion` | `src/contexts/datasetgovernance/slices/mark-current-recommended-feature-schema-version/mark-current-recommended-feature-schema-version.tsx` | `recommendationNote` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `featureSchemaId` | `string` | `feature-schema-catalog:field:featureSchemaId` | `formatValue/display text` |
| `featureDomain` | `string` | `feature-schema-catalog:field:featureDomain` | `formatValue/display text` |
| `version` | `string` | `feature-schema-catalog:field:version` | `formatValue/display text` |
| `dataModality` | `string` | `feature-schema-catalog:field:dataModality` | `formatValue/display text` |
| `features` | `FeatureDefinition[]` | `feature-schema-catalog:field:features` | `formatValue/display text` |
| `labels` | `LabelDefinition[]` | `feature-schema-catalog:field:labels` | `formatValue/display text` |
| `featureCount` | `number` | `feature-schema-catalog:field:featureCount` | `formatValue/display text` |
| `schemaStatus` | `string` | `feature-schema-catalog:field:schemaStatus` | `formatValue/display text` |
| `supersededByFeatureSchemaId` | `string` | `feature-schema-catalog:field:supersededByFeatureSchemaId` | `formatValue/display text` |
| `recommendedForDomain` | `boolean` | `feature-schema-catalog:field:recommendedForDomain` | `formatValue/display text` |

### Federation Membership Directory

| Property | Value |
| --- | --- |
| Resource name | `federation_membership_directory` |
| Route | `/federation-membership-directory` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/federationmanagement/slices/federation-membership-directory/list.tsx` |
| Generated show page | `src/contexts/federationmanagement/slices/federation-membership-directory/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "federation_membership_directory" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "federation_membership_directory", parent: "federationmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `federation-membership-directory:list` | `src/contexts/federationmanagement/slices/federation-membership-directory/list.tsx` |
| `show` | `federation-membership-directory:show` | `src/contexts/federationmanagement/slices/federation-membership-directory/show.tsx` |
| `inviteParticipant` | `federation-membership-directory:inviteParticipant` | `src/contexts/federationmanagement/slices/invite-participant/invite-participant-federation-membership-directory.tsx` |
| `removeParticipant` | `federation-membership-directory:removeParticipant` | `src/contexts/federationmanagement/slices/remove-participant/remove-participant-federation-membership-directory.tsx` |
| `approveParticipant` | `federation-membership-directory:approveParticipant` | `src/contexts/federationmanagement/slices/approve-participant/approve-participant-federation-membership-directory.tsx` |
| `rejectParticipant` | `federation-membership-directory:rejectParticipant` | `src/contexts/federationmanagement/slices/reject-participant/reject-participant-federation-membership-directory.tsx` |
| `revokeParticipantInvitation` | `federation-membership-directory:revokeParticipantInvitation` | `src/contexts/federationmanagement/slices/revoke-participant-invitation/revoke-participant-invitation-federation-membership-directory.tsx` |
| `suspendParticipant` | `federation-membership-directory:suspendParticipant` | `src/contexts/federationmanagement/slices/suspend-participant/suspend-participant-federation-membership-directory.tsx` |
| `activateFederation` | `federation-membership-directory:activateFederation` | `src/contexts/federationmanagement/slices/activate-federation/activate-federation-federation-membership-directory.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyFederationMembershipDirectoryList } from "./pages/my-federation-membership-directory-list";

export const pageOverrides = {
  "federation-membership-directory:list": <MyFederationMembershipDirectoryList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `inviteParticipant` | `federation-membership-directory:inviteParticipant` | `src/contexts/federationmanagement/slices/invite-participant/invite-participant-federation-membership-directory.tsx` | `organizationId`, `invitationNote` |
| `removeParticipant` | `federation-membership-directory:removeParticipant` | `src/contexts/federationmanagement/slices/remove-participant/remove-participant-federation-membership-directory.tsx` | `federationName`, `organizationId`, `organizationName`, `removalReason` |
| `approveParticipant` | `federation-membership-directory:approveParticipant` | `src/contexts/federationmanagement/slices/approve-participant/approve-participant-federation-membership-directory.tsx` | `federationName`, `organizationId`, `organizationName`, `approvalNote` |
| `rejectParticipant` | `federation-membership-directory:rejectParticipant` | `src/contexts/federationmanagement/slices/reject-participant/reject-participant-federation-membership-directory.tsx` | `federationName`, `organizationId`, `organizationName`, `rejectionReason` |
| `revokeParticipantInvitation` | `federation-membership-directory:revokeParticipantInvitation` | `src/contexts/federationmanagement/slices/revoke-participant-invitation/revoke-participant-invitation-federation-membership-directory.tsx` | `federationName`, `organizationId`, `organizationName`, `revokeReason` |
| `suspendParticipant` | `federation-membership-directory:suspendParticipant` | `src/contexts/federationmanagement/slices/suspend-participant/suspend-participant-federation-membership-directory.tsx` | `federationName`, `organizationId`, `organizationName`, `suspensionReason` |
| `activateFederation` | `federation-membership-directory:activateFederation` | `src/contexts/federationmanagement/slices/activate-federation/activate-federation-federation-membership-directory.tsx` | `activationNote` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `federationId` | `string` | `federation-membership-directory:field:federationId` | `formatValue/display text` |
| `organizationId` | `string` | `federation-membership-directory:field:organizationId` | `formatValue/display text` |
| `federationName` | `string` | `federation-membership-directory:field:federationName` | `formatValue/display text` |
| `organizationName` | `string` | `federation-membership-directory:field:organizationName` | `formatValue/display text` |
| `membershipStatus` | `string` | `federation-membership-directory:field:membershipStatus` | `formatValue/display text` |
| `invitationNote` | `string` | `federation-membership-directory:field:invitationNote` | `formatValue/display text` |
| `approvalNote` | `string` | `federation-membership-directory:field:approvalNote` | `formatValue/display text` |

### Federation Overview

| Property | Value |
| --- | --- |
| Resource name | `federation_overview` |
| Route | `/federation-overview` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/federationmanagement/slices/federation-overview/list.tsx` |
| Generated show page | `src/contexts/federationmanagement/slices/federation-overview/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "federation_overview" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "federation_overview", parent: "federationmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `federation-overview:list` | `src/contexts/federationmanagement/slices/federation-overview/list.tsx` |
| `show` | `federation-overview:show` | `src/contexts/federationmanagement/slices/federation-overview/show.tsx` |
| `createFederation` | `federation-overview:createFederation` | `src/contexts/federationmanagement/slices/create-federation/create-federation.tsx` |
| `removeParticipant` | `federation-overview:removeParticipant` | `src/contexts/federationmanagement/slices/remove-participant/remove-participant-federation-overview.tsx` |
| `activateFederation` | `federation-overview:activateFederation` | `src/contexts/federationmanagement/slices/activate-federation/activate-federation-federation-overview.tsx` |
| `suspendFederation` | `federation-overview:suspendFederation` | `src/contexts/federationmanagement/slices/suspend-federation/suspend-federation.tsx` |
| `reactivateFederation` | `federation-overview:reactivateFederation` | `src/contexts/federationmanagement/slices/reactivate-federation/reactivate-federation.tsx` |
| `inviteParticipant` | `federation-overview:inviteParticipant` | `src/contexts/federationmanagement/slices/invite-participant/invite-participant-federation-overview.tsx` |
| `approveParticipant` | `federation-overview:approveParticipant` | `src/contexts/federationmanagement/slices/approve-participant/approve-participant-federation-overview.tsx` |
| `rejectParticipant` | `federation-overview:rejectParticipant` | `src/contexts/federationmanagement/slices/reject-participant/reject-participant-federation-overview.tsx` |
| `revokeParticipantInvitation` | `federation-overview:revokeParticipantInvitation` | `src/contexts/federationmanagement/slices/revoke-participant-invitation/revoke-participant-invitation-federation-overview.tsx` |
| `suspendParticipant` | `federation-overview:suspendParticipant` | `src/contexts/federationmanagement/slices/suspend-participant/suspend-participant-federation-overview.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyFederationOverviewList } from "./pages/my-federation-overview-list";

export const pageOverrides = {
  "federation-overview:list": <MyFederationOverviewList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `createFederation` | `federation-overview:createFederation` | `src/contexts/federationmanagement/slices/create-federation/create-federation.tsx` | `federationName`, `description`, `minimumParticipantCount` |
| `removeParticipant` | `federation-overview:removeParticipant` | `src/contexts/federationmanagement/slices/remove-participant/remove-participant-federation-overview.tsx` | `federationName`, `organizationId`, `organizationName`, `removalReason` |
| `activateFederation` | `federation-overview:activateFederation` | `src/contexts/federationmanagement/slices/activate-federation/activate-federation-federation-overview.tsx` | `activationNote` |
| `suspendFederation` | `federation-overview:suspendFederation` | `src/contexts/federationmanagement/slices/suspend-federation/suspend-federation.tsx` | `suspensionReason` |
| `reactivateFederation` | `federation-overview:reactivateFederation` | `src/contexts/federationmanagement/slices/reactivate-federation/reactivate-federation.tsx` | `reactivationReason` |
| `inviteParticipant` | `federation-overview:inviteParticipant` | `src/contexts/federationmanagement/slices/invite-participant/invite-participant-federation-overview.tsx` | `organizationId`, `invitationNote` |
| `approveParticipant` | `federation-overview:approveParticipant` | `src/contexts/federationmanagement/slices/approve-participant/approve-participant-federation-overview.tsx` | `federationName`, `organizationId`, `organizationName`, `approvalNote` |
| `rejectParticipant` | `federation-overview:rejectParticipant` | `src/contexts/federationmanagement/slices/reject-participant/reject-participant-federation-overview.tsx` | `federationName`, `organizationId`, `organizationName`, `rejectionReason` |
| `revokeParticipantInvitation` | `federation-overview:revokeParticipantInvitation` | `src/contexts/federationmanagement/slices/revoke-participant-invitation/revoke-participant-invitation-federation-overview.tsx` | `federationName`, `organizationId`, `organizationName`, `revokeReason` |
| `suspendParticipant` | `federation-overview:suspendParticipant` | `src/contexts/federationmanagement/slices/suspend-participant/suspend-participant-federation-overview.tsx` | `federationName`, `organizationId`, `organizationName`, `suspensionReason` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `federationId` | `string` | `federation-overview:field:federationId` | `formatValue/display text` |
| `federationName` | `string` | `federation-overview:field:federationName` | `formatValue/display text` |
| `state` | `&#34;DRAFT&#34; | &#34;ACTIVE&#34; | &#34;SUSPENDED&#34;` | `federation-overview:field:state` | `Select/display text` |
| `minimumParticipantCount` | `number` | `federation-overview:field:minimumParticipantCount` | `formatValue/display text` |
| `activeMemberCount` | `number` | `federation-overview:field:activeMemberCount` | `formatValue/display text` |
| `pendingInvitationCount` | `number` | `federation-overview:field:pendingInvitationCount` | `formatValue/display text` |
| `activeRuntimeCount` | `number` | `federation-overview:field:activeRuntimeCount` | `formatValue/display text` |
| `activeTrainingJobCount` | `number` | `federation-overview:field:activeTrainingJobCount` | `formatValue/display text` |

### Model Artifact Catalog

| Property | Value |
| --- | --- |
| Resource name | `model_artifact_catalog` |
| Route | `/model-artifact-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/modelrepository/slices/model-artifact-catalog/list.tsx` |
| Generated show page | `src/contexts/modelrepository/slices/model-artifact-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "model_artifact_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "model_artifact_catalog", parent: "modelrepository" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `model-artifact-catalog:list` | `src/contexts/modelrepository/slices/model-artifact-catalog/list.tsx` |
| `show` | `model-artifact-catalog:show` | `src/contexts/modelrepository/slices/model-artifact-catalog/show.tsx` |
| `registerModelArtifact` | `model-artifact-catalog:registerModelArtifact` | `src/contexts/modelrepository/slices/register-model-artifact/register-model-artifact.tsx` |
| `downloadModelArtifact` | `model-artifact-catalog:downloadModelArtifact` | `src/contexts/modelrepository/slices/download-model-artifact/download-model-artifact.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyModelArtifactCatalogList } from "./pages/my-model-artifact-catalog-list";

export const pageOverrides = {
  "model-artifact-catalog:list": <MyModelArtifactCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `registerModelArtifact` | `model-artifact-catalog:registerModelArtifact` | `src/contexts/modelrepository/slices/register-model-artifact/register-model-artifact.tsx` | `modelName`, `modelPlugin`, `modelVersion`, `modelDescription`, `sourceType`, `fileId`, `modelFormat` |
| `downloadModelArtifact` | `model-artifact-catalog:downloadModelArtifact` | `src/contexts/modelrepository/slices/download-model-artifact/download-model-artifact.tsx` | _(none)_ |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `modelId` | `string` | `model-artifact-catalog:field:modelId` | `formatValue/display text` |
| `modelName` | `string` | `model-artifact-catalog:field:modelName` | `formatValue/display text` |
| `modelPlugin` | `string` | `model-artifact-catalog:field:modelPlugin` | `formatValue/display text` |
| `modelVersion` | `string` | `model-artifact-catalog:field:modelVersion` | `formatValue/display text` |
| `modelDescription` | `string` | `model-artifact-catalog:field:modelDescription` | `formatValue/display text` |
| `sourceType` | `string` | `model-artifact-catalog:field:sourceType` | `formatValue/display text` |
| `modelArtifactUri` | `string` | `model-artifact-catalog:field:modelArtifactUri` | `formatValue/display text` |
| `modelRegistryRef` | `string` | `model-artifact-catalog:field:modelRegistryRef` | `formatValue/display text` |
| `modelFormat` | `string` | `model-artifact-catalog:field:modelFormat` | `formatValue/display text` |
| `modelArtifactDigest` | `string` | `model-artifact-catalog:field:modelArtifactDigest` | `formatValue/display text` |
| `modelSignatureUri` | `string` | `model-artifact-catalog:field:modelSignatureUri` | `formatValue/display text` |
| `modelSizeBytes` | `number` | `model-artifact-catalog:field:modelSizeBytes` | `formatValue/display text` |
| `trainingJobId` | `string` | `model-artifact-catalog:field:trainingJobId` | `formatValue/display text` |
| `roundId` | `string` | `model-artifact-catalog:field:roundId` | `formatValue/display text` |
| `trainingJobObjective` | `string` | `model-artifact-catalog:field:trainingJobObjective` | `formatValue/display text` |
| `state` | `&#34;REGISTERED&#34;` | `model-artifact-catalog:field:state` | `Select/display text` |
| `registeredAt` | `string` | `model-artifact-catalog:field:registeredAt` | `formatValue/display text` |

### Model Catalog

| Property | Value |
| --- | --- |
| Resource name | `model_catalog` |
| Route | `/model-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/modellifecycle/slices/model-catalog/list.tsx` |
| Generated show page | `src/contexts/modellifecycle/slices/model-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "model_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "model_catalog", parent: "modellifecycle" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `model-catalog:list` | `src/contexts/modellifecycle/slices/model-catalog/list.tsx` |
| `show` | `model-catalog:show` | `src/contexts/modellifecycle/slices/model-catalog/show.tsx` |
| `recordModelEvaluationPackage` | `model-catalog:recordModelEvaluationPackage` | `src/contexts/modellifecycle/slices/record-model-evaluation-package/record-model-evaluation-package.tsx` |
| `approveModel` | `model-catalog:approveModel` | `src/contexts/modellifecycle/slices/approve-model/approve-model.tsx` |
| `promoteModelToProduction` | `model-catalog:promoteModelToProduction` | `src/contexts/modellifecycle/slices/promote-model-to-production/promote-model-to-production.tsx` |
| `rollbackModel` | `model-catalog:rollbackModel` | `src/contexts/modellifecycle/slices/rollback-model/rollback-model.tsx` |
| `retireModel` | `model-catalog:retireModel` | `src/contexts/modellifecycle/slices/retire-model/retire-model.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyModelCatalogList } from "./pages/my-model-catalog-list";

export const pageOverrides = {
  "model-catalog:list": <MyModelCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `recordModelEvaluationPackage` | `model-catalog:recordModelEvaluationPackage` | `src/contexts/modellifecycle/slices/record-model-evaluation-package/record-model-evaluation-package.tsx` | `trainingJobId`, `evaluationReportId`, `experimentId`, `hyperparameterSnapshotId`, `reproducibilityManifestId`, `modelCardId`, `baselineModelId` |
| `approveModel` | `model-catalog:approveModel` | `src/contexts/modellifecycle/slices/approve-model/approve-model.tsx` | `approvalNote` |
| `promoteModelToProduction` | `model-catalog:promoteModelToProduction` | `src/contexts/modellifecycle/slices/promote-model-to-production/promote-model-to-production.tsx` | `releaseChannel`, `productionStage` |
| `rollbackModel` | `model-catalog:rollbackModel` | `src/contexts/modellifecycle/slices/rollback-model/rollback-model.tsx` | `previousModelId`, `rollbackReason` |
| `retireModel` | `model-catalog:retireModel` | `src/contexts/modellifecycle/slices/retire-model/retire-model.tsx` | `retirementReason` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `modelId` | `string` | `model-catalog:field:modelId` | `formatValue/display text` |
| `trainingJobId` | `string` | `model-catalog:field:trainingJobId` | `formatValue/display text` |
| `finalRoundId` | `string` | `model-catalog:field:finalRoundId` | `formatValue/display text` |
| `modelArtifactId` | `string` | `model-catalog:field:modelArtifactId` | `formatValue/display text` |
| `trainingJobObjective` | `string` | `model-catalog:field:trainingJobObjective` | `formatValue/display text` |
| `modelArtifactDigest` | `string` | `model-catalog:field:modelArtifactDigest` | `formatValue/display text` |
| `evaluationReportId` | `string` | `model-catalog:field:evaluationReportId` | `formatValue/display text` |
| `finalGlobalAccuracy` | `string` | `model-catalog:field:finalGlobalAccuracy` | `formatValue/display text` |
| `state` | `&#34;CANDIDATE&#34; | &#34;EVALUATION_PACKAGED&#34; | &#34;APPROVED&#34; | &#34;PRODUCTION&#34; | &#34;ROLLED_BACK&#34; | &#34;RETIRED&#34;` | `model-catalog:field:state` | `Select/display text` |
| `releaseChannel` | `string` | `model-catalog:field:releaseChannel` | `formatValue/display text` |
| `productionStage` | `string` | `model-catalog:field:productionStage` | `formatValue/display text` |
| `previousModelId` | `string` | `model-catalog:field:previousModelId` | `formatValue/display text` |
| `experimentId` | `string` | `model-catalog:field:experimentId` | `formatValue/display text` |
| `hyperparameterSnapshotId` | `string` | `model-catalog:field:hyperparameterSnapshotId` | `formatValue/display text` |
| `reproducibilityManifestId` | `string` | `model-catalog:field:reproducibilityManifestId` | `formatValue/display text` |
| `modelCardId` | `string` | `model-catalog:field:modelCardId` | `formatValue/display text` |
| `baselineModelId` | `string` | `model-catalog:field:baselineModelId` | `formatValue/display text` |
| `hasEvaluationPackage` | `boolean` | `model-catalog:field:hasEvaluationPackage` | `formatValue/display text` |
| `approvalStatus` | `string` | `model-catalog:field:approvalStatus` | `formatValue/display text` |
| `releaseStatus` | `string` | `model-catalog:field:releaseStatus` | `formatValue/display text` |
| `isProduction` | `boolean` | `model-catalog:field:isProduction` | `formatValue/display text` |
| `canRecordEvaluationPackage` | `boolean` | `model-catalog:field:canRecordEvaluationPackage` | `formatValue/display text` |
| `canApprove` | `boolean` | `model-catalog:field:canApprove` | `formatValue/display text` |
| `canPromoteToProduction` | `boolean` | `model-catalog:field:canPromoteToProduction` | `formatValue/display text` |
| `canRollback` | `boolean` | `model-catalog:field:canRollback` | `formatValue/display text` |
| `canRetire` | `boolean` | `model-catalog:field:canRetire` | `formatValue/display text` |
| `blockedReason` | `string` | `model-catalog:field:blockedReason` | `formatValue/display text` |

### Organization Directory

| Property | Value |
| --- | --- |
| Resource name | `organization_directory` |
| Route | `/organization-directory` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/organizationmanagement/slices/organization-directory/list.tsx` |
| Generated show page | `src/contexts/organizationmanagement/slices/organization-directory/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "organization_directory" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "organization_directory", parent: "organizationmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `organization-directory:list` | `src/contexts/organizationmanagement/slices/organization-directory/list.tsx` |
| `show` | `organization-directory:show` | `src/contexts/organizationmanagement/slices/organization-directory/show.tsx` |
| `registerOrganization` | `organization-directory:registerOrganization` | `src/contexts/organizationmanagement/slices/register-organization/register-organization.tsx` |
| `activateOrganization` | `organization-directory:activateOrganization` | `src/contexts/organizationmanagement/slices/activate-organization/activate-organization.tsx` |
| `deactivateOrganization` | `organization-directory:deactivateOrganization` | `src/contexts/organizationmanagement/slices/deactivate-organization/deactivate-organization.tsx` |
| `createRuntimeInstallationPlan` | `organization-directory:createRuntimeInstallationPlan` | `src/contexts/runtimeprovisioning/slices/create-runtime-installation-plan/create-runtime-installation-plan-organization-directory.tsx` |
| `reactivateOrganization` | `organization-directory:reactivateOrganization` | `src/contexts/organizationmanagement/slices/reactivate-organization/reactivate-organization.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyOrganizationDirectoryList } from "./pages/my-organization-directory-list";

export const pageOverrides = {
  "organization-directory:list": <MyOrganizationDirectoryList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `registerOrganization` | `organization-directory:registerOrganization` | `src/contexts/organizationmanagement/slices/register-organization/register-organization.tsx` | `organizationName`, `organizationType`, `contactEmail` |
| `activateOrganization` | `organization-directory:activateOrganization` | `src/contexts/organizationmanagement/slices/activate-organization/activate-organization.tsx` | `activationNote` |
| `deactivateOrganization` | `organization-directory:deactivateOrganization` | `src/contexts/organizationmanagement/slices/deactivate-organization/deactivate-organization.tsx` | `deactivationReason` |
| `createRuntimeInstallationPlan` | `organization-directory:createRuntimeInstallationPlan` | `src/contexts/runtimeprovisioning/slices/create-runtime-installation-plan/create-runtime-installation-plan-organization-directory.tsx` | `organizationId`, `runtimeInfrastructurePackageId`, `runtimeName`, `agentInstallMode`, `expectedNodeCount` |
| `reactivateOrganization` | `organization-directory:reactivateOrganization` | `src/contexts/organizationmanagement/slices/reactivate-organization/reactivate-organization.tsx` | `reactivationReason` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `organizationId` | `string` | `organization-directory:field:organizationId` | `formatValue/display text` |
| `organizationName` | `string` | `organization-directory:field:organizationName` | `formatValue/display text` |
| `organizationType` | `OrganizationType` | `organization-directory:field:organizationType` | `Select/display text` |
| `state` | `&#34;REGISTERED&#34; | &#34;ACTIVE&#34; | &#34;DEACTIVATED&#34;` | `organization-directory:field:state` | `Select/display text` |
| `approvedDatasetCount` | `number` | `organization-directory:field:approvedDatasetCount` | `formatValue/display text` |

### Permission Catalog

| Property | Value |
| --- | --- |
| Resource name | `permission_catalog` |
| Route | `/permission-catalog` |
| Backend module | `federation-learning-support` |
| Data provider | `federation-learning-support` |
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
| Backend module | `federation-learning-support` |
| Data provider | `federation-learning-support` |
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
| Backend module | `federation-learning-support` |
| Data provider | `federation-learning-support` |
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

### Runtime Agent Endpoint Catalog

| Property | Value |
| --- | --- |
| Resource name | `runtime_agent_endpoint_catalog` |
| Route | `/runtime-agent-endpoint-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimeprovisioning/slices/runtime-agent-endpoint-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeprovisioning/slices/runtime-agent-endpoint-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_agent_endpoint_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_agent_endpoint_catalog", parent: "runtimeprovisioning" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-agent-endpoint-catalog:list` | `src/contexts/runtimeprovisioning/slices/runtime-agent-endpoint-catalog/list.tsx` |
| `show` | `runtime-agent-endpoint-catalog:show` | `src/contexts/runtimeprovisioning/slices/runtime-agent-endpoint-catalog/show.tsx` |
| `recordRuntimeConnectionEstablished` | `runtime-agent-endpoint-catalog:recordRuntimeConnectionEstablished` | `src/contexts/runtimeprovisioning/slices/record-runtime-connection-established/record-runtime-connection-established.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeAgentEndpointCatalogList } from "./pages/my-runtime-agent-endpoint-catalog-list";

export const pageOverrides = {
  "runtime-agent-endpoint-catalog:list": <MyRuntimeAgentEndpointCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `recordRuntimeConnectionEstablished` | `runtime-agent-endpoint-catalog:recordRuntimeConnectionEstablished` | `src/contexts/runtimeprovisioning/slices/record-runtime-connection-established/record-runtime-connection-established.tsx` | `runtimeAgentId`, `runtimeAgentEndpoint`, `endpointScope` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeAgentId` | `string` | `runtime-agent-endpoint-catalog:field:runtimeAgentId` | `formatValue/display text` |
| `runtimeId` | `string` | `runtime-agent-endpoint-catalog:field:runtimeId` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `runtime-agent-endpoint-catalog:field:runtimeInfrastructureId` | `formatValue/display text` |
| `organizationId` | `string` | `runtime-agent-endpoint-catalog:field:organizationId` | `formatValue/display text` |
| `runtimeName` | `string` | `runtime-agent-endpoint-catalog:field:runtimeName` | `formatValue/display text` |
| `runtimeAgentEndpoint` | `string` | `runtime-agent-endpoint-catalog:field:runtimeAgentEndpoint` | `formatValue/display text` |
| `endpointScope` | `string` | `runtime-agent-endpoint-catalog:field:endpointScope` | `formatValue/display text` |
| `connectionStatus` | `string` | `runtime-agent-endpoint-catalog:field:connectionStatus` | `formatValue/display text` |
| `connectedAt` | `string` | `runtime-agent-endpoint-catalog:field:connectedAt` | `formatValue/display text` |
| `activatedAt` | `string` | `runtime-agent-endpoint-catalog:field:activatedAt` | `formatValue/display text` |

### Runtime Capability Catalog

| Property | Value |
| --- | --- |
| Resource name | `runtime_capability_catalog` |
| Route | `/runtime-capability-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimegovernance/slices/runtime-capability-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimegovernance/slices/runtime-capability-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_capability_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_capability_catalog", parent: "runtimegovernance" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-capability-catalog:list` | `src/contexts/runtimegovernance/slices/runtime-capability-catalog/list.tsx` |
| `show` | `runtime-capability-catalog:show` | `src/contexts/runtimegovernance/slices/runtime-capability-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeCapabilityCatalogList } from "./pages/my-runtime-capability-catalog-list";

export const pageOverrides = {
  "runtime-capability-catalog:list": <MyRuntimeCapabilityCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeId` | `string` | `runtime-capability-catalog:field:runtimeId` | `formatValue/display text` |
| `capabilityTypes` | `string[]` | `runtime-capability-catalog:field:capabilityTypes` | `formatValue/display text` |
| `capabilityStatus` | `string` | `runtime-capability-catalog:field:capabilityStatus` | `formatValue/display text` |
| `detectedAt` | `string` | `runtime-capability-catalog:field:detectedAt` | `formatValue/display text` |

### Runtime Dataset Metadata Catalog

| Property | Value |
| --- | --- |
| Resource name | `runtime_dataset_metadata_catalog` |
| Route | `/runtime-dataset-metadata-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/datasetgovernance/slices/runtime-dataset-metadata-catalog/list.tsx` |
| Generated show page | `src/contexts/datasetgovernance/slices/runtime-dataset-metadata-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_dataset_metadata_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_dataset_metadata_catalog", parent: "datasetgovernance" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-dataset-metadata-catalog:list` | `src/contexts/datasetgovernance/slices/runtime-dataset-metadata-catalog/list.tsx` |
| `show` | `runtime-dataset-metadata-catalog:show` | `src/contexts/datasetgovernance/slices/runtime-dataset-metadata-catalog/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeDatasetMetadataCatalogList } from "./pages/my-runtime-dataset-metadata-catalog-list";

export const pageOverrides = {
  "runtime-dataset-metadata-catalog:list": <MyRuntimeDatasetMetadataCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeDatasetBindingId` | `string` | `runtime-dataset-metadata-catalog:field:runtimeDatasetBindingId` | `formatValue/display text` |
| `metadataReportId` | `string` | `runtime-dataset-metadata-catalog:field:metadataReportId` | `formatValue/display text` |
| `datasetId` | `string` | `runtime-dataset-metadata-catalog:field:datasetId` | `formatValue/display text` |
| `organizationId` | `string` | `runtime-dataset-metadata-catalog:field:organizationId` | `formatValue/display text` |
| `organizationName` | `string` | `runtime-dataset-metadata-catalog:field:organizationName` | `formatValue/display text` |
| `runtimeId` | `string` | `runtime-dataset-metadata-catalog:field:runtimeId` | `formatValue/display text` |
| `runtimeName` | `string` | `runtime-dataset-metadata-catalog:field:runtimeName` | `formatValue/display text` |
| `featureSchemaId` | `string` | `runtime-dataset-metadata-catalog:field:featureSchemaId` | `formatValue/display text` |
| `featureDomain` | `string` | `runtime-dataset-metadata-catalog:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `runtime-dataset-metadata-catalog:field:featureSchemaVersion` | `formatValue/display text` |
| `datasetName` | `string` | `runtime-dataset-metadata-catalog:field:datasetName` | `formatValue/display text` |
| `sampleCount` | `number` | `runtime-dataset-metadata-catalog:field:sampleCount` | `formatValue/display text` |
| `featureCount` | `number` | `runtime-dataset-metadata-catalog:field:featureCount` | `formatValue/display text` |
| `schemaCompatible` | `boolean` | `runtime-dataset-metadata-catalog:field:schemaCompatible` | `formatValue/display text` |
| `labelCompatible` | `boolean` | `runtime-dataset-metadata-catalog:field:labelCompatible` | `formatValue/display text` |
| `missingValueRate` | `string` | `runtime-dataset-metadata-catalog:field:missingValueRate` | `formatValue/display text` |
| `duplicateRate` | `string` | `runtime-dataset-metadata-catalog:field:duplicateRate` | `formatValue/display text` |
| `qualityScore` | `string` | `runtime-dataset-metadata-catalog:field:qualityScore` | `formatValue/display text` |
| `nonIidScore` | `string` | `runtime-dataset-metadata-catalog:field:nonIidScore` | `formatValue/display text` |
| `classBalanceScore` | `string` | `runtime-dataset-metadata-catalog:field:classBalanceScore` | `formatValue/display text` |
| `profilingStatus` | `string` | `runtime-dataset-metadata-catalog:field:profilingStatus` | `formatValue/display text` |
| `failureReason` | `string` | `runtime-dataset-metadata-catalog:field:failureReason` | `formatValue/display text` |
| `profiledAt` | `string` | `runtime-dataset-metadata-catalog:field:profiledAt` | `formatValue/display text` |

### Runtime Engine Profile Catalog

| Property | Value |
| --- | --- |
| Resource name | `runtime_engine_profile_catalog` |
| Route | `/runtime-engine-profile-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/trainingorchestration/slices/runtime-engine-profile-catalog/list.tsx` |
| Generated show page | `src/contexts/trainingorchestration/slices/runtime-engine-profile-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_engine_profile_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_engine_profile_catalog", parent: "trainingorchestration" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-engine-profile-catalog:list` | `src/contexts/trainingorchestration/slices/runtime-engine-profile-catalog/list.tsx` |
| `show` | `runtime-engine-profile-catalog:show` | `src/contexts/trainingorchestration/slices/runtime-engine-profile-catalog/show.tsx` |
| `registerRuntimeEngineProfile` | `runtime-engine-profile-catalog:registerRuntimeEngineProfile` | `src/contexts/trainingorchestration/slices/register-runtime-engine-profile/register-runtime-engine-profile.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeEngineProfileCatalogList } from "./pages/my-runtime-engine-profile-catalog-list";

export const pageOverrides = {
  "runtime-engine-profile-catalog:list": <MyRuntimeEngineProfileCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `registerRuntimeEngineProfile` | `runtime-engine-profile-catalog:registerRuntimeEngineProfile` | `src/contexts/trainingorchestration/slices/register-runtime-engine-profile/register-runtime-engine-profile.tsx` | `profileName`, `pluginProfile`, `runtimeEngineImage`, `imageDigest`, `supportedModelPluginsDescription`, `supportedAggregationAlgorithmsDescription`, `active` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeEngineProfileId` | `string` | `runtime-engine-profile-catalog:field:runtimeEngineProfileId` | `formatValue/display text` |
| `profileName` | `string` | `runtime-engine-profile-catalog:field:profileName` | `formatValue/display text` |
| `pluginProfile` | `string` | `runtime-engine-profile-catalog:field:pluginProfile` | `formatValue/display text` |
| `runtimeEngineImage` | `string` | `runtime-engine-profile-catalog:field:runtimeEngineImage` | `formatValue/display text` |
| `imageDigest` | `string` | `runtime-engine-profile-catalog:field:imageDigest` | `formatValue/display text` |
| `supportedModelPluginsDescription` | `string` | `runtime-engine-profile-catalog:field:supportedModelPluginsDescription` | `CopyableText` |
| `supportedAggregationAlgorithmsDescription` | `string` | `runtime-engine-profile-catalog:field:supportedAggregationAlgorithmsDescription` | `CopyableText` |
| `active` | `boolean` | `runtime-engine-profile-catalog:field:active` | `formatValue/display text` |
| `state` | `&#34;REGISTERED&#34;` | `runtime-engine-profile-catalog:field:state` | `Select/display text` |
| `registeredAt` | `string` | `runtime-engine-profile-catalog:field:registeredAt` | `formatValue/display text` |

### Runtime Health Dashboard

| Property | Value |
| --- | --- |
| Resource name | `runtime_health_dashboard` |
| Route | `/runtime-health-dashboard` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimemonitoring/slices/runtime-health-dashboard/list.tsx` |
| Generated show page | `src/contexts/runtimemonitoring/slices/runtime-health-dashboard/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_health_dashboard" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_health_dashboard", parent: "runtimemonitoring" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-health-dashboard:list` | `src/contexts/runtimemonitoring/slices/runtime-health-dashboard/list.tsx` |
| `show` | `runtime-health-dashboard:show` | `src/contexts/runtimemonitoring/slices/runtime-health-dashboard/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeHealthDashboardList } from "./pages/my-runtime-health-dashboard-list";

export const pageOverrides = {
  "runtime-health-dashboard:list": <MyRuntimeHealthDashboardList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `nodeId` | `string` | `runtime-health-dashboard:field:nodeId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `runtime-health-dashboard:field:runtimeAgentId` | `formatValue/display text` |
| `federationId` | `string` | `runtime-health-dashboard:field:federationId` | `formatValue/display text` |
| `trainingJobId` | `string` | `runtime-health-dashboard:field:trainingJobId` | `formatValue/display text` |
| `roundExecutionId` | `string` | `runtime-health-dashboard:field:roundExecutionId` | `formatValue/display text` |
| `federationName` | `string` | `runtime-health-dashboard:field:federationName` | `formatValue/display text` |
| `trainingJobObjective` | `string` | `runtime-health-dashboard:field:trainingJobObjective` | `formatValue/display text` |
| `cpuLoad` | `string` | `runtime-health-dashboard:field:cpuLoad` | `formatValue/display text` |
| `gpuLoad` | `string` | `runtime-health-dashboard:field:gpuLoad` | `formatValue/display text` |
| `memoryLoad` | `string` | `runtime-health-dashboard:field:memoryLoad` | `formatValue/display text` |
| `nodeReady` | `boolean` | `runtime-health-dashboard:field:nodeReady` | `formatValue/display text` |
| `availableCpuCores` | `number` | `runtime-health-dashboard:field:availableCpuCores` | `formatValue/display text` |
| `availableMemoryGb` | `number` | `runtime-health-dashboard:field:availableMemoryGb` | `formatValue/display text` |
| `availableGpuCount` | `number` | `runtime-health-dashboard:field:availableGpuCount` | `formatValue/display text` |
| `runningWorkloadCount` | `number` | `runtime-health-dashboard:field:runningWorkloadCount` | `formatValue/display text` |
| `workloadCapacity` | `number` | `runtime-health-dashboard:field:workloadCapacity` | `formatValue/display text` |
| `healthStatus` | `string` | `runtime-health-dashboard:field:healthStatus` | `formatValue/display text` |
| `lastHeartbeatAt` | `string` | `runtime-health-dashboard:field:lastHeartbeatAt` | `formatValue/display text` |
| `lastResourceSnapshotAt` | `string` | `runtime-health-dashboard:field:lastResourceSnapshotAt` | `formatValue/display text` |

### Runtime Identity Catalog

| Property | Value |
| --- | --- |
| Resource name | `runtime_identity_catalog` |
| Route | `/runtime-identity-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimegovernance/slices/runtime-identity-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimegovernance/slices/runtime-identity-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_identity_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_identity_catalog", parent: "runtimegovernance" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-identity-catalog:list` | `src/contexts/runtimegovernance/slices/runtime-identity-catalog/list.tsx` |
| `show` | `runtime-identity-catalog:show` | `src/contexts/runtimegovernance/slices/runtime-identity-catalog/show.tsx` |
| `revokeRuntimeIdentity` | `runtime-identity-catalog:revokeRuntimeIdentity` | `src/contexts/runtimegovernance/slices/revoke-runtime-identity/revoke-runtime-identity.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeIdentityCatalogList } from "./pages/my-runtime-identity-catalog-list";

export const pageOverrides = {
  "runtime-identity-catalog:list": <MyRuntimeIdentityCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `revokeRuntimeIdentity` | `runtime-identity-catalog:revokeRuntimeIdentity` | `src/contexts/runtimegovernance/slices/revoke-runtime-identity/revoke-runtime-identity.tsx` | `revocationReason` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeId` | `string` | `runtime-identity-catalog:field:runtimeId` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `runtime-identity-catalog:field:runtimeInfrastructureId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `runtime-identity-catalog:field:runtimeAgentId` | `formatValue/display text` |
| `organizationId` | `string` | `runtime-identity-catalog:field:organizationId` | `formatValue/display text` |
| `organizationName` | `string` | `runtime-identity-catalog:field:organizationName` | `formatValue/display text` |
| `runtimeName` | `string` | `runtime-identity-catalog:field:runtimeName` | `formatValue/display text` |
| `identityStatus` | `string` | `runtime-identity-catalog:field:identityStatus` | `formatValue/display text` |
| `activatedAt` | `string` | `runtime-identity-catalog:field:activatedAt` | `formatValue/display text` |
| `revokedAt` | `string` | `runtime-identity-catalog:field:revokedAt` | `formatValue/display text` |

### Runtime Infrastructure Access View

| Property | Value |
| --- | --- |
| Resource name | `runtime_infrastructure_access_view` |
| Route | `/runtime-infrastructure-access-view` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimeprovisioning/slices/runtime-infrastructure-access-view/list.tsx` |
| Generated show page | `src/contexts/runtimeprovisioning/slices/runtime-infrastructure-access-view/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_infrastructure_access_view" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_infrastructure_access_view", parent: "runtimeprovisioning" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-infrastructure-access-view:list` | `src/contexts/runtimeprovisioning/slices/runtime-infrastructure-access-view/list.tsx` |
| `show` | `runtime-infrastructure-access-view:show` | `src/contexts/runtimeprovisioning/slices/runtime-infrastructure-access-view/show.tsx` |
| `registerRuntimeInfrastructure` | `runtime-infrastructure-access-view:registerRuntimeInfrastructure` | `src/contexts/runtimeprovisioning/slices/register-runtime-infrastructure/register-runtime-infrastructure-runtime-infrastructure-access-view.tsx` |
| `confirmRuntimeInfrastructurePrepared` | `runtime-infrastructure-access-view:confirmRuntimeInfrastructurePrepared` | `src/contexts/runtimeprovisioning/slices/confirm-runtime-infrastructure-prepared/confirm-runtime-infrastructure-prepared-runtime-infrastructure-access-view.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeInfrastructureAccessViewList } from "./pages/my-runtime-infrastructure-access-view-list";

export const pageOverrides = {
  "runtime-infrastructure-access-view:list": <MyRuntimeInfrastructureAccessViewList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `registerRuntimeInfrastructure` | `runtime-infrastructure-access-view:registerRuntimeInfrastructure` | `src/contexts/runtimeprovisioning/slices/register-runtime-infrastructure/register-runtime-infrastructure-runtime-infrastructure-access-view.tsx` | `organizationId`, `organizationName`, `runtimeInfrastructurePackageId`, `runtimeInfrastructurePackageName`, `runtimeInfrastructurePackageVersion`, `runtimeEnvironmentType`, `runtimeName`, `agentInstallMode`, `expectedNodeCount` |
| `confirmRuntimeInfrastructurePrepared` | `runtime-infrastructure-access-view:confirmRuntimeInfrastructurePrepared` | `src/contexts/runtimeprovisioning/slices/confirm-runtime-infrastructure-prepared/confirm-runtime-infrastructure-prepared-runtime-infrastructure-access-view.tsx` | `organizationId`, `organizationName`, `runtimeInfrastructurePackageId`, `runtimeInfrastructurePackageName`, `runtimeInfrastructurePackageVersion`, `runtimeEnvironmentType`, `runtimeName`, `agentInstallMode`, `expectedNodeCount`, `preparedNodeCount`, `preparationNotes` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeInfrastructureId` | `string` | `runtime-infrastructure-access-view:field:runtimeInfrastructureId` | `formatValue/display text` |
| `organizationId` | `string` | `runtime-infrastructure-access-view:field:organizationId` | `formatValue/display text` |
| `runtimeInstallationPlanId` | `string` | `runtime-infrastructure-access-view:field:runtimeInstallationPlanId` | `formatValue/display text` |
| `runtimeInfrastructurePackageId` | `string` | `runtime-infrastructure-access-view:field:runtimeInfrastructurePackageId` | `formatValue/display text` |
| `runtimeInfrastructurePackageName` | `string` | `runtime-infrastructure-access-view:field:runtimeInfrastructurePackageName` | `formatValue/display text` |
| `runtimeInfrastructurePackageVersion` | `string` | `runtime-infrastructure-access-view:field:runtimeInfrastructurePackageVersion` | `formatValue/display text` |
| `organizationName` | `string` | `runtime-infrastructure-access-view:field:organizationName` | `formatValue/display text` |
| `runtimeName` | `string` | `runtime-infrastructure-access-view:field:runtimeName` | `formatValue/display text` |
| `runtimeEnvironmentType` | `string` | `runtime-infrastructure-access-view:field:runtimeEnvironmentType` | `formatValue/display text` |
| `agentInstallMode` | `string` | `runtime-infrastructure-access-view:field:agentInstallMode` | `formatValue/display text` |
| `expectedNodeCount` | `number` | `runtime-infrastructure-access-view:field:expectedNodeCount` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `runtime-infrastructure-access-view:field:runtimeAgentId` | `formatValue/display text` |
| `runtimeAgentVersion` | `string` | `runtime-infrastructure-access-view:field:runtimeAgentVersion` | `formatValue/display text` |
| `infrastructurePreparedAt` | `string` | `runtime-infrastructure-access-view:field:infrastructurePreparedAt` | `formatValue/display text` |
| `preparedNodeCount` | `number` | `runtime-infrastructure-access-view:field:preparedNodeCount` | `formatValue/display text` |
| `infrastructureVerifiedAt` | `string` | `runtime-infrastructure-access-view:field:infrastructureVerifiedAt` | `formatValue/display text` |
| `infrastructureVerificationFailedAt` | `string` | `runtime-infrastructure-access-view:field:infrastructureVerificationFailedAt` | `formatValue/display text` |
| `infrastructureVerificationFailureReason` | `string` | `runtime-infrastructure-access-view:field:infrastructureVerificationFailureReason` | `CopyableText` |
| `agentReadyAt` | `string` | `runtime-infrastructure-access-view:field:agentReadyAt` | `formatValue/display text` |
| `agentDeploymentFailedAt` | `string` | `runtime-infrastructure-access-view:field:agentDeploymentFailedAt` | `formatValue/display text` |
| `agentDeploymentFailureReason` | `string` | `runtime-infrastructure-access-view:field:agentDeploymentFailureReason` | `CopyableText` |
| `agentDeploymentRetryFailedAt` | `string` | `runtime-infrastructure-access-view:field:agentDeploymentRetryFailedAt` | `formatValue/display text` |
| `agentDeploymentRetryFailureReason` | `string` | `runtime-infrastructure-access-view:field:agentDeploymentRetryFailureReason` | `CopyableText` |
| `connectedAt` | `string` | `runtime-infrastructure-access-view:field:connectedAt` | `formatValue/display text` |
| `state` | `&#34;PLANNED&#34; | &#34;REGISTERED&#34; | &#34;PREPARED&#34; | &#34;VERIFIED&#34; | &#34;VERIFICATION_FAILED&#34; | &#34;AGENT_READY&#34; | &#34;RUNTIME_AGENT_FAILED&#34; | &#34;OFFLINE&#34; | &#34;CONNECTED&#34;` | `runtime-infrastructure-access-view:field:state` | `Select/display text` |

### Runtime Infrastructure Package Catalog

| Property | Value |
| --- | --- |
| Resource name | `runtime_infrastructure_package_catalog` |
| Route | `/runtime-infrastructure-package-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimeprovisioning/slices/runtime-infrastructure-package-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeprovisioning/slices/runtime-infrastructure-package-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_infrastructure_package_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_infrastructure_package_catalog", parent: "runtimeprovisioning" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-infrastructure-package-catalog:list` | `src/contexts/runtimeprovisioning/slices/runtime-infrastructure-package-catalog/list.tsx` |
| `show` | `runtime-infrastructure-package-catalog:show` | `src/contexts/runtimeprovisioning/slices/runtime-infrastructure-package-catalog/show.tsx` |
| `registerRuntimeInfrastructurePackage` | `runtime-infrastructure-package-catalog:registerRuntimeInfrastructurePackage` | `src/contexts/runtimeprovisioning/slices/register-runtime-infrastructure-package/register-runtime-infrastructure-package.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeInfrastructurePackageCatalogList } from "./pages/my-runtime-infrastructure-package-catalog-list";

export const pageOverrides = {
  "runtime-infrastructure-package-catalog:list": <MyRuntimeInfrastructurePackageCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `registerRuntimeInfrastructurePackage` | `runtime-infrastructure-package-catalog:registerRuntimeInfrastructurePackage` | `src/contexts/runtimeprovisioning/slices/register-runtime-infrastructure-package/register-runtime-infrastructure-package.tsx` | `packageName`, `packageVersion`, `runtimeEnvironmentType` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeInfrastructurePackageId` | `string` | `runtime-infrastructure-package-catalog:field:runtimeInfrastructurePackageId` | `formatValue/display text` |
| `packageName` | `string` | `runtime-infrastructure-package-catalog:field:packageName` | `formatValue/display text` |
| `packageVersion` | `string` | `runtime-infrastructure-package-catalog:field:packageVersion` | `formatValue/display text` |
| `runtimeEnvironmentType` | `string` | `runtime-infrastructure-package-catalog:field:runtimeEnvironmentType` | `formatValue/display text` |
| `state` | `&#34;REGISTERED&#34;` | `runtime-infrastructure-package-catalog:field:state` | `Select/display text` |

### Runtime Installation Guide

| Property | Value |
| --- | --- |
| Resource name | `runtime_installation_guide` |
| Route | `/runtime-installation-guide` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimeprovisioning/slices/runtime-installation-guide/list.tsx` |
| Generated show page | `src/contexts/runtimeprovisioning/slices/runtime-installation-guide/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_installation_guide" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_installation_guide", parent: "runtimeprovisioning" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-installation-guide:list` | `src/contexts/runtimeprovisioning/slices/runtime-installation-guide/list.tsx` |
| `show` | `runtime-installation-guide:show` | `src/contexts/runtimeprovisioning/slices/runtime-installation-guide/show.tsx` |
| `confirmRuntimeInfrastructurePrepared` | `runtime-installation-guide:confirmRuntimeInfrastructurePrepared` | `src/contexts/runtimeprovisioning/slices/confirm-runtime-infrastructure-prepared/confirm-runtime-infrastructure-prepared-runtime-installation-guide.tsx` |
| `registerRuntimeInfrastructure` | `runtime-installation-guide:registerRuntimeInfrastructure` | `src/contexts/runtimeprovisioning/slices/register-runtime-infrastructure/register-runtime-infrastructure-runtime-installation-guide.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeInstallationGuideList } from "./pages/my-runtime-installation-guide-list";

export const pageOverrides = {
  "runtime-installation-guide:list": <MyRuntimeInstallationGuideList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `confirmRuntimeInfrastructurePrepared` | `runtime-installation-guide:confirmRuntimeInfrastructurePrepared` | `src/contexts/runtimeprovisioning/slices/confirm-runtime-infrastructure-prepared/confirm-runtime-infrastructure-prepared-runtime-installation-guide.tsx` | `organizationId`, `organizationName`, `runtimeInfrastructurePackageId`, `runtimeInfrastructurePackageName`, `runtimeInfrastructurePackageVersion`, `runtimeEnvironmentType`, `runtimeName`, `agentInstallMode`, `expectedNodeCount`, `preparedNodeCount`, `preparationNotes` |
| `registerRuntimeInfrastructure` | `runtime-installation-guide:registerRuntimeInfrastructure` | `src/contexts/runtimeprovisioning/slices/register-runtime-infrastructure/register-runtime-infrastructure-runtime-installation-guide.tsx` | `organizationId`, `organizationName`, `runtimeInfrastructurePackageId`, `runtimeInfrastructurePackageName`, `runtimeInfrastructurePackageVersion`, `runtimeEnvironmentType`, `runtimeName`, `agentInstallMode`, `expectedNodeCount` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeInstallationPlanId` | `string` | `runtime-installation-guide:field:runtimeInstallationPlanId` | `formatValue/display text` |
| `organizationId` | `string` | `runtime-installation-guide:field:organizationId` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `runtime-installation-guide:field:runtimeInfrastructureId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `runtime-installation-guide:field:runtimeAgentId` | `formatValue/display text` |
| `runtimeInfrastructureState` | `&#34;PLANNED&#34; | &#34;REGISTERED&#34; | &#34;PREPARED&#34; | &#34;VERIFIED&#34; | &#34;VERIFICATION_FAILED&#34; | &#34;AGENT_READY&#34; | &#34;RUNTIME_AGENT_FAILED&#34; | &#34;OFFLINE&#34; | &#34;CONNECTED&#34;` | `runtime-installation-guide:field:runtimeInfrastructureState` | `Select/display text` |
| `runtimeInfrastructurePackageId` | `string` | `runtime-installation-guide:field:runtimeInfrastructurePackageId` | `formatValue/display text` |
| `runtimeInfrastructurePackageName` | `string` | `runtime-installation-guide:field:runtimeInfrastructurePackageName` | `formatValue/display text` |
| `runtimeInfrastructurePackageVersion` | `string` | `runtime-installation-guide:field:runtimeInfrastructurePackageVersion` | `formatValue/display text` |
| `organizationName` | `string` | `runtime-installation-guide:field:organizationName` | `formatValue/display text` |
| `runtimeName` | `string` | `runtime-installation-guide:field:runtimeName` | `formatValue/display text` |
| `bootstrapCommand` | `string` | `runtime-installation-guide:field:bootstrapCommand` | `CopyableText` |
| `nodeLabelCommand` | `string` | `runtime-installation-guide:field:nodeLabelCommand` | `CopyableText` |
| `nodeTaintCommand` | `string` | `runtime-installation-guide:field:nodeTaintCommand` | `CopyableText` |
| `runtimeAgentNodeSelectorYaml` | `string` | `runtime-installation-guide:field:runtimeAgentNodeSelectorYaml` | `CopyableText` |
| `runtimeAgentTolerationsYaml` | `string` | `runtime-installation-guide:field:runtimeAgentTolerationsYaml` | `CopyableText` |
| `bootstrapConfigYaml` | `string` | `runtime-installation-guide:field:bootstrapConfigYaml` | `CopyableText` |
| `runtimeEnvironmentType` | `string` | `runtime-installation-guide:field:runtimeEnvironmentType` | `formatValue/display text` |
| `agentInstallMode` | `string` | `runtime-installation-guide:field:agentInstallMode` | `formatValue/display text` |
| `expectedNodeCount` | `number` | `runtime-installation-guide:field:expectedNodeCount` | `formatValue/display text` |

### Runtime Installation Plan Catalog

| Property | Value |
| --- | --- |
| Resource name | `runtime_installation_plan_catalog` |
| Route | `/runtime-installation-plan-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimeprovisioning/slices/runtime-installation-plan-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimeprovisioning/slices/runtime-installation-plan-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_installation_plan_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_installation_plan_catalog", parent: "runtimeprovisioning" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-installation-plan-catalog:list` | `src/contexts/runtimeprovisioning/slices/runtime-installation-plan-catalog/list.tsx` |
| `show` | `runtime-installation-plan-catalog:show` | `src/contexts/runtimeprovisioning/slices/runtime-installation-plan-catalog/show.tsx` |
| `createRuntimeInstallationPlan` | `runtime-installation-plan-catalog:createRuntimeInstallationPlan` | `src/contexts/runtimeprovisioning/slices/create-runtime-installation-plan/create-runtime-installation-plan-runtime-installation-plan-catalog.tsx` |
| `retryRuntimeInfrastructureVerification` | `runtime-installation-plan-catalog:retryRuntimeInfrastructureVerification` | `src/contexts/runtimeprovisioning/slices/retry-runtime-infrastructure-verification/retry-runtime-infrastructure-verification.tsx` |
| `retryRuntimeAgentDeployment` | `runtime-installation-plan-catalog:retryRuntimeAgentDeployment` | `src/contexts/runtimeprovisioning/slices/retry-runtime-agent-deployment/retry-runtime-agent-deployment.tsx` |
| `registerRuntimeInfrastructure` | `runtime-installation-plan-catalog:registerRuntimeInfrastructure` | `src/contexts/runtimeprovisioning/slices/register-runtime-infrastructure/register-runtime-infrastructure-runtime-installation-plan-catalog.tsx` |
| `confirmRuntimeInfrastructurePrepared` | `runtime-installation-plan-catalog:confirmRuntimeInfrastructurePrepared` | `src/contexts/runtimeprovisioning/slices/confirm-runtime-infrastructure-prepared/confirm-runtime-infrastructure-prepared-runtime-installation-plan-catalog.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeInstallationPlanCatalogList } from "./pages/my-runtime-installation-plan-catalog-list";

export const pageOverrides = {
  "runtime-installation-plan-catalog:list": <MyRuntimeInstallationPlanCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `createRuntimeInstallationPlan` | `runtime-installation-plan-catalog:createRuntimeInstallationPlan` | `src/contexts/runtimeprovisioning/slices/create-runtime-installation-plan/create-runtime-installation-plan-runtime-installation-plan-catalog.tsx` | `organizationId`, `runtimeInfrastructurePackageId`, `runtimeName`, `agentInstallMode`, `expectedNodeCount` |
| `retryRuntimeInfrastructureVerification` | `runtime-installation-plan-catalog:retryRuntimeInfrastructureVerification` | `src/contexts/runtimeprovisioning/slices/retry-runtime-infrastructure-verification/retry-runtime-infrastructure-verification.tsx` | `retryReason` |
| `retryRuntimeAgentDeployment` | `runtime-installation-plan-catalog:retryRuntimeAgentDeployment` | `src/contexts/runtimeprovisioning/slices/retry-runtime-agent-deployment/retry-runtime-agent-deployment.tsx` | `retryReason` |
| `registerRuntimeInfrastructure` | `runtime-installation-plan-catalog:registerRuntimeInfrastructure` | `src/contexts/runtimeprovisioning/slices/register-runtime-infrastructure/register-runtime-infrastructure-runtime-installation-plan-catalog.tsx` | `organizationId`, `organizationName`, `runtimeInfrastructurePackageId`, `runtimeInfrastructurePackageName`, `runtimeInfrastructurePackageVersion`, `runtimeEnvironmentType`, `runtimeName`, `agentInstallMode`, `expectedNodeCount` |
| `confirmRuntimeInfrastructurePrepared` | `runtime-installation-plan-catalog:confirmRuntimeInfrastructurePrepared` | `src/contexts/runtimeprovisioning/slices/confirm-runtime-infrastructure-prepared/confirm-runtime-infrastructure-prepared-runtime-installation-plan-catalog.tsx` | `organizationId`, `organizationName`, `runtimeInfrastructurePackageId`, `runtimeInfrastructurePackageName`, `runtimeInfrastructurePackageVersion`, `runtimeEnvironmentType`, `runtimeName`, `agentInstallMode`, `expectedNodeCount`, `preparedNodeCount`, `preparationNotes` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `runtimeInstallationPlanId` | `string` | `runtime-installation-plan-catalog:field:runtimeInstallationPlanId` | `formatValue/display text` |
| `organizationId` | `string` | `runtime-installation-plan-catalog:field:organizationId` | `formatValue/display text` |
| `organizationName` | `string` | `runtime-installation-plan-catalog:field:organizationName` | `formatValue/display text` |
| `runtimeInfrastructurePackageId` | `string` | `runtime-installation-plan-catalog:field:runtimeInfrastructurePackageId` | `formatValue/display text` |
| `runtimeInfrastructurePackageName` | `string` | `runtime-installation-plan-catalog:field:runtimeInfrastructurePackageName` | `formatValue/display text` |
| `runtimeInfrastructurePackageVersion` | `string` | `runtime-installation-plan-catalog:field:runtimeInfrastructurePackageVersion` | `formatValue/display text` |
| `runtimeName` | `string` | `runtime-installation-plan-catalog:field:runtimeName` | `formatValue/display text` |
| `agentInstallMode` | `string` | `runtime-installation-plan-catalog:field:agentInstallMode` | `formatValue/display text` |
| `expectedNodeCount` | `number` | `runtime-installation-plan-catalog:field:expectedNodeCount` | `formatValue/display text` |
| `planStatus` | `string` | `runtime-installation-plan-catalog:field:planStatus` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `runtime-installation-plan-catalog:field:runtimeInfrastructureId` | `formatValue/display text` |
| `preparedAt` | `string` | `runtime-installation-plan-catalog:field:preparedAt` | `formatValue/display text` |
| `preparedNodeCount` | `number` | `runtime-installation-plan-catalog:field:preparedNodeCount` | `formatValue/display text` |
| `observedNodeCount` | `number` | `runtime-installation-plan-catalog:field:observedNodeCount` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `runtime-installation-plan-catalog:field:runtimeAgentId` | `formatValue/display text` |
| `runtimeAgentVersion` | `string` | `runtime-installation-plan-catalog:field:runtimeAgentVersion` | `formatValue/display text` |
| `plannedAt` | `string` | `runtime-installation-plan-catalog:field:plannedAt` | `formatValue/display text` |
| `verifiedAt` | `string` | `runtime-installation-plan-catalog:field:verifiedAt` | `formatValue/display text` |
| `verificationFailedAt` | `string` | `runtime-installation-plan-catalog:field:verificationFailedAt` | `formatValue/display text` |
| `verificationFailureReason` | `string` | `runtime-installation-plan-catalog:field:verificationFailureReason` | `CopyableText` |
| `agentReadyAt` | `string` | `runtime-installation-plan-catalog:field:agentReadyAt` | `formatValue/display text` |
| `agentDeploymentFailedAt` | `string` | `runtime-installation-plan-catalog:field:agentDeploymentFailedAt` | `formatValue/display text` |
| `agentDeploymentFailureReason` | `string` | `runtime-installation-plan-catalog:field:agentDeploymentFailureReason` | `CopyableText` |
| `agentDeploymentRetryFailedAt` | `string` | `runtime-installation-plan-catalog:field:agentDeploymentRetryFailedAt` | `formatValue/display text` |
| `agentDeploymentRetryFailureReason` | `string` | `runtime-installation-plan-catalog:field:agentDeploymentRetryFailureReason` | `CopyableText` |
| `lastConnectedAt` | `string` | `runtime-installation-plan-catalog:field:lastConnectedAt` | `formatValue/display text` |

### Runtime Node Inventory View

| Property | Value |
| --- | --- |
| Resource name | `runtime_node_inventory_view` |
| Route | `/runtime-node-inventory-view` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimemonitoring/slices/runtime-node-inventory-view/list.tsx` |
| Generated show page | `src/contexts/runtimemonitoring/slices/runtime-node-inventory-view/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_node_inventory_view" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_node_inventory_view", parent: "runtimemonitoring" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-node-inventory-view:list` | `src/contexts/runtimemonitoring/slices/runtime-node-inventory-view/list.tsx` |
| `show` | `runtime-node-inventory-view:show` | `src/contexts/runtimemonitoring/slices/runtime-node-inventory-view/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeNodeInventoryViewList } from "./pages/my-runtime-node-inventory-view-list";

export const pageOverrides = {
  "runtime-node-inventory-view:list": <MyRuntimeNodeInventoryViewList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `nodeId` | `string` | `runtime-node-inventory-view:field:nodeId` | `formatValue/display text` |
| `runtimeNodeInventoryReportId` | `string` | `runtime-node-inventory-view:field:runtimeNodeInventoryReportId` | `formatValue/display text` |
| `organizationId` | `string` | `runtime-node-inventory-view:field:organizationId` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `runtime-node-inventory-view:field:runtimeInfrastructureId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `runtime-node-inventory-view:field:runtimeAgentId` | `formatValue/display text` |
| `organizationName` | `string` | `runtime-node-inventory-view:field:organizationName` | `formatValue/display text` |
| `runtimeName` | `string` | `runtime-node-inventory-view:field:runtimeName` | `formatValue/display text` |
| `runtimeNodeName` | `string` | `runtime-node-inventory-view:field:runtimeNodeName` | `formatValue/display text` |
| `infrastructureNodeId` | `string` | `runtime-node-inventory-view:field:infrastructureNodeId` | `formatValue/display text` |
| `runtimeNodeRole` | `string` | `runtime-node-inventory-view:field:runtimeNodeRole` | `formatValue/display text` |
| `nodeReady` | `boolean` | `runtime-node-inventory-view:field:nodeReady` | `formatValue/display text` |
| `runtimeEngineVersion` | `string` | `runtime-node-inventory-view:field:runtimeEngineVersion` | `formatValue/display text` |
| `containerEngineVersion` | `string` | `runtime-node-inventory-view:field:containerEngineVersion` | `formatValue/display text` |
| `operatingSystem` | `string` | `runtime-node-inventory-view:field:operatingSystem` | `formatValue/display text` |
| `architecture` | `string` | `runtime-node-inventory-view:field:architecture` | `formatValue/display text` |
| `inventoryHash` | `string` | `runtime-node-inventory-view:field:inventoryHash` | `formatValue/display text` |
| `discoveredAt` | `string` | `runtime-node-inventory-view:field:discoveredAt` | `formatValue/display text` |
| `recordedAt` | `string` | `runtime-node-inventory-view:field:recordedAt` | `formatValue/display text` |

### Runtime Node Resource Latest

| Property | Value |
| --- | --- |
| Resource name | `runtime_node_resource_latest` |
| Route | `/runtime-node-resource-latest` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimemonitoring/slices/runtime-node-resource-latest/list.tsx` |
| Generated show page | `src/contexts/runtimemonitoring/slices/runtime-node-resource-latest/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_node_resource_latest" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_node_resource_latest", parent: "runtimemonitoring" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-node-resource-latest:list` | `src/contexts/runtimemonitoring/slices/runtime-node-resource-latest/list.tsx` |
| `show` | `runtime-node-resource-latest:show` | `src/contexts/runtimemonitoring/slices/runtime-node-resource-latest/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeNodeResourceLatestList } from "./pages/my-runtime-node-resource-latest-list";

export const pageOverrides = {
  "runtime-node-resource-latest:list": <MyRuntimeNodeResourceLatestList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `nodeId` | `string` | `runtime-node-resource-latest:field:nodeId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `runtime-node-resource-latest:field:runtimeAgentId` | `formatValue/display text` |
| `runtimeInfrastructureId` | `string` | `runtime-node-resource-latest:field:runtimeInfrastructureId` | `formatValue/display text` |
| `runtimeNodeName` | `string` | `runtime-node-resource-latest:field:runtimeNodeName` | `formatValue/display text` |
| `nodeReady` | `boolean` | `runtime-node-resource-latest:field:nodeReady` | `formatValue/display text` |
| `allocatableCpuCores` | `number` | `runtime-node-resource-latest:field:allocatableCpuCores` | `formatValue/display text` |
| `allocatableMemoryGb` | `number` | `runtime-node-resource-latest:field:allocatableMemoryGb` | `formatValue/display text` |
| `allocatableGpuCount` | `number` | `runtime-node-resource-latest:field:allocatableGpuCount` | `formatValue/display text` |
| `allocatedCpuCores` | `number` | `runtime-node-resource-latest:field:allocatedCpuCores` | `formatValue/display text` |
| `allocatedMemoryGb` | `number` | `runtime-node-resource-latest:field:allocatedMemoryGb` | `formatValue/display text` |
| `allocatedGpuCount` | `number` | `runtime-node-resource-latest:field:allocatedGpuCount` | `formatValue/display text` |
| `availableCpuCores` | `number` | `runtime-node-resource-latest:field:availableCpuCores` | `formatValue/display text` |
| `availableMemoryGb` | `number` | `runtime-node-resource-latest:field:availableMemoryGb` | `formatValue/display text` |
| `availableGpuCount` | `number` | `runtime-node-resource-latest:field:availableGpuCount` | `formatValue/display text` |
| `runningWorkloadCount` | `number` | `runtime-node-resource-latest:field:runningWorkloadCount` | `formatValue/display text` |
| `workloadCapacity` | `number` | `runtime-node-resource-latest:field:workloadCapacity` | `formatValue/display text` |
| `observedAt` | `string` | `runtime-node-resource-latest:field:observedAt` | `formatValue/display text` |
| `allocatableCapacityChanged` | `boolean` | `runtime-node-resource-latest:field:allocatableCapacityChanged` | `formatValue/display text` |
| `telemetryRetentionPolicy` | `string` | `runtime-node-resource-latest:field:telemetryRetentionPolicy` | `formatValue/display text` |

### Runtime Telemetry Latest

| Property | Value |
| --- | --- |
| Resource name | `runtime_telemetry_latest` |
| Route | `/runtime-telemetry-latest` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimemonitoring/slices/runtime-telemetry-latest/list.tsx` |
| Generated show page | `src/contexts/runtimemonitoring/slices/runtime-telemetry-latest/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "runtime_telemetry_latest" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "runtime_telemetry_latest", parent: "runtimemonitoring" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `runtime-telemetry-latest:list` | `src/contexts/runtimemonitoring/slices/runtime-telemetry-latest/list.tsx` |
| `show` | `runtime-telemetry-latest:show` | `src/contexts/runtimemonitoring/slices/runtime-telemetry-latest/show.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyRuntimeTelemetryLatestList } from "./pages/my-runtime-telemetry-latest-list";

export const pageOverrides = {
  "runtime-telemetry-latest:list": <MyRuntimeTelemetryLatestList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| _(none)_ | | | |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `nodeId` | `string` | `runtime-telemetry-latest:field:nodeId` | `formatValue/display text` |
| `runtimeAgentId` | `string` | `runtime-telemetry-latest:field:runtimeAgentId` | `formatValue/display text` |
| `federationId` | `string` | `runtime-telemetry-latest:field:federationId` | `formatValue/display text` |
| `federationName` | `string` | `runtime-telemetry-latest:field:federationName` | `formatValue/display text` |
| `trainingJobId` | `string` | `runtime-telemetry-latest:field:trainingJobId` | `formatValue/display text` |
| `trainingJobObjective` | `string` | `runtime-telemetry-latest:field:trainingJobObjective` | `formatValue/display text` |
| `roundExecutionId` | `string` | `runtime-telemetry-latest:field:roundExecutionId` | `formatValue/display text` |
| `runtimeNodeName` | `string` | `runtime-telemetry-latest:field:runtimeNodeName` | `formatValue/display text` |
| `cpuLoad` | `string` | `runtime-telemetry-latest:field:cpuLoad` | `formatValue/display text` |
| `gpuLoad` | `string` | `runtime-telemetry-latest:field:gpuLoad` | `formatValue/display text` |
| `memoryLoad` | `string` | `runtime-telemetry-latest:field:memoryLoad` | `formatValue/display text` |
| `lastHeartbeatAt` | `string` | `runtime-telemetry-latest:field:lastHeartbeatAt` | `formatValue/display text` |
| `heartbeatMissingBeyondThreshold` | `boolean` | `runtime-telemetry-latest:field:heartbeatMissingBeyondThreshold` | `formatValue/display text` |
| `heartbeatObservedAfterOffline` | `boolean` | `runtime-telemetry-latest:field:heartbeatObservedAfterOffline` | `formatValue/display text` |
| `resourcePressureDetected` | `boolean` | `runtime-telemetry-latest:field:resourcePressureDetected` | `formatValue/display text` |
| `telemetryRetentionPolicy` | `string` | `runtime-telemetry-latest:field:telemetryRetentionPolicy` | `formatValue/display text` |

### Secure Aggregation Session Catalog

| Property | Value |
| --- | --- |
| Resource name | `secure_aggregation_session_catalog` |
| Route | `/secure-aggregation-session-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/secureaggregation/slices/secure-aggregation-session-catalog/list.tsx` |
| Generated show page | `src/contexts/secureaggregation/slices/secure-aggregation-session-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "secure_aggregation_session_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "secure_aggregation_session_catalog", parent: "secureaggregation" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `secure-aggregation-session-catalog:list` | `src/contexts/secureaggregation/slices/secure-aggregation-session-catalog/list.tsx` |
| `show` | `secure-aggregation-session-catalog:show` | `src/contexts/secureaggregation/slices/secure-aggregation-session-catalog/show.tsx` |
| `failSecureAggregationSession` | `secure-aggregation-session-catalog:failSecureAggregationSession` | `src/contexts/secureaggregation/slices/fail-secure-aggregation-session/fail-secure-aggregation-session.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MySecureAggregationSessionCatalogList } from "./pages/my-secure-aggregation-session-catalog-list";

export const pageOverrides = {
  "secure-aggregation-session-catalog:list": <MySecureAggregationSessionCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `failSecureAggregationSession` | `secure-aggregation-session-catalog:failSecureAggregationSession` | `src/contexts/secureaggregation/slices/fail-secure-aggregation-session/fail-secure-aggregation-session.tsx` | `failureReason` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `secureAggregationSessionId` | `string` | `secure-aggregation-session-catalog:field:secureAggregationSessionId` | `formatValue/display text` |
| `trainingJobId` | `string` | `secure-aggregation-session-catalog:field:trainingJobId` | `formatValue/display text` |
| `trainingRunConfigurationId` | `string` | `secure-aggregation-session-catalog:field:trainingRunConfigurationId` | `formatValue/display text` |
| `featureSchemaId` | `string` | `secure-aggregation-session-catalog:field:featureSchemaId` | `formatValue/display text` |
| `roundId` | `string` | `secure-aggregation-session-catalog:field:roundId` | `formatValue/display text` |
| `roundNumber` | `number` | `secure-aggregation-session-catalog:field:roundNumber` | `formatValue/display text` |
| `requiredParticipantCount` | `number` | `secure-aggregation-session-catalog:field:requiredParticipantCount` | `formatValue/display text` |
| `selectedOrganizationIds` | `string[]` | `secure-aggregation-session-catalog:field:selectedOrganizationIds` | `formatValue/display text` |
| `selectedRuntimeIds` | `string[]` | `secure-aggregation-session-catalog:field:selectedRuntimeIds` | `formatValue/display text` |
| `selectedOrganizationCount` | `number` | `secure-aggregation-session-catalog:field:selectedOrganizationCount` | `formatValue/display text` |
| `selectedRuntimeCount` | `number` | `secure-aggregation-session-catalog:field:selectedRuntimeCount` | `formatValue/display text` |
| `selectedParticipantCount` | `number` | `secure-aggregation-session-catalog:field:selectedParticipantCount` | `formatValue/display text` |
| `encryptionContextPrepared` | `boolean` | `secure-aggregation-session-catalog:field:encryptionContextPrepared` | `formatValue/display text` |
| `receivedEncryptedUpdateCount` | `number` | `secure-aggregation-session-catalog:field:receivedEncryptedUpdateCount` | `formatValue/display text` |
| `encryptionScheme` | `string` | `secure-aggregation-session-catalog:field:encryptionScheme` | `formatValue/display text` |
| `publicKeyVersion` | `string` | `secure-aggregation-session-catalog:field:publicKeyVersion` | `formatValue/display text` |
| `publicKeyRef` | `string` | `secure-aggregation-session-catalog:field:publicKeyRef` | `formatValue/display text` |
| `encryptedParameterScale` | `number` | `secure-aggregation-session-catalog:field:encryptedParameterScale` | `formatValue/display text` |
| `aggregatedModelId` | `string` | `secure-aggregation-session-catalog:field:aggregatedModelId` | `formatValue/display text` |
| `modelFormat` | `string` | `secure-aggregation-session-catalog:field:modelFormat` | `formatValue/display text` |
| `modelArtifactDigest` | `string` | `secure-aggregation-session-catalog:field:modelArtifactDigest` | `formatValue/display text` |
| `state` | `&#34;PLANNED&#34; | &#34;PARTICIPANTS_SELECTED&#34; | &#34;ENCRYPTION_CONTEXT_PREPARED&#34; | &#34;COMPLETED&#34; | &#34;FAILED&#34;` | `secure-aggregation-session-catalog:field:state` | `Select/display text` |
| `failureReason` | `string` | `secure-aggregation-session-catalog:field:failureReason` | `formatValue/display text` |
| `createdAt` | `string` | `secure-aggregation-session-catalog:field:createdAt` | `formatValue/display text` |
| `selectedAt` | `string` | `secure-aggregation-session-catalog:field:selectedAt` | `formatValue/display text` |
| `encryptionContextPreparedAt` | `string` | `secure-aggregation-session-catalog:field:encryptionContextPreparedAt` | `formatValue/display text` |
| `decryptedAt` | `string` | `secure-aggregation-session-catalog:field:decryptedAt` | `formatValue/display text` |
| `completedAt` | `string` | `secure-aggregation-session-catalog:field:completedAt` | `formatValue/display text` |
| `failedAt` | `string` | `secure-aggregation-session-catalog:field:failedAt` | `formatValue/display text` |

### Service Account Api Token Catalog

| Property | Value |
| --- | --- |
| Resource name | `service_account_api_token_catalog` |
| Route | `/service-account-api-token-catalog` |
| Backend module | `federation-learning-support` |
| Data provider | `federation-learning-support` |
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

### Training Alert Catalog

| Property | Value |
| --- | --- |
| Resource name | `training_alert_catalog` |
| Route | `/training-alert-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/runtimemonitoring/slices/training-alert-catalog/list.tsx` |
| Generated show page | `src/contexts/runtimemonitoring/slices/training-alert-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "training_alert_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "training_alert_catalog", parent: "runtimemonitoring" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `training-alert-catalog:list` | `src/contexts/runtimemonitoring/slices/training-alert-catalog/list.tsx` |
| `show` | `training-alert-catalog:show` | `src/contexts/runtimemonitoring/slices/training-alert-catalog/show.tsx` |
| `acknowledgeTrainingAlert` | `training-alert-catalog:acknowledgeTrainingAlert` | `src/contexts/runtimemonitoring/slices/acknowledge-training-alert/acknowledge-training-alert.tsx` |
| `resolveTrainingAlert` | `training-alert-catalog:resolveTrainingAlert` | `src/contexts/runtimemonitoring/slices/resolve-training-alert/resolve-training-alert.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyTrainingAlertCatalogList } from "./pages/my-training-alert-catalog-list";

export const pageOverrides = {
  "training-alert-catalog:list": <MyTrainingAlertCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `acknowledgeTrainingAlert` | `training-alert-catalog:acknowledgeTrainingAlert` | `src/contexts/runtimemonitoring/slices/acknowledge-training-alert/acknowledge-training-alert.tsx` | `acknowledgementNote` |
| `resolveTrainingAlert` | `training-alert-catalog:resolveTrainingAlert` | `src/contexts/runtimemonitoring/slices/resolve-training-alert/resolve-training-alert.tsx` | `resolutionSummary` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `alertId` | `string` | `training-alert-catalog:field:alertId` | `formatValue/display text` |
| `nodeId` | `string` | `training-alert-catalog:field:nodeId` | `formatValue/display text` |
| `trainingJobId` | `string` | `training-alert-catalog:field:trainingJobId` | `formatValue/display text` |
| `runtimeNodeName` | `string` | `training-alert-catalog:field:runtimeNodeName` | `formatValue/display text` |
| `trainingJobObjective` | `string` | `training-alert-catalog:field:trainingJobObjective` | `formatValue/display text` |
| `severity` | `string` | `training-alert-catalog:field:severity` | `formatValue/display text` |
| `message` | `string` | `training-alert-catalog:field:message` | `formatValue/display text` |
| `state` | `&#34;RAISED&#34; | &#34;ACKNOWLEDGED&#34; | &#34;RESOLVED&#34;` | `training-alert-catalog:field:state` | `Select/display text` |
| `acknowledgedAt` | `string` | `training-alert-catalog:field:acknowledgedAt` | `formatValue/display text` |
| `resolvedAt` | `string` | `training-alert-catalog:field:resolvedAt` | `formatValue/display text` |
| `resolutionSummary` | `string` | `training-alert-catalog:field:resolutionSummary` | `formatValue/display text` |
| `canAcknowledge` | `boolean` | `training-alert-catalog:field:canAcknowledge` | `formatValue/display text` |
| `canResolve` | `boolean` | `training-alert-catalog:field:canResolve` | `formatValue/display text` |

### Training Job Dashboard

| Property | Value |
| --- | --- |
| Resource name | `training_job_dashboard` |
| Route | `/training-job-dashboard` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/trainingorchestration/slices/training-job-dashboard/list.tsx` |
| Generated show page | `src/contexts/trainingorchestration/slices/training-job-dashboard/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "training_job_dashboard" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "training_job_dashboard", parent: "trainingorchestration" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `training-job-dashboard:list` | `src/contexts/trainingorchestration/slices/training-job-dashboard/list.tsx` |
| `show` | `training-job-dashboard:show` | `src/contexts/trainingorchestration/slices/training-job-dashboard/show.tsx` |
| `createTrainingJob` | `training-job-dashboard:createTrainingJob` | `src/contexts/trainingorchestration/slices/create-training-job/create-training-job-training-job-dashboard.tsx` |
| `cancelTrainingJob` | `training-job-dashboard:cancelTrainingJob` | `src/contexts/trainingorchestration/slices/cancel-training-job/cancel-training-job-training-job-dashboard.tsx` |
| `submitTrainingJob` | `training-job-dashboard:submitTrainingJob` | `src/contexts/trainingorchestration/slices/submit-training-job/submit-training-job-training-job-dashboard.tsx` |
| `pauseTrainingJob` | `training-job-dashboard:pauseTrainingJob` | `src/contexts/trainingorchestration/slices/pause-training-job/pause-training-job-training-job-dashboard.tsx` |
| `resumeTrainingJob` | `training-job-dashboard:resumeTrainingJob` | `src/contexts/trainingorchestration/slices/resume-training-job/resume-training-job.tsx` |
| `retryTrainingRoundParticipantSelection` | `training-job-dashboard:retryTrainingRoundParticipantSelection` | `src/contexts/trainingorchestration/slices/retry-training-round-participant-selection/retry-training-round-participant-selection-training-job-dashboard.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyTrainingJobDashboardList } from "./pages/my-training-job-dashboard-list";

export const pageOverrides = {
  "training-job-dashboard:list": <MyTrainingJobDashboardList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `createTrainingJob` | `training-job-dashboard:createTrainingJob` | `src/contexts/trainingorchestration/slices/create-training-job/create-training-job-training-job-dashboard.tsx` | `federationId`, `trainingRunConfigurationId`, `objective` |
| `cancelTrainingJob` | `training-job-dashboard:cancelTrainingJob` | `src/contexts/trainingorchestration/slices/cancel-training-job/cancel-training-job-training-job-dashboard.tsx` | `cancelReason` |
| `submitTrainingJob` | `training-job-dashboard:submitTrainingJob` | `src/contexts/trainingorchestration/slices/submit-training-job/submit-training-job-training-job-dashboard.tsx` | _(none)_ |
| `pauseTrainingJob` | `training-job-dashboard:pauseTrainingJob` | `src/contexts/trainingorchestration/slices/pause-training-job/pause-training-job-training-job-dashboard.tsx` | `pauseReason` |
| `resumeTrainingJob` | `training-job-dashboard:resumeTrainingJob` | `src/contexts/trainingorchestration/slices/resume-training-job/resume-training-job.tsx` | `resumeReason` |
| `retryTrainingRoundParticipantSelection` | `training-job-dashboard:retryTrainingRoundParticipantSelection` | `src/contexts/trainingorchestration/slices/retry-training-round-participant-selection/retry-training-round-participant-selection-training-job-dashboard.tsx` | _(none)_ |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `trainingJobId` | `string` | `training-job-dashboard:field:trainingJobId` | `formatValue/display text` |
| `federationId` | `string` | `training-job-dashboard:field:federationId` | `formatValue/display text` |
| `trainingRunConfigurationId` | `string` | `training-job-dashboard:field:trainingRunConfigurationId` | `formatValue/display text` |
| `featureSchemaId` | `string` | `training-job-dashboard:field:featureSchemaId` | `formatValue/display text` |
| `federationName` | `string` | `training-job-dashboard:field:federationName` | `formatValue/display text` |
| `featureDomain` | `string` | `training-job-dashboard:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `training-job-dashboard:field:featureSchemaVersion` | `formatValue/display text` |
| `objective` | `string` | `training-job-dashboard:field:objective` | `formatValue/display text` |
| `strategyName` | `string` | `training-job-dashboard:field:strategyName` | `formatValue/display text` |
| `aggregationAlgorithm` | `string` | `training-job-dashboard:field:aggregationAlgorithm` | `formatValue/display text` |
| `secureAggregationRequired` | `boolean` | `training-job-dashboard:field:secureAggregationRequired` | `formatValue/display text` |
| `state` | `&#34;DRAFT&#34; | &#34;SUBMITTED&#34; | &#34;RUNNING&#34; | &#34;PAUSED&#34; | &#34;CANCELED&#34; | &#34;COMPLETED&#34;` | `training-job-dashboard:field:state` | `Select/display text` |
| `workflowStage` | `string` | `training-job-dashboard:field:workflowStage` | `formatValue/display text` |
| `workflowStep` | `number` | `training-job-dashboard:field:workflowStep` | `formatValue/display text` |
| `nextAction` | `string` | `training-job-dashboard:field:nextAction` | `formatValue/display text` |
| `availableActions` | `string[]` | `training-job-dashboard:field:availableActions` | `formatValue/display text` |
| `blockedReason` | `string` | `training-job-dashboard:field:blockedReason` | `formatValue/display text` |
| `canSubmit` | `boolean` | `training-job-dashboard:field:canSubmit` | `formatValue/display text` |
| `canStartRound` | `boolean` | `training-job-dashboard:field:canStartRound` | `formatValue/display text` |
| `canPause` | `boolean` | `training-job-dashboard:field:canPause` | `formatValue/display text` |
| `canResume` | `boolean` | `training-job-dashboard:field:canResume` | `formatValue/display text` |
| `canCancel` | `boolean` | `training-job-dashboard:field:canCancel` | `formatValue/display text` |
| `canComplete` | `boolean` | `training-job-dashboard:field:canComplete` | `formatValue/display text` |
| `currentRoundNumber` | `number` | `training-job-dashboard:field:currentRoundNumber` | `formatValue/display text` |
| `startedRuntimeCount` | `number` | `training-job-dashboard:field:startedRuntimeCount` | `formatValue/display text` |
| `minimumNodesPerRound` | `number` | `training-job-dashboard:field:minimumNodesPerRound` | `formatValue/display text` |
| `maxRounds` | `number` | `training-job-dashboard:field:maxRounds` | `formatValue/display text` |
| `roundProgressPercent` | `number` | `training-job-dashboard:field:roundProgressPercent` | `formatValue/display text` |
| `globalAccuracy` | `string` | `training-job-dashboard:field:globalAccuracy` | `formatValue/display text` |
| `finalModelId` | `string` | `training-job-dashboard:field:finalModelId` | `formatValue/display text` |
| `stopReason` | `string` | `training-job-dashboard:field:stopReason` | `formatValue/display text` |

### Training Participant Eligibility

| Property | Value |
| --- | --- |
| Resource name | `training_participant_eligibility` |
| Route | `/training-participant-eligibility` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/trainingorchestration/slices/training-participant-eligibility/list.tsx` |
| Generated show page | `src/contexts/trainingorchestration/slices/training-participant-eligibility/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "training_participant_eligibility" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "training_participant_eligibility", parent: "trainingorchestration" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `training-participant-eligibility:list` | `src/contexts/trainingorchestration/slices/training-participant-eligibility/list.tsx` |
| `show` | `training-participant-eligibility:show` | `src/contexts/trainingorchestration/slices/training-participant-eligibility/show.tsx` |
| `submitTrainingJob` | `training-participant-eligibility:submitTrainingJob` | `src/contexts/trainingorchestration/slices/submit-training-job/submit-training-job-training-participant-eligibility.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyTrainingParticipantEligibilityList } from "./pages/my-training-participant-eligibility-list";

export const pageOverrides = {
  "training-participant-eligibility:list": <MyTrainingParticipantEligibilityList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `submitTrainingJob` | `training-participant-eligibility:submitTrainingJob` | `src/contexts/trainingorchestration/slices/submit-training-job/submit-training-job-training-participant-eligibility.tsx` | _(none)_ |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `trainingJobId` | `string` | `training-participant-eligibility:field:trainingJobId` | `formatValue/display text` |
| `federationId` | `string` | `training-participant-eligibility:field:federationId` | `formatValue/display text` |
| `organizationId` | `string` | `training-participant-eligibility:field:organizationId` | `formatValue/display text` |
| `runtimeId` | `string` | `training-participant-eligibility:field:runtimeId` | `formatValue/display text` |
| `featureSchemaId` | `string` | `training-participant-eligibility:field:featureSchemaId` | `formatValue/display text` |
| `federationName` | `string` | `training-participant-eligibility:field:federationName` | `formatValue/display text` |
| `organizationName` | `string` | `training-participant-eligibility:field:organizationName` | `formatValue/display text` |
| `featureDomain` | `string` | `training-participant-eligibility:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `training-participant-eligibility:field:featureSchemaVersion` | `formatValue/display text` |
| `participantStatus` | `string` | `training-participant-eligibility:field:participantStatus` | `formatValue/display text` |
| `readinessStatus` | `string` | `training-participant-eligibility:field:readinessStatus` | `formatValue/display text` |
| `readinessStage` | `string` | `training-participant-eligibility:field:readinessStage` | `formatValue/display text` |
| `eligibilityScore` | `number` | `training-participant-eligibility:field:eligibilityScore` | `formatValue/display text` |
| `runtimeIdentityActive` | `boolean` | `training-participant-eligibility:field:runtimeIdentityActive` | `formatValue/display text` |
| `runtimeCapabilitySatisfied` | `boolean` | `training-participant-eligibility:field:runtimeCapabilitySatisfied` | `formatValue/display text` |
| `runtimeConnectionEstablished` | `boolean` | `training-participant-eligibility:field:runtimeConnectionEstablished` | `formatValue/display text` |
| `runtimeHealthy` | `boolean` | `training-participant-eligibility:field:runtimeHealthy` | `formatValue/display text` |
| `datasetId` | `string` | `training-participant-eligibility:field:datasetId` | `formatValue/display text` |
| `datasetName` | `string` | `training-participant-eligibility:field:datasetName` | `formatValue/display text` |
| `datasetReady` | `boolean` | `training-participant-eligibility:field:datasetReady` | `formatValue/display text` |
| `datasetReadinessStatus` | `string` | `training-participant-eligibility:field:datasetReadinessStatus` | `formatValue/display text` |
| `matchedDatasetMetadataReady` | `boolean` | `training-participant-eligibility:field:matchedDatasetMetadataReady` | `formatValue/display text` |
| `datasetAccessValidated` | `boolean` | `training-participant-eligibility:field:datasetAccessValidated` | `formatValue/display text` |
| `datasetApprovedForTraining` | `boolean` | `training-participant-eligibility:field:datasetApprovedForTraining` | `formatValue/display text` |
| `schemaCompatible` | `boolean` | `training-participant-eligibility:field:schemaCompatible` | `formatValue/display text` |
| `labelCompatible` | `boolean` | `training-participant-eligibility:field:labelCompatible` | `formatValue/display text` |
| `qualityScore` | `string` | `training-participant-eligibility:field:qualityScore` | `formatValue/display text` |
| `securityReady` | `boolean` | `training-participant-eligibility:field:securityReady` | `formatValue/display text` |
| `eligible` | `boolean` | `training-participant-eligibility:field:eligible` | `formatValue/display text` |
| `eligibleRuntimeCount` | `number` | `training-participant-eligibility:field:eligibleRuntimeCount` | `formatValue/display text` |
| `minimumNodesPerRound` | `number` | `training-participant-eligibility:field:minimumNodesPerRound` | `formatValue/display text` |
| `selectionReady` | `boolean` | `training-participant-eligibility:field:selectionReady` | `formatValue/display text` |
| `eligibilityReason` | `string` | `training-participant-eligibility:field:eligibilityReason` | `formatValue/display text` |
| `ineligibleReasons` | `string[]` | `training-participant-eligibility:field:ineligibleReasons` | `formatValue/display text` |
| `warningReasons` | `string[]` | `training-participant-eligibility:field:warningReasons` | `formatValue/display text` |
| `nextRequiredAction` | `string` | `training-participant-eligibility:field:nextRequiredAction` | `formatValue/display text` |

### Training Round Progress

| Property | Value |
| --- | --- |
| Resource name | `training_round_progress` |
| Route | `/training-round-progress` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/trainingorchestration/slices/training-round-progress/list.tsx` |
| Generated show page | `src/contexts/trainingorchestration/slices/training-round-progress/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "training_round_progress" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "training_round_progress", parent: "trainingorchestration" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `training-round-progress:list` | `src/contexts/trainingorchestration/slices/training-round-progress/list.tsx` |
| `show` | `training-round-progress:show` | `src/contexts/trainingorchestration/slices/training-round-progress/show.tsx` |
| `cancelTrainingJob` | `training-round-progress:cancelTrainingJob` | `src/contexts/trainingorchestration/slices/cancel-training-job/cancel-training-job-training-round-progress.tsx` |
| `submitModelUpdateSubmission` | `training-round-progress:submitModelUpdateSubmission` | `src/contexts/trainingorchestration/slices/submit-model-update-submission/submit-model-update-submission.tsx` |
| `submitTrainingJob` | `training-round-progress:submitTrainingJob` | `src/contexts/trainingorchestration/slices/submit-training-job/submit-training-job-training-round-progress.tsx` |
| `retryTrainingRoundParticipantSelection` | `training-round-progress:retryTrainingRoundParticipantSelection` | `src/contexts/trainingorchestration/slices/retry-training-round-participant-selection/retry-training-round-participant-selection-training-round-progress.tsx` |
| `pauseTrainingJob` | `training-round-progress:pauseTrainingJob` | `src/contexts/trainingorchestration/slices/pause-training-job/pause-training-job-training-round-progress.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyTrainingRoundProgressList } from "./pages/my-training-round-progress-list";

export const pageOverrides = {
  "training-round-progress:list": <MyTrainingRoundProgressList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `cancelTrainingJob` | `training-round-progress:cancelTrainingJob` | `src/contexts/trainingorchestration/slices/cancel-training-job/cancel-training-job-training-round-progress.tsx` | `cancelReason` |
| `submitModelUpdateSubmission` | `training-round-progress:submitModelUpdateSubmission` | `src/contexts/trainingorchestration/slices/submit-model-update-submission/submit-model-update-submission.tsx` | `executionSessionId`, `executionPlanId`, `trainingJobId`, `trainingRunConfigurationId`, `roundId`, `roundExecutionId`, `runtimeId`, `featureSchemaId`, `secureAggregationRequired`, `secureAggregationSessionId`, `encryptionScheme`, `publicKeyVersion`, `localModelId`, `updateArtifactId`, `artifactRef`, `artifactDigest`, `updateProtectionType`, `trainingLoss` |
| `submitTrainingJob` | `training-round-progress:submitTrainingJob` | `src/contexts/trainingorchestration/slices/submit-training-job/submit-training-job-training-round-progress.tsx` | _(none)_ |
| `retryTrainingRoundParticipantSelection` | `training-round-progress:retryTrainingRoundParticipantSelection` | `src/contexts/trainingorchestration/slices/retry-training-round-participant-selection/retry-training-round-participant-selection-training-round-progress.tsx` | _(none)_ |
| `pauseTrainingJob` | `training-round-progress:pauseTrainingJob` | `src/contexts/trainingorchestration/slices/pause-training-job/pause-training-job-training-round-progress.tsx` | `pauseReason` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `trainingJobId` | `string` | `training-round-progress:field:trainingJobId` | `formatValue/display text` |
| `trainingRunConfigurationId` | `string` | `training-round-progress:field:trainingRunConfigurationId` | `formatValue/display text` |
| `featureSchemaId` | `string` | `training-round-progress:field:featureSchemaId` | `formatValue/display text` |
| `roundId` | `string` | `training-round-progress:field:roundId` | `formatValue/display text` |
| `trainingJobObjective` | `string` | `training-round-progress:field:trainingJobObjective` | `formatValue/display text` |
| `featureDomain` | `string` | `training-round-progress:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `training-round-progress:field:featureSchemaVersion` | `formatValue/display text` |
| `roundNumber` | `number` | `training-round-progress:field:roundNumber` | `formatValue/display text` |
| `state` | `&#34;PARTICIPANTS_SELECTED&#34; | &#34;RUNNING&#34; | &#34;COLLECTING_UPDATES&#34; | &#34;AGGREGATING&#34; | &#34;EVALUATING_GLOBAL_MODEL&#34; | &#34;COMPLETED&#34; | &#34;FAILED&#34;` | `training-round-progress:field:state` | `Select/display text` |
| `selectedOrganizationIds` | `string[]` | `training-round-progress:field:selectedOrganizationIds` | `formatValue/display text` |
| `selectedParticipants` | `TrainingRoundParticipant[]` | `training-round-progress:field:selectedParticipants` | `formatValue/display text` |
| `selectedOrganizationCount` | `number` | `training-round-progress:field:selectedOrganizationCount` | `formatValue/display text` |
| `selectedRuntimeCount` | `number` | `training-round-progress:field:selectedRuntimeCount` | `formatValue/display text` |
| `targetRuntimeCount` | `number` | `training-round-progress:field:targetRuntimeCount` | `formatValue/display text` |
| `executionPlanDispatchedCount` | `number` | `training-round-progress:field:executionPlanDispatchedCount` | `formatValue/display text` |
| `roundExecutionStartedCount` | `number` | `training-round-progress:field:roundExecutionStartedCount` | `formatValue/display text` |
| `submittedModelUpdateCount` | `number` | `training-round-progress:field:submittedModelUpdateCount` | `formatValue/display text` |
| `rejectedUpdateCount` | `number` | `training-round-progress:field:rejectedUpdateCount` | `formatValue/display text` |
| `acceptedModelUpdateCount` | `number` | `training-round-progress:field:acceptedModelUpdateCount` | `formatValue/display text` |
| `acceptedUpdateCount` | `number` | `training-round-progress:field:acceptedUpdateCount` | `formatValue/display text` |
| `pendingUpdateCount` | `number` | `training-round-progress:field:pendingUpdateCount` | `formatValue/display text` |
| `failedRoundExecutionCount` | `number` | `training-round-progress:field:failedRoundExecutionCount` | `formatValue/display text` |
| `completedRoundExecutionCount` | `number` | `training-round-progress:field:completedRoundExecutionCount` | `formatValue/display text` |
| `retriedRoundExecutionCount` | `number` | `training-round-progress:field:retriedRoundExecutionCount` | `formatValue/display text` |
| `failedRoundExecutionRetryCount` | `number` | `training-round-progress:field:failedRoundExecutionRetryCount` | `formatValue/display text` |
| `quorumMet` | `boolean` | `training-round-progress:field:quorumMet` | `formatValue/display text` |
| `quorumStatus` | `string` | `training-round-progress:field:quorumStatus` | `formatValue/display text` |
| `minimumNodesPerRound` | `number` | `training-round-progress:field:minimumNodesPerRound` | `formatValue/display text` |
| `aggregationReady` | `boolean` | `training-round-progress:field:aggregationReady` | `formatValue/display text` |
| `secureAggregationRequired` | `boolean` | `training-round-progress:field:secureAggregationRequired` | `formatValue/display text` |
| `secureAggregationStatus` | `string` | `training-round-progress:field:secureAggregationStatus` | `formatValue/display text` |
| `evaluationComplete` | `boolean` | `training-round-progress:field:evaluationComplete` | `formatValue/display text` |
| `progressPercent` | `number` | `training-round-progress:field:progressPercent` | `formatValue/display text` |
| `currentPhase` | `string` | `training-round-progress:field:currentPhase` | `formatValue/display text` |
| `nextAction` | `string` | `training-round-progress:field:nextAction` | `formatValue/display text` |
| `blockedReason` | `string` | `training-round-progress:field:blockedReason` | `formatValue/display text` |
| `delayedReason` | `string` | `training-round-progress:field:delayedReason` | `formatValue/display text` |
| `roundStartedAt` | `string` | `training-round-progress:field:roundStartedAt` | `formatValue/display text` |
| `contributionDeadlineAt` | `string` | `training-round-progress:field:contributionDeadlineAt` | `formatValue/display text` |
| `aggregationStartedAt` | `string` | `training-round-progress:field:aggregationStartedAt` | `formatValue/display text` |
| `evaluationSubmittedAt` | `string` | `training-round-progress:field:evaluationSubmittedAt` | `formatValue/display text` |
| `completedAt` | `string` | `training-round-progress:field:completedAt` | `formatValue/display text` |
| `failedAt` | `string` | `training-round-progress:field:failedAt` | `formatValue/display text` |
| `baseModelId` | `string` | `training-round-progress:field:baseModelId` | `formatValue/display text` |
| `artifactRefs` | `string[]` | `training-round-progress:field:artifactRefs` | `formatValue/display text` |
| `rejectedUpdateReasons` | `string[]` | `training-round-progress:field:rejectedUpdateReasons` | `formatValue/display text` |
| `aggregatedModelId` | `string` | `training-round-progress:field:aggregatedModelId` | `formatValue/display text` |
| `globalAccuracy` | `string` | `training-round-progress:field:globalAccuracy` | `formatValue/display text` |
| `globalFairnessScore` | `string` | `training-round-progress:field:globalFairnessScore` | `formatValue/display text` |
| `failureReason` | `string` | `training-round-progress:field:failureReason` | `formatValue/display text` |

### Training Run Configuration Catalog

| Property | Value |
| --- | --- |
| Resource name | `training_run_configuration_catalog` |
| Route | `/training-run-configuration-catalog` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/trainingorchestration/slices/training-run-configuration-catalog/list.tsx` |
| Generated show page | `src/contexts/trainingorchestration/slices/training-run-configuration-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "training_run_configuration_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "training_run_configuration_catalog", parent: "trainingorchestration" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `training-run-configuration-catalog:list` | `src/contexts/trainingorchestration/slices/training-run-configuration-catalog/list.tsx` |
| `show` | `training-run-configuration-catalog:show` | `src/contexts/trainingorchestration/slices/training-run-configuration-catalog/show.tsx` |
| `defineTrainingRunConfiguration` | `training-run-configuration-catalog:defineTrainingRunConfiguration` | `src/contexts/trainingorchestration/slices/define-training-run-configuration/define-training-run-configuration.tsx` |
| `updateTrainingRunConfiguration` | `training-run-configuration-catalog:updateTrainingRunConfiguration` | `src/contexts/trainingorchestration/slices/update-training-run-configuration/update-training-run-configuration.tsx` |
| `createTrainingJob` | `training-run-configuration-catalog:createTrainingJob` | `src/contexts/trainingorchestration/slices/create-training-job/create-training-job-training-run-configuration-catalog.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyTrainingRunConfigurationCatalogList } from "./pages/my-training-run-configuration-catalog-list";

export const pageOverrides = {
  "training-run-configuration-catalog:list": <MyTrainingRunConfigurationCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `defineTrainingRunConfiguration` | `training-run-configuration-catalog:defineTrainingRunConfiguration` | `src/contexts/trainingorchestration/slices/define-training-run-configuration/define-training-run-configuration.tsx` | `configurationName`, `federationId`, `featureSchemaId`, `initialModelId`, `runtimeEngineProfileId`, `strategyName`, `aggregationAlgorithm`, `maxRounds`, `minimumNodesPerRound`, `roundTimeoutSeconds`, `nodeResponseTimeoutSeconds`, `localEpochs`, `batchSize`, `learningRate`, `optimizer`, `lossFunction`, `gradientClippingNorm`, `secureAggregationRequired`, `minimumAccuracy`, `minimumFairnessScore` |
| `updateTrainingRunConfiguration` | `training-run-configuration-catalog:updateTrainingRunConfiguration` | `src/contexts/trainingorchestration/slices/update-training-run-configuration/update-training-run-configuration.tsx` | `configurationName`, `federationId`, `featureSchemaId`, `initialModelId`, `runtimeEngineProfileId`, `strategyName`, `aggregationAlgorithm`, `maxRounds`, `minimumNodesPerRound`, `roundTimeoutSeconds`, `nodeResponseTimeoutSeconds`, `localEpochs`, `batchSize`, `learningRate`, `optimizer`, `lossFunction`, `gradientClippingNorm`, `secureAggregationRequired`, `minimumAccuracy`, `minimumFairnessScore`, `updateReason` |
| `createTrainingJob` | `training-run-configuration-catalog:createTrainingJob` | `src/contexts/trainingorchestration/slices/create-training-job/create-training-job-training-run-configuration-catalog.tsx` | `federationId`, `trainingRunConfigurationId`, `objective` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `trainingRunConfigurationId` | `string` | `training-run-configuration-catalog:field:trainingRunConfigurationId` | `formatValue/display text` |
| `configurationName` | `string` | `training-run-configuration-catalog:field:configurationName` | `formatValue/display text` |
| `federationId` | `string` | `training-run-configuration-catalog:field:federationId` | `formatValue/display text` |
| `featureSchemaId` | `string` | `training-run-configuration-catalog:field:featureSchemaId` | `formatValue/display text` |
| `initialModelId` | `string` | `training-run-configuration-catalog:field:initialModelId` | `formatValue/display text` |
| `initialModelName` | `string` | `training-run-configuration-catalog:field:initialModelName` | `formatValue/display text` |
| `initialModelPlugin` | `string` | `training-run-configuration-catalog:field:initialModelPlugin` | `formatValue/display text` |
| `initialModelVersion` | `string` | `training-run-configuration-catalog:field:initialModelVersion` | `formatValue/display text` |
| `federationName` | `string` | `training-run-configuration-catalog:field:federationName` | `formatValue/display text` |
| `featureDomain` | `string` | `training-run-configuration-catalog:field:featureDomain` | `formatValue/display text` |
| `featureSchemaVersion` | `string` | `training-run-configuration-catalog:field:featureSchemaVersion` | `formatValue/display text` |
| `initialModelArtifactUri` | `string` | `training-run-configuration-catalog:field:initialModelArtifactUri` | `formatValue/display text` |
| `initialModelRegistryRef` | `string` | `training-run-configuration-catalog:field:initialModelRegistryRef` | `formatValue/display text` |
| `initialModelFormat` | `string` | `training-run-configuration-catalog:field:initialModelFormat` | `formatValue/display text` |
| `initialModelArtifactDigest` | `string` | `training-run-configuration-catalog:field:initialModelArtifactDigest` | `formatValue/display text` |
| `initialModelSignatureUri` | `string` | `training-run-configuration-catalog:field:initialModelSignatureUri` | `formatValue/display text` |
| `runtimeEngineProfileId` | `string` | `training-run-configuration-catalog:field:runtimeEngineProfileId` | `formatValue/display text` |
| `runtimeEngineProfileName` | `string` | `training-run-configuration-catalog:field:runtimeEngineProfileName` | `formatValue/display text` |
| `runtimeEnginePluginProfile` | `string` | `training-run-configuration-catalog:field:runtimeEnginePluginProfile` | `formatValue/display text` |
| `runtimeEngineImage` | `string` | `training-run-configuration-catalog:field:runtimeEngineImage` | `formatValue/display text` |
| `runtimeEngineImageDigest` | `string` | `training-run-configuration-catalog:field:runtimeEngineImageDigest` | `formatValue/display text` |
| `strategyName` | `string` | `training-run-configuration-catalog:field:strategyName` | `formatValue/display text` |
| `aggregationAlgorithm` | `string` | `training-run-configuration-catalog:field:aggregationAlgorithm` | `formatValue/display text` |
| `maxRounds` | `number` | `training-run-configuration-catalog:field:maxRounds` | `formatValue/display text` |
| `minimumNodesPerRound` | `number` | `training-run-configuration-catalog:field:minimumNodesPerRound` | `formatValue/display text` |
| `roundTimeoutSeconds` | `number` | `training-run-configuration-catalog:field:roundTimeoutSeconds` | `formatValue/display text` |
| `nodeResponseTimeoutSeconds` | `number` | `training-run-configuration-catalog:field:nodeResponseTimeoutSeconds` | `formatValue/display text` |
| `localEpochs` | `number` | `training-run-configuration-catalog:field:localEpochs` | `formatValue/display text` |
| `batchSize` | `number` | `training-run-configuration-catalog:field:batchSize` | `formatValue/display text` |
| `learningRate` | `string` | `training-run-configuration-catalog:field:learningRate` | `formatValue/display text` |
| `optimizer` | `string` | `training-run-configuration-catalog:field:optimizer` | `formatValue/display text` |
| `lossFunction` | `string` | `training-run-configuration-catalog:field:lossFunction` | `formatValue/display text` |
| `gradientClippingNorm` | `string` | `training-run-configuration-catalog:field:gradientClippingNorm` | `formatValue/display text` |
| `secureAggregationRequired` | `boolean` | `training-run-configuration-catalog:field:secureAggregationRequired` | `formatValue/display text` |
| `minimumAccuracy` | `string` | `training-run-configuration-catalog:field:minimumAccuracy` | `formatValue/display text` |
| `minimumFairnessScore` | `string` | `training-run-configuration-catalog:field:minimumFairnessScore` | `formatValue/display text` |
| `updateReason` | `string` | `training-run-configuration-catalog:field:updateReason` | `formatValue/display text` |
| `lockedByTrainingJobId` | `string` | `training-run-configuration-catalog:field:lockedByTrainingJobId` | `formatValue/display text` |
| `state` | `&#34;DRAFT&#34; | &#34;LOCKED&#34;` | `training-run-configuration-catalog:field:state` | `Select/display text` |

### Uploaded File Catalog

| Property | Value |
| --- | --- |
| Resource name | `uploaded_file_catalog` |
| Route | `/uploaded-file-catalog` |
| Backend module | `federation-learning-support` |
| Data provider | `federation-learning-support` |
| Generated list page | `src/contexts/fileupload/slices/uploaded-file-catalog/list.tsx` |
| Generated show page | `src/contexts/fileupload/slices/uploaded-file-catalog/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "uploaded_file_catalog" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "uploaded_file_catalog", parent: "fileupload" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `uploaded-file-catalog:list` | `src/contexts/fileupload/slices/uploaded-file-catalog/list.tsx` |
| `show` | `uploaded-file-catalog:show` | `src/contexts/fileupload/slices/uploaded-file-catalog/show.tsx` |
| `uploadFile` | `uploaded-file-catalog:uploadFile` | `src/contexts/fileupload/slices/upload-file/upload-file.tsx` |
| `markFileReferenced` | `uploaded-file-catalog:markFileReferenced` | `src/contexts/fileupload/slices/mark-file-referenced/mark-file-referenced.tsx` |
| `discardFile` | `uploaded-file-catalog:discardFile` | `src/contexts/fileupload/slices/discard-file/discard-file.tsx` |
| `downloadFile` | `uploaded-file-catalog:downloadFile` | `src/contexts/fileupload/slices/download-file/download-file.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyUploadedFileCatalogList } from "./pages/my-uploaded-file-catalog-list";

export const pageOverrides = {
  "uploaded-file-catalog:list": <MyUploadedFileCatalogList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `uploadFile` | `uploaded-file-catalog:uploadFile` | `src/contexts/fileupload/slices/upload-file/upload-file.tsx` | `uploadedFile`, `purpose` |
| `markFileReferenced` | `uploaded-file-catalog:markFileReferenced` | `src/contexts/fileupload/slices/mark-file-referenced/mark-file-referenced.tsx` | `referencedByContext`, `referencedByCommand`, `referencedByCommandId` |
| `discardFile` | `uploaded-file-catalog:discardFile` | `src/contexts/fileupload/slices/discard-file/discard-file.tsx` | `discardReason` |
| `downloadFile` | `uploaded-file-catalog:downloadFile` | `src/contexts/fileupload/slices/download-file/download-file.tsx` | _(none)_ |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `fileId` | `string` | `uploaded-file-catalog:field:fileId` | `formatValue/display text` |
| `originalFileName` | `string` | `uploaded-file-catalog:field:originalFileName` | `formatValue/display text` |
| `contentType` | `string` | `uploaded-file-catalog:field:contentType` | `formatValue/display text` |
| `sizeBytes` | `number` | `uploaded-file-catalog:field:sizeBytes` | `formatValue/display text` |
| `purpose` | `string` | `uploaded-file-catalog:field:purpose` | `formatValue/display text` |
| `fileLocation` | `string` | `uploaded-file-catalog:field:fileLocation` | `formatValue/display text` |
| `checksum` | `string` | `uploaded-file-catalog:field:checksum` | `formatValue/display text` |
| `state` | `&#34;AVAILABLE&#34; | &#34;REFERENCED&#34; | &#34;DISCARDED&#34; | &#34;EXPIRED&#34;` | `uploaded-file-catalog:field:state` | `Select/display text` |
| `uploadedAt` | `string` | `uploaded-file-catalog:field:uploadedAt` | `formatValue/display text` |
| `referencedAt` | `string` | `uploaded-file-catalog:field:referencedAt` | `formatValue/display text` |
| `referencedByContext` | `string` | `uploaded-file-catalog:field:referencedByContext` | `formatValue/display text` |
| `referencedByCommand` | `string` | `uploaded-file-catalog:field:referencedByCommand` | `formatValue/display text` |
| `referencedByCommandId` | `string` | `uploaded-file-catalog:field:referencedByCommandId` | `formatValue/display text` |
| `discardedAt` | `string` | `uploaded-file-catalog:field:discardedAt` | `formatValue/display text` |
| `discardReason` | `string` | `uploaded-file-catalog:field:discardReason` | `formatValue/display text` |
| `expiresAt` | `string` | `uploaded-file-catalog:field:expiresAt` | `formatValue/display text` |
| `expiredAt` | `string` | `uploaded-file-catalog:field:expiredAt` | `formatValue/display text` |
| `expirationReason` | `string` | `uploaded-file-catalog:field:expirationReason` | `formatValue/display text` |

### User Account Catalog

| Property | Value |
| --- | --- |
| Resource name | `user_account_catalog` |
| Route | `/user-account-catalog` |
| Backend module | `federation-learning-support` |
| Data provider | `federation-learning-support` |
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

### User Organization Membership Directory

| Property | Value |
| --- | --- |
| Resource name | `user_organization_membership_directory` |
| Route | `/user-organization-membership-directory` |
| Backend module | `federation-learning-platform` |
| Data provider | `federation-learning-platform` |
| Generated list page | `src/contexts/organizationmanagement/slices/user-organization-membership-directory/list.tsx` |
| Generated show page | `src/contexts/organizationmanagement/slices/user-organization-membership-directory/show.tsx` |
| Resource metadata override | `resourceOverrides[{ name: "user_organization_membership_directory" }]` |
| Menu icon request | `resolveMenuIcon({ type: "resource", name: "user_organization_membership_directory", parent: "organizationmanagement" })` |

#### Page Overrides

| View | Override key | Generated fallback |
| --- | --- | --- |
| `list` | `user-organization-membership-directory:list` | `src/contexts/organizationmanagement/slices/user-organization-membership-directory/list.tsx` |
| `show` | `user-organization-membership-directory:show` | `src/contexts/organizationmanagement/slices/user-organization-membership-directory/show.tsx` |
| `bindUserAccountToOrganization` | `user-organization-membership-directory:bindUserAccountToOrganization` | `src/contexts/organizationmanagement/slices/bind-user-account-to-organization/bind-user-account-to-organization.tsx` |

Example:

```tsx
// src/domain/page-overrides.tsx
import { MyUserOrganizationMembershipDirectoryList } from "./pages/my-user-organization-membership-directory-list";

export const pageOverrides = {
  "user-organization-membership-directory:list": <MyUserOrganizationMembershipDirectoryList />,
};
```

#### Commands And Row Actions

| Command | Override key | Generated fallback | Fields |
| --- | --- | --- | --- |
| `bindUserAccountToOrganization` | `user-organization-membership-directory:bindUserAccountToOrganization` | `src/contexts/organizationmanagement/slices/bind-user-account-to-organization/bind-user-account-to-organization.tsx` | `userAccountId`, `organizationId`, `organizationUserRole` |

#### Field Renderers

| Field | Type | Renderer override id | Default renderer |
| --- | --- | --- | --- |
| `userOrganizationMembershipId` | `string` | `user-organization-membership-directory:field:userOrganizationMembershipId` | `formatValue/display text` |
| `userAccountId` | `string` | `user-organization-membership-directory:field:userAccountId` | `formatValue/display text` |
| `username` | `string` | `user-organization-membership-directory:field:username` | `formatValue/display text` |
| `organizationId` | `string` | `user-organization-membership-directory:field:organizationId` | `formatValue/display text` |
| `organizationName` | `string` | `user-organization-membership-directory:field:organizationName` | `formatValue/display text` |
| `organizationUserRole` | `string` | `user-organization-membership-directory:field:organizationUserRole` | `formatValue/display text` |
| `state` | `&#34;ACTIVE&#34;` | `user-organization-membership-directory:field:state` | `Select/display text` |

### User Role Assignment Catalog

| Property | Value |
| --- | --- |
| Resource name | `user_role_assignment_catalog` |
| Route | `/user-role-assignment-catalog` |
| Backend module | `federation-learning-support` |
| Data provider | `federation-learning-support` |
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
