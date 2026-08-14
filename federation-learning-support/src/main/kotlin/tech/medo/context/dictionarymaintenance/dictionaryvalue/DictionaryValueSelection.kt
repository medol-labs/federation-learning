package tech.medo.dictionarymaintenance.dictionaryvalue

import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;
import tech.medo.dictionarymaintenance.domain.types.DictionaryValueCode;


data class DictionaryValueSelection(
    val dictionaryCode: DictionaryCode,
    val valueCode: DictionaryValueCode
)

object DictionaryValueTags {
    const val DICTIONARY_CODE = "dictionaryCode"
    const val VALUE_CODE = "valueCode"
}

object DictionaryValueMetadata {
    val concepts = listOf("DictionaryValue")
}
