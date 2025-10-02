import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuizMasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // ✅ REQUIRED ACTION: Schedule the SyncWorker on application start.
        scheduleSyncWorker()
    }

    private fun scheduleSyncWorker() {
        // Constraints: Only run when a network connection is present
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Create a request for the SyncWorker
        val syncWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        // Enqueue the request, ensuring only one instance is pending at a time
        WorkManager.getInstance(this).enqueueUniqueWork(
            "SyncPendingEvents",
            ExistingWorkPolicy.KEEP,
            syncWorkRequest
        )

    }
}