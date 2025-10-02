import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for authentication operations
 * Handles Google Sign-In and user profile creation
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao,
    private val settingsDao: SettingsDao
) : ViewModel() {

    private val _authState = MutableStateFlow<Resource<FirebaseUser>>(Resource.Loading())
    val authState: StateFlow<Resource<FirebaseUser>> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    init {
        _currentUser.value = firebaseAuth.currentUser
        if (firebaseAuth.currentUser != null) {
            _authState.value = Resource.Success(firebaseAuth.currentUser!!)
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            try {
                _authState.value = Resource.Loading()

                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                val user = authResult.user

                if (user != null) {
                    if (authResult.additionalUserInfo?.isNewUser == true) {
                        createUserProfile(user)
                    }
                    saveUserLocally(user)

                    _currentUser.value = user
                    _authState.value = Resource.Success(user)
                } else {
                    _authState.value = Resource.Error("Authentication failed")
                }
            } catch (e: Exception) {
                _authState.value = Resource.Error("Sign in failed: ${e.message}")

            }
        }
    }

    private suspend fun createUserProfile(user: FirebaseUser) {
        try {
            val userProfile = hashMapOf(
                "uid" to user.uid,
                "displayName" to user.displayName,
                "email" to user.email,
                "photoUrl" to user.photoUrl?.toString(),
                "xp" to 0,
                "level" to 1,
                "coins" to 0,
                "currentStreak" to 0,
                "longestStreak" to 0,
                "createdAt" to System.currentTimeMillis(),
                "updatedAt" to System.currentTimeMillis()
            )

            firestore.collection("users").document(user.uid).set(userProfile).await()

            val defaultSettings = hashMapOf(
                "language" to "en",
                "notificationsEnabled" to true,
                "biometricsEnabled" to false,
                "dataSaverMode" to false,
                "theme" to "system"
            )

            firestore.collection("users")
                .document(user.uid)
                .collection("settings")
                .document("preferences")
                .set(defaultSettings)
                .await()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun saveUserLocally(user: FirebaseUser) {
        try {
            withContext(Dispatchers.IO) {
                val userEntity = UserEntity(
                    uid = user.uid,
                    displayName = user.displayName,
                    email = user.email,

                )
                userDao.insertUser(userEntity)

                val settingsEntity = UserSettingsEntity(uid = user.uid)
                settingsDao.insertSettings(settingsEntity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        _currentUser.value = null
        _authState.value = Resource.Loading()
    }
}

