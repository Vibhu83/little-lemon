package com.vibhu.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.vibhu.littlelemon.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)



//val fontFamilyKarla = FontFamily(
//    Font(R.font.karla_regular, FontWeight.Thin),
//    Font(R.font.karla_regular, FontWeight.ExtraLight),
//    Font(R.font.karla_regular, FontWeight.Light),
//    Font(R.font.karla_regular, FontWeight.Normal),
//    Font(R.font.karla_regular, FontWeight.Medium),
//    Font(R.font.karla_regular, FontWeight.SemiBold),
//    Font(R.font.karla_regular, FontWeight.Bold),
//    Font(R.font.karla_regular, FontWeight.ExtraBold),
//    Font(R.font.karla_regular, FontWeight.Black),
//)
//val fontFamilyMarkazi = FontFamily(
//    Font(R.font.markazi_text_regular, FontWeight.Thin),
//    Font(R.font.markazi_text_regular, FontWeight.ExtraLight),
//    Font(R.font.markazi_text_regular, FontWeight.Light),
//    Font(R.font.markazi_text_regular, FontWeight.Normal),
//    Font(R.font.markazi_text_regular, FontWeight.Medium),
//    Font(R.font.markazi_text_regular, FontWeight.SemiBold),
//    Font(R.font.markazi_text_regular, FontWeight.Bold),
//    Font(R.font.markazi_text_regular, FontWeight.ExtraBold),
//    Font(R.font.markazi_text_regular, FontWeight.Black),
//)

object LittleLemonTypography{

    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val fontFamilyKarla = FontFamily(
        Font(
            googleFont = GoogleFont("Karla"),
            fontProvider = provider,
        )
    )
    val fontFamilyMarkazi = FontFamily(
        Font(
            googleFont = GoogleFont("Markazi"),
            fontProvider = provider,
        )
    )

    val displayTitle = TextStyle(
        fontFamily = fontFamilyMarkazi,
        fontWeight = FontWeight.Medium,
        fontSize = 64.sp
    )
    val subTitle = TextStyle(
        fontFamily = fontFamilyMarkazi,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp
    )
    val regular = TextStyle(
        fontFamily = fontFamilyMarkazi,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp
    )
    val leadText = TextStyle(
        fontFamily = fontFamilyKarla,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    )
    val sectionTitle = TextStyle(
        fontFamily = fontFamilyKarla,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
    )
    val sectionCategory = TextStyle(
        fontFamily = fontFamilyKarla,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
    )
    val cardTitle = TextStyle(
        fontFamily = fontFamilyKarla,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    )
    val paragraphText = TextStyle(
        fontFamily = fontFamilyKarla,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    )
    val highlightText = TextStyle(
        fontFamily = fontFamilyKarla,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    )
}