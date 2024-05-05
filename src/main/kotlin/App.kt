import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay
import androidx.compose.ui.input.key.*

@Composable
@Preview
fun App(
    viewModel: IViewModel
) {

    val foco = remember { FocusRequester() }

    var mostrarGuardarCambios by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = true) {
        viewModel.anadirAlumno()
    }


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
                nuevoAlumno = viewModel.nuevoAlumno,
                cambiarValor = {viewModel.nuevoAlumno = it},
                onClicAnadir = { viewModel.anadirAlumno() },
                foco = foco
            )

            mostrarAlumnos(
                alumnos = viewModel.alumnos,
                lambdaTexto = {
                    viewModel.eliminarAlumno(it)
                },
                onClicEliminarTo = {
                    viewModel.eliminarTodos()
                },
                foco = foco
            )
        }

        compBoton("Guardar Cambios") {
            viewModel.guardarCambios()
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun columnaAnadirAlumno(nuevoAlumno:String,cambiarValor:(String)->Unit,onClicAnadir:()->Unit,foco:FocusRequester){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 20.dp)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyUp && event.key == Key.Enter) {
                    onClicAnadir()
                    true // Consumimos el evento
                } else {
                    false // No consumimos el evento
                }
            }

    ){
        OutlinedTextField(
            value = nuevoAlumno,
            label = { Text("Alumno") },
            onValueChange = cambiarValor,
            singleLine = true,
            modifier = Modifier.focusRequester(foco)
        )
        compBoton("Añadir Alumno",{
            onClicAnadir()
        },Modifier.focusRequester(foco))

    }
}

@Composable
fun mostrarAlumnos(alumnos:MutableList<String>,lambdaTexto:(Int) -> Unit,onClicEliminarTo:()-> Unit,foco:FocusRequester){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Alumnos: ${alumnos.size}")
        LazyColumn(
            modifier = Modifier
                .size(width = 250.dp, height = 350.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
        ) {
            itemsIndexed(alumnos) { index, alumno ->
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
fun compBoton(testito:String,funcionClic:()->Unit,modifier: Modifier = Modifier){
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

