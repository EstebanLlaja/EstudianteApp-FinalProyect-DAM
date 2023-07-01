package pe.edu.cibertec.estudianteapp.data.repository

import pe.edu.cibertec.estudianteapp.data.model.Curso
import pe.edu.cibertec.estudianteapp.data.model.Sedes
import pe.edu.cibertec.estudianteapp.data.remote.ApiClient
import pe.edu.cibertec.estudianteapp.data.remote.service.CursoService
import pe.edu.cibertec.estudianteapp.data.remote.service.SedeService
import pe.edu.cibertec.estudianteapp.util.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SedeRepository {
    private val sedeService: SedeService = ApiClient.getSedeService()

    fun getSedes(callback: (Result<List<Sedes>>) -> Unit){
        sedeService.getSedes().enqueue(object: Callback<List<Sedes>> {
            override fun onResponse(call: Call<List<Sedes>>, response: Response<List<Sedes>>) {
                if (response.isSuccessful && response.body() != null){
                    callback(Result.Success(response.body()!!))
                } else {
                    callback(Result.Error("No se añadió el cursoi webon"))
                }
            }

            override fun onFailure(call: Call<List<Sedes>>, t: Throwable) {
                callback(Result.Error(t.message.toString()))
            }


        })
    }
}