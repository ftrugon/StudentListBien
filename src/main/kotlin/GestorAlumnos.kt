class GestorAlumnos(
    private val registros: IGestorDatos
):IGestorAlumnos {


    private var alumnos = registros.recogerAlumnos().toMutableList()

    override fun anadirALista(alumnoAAnadir: String){
        alumnos.add(alumnoAAnadir)
    }

    override fun eliminarDeLista(alumnoAeliminar: String){
        alumnos.remove(alumnoAeliminar)
    }

    override fun borrarTodo(){
        alumnos = mutableListOf<String>()
    }

}