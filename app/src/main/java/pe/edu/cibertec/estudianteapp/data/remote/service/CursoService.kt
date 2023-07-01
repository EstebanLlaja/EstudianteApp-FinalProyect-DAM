package pe.edu.cibertec.estudianteapp.data.remote.service

import pe.edu.cibertec.estudianteapp.data.model.Curso
import retrofit2.Call
import retrofit2.http.GET

interface CursoService {

    @GET("Cursos")
    fun getCursos(): Call<List<Curso>>
}