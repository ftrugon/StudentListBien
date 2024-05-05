import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay

@Composable
fun App2(viewModel: ViewModel) {
    var mostrarGuardarCambios by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            columnaAnadirAlumno(
                viewModel = viewModel,
                modifier = Modifier.weight(1f)
            )

            mostrarAlumnos(
                alumnos = viewModel.alumnos,
                viewModel = viewModel,
                modifier = Modifier.weight(1f)
            )
        }

        compBoton("Guardar Cambios") {
            viewModel.guardarCambios()
            mostrarGuardarCambios = true
        }

        if (mostrarGuardarCambios) {
            Toast("SE ESTAN GUARDANDO LOS CAMBIOS") {
                mostrarGuardarCambios = false
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun columnaAnadirAlumno(viewModel: ViewModel, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = viewModel.nuevoAlumno,
            label = { Text("Alumno") },
            onValueChange = { viewModel.nuevoAlumno = it },
            singleLine = true
        )
        compBoton("Añadir Alumno", viewModel::anadirAlumno)
    }
}

@Composable
fun mostrarAlumnos(
    alumnos: MutableList<String>,
    viewModel: ViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text("Alumnos: ${alumnos.size}")
        LazyColumn(
            modifier = Modifier
                .size(width = 250.dp, height = 350.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
        ) {
            itemsIndexed(alumnos) { index, alumno ->
                TextBox(alumno) {
                    viewModel.eliminarAlumno(index)
                }
            }
        }
        compBoton("Borrar Todo") {
            viewModel.eliminarTodos()
        }
    }
}

@Composable
fun TextBox(text: String, onDeleteClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
            .padding(start = 10.dp)
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = onDeleteClick
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}

@Composable
fun compBoton(testito: String, funcionClic: () -> Unit) {
    Button(
        onClick = funcionClic
    ) {
        Text(testito)
    }
}
