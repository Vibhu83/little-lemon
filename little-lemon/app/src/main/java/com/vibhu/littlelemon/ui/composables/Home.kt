package com.vibhu.littlelemon.ui.composables

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vibhu.littlelemon.R
import com.vibhu.littlelemon.ui.entities.MenuItemRoom
import com.vibhu.littlelemon.ui.navigation.Destinations
import com.vibhu.littlelemon.ui.network.ConnectivityObserver
import com.vibhu.littlelemon.ui.network.NetworkMonitor
import com.vibhu.littlelemon.ui.room.LittleLemonDatabase
import com.vibhu.littlelemon.ui.theme.LittleLemonColors
import com.vibhu.littlelemon.ui.theme.LittleLemonTypography
import com.vibhu.littlelemon.ui.viewmodels.MenuViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Home(
    navController: NavHostController? = null,
) {
    val context = LocalContext.current
    val networkStatus by NetworkMonitor.observe().collectAsState(initial = ConnectivityObserver.Status.Unavailable)
    val database = LittleLemonDatabase.getInstance(context)
    val menuViewModel = MenuViewModel.getInstance(database,context as ComponentActivity)
    val menuList by menuViewModel.menuList.observeAsState(emptyList())
    if (networkStatus == ConnectivityObserver.Status.Available){
        menuViewModel.loadRemoteMenu(context,menuList)
    }
    val menuCategories = menuList.map {
       StringBuilder().apply {
           append(it.category[0].uppercase())
           append(it.category.substring(1))
       }.toString()
    }.toSet().toList()
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {

        val height = maxHeight
        val width = maxWidth

        val horizontalPadding = width*.035f
        val screenWidthAfterPadding = width-(horizontalPadding*2)

        val topBarHeight = height*.1f
        //remaining height: .9f
        val heroSectionHeight = height*.35f
        val heroSectionTopPadding = heroSectionHeight*0.025f
        val heroSectionBottomPadding = heroSectionHeight*0.05f
        //remaining height: .55f
        val sortingSectionHeight = height*.15f
        val sortingSectionVerticalPadding = sortingSectionHeight*.2f
        //remaining height: .4f
        val menuItemCardHeight = height*.15f
        val menuItemVerticalPadding = height*.0067f

        var searchValue by remember { mutableStateOf("") }
        var currentSortingOptionIndex by remember { mutableStateOf<Int?>(null) }

        var currentMenuList = menuList
            .searchBy(searchValue)
            .sortByCategory(
                category =
                    if (currentSortingOptionIndex!=null) {
                        menuCategories[currentSortingOptionIndex!!]
                    }
                    else{
                        null
                    }
            )

        Column(
            Modifier.fillMaxSize(),
        ) {
            TopBar(
                height = topBarHeight, width = width
            ) {
                navController?.let {
                    it.navigate(Destinations.Profile.route)
                }
            }
            HeroSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heroSectionHeight)
                    .background(color = LittleLemonColors.primary1,)
                    .padding(
                        start = horizontalPadding,
                        end = horizontalPadding,
                        top = heroSectionTopPadding,
                        bottom = heroSectionBottomPadding,
                    ),
                height = heroSectionHeight - (heroSectionTopPadding + heroSectionBottomPadding),
                width = screenWidthAfterPadding,
                searchValue = searchValue
            ){
                searchValue = it
            }
            if (menuList.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(max(height * .55f, screenWidthAfterPadding))
                        .padding(horizontal = horizontalPadding, vertical = height * .025f)
                        .align(Alignment.CenterHorizontally),
                    color = LittleLemonColors.primary1,
                    trackColor = LittleLemonColors.secondary3
                )
            } else {
                SortingSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(sortingSectionHeight)
                        .padding(
                            start = horizontalPadding,
                            end = horizontalPadding,
                        )
                        .bottomBorder(1.dp, LittleLemonColors.primary1.copy(alpha = .5f))
                        .padding(
                            top = sortingSectionVerticalPadding,
                            bottom = sortingSectionVerticalPadding,
                        ),
                    height = sortingSectionHeight - (sortingSectionVerticalPadding * 2),
                    width = screenWidthAfterPadding,
                    options = menuCategories,
                    currentSelectedIndex = currentSortingOptionIndex

                ){ index ->
                    currentSortingOptionIndex = index
                }
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = horizontalPadding)
                ) {
                    itemsIndexed(currentMenuList) { index, menuItem ->
                        var padding = PaddingValues(vertical = menuItemVerticalPadding)
                        if (index == 0) {
                            padding = PaddingValues(
                                bottom = menuItemVerticalPadding,
                                top = menuItemVerticalPadding / 2
                            )
                        } else if (index == menuList.size - 1) {
                            padding = PaddingValues(
                                top = menuItemVerticalPadding,
                                bottom = menuItemVerticalPadding / 2
                            )
                        }

                        MenuItem(
                            menuItem = menuItem,
                            modifier = Modifier
                                .padding(padding)
                                .width(screenWidthAfterPadding)
                                .height(height * .175f),
                            cardWidth = screenWidthAfterPadding,
                            cardHeight = menuItemCardHeight
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    onClickProfile: () -> Unit
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
        Button(
            onClick = onClickProfile,
            modifier = Modifier
                .padding(horizontal = width * .025f, vertical = height * .175f)
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

@Composable
private fun HeroSection(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    searchValue: String,
    onSearchBarValueChange: (String) -> Unit,
    ){
    Column(
        modifier = modifier
    ) {
        val availableHeight = height
        val availableWidth = width

        //Remaining Height: 1f
        val titleHeight = availableHeight*.225f
        val subtitleOffset = titleHeight*.25f
        val imageOffset = subtitleOffset*.1f
        val descriptionOffset = subtitleOffset*.5f
        //Remaining height: 0.775f
        val middleRowHeight = availableHeight*.625f -descriptionOffset
        //Remaining height: 0.15f
        val searchBarHeight = availableHeight*.15f + descriptionOffset

        //Title
        Text(
            "Little Lemon",
            style = LittleLemonTypography.displayTitle,
            color = LittleLemonColors.primary2,
            fontSize = (titleHeight*.9f).value.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        )

        //Mid Row
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(middleRowHeight)
                .offset(y = 0.dp.minus(descriptionOffset))
        )
        {
            //Subtitle + Description
            Column(
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .fillMaxHeight()
                ,
                horizontalAlignment = Alignment.Start
            ) {
                val subtitleHeight = minOf(middleRowHeight*.25f, titleHeight*.75f)
                val descriptionHeight = middleRowHeight*.75f
                Text(
                    "Chicago",
                    style = LittleLemonTypography.subTitle,
                    fontSize = (subtitleHeight).value.sp,
                    color = LittleLemonColors.secondary3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 0.dp.minus(subtitleOffset))
                )
                Text(
                    "We are a family owned Mediterranean restaurant, focused on traditional recipes with a modern twist.",
                    style = LittleLemonTypography.leadText,
                    fontSize = (descriptionHeight*.1525f).value.sp,
                    color = LittleLemonColors.secondary3,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = 0.dp.minus(descriptionOffset))
                )
            }

            Spacer(Modifier.width(availableWidth *.025f))

            //Image
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(middleRowHeight * .9f)
                    .offset(y = 0.dp.minus(imageOffset))
            ){
                Image(
                    painter = painterResource(R.drawable.hero_image),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Hero image",
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                )
            }

        }

        //Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(searchBarHeight)
                .background(LittleLemonColors.secondary3, RoundedCornerShape(8.dp))
                .padding(searchBarHeight * .25f)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var placeHolderShouldBeVisible by remember {
                mutableStateOf(true)
            }
            val itemSize = searchBarHeight*.5f
            Icon(
                painterResource(R.drawable.round_search_24),
                tint = LittleLemonColors.secondary4,
                contentDescription = "Search",
                modifier = Modifier
                    .size(itemSize)
                    .offset(y = itemSize * .075f)
            )
            Spacer(modifier = Modifier.width(searchBarHeight*.25f))
            BasicTextField(
                value = searchValue,
                onValueChange = {
                    if (placeHolderShouldBeVisible){
                        placeHolderShouldBeVisible = false
                    }
                    onSearchBarValueChange(it)
                                },
                singleLine = true,
                textStyle = LittleLemonTypography.paragraphText.copy(
                    color = LittleLemonColors.primary1,
                    fontSize = (itemSize*.725f).value.sp,
                ),
                decorationBox = { innerTextField ->
                    if (placeHolderShouldBeVisible)
                    {
                        Text(
                            "Enter search phrase",
                            maxLines = 1,
                            style = LittleLemonTypography.paragraphText,
                            fontSize = (itemSize*.725f).value.sp,
                            fontWeight = FontWeight.Medium,
                            color = LittleLemonColors.primary1.copy(alpha = .9f),
                            modifier = Modifier
                                .height(itemSize)
                                .fillMaxWidth()
                        )
                        innerTextField()
                    }
                    else {
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
private fun SortingSection(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    options: List<String> = listOf<String>(
        "Starters",
        "Mains",
        "Desserts",
        "Drinks"
    ),
    currentSelectedIndex: Int?,
    onSortingOptionClick: (Int?) -> Unit
){
    val titleHeight = height*.5f
    val listHeight = height*.5f
    Column(
        modifier = modifier
    ) {
        Text(
            "Order for delivery!".uppercase(),
            style = LittleLemonTypography.sectionTitle,
            fontSize = (titleHeight*.55f).value.sp,
            maxLines = 1,
            color = LittleLemonColors.secondary4,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f)

        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentPadding = PaddingValues(0.dp)
        ) {
            itemsIndexed(options){index, item ->
                val horizontalPadding = width*.025f
                val isSelected = currentSelectedIndex == index
                val padding =
                    when (index) {
                        0 -> PaddingValues(end = horizontalPadding)
                        options.size-1 -> PaddingValues(start = horizontalPadding)
                        else -> PaddingValues(horizontal = horizontalPadding)
                    }
                    SortingOption(
                        height = listHeight,
                        width = width,
                        paddingValues = padding,
                        title = item,
                        isSelected = isSelected
                    ){
                        if (isSelected)
                            onSortingOptionClick(null)
                        else
                            onSortingOptionClick(index)
                    }
            }
        }
    }
}

@Composable
private fun SortingOption(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    paddingValues: PaddingValues,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
){
    Box(
        Modifier.padding(paddingValues)
    ) {
        Button(
            onClick = {
                onClick()
            },
            contentPadding = PaddingValues(horizontal = width*.04f, vertical =height*.2f),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if(isSelected)
                        LittleLemonColors.primary1
                    else
                        LittleLemonColors.secondary3,
                contentColor = if(isSelected)
                    LittleLemonColors.secondary3
                else
                    LittleLemonColors.primary1
            )
        ) {
            Text(
                title,
                style = LittleLemonTypography.sectionCategory,
                fontSize = (height*.4f).value.sp,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview(){

    Home()
}

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = this.then(
    Modifier.drawBehind {
        val stroke = strokeWidth.toPx()
        drawLine(
            color = color,
            start = Offset(0f, size.height - stroke / 2),
            end = Offset(size.width, size.height - stroke / 2),
            strokeWidth = stroke
        )
    }
)

private fun List<MenuItemRoom>.searchBy(searchPhrase: String): List<MenuItemRoom>{
    return if (searchPhrase.isBlank()){
        this
    }
    else{
        this.filter { it.title.contains(searchPhrase, ignoreCase = true) }
    }
}

private fun List<MenuItemRoom>.sortByCategory(category: String?): List<MenuItemRoom>{
    return if(category==null || category.isBlank()){
        this
    }
    else{
        this.filter { it.category.lowercase() == category.lowercase() }
    }
}



