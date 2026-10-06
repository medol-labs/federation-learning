package tech.medo.dictionarymaintenance.dictionaryvaluetranslation

import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;
import tech.medo.dictionarymaintenance.domain.types.LocaleCode;


data class DictionaryValueTranslationSelection(
    val dictionaryCode: DictionaryCode,
    val valueCode: DictionaryValueCode,
    val locale: LocaleCode
)

object DictionaryValueTranslationTags {
    const val DICTIONARY_CODE = "dictionaryCode"
    const val VALUE_CODE = "valueCode"
    const val LOCALE = "locale"
}

object DictionaryValueTranslationMetadata {
    val concepts = listOf("DictionaryValueTranslation")
}
