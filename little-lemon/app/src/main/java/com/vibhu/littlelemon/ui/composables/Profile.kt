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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.vibhu.littlelemon.R
import com.vibhu.littlelemon.ui.keys.ApplicationKeys
import com.vibhu.littlelemon.ui.keys.LoginKeys
import com.vibhu.littlelemon.ui.navigation.Destinations
import com.vibhu.littlelemon.ui.theme.LittleLemonColors
import com.vibhu.littlelemon.ui.theme.LittleLemonTypography

@Composable
fun Profile(navController: NavHostController) {
    val paddingFormHorizontal = 20.dp
    val context = LocalContext.current
    val preferences = context.getSharedPreferences(ApplicationKeys.preferences, Context.MODE_PRIVATE)

    val firstName = preferences.getString(LoginKeys.UserKeys.firstName, "")?: ""
    val lastName = preferences.getString(LoginKeys.UserKeys.lastName, "")?: ""
    val email = preferences.getString(LoginKeys.UserKeys.email, "")?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
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
                TextBox(
                    modifier = Modifier,
                    "First Name",
                    firstName,
                )
                Spacer(Modifier.fillMaxWidth().height(40.dp))
                TextBox(
                    modifier = Modifier,
                    "Last Name",
                    lastName,
                )
                Spacer(Modifier.fillMaxWidth().height(40.dp))
                TextBox(
                    modifier = Modifier,
                    "Email",
                    email,
                )
                Spacer(Modifier.fillMaxWidth().height(40.dp))
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
                navController.navigate(Destinations.Onboarding.route)

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingFormHorizontal)
                .align(Alignment.BottomCenter)
                .offset(y = 0.dp.minus(40.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LittleLemonColors.primary2,
                contentColor = LittleLemonColors.secondary4
            ),
            border = BorderStroke(1.dp, LittleLemonColors.primary1.copy(alpha = .5f))

        ) {
            Text(
                "Log out",
                fontFamily = LittleLemonTypography.fontFamilyKarla,
                color = LittleLemonColors.secondary4
            )
        }
    }
}

@Composable
private fun TextBox(modifier: Modifier, label: String, value: String){
    Column(
        modifier = modifier
    ) {
        Text(
            label,
            style = LittleLemonTypography.highlightText,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            color = LittleLemonColors.primary1,
            modifier = Modifier.padding(bottom = 5.dp, start = 1.dp)
        )
        Text(
            value,
            maxLines = 1,
            style = LittleLemonTypography.highlightText,
            color = LittleLemonColors.primary1,
            modifier = Modifier
                .fillMaxWidth()
                .background(LittleLemonColors.transparent , RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    LittleLemonColors.primary1.copy(alpha = .4f),
                    RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp),
        )
    }
}