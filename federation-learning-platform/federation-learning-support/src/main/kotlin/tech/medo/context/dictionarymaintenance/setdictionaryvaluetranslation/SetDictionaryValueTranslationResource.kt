package tech.medo.dictionarymaintenance.setdictionaryvaluetranslation

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.axonframework.messaging.commandhandling.gateway.CommandGateway
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.medo.shared.application.metadata.MetadataFactory


import java.util.concurrent.CompletableFuture

@CrossOrigin
@RestController
@RequestMapping("/dictionaryvaluetranslation")
class SetDictionaryValueTranslationResource(
    private val commandGateway: CommandGateway
) {
    @PreAuthorize("hasAuthority('*:*') or hasAuthority('set_dictionary_value_translation:execute')")
    @PostMapping("/setdictionaryvaluetranslation")
    fun SetDictionaryValueTranslation(
        @Valid @RequestBody command: SetDictionaryValueTranslationCommand,
        request: HttpServletRequest
    ): CompletableFuture<SetDictionaryValueTranslationCommand> =
        commandGateway.send(command, MetadataFactory.from(request)).resultMessage.thenApply { command }
}
