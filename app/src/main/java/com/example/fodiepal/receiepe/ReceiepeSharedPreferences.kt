import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeSharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("Recipe_Prefs", Context.MODE_PRIVATE)
    }

    fun saveRecipes(recipes: MutableList<Recipe>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val recipeJsonList = gson.toJson(recipes)
        editor.putString("Recipe_List", recipeJsonList)
        editor.apply()
    }

    fun getRecipes(): List<Recipe> {
        val gson = Gson()
        val recipeJsonList = sharedPreferences.getString("Recipe_List", null)
        val type = object : TypeToken<List<Recipe>>() {}.type
        return gson.fromJson(recipeJsonList, type) ?: emptyList()
    }

//    fun removeRecipe(recipe: Recipe) {
//        val savedRecipes = getRecipes().toMutableList()
//        savedRecipes.remove(recipe)
//        saveRecipes(savedRecipes)
//    }
}
