package com.medaxis.app.domain.model

/**
 * Represents the urgency level determined by the medical AI triage service.
 */
enum class Urgency {
    EMERGENCY,
    URGENT,
    NON_URGENT
}

/**
 * A possible medical condition returned by the triage AI.
 */
data class ProbableCondition(
    val name: String,
    val description: String,
    val relevance: Float?, // optional relevance score (0-1)
    val supportingSymptoms: List<String>?,
    val specialty: String?
)

/**
 * Structured response from the medical AI triage service.
 */
data class TriageResponse(
    val summary: String?,
    val urgency: Urgency,
    val possibleConditions: List<ProbableCondition>,
    val recommendedSpecialty: String?,
    val supportingSymptoms: List<String>?,
    val advice: String?,
    val warningSigns: List<String>?,
    val disclaimer: String?
)
