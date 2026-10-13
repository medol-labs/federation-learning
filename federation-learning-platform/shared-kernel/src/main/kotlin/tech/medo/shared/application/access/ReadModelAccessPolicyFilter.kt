package tech.medo.shared.application.access

import org.springframework.data.jpa.domain.Specification

data class ReadModelAccessPolicy(
    val name: String?,
    val fields: List<String> = emptyList()
)

interface ReadModelAccessPolicyFilter {
    fun <T> specification(
        contextName: String,
        readModelName: String,
        policy: ReadModelAccessPolicy
    ): Specification<T>
}

class PermitAllReadModelAccessPolicyFilter : ReadModelAccessPolicyFilter {
    override fun <T> specification(
        contextName: String,
        readModelName: String,
        policy: ReadModelAccessPolicy
    ): Specification<T> = Specification.where(null)
}
