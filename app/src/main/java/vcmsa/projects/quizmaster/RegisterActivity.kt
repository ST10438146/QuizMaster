package vcmsa.projects.quizmaster

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast // Used for quick placeholder messages
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. Core Register Button
        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            // TODO: Implement actual registration (Check fields, create user, store data)
            // Example: Validate password complexity, check username availability.
            Toast.makeText(this, "Attempting Registration...", Toast.LENGTH_SHORT).show()

            // On successful registration:
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Prevents returning to registration via back button
        }

        // 2. Navigation back to Login Screen (using the inactive tab button)
        findViewById<Button>(R.id.btn_login_tab_go).setOnClickListener {
            finish() // Simply close this activity to return to the parent LoginActivity
        }

        // 3. SSO and Biometrics (Matching Login for consistency)
        findViewById<Button>(R.id.btnRegFingerprint).setOnClickListener {
            // TODO: Biometric integration (likely set up AFTER registration is complete)
            Toast.makeText(this, "Biometric setup enabled.", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnRegFaceId).setOnClickListener {
            // TODO: Biometric integration (likely set up AFTER registration is complete)
            Toast.makeText(this, "Biometric setup enabled.", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btnRegGoogleSignIn).setOnClickListener {
            // TODO: Implement Google Sign-In for quick registration/sign-up
            Toast.makeText(this, "Starting Google Sign-Up flow...", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btnRegEmailLogin).setOnClickListener {
            // Placeholder: For visual consistency.
            Toast.makeText(this, "Email option clicked.", Toast.LENGTH_SHORT).show()
        }
    }
}