package com.vibhu.littlelemon.ui.composables

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vibhu.littlelemon.R
import com.vibhu.littlelemon.ui.theme.LittleLemonColors
import com.vibhu.littlelemon.ui.theme.LittleLemonTypography

@Composable
fun Onboarding(){
    val paddingFormHorizontal = 20.dp

    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            Modifier.fillMaxSize(),
        ) {
            Header(Modifier
                .fillMaxWidth()
                .fillMaxHeight(.125f))
            Title(Modifier
                .fillMaxWidth()
                .fillMaxHeight(.175f))
            Heading(Modifier
                .fillMaxHeight(.2f)
                .padding(horizontal = paddingFormHorizontal)
            )
            Form(modifier = Modifier
                .padding(horizontal = paddingFormHorizontal), 40.dp)
        }
        RegisterButton(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = paddingFormHorizontal)
            .align(Alignment.BottomCenter)
            .offset(y = 0.dp.minus(25.dp))
        )
    }
}

@Composable
@Preview(
//    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
fun OnboardingPreview(){
    Onboarding()
}

@Composable
private fun Header(modifier: Modifier){
    Box(
        modifier = modifier,
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
}

@Composable
private fun Title(modifier: Modifier){
    Box(
        modifier
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
}

@Composable
private fun Heading(modifier: Modifier){
    Box(
        modifier,
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
}

@Composable
private fun Form(modifier: Modifier, spaceBetween: Dp){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        FirstNameEntry()
        Spacer(modifier.fillMaxWidth().height(spaceBetween))
        LastNameEntry()
        Spacer(modifier.fillMaxWidth().height(spaceBetween))
        EmailEntry()
        Spacer(modifier.fillMaxWidth().height(spaceBetween))
    }
}


@Composable
private fun EntryField(
    modifier: Modifier = Modifier,
    label: String,
    hintText: String,
    value: String,
    onValueChange: (String)-> Unit,

    ) {
    Column(
        modifier = modifier
    ) {
        Text(
            label,
            style = LittleLemonTypography.highlightText,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            color = LittleLemonColors.primary1.copy(alpha = .85f),
            modifier = Modifier.padding(bottom = 5.dp, start = 1.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = LittleLemonTypography.paragraphText.copy(color = LittleLemonColors.primary1),
            modifier = Modifier
                .fillMaxWidth()
                .background(LittleLemonColors.transparent , RoundedCornerShape(8.dp))
                .border(1.dp, LittleLemonColors.primary1.copy(alpha = .5f), RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            decorationBox = { innerTextField ->
                Text(
                    hintText,
                    style = LittleLemonTypography.paragraphText,
                    color = LittleLemonColors.primary1.copy(alpha = .75f)
                )
                innerTextField()
            }
        )
    }
}

@Composable
private fun FirstNameEntry(modifier: Modifier = Modifier) {
    EntryField(
        modifier = modifier,
        "First Name",
        "Tilly",
        "",
    ) { }
}
@Composable
private fun LastNameEntry(modifier: Modifier = Modifier) {
    EntryField(
        modifier = modifier,
        "Last Name",
        "Doe",
        "",
    ) { }
}
@Composable
private fun EmailEntry(modifier: Modifier = Modifier) {
    EntryField(
        modifier = modifier,
        "Email",
        "tillydoe@example.com",
        "",
    ) { }
}

@Composable
private fun RegisterButton(modifier: Modifier = Modifier){
    Button(
        onClick = {},
        modifier = modifier,
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