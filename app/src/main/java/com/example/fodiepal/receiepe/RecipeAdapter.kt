package com.example.fodiepal.receiepe

import Recipe
import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fodiepal.R
import com.bumptech.glide.Glide
import com.example.fodiepal.home.HomeActivity

class RecipeAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeName: TextView = itemView.findViewById(R.id.textRecipeName)
        private val cookingTime: TextView = itemView.findViewById(R.id.textCookingTime)
        private val ingredients: TextView = itemView.findViewById(R.id.ingredients)
        private val instructions: TextView = itemView.findViewById(R.id.textView2)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val recipeImage: ImageView = itemView.findViewById(R.id.imageRecipe)

        @SuppressLint("SetTextI18n")
        fun bind(recipe: Recipe) {
            recipeName.text = "Recipe Name: ${recipe.name}"
            cookingTime.text = "Cooking Time: ${recipe.cookingTime}"
            ingredients.text = "Ingredients: ${recipe.ingredients.joinToString(separator = ", ")}"
            instructions.text = "Instructions: ${recipe.instructions}"
            ratingBar.rating = recipe.rating

            // Load the image if URI is not null
            recipe.imageUriString?.let { uriString ->
                val imageUri = Uri.parse(uriString)
                Glide.with(itemView.context)
                    .load(imageUri)
                    .placeholder(R.drawable.ic_notifications_black_24dp)
                    .into(recipeImage)
            } ?: run {
                recipeImage.setImageResource(R.drawable.ic_notifications_black_24dp)
            }
        }
    }

}
