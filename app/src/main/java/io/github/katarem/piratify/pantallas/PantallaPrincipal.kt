package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
        Modifier
            .fillMaxSize()
            .background(Color(0xFF191414))
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
        Text(text = "Now playing", fontSize = 25.sp, color = Color.White)
        Text(text = cancionActual.nombre + " - " + cancionActual.artista, fontSize = 25.sp, color = Color.White)
    }
}



@Composable
fun Header(){
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFF1db954))
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
                    ),colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1db954))
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
    val duracion = model.duracion.collectAsState()
    val progreso = model.progreso.collectAsState()

    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Slider(value = progreso.value.toFloat(), onValueChange = { model.changeProgreso(it.toInt())}, valueRange = 0f..duracion.value.toFloat(),
            colors = SliderDefaults.colors(thumbColor = Color(0xFF1db954)))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = durationParsed((progreso.value/1000)), color = Color.White)
            Text(text = durationParsed(duracion.value/1000), color = Color.White)
        }
    }
}



@Composable
fun UIControls(){
    Row(
        Modifier
            .background(Color(0xFF1db954))
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