import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import java.io.IOException

/**
 * SyncWorker handles the upload of all local, unsynced gameplay data to the cloud.
 * This fulfills the PixelPartners requirement for "offline with sync."
 */
// @HiltWorker is required for injecting DAOs and the API service
@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val attemptDao: AttemptDao,
    private val pendingEventDao: PendingEventDao,
    private val api: QuizMasterApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = coroutineScope {
        // ... (existing implementation as provided in the previous step) ...
        try {
            // 1. Sync attempts
            val attempts = attemptDao.getUnsyncedAttempts()


            if (attempts.isNotEmpty()) {
                val dtos = attempts.map { it.toDto() } // uses AttemptEntity.toDto()
                val response = api.syncAttempts(dtos)
                if (response.isSuccessful) {
                    val attemptIds = attempts.map { it.id }
                    attemptDao.markAsSynced(attemptIds)
                } else if (response.code() in 400..499) {

                }
                return@coroutineScope Result.failure()
            }

            // 2. Sync pending events
            TODO("Sync pending events")
            val events = pendingEventDao.getUnsyncedEvents()
            Result.success()
        } catch (e: IOException) {
            Result.retry()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}