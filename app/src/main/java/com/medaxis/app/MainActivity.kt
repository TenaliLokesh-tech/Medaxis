package com.medaxis.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.medaxis.app.ui.navigation.MedaxisNavHost
import com.medaxis.app.ui.theme.MedaxisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedaxisTheme {
                MedaxisNavHost()
            }
        }
    }
}
