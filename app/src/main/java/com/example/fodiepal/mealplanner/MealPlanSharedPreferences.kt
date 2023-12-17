package com.example.fodiepal.mealplanner

import DayMealPlan
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealPlanSharedPreferences(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MealPlans", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val mealPlanKey = "MealPlans"

    fun saveMealPlan(mealPlan: DayMealPlan) {
        val editor = sharedPreferences.edit()
        val mealPlanList = getMealPlans().toMutableList()
        mealPlanList.add(mealPlan)
        val json = gson.toJson(mealPlanList)
        editor.putString("MealPlans", json)
        editor.apply()
    }

    fun getMealPlans(): List<DayMealPlan> {
        val json = sharedPreferences.getString("MealPlans", "")
        return if (json.isNullOrEmpty()) {
            listOf()
        } else {
            val type = object : TypeToken<List<DayMealPlan>>() {}.type
            gson.fromJson(json, type)
        }
    }

    fun removeMealPlan(mealPlan: DayMealPlan) {
        val savedMealPlans = getMealPlans().toMutableList()
        savedMealPlans.remove(mealPlan)
        val json = gson.toJson(savedMealPlans)
        sharedPreferences.edit().putString(mealPlanKey, json).apply()
    }
}
