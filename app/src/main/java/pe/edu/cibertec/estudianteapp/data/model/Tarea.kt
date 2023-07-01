package pe.edu.cibertec.estudianteapp.data.model

data class Tarea (
    val id: Int= getNextId() ,
    var tarea: String

    ){
    companion object {
        private var counter = 2

        fun getNextId(): Int {
            counter++
            return counter
        }
    }
}