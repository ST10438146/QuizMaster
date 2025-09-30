import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDAO {
    @Query("SELECT * FROM questions WHERE category = :category LIMIT :limit")
    suspend fun getQuestionsByCategory(category: String, limit: Int = 10): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)
}
