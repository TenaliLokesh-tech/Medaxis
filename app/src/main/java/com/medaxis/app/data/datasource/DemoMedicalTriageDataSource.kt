package com.medaxis.app.data.datasource

import com.medaxis.app.domain.model.Urgency
import com.medaxis.app.domain.model.ProbableCondition
import com.medaxis.app.domain.model.TriageResponse
import com.medaxis.app.data.remote.medical.model.TriageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Demo implementation that returns deterministic but varied triage responses based on simple keyword matching.
 * This is used when BuildConfig.USE_DEMO_MODE = true.
 */
class DemoMedicalTriageDataSource : MedicalTriageDataSource {
    override suspend fun getTriage(symptomText: String): TriageResponse = withContext(Dispatchers.Default) {
        val normalized = symptomText.lowercase().trim()
        // Simple keyword groups for demo conditions
        val conditions = mutableListOf<ProbableCondition>()
        var urgency = Urgency.NON_URGENT
        if (listOf("chest pain", "shortness of breath", "difficulty breathing").any { normalized.contains(it) }) {
            urgency = Urgency.EMERGENCY
            conditions.add(
                ProbableCondition(
                    name = "Possible Cardiac Issue",
                    description = "Chest pain and breathing difficulty may indicate a heart problem.",
                    relevance = 0.9f,
                    supportingSymptoms = listOf("chest pain", "shortness of breath"),
                    specialty = "Cardiology"
                )
            )
        }
        if (listOf("headache", "migraine", "light sensitivity").any { normalized.contains(it) }) {
            if (urgency == Urgency.NON_URGENT) urgency = Urgency.URGENT
            conditions.add(
                ProbableCondition(
                    name = "Migraine",
                    description = "Severe headache often worsened by light.",
                    relevance = 0.8f,
                    supportingSymptoms = listOf("headache", "light sensitivity"),
                    specialty = "Neurology"
                )
            )
        }
        if (listOf("fever", "sore throat").any { normalized.contains(it) }) {
            conditions.add(
                ProbableCondition(
                    name = "Viral Upper Respiratory Infection",
                    description = "Common viral infection causing fever and sore throat.",
                    relevance = 0.7f,
                    supportingSymptoms = listOf("fever", "sore throat"),
                    specialty = "General Medicine"
                )
            )
        }
        if (listOf("rash", "itchy", "red").any { normalized.contains(it) }) {
            conditions.add(
                ProbableCondition(
                    name = "Dermatitis",
                    description = "Skin inflammation causing itchy red rash.",
                    relevance = 0.75f,
                    supportingSymptoms = listOf("rash", "itchy", "red"),
                    specialty = "Dermatology"
                )
            )
        }
        if (listOf("stomach", "vomit", "abdominal").any { normalized.contains(it) }) {
            conditions.add(
                ProbableCondition(
                    name = "Gastroenteritis",
                    description = "Stomach upset with vomiting.",
                    relevance = 0.78f,
                    supportingSymptoms = listOf("stomach pain", "vomiting"),
                    specialty = "Gastroenterology"
                )
            )
        }
        // Fallback generic condition if none matched
        if (conditions.isEmpty()) {
            conditions.add(
                ProbableCondition(
                    name = "General Consultation",
                    description = "Symptoms are non-specific; consider a general medical evaluation.",
                    relevance = 0.5f,
                    supportingSymptoms = emptyList(),
                    specialty = "General Medicine"
                )
            )
        }
        TriageResponse(
            summary = "Based on the provided symptoms, the following possible explanations are suggested.",
            urgency = urgency,
            possibleConditions = conditions,
            recommendedSpecialty = conditions.firstOrNull()?.specialty,
            supportingSymptoms = conditions.flatMap { it.supportingSymptoms ?: emptyList() }.distinct(),
            advice = "If symptoms worsen or new warning signs appear, seek medical care promptly.",
            warningSigns = if (urgency == Urgency.EMERGENCY) listOf("Chest pain", "Severe shortness of breath") else emptyList(),
            disclaimer = "This is not a diagnosis. Please consult a healthcare professional for proper evaluation."
        )
    }
}
