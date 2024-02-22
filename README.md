# Countdown App

## Overview

This Android application allows users to input their name, birthday, and optionally, a profile image, and then calculates and displays the time left until their next birthday. It provides a countdown timer that updates in real-time, showing the remaining days, hours, minutes, and seconds until the user's next birthday.

## Features

- **Input Data:** Users can input their name and birthday date.
- **Profile Image:** Optionally, users can select a profile image to personalize their countdown.
- **Real-Time Countdown:** The app dynamically calculates the time remaining until the user's next birthday and updates the countdown timer accordingly.
- **Birthday Reminder:** When the countdown timer reaches zero, the app displays a "Happy Birthday" message.

## Technologies Used

- **Android Studio:** Developed using the Android Studio IDE.
- **Kotlin:** Implemented in Kotlin programming language.
- **Date and Time Handling:** Utilizes Java's `LocalDate`, `LocalDateTime`, and `Duration` classes for accurate date and time calculations.

## Light Mode and Dark Mode Support

This app supports both light mode and dark mode for better user experience. It adapts its appearance based on the system's theme settings.

### Configuration

- Define theme attributes for both light and dark modes in the `res/values/styles.xml` file.
- Configure the theme in the `AndroidManifest.xml` file to use the defined theme.
- Customize theme attributes and adjust layouts to ensure compatibility with both light and dark modes.

### Automatic Mode Switching

The app also supports automatic mode switching based on the system's dark mode settings. Dark mode specific resources are placed in the `res/values-night/` directory.

## Getting Started

To get started with the Countdown App, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or physical device.
