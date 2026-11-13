package tech.medo.infrastructure.primary.enrollment

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import tech.medo.infrastructure.secondary.runtimeagentoperations.runtimeagentlifecycle.loadruntimeagentbootstrapconfiguration.RuntimeAgentBootstrapConfigurationProperties
import tech.medo.infrastructure.secondary.runtimeagentoperations.agentruntimeinfrastructureconnection.reportruntimeinstanceconnected.RuntimePlatformConnectionReportingProperties
import tech.medo.shared.security.MedolSecurityProperties

class RuntimeAgentEnrollmentTest {
    private fun status(configured: Boolean = false, token: String = "test-token") = RuntimeAgentEnrollmentStatus(
        RuntimeAgentBootstrapConfigurationProperties(
            runtimeAgentId = if (configured) "11111111-1111-4111-8111-111111111111" else null,
            runtimeInfrastructureId = if (configured) "22222222-2222-4222-8222-222222222222" else null,
            runtimeAgentEndpoint = "http://agent:8082"
        ), RuntimePlatformConnectionReportingProperties(), MedolSecurityProperties().apply { internalToken = token }
    )

    @Test fun pendingAgentRejectsBusinessRequestsButKeepsHealthAvailable() {
        val enrollment = status()
        assertFalse(enrollment.configured())
        assertEquals("WAITING_FOR_CONFIGURATION", enrollment.health().details["enrollmentStatus"])
        val filter = RuntimeAgentEnrollmentFilter(enrollment)
        val response = MockHttpServletResponse()
        filter.doFilter(MockHttpServletRequest("POST", "/roundexecution/acceptexecutionplan"), response) { _, _ -> fail("Must not accept work") }
        assertEquals(503, response.status)
        var healthReached = false
        filter.doFilter(MockHttpServletRequest("GET", "/actuator/health"), MockHttpServletResponse()) { _, _ -> healthReached = true }
        assertTrue(healthReached)
    }

    @Test fun configuredAgentContinuesThroughNormalAuthentication() {
        var reached = false
        RuntimeAgentEnrollmentFilter(status(true)).doFilter(MockHttpServletRequest("POST", "/roundexecution/acceptexecutionplan"), MockHttpServletResponse()) { _, _ -> reached = true }
        assertTrue(reached)
        assertFalse(status(true, "").configured())
    }
}
