package pe.edu.cibertec.estudianteapp.data.remote.service

import pe.edu.cibertec.estudianteapp.data.model.Estudiante
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EstudianteService {
    @POST("estudiantes")
    fun createEstudiante(@Body estudiante: Estudiante): Call<Estudiante>

    @GET("estudiantes")
    fun validateEstudiante(@Query("estudiante") estudiante: String): Call<List<Estudiante>>

    @GET("estudiantes")
    fun loginEstudiante(
        @Query("estudiante") estudiante: String,
        @Query("contraseña") contraseña: String
    ): Call<List<Estudiante>>
}