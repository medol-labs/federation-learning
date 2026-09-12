package tech.medo.iam.infrastructure.security

import java.util.UUID

interface AuthOrganizationResolver {
    fun resolveOrganizationId(userAccountId: UUID): UUID?
}
