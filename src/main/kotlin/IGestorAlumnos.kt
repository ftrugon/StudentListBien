interface IGestorAlumnos {
    fun anadirALista(alumnoAAnadir: String)

    fun eliminarDeLista(indexAlumnoAeliminar: Int)

    fun borrarTodo()

    fun escribirArchivo()

    fun getAlumnos():MutableList<String>
}