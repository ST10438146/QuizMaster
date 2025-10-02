
import java.text.SimpleDateFormat
import java.util.*

/**
 * Extension functions for common operations
 */

/**
 * Format timestamp to readable date string
 */
fun Long.toDateString(): String {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return dateFormat.format(Date(this))
}

/**
 * Format timestamp to readable time string
 */
fun Long.toTimeString(): String {
    val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(this))
}

/**
 * Check if a date is today
 */
fun Long.isToday(): Boolean {
    val calendar = Calendar.getInstance()
    val today = calendar.timeInMillis

    calendar.timeInMillis = this
    val compareDate = calendar.timeInMillis

    val todayDate = Calendar.getInstance().apply { timeInMillis = today }
    val compareCalendar = Calendar.getInstance().apply { timeInMillis = compareDate }

    return todayDate.get(Calendar.YEAR) == compareCalendar.get(Calendar.YEAR) &&
            todayDate.get(Calendar.DAY_OF_YEAR) == compareCalendar.get(Calendar.DAY_OF_YEAR)
}

/**
 * Calculate accuracy percentage
 */
fun calculateAccuracy(correct: Int, total: Int): Double {
    if (total == 0) return 0.0
    return (correct.toDouble() / total.toDouble()) * 100
}

/**
 * Format milliseconds to MM:SS
 */
fun Long.toTimerFormat(): String {
    val minutes = (this / 1000) / 60
    val seconds = (this / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}
