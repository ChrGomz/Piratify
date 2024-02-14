package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.katarem.piratify.R
import io.github.katarem.piratify.utils.AppColors
import io.github.katarem.piratify.utils.loginUser
import io.github.katarem.piratify.utils.registerUser

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PantallaLogin(navController: NavHostController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val errorPass = remember {
        mutableStateOf("")
    }
    var formulario by remember { mutableStateOf("logIn") }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.negro)
            .padding(16.dp)
    ) {
        if (formulario.equals("logIn")) {
            ContenidoLogin(
                username = username,
                onUsernameChange = { username = it },
                password = password,
                onPasswordChange = { password = it },
                errorPass = errorPass.value,
                onLoginClick = {
                    if (username.isBlank() || password.isBlank()) {
                        errorPass.value = "Es necesario rellenar todos los campos"
                    } else {
                        loginUser(
                            context,
                            username,
                            password, {
                    navController.navigate(Rutas.PantallaPlaylists.ruta)
                            }
                        ) {
                            errorPass.value = "Correo o contraseña inválidos"
                        }
                    }
                },
                onSinginClick = {
                    formulario = "signIn"
                },
                focusManager = focusManager,
                keyboardController = keyboardController
            )
        } else {
            ContenidoSingIn(
                username = username,
                onUsernameChange = { username = it },
                password = password,
                confirmPassword = confirmPassword,
                onPasswordChange = { password = it },
                onConfirmPasswordChange = { confirmPassword = it },
                errorPass = errorPass.value,
                onSignInClick = {
                    if (username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                        errorPass.value = "Es necesario rellenar todos los campos"
                    } else if (password != confirmPassword) {
                        errorPass.value = "Las contraseñas no coinciden"
                    } else {
                        registerUser(
                            context,
                            username,
                            confirmPassword,
                            {
                                navController.navigate(Rutas.PantallaPlaylists.ruta)
                            }) {
                            errorPass.value = "Correo o contraseña inválidos"
                        }
                    }
                },
                onLoginClick = {
                    formulario = "logIn"
                },
                focusManager = focusManager,
                keyboardController = keyboardController
            )

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContenidoLogin(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorPass: String,
    onLoginClick: () -> Unit,
    onSinginClick: () -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()

        TextoPiratify()

        EntradaUsuario(
            username = username, onUsernameChange = onUsernameChange, focusManager = focusManager
        )

        EntradaContrasenya(
            password = password,
            onPasswordChange = onPasswordChange,
            onLoginClick = onLoginClick,
            keyboardController = keyboardController
        )

        Text(
            text = errorPass,
            color = Color.Red,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        BotonEnviarFormulario(onClick = onLoginClick, "Iniciar sesion")

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            TextoRedirigir(onSigninClick = onSinginClick, "No tienes una cuenta?", "Registrame")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextoContinuarCon()

        Spacer(modifier = Modifier.height(8.dp))

        RedesSociales()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContenidoSingIn(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    confirmPassword: String,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    errorPass: String,
    onSignInClick: () -> Unit,
    onLoginClick: () -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()

        TextoPiratify()

        EntradaUsuario(
            username = username, onUsernameChange = onUsernameChange, focusManager = focusManager
        )

        RegistrarContrasenya(
            password = password,
            confirmPassword = confirmPassword,
            onPasswordChange = onPasswordChange,
            onConfirmPasswordChange = onConfirmPasswordChange,
            onSingInClick = onSignInClick,
            focusManager = focusManager,
            keyboardController = keyboardController
        )

        Text(
            text = errorPass,
            color = Color.Red,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        BotonEnviarFormulario(
            onClick = onSignInClick,
            "Registrarse"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            TextoRedirigir(onSigninClick = onLoginClick, "Ya tengo una cuenta, ", "Iniciar sesion")
        }

    }
}

@Composable
fun Logo() {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.piratify),
        contentDescription = "Logo",
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
    )
}

@Composable
fun TextoPiratify(size: TextUnit = 42.sp) {
    Text(
        text = "Piratify",
        fontSize = size,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradaUsuario(
    username: String, onUsernameChange: (String) -> Unit, focusManager: FocusManager
) {
    OutlinedTextField(
        value = username,
        onValueChange = { onUsernameChange(it) },
        label = { Text(text = "Usuario", color = Color.White) },
        placeholder = { Text(text = "Ejemplo@correo.com", color = Color.White) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            unfocusedContainerColor = AppColors.negro,
            focusedContainerColor = AppColors.verde
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EntradaContrasenya(
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        label = { Text(text = "Contraseña") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onDone = {
            onLoginClick()
            keyboardController?.hide()
        }),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            unfocusedContainerColor = AppColors.negro,
            focusedContainerColor = AppColors.verde
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrarContrasenya(
    password: String,
    confirmPassword: String,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSingInClick: () -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {
    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        label = { Text(text = "Contraseña") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            unfocusedContainerColor = AppColors.negro,
            focusedContainerColor = AppColors.verde
        )
    )
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = { onConfirmPasswordChange(it) },
        label = { Text(text = "Repetir Contraseña") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onDone = {
            if (password == confirmPassword) {
                onSingInClick()
                keyboardController?.hide()
            } else {
//                Toast.makeText(LocalContext.current, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            unfocusedContainerColor = AppColors.negro,
            focusedContainerColor = AppColors.verde
        )
    )
}


@Composable
fun BotonEnviarFormulario(onClick: () -> Unit, textoBoton: String) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = AppColors.verde
        )
    ) {
        Text(text = textoBoton)
    }
}

@Composable
fun TextoRedirigir(onSigninClick: () -> Unit, textoInformacion: String, textoRedirigir: String) {
    Row {
        Text(
            text = textoInformacion, color = Color.DarkGray
        )
        Text(text = textoRedirigir,
            color = AppColors.verde,
            modifier = Modifier.clickable { onSigninClick() })
    }
}

@Composable
fun TextoContinuarCon() {
    Text(text = "Continúa también con", color = Color.DarkGray)
}

@Composable
fun RedSocial(imageRes: Int) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(color = AppColors.verde, shape = CircleShape)
            .padding(2.dp), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Logo",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(color = AppColors.negro, shape = CircleShape)
        )
    }
}

@Composable
fun RedesSociales() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        RedSocial(imageRes = R.drawable.googlelogo)
        RedSocial(imageRes = R.drawable.twitterlogo)
        RedSocial(imageRes = R.drawable.logo_cl_ve)
    }
}
