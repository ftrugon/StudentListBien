package ViewModelBd
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import interfaces.IStudentRepo
import interfaces.IViewModel

class ViewModelDb(
    private val dataBase:IStudentRepo
): IViewModel {

    private val alumnos = mutableStateListOf<String>()
    private var nuevoAlumno by mutableStateOf("")

    init {
        val resul = dataBase.getAllStudents()
        resul.onSuccess {
            alumnos.addAll(it)
        }
        resul.onFailure {
            throw Database.DatabaseTimeoutException("$it")
        }

    }

    override fun anadirAlumno() {
        if (nuevoAlumno.trim().isNotEmpty()) {
            alumnos.add(nuevoAlumno.trimStart().trimEnd())
            nuevoAlumno = ""
        }
    }

    override fun guardarCambios() {
        dataBase.updateStudents(alumnos)
    }

    override fun eliminarAlumno(index: Int) {
        alumnos.removeAt(index)
    }

    override fun eliminarTodos() {
        alumnos.clear()
    }

    override fun cambiarNombreAlumno(index: Int, nuevoNombre: String){
        alumnos[index] = nuevoNombre
    }

    override fun obtenerAlumnos() = alumnos

    override fun obtenerNuevoAlumno() = nuevoAlumno

    override fun cambiarNuevoAlumno(nuevoNombre:String){
        nuevoAlumno = nuevoNombre
    }

}