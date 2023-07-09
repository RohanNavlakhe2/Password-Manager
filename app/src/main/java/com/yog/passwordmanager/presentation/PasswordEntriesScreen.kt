package com.yog.passwordmanager.presentation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yog.passwordmanager.R
import com.yog.passwordmanager.presentation.viewmodel.PasswordEntriesViewModel
import com.yog.passwordmanager.ui.theme.Green
import com.yog.passwordmanager.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PasswordEntriesScreen(
    navController: NavController, viewModel: PasswordEntriesViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val passwordDataState = viewModel.passwordDataState

    val arePasswordCheckboxesVisibleState = rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.AddUpdatePasswordScreen.route)
            }, backgroundColor = Green
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.Save),
                tint = Color.White
            )
        }
    }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Green)
                        .padding(dimensionResource(id = R.dimen.dimen20dp))
                ) {
                    Text(
                        text = "Your Passwords",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    if(arePasswordCheckboxesVisibleState.value){
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable {
                                    viewModel.deleteSelectedPasswordEntries()
                                }
                        )
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                if (passwordDataState.isNotEmpty()) {
                    LazyColumn(content = {


                        items(passwordDataState.size) {

                            val passwordEntry = passwordDataState[it]

                            //rememberSaveable maintains the state in configuaration changes like screen
                            //orientation as well.

                            //We could create the state in the viewmodel to achieve the same behavior (as we have done
                            //at other places)
                            val isPasswordVisibleState = rememberSaveable {
                                mutableStateOf(false)
                            }


                            //Here we needed to use rememberUpdatedState{} because we're referencing
                            //PasswordEntry here. if we don't use rememberUpdatedState{} and directly use
                            //passwordEntry inside onTap callback then when we update the state we don't get
                            //new value of passwordEntry.isPasswordChecked there.
                            val onTap = rememberUpdatedState(newValue = {
                                if (arePasswordCheckboxesVisibleState.value) {
                                    viewModel.updateCheckedPassword(
                                        it,
                                        !passwordEntry.isPasswordChecked
                                    )
                                } else {
                                    navController.navigate(
                                        Screen.AddUpdatePasswordScreen.route + "?passwordEntryId=${passwordEntry.id}"
                                    )
                                }
                            })


                            Column(modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .border(0.9.dp, Color.Green, RoundedCornerShape(10.dp))
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }, indication = rememberRipple(
                                        bounded = true, radius = 120.dp, color = Green
                                    )
                                ) {

                                }
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = { offset ->
                                            arePasswordCheckboxesVisibleState.value = true
                                            viewModel.updateCheckedPassword(it, true)
                                            Log.d("Krishna", "On Long Press : $it")
                                        },
                                        onTap = {
                                            onTap.value.invoke()
                                        }
                                    )
                                }
                                .padding(20.dp)
                            ) {
                                if (arePasswordCheckboxesVisibleState.value) {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Checkbox(checked = /*isPasswordSelectedState.value*/ passwordEntry.isPasswordChecked,
                                            onCheckedChange = { isChecked ->
                                                //isPasswordSelectedState.value = it
                                                viewModel.updateCheckedPassword(it, isChecked)
                                            }
                                        )
                                    }
                                }
                                Text(
                                    text = passwordEntry.title,
                                    style = MaterialTheme.typography.subtitle1
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    BasicTextField(
                                        value = passwordEntry.password, onValueChange = {},
                                        visualTransformation = if (isPasswordVisibleState.value) VisualTransformation.None else PasswordVisualTransformation(),
                                        enabled = false
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        imageVector = if (isPasswordVisibleState.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                        contentDescription = "Toggle Password",
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                isPasswordVisibleState.value =
                                                    !isPasswordVisibleState.value
                                            },
                                    )

                                }
                            }
                        }
                    })
                }


            }
            if (passwordDataState.isEmpty()) Text(
                "No Saved Passwords",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    //If enabled = true means the screen will not pop back.
    //So here if the password selection checkboxes are visible to the user and if user do system back then
    //we're removing the checkboxes. Now if user again does system back then actual back will happen and the screen
    //will be popped up.
    BackHandler(enabled = arePasswordCheckboxesVisibleState.value) {
        if (arePasswordCheckboxesVisibleState.value) {
            arePasswordCheckboxesVisibleState.value = false
            viewModel.unCheckAll()
        }
    }
}