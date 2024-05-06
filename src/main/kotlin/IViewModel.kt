interface IViewModel {

    //val alumnos:MutableList<String>
    //var nuevoAlumno:String

    fun anadirAlumno()
    fun guardarCambios()
    fun eliminarAlumno(index: Int)
    fun eliminarTodos()

    fun obtenerAlumnos():MutableList<String>

    fun obtenerNuevoAlumno():String
    fun cambiarNuevoAlumno(nuevoNombre:String)

}