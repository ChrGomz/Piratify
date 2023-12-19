package io.github.katarem.piratify.model

import androidx.lifecycle.ViewModel
import io.github.katarem.piratify.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Model: ViewModel() {

    //Titulo de canción
    private var _currentSongTitle = MutableStateFlow("Rip & Tear")
    val currentSongTitle = _currentSongTitle.asStateFlow()

    //Duración
    private var _songDuration = MutableStateFlow("3:40")
    val songDuration = _songDuration.asStateFlow()

    //Imagen
    private var _currentSongImage = MutableStateFlow(R.drawable.doom_album)
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


}