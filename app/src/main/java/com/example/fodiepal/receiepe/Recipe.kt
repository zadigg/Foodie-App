data class Recipe(
    val name: String,
    val cookingTime: String,
    val ingredients: List<String>,
    val instructions: String,
    val rating: Float,
    val imageUriString: String?
)