package io.github.katarem.piratify.model

import androidx.lifecycle.ViewModel
import io.github.katarem.piratify.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReproductorModel: ViewModel() {

    //index de la cancion
    private val _index = MutableStateFlow(0)
    val index = _index.asStateFlow()

    //Cancion actual designada
    private val canciones = listOf<Cancion>(
        Cancion("Rip & Tear",R.drawable.doom_album,"Doomguy","4:18","Doom OST"),
        Cancion("El Arte Sano",R.drawable.keyblade,"Keyblade","5:36",""),
        Cancion("El Violador",R.drawable.piterg,"Piter G","3:18",""),
        Cancion("Es Épico",R.drawable.muerte_canserbero,"Canserbero","6:01","Muerte"),
        Cancion("Maw",R.drawable.maw,"Gato","0:16","miau")
    )
    private var _currentSong = MutableStateFlow(canciones[index.value])
    val currentSong = _currentSong.asStateFlow()

    //Titulo de canción
    private var _currentSongTitle = MutableStateFlow(_currentSong.value.nombre)
    val currentSongTitle = _currentSongTitle.asStateFlow()

    //Duración
    private var _songDuration = MutableStateFlow(_currentSong.value.duracion)
    val songDuration = _songDuration.asStateFlow()

    //Imagen
    private var _currentSongImage = MutableStateFlow(_currentSong.value.portada)
    val currentSongImage = _currentSongImage.asStateFlow()

    //Está Repeat
    private var _isRepeating = MutableStateFlow(false)
    val isRepeating = _isRepeating.asStateFlow()

    //Está reproduciendo
    private var _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    //Está Shuffled
    private var _isShuffle = MutableStateFlow(false)
    val isShuffle = _isShuffle.asStateFlow()
    fun changePlayingState(newState: Boolean){
        _isPlaying.value = newState
    }

    fun changeRepeatingState(newState: Boolean){
        _isRepeating.value = newState
    }

    fun changeShuffleState(newState: Boolean){
        _isShuffle.value = newState
    }

    fun changeCurrentSongTitle(newSongTitle: String){
        _currentSongTitle.value = newSongTitle
    }

    fun changeSongDuration(newDuration: String){
        _songDuration.value = newDuration
    }

    fun changeSongImage(newImage: Int){
        _currentSongImage.value = newImage
    }

    //TODO optimizar estas funciones para ver si es posible o no realizarse
    fun nextSong(){
        _index.value += 1
    }

    fun prevSong(){
        _index.value -= 1
    }

}