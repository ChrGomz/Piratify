package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.katarem.piratify.entities.Albums
import io.github.katarem.piratify.entities.Cancion

@Composable
fun PantallaAllCanciones(navController: NavController?){
    val album = Albums.TodasLasCanciones
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Selecciona una canción")
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(album.canciones) { index, cancion ->
                CancionCard(cancion = cancion, onClick = { navController?.navigate(Rutas.PantallaReproductor.ruta + "/${album.nombre}/0/false") })
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
            Text(text = cancion.nombre)
            Text(text = cancion.album)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PantallaAllCancionesPreview(){
    PantallaAllCanciones(navController = null)
}