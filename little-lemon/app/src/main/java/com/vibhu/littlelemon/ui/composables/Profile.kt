package com.vibhu.littlelemon.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vibhu.littlelemon.ui.theme.LittleLemonTypography

@Composable
fun Profile() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Profile", style = LittleLemonTypography.displayTitle)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview(){
    Profile()
}