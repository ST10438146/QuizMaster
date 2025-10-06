package student.projects.quizmaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.QuestionEntity

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions")@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)
}
