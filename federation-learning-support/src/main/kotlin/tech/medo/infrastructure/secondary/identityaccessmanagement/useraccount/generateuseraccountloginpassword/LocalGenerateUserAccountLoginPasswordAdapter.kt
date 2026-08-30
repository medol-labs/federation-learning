package tech.medo.infrastructure.secondary.identityaccessmanagement.useraccount.generateuseraccountloginpassword

import java.security.SecureRandom
import java.util.Base64
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordInput
import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordResult
import tech.medo.identityaccessmanagement.generateuseraccountloginpassword.GenerateUserAccountLoginPasswordService

@Component
class LocalGenerateUserAccountLoginPasswordAdapter(
    private val passwordEncoder: PasswordEncoder,
) : GenerateUserAccountLoginPasswordService {
    override fun supports(input: GenerateUserAccountLoginPasswordInput): Boolean = true

    override fun execute(input: GenerateUserAccountLoginPasswordInput): GenerateUserAccountLoginPasswordResult {
        val temporaryPassword = temporaryPassword()
        return GenerateUserAccountLoginPasswordResult.Succeeded(
            passwordHash = passwordEncoder.encode(temporaryPassword),
            temporaryPassword = temporaryPassword,
        )
    }

    private fun temporaryPassword(): String {
        val bytes = ByteArray(18)
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    companion object {
        private val secureRandom = SecureRandom()
    }
}
