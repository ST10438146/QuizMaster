/**
 * App-wide constants
 */
object Constants {
    // API
    const val BASE_URL = "https://your-api-url.com/api/v1/" // Replace with actual URL
    const val TIMEOUT_SECONDS = 30L

    // Database
    const val CACHE_EXPIRY_DAYS = 7

    // Sync
    const val SYNC_WORK_NAME = "quiz_sync_work"
    const val SYNC_INTERVAL_HOURS = 1L

    // Gamification
    const val XP_PER_CORRECT_ANSWER = 10
    const val XP_PER_STREAK = 5
    const val COINS_PER_CORRECT_ANSWER = 2
    const val COINS_PER_WIN = 10

    // Timer
    const val QUESTION_TIME_SECONDS = 30

    // Languages
    val SUPPORTED_LANGUAGES = listOf("en", "zu", "af") // English, isiZulu, Afrikaans

    // Categories
    val QUIZ_CATEGORIES = listOf(
        "General Knowledge",
        "Science",
        "History",
        "Geography",
        "Sports",
        "Entertainment",
        "Technology"
    )

    // Preferences
    const val PREF_BIOMETRIC_ENABLED = "biometric_enabled"
    const val PREF_LANGUAGE = "language"
    const val PREF_NOTIFICATIONS_ENABLED = "notifications_enabled"
    const val PREF_DATA_SAVER = "data_saver"
    const val PREF_THEME = "theme"
}
