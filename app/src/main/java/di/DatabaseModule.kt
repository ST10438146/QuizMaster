package di

import PendingEventDao
import QuestionDao
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import data.AttemptDao
import student.projects.quizmaster.data.MIGRATION_ANY_TO_2
import student.projects.quizmaster.data.QuizMasterDatabase
import student.projects.quizmaster.data.UserDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): QuizMasterDatabase {
        return Room.databaseBuilder(appContext, QuizMasterDatabase::class.java, "quizmaster_db")
            .addMigrations(MIGRATION_ANY_TO_2)
            .build()
    }

    @Provides
    fun provideQuestionDao(db: QuizMasterDatabase): QuestionDao = db.questionDao()

    @Provides
    fun provideAttemptDao(db: QuizMasterDatabase): AttemptDao = db.attemptDao()

    @Provides
    fun providePendingEventDao(db: QuizMasterDatabase): PendingEventDao = db.pendingEventDao()

    @Provides
    fun provideUserDao(db: QuizMasterDatabase): UserDao = db.userDao()
}