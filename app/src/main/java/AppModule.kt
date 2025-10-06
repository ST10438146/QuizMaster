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
import network.AuthInterceptor
import network.FirebaseTokenProvider
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
    //Provides the network.FirebaseTokenProvider, which is used by the network.AuthInterceptor
    @Provides @Singleton
    fun provideTokenProvider(firebaseAuth: FirebaseAuth): FirebaseTokenProvider =
        FirebaseTokenProvider(firebaseAuth)


    //Explicitly provides the network.AuthInterceptor, injecting the required provider
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
     * Provide Firebase Auth instance
     */
    @Provides @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

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
