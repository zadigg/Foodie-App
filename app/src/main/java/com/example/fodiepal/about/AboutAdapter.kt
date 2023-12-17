import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fodiepal.R
import com.example.fodiepal.about.AboutDetail

class AboutAdapter(private val detailsList: List<AboutDetail>) :
    RecyclerView.Adapter<AboutAdapter.AboutMeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutMeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_me, parent, false)
        return AboutMeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AboutMeViewHolder, position: Int) {
        val currentDetail = detailsList[position]
        holder.bind(currentDetail)
    }

    override fun getItemCount() = detailsList.size

    inner class AboutMeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val culinaryJourneyTextView: TextView = itemView.findViewById(R.id.textViewCulinaryJourney)
        private val favoriteRecipesTextView: TextView = itemView.findViewById(R.id.textViewFavoriteRecipes)
        private val textViewFoodPhilosophy: TextView = itemView.findViewById(R.id.textViewFoodPhilosophy)


        fun bind(detail: AboutDetail) {
            culinaryJourneyTextView.text = detail.culinaryJourney
            favoriteRecipesTextView.text = detail.favoriteRecipes
            textViewFoodPhilosophy.text = detail.foodPhilosophy
        }
    }
}
