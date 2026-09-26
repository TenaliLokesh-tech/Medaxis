package com.medaxis.app.data.local.profile

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Data class representing a simple user profile */
data class UserProfile(
    val name: String,
    val phoneNumber: String
)

/** Repository that persists the user profile using DataStore (Preferences). */
class UserProfileRepository(private val context: Context) {
    private val Context.profileDataStore by preferencesDataStore(name = "user_profile")

    companion object {
        private val NAME_KEY = stringPreferencesKey("profile_name")
        private val PHONE_KEY = stringPreferencesKey("profile_phone")
    }

    /** Flow emitting the stored profile, or null if none saved. */
    val profileFlow: Flow<UserProfile?> = context.profileDataStore.data.map { prefs ->
        val name = prefs[NAME_KEY]
        val phone = prefs[PHONE_KEY]
        if (name != null && phone != null) UserProfile(name, phone) else null
    }

    /** Save a new profile. */
    suspend fun saveProfile(profile: UserProfile) {
        context.profileDataStore.edit { prefs ->
            prefs[NAME_KEY] = profile.name
            prefs[PHONE_KEY] = profile.phoneNumber
        }
    }

    /** Clear the stored profile. */
    suspend fun clearProfile() {
        context.profileDataStore.edit { prefs ->
            prefs.remove(NAME_KEY)
            prefs.remove(PHONE_KEY)
        }
    }
}
