# **Clean Architecture Note App (2026 Edition)**

A modern, offline-first notes application built with **Jetpack Compose**, focusing on Clean Architecture principles, reactive state management, and a comprehensive automated testing suite with integrated monitoring.

## Tech Stack

* **UI:** Jetpack Compose (Material 3)
* **Architecture:** Clean Architecture + MVVM
* **DI:** Koin 4.x (Modern & Lightweight)
* **Database:** Room Persistence (Offline-first)
* **Concurrency:** Kotlin Coroutines & Flow
* **Monitoring:**
    * **Analytics:** Firebase Analytics (via Custom Provider Architecture)
    * **Crash Reporting:** Firebase Crashlytics
* **Testing:**
    * **Unit:** MockK, Google Truth, JUnit 5
    * **UI/E2E:** Compose Test Rule, Koin Testing

---

## Project Structure

```text
com.egcoding.notes
├── core                # Global utilities, navigation, and theme
│   └── analytics       # Event tracking system & provider abstractions
├── di                  # Koin modules (AppModule, TestAppModule)
├── domain              # Business logic (Models, Repositories, Use Cases)
└── presentation        # UI Screens, ViewModels, and State