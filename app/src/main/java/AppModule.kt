import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Hilt module for app-wide dependencies
 * Provides singleton instances of database, network, and Firebase services
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //Provides the FirebaseTokenProvider, which is used by the AuthInterceptor
    @Provides @Singleton
    fun provideTokenProvider(): FirebaseTokenProvider = FirebaseTokenProvider(
        firebaseAuth = TODO()
    )

    //Explicitly provides the AuthInterceptor, injecting the required provider
    @Provides @Singleton
    fun provideAuthInterceptor(tokenProvider: FirebaseTokenProvider): AuthInterceptor {
        return AuthInterceptor(tokenProvider)
    }

    @Provides @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC // Use BASIC for robust production readiness
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor) // Now uses the correctly injected interceptor
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.quizmaster.example/") // Ensure this is your actual API URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    //Uses the correct service interface name (QuizMasterApi, not ApiService)
    @Provides @Singleton
    fun provideApiService(retrofit: Retrofit): QuizMasterApi {
        return retrofit.create(QuizMasterApi::class.java)
    }
    /**
     * Provide Gson for JSON parsing
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    /**
     * Provide Retrofit instance
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * Provide Firebase Auth instance
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    /**
     * Provide Firebase Firestore instance
     */
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    /**
     * Provide Network Monitor
     */
    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor {
        return NetworkMonitor(context)
    }
}
