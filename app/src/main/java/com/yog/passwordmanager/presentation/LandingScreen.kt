package com.yog.passwordmanager.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.yog.passwordmanager.presentation.components.LoginPasswordTextField
import com.yog.passwordmanager.presentation.components.LoginTextField
import com.yog.passwordmanager.ui.theme.Dimens
import com.yog.passwordmanager.ui.theme.LoginBg
import kotlinx.coroutines.launch

@Preview
@Composable
fun LandingScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(LoginBg)
            .padding(Dimens.LoginPadding)
    ) {

        RegisterLoginTab(
            Modifier.padding(top = 50.dp)
        )
    }

}

@Composable
fun RegisterComponent() {
    Column() {
        LoginTextField(value = "", onValueChange = {}, hint = "Name")
        Spacer(modifier = Modifier.height(Dimens.FormFieldVerticalSpacing))
        LoginTextField(value = "", onValueChange = {}, hint = "User Name")
        Spacer(modifier = Modifier.height(Dimens.FormFieldVerticalSpacing))
        LoginPasswordTextField(value = "", onValueChange = {}, hint = "Password", false, {})
        Spacer(modifier = Modifier.height(Dimens.FormFieldVerticalSpacing))
        LoginPasswordTextField(value = "", onValueChange = {}, hint = "Confirm Password", false, {})
        Spacer(modifier = Modifier.height(Dimens.FormFieldVerticalSpacing + 5.dp))
        Button(onClick = {

        }) {
          Text("Register")
        }


    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterLoginTab(modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf("Register", "Login")

    val pagerState = rememberPagerState(
        pageCount = tabs.size
    )

    val tabIndex = pagerState.currentPage

    Column(modifier) {
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier.border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(10.dp)),
            indicator = {},
            backgroundColor = Color.Transparent,
            divider = {}
        ) {
            tabs.forEachIndexed { index, tabName ->
                Tab(
                    selected = index == tabIndex,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier
                        .background(
                            shape = if(index == 0)  RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                        else RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
                            color = if(index == tabIndex) MaterialTheme.colors.primary else Color.White
                        ),
                    text = {
                        Text(
                            text = tabName,
                            color = if (index == tabIndex) Color.White else Color.Black,
                        )
                    },
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalPager(state = pagerState) {
            if (pagerState.currentPage == 0) {
                RegisterComponent()
            } else if (pagerState.currentPage == 1) {
                Text("Login")
            }
        }
    }


}