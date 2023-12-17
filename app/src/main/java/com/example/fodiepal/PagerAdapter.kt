import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.fodiepal.about.AboutFragment
import com.example.fodiepal.contact.ContactFragment
import com.example.fodiepal.mealplanner.MealPlannerFragment
import com.example.fodiepal.receiepe.RecipeListFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RecipeListFragment()
            1 -> MealPlannerFragment()
            2 -> BlogFragment()
            3 -> ContactFragment()
            4 -> AboutFragment()
            else -> RecipeListFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Recipes"
            1 -> "Meal Planner"
            2 -> "Blog"
            3 -> "Contact"
            4 -> "About Me"
            else -> "Recipes"
        }
    }
}
