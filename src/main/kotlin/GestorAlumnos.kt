class GestorAlumnos(
    private val registros: IGestorDatos
):IGestorAlumnos {


    private var alumnos = registros.recogerAlumnos().toMutableList()


    override fun anadirALista(alumnoAAnadir: String){
        alumnos.add(alumnoAAnadir)
    }

    override fun eliminarDeLista(alumnoAeliminar: String){
        val alumnoLowerCase = alumnoAeliminar.lowercase()
        alumnos = alumnos.filter { it.lowercase() != alumnoLowerCase }.toMutableList()
    }

    override fun borrarTodo(){
        alumnos = mutableListOf<String>()
    }

    override fun escribirArchivo() = registros.guardarAlumnos(alumnos)

    override fun getAlumnos() = alumnos

}
