import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {

    val registro = GestorRegistros()

    val alumnos = ViewModel(registro)

    val windowState = rememberWindowState(height = 600.dp, width = 800.dp)

    Window(
        onCloseRequest = ::exitApplication,
        state =  windowState
    ) {
        App2(alumnos)
    }


}