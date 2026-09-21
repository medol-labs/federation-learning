import { CopyableText } from "@/components/refine-ui/fields/copyable-text";
import type { FieldRendererProps } from "@/platform/composition";

export function DictionaryCodeFieldRenderer({
  value,
  compact,
}: FieldRendererProps) {
  return <CopyableText value={value} compact={compact} />;
}
