package pe.edu.cibertec.estudianteapp.ui.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Inicio(navController: NavController) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Estudiante App",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = { navController.navigate("cursosList") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(200.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text = "Cursos", color = Color.White, style = MaterialTheme.typography.titleMedium)
            }

            Button(
                onClick = { navController.navigate("tareas") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(200.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text = "Tareas", color = Color.White, style = MaterialTheme.typography.titleMedium)
            }

            Button(
                onClick = { navController.navigate("sedes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text = "Nuestras Sedes", color = Color.White, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
