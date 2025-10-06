package network.dto

import data.AttemptEntity

data class AttemptDto(
    val id: Long? = null,
    val userId: String? = null,
    val questionId: String,
    val selectedIndex: Int,
    val isCorrect: Boolean,
    val timeSpentMs: Long,
    val timestamp: Long
)

fun AttemptEntity.toDto(): AttemptDto {
    return AttemptDto(
        id = if (id == 0L) null else id,
        userId = userId,
        questionId = questionId,
        selectedIndex = selectedIndex,
        isCorrect = isCorrect,
        timeSpentMs = timeSpentMs,
        timestamp = timestamp
    )
}
