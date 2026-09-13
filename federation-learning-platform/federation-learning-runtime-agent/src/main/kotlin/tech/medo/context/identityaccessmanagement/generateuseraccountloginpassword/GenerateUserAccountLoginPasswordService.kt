package tech.medo.identityaccessmanagement.generateuseraccountloginpassword

import java.util.UUID;

interface GenerateUserAccountLoginPasswordService {
    fun supports(input: GenerateUserAccountLoginPasswordInput): Boolean = true
    fun execute(input: GenerateUserAccountLoginPasswordInput): GenerateUserAccountLoginPasswordResult
}

data class GenerateUserAccountLoginPasswordInput(
    val userAccountId: UUID,
    val passwordResetRequired: Boolean
)

sealed interface GenerateUserAccountLoginPasswordResult {
    data class Succeeded(
        val passwordHash: String,
        val temporaryPassword: String
    ) : GenerateUserAccountLoginPasswordResult


}
