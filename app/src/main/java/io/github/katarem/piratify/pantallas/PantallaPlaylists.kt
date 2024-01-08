package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.katarem.piratify.entities.Album
import io.github.katarem.piratify.entities.Albums
import io.github.katarem.piratify.model.ScaffoldViewModel

@Composable
fun PantallaPlaylists(model: ScaffoldViewModel, navController: NavController?) {

    val albums = model.albums.collectAsState()
    val playlists = model.playlists.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(text = "Últimos álbumes que te pueden gustar")
        LazyRow(content = {
           items(albums.value){
               AlbumItem(album = it, { navController?.navigate(Rutas.PantallaAlbum.ruta + "/${it.nombre}")})
           }
        }, modifier = Modifier
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround)
        Text(text = "Sigue escuchando tu música favorita")
        LazyRow(content = {
            items(playlists.value){
                AlbumItem(album = it,{ navController?.navigate(Rutas.PantallaAlbum.ruta + "/${it.nombre}")})
            }
        },modifier = Modifier
            .padding(10.dp))
        }
}

@Composable
fun AlbumItem(album: Album, onClick: () -> Unit){
    Card(modifier = Modifier.size(150.dp).padding(5.dp).clickable { onClick() }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = album.caratula), contentDescription = album.nombre, contentScale = ContentScale.Crop, modifier = Modifier.weight(1f))
            Text(text = album.nombre, textAlign = TextAlign.Center)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AlbumItemPreview(){
    AlbumItem(album = Albums.Nightmare,{})
}


@Preview(showBackground = true)
@Composable
fun PantallaPlaylistsPreview(){
    val model: ScaffoldViewModel = viewModel()
    PantallaPlaylists(model,null)
}