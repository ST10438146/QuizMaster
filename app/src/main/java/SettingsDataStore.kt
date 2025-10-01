import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Delegate to create a DataStore instance
private val Context.dataStore by preferencesDataStore("user_settings")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val BIOMETRICS = booleanPreferencesKey("biometrics_enabled")
        // Add other settings keys here (e.g., KEY_NOTIFICATIONS)
    }

    // Flow to observe the current state of biometrics setting
    val biometricsEnabled: Flow<Boolean> =
        context.dataStore.data.map { it[BIOMETRICS] ?: false }

    // Suspending function to update the local setting
    suspend fun setBiometrics(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[BIOMETRICS] = enabled }
        // TODO: also update Firestore `/users/{uid}/settings` in the background [cite: 136]
    }
}