package pe.edu.cibertec.estudianteapp.data.repository

import pe.edu.cibertec.estudianteapp.data.model.Estudiante
import pe.edu.cibertec.estudianteapp.data.remote.ApiClient
import pe.edu.cibertec.estudianteapp.data.remote.service.EstudianteService
import pe.edu.cibertec.estudianteapp.util.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EstudianteRepository (
    private val estudianteService: EstudianteService = ApiClient.getEstudianteService()
        ){
    private fun validateEstudiante(estudiante: String, callback: (Result<Boolean>) -> Unit ) {
        estudianteService.validateEstudiante(estudiante).enqueue(object :
            Callback<List<Estudiante>> {
            override fun onResponse(
                call: Call<List<Estudiante>>,
                response: Response<List<Estudiante>>
            ) {
                if(response.isSuccessful && response.body() != null){
                    if (response.body()!!.isEmpty()){
                        callback(Result.Success(true))
                    } else {
                        callback(Result.Error("El estudiante ya está registrado", false))
                    }
                } else {
                    callback(Result.Error("Datos no encontrados"))
                }
            }

            override fun onFailure(call: Call<List<Estudiante>>, t: Throwable) {
                callback(Result.Error(t.message.toString()))
            }

        })
    }
    fun createEstudiante(estudiante: String, contraseña: String, callback: (Result<Boolean>) -> Unit){
        validateEstudiante(estudiante){result ->
            if (result is Result.Success){
                estudianteService.createEstudiante(Estudiante(estudiante, contraseña)).enqueue(object : Callback<Estudiante>{
                    override fun onResponse(
                        call: Call<Estudiante>,
                        response: Response<Estudiante>
                    ) {
                        if (response.isSuccessful && response.body() != null){
                            callback(Result.Success(true))
                        }
                    }

                    override fun onFailure(call: Call<Estudiante>, t: Throwable) {
                        callback(Result.Error(t.message.toString()))
                    }

                })
            } else {
                callback(Result.Error(result.message.toString()))
            }

        }
    }

    fun loginEstudiante(estudiante: String, contraseña: String, callback: (Result<Boolean>) -> Unit) {
        estudianteService.loginEstudiante(estudiante, contraseña).enqueue(object : Callback<List<Estudiante>> {
            override fun onResponse(
                call: Call<List<Estudiante>>,
                response: Response<List<Estudiante>>
            ) {
                if (response.isSuccessful && response.body() !=null){
                    if (response.body()!!.isNotEmpty()){
                        callback(Result.Success(true))
                    } else{
                        validateEstudiante(estudiante) {result ->
                            if (result is Result.Error && result.data!= null && !result.data){
                                callback(Result.Error("Contraseña incorrecta"))
                            } else {
                                callback(Result.Error("Estudiante incorrecto"))
                            }
                        }
                    }
                } else {
                    callback(Result.Error("Datos no encontrados"))
                }
            }

            override fun onFailure(call: Call<List<Estudiante>>, t: Throwable) {
                callback(Result.Error(t.message.toString()))
            }

        })
    }




}


