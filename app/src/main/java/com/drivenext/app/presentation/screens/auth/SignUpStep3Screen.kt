package com.drivenext.app.presentation.screens.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.drivenext.app.R
import com.drivenext.app.presentation.theme.DriveNextTheme
import com.drivenext.app.presentation.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

/**
 * Экран регистрации (Шаг 3 из 3)
 * Документы и фото: фото профиля, номер ВУ, дата выдачи, загрузка документов
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpStep3Screen(
    onNavigateBack: () -> Unit,
    onRegistrationSuccess: () -> Unit,
    viewModel: SignUpStep3ViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
) {
    var profilePhotoUri by remember { mutableStateOf<Uri?>(null) }
    var driverLicenseNumber by remember { mutableStateOf("") }
    var selectedIssueDate by remember { mutableStateOf<Date?>(null) }
    var driverLicensePhotoUri by remember { mutableStateOf<Uri?>(null) }
    var passportPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    
    val uiState by viewModel.uiState.collectAsState()
    
    // Функция для форматирования даты
    val dateFormatter = remember { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }
    val issueDateText = selectedIssueDate?.let { dateFormatter.format(it) } ?: ""
    
    // Launcher для выбора фото профиля
    val profilePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { profilePhotoUri = it }
    }
    
    // Launcher для выбора фото водительского удостоверения
    val driverLicensePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { driverLicensePhotoUri = it }
    }
    
    // Launcher для выбора фото паспорта
    val passportPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { passportPhotoUri = it }
    }
    
    // Обработка успешной регистрации
    LaunchedEffect(uiState.isRegistrationSuccessful) {
        if (uiState.isRegistrationSuccessful) {
            onRegistrationSuccess()
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Верхняя панель
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.step_3_of_3),
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
                text = "Документы и фото",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Загрузите необходимые документы",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Фото профиля
            Text(
                text = stringResource(R.string.profile_photo),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .clip(MaterialTheme.shapes.medium),
                onClick = {
                    profilePhotoLauncher.launch("image/*")
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (profilePhotoUri != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(profilePhotoUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Фото профиля",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Добавить фото",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Поле Номер водительского удостоверения
            OutlinedTextField(
                value = driverLicenseNumber,
                onValueChange = { driverLicenseNumber = it },
                label = { Text(stringResource(R.string.driver_license_number)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.driverLicenseNumberError != null
            )
            
            uiState.driverLicenseNumberError?.let { driverLicenseNumberError ->
                Text(
                    text = driverLicenseNumberError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Поле Дата выдачи ВУ с DatePicker
            OutlinedTextField(
                value = issueDateText,
                onValueChange = { },
                label = { Text(stringResource(R.string.driver_license_issue_date)) },
                placeholder = { Text("Выберите дату выдачи") },
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
                isError = uiState.driverLicenseIssueDateError != null
            )
            
            uiState.driverLicenseIssueDateError?.let { driverLicenseIssueDateError ->
                Text(
                    text = driverLicenseIssueDateError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Загрузка фото водительского удостоверения
            Text(
                text = stringResource(R.string.upload_driver_license_photo),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                onClick = {
                    driverLicensePhotoLauncher.launch("image/*")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Загрузить фото")
            }
            
            if (driverLicensePhotoUri != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(driverLicensePhotoUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Фото ВУ",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Фото ВУ загружено",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Загрузка фото паспорта
            Text(
                text = stringResource(R.string.upload_passport_photo),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                onClick = {
                    passportPhotoLauncher.launch("image/*")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Загрузить фото")
            }
            
            if (passportPhotoUri != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(passportPhotoUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Фото паспорта",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Фото паспорта загружено",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // DatePicker Dialog
            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = selectedIssueDate?.time
                )
                
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    selectedIssueDate = Date(millis)
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
                    val issueDateString = selectedIssueDate?.let { dateFormatter.format(it) } ?: ""
                    // Создаем данные для регистрации (пока с тестовыми данными)
                    val registrationData = RegistrationData(
                        email = "test@example.com", // Тестовый email
                        password = "testPassword123", // Тестовый пароль
                        firstName = "Тестовое",
                        lastName = "Имя",
                        middleName = "Отчество",
                        birthDate = "15.05.1990",
                        gender = "Мужской",
                        driverLicenseNumber = driverLicenseNumber,
                        driverLicenseIssueDate = issueDateString,
                        driverLicensePhotoUri = driverLicensePhotoUri,
                        passportPhotoUri = passportPhotoUri
                    )
                    viewModel.completeRegistration(registrationData)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading && driverLicenseNumber.isNotEmpty() && selectedIssueDate != null && driverLicensePhotoUri != null && passportPhotoUri != null,
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
fun SignUpStep3ScreenPreview() {
    DriveNextTheme {
        SignUpStep3Screen(
            onNavigateBack = { },
            onRegistrationSuccess = { }
        )
    }
}
