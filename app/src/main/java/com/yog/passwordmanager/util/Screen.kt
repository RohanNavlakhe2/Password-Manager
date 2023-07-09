package com.yog.passwordmanager.util

sealed class Screen(val route:String){
    object PasswordListScreen: Screen("password_list_screen_route")
    object AddUpdatePasswordScreen: Screen("add_update_screen_route")
}
