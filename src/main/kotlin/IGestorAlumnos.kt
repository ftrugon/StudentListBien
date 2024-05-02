interface IGestorAlumnos {
    fun anadirALista(alumnoAAnadir: String)

    fun eliminarDeLista(alumnoAeliminar: String)

    fun borrarTodo()

    fun escribirArchivo()

    fun getAlumnos():MutableList<String>
}