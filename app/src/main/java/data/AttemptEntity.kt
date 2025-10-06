package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attempts")
data class AttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val userId: String? = null,
    val questionId: String,
    val selectedIndex: Int,
    val isCorrect: Boolean,
    val timeSpentMs: Long,
    val timestamp: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)