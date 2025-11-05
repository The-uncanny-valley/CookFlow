package com.uncannyvalley.cookflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uncannyvalley.cookflow.presentation.navigation.CookFlowNavHost
import com.uncannyvalley.cookflow.presentation.screen.HomeScreen
import com.uncannyvalley.cookflow.presentation.theme.CookFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookFlowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CookFlowNavHost()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    CookFlowTheme {
        HomeScreen()
    }
}