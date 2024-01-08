package io.github.katarem.piratify.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import io.github.katarem.piratify.entities.Album
import io.github.katarem.piratify.entities.Albums
import io.github.katarem.piratify.entities.Playlists
import io.github.katarem.piratify.pantallas.Rutas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScaffoldViewModel: ViewModel(){

    //Aquí tenemos los álbumes y playlists que usamos en la app
    private var _albums = MutableStateFlow(Albums.albums)

    private var _playlists = MutableStateFlow(Playlists.playlists)

    private var _rutaActual = MutableStateFlow(Rutas.PantallaPlaylists.ruta)

    val rutaActual = _rutaActual.asStateFlow()
    val albums = _albums.asStateFlow()
    val playlists = _playlists.asStateFlow()

    fun changeRuta(newRuta: String){
        _rutaActual.value = newRuta
    }




}