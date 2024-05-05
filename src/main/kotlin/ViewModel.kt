import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ViewModel(
    private val registros: IGestorDatos
):IViewModel {

    override val alumnos = mutableStateListOf<String>()
    override var nuevoAlumno by mutableStateOf("")

    init {
        alumnos.addAll(registros.recogerAlumnos())
    }


    override fun anadirAlumno() {
        if (nuevoAlumno.trim().isNotEmpty()) {
            alumnos.add(nuevoAlumno.trimStart().trimEnd())
            nuevoAlumno = ""
        }
    }

    override fun guardarCambios() {
        registros.guardarAlumnos(alumnos)
    }

    override fun eliminarAlumno(index: Int) {
        alumnos.removeAt(index)
    }

    override fun eliminarTodos() {
        alumnos.clear()
    }
}
