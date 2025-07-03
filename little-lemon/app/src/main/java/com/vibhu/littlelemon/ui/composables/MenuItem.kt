package com.vibhu.littlelemon.ui.composables

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.vibhu.littlelemon.ui.entities.MenuItemRoom
import com.vibhu.littlelemon.ui.theme.LittleLemonColors
import com.vibhu.littlelemon.ui.theme.LittleLemonTypography
import com.vibhu.littlelemon.R

@Preview(showBackground = true)
@Composable
private fun Preview(
){
    val menuItem = MenuItemRoom(
        id = 1,
        title = "Greek Salad",
        description = "The famous greek salad of crispy lettuce, peppers, olives, our Chicago.",
        price = 10.0,
        imageUrl = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
        category = "starters"
    )
    MenuItem(
        menuItem,
        Modifier
            .padding(vertical = 6.dp)
            .width(416.dp)
            .height(138.dp)
            .border(1.dp, LittleLemonColors.primary1),
        416.dp,
        138.dp
        )
}

@Composable
private fun ImageFromFile(imagePath: String, modifier: Modifier = Modifier){
    val options = BitmapFactory.Options().apply {
        inSampleSize = 4
    }
    val bitmap = BitmapFactory.decodeFile(imagePath, )
    Image(
        bitmap.asImageBitmap(),
        contentScale = ContentScale.Crop,
        contentDescription = "",
        modifier = modifier
    )
}

@Composable
private fun ImageLoading(
    modifier: Modifier = Modifier,
    color: Color = LittleLemonColors.primary1
){
    CircularProgressIndicator(
        color = color,
        modifier = modifier
    )
}

@Composable
fun MenuItem(
    menuItem: MenuItemRoom,
    modifier: Modifier = Modifier,
    cardWidth: Dp,
    cardHeight: Dp
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageHeight = cardHeight*.7f
        val imageWidth = cardWidth*.275f
        val imageSize = max(imageHeight,imageWidth)

        val textColumnWidth = cardWidth-imageSize
        val textColumnEndPadding = cardWidth*.02f

        val titleHeight = cardHeight*.169f
        val descriptionHeight = cardHeight*.68f
        val priceHeight = cardHeight*.151f



        Column(
            modifier = Modifier.width(textColumnWidth).height(cardHeight).padding(end = textColumnEndPadding)
        ) {
            Text(
                text = menuItem.title,
                style = LittleLemonTypography.cardTitle,
                color = LittleLemonColors.secondary4,
                maxLines = 1,
                fontSize = (titleHeight*.9f).value.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.height(titleHeight).fillMaxWidth()
            )
            Text(
                menuItem.description,
                style = LittleLemonTypography.paragraphText,
                color = LittleLemonColors.primary1,
                maxLines = 2,
                fontSize = ((descriptionHeight*.1875f)).value.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.height(descriptionHeight).padding(top = descriptionHeight*.075f).fillMaxWidth()
            )

            Text(
                "$${menuItem.price}",
                style = LittleLemonTypography.highlightText,
                fontSize = (priceHeight*.9f).value.sp,
                maxLines = 1,
                color = LittleLemonColors.primary1,
                modifier = Modifier.height(priceHeight).fillMaxWidth()
            )
        }

        val imageModifier = Modifier.size(imageSize)
        if (menuItem.imageLocalPath.isEmpty())
            ImageLoading(
                modifier = imageModifier
            )
        else
            ImageFromFile(menuItem.imageLocalPath, modifier = imageModifier)

    }
}


