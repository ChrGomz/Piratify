package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.katarem.piratify.entities.Albums
import io.github.katarem.piratify.entities.Cancion
import io.github.katarem.piratify.model.ScaffoldViewModel
import io.github.katarem.piratify.utils.AppColors

@Composable
fun PantallaAllCanciones(navController: NavController?, model: ScaffoldViewModel){
    val nombreAlbum = Albums.TodasLasCanciones.nombre
    val canciones = model.filteredSongs.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(AppColors.negro)) {
        Text(text = "Selecciona una canción", color = Color.White)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(canciones.value) { index, cancion ->
                CancionCard(cancion = cancion, onClick = { navController?.navigate(Rutas.PantallaReproductor.ruta + "/${nombreAlbum}/$index/false") })
            }
        }
    }
}

@Composable
fun CancionCard(cancion: Cancion, onClick: () -> Unit){
    Row(modifier = Modifier
        .clickable { onClick() }
        .fillMaxWidth()
        .height(60.dp)
        .padding(5.dp)) {
        Image(painter = painterResource(id = cancion.portada), contentDescription = cancion.nombre, contentScale = ContentScale.Inside)
        Column(modifier = Modifier
            .weight(1f)
            .padding(5.dp)) {
            Text(text = cancion.nombre, color = Color.White)
            Text(text = cancion.album, color = Color.White)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PantallaAllCancionesPreview(){
    PantallaAllCanciones(navController = null, model = viewModel())
}