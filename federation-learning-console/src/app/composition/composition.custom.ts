import type { FrontendCompositionCustom } from "@/platform/composition";

import { DictionaryCodeFieldRenderer } from "@/domain/overrides/dictionary-catalog/DictionaryCodeFieldRenderer";
import { ModelArtifactUriFieldRenderer } from "@/domain/overrides/model-artifact-catalog/ModelArtifactUriFieldRenderer";

export const frontendCompositionCustom: FrontendCompositionCustom = {
  blueprints: [],
  extensions: [],
  overrides: [
    {
      target: "field:dictionary-catalog:display:dictionaryCode",
      type: "component",
      implementation: DictionaryCodeFieldRenderer,
    },
    {
      target: "field:model-artifact-catalog:display:modelArtifactUri",
      type: "component",
      implementation: ModelArtifactUriFieldRenderer,
    },
  ],
};
