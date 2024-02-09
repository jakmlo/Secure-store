# SecureStore - Android App

This Android application was developed as a college project to manage credit card information securely. It provides functionalities like login, registration, session persistence, and the ability to store, view, edit, and delete credit card numbers encrypted using Keystore keys.


## Features

- **Authentication:** Users can register and log in securely using Firebase Authentication.
- **Session Persistence:** User sessions are remembered, so users don't need to log in every time the app starts.
- **Credit Card Management:**
  - Store credit card numbers securely in a local Room Database.
  - Encrypt credit card numbers using Keystore keys for enhanced security.
  - View, edit, and delete saved credit card numbers.
- **Recycler View:** Display saved credit card numbers in a RecyclerView for easy navigation and management.

## Technologies Used

- **Kotlin:** The app is written in Kotlin, a modern programming language for Android development.
- **Firebase Authentication:** Provides secure authentication services for user registration and login.
- **Room Database:** Local database for storing encrypted credit card numbers.
- **Android Keystore:** For securely storing encryption keys.

**google-service.json** needed to connect with firebase https://firebase.google.com/docs/android/setup?hl=pl



