import android.content.Context
import android.content.SharedPreferences
import com.example.fodiepal.about.AboutDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AboutDetailsSharedPreferences(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("AboutDetails", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveDetailsList(detailsList: List<AboutDetail>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(detailsList)
        editor.putString("detailsList", json)
        editor.apply()
    }

    fun retrieveDetailsList(): MutableList<AboutDetail> {
        val json = sharedPreferences.getString("detailsList", null)
        val type = object : TypeToken<MutableList<AboutDetail>>() {}.type
        return gson.fromJson(json, type) ?: mutableListOf()
    }
}