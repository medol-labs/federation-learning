package tech.medo.shared.security

import jakarta.servlet.DispatcherType
import jakarta.servlet.FilterChain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

class InternalTokenAuthenticationFilterTest {
    @AfterEach
    fun clearSecurityContext() {
        SecurityContextHolder.clearContext()
    }

    @Test
    fun `authenticates internal token during async dispatch`() {
        val properties = MedolSecurityProperties(internalToken = "service-token")
        val request = MockHttpServletRequest().apply {
            dispatcherType = DispatcherType.ASYNC
            addHeader(InternalTokenAuthenticationFilter.INTERNAL_TOKEN_HEADER, "service-token")
        }
        val response = MockHttpServletResponse()
        var authentication: Authentication? = null

        InternalTokenAuthenticationFilter(properties).doFilter(
            request,
            response,
            FilterChain { _, _ -> authentication = SecurityContextHolder.getContext().authentication },
        )

        assertThat(authentication).isNotNull
        assertThat(authentication?.principal)
            .isEqualTo(
                CurrentUser(
                    id = java.util.UUID.fromString(properties.internalSubject),
                    username = properties.internalUsername,
                    organizationId = null,
                    roles = properties.internalRoles.toSet(),
                    permissions = properties.internalPermissions.toSet(),
                ),
            )
    }
}
