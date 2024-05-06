import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay
import kotlin.system.exitProcess

@Composable
@Preview
fun App(
    alumnos: IGestorAlumnos
) {

    var visualDeAlumnos by remember { mutableStateOf(alumnos.getAlumnos()) }

    var nuevoAlumno by remember { mutableStateOf("") }

    val foco = remember { FocusRequester() }

    var mostrarGuardarCambios by remember { mutableStateOf(false) }


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

            columnaAnadirAlumno(
                nuevoAlumno = nuevoAlumno,
                cambiarValor = {nuevoAlumno = it},
                onClicAnadir = {
                    if (nuevoAlumno.trim().isNotEmpty()) {
                        alumnos.anadirALista(nuevoAlumno.trimEnd().trimStart())
                        nuevoAlumno = ""
                        visualDeAlumnos = alumnos.getAlumnos()
                    }
                },
                foco
            )

            mostrarAlumnos(
                visualDeAlumnos = visualDeAlumnos,
                lambdaTexto = {
                    alumnos.eliminarDeLista(it)
                    visualDeAlumnos = alumnos.getAlumnos()
                },
                onClicEliminarTo = {
                    alumnos.borrarTodo()
                    visualDeAlumnos = alumnos.getAlumnos()
                },
                foco
            )
        }

        compBoton("Guardar Cambios") {
            alumnos.escribirArchivo()
            foco.requestFocus()
            mostrarGuardarCambios = true
        }

        if (mostrarGuardarCambios){
            Toast("SE ESTAN GUARDANDO LOS CAMBIOS"){
                mostrarGuardarCambios = false
            }
        }

    }
}

@Composable
fun columnaAnadirAlumno(nuevoAlumno:String,cambiarValor:(String)->Unit,onClicAnadir:()->Unit,foco:FocusRequester){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = nuevoAlumno,
            label = { Text("Alumno") },
            onValueChange = cambiarValor,
            singleLine = true,
            modifier = Modifier.focusRequester(foco)
        )
        compBoton("Añadir Alumno") {
            onClicAnadir()
        }

    }
}

@Composable
fun mostrarAlumnos(visualDeAlumnos:MutableList<String>,lambdaTexto:(Int) -> Unit,onClicEliminarTo:()-> Unit,foco:FocusRequester){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Alumnos: ${visualDeAlumnos.size}")
        LazyColumn(
            modifier = Modifier
                .size(width = 250.dp, height = 350.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
        ) {
            itemsIndexed(visualDeAlumnos) { index, alumno ->
                TextBox(alumno, foco) {
                    lambdaTexto(index)
                }
            }
        }

        compBoton("Borrar Todo") {
            onClicEliminarTo()
            foco.requestFocus()
        }

    }
}

@Composable
fun TextBox(text: String = "Item",foco:FocusRequester, onDeleteClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
            .padding(start = 10.dp)
    ){
        Text(text = text)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                onDeleteClick()
                foco.requestFocus()
            }
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}


@Composable
@Preview
fun compBoton(testito:String,funcionClic:()->Unit){
    Button(
        onClick = {
            funcionClic()
        }
    ){
        Text(testito)
    }
}

@Composable
@Preview
fun Toast(message: String, onDismiss: () -> Unit) {
    Dialog(
        title = "Atención",
        resizable = false,
        onCloseRequest = onDismiss
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(message)
        }
    }
    // Cierra el Toast después de 1 segundos
    LaunchedEffect(Unit) {
        delay(1000)
        onDismiss()
    }
}


fun main() = application {

    val registro = GestorRegistros()
    val alumnos = GestorAlumnos(registro)

    val windowState = rememberWindowState(height = 600.dp, width = 800.dp)

    Window(
        onCloseRequest = ::exitApplication,
        state =  windowState
    ) {
        App(alumnos)
    }


}