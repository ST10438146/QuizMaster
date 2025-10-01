package vcmsa.projects.quizmaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LeaderboardFragment : Fragment() {
    // Dummy Data Class for Leaderboard Entry
    data class LeaderboardEntry(val rank: Int, val name: String, val score: Int)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        val leaderboardRecyclerView: RecyclerView = view.findViewById(R.id.leaderboardRecyclerView)
        leaderboardRecyclerView.layoutManager = LinearLayoutManager(context)

        // --- TODO: Fetch leaderboard data from API/DB ---
        // Placeholder data for demonstration
        val dummyData = listOf(
            LeaderboardEntry(1, "The Quiz Master", 15000),
            LeaderboardEntry(2, "Pixel Partner 1", 12500),
            LeaderboardEntry(3, "Pixel Partner 2", 11800),
            LeaderboardEntry(4, "Player 4", 9900),
            LeaderboardEntry(5, "Player 5", 8700)
        )

        leaderboardRecyclerView.adapter = LeaderboardAdapter(dummyData)
        // --- END TODO ---

        // Set up button listeners (Global/Friends) as planned in the previous step
        view.findViewById<TextView>(R.id.btnGlobal).setOnClickListener {
            // Logic to fetch and display global rankings
        }
        view.findViewById<TextView>(R.id.btnFriends).setOnClickListener {
            // Logic to fetch and display friend rankings
        }

        return view
    }

    // Minimal Adapter implementation to bind data to the new XML layout
    class LeaderboardAdapter(private val entries: List<LeaderboardEntry>) :
        RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

        class LeaderboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvRank: TextView = view.findViewById(R.id.tvRank)
            val tvPlayerName: TextView = view.findViewById(R.id.tvPlayerName)
            val tvPlayerScore: TextView = view.findViewById(R.id.tvPlayerScore)
            // val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar) // Not used in this snippet
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_leaderboard_entry, parent, false)
            return LeaderboardViewHolder(view)
        }

        override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
            val entry = entries[position]
            holder.tvRank.text = "#${entry.rank}"
            holder.tvPlayerName.text = entry.name
            holder.tvPlayerScore.text = "${entry.score} XP"
        }

        override fun getItemCount() = entries.size
    }
}