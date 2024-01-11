package io.github.katarem.piratify.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import io.github.katarem.piratify.entities.Album
import io.github.katarem.piratify.entities.Albums
import io.github.katarem.piratify.entities.Cancion
import io.github.katarem.piratify.entities.Playlists
import io.github.katarem.piratify.pantallas.Rutas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScaffoldViewModel: ViewModel(){

    //Aquí tenemos los álbumes y playlists que usamos en la app
    private var _albums = MutableStateFlow(Albums.albums)

    private val _allSongs = Albums.TodasLasCanciones.canciones
    private val _filteredSongs = MutableStateFlow(_allSongs)

    private var _playlists = MutableStateFlow(Playlists.playlists)

    private var _rutaActual = MutableStateFlow(Rutas.PantallaPlaylists.ruta)

    private var _query = MutableStateFlow("")

    val rutaActual = _rutaActual.asStateFlow()
    val albums = _albums.asStateFlow()
    val playlists = _playlists.asStateFlow()
    val query = _query.asStateFlow()
    val filteredSongs = _filteredSongs.asStateFlow()
    fun changeRuta(newRuta: String){
        _rutaActual.value = newRuta
    }
    fun changeQuery(newQuery: String){
        _query.value = newQuery
        filter(_query.value)
    }

    private fun filter(query: String){
        if(query.isEmpty()) _filteredSongs.value = Albums.TodasLasCanciones.canciones
        else _filteredSongs.value = _allSongs.filter { cancion -> cancion.nombre.startsWith(query,true) }
    }


}