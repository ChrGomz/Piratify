package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.katarem.piratify.R
import io.github.katarem.piratify.model.Cancion
import io.github.katarem.piratify.model.ReproductorModel

@Composable
fun PantallaPrincipal(){
    val model : ReproductorModel = viewModel()
    val cancionActual = model.currentSong.collectAsState().value
    Column(
        Modifier.fillMaxSize()
    ){
        Header()
        SongInfoText(cancionActual)
        Image(painter = painterResource(id = cancionActual.portada),
            contentDescription = "",
            Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop,)
        PlayerControls(model, cancionActual)
    }
}

@Composable
fun SongInfoText(cancionActual: Cancion){
    Column (
        Modifier
            .fillMaxWidth()
            .padding(10.dp)){
        Text(text = "Now playing", fontSize = 25.sp)
        Text(text = cancionActual.nombre + " - " + cancionActual.artista, fontSize = 25.sp)
    }
}



@Composable
fun Header(){
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(R.color.spotify_green))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        TextButton(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = R.drawable.back_arrow), contentDescription = "")
        }
        Text(text = "Piratify", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PlayerControls(model: ReproductorModel, cancionActual: Cancion){

    val context = LocalContext.current

    val isShuffle = model.isShuffle.collectAsState()
    val isRepeat = model.isRepeating.collectAsState()
    var playIcon = R.drawable.play
    var repeatIcon = R.drawable.repeat
    var shuffleIcon = R.drawable.shuffle

    if(isRepeat.value) repeatIcon = R.drawable.activated_repeat
    if(isShuffle.value) shuffleIcon = R.drawable.activated_shuffle

    LaunchedEffect(Unit){
        model.createExoPlayer(context)
        model.playSong(context)
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
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
                TextButton(onClick = { model.changeShuffleState(!isShuffle.value) }){
                    Image(painter = painterResource(id = shuffleIcon), contentDescription = "")
                }
                TextButton(onClick = { model.prevSong(context) }){
                    Image(painter = painterResource(id = R.drawable.prev), contentDescription = "")
                }
                Button(onClick = { model.playOrPause()
                                    if(model.isPlaying) playIcon = R.drawable.baseline_pause_24
                                    else playIcon = R.drawable.play}, modifier = Modifier
                    .size(70.dp)
                    .clip(
                        CircleShape
                    )){
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
        UIControls()
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
    val progreso = model.progreso.collectAsState()

    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Slider(value = 0f, onValueChange = {})
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = durationParsed((progreso.value/1000)))
            Text(text = cancionActual.duracion)
        }
    }
}



@Composable
fun UIControls(){
    Row(
        Modifier
            .background(Color(R.color.spotify_green))
            .height(80.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { /*TODO*/ }){
            Image(painter = painterResource(id = R.drawable.home), contentDescription = "")
        }
        TextButton(onClick = { /*TODO*/ }){
            Image(painter = painterResource(id = R.drawable.search), contentDescription = "")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPrincipalPreview(){
    PantallaPrincipal()
}