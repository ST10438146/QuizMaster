import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Intercepts outgoing requests to add the Firebase ID token in the Authorization header.
 * Uses runBlocking because OkHttp interceptors are synchronous.
 */
class AuthInterceptor @Inject constructor(
    private val tokenProvider: FirebaseTokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Synchronously fetch the token using runBlocking on the token provider
        val token = runBlocking {
            tokenProvider.getIdToken()
        }

        return if (token != null) {
            // Add Authorization header: Bearer [TOKEN]
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            // Proceed with the original request (might be an unauthenticated call like login/register)
            // or return an unauthorized response if it's a critical path.
            // For now, we proceed to allow the API to handle unauthorized access.
            chain.proceed(originalRequest)
        }
    }
}