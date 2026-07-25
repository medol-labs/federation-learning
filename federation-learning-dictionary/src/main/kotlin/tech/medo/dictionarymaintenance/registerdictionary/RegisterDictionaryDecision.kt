package tech.medo.dictionarymaintenance.registerdictionary

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.medo.dictionarymaintenance.registerdictionary.RegisterDictionaryCommand

import tech.medo.dictionarymaintenance.events.DictionaryRegisteredEvent
import tech.medo.dictionarymaintenance.events.DictionaryCodeReservedEvent
import tech.medo.dictionarymaintenance.dictionary.DictionaryState

import tech.medo.dictionarymaintenance.dictionary.DictionaryCodeReservationState



@Component
class RegisterDictionaryDecision {
    private val logger = LoggerFactory.getLogger(RegisterDictionaryDecision::class.java)

    fun decide(command: RegisterDictionaryCommand, dictionaryCodeReservation: DictionaryCodeReservationState): List<Any> {
        logger.debug(
            "Deciding register dictionary. dictionaryCode={}, normalizedName={}, reserved={}, reservedDictionaryId={}",
            command.dictionaryCode,
            command.dictionaryCodeSelection.normalizedName,
            dictionaryCodeReservation.reserved,
            dictionaryCodeReservation.dictionaryId
        )
        require(!dictionaryCodeReservation.reserved) {
            "Code already exists."
        }
        return listOf(
            DictionaryCodeReservedEvent(dictionaryId = command.dictionaryId, dictionaryCode = command.dictionaryCode, normalizedName = command.dictionaryCode.value.trim().lowercase()),
            DictionaryRegisteredEvent(dictionaryId = command.dictionaryId, dictionaryCode = command.dictionaryCode, dictionaryName = command.dictionaryName, description = command.description)
        )
    }
}
