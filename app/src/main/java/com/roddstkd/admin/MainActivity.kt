package com.roddstkd.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.roddstkd.admin.ui.AdminApp
import com.roddstkd.admin.ui.theme.RoddsTKDAdminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoddsTKDAdminTheme {
                AdminApp()
            }
        }
    }
}
