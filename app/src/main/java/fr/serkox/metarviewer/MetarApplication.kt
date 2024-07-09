package fr.serkox.metarviewer

import android.app.Application
import fr.serkox.metarviewer.data.AppContainer
import fr.serkox.metarviewer.data.DefaultAppContainer

class MetarApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}