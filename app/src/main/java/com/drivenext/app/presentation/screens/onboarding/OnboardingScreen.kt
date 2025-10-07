package com.drivenext.app.presentation.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drivenext.app.R
import com.drivenext.app.presentation.theme.DriveNextTheme
import kotlinx.coroutines.launch

/**
 * Экран приветствия (Onboarding)
 * Показывает слайды с информацией о приложении
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onSkip: () -> Unit,
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Верхняя панель с кнопкой "Пропустить"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onSkip
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
            }
        }
        
        // Основной контент
        Box(
            modifier = Modifier.weight(1f)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                OnboardingPage(
                    page = onboardingPages[page]
                )
            }
        }
        
        // Индикаторы и кнопки навигации
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Индикаторы страниц
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(onboardingPages.size) { index ->
                    val isSelected = index == pagerState.currentPage
                    Box(
                        modifier = Modifier
                            .size(
                                width = if (isSelected) 24.dp else 8.dp,
                                height = 8.dp
                            )
                            .background(
                                color = if (isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                },
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Кнопки навигации
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (pagerState.currentPage == onboardingPages.size - 1) {
                    Arrangement.Center
                } else {
                    Arrangement.SpaceBetween
                }
            ) {
                if (pagerState.currentPage < onboardingPages.size - 1) {
                    // Кнопка "Далее" как в дизайне Figma
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.next),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                } else {
                    // Кнопка "Поехали" как в дизайне Figma
                    Button(
                        onClick = onFinish,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.get_started),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingPage(
    page: OnboardingPageData
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Изображение
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(220.dp)
                .padding(bottom = 24.dp),
            contentScale = ContentScale.Fit
        )
        
        // Заголовок
        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Описание
        Text(
            text = page.description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

data class OnboardingPageData(
    val imageRes: Int,
    val title: String,
    val description: String
)

// Данные для страниц onboarding как в дизайне Figma
private val onboardingPages = listOf(
    OnboardingPageData(
        imageRes = R.drawable.splash_image,
        title = "Аренда автомобилей",
        description = "Выберите из множества автомобилей и арендуйте тот, который подходит именно вам"
    ),
    OnboardingPageData(
        imageRes = R.drawable.splash_new,
        title = "Безопасно и удобно",
        description = "Быстрое бронирование и безопасная аренда с полной поддержкой клиентов"
    ),
    OnboardingPageData(
        imageRes = R.drawable.my_splash_image,
        title = "Лучшие предложения",
        description = "Найдите лучшие предложения и сэкономьте деньги на аренде автомобиля"
    )
)

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    DriveNextTheme {
        OnboardingScreen(
            onSkip = { },
            onFinish = { }
        )
    }
}
