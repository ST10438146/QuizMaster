import retrofit2.Response
import retrofit2.http.*

/**
 * REST API service interface
 */
interface QuizMasterApi {

    @GET("questions")
    suspend fun getQuestions(
        @Query("category") category: String,
        @Query("difficulty") difficulty: String,
        @Query("limit") limit: Int = 10
    ): Response<List<Question>>

    @POST("matches/create")
    suspend fun createMatch(
        @Body matchRequest: MatchRequest
    ): Response<Match>

    @POST("matches/{matchId}/submit")
    suspend fun submitAnswer(
        @Path("matchId") matchId: String,
        @Body answerRequest: AnswerRequest
    ): Response<AnswerResponse>

    @GET("leaderboard")
    suspend fun getLeaderboard(
        @Query("period") period: String = "weekly"
    ): Response<List<LeaderboardEntry>>

    @POST("user/stats")
    suspend fun updateUserStats(
        @Body statsUpdate: StatsUpdate
    ): Response<User>

    fun syncAttempts(unsyncedAttempts: List<AttemptEntity>)
}

// API Request/Response models
data class MatchRequest(val category: String, val mode: String = "solo")
data class AnswerRequest(val questionId: String, val selectedIndex: Int, val timeSpent: Int)
data class AnswerResponse(val isCorrect: Boolean, val xpEarned: Int, val coinsEarned: Int)
data class StatsUpdate(val xpGained: Int, val coinsGained: Int)