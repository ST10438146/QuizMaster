data class User(
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val language: String = "en",
    val country: String = "ZA",
    val xp: Int = 0,
    val level: Int = 1,
    val coins: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)