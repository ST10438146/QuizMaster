import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Main Room database for offline storage
 */
@Database(
    entities = [QuestionEntity::class, AttemptEntity::class, PendingEventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QuizMasterDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun attemptDao(): AttemptDao
    abstract fun pendingEventDao(): PendingEventDao

    companion object {
        @Volatile
        private var INSTANCE: QuizMasterDatabase? = null

        /**
         * Get database instance (singleton pattern)
         */
        fun getDatabase(context: Context): QuizMasterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizMasterDatabase::class.java,
                    "quizmaster_database"
                )
                    .fallbackToDestructiveMigration() // For development
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}