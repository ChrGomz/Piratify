package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.katarem.piratify.R
import io.github.katarem.piratify.entities.Album
import io.github.katarem.piratify.entities.Albums
import io.github.katarem.piratify.entities.Cancion
import io.github.katarem.piratify.model.ReproductorModel
import io.github.katarem.piratify.utils.AppColors

@Composable
fun PantallaReproductor(album: Album, index: Int, isShuffle: Boolean){
    val model : ReproductorModel = viewModel()
    val context = LocalContext.current

    val cancionActual = model.currentSong.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .background(AppColors.negro),
        verticalArrangement = Arrangement.Center
    ){
        SongInfoText(cancionActual.value)
        Image(painter = painterResource(id = cancionActual.value.portada),
            contentDescription = "",
            Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop,)
        PlayerControls(model, cancionActual.value,album, index,isShuffle)
    }
}

@Composable
fun SongInfoText(cancionActual: Cancion){
    Column (
        Modifier
            .fillMaxWidth()
            .padding(10.dp)){
        Text(text = "Now playing", fontSize = 25.sp, color = Color.White)
        Text(text = cancionActual.nombre + " - " + cancionActual.artista, fontSize = 25.sp, color = Color.White)
    }
}

@Composable
fun PlayerControls(model: ReproductorModel, cancionActual: Cancion, album: Album,index: Int, shuffleMode: Boolean){

    val context = LocalContext.current
    val isPlaying = model.isPlaying.collectAsState()
    val isShuffle = model.isShuffle.collectAsState()
    val isRepeat = model.isRepeating.collectAsState()
    var playIcon = R.drawable.pause
    var repeatIcon = R.drawable.repeat
    var shuffleIcon = R.drawable.shuffle

    if(isRepeat.value) repeatIcon = R.drawable.activated_repeat
    if(isShuffle.value) shuffleIcon = R.drawable.activated_shuffle
    if(!isPlaying.value) playIcon = R.drawable.play
    LaunchedEffect(Unit){
        //setteamos el album que recibimos
        model.changeAlbum(album,index,shuffleMode)
        model.createExoPlayer(context)
        model.playSong(context)
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            SliderView(cancionActual)
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { model.changeShuffleState(context,!isShuffle.value) }){
                    Image(painter = painterResource(id = shuffleIcon), contentDescription = "")
                }
                TextButton(onClick = { model.prevSong(context) }){
                    Image(painter = painterResource(id = R.drawable.prev), contentDescription = "")
                }
                Button(onClick = { model.playOrPause() }, modifier = Modifier
                    .size(70.dp)
                    .clip(
                        CircleShape
                    ),colors = ButtonDefaults.buttonColors(containerColor = AppColors.verde)
                ){
                    Image(painter = painterResource(id = playIcon), contentDescription = "", modifier = Modifier.fillMaxSize())
                }
                TextButton(onClick = { model.nextSong(context) }){
                    Image(painter = painterResource(id = R.drawable.next), contentDescription = "")
                }
                TextButton(onClick = {  model.changeRepeatingState(!isRepeat.value) }){
                    Image(painter = painterResource(id = repeatIcon), contentDescription = "")
                }
            }

        }
    }
}

fun durationParsed(tiempo: Int): String{
    val minutos = tiempo/60
    val segundos = tiempo - (minutos*60)
    var minutosString = "" + minutos
    var segundosString = "" + segundos
    if(segundos<10) segundosString = "0$segundosString"
    if(minutos<10) minutosString = "0$minutosString"
    return "$minutosString:$segundosString"
}




@Composable
fun SliderView(cancionActual: Cancion){
    val model: ReproductorModel = viewModel()
    val duracion = model.duracion.collectAsState()
    val progreso = model.progreso.collectAsState()

    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Slider(value = progreso.value.toFloat(), onValueChange = { model.changeProgreso(it.toInt())}, valueRange = 0f..duracion.value.toFloat(),
            colors = SliderDefaults.colors(thumbColor = AppColors.verde))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = durationParsed((progreso.value/1000)), color = Color.White)
            Text(text = durationParsed(duracion.value/1000), color = Color.White)
        }
    }
}






@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPrincipalPreview(){
    PantallaReproductor(album = Albums.Nightmare,0,false)
}