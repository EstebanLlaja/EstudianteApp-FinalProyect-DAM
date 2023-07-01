package pe.edu.cibertec.estudianteapp.data.repository

import pe.edu.cibertec.estudianteapp.data.model.Curso
import pe.edu.cibertec.estudianteapp.data.model.Tarea
import pe.edu.cibertec.estudianteapp.data.remote.ApiClient
import pe.edu.cibertec.estudianteapp.data.remote.service.TareaService
import pe.edu.cibertec.estudianteapp.util.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TareaRepository (
    private val tareaService: TareaService = ApiClient.getTareaService()
)  {

    fun getTareas(callback: (Result<List<Tarea>>) -> Unit){
        tareaService.getTareas().enqueue(object: Callback<List<Tarea>> {
            override fun onResponse(call: Call<List<Tarea>>, response: Response<List<Tarea>>) {
                if (response.isSuccessful && response.body() != null){
                    callback(Result.Success(response.body()!!))
                } else {
                    callback(Result.Error("Datos no Encontrados"))
                }
            }

            override fun onFailure(call: Call<List<Tarea>>, t: Throwable) {
                callback(Result.Error(t.message.toString()))
            }

        })
    }

    fun createTarea(id: Int, tarea: String, callback: (Result<Boolean>) -> Unit){
        tareaService.createTareas(Tarea(id, tarea)).enqueue(object: Callback<Tarea>{
            override fun onResponse(call: Call<Tarea>, response: Response<Tarea>) {
                if (response.isSuccessful && response.body() != null){
                    callback(Result.Success(true))
                }
            }

            override fun onFailure(call: Call<Tarea>, t: Throwable) {
                callback(Result.Error(t.message.toString()))
            }

        })
    }

    fun updateTarea(id: Int, tarea: Tarea, callback: (Result<Boolean>) -> Unit) {
        tareaService.updateTarea(id, tarea).enqueue(object : Callback<Tarea> {
            override fun onResponse(call: Call<Tarea>, response: Response<Tarea>) {
                if (response.isSuccessful && response.body() != null) {
                    callback(Result.Success(true))
                } else {
                    callback(Result.Error("Error al actualizar la tarea"))
                }
            }

            override fun onFailure(call: Call<Tarea>, t: Throwable) {
                callback(Result.Error(t.message.toString()))
            }
        })
    }

    fun deleteTarea(id: Int, callback: (Result<Boolean>) -> Unit) {
        tareaService.deleteTarea(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(Result.Success(true))
                } else {
                    callback(Result.Error("Error al eliminar la tarea"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(Result.Error(t.message.toString()))
            }
        })
    }




}