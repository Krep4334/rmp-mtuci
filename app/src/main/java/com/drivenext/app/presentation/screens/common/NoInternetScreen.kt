package com.drivenext.app.presentation.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
 * Экран отсутствия подключения к интернету
 * Показывается когда нет доступа к сети
 */
@Composable
fun NoInternetScreen(
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        // Контейнер для позиционирования векторных изображений как розетка
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        ) {
            // Верхняя векторная картинка (правее и ниже)
            Image(
                    painter = painterResource(id = R.drawable.vector_upper),
                    contentDescription = null,
                    modifier = Modifier
                        .width(56.dp)
                        .height(50.88.dp)
                        .offset(x = 42.dp, y = 20.dp)
                )
            
            // Нижняя векторная картинка (левее)
            Image(
                painter = painterResource(id = R.drawable.vector_down),
                contentDescription = null,
                modifier = Modifier
                    .width(66.5.dp)
                    .height(60.8.dp)
                    .offset(x = (-10).dp, y = 60.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Заголовок как в дизайне
        Text(
            text = "Нет подключения\nк интернету",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Описание как в дизайне
        Text(
            text = "Проверьте подключение и повторите\nпопытку",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Кнопка повторной попытки подключения к интернету
        Button(
            onClick = onRetryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Повторить попытку",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun NoInternetScreenPreview() {
    DriveNextTheme {
        NoInternetScreen(
            onRetryClick = { }
        )
    }
}
