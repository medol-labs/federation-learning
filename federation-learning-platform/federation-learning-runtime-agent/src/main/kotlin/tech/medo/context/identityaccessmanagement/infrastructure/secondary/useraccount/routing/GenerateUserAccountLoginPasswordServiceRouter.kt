package tech.medo.identityaccessmanagement.infrastructure.secondary.useraccount.routing

import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordInput
import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordService
import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordResult
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class GenerateUserAccountLoginPasswordServiceRouter(private val adapters: ObjectProvider<GenerateUserAccountLoginPasswordService>) : GenerateUserAccountLoginPasswordService {
    override fun supports(input: GenerateUserAccountLoginPasswordInput): Boolean = true

    override fun execute(input: GenerateUserAccountLoginPasswordInput): GenerateUserAccountLoginPasswordResult {
        val candidates = adapters.stream()
            .filter { it !== this }
            .filter { it.supports(input) }
            .toList()
        return when (candidates.size) {
            1 -> try {
                candidates.first().execute(input)
            } catch (ex: Exception) {
                throw ex
            }
            0 -> error("No GenerateUserAccountLoginPasswordService adapter supports the requested input.")
            else -> error("Multiple GenerateUserAccountLoginPasswordService adapters support the requested input.")
        }
    }
}
