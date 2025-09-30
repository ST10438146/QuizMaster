import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Local cache for questions (offline support)
 */
@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val text: String,
    val choices: String, // JSON string of list
    val correctIndex: Int,
    val category: String,
    val difficulty: String,
    val language: String,
    val explanation: String,
    val cachedAt: Long = System.currentTimeMillis()
)

/**
 * Store user attempts for sync
 */
@Entity(tableName = "attempts")
data class AttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionId: String,
    val selectedIndex: Int,
    val isCorrect: Boolean,
    val timestamp: Long,
    val synced: Boolean = false
)

/**
 * Pending events to sync when online
 */
@Entity(tableName = "pending_events")
data class PendingEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventType: String, // "answer_submitted", "match_completed", etc.
    val eventData: String, // JSON string
    val timestamp: Long,
    val synced: Boolean = false
)