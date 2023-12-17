
import DayMealPlan
import android.content.Context
import android.content.SharedPreferences
import com.example.fodiepal.blog.Blog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BlogSharedPreferences(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("BlogPosts", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val blogPostsKey = "BlogPosts"

    fun saveBlogPost(blogPost: Blog) {
        val editor = sharedPreferences.edit()
        val blogPosts = getBlogPosts().toMutableList()
        blogPosts.add(blogPost)
        val json = gson.toJson(blogPosts)
        editor.putString(blogPostsKey, json)
        editor.apply()
    }

    fun getBlogPosts(): List<Blog> {
        val json = sharedPreferences.getString(blogPostsKey, "")
        return if (json.isNullOrEmpty()) {
            listOf()
        } else {
            val type = object : TypeToken<List<Blog>>() {}.type
            gson.fromJson(json, type)
        }
    }

    fun removeBlogPost(blogPost: Blog) {
        val savedBlogPosts = getBlogPosts().toMutableList()
        savedBlogPosts.remove(blogPost)
        val json = gson.toJson(savedBlogPosts)
        sharedPreferences.edit().putString(blogPostsKey, json).apply()
    }
}
