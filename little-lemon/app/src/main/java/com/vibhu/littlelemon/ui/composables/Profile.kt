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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.vibhu.littlelemon.R
import com.vibhu.littlelemon.ui.keys.ApplicationKeys
import com.vibhu.littlelemon.ui.keys.LoginKeys
import com.vibhu.littlelemon.ui.navigation.Destinations
import com.vibhu.littlelemon.ui.theme.LittleLemonColors
import com.vibhu.littlelemon.ui.theme.LittleLemonTypography

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Profile(navController: NavHostController? = null) {
    val paddingFormHorizontal = 20.dp
    val context = LocalContext.current
    val preferences = context.getSharedPreferences(ApplicationKeys.preferences, Context.MODE_PRIVATE)

    val firstName = preferences.getString(LoginKeys.UserKeys.firstName, "")?: ""
    val lastName = preferences.getString(LoginKeys.UserKeys.lastName, "")?: ""
    val email = preferences.getString(LoginKeys.UserKeys.email, "")?: ""

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val height = maxHeight
        val width = maxWidth

        val horizontalPadding = width*.035f
        val screenWidthAfterPadding = width-(horizontalPadding*2)

        val topBarHeight = height*.1f
        val titleHeight = height*.15f

        val textBoxHeight = height*.1f
        val textBoxSpacerHeight = height*.05f
        val logoutButtonHeight = height*.06f
        Column(
            Modifier.fillMaxSize(),
        ) {
            TopBar(height = topBarHeight)
            FormTitle(
                Modifier
                    .height(titleHeight)
                    .padding(horizontal = horizontalPadding),
                height = titleHeight,
                width = screenWidthAfterPadding
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = paddingFormHorizontal),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                TextBox(
                    modifier = Modifier,
                    "First Name",
                    firstName,
                    textBoxHeight,
                    screenWidthAfterPadding
                )
                Spacer(Modifier.fillMaxWidth().height(textBoxSpacerHeight))
                TextBox(
                    modifier = Modifier,
                    "Last Name",
                    lastName,
                    textBoxHeight,
                    screenWidthAfterPadding
                )
                Spacer(Modifier.fillMaxWidth().height(textBoxSpacerHeight))
                TextBox(
                    modifier = Modifier,
                    "Email",
                    email,
                    textBoxHeight,
                    screenWidthAfterPadding
                )
                Spacer(Modifier.fillMaxWidth().height(textBoxSpacerHeight))
            }
        }
        Button(
            onClick = {
                preferences.edit {
                    putString(LoginKeys.UserKeys.firstName, "")
                    putString(LoginKeys.UserKeys.lastName, "")
                    putString(LoginKeys.UserKeys.email, "")
                    putBoolean(LoginKeys.userIsLoggedIn, false)
                }
                Toast.makeText(context, "Successfully logged out!", Toast.LENGTH_LONG).show()
                navController?.let {
                    it.navigate(Destinations.Onboarding.route)
                }

            },
            modifier = Modifier
                .height(logoutButtonHeight)
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
                .align(Alignment.BottomCenter)
                .offset(y = 0.dp.minus(logoutButtonHeight/3)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LittleLemonColors.primary2,
                contentColor = LittleLemonColors.secondary4
            ),
            border = BorderStroke(1.dp, LittleLemonColors.primary1.copy(alpha = .5f))

        ) {
            Text(
                "Log out",
                style = LittleLemonTypography.highlightText,
                fontSize = (logoutButtonHeight*.38f).value.sp,
                color = LittleLemonColors.secondary4
            )
        }
    }
}

@Composable
private fun TextBox(
    modifier: Modifier,
    label: String,
    value: String,
    height: Dp,
    width: Dp
){
    Column(
        modifier = modifier
    ) {
        Text(
            label,
            style = LittleLemonTypography.highlightText,
            fontWeight = FontWeight.SemiBold,
            fontSize = (height*.14f).value.sp,
            letterSpacing = 0.75.sp,
            maxLines = 1,
            color = LittleLemonColors.primary1,
            modifier = Modifier.padding(bottom = height*.05f, start = height*.015f)
        )
        Text(
            value,
            maxLines = 1,
            style = LittleLemonTypography.highlightText,
            color = LittleLemonColors.primary1,
            fontSize = (height*.2f).value.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(LittleLemonColors.transparent , RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    LittleLemonColors.primary1.copy(alpha = .4f),
                    RoundedCornerShape(8.dp)
                )
                .padding(horizontal = width*.04f, vertical = height*.2f),
        )
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

@Preview(showBackground = true)
@Composable
private fun ProfilePreview(){
    Profile()
}