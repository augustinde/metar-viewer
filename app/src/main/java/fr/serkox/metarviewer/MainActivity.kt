package fr.serkox.metarviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.serkox.metarviewer.ui.screen.MetarViewerApp
import fr.serkox.metarviewer.ui.theme.MetarViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetarViewerTheme {
                MetarViewerApp()
            }
        }
    }
}