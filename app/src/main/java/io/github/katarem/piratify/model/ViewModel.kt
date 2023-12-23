package io.github.katarem.piratify.model

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import io.github.katarem.piratify.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ReproductorModel: ViewModel() {

    //index de la cancion
    private val _index = MutableStateFlow(0)
    val index = _index.asStateFlow()

    //Cancion actual designada
    private val canciones = listOf<Cancion>(
        Cancion("Rip & Tear",R.drawable.doom_album,"Doomguy","4:18","Doom OST",R.raw.rip_and_tear_doom),
        Cancion("El Arte Sano",R.drawable.keyblade,"Keyblade","5:36","",R.raw.el_arte_sano_keyblade),
        Cancion("El Violador",R.drawable.piterg,"Piter G","3:18","",R.raw.el_violador_piterg),
        Cancion("Es Épico",R.drawable.muerte_canserbero,"Canserbero","6:01","Muerte",R.raw.es_epico_canserbero),
        Cancion("Maw",R.drawable.maw,"Gato","0:16","miau",R.raw.maw_master)
    )

    private val _exoPlayer: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()


    private var _currentSong = MutableStateFlow(canciones[index.value])
    val currentSong = _currentSong.asStateFlow()

    //Duración total
    private var _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    //Progreso actual de la canción
    private var _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    //Está Repeat
    private var _isRepeating = MutableStateFlow(true)
    val isRepeating = _isRepeating.asStateFlow()

    //Está Shuffled
    private var _isShuffle = MutableStateFlow(false)
    val isShuffle = _isShuffle.asStateFlow()

    var isPlaying = false

    fun changeRepeatingState(newState: Boolean){
        _isRepeating.value = newState
    }

    fun changeShuffleState(newState: Boolean){
        _isShuffle.value = newState
    }

    fun createExoPlayer(context: Context){
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        exoPlayer.value!!.prepare()
    }

    fun playSong(context: Context){
        val item = MediaItem.fromUri(obtenerRuta(context,currentSong.value.media))
        exoPlayer.value!!.setMediaItem(item)
        exoPlayer.value!!.play()
        exoPlayer.value!!.addListener(object: Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                when(playbackState){
                    Player.STATE_READY -> {
                        _duracion.value = _exoPlayer.value!!.duration.toInt()
                        viewModelScope.launch {
                            /* TODO: Actualizar el progreso usando currentPosition cada segundo */
                            while(isActive){
                                _progreso.value = _exoPlayer.value!!.currentPosition.toInt()
                                delay(1000)
                            }
                        }
                    }
                    Player.STATE_BUFFERING->{}//nada basicamente
                    Player.STATE_ENDED->{nextSong(context)}
                    Player.STATE_IDLE->{}
                }
            }
        })
    }
    // Este método se llama cuando el VM se destruya.
    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }

    fun playOrPause() {
        if (_exoPlayer.value!!.isPlaying){
            _exoPlayer.value!!.pause()
            isPlaying = false
        }else {
            _exoPlayer.value!!.play()
            isPlaying = true
        }
    }


    fun CambiarCancion(context: Context) {
        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()
        _exoPlayer.value!!.setMediaItem(MediaItem.fromUri(obtenerRuta(context, _currentSong.value.media)))
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun nextSong(context: Context){
        if(_index.value==canciones.size-1 && isRepeating.value){
            _index.value = 0
            _currentSong.value = canciones[_index.value]
            CambiarCancion(context)
        }
        else if(_index.value < canciones.size-1){
            _index.value += 1
            _currentSong.value = canciones[_index.value]
            CambiarCancion(context)
        }
    }

    fun prevSong(context: Context){
        if(_index.value==0 && isRepeating.value){
            _index.value = canciones.size-1
            _currentSong.value = canciones[_index.value]
            CambiarCancion(context)
        }
        else if(_index.value > 0){
            _index.value -= 1
            _currentSong.value = canciones[_index.value]
            CambiarCancion(context)
        }
    }

    // Funcion auxiliar que devuelve la ruta de un fichero a partir de su ID
    @Throws(Resources.NotFoundException::class)
    fun obtenerRuta(context: Context, @AnyRes resId: Int): Uri {
        val res: Resources = context.resources
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + res.getResourcePackageName(resId)
                    + '/' + res.getResourceTypeName(resId)
                    + '/' + res.getResourceEntryName(resId)
        )
    }


}