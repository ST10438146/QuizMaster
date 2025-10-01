package vcmsa.projects.quizmaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // 1. Set listener for BottomNavigationView item selection
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_play -> {
                    loadFragment(PlayFragment())
                    true
                }
                R.id.nav_quests -> {
                    loadFragment(QuestsFragment())
                    true
                }
                R.id.nav_leaderboard -> {
                    loadFragment(LeaderboardFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        // 2. Load the default screen when the activity starts (Play)
        // Ensure this is called after setting the listener
        if (savedInstanceState == null) {
            // Programmatically select the default item to trigger the fragment load
            bottomNav.selectedItemId = R.id.nav_play
        }
    }

    /**
     * Helper function to perform the Fragment transaction.
     * Replaces the current fragment in the container with the new one.
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            // Replace the fragment in the FrameLayout container with the new fragment
            .replace(R.id.home_fragment_container, fragment)
            .commit()
    }
}