package tech.medo.infrastructure.secondary.trainingorchestration.participantexecutionplan.dispatchparticipantexecutionplan

import org.aopalliance.intercept.MethodInterceptor
import org.springframework.aop.framework.ProxyFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanCommand
import tech.medo.trainingorchestration.dispatchparticipantexecutionplan.DispatchParticipantExecutionPlanCommandHandler
import tech.medo.trainingorchestration.domain.states.ParticipantExecutionPlanStateEnum
import tech.medo.trainingorchestration.participantexecutionplan.ParticipantExecutionPlanState

/** On Axon batch redelivery, do not send an already acknowledged execution plan to the agent again. */
@Component
class ExecutionPlanDispatchReplayGuard : BeanPostProcessor {
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        if (bean !is DispatchParticipantExecutionPlanCommandHandler) return bean
        return ProxyFactory(bean).apply {
            isProxyTargetClass = true
            addAdvice(MethodInterceptor { invocation ->
                val cmd = invocation.arguments.getOrNull(0) as? DispatchParticipantExecutionPlanCommand
                val state = invocation.arguments.getOrNull(1) as? ParticipantExecutionPlanState
                if (cmd != null && state?.currentState == ParticipantExecutionPlanStateEnum.PlanDispatched &&
                    state.executionPlanId == cmd.executionPlanId && state.executionSessionId == cmd.executionSessionId &&
                    state.runtimeId == cmd.runtimeId && state.roundId == cmd.roundId && state.trainingJobId == cmd.trainingJobId
                ) null else invocation.proceed()
            })
        }.proxy
    }
}
