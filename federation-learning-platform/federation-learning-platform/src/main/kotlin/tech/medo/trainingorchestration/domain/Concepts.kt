package tech.medo.trainingorchestration.domain

object Concepts {
    data object RuntimeEngineProfile {
        const val NAME = "RuntimeEngineProfile"
        val slices = listOf("RegisterRuntimeEngineProfile", "RuntimeEngineProfileCatalog")
        val states = listOf("Registered")
    }

    data object TrainingRunConfiguration {
        const val NAME = "TrainingRunConfiguration"
        val slices = listOf("DefineTrainingRunConfiguration", "UpdateTrainingRunConfiguration", "LockTrainingRunConfiguration", "TrainingRunConfigurationCatalog")
        val states = listOf("Draft", "Locked")
    }

    data object TrainingJob {
        const val NAME = "TrainingJob"
        val slices = listOf("CreateTrainingJob", "SubmitTrainingJob", "PauseTrainingJob", "ResumeTrainingJob", "CancelTrainingJob", "ScheduleNextTrainingRound", "CompleteTrainingJob", "TrainingJobDashboard", "TrainingParticipantEligibility")
        val states = listOf("Draft", "Submitted", "Running", "Paused", "Canceled", "Completed")
    }

    data object TrainingRound {
        const val NAME = "TrainingRound"
        val slices = listOf("SelectTrainingRoundParticipants", "RetryTrainingRoundParticipantSelection", "StartTrainingRound", "SubmitModelUpdateSubmission", "EvaluateModelUpdateSubmission", "AggregatePlainModelUpdates", "RequestSecureAggregation", "CompleteModelAggregation", "SubmitGlobalModelEvaluation", "CompleteTrainingRound", "FailTrainingRound", "TrainingRoundProgress")
        val states = listOf("ParticipantsSelected", "Running", "CollectingUpdates", "Aggregating", "EvaluatingGlobalModel", "Completed", "Failed")
    }

    data object ParticipantExecutionPlan {
        const val NAME = "ParticipantExecutionPlan"
        val slices = listOf("GenerateParticipantExecutionPlan", "DispatchParticipantExecutionPlan")
        val states = listOf("PlanGenerated", "PlanDispatched")
    }
}
