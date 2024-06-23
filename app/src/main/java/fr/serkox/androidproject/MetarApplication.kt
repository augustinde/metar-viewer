package fr.serkox.androidproject

import android.app.Application
import fr.serkox.androidproject.data.AppContainer
import fr.serkox.androidproject.data.DefaultAppContainer

class MetarApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}