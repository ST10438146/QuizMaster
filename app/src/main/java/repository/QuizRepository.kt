package repository

import NetworkMonitor
import QuestionDao
import data.PendingEventEntity
import network.QuizMasterApi
import student.projects.quizmaster.data.PendingEventDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepository @Inject constructor(
    private val api: QuizMasterApi,
    private val questionDao: QuestionDao,
    private val pendingEventDao: PendingEventDao,
    private val networkMonitor: NetworkMonitor // Or whatever your connectivity check class is named
) {




    /** Queue an event (gameplay / progress) for later sync */
    suspend fun queueEvent(event: PendingEventEntity) {
        pendingEventDao.insertEvent(event)
    }

    // --- CLOUD-ONLY CALLS (NO LOCAL CACHING) ---

   }