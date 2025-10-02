data class QuestsDto(val quests: List<QuestDto> = emptyList())
data class QuestDto(val id: String, val title: String, val description: String)