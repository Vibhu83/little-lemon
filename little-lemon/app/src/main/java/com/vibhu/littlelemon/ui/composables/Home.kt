package com.vibhu.littlelemon.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.vibhu.littlelemon.R
import com.vibhu.littlelemon.ui.navigation.Destinations
import com.vibhu.littlelemon.ui.theme.LittleLemonColors
import com.vibhu.littlelemon.ui.theme.LittleLemonTypography

@Composable
fun Home(navController: NavHostController){
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
                Button(
                    onClick = {
                        navController.navigate(Destinations.Profile.route)
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 14.dp)
                        .align(Alignment.CenterEnd)
                    ,
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LittleLemonColors.transparent,
                        contentColor = LittleLemonColors.transparent
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "",
                        alignment = Alignment.Center,
                    )
                }
            }
        }
    }
}
