package com.vibhu.littlelemon.ui.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibhu.littlelemon.R
import com.vibhu.littlelemon.ui.keys.ApplicationKeys
import com.vibhu.littlelemon.ui.theme.LittleLemonColors
import com.vibhu.littlelemon.ui.theme.LittleLemonTypography
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.vibhu.littlelemon.ui.keys.LoginKeys
import com.vibhu.littlelemon.ui.navigation.Destinations

@Composable
fun Onboarding(navController: NavHostController){
    val paddingFormHorizontal = 20.dp

    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var firstNameHasIncorrectValue by remember {
        mutableStateOf(false)
    }
    var lastNameHasIncorrectValue by remember {
        mutableStateOf(false)
    }
    var emailHasIncorrectValue by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val preferences = context.getSharedPreferences(
        ApplicationKeys.preferences,
        Context.MODE_PRIVATE
    )
    var formHasIncorrectInput by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    )
    {
        Column(
            Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.125f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .fillMaxHeight()

                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.175f)
                    .background(LittleLemonColors.primary1),
                contentAlignment = Alignment.Center
            ){
                Text(
                    "Let's get to know you",
                    color = LittleLemonColors.secondary3,
                    style = LittleLemonTypography.cardTitle,

                    fontSize = 26.sp
                )
            }
            Box(
                Modifier
                    .fillMaxHeight(.2f)
                    .padding(horizontal = paddingFormHorizontal),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Personal Information",
                    fontFamily = LittleLemonTypography.fontFamilyKarla,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    letterSpacing = 1.sp,
                    color = LittleLemonColors.secondary4
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = paddingFormHorizontal),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                EntryField(
                    modifier = Modifier,
                    "First Name",
                    "Tilly",
                    firstName,
                    firstNameHasIncorrectValue
                ) {
                    firstNameHasIncorrectValue = it.isBlank()
                    firstName = it
                }
                Spacer(Modifier.fillMaxWidth().height(40.dp))
                EntryField(
                    modifier = Modifier,
                    "Last Name",
                    "Doe",
                    lastName,
                    lastNameHasIncorrectValue
                ) {
                    lastNameHasIncorrectValue = it.isBlank()
                    lastName = it
                }
                Spacer(Modifier.fillMaxWidth().height(40.dp))
                EntryField(
                    modifier = Modifier,
                    "Email",
                    "tillydoe@example.com",
                    email,
                    emailHasIncorrectValue
                ) {
                    emailHasIncorrectValue = it.isBlank()
                    email = it
                }
                Spacer(Modifier.fillMaxWidth().height(40.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingFormHorizontal)
                                .align(Alignment.BottomCenter)
                .offset(y = 0.dp.minus(40.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                if (formHasIncorrectInput)
                        "Registration unsuccessful. Please enter all data."
                    else
                        " "
                ,
                style = LittleLemonTypography.highlightText,
                fontSize = 14.sp,
                color = LittleLemonColors.error,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Button(
                onClick = {
                    firstNameHasIncorrectValue = firstName.isBlank()
                    lastNameHasIncorrectValue = lastName.isBlank()
                    emailHasIncorrectValue = email.isBlank()
                    if (firstNameHasIncorrectValue || lastNameHasIncorrectValue || emailHasIncorrectValue){
                        formHasIncorrectInput = true
                    }
                    else{
                        formHasIncorrectInput = false
                        preferences.edit{
                            val userKeys = LoginKeys.UserKeys
                            putBoolean(LoginKeys.userIsLoggedIn, true)
                            putString(userKeys.firstName, firstName)
                            putString(userKeys.lastName, lastName)
                            putString(userKeys.email, email)
                        }
                        Toast.makeText(context,"Registration successful!", Toast.LENGTH_LONG).show()
                        navController.navigate(Destinations.Home.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LittleLemonColors.primary2,
                    contentColor = LittleLemonColors.secondary4
                ),
                border = BorderStroke(1.dp, LittleLemonColors.primary1.copy(alpha = .5f))

        ) {
                Text(
                    "Register",
                    fontFamily = LittleLemonTypography.fontFamilyKarla,
                    color = LittleLemonColors.secondary4
                )
            }
        }
    }
}


@Composable
private fun EntryField(
    modifier: Modifier = Modifier,
    label: String,
    hintText: String,
    value: String,
    hasIncorrectInput: Boolean = false,
    onValueChange: (String)-> Unit,
    ) {
    var placeHolderShouldBeVisible by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = modifier
    ) {
        Text(
            label,
            style = LittleLemonTypography.highlightText,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            color = if(hasIncorrectInput) LittleLemonColors.error else LittleLemonColors.primary1.copy(alpha = .85f),
            modifier = Modifier.padding(bottom = 5.dp, start = 1.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = {
                if (placeHolderShouldBeVisible){
                    placeHolderShouldBeVisible = false
                }
                onValueChange(it)
            },
            singleLine = true,
            textStyle = LittleLemonTypography.paragraphText.copy(color = LittleLemonColors.primary1),
            modifier = Modifier
                .fillMaxWidth()
                .background(LittleLemonColors.transparent , RoundedCornerShape(8.dp))
                .border(
                    1.dp,

                    if(hasIncorrectInput)
                                LittleLemonColors.error
                            else
                                LittleLemonColors.primary1.copy(alpha = .5f),

                    RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            decorationBox = { innerTextField ->
                if (placeHolderShouldBeVisible)
                {
                    Text(
                        hintText,
                        style = LittleLemonTypography.paragraphText,
                        color = LittleLemonColors.primary1.copy(alpha = .75f)
                    )
                    innerTextField()
                }
                else{
                    innerTextField()
                }
            }
        )
    }
}

