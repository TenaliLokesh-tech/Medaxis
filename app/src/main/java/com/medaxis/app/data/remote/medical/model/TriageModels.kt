package com.medaxis.app.data.remote.medical.model

import com.medaxis.app.domain.model.Urgency


/** Request model sent to the medical AI triage service */
data class TriageRequest(
    val symptomText: String
)

/** Represents a possible condition returned by the AI */
data class ProbableCondition(
    val name: String,
    val simpleDescription: String,
    val confidence: Float? = null,
    val supportingSymptoms: List<String>? = null
)

/** Overall response from the AI triage service */
data class TriageResponse(
    val possibleConditions: List<ProbableCondition>,
    val explanation: String?,
    val recommendedSpecialty: String?,
    val urgency: Urgency,
    val supportingSymptoms: List<String>? = null
)
