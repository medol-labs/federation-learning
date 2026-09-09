// Generated domain value types. Do not edit manually.
export type OrganizationType = "HOSPITAL" | "RESEARCH_INSTITUTE" | "PUBLIC_HEALTH_AGENCY" | "LABORATORY" | "REHABILITATION_CENTER";
export type DictionaryCode = string;
export type DictionaryValueCode = string;
export type DisplayOrder = number;
export type FeatureDefinition = {
  featureName: string;
  dataType: string;
  required: boolean;
  nullable: boolean;
  description?: string;
  validationRules: string[];
  defaultValue?: string;
  isIdentifier: boolean;
  isSensitive: boolean;
  encodingStrategy?: string;
  featureTags: string[];
};
export type LabelDefinition = {
  labelName: string;
  dataType: string;
  cardinality: number;
  classLabels?: string[];
  isMultilabel: boolean;
  description?: string;
  validationRules: string[];
  defaultValue?: string;
};
export type TrainingRoundParticipant = {
  organizationId: string;
  runtimeId: string;
  datasetId: string;
};
