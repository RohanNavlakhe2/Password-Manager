package com.yog.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yog.passwordmanager.db.PasswordEntry
import com.yog.passwordmanager.presentation.AddUpdatePasswordScreen
import com.yog.passwordmanager.presentation.PasswordEntriesScreen
import com.yog.passwordmanager.ui.theme.PasswordManagerTheme
import com.yog.passwordmanager.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.PasswordListScreen.route
                    ) {

                        composable(route = Screen.PasswordListScreen.route) {
                            PasswordEntriesScreen(navController)
                        }

                        composable(
                            route = Screen.AddUpdatePasswordScreen.route +
                                    "?passwordEntryId={passwordEntryId}",
                            arguments = listOf(
                                navArgument(
                                    name = "passwordEntryId",
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddUpdatePasswordScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PasswordManagerTheme {

    }
}