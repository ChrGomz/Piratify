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

    //Lista de canciones
    private val canciones = listOf<Cancion>(
        Cancion("Rip & Tear",R.drawable.doom_album,"Doomguy","Doom OST",R.raw.rip_and_tear_doom),
        Cancion("El Arte Sano",R.drawable.keyblade,"Keyblade","",R.raw.el_arte_sano_keyblade),
        Cancion("El Violador",R.drawable.piterg,"Piter G","",R.raw.el_violador_piterg),
        Cancion("Es Épico",R.drawable.muerte_canserbero,"Canserbero","Muerte",R.raw.es_epico_canserbero),
        Cancion("Maw",R.drawable.maw,"Gato","miau",R.raw.maw_master)
    )
    //Lista para shuffle
    private val cancionesShuffled = canciones.shuffled()

    //index de la cancion
    private val _index = MutableStateFlow(0)

    val index = _index.asStateFlow()
    //Player
    private val _exoPlayer: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    //Canción actual
    private var _currentSong = MutableStateFlow(canciones[index.value])
    val currentSong = _currentSong.asStateFlow()

    //Duración total
    private var _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    //Progreso actual de la canción
    private var _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    //Está Repeat
    private var _isRepeating = MutableStateFlow(false)
    val isRepeating = _isRepeating.asStateFlow()

    //Está Shuffled
    private var _isShuffle = MutableStateFlow(false)
    val isShuffle = _isShuffle.asStateFlow()

    //Reproduciendo o no
    private var _isPlaying = MutableStateFlow(true)
    var isPlaying = _isPlaying.asStateFlow()

    //Repite o no
    fun changeRepeatingState(newState: Boolean){
        _isRepeating.value = newState
    }
    //Cambiar si mezclamos o no las canciones
    fun changeShuffleState(context: Context, newState: Boolean){
        _isShuffle.value = newState
        if(_isShuffle.value) _index.value = 0
    }
    //Crear Player
    fun createExoPlayer(context: Context){
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        exoPlayer.value!!.prepare()
    }
    //Reproducir
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
                    //Player.STATE_BUFFERING->{}//nada basicamente
                    Player.STATE_ENDED->{nextSong(context)}
                    //Player.STATE_IDLE->{}
                }
            }
        })
    }
    // Este método se llama cuando el VM se destruya.
    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }
    //Pausar o reproducir
    fun playOrPause() {
        if (_exoPlayer.value!!.isPlaying){
            _exoPlayer.value!!.pause()
            _isPlaying.value = false
        }else {
            _exoPlayer.value!!.play()
            _isPlaying.value = true
        }
    }
    //Cambiar canción XD
    fun CambiarCancion(context: Context) {
        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()
        _exoPlayer.value!!.setMediaItem(MediaItem.fromUri(obtenerRuta(context, _currentSong.value.media)))
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }
    //Siguiente canción
    fun nextSong(context: Context){
        if(_index.value==canciones.size-1 && isRepeating.value){
            _index.value = 0
            if(_isShuffle.value) _currentSong.value = cancionesShuffled[_index.value]
            else _currentSong.value = canciones[_index.value]
            CambiarCancion(context)
        }
        else if(_index.value < canciones.size-1){
            _index.value += 1
            if(_isShuffle.value) _currentSong.value = cancionesShuffled[_index.value]
            else _currentSong.value = canciones[_index.value]
            CambiarCancion(context)
        }
    }
    //Canción previa
    fun prevSong(context: Context){
        if(_index.value==0 && isRepeating.value){
            _index.value = canciones.size-1
            if(_isShuffle.value) _currentSong.value = cancionesShuffled[_index.value]
            else _currentSong.value = canciones[_index.value]
            CambiarCancion(context)
        }
        else if(_index.value > 0){
            _index.value -= 1
            if(_isShuffle.value) _currentSong.value = cancionesShuffled[_index.value]
            else _currentSong.value = canciones[_index.value]
            CambiarCancion(context)
        }
    }

    fun changeProgreso(progreso: Int){
        _exoPlayer.value!!.seekTo(progreso.toLong())
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