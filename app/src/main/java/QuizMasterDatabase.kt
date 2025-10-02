import AttemptDao
import PendingEventDao
import QuestionDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.Firebase

/**
 * Main Room database for offline storage
 */
val MIGRATION_INT_TO_LONG = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS attempts_new (
              id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              questionId TEXT NOT NULL,
              selectedIndex INTEGER NOT NULL,
              isCorrect INTEGER NOT NULL,
              timeSpentMs INTEGER NOT NULL,
              timestamp INTEGER NOT NULL,
              synced INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent())

        database.execSQL("""
            INSERT INTO attempts_new (id, questionId, selectedIndex, isCorrect, timeSpentMs, timestamp, synced)
            SELECT id, questionId, selectedIndex, isCorrect, timeSpentMs, timestamp, synced FROM attempts
        """.trimIndent())

        database.execSQL("DROP TABLE IF EXISTS attempts")
        database.execSQL("ALTER TABLE attempts_new RENAME TO attempts")
    }
}
@Database(
    entities = [QuestionEntity::class, AttemptEntity::class, PendingEventEntity::class],
    version = 2,
    exportSchema = false
)
abstract class QuizMasterDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun attemptDao(): AttemptDao
    abstract fun pendingEventDao(): PendingEventDao

    companion object {
        const val DATABASE_NAME: String = "quizmaster_database"

        @Volatile
        private var INSTANCE: QuizMasterDatabase? = null

        fun getDatabase(context: Context): QuizMasterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizMasterDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_INT_TO_LONG)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}