import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fodiepal.R
import com.example.fodiepal.mealplanner.MealPlanSharedPreferences

class MealPlanAdapter(
    private val mealPlans: MutableList<DayMealPlan>,
    private val mealPlanSharedPreferences: MealPlanSharedPreferences
) : RecyclerView.Adapter<MealPlanAdapter.MealPlanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealPlanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_plan_item, parent, false)
        return MealPlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealPlanViewHolder, position: Int) {
        val mealPlan = mealPlans[position]
        holder.bind(mealPlan)
        holder.bindDeleteButton(mealPlan, position)
    }

    override fun getItemCount(): Int = mealPlans.size

    fun removeMealPlan(position: Int) {
        mealPlanSharedPreferences.removeMealPlan(mealPlans[position])
        mealPlans.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MealPlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayOfWeekTextView: TextView = itemView.findViewById(R.id.textDayOfWeek)
        private val breakfastTextView: TextView = itemView.findViewById(R.id.textBreakfast)
        private val lunchTextView: TextView = itemView.findViewById(R.id.textLunch)
        private val dinnerTextView: TextView = itemView.findViewById(R.id.textDinner)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(dayMealPlan: DayMealPlan) {
            dayOfWeekTextView.text = dayMealPlan.dayOfWeek
            breakfastTextView.text = dayMealPlan.breakfast
            lunchTextView.text = dayMealPlan.lunch
            dinnerTextView.text = dayMealPlan.dinner
        }

        fun bindDeleteButton(mealPlan: DayMealPlan, position: Int) {
            deleteButton.setOnClickListener {
                removeMealPlan(position)
            }
        }
    }
}


