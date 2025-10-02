data class PendingEventDto(
    val id: Long? = null,
    val type: String,
    val payload: Map<String, Any> = emptyMap(),
    val timestamp: Long
)