package pe.edu.cibertec.estudianteapp.data.repository

import pe.edu.cibertec.estudianteapp.data.model.Curso
import pe.edu.cibertec.estudianteapp.data.remote.ApiClient
import pe.edu.cibertec.estudianteapp.data.remote.service.CursoService
import pe.edu.cibertec.estudianteapp.data.remote.service.TareaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import pe.edu.cibertec.estudianteapp.util.Result

class CursoRepository {
    private val cursoService: CursoService = ApiClient.getCursoService()

    fun getCursos(callback: (Result<List<Curso>>) -> Unit){
        cursoService.getCursos().enqueue(object: Callback<List<Curso>>{
            override fun onResponse(call: Call<List<Curso>>, response: Response<List<Curso>>) {
                if (response.isSuccessful && response.body() != null){
                    callback(Result.Success(response.body()!!))
                } else {
                    callback(Result.Error("No se añadió la tarea"))
                }
            }

            override fun onFailure(call: Call<List<Curso>>, t: Throwable) {
                callback(Result.Error(t.message.toString()))
            }

        })
    }

}