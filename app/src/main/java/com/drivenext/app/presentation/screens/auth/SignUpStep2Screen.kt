package com.drivenext.app.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.drivenext.app.R
import com.drivenext.app.presentation.theme.DriveNextTheme
import java.text.SimpleDateFormat
import java.util.*

/**
 * Экран регистрации (Шаг 2 из 3)
 * Личные данные: фамилия, имя, отчество, дата рождения, пол
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpStep2Screen(
    onNavigateBack: () -> Unit,
    onNavigateToStep3: () -> Unit,
    viewModel: SignUpStep2ViewModel = viewModel()
) {
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }
    var selectedBirthDate by remember { mutableStateOf<Date?>(null) }
    var selectedGender by remember { mutableStateOf("") }
    var isGenderExpanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    
    val uiState by viewModel.uiState.collectAsState()
    
    // Получаем строки заранее для использования в onClick
    val maleText = stringResource(R.string.male)
    val femaleText = stringResource(R.string.female)
    
    // Функция для форматирования даты
    val dateFormatter = remember { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }
    val birthDateText = selectedBirthDate?.let { dateFormatter.format(it) } ?: ""
    
    // Обработка успешной валидации второго шага
    LaunchedEffect(uiState.isStep2Valid) {
        if (uiState.isStep2Valid) {
            onNavigateToStep3()
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Верхняя панель
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.step_2_of_3),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            }
        )
        
        // Основной контент
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Заголовок
            Text(
                text = "Личные данные",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Введите ваши личные данные",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Поле Фамилия
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(stringResource(R.string.last_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.lastNameError != null
            )
            
            uiState.lastNameError?.let { lastNameError ->
                Text(
                    text = lastNameError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Поле Имя
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(stringResource(R.string.first_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.firstNameError != null
            )
            
            uiState.firstNameError?.let { firstNameError ->
                Text(
                    text = firstNameError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Поле Отчество (необязательное)
            OutlinedTextField(
                value = middleName,
                onValueChange = { middleName = it },
                label = { Text(stringResource(R.string.middle_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Поле Дата рождения с DatePicker
            OutlinedTextField(
                value = birthDateText,
                onValueChange = { },
                label = { Text(stringResource(R.string.birth_date)) },
                placeholder = { Text("Выберите дату рождения") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Выбрать дату"
                        )
                    }
                },
                isError = uiState.birthDateError != null
            )
            
            uiState.birthDateError?.let { birthDateError ->
                Text(
                    text = birthDateError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Поле Пол
            ExposedDropdownMenuBox(
                expanded = isGenderExpanded,
                onExpandedChange = { isGenderExpanded = !isGenderExpanded }
            ) {
                OutlinedTextField(
                    value = selectedGender,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text(stringResource(R.string.gender)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    isError = uiState.genderError != null
                )
                
                ExposedDropdownMenu(
                    expanded = isGenderExpanded,
                    onDismissRequest = { isGenderExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(maleText) },
                        onClick = {
                            selectedGender = maleText
                            isGenderExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(femaleText) },
                        onClick = {
                            selectedGender = femaleText
                            isGenderExpanded = false
                        }
                    )
                }
            }
            
            uiState.genderError?.let { genderError ->
                Text(
                    text = genderError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // DatePicker Dialog
            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = selectedBirthDate?.time
                )
                
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    selectedBirthDate = Date(millis)
                                }
                                showDatePicker = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Отмена")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            
            // Кнопка "Далее"
            Button(
                onClick = {
                    val birthDateString = selectedBirthDate?.let { dateFormatter.format(it) } ?: ""
                    viewModel.validateStep2(lastName, firstName, middleName, birthDateString, selectedGender)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading && lastName.isNotEmpty() && firstName.isNotEmpty() && selectedBirthDate != null && selectedGender.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = stringResource(R.string.next),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            // Показ общих ошибок
            uiState.errorMessage?.let { errorMessage ->
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            
            // Отступ внизу для прокрутки
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpStep2ScreenPreview() {
    DriveNextTheme {
        SignUpStep2Screen(
            onNavigateBack = { },
            onNavigateToStep3 = { }
        )
    }
}
