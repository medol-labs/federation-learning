import type { FrontendCompositionCustom } from "@/platform/composition";

import { DictionaryCodeFieldRenderer } from "@/domain/overrides/dictionary-catalog/DictionaryCodeFieldRenderer";

export const frontendCompositionCustom: FrontendCompositionCustom = {
  blueprints: [],
  extensions: [],
  overrides: [
    {
      target: "field:dictionary-catalog:display:dictionaryCode",
      type: "component",
      implementation: DictionaryCodeFieldRenderer,
    },
  ],
};
