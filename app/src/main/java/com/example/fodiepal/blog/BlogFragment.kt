import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fodiepal.R
import com.example.fodiepal.blog.Blog
import com.example.fodiepal.blog.BlogAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BlogFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var blogAdapter: BlogAdapter
    private val blogPosts = mutableListOf<Blog>()
    private lateinit var blogSharedPreferences: BlogSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blogSharedPreferences = BlogSharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blog, container, false)

        blogPosts.clear()
        blogPosts.addAll(blogSharedPreferences.getBlogPosts())

        recyclerView = view.findViewById(R.id.recyclerViewBlog)
        blogAdapter = BlogAdapter(blogPosts)
        recyclerView.adapter = blogAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val fabAddBlog: FloatingActionButton = view.findViewById(R.id.fabAddBlog)
        fabAddBlog.setOnClickListener {
            showAddBlogDialog()
        }

        return view
    }

    private fun showAddBlogDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_blog, null)

        val editTextTitle = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val editTextContent = dialogView.findViewById<EditText>(R.id.editTextContent)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Add Blog Post")
            .setPositiveButton("Add") { dialog, _ ->
                val title = editTextTitle.text.toString().trim()
                val content = editTextContent.text.toString().trim()

                if (title.isNotEmpty() && content.isNotEmpty()) {
                    val newBlog = Blog(title, content)
                    addBlog(newBlog)
                }

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun addBlog(newBlog: Blog) {
        blogSharedPreferences.saveBlogPost(newBlog)
        blogPosts.add(newBlog)
        blogAdapter.notifyItemInserted(blogPosts.size - 1)
    }
}