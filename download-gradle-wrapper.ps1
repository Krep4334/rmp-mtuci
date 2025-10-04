# PowerShell script to download Gradle Wrapper
$gradleVersion = "8.4"
$wrapperUrl = "https://services.gradle.org/distributions/gradle-$gradleVersion-bin.zip"
$wrapperJarUrl = "https://github.com/gradle/gradle/raw/v$gradleVersion/gradle/wrapper/gradle-wrapper.jar"

# Create gradle/wrapper directory
New-Item -ItemType Directory -Force -Path "gradle\wrapper"

# Download gradle-wrapper.jar
Write-Host "Downloading Gradle Wrapper JAR..."
try {
    Invoke-WebRequest -Uri $wrapperJarUrl -OutFile "gradle\wrapper\gradle-wrapper.jar"
    Write-Host "✅ Gradle Wrapper JAR downloaded successfully!"
} catch {
    Write-Host "❌ Failed to download from GitHub, trying alternative source..."
    # Alternative: download from a working Gradle installation
    $altUrl = "https://services.gradle.org/distributions/gradle-$gradleVersion-bin.zip"
    Write-Host "Please download Gradle manually from: $altUrl"
    Write-Host "Extract it and copy gradle-wrapper.jar from gradle/wrapper/ folder"
}

Write-Host ""
Write-Host "After downloading, you can run:"
Write-Host "  .\gradlew.bat clean"
Write-Host "  .\gradlew.bat build"
