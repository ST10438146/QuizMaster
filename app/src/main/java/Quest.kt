
/**
 * Domain model for quests (daily/weekly challenges)
 */
data class Quest(
    val id: String,
    val title: String,
    val description: String,
    val type: QuestType,
    val targetValue: Int,
    val currentProgress: Int,
    val rewardXp: Int,
    val rewardCoins: Int,
    val expiresAt: Long,
    val completed: Boolean = false
)

enum class QuestType {
    DAILY, WEEKLY, SEASONAL
}
