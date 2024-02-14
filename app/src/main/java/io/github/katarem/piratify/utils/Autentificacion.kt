package io.github.katarem.piratify.utils

import android.content.Context
import com.google.firebase.auth.FirebaseAuth


fun loginUser(context: Context, email: String, password: String, onError: () -> Unit, onSuccess: () -> Unit){
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
//                val intent = Intent(context, Rutas.PantallaPlaylists::class.java)
//                context.startActivity(intent)
            } else onError()
        }
}

fun registerUser(context: Context, email: String, password: String, onError: () -> Unit, onSuccess: () -> Unit) {
    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
//                val intent = Intent(context, Rutas.PantallaPlaylists::class.java)
//                context.startActivity(intent)
            } else onError()
        }
}
