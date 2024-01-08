package io.github.katarem.piratify

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.katarem.piratify.model.ScaffoldViewModel
import io.github.katarem.piratify.pantallas.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(goBack: () -> Unit){
    val model: ScaffoldViewModel = viewModel()
    val rutaActual = model.rutaActual.collectAsState()
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFF1db954))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        TextButton(onClick = { goBack() }) {
            Image(painter = painterResource(id = R.drawable.back_arrow), contentDescription = "")
        }
        if(rutaActual.value.equals(Rutas.PantallaAllCanciones)) SearchBar(
            query = "",
            onQueryChange = {},
            onSearch = {},
            active = true,
            onActiveChange = {}
        ) {
            
        }
        else Text(text = "Piratify", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}




@Composable
fun UIControls(gotoHome: () -> Unit, gotoAllCanciones: () -> Unit){
    Row(
        Modifier
            .background(Color(0xFF1db954))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { gotoHome() }){
            Image(painter = painterResource(id = R.drawable.home), contentDescription = "")
        }
        TextButton(onClick = { gotoAllCanciones() }){
            Image(painter = painterResource(id = R.drawable.search), contentDescription = "")
        }
    }
}