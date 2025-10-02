import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_events")
data class PendingEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,                // e.g., "progress", "purchase", "stat_update"
    val payloadJson: String,         // serialized event payload (JSON)
    val timestamp: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)