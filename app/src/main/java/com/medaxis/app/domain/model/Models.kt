package com.medaxis.app.domain.model

/**
 * Represents a probable disease returned by the symptom‑checking API.
 */
data class ProbableDisease(
    val name: String,
    val accuracyPercentage: Int,
    val simpleDescription: String
)

/**
 * Represents a healthcare facility that can be shown in the Directory screen.
 *
 * @property id Unique identifier (could be a place‑id from the real API).
 * @property name Human readable name of the hospital/clinic.
 * @property distance Human readable distance string, e.g. "3.2 km away".
 * @property address Full street address.
 * @property specialty Primary specialty or service offered.
 * @property isOpen Whether the facility is currently open.
 */
data class Hospital(
    val id: String,
    val name: String,
    val distance: String,
    val address: String,
    val specialty: String,
    val isOpen: Boolean
)
