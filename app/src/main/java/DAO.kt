import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Questions
 */
@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions WHERE category = :category LIMIT :limit")
    suspend fun getQuestionsByCategory(category: String, limit: Int = 10): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Query("DELETE FROM questions WHERE cachedAt < :timestamp")
    suspend fun deleteOldQuestions(timestamp: Long)

    @Query("SELECT * FROM questions")
    fun getAllQuestionsFlow(): Flow<List<QuestionEntity>>
}

/**
 * Data Access Object for Attempts
 */
@Dao
interface AttemptDao {

    @Insert
    suspend fun insertAttempt(attempt: AttemptEntity): Long

    @Query("SELECT * FROM attempts WHERE synced = 0")
    suspend fun getUnsyncedAttempts(): List<AttemptEntity>

    @Query("UPDATE attempts SET synced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: List<Long>)

    @Query("SELECT COUNT(*) FROM attempts WHERE isCorrect = 1")
    fun getCorrectAnswersCount(): Flow<Int>
}

/**
 * Data Access Object for Pending Events
 */
@Dao
interface PendingEventDao {

    @Insert
    suspend fun insertEvent(event: PendingEventEntity): Long

    @Query("SELECT * FROM pending_events WHERE synced = 0 ORDER BY timestamp ASC")
    suspend fun getUnsyncedEvents(): List<PendingEventEntity>

    @Query("UPDATE pending_events SET synced = 1 WHERE id = :id")
    suspend fun markEventAsSynced(id: Int)

    @Delete
    suspend fun deleteEvent(event: PendingEventEntity)
}