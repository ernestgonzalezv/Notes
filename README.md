# **Clean Notes App (2026)**

Modern **Jetpack Compose** application focused on **Clean Architecture**, offline-first persistence, and a decoupled observability engine.

## **Core Tech Stack**
* **UI:** Jetpack Compose (Material 3) + UDF.
* **Logic:** Clean Architecture + MVVM + Use Cases.
* **DI:** Koin 4.x.
* **Storage:** Room Persistence + Flow.
* **Concurrency:** Kotlin Coroutines.

---

## ** Analytics & Observability (Architecture Focus)**
A provider-agnostic system designed for scalability and **Zero-Code updates**.



* **Google Tag Manager (GTM):** Implemented local container orchestration via `GTM-P64M48HJ.json`. Enables dynamic tag firing without app releases.
* **Firebase Suite:** Type-safe **Analytics** via `Sealed Classes` and **Crashlytics** for real-time error monitoring.
* **Event Hub Pattern:** Decoupled Facade (`AnalyticsTracker`) that broadcasts events to multiple providers simultaneously.
* **Privacy-First:** Architecture-level support for GDPR/CCPA opt-out and data reset.

---

## ** Testing & Quality**
* **Unit Tests:** MockK + Google Truth for business rules and Use Cases.
* **E2E Tests:** Compose Testing Library covering primary user flows.
* **Standardization:** Centralized dependency management via **Gradle Version Catalogs**.

---

## ** Key Features**
* ✅ **Clean Architecture:** Strict separation of concerns (Domain, Data, Presentation).
* ✅ **Real-time UX:** Reactive state management with Kotlin Flow.
* ✅ **Scalable Monitoring:** GTM + Firebase integration for business intelligence.
* ✅ **Offline-First:** Robust local data handling.

---
