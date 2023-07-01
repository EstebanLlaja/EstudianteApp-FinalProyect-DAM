package pe.edu.cibertec.estudianteapp.data.remote

import pe.edu.cibertec.estudianteapp.data.remote.service.CursoService
import pe.edu.cibertec.estudianteapp.data.remote.service.EstudianteService
import pe.edu.cibertec.estudianteapp.data.remote.service.SedeService
import pe.edu.cibertec.estudianteapp.data.remote.service.TareaService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val API_BASE_URL = "https://knowing-outgoing-periodical.glitch.me/"

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getEstudianteService(): EstudianteService {
        return getRetrofit().create(EstudianteService::class.java)
    }

    fun getCursoService(): CursoService{
        return getRetrofit().create(CursoService::class.java)
    }

    fun getTareaService(): TareaService{
        return getRetrofit().create(TareaService::class.java)
    }

    fun getSedeService(): SedeService{
        return getRetrofit().create(SedeService::class.java)
    }

}