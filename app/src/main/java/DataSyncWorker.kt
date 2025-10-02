import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.IOException
import kotlinx.coroutines.coroutineScope


@HiltWorker
class DataSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val attemptDao: AttemptDao,
    // FIX 1: Inject PendingEventDao, required for the sync architecture
    private val pendingEventDao: PendingEventDao,
    private val api: QuizMasterApi
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            // 1. Sync Attempts
            val unsyncedAttempts = attemptDao.getUnsyncedAttempts()

            if (unsyncedAttempts.isNotEmpty()) {
                val response = api.syncAttempts(unsyncedAttempts.map { it.toDto() })

                if (response.isSuccessful) {
                    val attemptIds = unsyncedAttempts.map { it.id }
                    // FIX 2: Use the corrected DAO method: markAsSynced
                    attemptDao.markAsSynced(attemptIds)
                } else if (response.code() in 400..499) {
                    // Client error (e.g., bad data) - Do not retry.
                    return@coroutineScope Result.failure()
                } else return@coroutineScope Result.retry()
            }

            // 2. Sync Pending Events (Required Logic)
            val unsyncedEvents = pendingEventDao.getUnsyncedEvents()


            Result.success()

        } catch (e: IOException) {
            Result.retry() // Retry on network failure
        } catch (e: Exception) {
            Result.failure() // Fail on data/serialization errors
        }
    }
}