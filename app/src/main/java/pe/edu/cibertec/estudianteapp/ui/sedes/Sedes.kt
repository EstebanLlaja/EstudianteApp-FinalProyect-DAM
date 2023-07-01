package pe.edu.cibertec.estudianteapp.ui.sedes

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
import pe.edu.cibertec.estudianteapp.data.model.Sedes
import pe.edu.cibertec.estudianteapp.data.repository.SedeRepository
import pe.edu.cibertec.estudianteapp.util.Result



@Composable
fun Sedes(navController: NavController){

    val sedes = remember {
        mutableStateOf(listOf<Sedes>())
    }

    val cargandoPantalla = remember {
        mutableStateOf(true)
    }
    val sedeRepository = SedeRepository()

    val context = LocalContext.current



    sedeRepository.getSedes {  result ->
        if (result is Result.Success){
            sedes.value = result.data!!
        }else {
            Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
        }
        cargandoPantalla.value = false
    }

    LazyColumn {
        items(sedes.value) { sedes ->
            Box {
                Card(modifier = Modifier.padding(8.dp)) {
                    CoilImage(imageModel = { sedes.imagen })
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
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(sedes.ubicacionUrl)
                            LocalContext.run { intent }
                        }) {
                            Text(
                                text = sedes.nombre,
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