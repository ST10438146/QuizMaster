package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AttemptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: AttemptEntity): Long

    @Query("SELECT * FROM attempts WHERE synced = 0")
    suspend fun getUnsyncedAttempts(): List<AttemptEntity>

    @Query("UPDATE attempts SET synced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<Long>)

    @Query("SELECT * FROM attempts ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentAttempts(limit: Int = 50): Flow<List<AttemptEntity>>

    @Query("SELECT COUNT(*) FROM attempts WHERE isCorrect = 1")
    fun getCorrectAnswersCount(): Flow<Int>
}