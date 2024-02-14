package io.github.katarem.piratify.pantallas

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.katarem.piratify.entities.Album
import io.github.katarem.piratify.model.ScaffoldViewModel
import io.github.katarem.piratify.utils.AppColors

@Composable
fun PantallaPlaylists(model: ScaffoldViewModel, navController: NavController?, context: Context) {
    val albums = model.albums.collectAsState()
    val playlists = model.playlists.collectAsState()
    Box(modifier = Modifier.background(AppColors.negro)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = "Últimos álbumes que te pueden gustar",
                color = Color.White,
                fontSize = 20.sp
            )
            LazyRow(
                content = {
                    items(albums.value) {
                        AlbumItem(
                            album = it,
                            { navController?.navigate(Rutas.PantallaAlbum.ruta + "/${it.nombre}") })
                    }
                }, modifier = Modifier
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround
            )
            Text(
                text = "Sigue escuchando tu música favorita",
                color = Color.White,
                fontSize = 20.sp
            )
            LazyRow(
                content = {
                    items(playlists.value) {
                        AlbumItem(album = it,
                            { navController?.navigate(Rutas.PantallaAlbum.ruta + "/${it.nombre}") })
                    }
                }, modifier = Modifier
                    .padding(10.dp)
            )
        }
    }

    var tiempoParaSalir by remember { mutableStateOf(0L) }

    BackHandler {
        val tiempoActual = System.currentTimeMillis()
        if (tiempoActual - tiempoParaSalir > 2000) {
            Toast.makeText(
                context,
                "Pulsar otra vez para salir",
                Toast.LENGTH_SHORT
            ).show()
            tiempoParaSalir = tiempoActual
        } else {
//            exitProcess(0)
            (context as Activity).finishAffinity()
        }
    }
}

@Composable
fun AlbumItem(album: Album, onClick: () -> Unit) {
    Card(modifier = Modifier
        .size(150.dp)
        .padding(5.dp)
        .clickable { onClick() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(AppColors.verde)
        ) {
            Image(
                painter = painterResource(id = album.caratula),
                contentDescription = album.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier.weight(1f)
            )
            Text(text = album.nombre, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun AlbumItemPreview() {
//    AlbumItem(album = Albums.Nightmare, {})
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun PantallaPlaylistsPreview() {
//    val model: ScaffoldViewModel = viewModel()
//    PantallaPlaylists(model, null, context)
//}