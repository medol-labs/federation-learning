package tech.medo.infrastructure.primary.enrollment

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
class RuntimeAgentEnrollmentFilter(private val enrollment: RuntimeAgentEnrollmentStatus) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val path = request.requestURI.removePrefix(request.contextPath)
        if (enrollment.configured() || path == "/actuator/health" || path.startsWith("/actuator/health/") || request.method == "OPTIONS") {
            chain.doFilter(request, response)
            return
        }
        response.status = HttpServletResponse.SC_SERVICE_UNAVAILABLE
        response.contentType = "application/problem+json"
        response.writer.write("""{"title":"Runtime agent is awaiting configuration","status":503,"code":"WAITING_FOR_CONFIGURATION","detail":"Register the runtime infrastructure, configure identity and credentials, then restart the agent."}""")
    }
}
