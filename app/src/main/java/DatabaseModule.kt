import android.content.Context
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
    fun provideDatabase(@ApplicationContext context: Context): QuizMasterDatabase {
        return QuizMasterDatabase.getDatabase(context)
    }

    @Provides
    fun provideQuestionDao(database: QuizMasterDatabase): QuestionDao {
        return database.questionDao()
    }

    @Provides
    fun provideAttemptDao(database: QuizMasterDatabase): AttemptDao {
        return database.attemptDao()
    }

    @Provides
    fun providePendingEventDao(database: QuizMasterDatabase): PendingEventDao {
        return database.pendingEventDao()
    }
}