package tech.medo.systemnotification.recipientnotificationacknowledgement



data class RecipientNotificationAcknowledgementSelection(
    val recipientUserId: String
)

object RecipientNotificationAcknowledgementTags {
    const val RECIPIENT_USER_ID = "recipientUserId"
}

object RecipientNotificationAcknowledgementMetadata {
    val concepts = listOf("RecipientNotificationAcknowledgement")
}
