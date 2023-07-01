package pe.edu.cibertec.estudianteapp.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pe.edu.cibertec.estudianteapp.data.repository.EstudianteRepository
import pe.edu.cibertec.estudianteapp.ui.theme.EstudianteAppTheme
import pe.edu.cibertec.estudianteapp.util.Result

@Composable
fun Login (navController: NavController) {

    val estudiante = remember {
        mutableStateOf(TextFieldValue())
    }

    val contraseña = remember {
        mutableStateOf(TextFieldValue())
    }

    val context = LocalContext.current

    val estudianteRepository = EstudianteRepository()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            label = { Text(text = "Estudiante") },
            value = estudiante.value,
            onValueChange = {
                estudiante.value = it
            },
            leadingIcon = { Icon(Icons.Default.Person, null) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            label = { Text(text = "Contraseña") },
            value = contraseña.value,
            onValueChange = {
                contraseña.value = it
            },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, null) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            onClick = {
                estudianteRepository.loginEstudiante(estudiante.value.text, contraseña.value.text) { result ->
                    if (result is Result.Success) {
                        navController.navigate("inicio")
                    } else {
                        Toast.makeText(context,result.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }) {
            Text(text = "Iniciar Sesion")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            onClick = {
                navController.navigate("register")
            }) {
            Text(text = "Registrate")
        }
        TextButton(
            onClick = { /*TODO*/ }) {
            Text(text = "¿Olvidaste tu contraseña?")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    EstudianteAppTheme() {
        Login(navController = rememberNavController())
    }
}