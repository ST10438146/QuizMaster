package vcmsa.projects.quizmaster

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast // Used for quick placeholder messages
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. Core Login Button
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            // TODO: Implement actual authentication (Firebase/REST API call)
            // Example: Check fields, then call login service.
            Toast.makeText(this, "Attempting Login...", Toast.LENGTH_SHORT).show()

            // On successful login:
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 2. Navigation to Register Screen (using the inactive tab button)
        findViewById<Button>(R.id.btn_register_tab_go).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            // Note: finish() is not called here so the user can easily go back to Login
        }

        // 3. SSO and Biometrics (PixelPartners Requirement)
        findViewById<Button>(R.id.btnFingerprint).setOnClickListener {
            // TODO: Implement BiometricPrompt logic for Fingerprint
            Toast.makeText(this, "Fingerprint login initiated...", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnFaceId).setOnClickListener {
            // TODO: Implement BiometricPrompt logic for Face ID
            Toast.makeText(this, "Face ID login initiated...", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btnGoogleSignIn).setOnClickListener {
            // TODO: Implement Google Sign-In SDK/Firebase Authentication
            Toast.makeText(this, "Starting Google Sign-In flow...", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btnEmailLogin).setOnClickListener {
            // Placeholder: This button is present in the UI but might redirect to a forgotten password flow
            // or just serve as a visual anchor.
            Toast.makeText(this, "Email option clicked.", Toast.LENGTH_SHORT).show()
        }
    }
}