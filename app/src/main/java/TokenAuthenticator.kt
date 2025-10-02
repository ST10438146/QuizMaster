import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

/**
 * OkHttp Authenticator that tries to refresh the Firebase token once on 401.
 * Uses synchronous read afterwards from cached provider.
 */
class TokenAuthenticator @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val tokenProvider: FirebaseTokenProvider
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null
        return try {
            val newToken = runBlocking {
                firebaseAuth.currentUser?.getIdToken(true)?.await()?.token
            }
            if (!newToken.isNullOrEmpty()) {
                response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            } else null
        } catch (e: Exception) {
            null
        }
    }

    private fun responseCount(response: Response): Int {
        var res = response.priorResponse
        var result = 1
        var r = res
        while (r != null) {
            result += 1
            r = r.priorResponse
        }
        return result
    }
}