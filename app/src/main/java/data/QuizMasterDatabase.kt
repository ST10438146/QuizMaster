package student.projects.quizmaster.data

import PendingEventDao
import QuestionDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import student.projects.quizmaster.dao.SettingsDao
import data.AttemptDao
import data.AttemptEntity
import data.PendingEventEntity
import data.QuestionEntity
import data.UserEntity
import data.UserSettingsEntity

// MIGRATION_ANY_TO_2 stays here (you can keep same implementation)
val MIGRATION_ANY_TO_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // ... same migration SQL as you already have ...
    }
}

@Database(
    entities = [QuestionEntity::class, AttemptEntity::class, PendingEventEntity::class, UserEntity::class, UserSettingsEntity::class],
    version = 2,
    exportSchema = false
)
abstract class QuizMasterDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun attemptDao(): AttemptDao
    abstract fun pendingEventDao(): PendingEventDao
    abstract fun userDao(): UserDao
    abstract fun settingsDao(): SettingsDao


    companion object {
        const val DATABASE_NAME: String = "quizmaster_database"
        @Volatile private var INSTANCE: QuizMasterDatabase? = null

        fun getDatabase(context: Context): QuizMasterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, QuizMasterDatabase::class.java, DATABASE_NAME)
                    .addMigrations(MIGRATION_ANY_TO_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
