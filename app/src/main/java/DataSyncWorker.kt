import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.IOException
import kotlinx.coroutines.coroutineScope
import network.QuizMasterApi
import network.dto.toDto


@HiltWorker
class DataSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val attemptDao: AttemptDao,
    private val pendingEventDao: PendingEventDao,
    private val api: QuizMasterApi
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val unsyncedAttempts = attemptDao.getUnsyncedAttempts()
            if (unsyncedAttempts.isNotEmpty()) {
                val response = api.syncAttempts(unsyncedAttempts.map { it.toDto() })
                if (response.isSuccessful) {
                    val attemptIds = unsyncedAttempts.map { it.id }
                    attemptDao.markAsSynced(attemptIds)
                } else if (response.code() in 400..499) {
                    return@coroutineScope Result.failure()
                } else {
                    return@coroutineScope Result.retry()
                }
            }

            val unsyncedEvents = pendingEventDao.getUnsyncedEvents()
            if (unsyncedEvents.isNotEmpty()) {
                // TODO: call api.syncEvent or batch endpoint, then mark events as synced or delete them
                // Example placeholder:
                // val eventResponse = api.syncEvents(unsyncedEvents.map { it.network.dto.toDto() })
                // if (eventResponse.isSuccessful) mark events as synced...
            }

            Result.success()
        } catch (ioe: IOException) {
            Result.retry()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
