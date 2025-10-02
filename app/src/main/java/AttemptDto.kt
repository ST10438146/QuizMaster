data class AttemptDto(
    val id: Long? = null,
    val questionId: String,
    val selectedIndex: Int,
    val isCorrect: Boolean,
    val timeSpentMs: Long,
    val timestamp: Long
)

// Extension mapper
fun AttemptEntity.toDto(): AttemptDto {
    return AttemptDto(
        id = id,
        questionId = questionId,
        selectedIndex = selectedIndex,
        isCorrect = isCorrect,
        timeSpentMs = timeSpentMs,
        timestamp = timestamp
    )
}
