
---

# **Clean Architecture Note App **

A modern, offline-first notes application built with **Jetpack Compose**, focusing on Clean Architecture principles, reactive state management, and a comprehensive automated testing suite.

## Tech Stack

* **UI:** Jetpack Compose (Material 3)
* **Architecture:** Clean Architecture + MVVM
* **DI:** Koin 4.x (Modern & Lightweight)
* **Database:** Room Persistence 
* **Concurrency:** Kotlin Coroutines & Flow
* **Testing:** * **Unit:** MockK, Google Truth, JUnit 5
* **UI/E2E:** Compose Test Rule, Koin Testing



---

## Project Structure

```text
com.egcoding.notes
├── core                # Global utilities and navigation
├── di                  # Koin modules (AppModule, TestAppModule)
├── domain              # Business logic (Models, Repositories, Use Cases)
└── presentation        # UI Screens, ViewModels, and State

```

---

## Architecture Overview

This project follows **Uncle Bob's Clean Architecture**. Each feature is divided into three layers:

1. **Domain Layer:** Contains the pure Kotlin business logic. It defines the `Note` entity and the `NoteRepository` interface. It uses **Use Cases** (e.g., `GetNotes`, `AddNote`) to execute specific business actions.
2. **Data Layer:** Handles data persistence. It contains the Room database implementation, DAOs, and the concrete implementation of the repository.
3. **Presentation Layer:** Powered by Jetpack Compose. **ViewModels** interact with Use Cases and expose a single **State** object to the UI to ensure a Unidirectional Data Flow (UDF).

---

## Testing Suite

The application is heavily tested to ensure reliability and prevent regressions.

### Unit Tests

Located in `src/test`, these tests validate:

* **Use Cases:** Sorting logic (Date, Title, Color) and business rules.
* **ViewModels:** State transitions and UI event handling using **MockK**.

### End-to-End (E2E) / Instrumented Tests

Located in `src/androidTest`, using the **Compose Testing Library**:

* **Navigation:** Verified using a test-specific `NavHost`.
* **Persistence:** Uses a **Room In-Memory Database** to ensure tests are isolated and don't affect local data.


---

## Setup & Installation

1. **Clone the repo:**
```bash
git clone https://github.com/your-repo/clean-architecture-note-app.git

```


2. **Open in Android Studio:** Ensure you are using the **Ladybug** version or newer (2024/2025+).
3. **Sync Gradle:** The project uses **Version Catalogs** (`libs.versions.toml`) for dependency management.
4. **Run Tests:**
```bash
./gradlew test                 # Run unit tests
./gradlew connectedAndroidTest # Run E2E tests on emulator/device

```



---

## **Features**

* ✅ Create and Edit Notes with custom colors.
* ✅ Real-time sorting by Title, Date, or Color.
* ✅ Undo delete functionality with Snackbars.
* ✅ Fully responsive UI with Material 3.
* ✅ Robust E2E Testing suite covering all primary user flows.

---
