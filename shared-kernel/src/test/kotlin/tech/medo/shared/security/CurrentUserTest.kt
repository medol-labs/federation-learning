package tech.medo.shared.security

import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CurrentUserTest {
    @Test
    fun `checks permission code membership`() {
        val user = CurrentUser(
            id = UUID.randomUUID(),
            username = "alice",
            organizationId = UUID.randomUUID(),
            roles = setOf("ADMIN"),
            permissions = setOf("federation:manage", "training:read"),
        )

        assertThat(user.hasPermission("federation:manage")).isTrue()
        assertThat(user.hasPermission("dataset:manage")).isFalse()
    }

    @Test
    fun `anonymous user has no roles or permissions`() {
        val anonymous = CurrentUser.anonymous()

        assertThat(anonymous.username).isEqualTo("anonymous")
        assertThat(anonymous.organizationId).isNull()
        assertThat(anonymous.roles).isEmpty()
        assertThat(anonymous.permissions).isEmpty()
    }
}
