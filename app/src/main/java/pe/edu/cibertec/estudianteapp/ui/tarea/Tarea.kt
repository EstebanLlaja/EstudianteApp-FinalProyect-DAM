package pe.edu.cibertec.estudianteapp.ui.tarea

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pe.edu.cibertec.estudianteapp.data.model.Tarea
import pe.edu.cibertec.estudianteapp.data.repository.TareaRepository
import pe.edu.cibertec.estudianteapp.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tarea(navController: NavController) {
    val focusRequester = remember {
        FocusRequester()
    }

    val tareas = remember {
        mutableStateOf(listOf<Tarea>())
    }

    val newTarea = remember {
        mutableStateOf(TextFieldValue())
    }

    val id = remember {
        mutableStateOf(0)
    }

    val isEditing = remember {
        mutableStateOf(false)
    }

    val isEnabled = remember {
        mutableStateOf(false)
    }

    val cargandoPantalla = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    val tareaRepository = TareaRepository()

    val deleteIndex = remember {
        mutableStateOf(-1)
    }

    val selectedTarea = remember {
        mutableStateOf<Tarea?>(null)
    }

    tareaRepository.getTareas { result ->
        if (result is Result.Success) {
            tareas.value = result.data!!
        } else {
            Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
        }
        cargandoPantalla.value = false
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Agenda de Tareas") },
            actions = {
                IconButton(
                    onClick = {
                        if (isEditing.value) {
                            selectedTarea.value?.let { tarea ->
                                tareaRepository.updateTarea(tarea.id, tarea) { result ->
                                    if (result is Result.Success) {
                                        tareaRepository.getTareas { result ->
                                            if (result is Result.Success) {
                                                tareas.value = result.data!!
                                                newTarea.value = TextFieldValue("")
                                            } else {
                                                Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
                                            }
                                            cargandoPantalla.value = false
                                        }
                                    } else {
                                        Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } else {
                            tareaRepository.createTarea(id.value, newTarea.value.text) { result ->
                                if (result is Result.Success) {
                                    tareaRepository.getTareas { result ->
                                        if (result is Result.Success) {
                                            tareas.value = result.data!!
                                            newTarea.value = TextFieldValue("")// Borrar la información del TextField después de agregar una tarea
                                        } else {
                                            Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                        cargandoPantalla.value = false
                                    }
                                } else {
                                    Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        isEditing.value = false
                        isEnabled.value = false
                    },
                    enabled = isEnabled.value
                ) {
                    Icon(
                        if (isEditing.value) {
                            Icons.Filled.Edit
                        } else {
                            Icons.Filled.Add
                        }, null
                    )
                }
            }
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                label = { Text(text = "Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isEnabled.value = focusState.isFocused
                    },
                value = if (isEditing.value && selectedTarea.value != null) {
                    selectedTarea.value!!.tarea
                } else {
                    newTarea.value.text
                },
                onValueChange = {
                    if (isEditing.value && selectedTarea.value != null) {
                        selectedTarea.value = selectedTarea.value!!.copy(tarea = it)
                    } else {
                        newTarea.value = TextFieldValue(it)
                    }
                    isEnabled.value = (isEditing.value && selectedTarea.value != null && selectedTarea.value!!.tarea.isNotEmpty()) || (!isEditing.value && newTarea.value.text.isNotEmpty())
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Limpiar el texto cuando se presione "Done" en el teclado durante la edición

                    }
                )
            )
            LazyColumn {
                items(tareas.value) { tarea ->
                    val isSelected = selectedTarea.value?.id == tarea.id
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    selectedTarea.value = tarea
                                    isEditing.value = true
                                }
                        ) {
                            Text(
                                text = tarea.tarea,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(7f)
                            )
                            if (isSelected) {
                                IconButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        deleteIndex.value = tareas.value.indexOf(tarea)
                                    }
                                ) {
                                    Icon(Icons.Filled.Delete, null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Implementación del método de eliminación de tarea
    if (deleteIndex.value != -1) {
        val tareaToDelete = tareas.value[deleteIndex.value]
        AlertDialog(
            onDismissRequest = { deleteIndex.value = -1 },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que quieres eliminar esta tarea?") },
            confirmButton = {
                Button(
                    onClick = {
                        tareaRepository.deleteTarea(tareaToDelete.id) { result ->
                            if (result is Result.Success) {
                                tareaRepository.getTareas {result ->
                                    if (result is Result.Success){
                                        tareas.value = tareas.value.filterIndexed { index, _ -> index != deleteIndex.value }
                                        deleteIndex.value = -1
                                        tareas.value=result.data!!
                                        newTarea.value=TextFieldValue("")
                                        selectedTarea.value=null
                                        isEditing.value=false
                                    }else{
                                        Toast.makeText(context,result.message.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                    cargandoPantalla.value=false
                                }


                            }
                            else {
                                Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }



                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { deleteIndex.value = -1 }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}















