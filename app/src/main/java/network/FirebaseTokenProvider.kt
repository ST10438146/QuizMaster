package network

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Provides the current Firebase ID token asynchronously for authenticated API calls.
 */
class FirebaseTokenProvider @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    // Fetches the current user's ID token. Returns null if user is not logged in.
    suspend fun getIdToken(): String? {
        // Use Kotlin coroutines await() for a synchronous feel within the interceptor
        return try {
            firebaseAuth.currentUser?.getIdToken(false)?.await()?.token
        } catch (e: Exception) {
            // Log the error (e.g., token refresh failed, user session expired)
            e.printStackTrace()
            null
        }
    }
    // Convenience blocking helper for use on background threads (e.g., Interceptor)
    fun getIdTokenBlocking(): String? = runBlocking { getIdToken() }
}