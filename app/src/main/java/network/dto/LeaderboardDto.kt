package network.dto

data class LeaderboardDto(
    val period: String,
    val entries: List<LeaderboardEntryDto> = emptyList()
)
data class LeaderboardEntryDto(val uid: String, val username: String, val score: Int)
