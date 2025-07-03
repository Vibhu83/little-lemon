package com.vibhu.littlelemon.ui.composables

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.navigation.NavHostController
import com.vibhu.littlelemon.ui.keys.LoginKeys
import com.vibhu.littlelemon.ui.navigation.Destinations

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Onboarding(navController: NavHostController? =null){

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
    val isUserLoggedIn = preferences.getBoolean(LoginKeys.userIsLoggedIn, false)
    if (isUserLoggedIn){
        navController?.let {
            it.navigate(Destinations.Home.route)
        }
    }
    var formHasIncorrectInput by remember {
        mutableStateOf(false)
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    )
    {
        val height = maxHeight
        val width = maxWidth

        val horizontalPadding = width*.035f
        val screenWidthAfterPadding = width-(horizontalPadding*2)

        val topBarHeight = height*.1f
        //.9
        val pageTitleHeight = height*.2f
        //.7
        val formTitleHeight = height*.15f
        //.55
        val entryFieldHeight = height*.1f
        //.25
        val entrySpacerHeight = height*.05f
        //.1
        val registerButtonHeight = height*.1f
        
        Column(
            Modifier.fillMaxSize(),
        ) {
            TopBar(height = topBarHeight)
            PageTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height*.2f)
                    .background(LittleLemonColors.primary1),
                height = pageTitleHeight,
            )
            FormTitle(
                Modifier
                    .height(formTitleHeight)
                    .padding(horizontal = horizontalPadding),
                height = formTitleHeight,
                width = screenWidthAfterPadding
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = horizontalPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                EntryField(
                    modifier = Modifier,
                    "First Name",
                    "Tilly",
                    firstName,
                    firstNameHasIncorrectValue,
                    height = entryFieldHeight,
                    width = screenWidthAfterPadding
                ) {
                    firstNameHasIncorrectValue = it.isBlank()
                    firstName = it
                }
                Spacer(Modifier.fillMaxWidth().height(entrySpacerHeight))
                EntryField(
                    modifier = Modifier,
                    "Last Name",
                    "Doe",
                    lastName,
                    lastNameHasIncorrectValue,
                    height = entryFieldHeight,
                    width = screenWidthAfterPadding
                ) {
                    lastNameHasIncorrectValue = it.isBlank()
                    lastName = it
                }
                Spacer(Modifier.fillMaxWidth().height(entrySpacerHeight))
                EntryField(
                    modifier = Modifier,
                    "Email",
                    "tillydoe@example.com",
                    email,
                    emailHasIncorrectValue,
                    height = entryFieldHeight,
                    width = screenWidthAfterPadding
                ) {
                    emailHasIncorrectValue = it.isBlank()
                    email = it
                }
                Spacer(Modifier.fillMaxWidth().height(entrySpacerHeight))
            }
        }

        Column(
            modifier = Modifier
                .height(registerButtonHeight)
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
                .align(Alignment.BottomCenter)
                .offset(y = 0.dp.minus(registerButtonHeight/3)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val errorTextHeight = registerButtonHeight*.2f
            val buttonHeight = registerButtonHeight*.6f
            Text(
                if (formHasIncorrectInput)
                        "Registration unsuccessful. Please enter all data."
                    else
                        ""
                ,
                style = LittleLemonTypography.highlightText,
                fontSize = (errorTextHeight*.825f).value.sp,
                color = LittleLemonColors.error,
                modifier = Modifier.fillMaxWidth(0.85f).height(errorTextHeight)
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
                        navController?.let {
                            it.navigate(Destinations.Home.route)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(buttonHeight),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LittleLemonColors.primary2,
                    contentColor = LittleLemonColors.secondary4
                ),
                border = BorderStroke(1.dp, LittleLemonColors.primary1.copy(alpha = .5f))

        ) {
                Text(
                    "Register",
                    fontSize = (buttonHeight*.38f).value.sp,
                    style = LittleLemonTypography.highlightText,
                    color = LittleLemonColors.secondary4
                )
            }
        }
    }
}


@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    height: Dp,
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentScale = ContentScale.FillWidth,
            contentDescription = "",
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(.5f)
                .fillMaxHeight()
                .align(Alignment.Center)

        )
    }
}

@Composable
private fun PageTitle(
    modifier: Modifier = Modifier,
    height: Dp,
){
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ){
        Text(
            "Let's get to know you",
            color = LittleLemonColors.secondary3,
            style = LittleLemonTypography.cardTitle,
            fontSize = (height*.175f).value.sp,
            maxLines = 1
        )
    }
}

@Composable
private fun FormTitle(
    modifier: Modifier,
    height: Dp,
    width: Dp
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Personal Information",
            style = LittleLemonTypography.cardTitle,
            fontSize = (height*.165f).value.sp,
            color = LittleLemonColors.secondary4
        )
    }

}



@Composable
private fun EntryField(
    modifier: Modifier = Modifier,
    label: String,
    hintText: String,
    value: String,
    hasIncorrectInput: Boolean = false,
    height: Dp,
    width: Dp,
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
            fontWeight = FontWeight.SemiBold,
            fontSize = (height*.14f).value.sp,
            letterSpacing = 0.75.sp,
            color = if(hasIncorrectInput) LittleLemonColors.error else LittleLemonColors.primary1.copy(alpha = .75f),
            modifier = Modifier.padding(bottom = height*.05f, start = height*.015f)
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
            textStyle = LittleLemonTypography.paragraphText.copy(
                color = LittleLemonColors.primary1,
                fontSize = (height*.2f).value.sp,
            ),
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
                .padding(horizontal = width*.04f, vertical = height*.2f),
            decorationBox = { innerTextField ->
                if (placeHolderShouldBeVisible)
                {
                    Text(
                        hintText,
                        fontSize = (height*.2f).value.sp,
                        style = LittleLemonTypography.paragraphText,
                        color = LittleLemonColors.primary1.copy(alpha = .9f)
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

@Preview(showBackground = true)
@Composable
private fun OnboardingPreview(){
    Onboarding()
}

