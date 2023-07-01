package pe.edu.cibertec.estudianteapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.cibertec.estudianteapp.ui.ListaCursos.CursosList
import pe.edu.cibertec.estudianteapp.ui.inicio.Inicio
import pe.edu.cibertec.estudianteapp.ui.login.Login
import pe.edu.cibertec.estudianteapp.ui.register.Register
import pe.edu.cibertec.estudianteapp.ui.sedes.Sedes
import pe.edu.cibertec.estudianteapp.ui.tarea.Tarea

@Preview
@Composable
fun Home(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){

        composable("login"){
            Login(navController)
        }
        composable("register"){
            Register(navController)
        }
        composable("cursosList"){
            CursosList(navController)
        }
        composable("tareas"){
            Tarea(navController)
        }
        composable("sedes"){
            Sedes(navController)
        }
        composable("inicio"){
            Inicio(navController)
        }



    }
}