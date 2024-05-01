import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
@Preview
fun App(
    alumnos: IGestorAlumnos
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(
                    value = "",
                    label = { Text("Alumno") },
                    onValueChange = {},
                )
                Button(
                    onClick = {}
                ){
                    Text("Añadir alumno")
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                LazyColumn(
                    modifier = Modifier
                        .size(width = 250.dp, height = 350.dp)
                        .border(width = 1.dp, color =  Color.Black)

                ){
                }
                Button(
                    onClick = {alumnos.borrarTodo()}
                ){
                    Text("Borrar todo")
                }
            }
        }
        
        Button(
            onClick = {}
        ){
            Text("Guardar Cambios")
        }

    }

}

fun main() = application {

    val windowState = rememberWindowState(height = 600.dp, width = 800.dp)

    Window(
        onCloseRequest = ::exitApplication,
        state =  windowState
    ) {
        App()
    }


}
