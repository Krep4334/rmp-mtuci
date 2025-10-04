package com.drivenext.app.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drivenext.app.R
import com.drivenext.app.presentation.theme.DriveNextTheme

/**
 * Splash Screen как в дизайне Figma
 */
@Composable
fun SplashScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        
        // Заголовок DriveNext (точные параметры из Figma)
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp, // 24px из Figma
            fontWeight = FontWeight.SemiBold, // Weight 600 из Figma
            color = Color(0xFF2A1246), // Точный цвет из Figma
            textAlign = TextAlign.Center,
            lineHeight = 24.sp, // Line height 100%
            modifier = Modifier
                .width(124.dp) // Width 124px из Figma
                .height(29.dp) // Height 29px из Figma
        )
        
        Spacer(modifier = Modifier.height(16.dp)) // Paragraph spacing из Figma
        
        // Подзаголовок (точные параметры из Figma)
        Text(
            text = stringResource(R.string.app_slogan),
            fontSize = 14.sp, // Size 14px из Figma
            fontWeight = FontWeight.Normal, // Weight 400 из Figma
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp, // Line height 100%
            modifier = Modifier
                .width(342.dp) // Width 342px из Figma
                .height(17.dp) // Height 17px из Figma
        )
        
        Spacer(modifier = Modifier.height(48.dp)) // Top 285px - текущая позиция
        
        // Иллюстрация (новая современная)
        Image(
            painter = painterResource(id = R.drawable.my_splash_image),
            contentDescription = null,
            modifier = Modifier
                .size(343.dp) // Width и Height 343px из Figma
        )
        
        Spacer(modifier = Modifier.height(80.dp))
        
        // Индикатор загрузки (черная полоска как на iPhone)
        Box(
            modifier = Modifier
                .width(134.dp)
                .height(5.dp)
                .background(
                    Color.Black,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(2.5.dp)
                )
        )
        
        Spacer(modifier = Modifier.height(34.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenContentPreview() {
    DriveNextTheme {
        SplashScreenContent()
    }
}
