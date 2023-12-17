package com.example.fodiepal.about

import AboutAdapter
import AboutDetailsSharedPreferences
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fodiepal.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

import androidx.appcompat.app.AppCompatActivity
import com.example.fodiepal.auth.AuthenticationActivity

class AboutFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AboutAdapter
    private lateinit var detailsList: MutableList<AboutDetail>
    private lateinit var detailsSharedPreferences: AboutDetailsSharedPreferences
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        detailsSharedPreferences = AboutDetailsSharedPreferences(requireContext())
        detailsList = detailsSharedPreferences.retrieveDetailsList()

        recyclerView = view.findViewById(R.id.recyclerViewAboutMe)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AboutAdapter(detailsList)
        recyclerView.adapter = adapter


        sharedPref = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val logoutButton: Button = view.findViewById(R.id.logoutBtn)
        logoutButton.setOnClickListener {
            clearSession()
            navigateToLogin()
        }



        val fabAddDetail: FloatingActionButton = view.findViewById(R.id.fabAddDetail)
        fabAddDetail.setOnClickListener {
            showAddDetailsDialog()
        }



        return view
    }
    private fun clearSession() {
        val editor = sharedPref.edit()
        editor.remove("isLoggedIn")
        editor.remove("username")
        editor.apply()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireActivity(), AuthenticationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }




    private fun showAddDetailsDialog() {
        val dialogView = layoutInflater.inflate(R.layout.add_details, null)

        val editTextCulinaryJourney = dialogView.findViewById<EditText>(R.id.editTextCulinaryJourney)
        val editTextFavoriteRecipes = dialogView.findViewById<EditText>(R.id.editTextFavoriteRecipes)
        val editTextFoodPhilosophy = dialogView.findViewById<EditText>(R.id.editTextFoodPhilosophy)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Add New Detail")
            .setPositiveButton("Add") { dialog, _ ->
                val culinaryJourney = editTextCulinaryJourney.text.toString().trim()
                val favoriteRecipes = editTextFavoriteRecipes.text.toString().trim()
                val foodPhilosophy = editTextFoodPhilosophy.text.toString().trim()

                val newDetail = AboutDetail(culinaryJourney, favoriteRecipes, foodPhilosophy)
                detailsList.add(newDetail)
                adapter.notifyDataSetChanged()

                detailsSharedPreferences.saveDetailsList(detailsList)

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

}
