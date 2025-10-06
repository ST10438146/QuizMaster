package network

import network.dto.AttemptDto
import network.dto.ProfileDto
import Question
import QuestionDao
import network.dto.QuestsDto
import User
import android.service.autofill.FieldClassification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import student.projects.quizmaster.data.PendingEventDao
import vcmsa.projects.quizmaster.LeaderboardFragment

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
    ): Response<FieldClassification.Match>

    @POST("matches/{matchId}/submit")
    suspend fun submitAnswer(
        @Path("matchId") matchId: String,
        @Body answerRequest: AnswerRequest
    ): Response<AnswerResponse>

    @GET("leaderboard")
    suspend fun getLeaderboard(
        @Query("period") period: String = "weekly"
    ): Response<List<LeaderboardFragment.LeaderboardEntry>>

    @POST("user/stats")
    suspend fun updateUserStats(
        @Body statsUpdate: StatsUpdate
    ): Response<User>

    @POST("sync/attempts")
    suspend fun syncAttempts(@Body attempts: List<AttemptDto>): Response<Unit>

    @GET("questions")
    suspend fun getQuestions(): Response<List<QuestionDao>>


    @GET("quests")
    suspend fun getQuests(): Response<QuestsDto>

    @GET("user/{uid}")
    suspend fun getUserProfile(@Path("uid") uid: String): Response<ProfileDto>

    @POST("sync/event")
    suspend fun syncEvent(@Body event: PendingEventDao): Response<Unit>
}

// API Request/Response models
data class MatchRequest(val category: String, val mode: String = "solo")
data class AnswerRequest(val questionId: String, val selectedIndex: Int, val timeSpent: Int)
data class AnswerResponse(val isCorrect: Boolean, val xpEarned: Int, val coinsEarned: Int)
data class StatsUpdate(val xpGained: Int, val coinsGained: Int)