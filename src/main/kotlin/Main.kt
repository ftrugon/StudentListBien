import ViewModelBd.StudentRepo
import ViewModelBd.ViewModelDb
import ViewModelFichero.GestorRegistros
import ViewModelFichero.ViewModel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {

    val registro = GestorRegistros()

    val gestorRepo = StudentRepo()

    val alumnos = ViewModelDb(gestorRepo)

    val windowState = rememberWindowState(height = 600.dp, width = 800.dp)

    Window(
        onCloseRequest = ::exitApplication,
        state =  windowState,
        resizable = false,

    ) {
        App(alumnos)
    }


}