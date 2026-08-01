package tech.medo.datasetgovernance.definefeatureschema

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import tech.medo.datasetgovernance.definefeatureschema.DefineFeatureSchemaCommand

import tech.medo.datasetgovernance.featureschema.FeatureSchemaFeatureDomainVersionReservationState
import tech.medo.datasetgovernance.events.FeatureSchemaFeatureDomainVersionReservedEvent


import java.util.UUID;
import tech.medo.datasetgovernance.domain.types.FeatureDefinition;
import tech.medo.datasetgovernance.domain.types.LabelDefinition;


class DefineFeatureSchemaDecisionTest {
    @Test
    fun RejectDuplicateFeatureSchemaVersion() {
        val featureSchemaFeatureDomainVersionReservation = FeatureSchemaFeatureDomainVersionReservationState()
        featureSchemaFeatureDomainVersionReservation.evolve(
            FeatureSchemaFeatureDomainVersionReservedEvent(
                featureSchemaId = java.util.UUID.randomUUID(),
                featureDomain = "CreditRisk",
                version = "1.0.0",
                normalizedFeatureDomain = "CreditRisk".trim().lowercase(),
                normalizedVersion = "1.0.0".trim().lowercase()
            )
        )

        assertThrows<IllegalArgumentException> {
            (object : DefineFeatureSchemaDecision {}).decide(
                        DefineFeatureSchemaCommand(
                        featureSchemaId = java.util.UUID.randomUUID(),
                        featureDomain = "CreditRisk",
                        version = "1.0.0",
                        dataModality = "",
                        features = emptyList(),
                        labels = emptyList()
                        ),
                            featureSchemaFeatureDomainVersionReservation = featureSchemaFeatureDomainVersionReservation
                    )
        }
    }
}
