/**
 * Generic wrapper for API/database responses
 * Handles loading, success, and error states
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String?, throwable: Throwable? = null, data: T? = null)
        : Resource<T>(data, message, throwable)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}