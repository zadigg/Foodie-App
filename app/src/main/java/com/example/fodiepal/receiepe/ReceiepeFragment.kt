package com.example.fodiepal.receiepe

import Recipe
import RecipeSharedPreferences
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fodiepal.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeListFragment : Fragment() {

    private lateinit var recipeAdapter: RecipeAdapter
    private val recipesList = mutableListOf<Recipe>()
    private lateinit var recipeSharedPreferences: RecipeSharedPreferences
    private val PICK_IMAGE_REQUEST_CODE = 1001
    private lateinit var imageViewRecipe: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_receiepe, container, false)
        recipeSharedPreferences = RecipeSharedPreferences(requireContext())

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewRecipes)
        recipeAdapter = RecipeAdapter(recipesList)
        recyclerView.adapter = recipeAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val fabAddMealPlan = view.findViewById<FloatingActionButton>(R.id.add_new_receipe)
        fabAddMealPlan.setOnClickListener {
            showAddRecipeDialog()
        }

        loadRecipesFromSharedPreferences()

        return view
    }

    private fun showAddRecipeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_recipe, null)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Add New Recipe")

        val recipeNameEditText = dialogView.findViewById<EditText>(R.id.editTextRecipeName)
        val cookingTimeEditText = dialogView.findViewById<EditText>(R.id.editTextCookingTime)
        val ingredientsEditText = dialogView.findViewById<EditText>(R.id.editTextIngredients)
        val instructionsEditText = dialogView.findViewById<EditText>(R.id.editTextInstructions)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)
        val buttonSelectImage = dialogView.findViewById<Button>(R.id.buttonSelectImage)
        imageViewRecipe = dialogView.findViewById(R.id.imageViewRecipe)

        buttonSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }

        dialogBuilder.setPositiveButton("Add") { dialog, _ ->
            val recipeName = recipeNameEditText.text.toString()
            val cookingTime = cookingTimeEditText.text.toString()
            val ingredients = ingredientsEditText.text.toString()
            val instructions = instructionsEditText.text.toString()
            val rating = ratingBar.rating

            val newRecipe = Recipe(recipeName, cookingTime, ingredients.split("\n"), instructions, rating, selectedImageUri.toString())
            recipesList.add(newRecipe)
            saveRecipes()
            recipeAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun saveRecipes() {
        recipeSharedPreferences.saveRecipes(recipesList)
    }

    private fun loadRecipesFromSharedPreferences() {
        recipesList.clear()
        recipesList.addAll(recipeSharedPreferences.getRecipes())
        recipeAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri? = data?.data
            if (::imageViewRecipe.isInitialized) {
                imageViewRecipe.setImageURI(selectedImage)
                selectedImageUri = selectedImage
            }
        }
    }
}
