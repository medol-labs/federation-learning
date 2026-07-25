package tech.medo.dictionarymaintenance.dictionary

import java.util.UUID;


data class DictionarySelection(
    val dictionaryId: UUID
)

object DictionaryTags {
    const val DICTIONARY_ID = "dictionaryId"
}

object DictionaryMetadata {
    val concepts = listOf("Dictionary")
}
