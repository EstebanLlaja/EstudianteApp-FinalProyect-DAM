package pe.edu.cibertec.estudianteapp.data.remote.service
import pe.edu.cibertec.estudianteapp.data.model.Tarea
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path


interface TareaService {

    @GET("tareas")
    fun getTareas(): Call<List<Tarea>>

    @POST("tareas")
    fun createTareas (@Body tarea: Tarea): Call<Tarea>

    @PUT("tareas/{id}")
    fun updateTarea (@Path("id") id: Int ,@Body tarea: Tarea): Call<Tarea>

    @DELETE("tareas/{id}")
    fun deleteTarea (@Path("id") id: Int): Call<Void>
}