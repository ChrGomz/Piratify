package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.katarem.piratify.R
import io.github.katarem.piratify.model.ReproductorModel

@Composable
fun PantallaPrincipal(){
    val model = ReproductorModel()
    val portadaCancion = model.currentSongImage.collectAsState()
    Column(
        Modifier.fillMaxSize()
    ){
        Header()
        SongInfoText()
        Image(painter = painterResource(id = portadaCancion.value),
            contentDescription = "",
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentScale = ContentScale.Crop,)
        PlayerControls()
    }
}

@Composable
fun SongInfoText(){
    val model = ReproductorModel()
    val nombreCancion = model.currentSongTitle.collectAsState()
    Column (
        Modifier
            .fillMaxWidth()
            .padding(10.dp)){
        Text(text = "Now playing", fontSize = 20.sp)
        Text(text = nombreCancion.value, fontSize = 20.sp)
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
fun PlayerControls(){
    val model = ReproductorModel()
    val isShuffle = model.isShuffle.collectAsState()
    val isRepeat = model.isRepeating.collectAsState()
    val isPlaying = model.isPlaying.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SliderView()
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { model.changeShuffleState(!isShuffle.value) }){
                    Image(painter = painterResource(id = R.drawable.shuffle), contentDescription = "")
                }
                TextButton(onClick = { model.prevSong() }){
                    Image(painter = painterResource(id = R.drawable.prev), contentDescription = "")
                }
                TextButton(onClick = { model.changePlayingState(!isPlaying.value) }){
                    Image(painter = painterResource(id = R.drawable.play), contentDescription = "")
                }
                TextButton(onClick = { model.nextSong() }){
                    Image(painter = painterResource(id = R.drawable.next), contentDescription = "")
                }
                TextButton(onClick = {  model.changeRepeatingState(!isRepeat.value) }){
                    Image(painter = painterResource(id = R.drawable.baseline_repeat_24), contentDescription = "")
                }
            }

        }
        UIControls()
    }
}

@Composable
fun SliderView(){
    val model = ReproductorModel()
    val songDuration = model.songDuration.collectAsState()
    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Slider(value = 0f, onValueChange = {})
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "00:00")
            Text(text = songDuration.value)
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