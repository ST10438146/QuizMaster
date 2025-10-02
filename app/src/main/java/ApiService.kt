package student.projects.quizmaster0.data.remote

import AttemptDto
import LeaderboardDto
import PendingEventDto
import ProfileDto
import QuestionDto
import QuestsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("questions")
    suspend fun getQuestions(): Response<List<QuestionDto>>

    @GET("leaderboard/{period}")
    suspend fun getLeaderboard(@Path("period") period: String): Response<LeaderboardDto>

    @GET("quests")
    suspend fun getQuests(): Response<QuestsDto>

    @GET("user/{uid}")
    suspend fun getUserProfile(@Path("uid") uid: String): Response<ProfileDto>

    @POST("sync/attempts")
    suspend fun syncAttempts(@Body attempts: List<AttemptDto>): Response<Unit>

    @POST("sync/event")
    suspend fun syncEvent(@Body event: PendingEventDto): Response<Unit>
}