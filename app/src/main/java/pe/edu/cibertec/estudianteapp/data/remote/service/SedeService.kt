package pe.edu.cibertec.estudianteapp.data.remote.service

import pe.edu.cibertec.estudianteapp.data.model.Curso
import pe.edu.cibertec.estudianteapp.data.model.Sedes
import retrofit2.Call
import retrofit2.http.GET

interface SedeService {
    @GET("Sedes")
    fun getSedes(): Call<List<Sedes>>
}