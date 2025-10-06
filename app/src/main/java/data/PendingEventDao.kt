package student.projects.quizmaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import data.PendingEventEntity

@Dao
interface PendingEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: PendingEventEntity)

    @Delete
    suspend fun deleteEvent(event: PendingEventEntity)

    @Query("SELECT * FROM pending_events")
    suspend fun getAllPendingEvents(): List<PendingEventEntity>

    @Query("DELETE FROM pending_events")
    suspend fun clearAll()
}
