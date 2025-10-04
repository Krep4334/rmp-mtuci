# DriveNext - Мобильное приложение аренды автомобилей

## Описание проекта

DriveNext - это Android приложение для смартфонов, предназначенное для аренды автомобилей. Приложение разработано с использованием нативных Android технологий и современной многослойной архитектуры.

## Технологический стек

- **Платформа**: Android (нативная разработка)
- **Минимальная версия**: Android 7.0+ (API 24)
- **Ориентация**: Портретная (телефон)
- **Язык программирования**: Kotlin
- **UI Framework**: Jetpack Compose
- **Архитектура**: Clean Architecture (Domain, Data, Presentation)
- **DI**: Dagger Hilt
- **Навигация**: Navigation Compose
- **Сеть**: Retrofit + OkHttp
- **База данных**: Room (локальное кэширование)
- **Бэкенд**: Supabase (настройка требуется)
- **Управление состоянием**: ViewModel + StateFlow

## Структура проекта

```
app/
├── src/main/java/com/drivenext/app/
│   ├── domain/              # Доменный слой
│   │   ├── model/          # Модели данных
│   │   ├── repository/     # Интерфейсы репозиториев
│   │   ├── usecase/        # Use cases (бизнес-логика)
│   │   └── util/           # Утилиты домена
│   ├── data/               # Слой данных
│   │   ├── remote/         # Удаленные источники данных
│   │   ├── repository/     # Реализации репозиториев
│   │   └── dto/            # DTO объекты
│   ├── presentation/       # Слой представления
│   │   ├── screens/        # Экраны приложения
│   │   ├── navigation/     # Навигация
│   │   ├── theme/          # Тема приложения
│   │   └── util/           # UI утилиты
│   └── di/                 # Dependency Injection
```

## Реализованные экраны

### ✅ Базовая структура
- [x] Многослойная архитектура (Clean Architecture)
- [x] Dependency Injection с Hilt
- [x] Навигация между экранами
- [x] Тема приложения с Material Design 3

### ✅ Экраны приложения
- [x] **Splash Screen** - Экран загрузки с логотипом и слоганом
- [x] **No Internet** - Экран отсутствия подключения к интернету
- [x] **Onboarding** - Приветственные слайды (3 страницы)
- [x] **Auth Welcome** - Выбор способа входа/регистрации
- [x] **Sign In** - Вход в систему (email, пароль, Google OAuth заготовка)
- [x] **Sign Up** - Регистрация (Шаг 1: email, пароль, согласие)

### 🔄 В разработке
- [ ] **Sign Up Step 2** - Персональные данные (ФИО, дата рождения, пол)
- [ ] **Sign Up Step 3** - Документы (фото профиля, ВУ, паспорт)
- [ ] **Sign Up Success** - Успешная регистрация
- [ ] Основные экраны приложения (каталог, детали автомобиля, бронирование)

## Настройка проекта

### 1. Клонирование репозитория
```bash
git clone <repository-url>
cd rmp
```

### 2. Настройка Supabase

Для подключения к Supabase выполните следующие шаги:

1. Создайте проект в [Supabase](https://supabase.com/)
2. Получите URL проекта и anon key
3. Откройте файл `app/src/main/java/com/drivenext/app/data/remote/SupabaseClient.kt`
4. Замените значения:
```kotlin
private val supabaseUrl = "YOUR_SUPABASE_URL"
private val supabaseKey = "YOUR_SUPABASE_ANON_KEY"
```

### 3. Создание таблиц в Supabase

Выполните следующие SQL команды в Supabase SQL Editor:

```sql
-- Таблица пользователей
CREATE TABLE users (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    middle_name TEXT,
    birth_date DATE NOT NULL,
    gender TEXT NOT NULL CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
    profile_photo_url TEXT,
    driver_license_number TEXT,
    driver_license_issue_date DATE,
    driver_license_photo_url TEXT,
    passport_photo_url TEXT,
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Таблица автомобилей
CREATE TABLE cars (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    year INTEGER NOT NULL,
    color TEXT NOT NULL,
    license_plate TEXT UNIQUE NOT NULL,
    fuel_type TEXT NOT NULL CHECK (fuel_type IN ('GASOLINE', 'DIESEL', 'ELECTRIC', 'HYBRID')),
    transmission TEXT NOT NULL CHECK (transmission IN ('MANUAL', 'AUTOMATIC')),
    seating_capacity INTEGER NOT NULL,
    price_per_day DECIMAL(10,2) NOT NULL,
    image_urls TEXT[],
    features TEXT[],
    location_address TEXT NOT NULL,
    location_latitude DOUBLE PRECISION NOT NULL,
    location_longitude DOUBLE PRECISION NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    rating DECIMAL(3,2) DEFAULT 0,
    reviews_count INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Таблица бронирований
CREATE TABLE bookings (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    car_id UUID REFERENCES cars(id) ON DELETE CASCADE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status TEXT NOT NULL CHECK (status IN ('PENDING', 'CONFIRMED', 'ACTIVE', 'COMPLETED', 'CANCELLED')),
    pickup_location TEXT NOT NULL,
    dropoff_location TEXT NOT NULL,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Индексы для оптимизации
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_cars_available ON cars(is_available);
CREATE INDEX idx_bookings_user_id ON bookings(user_id);
CREATE INDEX idx_bookings_car_id ON bookings(car_id);
CREATE INDEX idx_bookings_dates ON bookings(start_date, end_date);
```

### 4. Настройка Google OAuth (опционально)

Для настройки Google OAuth:
1. Создайте проект в [Google Cloud Console](https://console.cloud.google.com/)
2. Настройте OAuth 2.0 credentials
3. Добавьте SHA-1 fingerprint вашего приложения
4. Обновите код в соответствующих местах (помечены TODO)

### 5. Сборка проекта

```bash
# Открыть проект в Android Studio
# или через командную строку:
./gradlew assembleDebug
```

## Особенности реализации

### Архитектура
- **Clean Architecture** с разделением на слои Domain, Data, Presentation
- **MVVM** паттерн с использованием ViewModel и StateFlow
- **Dependency Injection** через Dagger Hilt
- **Repository Pattern** для абстракции работы с данными

### UI/UX
- **Jetpack Compose** для современного декларативного UI
- **Material Design 3** с кастомной цветовой схемой
- **Адаптивная верстка** для планшетов
- **Обработка состояний загрузки и ошибок**

### Безопасность
- Валидация пользовательского ввода
- Обработка ошибок сервера
- Проверка подключения к интернету
- Безопасное хранение токенов (готово к реализации)

## Дизайн

Дизайн приложения основан на макетах Figma: [DriveNext Design](https://www.figma.com/design/MpSh8Dp97oCbDJLFmW9Dko/DriveNext?node-id=3908-2710&t=GDFxPtDgBHah5M3a-1)

Цветовая схема:
- **Primary**: #007AFF (синий)
- **Secondary**: #FF6B35 (оранжевый) 
- **Success**: #34C759 (зеленый)
- **Error**: #FF3B30 (красный)

## Контрибьюция

1. Создайте feature branch из main
2. Внесите изменения
3. Создайте Pull Request с описанием изменений
4. Дождитесь code review

## Лицензия

Проект разрабатывается под закрытой лицензией.

## Контакты

Для вопросов по проекту обращайтесь к команде разработки.
