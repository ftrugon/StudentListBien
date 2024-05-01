interface IGestorDatos {

    fun recogerAlumnos():List<String>


    fun guardarAlumnos(alumnos: List<String>)
}