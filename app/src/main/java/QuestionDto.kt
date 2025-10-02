data class QuestionDto(
    val id: String,
    val text: String,
    val choices: List<String> = emptyList(),
    val correctIndex: Int = 0
)