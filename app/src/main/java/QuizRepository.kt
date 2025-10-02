import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

// Import necessary models/utilities


@Singleton
class QuizRepository @Inject constructor(
    private val api: QuizMasterApi,
    private val questionDao: QuestionDao,
    private val attemptDao: AttemptDao,
    private val pendingEventDao: PendingEventDao,
    private val networkMonitor: NetworkMonitor // Or whatever your connectivity check class is named
) {



    /** Attempts: record locally, later synced by SyncWorker */
    suspend fun saveAttemptLocally(attempt: AttemptEntity) {
        attemptDao.insertAttempt(attempt)
    }

    /** Queue an event (gameplay / progress) for later sync */
    suspend fun queueEvent(event: PendingEventEntity) {
        pendingEventDao.insertEvent(event)
    }

    // --- CLOUD-ONLY CALLS (NO LOCAL CACHING) ---

   }