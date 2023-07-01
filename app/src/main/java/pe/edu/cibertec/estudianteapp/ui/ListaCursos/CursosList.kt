package pe.edu.cibertec.estudianteapp.ui.ListaCursos

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import pe.edu.cibertec.estudianteapp.data.model.Curso
import pe.edu.cibertec.estudianteapp.data.repository.CursoRepository
import pe.edu.cibertec.estudianteapp.util.Result


@Composable
fun CursosList(navController: NavController){

    val cursos = remember {
        mutableStateOf(listOf<Curso>())
    }

    val cargandoPantalla = remember {
        mutableStateOf(true)
    }
    val cursoRepository = CursoRepository()

    val context = LocalContext.current



    cursoRepository.getCursos { result ->
        if (result is Result.Success){
            cursos.value = result.data!!
        }else {
            Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
        }
        cargandoPantalla.value = false
    }

    LazyColumn {
        items(cursos.value) { cursos ->
            Box {
                Card(modifier = Modifier.padding(8.dp)) {
                    CoilImage(imageModel = { cursos.content })
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {

                        TextButton(onClick = {
                            val url = "https://www.ejemplo.com" // Reemplaza con el enlace externo que deseas redirigir
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                        }) {
                            Text(
                                text = cursos.title,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(4.dp)
                            )
                        }


                    }
                }
            }
        }
    }

    if (cargandoPantalla.value){
        Dialog(onDismissRequest = { cargandoPantalla.value = false }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }
}