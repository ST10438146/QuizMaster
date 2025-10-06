package student.projects.quizmaster.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.UserSettingsEntity

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: UserSettingsEntity)

    @Query("SELECT * FROM user_settings WHERE uid = :uid")
    suspend fun getSettings(uid: String): UserSettingsEntity?
}