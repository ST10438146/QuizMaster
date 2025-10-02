import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val text: String,
    // Stored as JSON string or normalized fields depending on your DAO; using simple fields:
    val choicesJson: String = "[]", // if you store choices as JSON; otherwise create a relation table
    val correctIndex: Int = 0,
    val category: String = "",
    val difficulty: String = "medium",
    val language: String = "en",
    val explanation: String = "",
    val cachedAt: Long = System.currentTimeMillis()
)