package com.newcompose.composehiltmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.newcompose.composehiltmvvm.navigation.NavigationHost
import com.newcompose.composehiltmvvm.ui.theme.ComposeHiltMvvmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ComposeHiltMvvmTheme {
                NavigationHost(navHostController = navController)
            }
        }
    }
}