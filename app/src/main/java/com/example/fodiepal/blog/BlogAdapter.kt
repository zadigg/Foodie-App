package com.example.fodiepal.blog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fodiepal.R

class BlogAdapter(private val blogList: List<Blog>) :
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.blog_item, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = blogList[position]
        holder.bind(blog)
    }

    override fun getItemCount(): Int {
        return blogList.size
    }

    inner class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val contentTextView: TextView = itemView.findViewById(R.id.textViewContent)

        fun bind(blog: Blog) {
            titleTextView.text = blog.title
            contentTextView.text = blog.content
        }
    }
}

