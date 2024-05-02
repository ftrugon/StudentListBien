import java.io.File

class GestorRegistros():IGestorDatos {

    private val archivo = File(System.getProperty("user.dir") + "/alumnos.txt")

    init {
        if (!existeFich()){
            crearFich()
        }
    }

    private fun existeFich():Boolean{
        return archivo.exists()
    }

    private fun crearFich(){
        archivo.writeText("")
    }

    override fun recogerAlumnos():List<String>{
        return archivo.useLines { it.toList() }
    }

    override fun guardarAlumnos(alumnos: List<String>){
        archivo.writeText("")
        alumnos.forEach {
            if (it == alumnos[0]){
                archivo.appendText(it)
            }else{
                archivo.appendText("\n$it")
            }

        }
    }

}
