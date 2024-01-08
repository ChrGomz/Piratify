package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.katarem.piratify.R
import io.github.katarem.piratify.entities.Album
import io.github.katarem.piratify.entities.Albums
import io.github.katarem.piratify.entities.Cancion
import io.github.katarem.piratify.entities.Playlists

@Composable
fun PantallaAlbum(album: Album, navController: NavController?){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        AlbumDescription(album = album)
        Button(onClick = { navController?.navigate(Rutas.PantallaReproductor.ruta + "/${album.nombre}/0/true") }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Modo aleatorio")
        }
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .weight(1f),content = {
            itemsIndexed(album.canciones){
                index,cancion ->
                    CancionItem(index = index, cancion = cancion, onClick = { navController?.navigate(Rutas.PantallaReproductor.ruta + "/${album.nombre}/$index/false") })
            }
        })
        Text(text = "${album.canciones.size} canciones - ${duracionTotal(album)}", fontSize = 20.sp)
    }

}

@Composable
fun CancionItem(index: Int, cancion: Cancion,onClick: () -> Unit){
    Row(modifier = Modifier
        .height(35.dp)
        .fillMaxWidth()
        .clickable { onClick() }, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "${index+1}. ${cancion.nombre}", fontSize = 20.sp)
        Text(text = cancion.duracion, fontSize = 20.sp)
    }
}



fun duracionTotal(album: Album): String{
    val duraciones = arrayListOf<Int>()
    album.canciones.map { cancion -> cancion.duracion }.forEach{ duraciones.add(durationToInt(it)) }
    val duracionAlbum = durationParsed(duraciones.sum())
    val horas = duracionAlbum.split(":")[0].toInt()/60
    val minutos = duracionAlbum.split(":")[1].toInt()
    return if(horas < 1)"$minutos min" else "$horas h $minutos min"
}

fun durationToInt(value: String): Int{
    val mins = value.split(":")[0]
    val segs = value.split(":")[1]
    return (mins.toInt()*60)+segs.toInt()
}



@Composable
fun AlbumDescription(album: Album){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = album.caratula), contentDescription = "",
            contentScale = ContentScale.Crop, modifier = Modifier.size(200.dp))
        Text(text = album.nombre, fontSize = 20.sp)
        Text(text = "${album.artista} - ${album.anio}", fontSize = 20.sp)
    }
}



@Preview(showBackground = true)
@Composable
fun PantallaAlbumPreview(){
    PantallaAlbum(album = Albums.Muerte, navController = null)
}
