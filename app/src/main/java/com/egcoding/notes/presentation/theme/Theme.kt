package com.ernestgonzalezv.cleanarchitecturenoteapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.egcoding.notes.presentation.theme.Typography

val DarkGray = Color(0xFF202020)
val LightBlue = Color(0xFFD7E8DE)

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    background = DarkGray,
    onBackground = Color.White,
    surface = DarkGray,
    onSurface = Color.White,
    secondary = Color.LightGray,
    tertiary = LightBlue
)

private val LightColorScheme = lightColorScheme(
    primary = DarkGray,
    background = Color.White,
    onBackground = DarkGray,
    surface = Color.White,
    onSurface = DarkGray,
    secondary = Color.Gray,
    tertiary = Color(0xFF506659)
)

@Composable
fun NotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        // Usamos un LaunchedEffect o SideEffect solo para el contraste de los iconos
        SideEffect {
            val window = (view.context as Activity).window
            // Solo controlamos si los iconos de estado son claros u oscuros
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}