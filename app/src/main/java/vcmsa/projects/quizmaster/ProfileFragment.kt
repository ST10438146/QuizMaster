package vcmsa.projects.quizmaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = TextView(context).apply {
            text = "Profile Screen Content (Settings, Stats, Logout)"
            textSize = 24f
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            // TODO: Replace with the actual profile layout XML
        }
        return view
    }
}