data class Match(
    val matchId: String = "",
    val players: List<String> = emptyList(),
    val questionIds: List<String> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val scores: Map<String, Int> = emptyMap(),
    val state: String = "waiting", // waiting, active, completed
    val createdAt: Long = System.currentTimeMillis()
)