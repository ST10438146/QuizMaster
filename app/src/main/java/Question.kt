data class Question(
    val id: String = "",
    val text: String = "",
    val choices: List<String> = emptyList(),
    val correctIndex: Int = 0,
    val category: String = "",
    val difficulty: String = "medium",
    val language: String = "en",
    val explanation: String = ""
)