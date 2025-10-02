import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency injection module for database
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): QuizMasterDatabase {
        return Room.databaseBuilder(appContext, QuizMasterDatabase::class.java, "quizmaster_db")
            .addMigrations(MIGRATION_INT_TO_LONG)
            .build()
    }

    @Provides
    fun provideAttemptDao(db: QuizMasterDatabase): AttemptDao = db.attemptDao()
}
