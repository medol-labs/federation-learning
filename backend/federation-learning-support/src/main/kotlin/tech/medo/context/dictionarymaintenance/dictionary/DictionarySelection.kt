package tech.medo.dictionarymaintenance.dictionary

import tech.medo.dictionarymaintenance.domain.types.DictionaryCode;


data class DictionarySelection(
    val dictionaryCode: DictionaryCode
)

object DictionaryTags {
    const val DICTIONARY_CODE = "dictionaryCode"
}

object DictionaryMetadata {
    val concepts = listOf("Dictionary")
}
