import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Custom colors based on the QuizMaster UI design (pink/purple gradient)
val PrimaryPurple = Color(0xFF673AB7) // Deep Purple
val PrimaryPink = Color(0xFFE91E63) // Pink
val BackgroundWhite = Color(0xFFFAFAFA)
val CorrectGreen = Color(0xFF4CAF50)
val WrongRed = Color(0xFFF44336)

private val QuizMasterColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    secondary = PrimaryPink,
    background = BackgroundWhite,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun QuizMasterTheme(content: @Composable () -> Unit) {

}