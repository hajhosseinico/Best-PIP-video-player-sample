
# Best PIP Video Player Sample

---

<div align="center">
    <img src="https://github.com/user-attachments/assets/345158ad-5321-42fb-a1c0-43f2f326bf89" width="250">
    <img src="https://github.com/user-attachments/assets/df5a0b22-1859-4ec7-9bd9-c34c91f26bda" width="250">
    <img src="https://github.com/user-attachments/assets/fbcf8d12-c418-4c13-9217-87d5ba9a8253" width="250">
</div>

---

**Best PIP Video Player Sample** is a beautifully structured Android app that showcases a modern Picture-in-Picture (PiP) video player with a clean, modular architecture. Built with **Clean Architecture** principles and following the **MVVM** pattern, the app offers a seamless video viewing experience that transitions smoothly between fullscreen and PiP modes. This app leverages **Jetpack Compose** for UI development and **Hilt** for dependency injection, ensuring efficient code management and scalability.

## 🚀 Features

- **Picture-in-Picture Mode**: Smooth PiP transitions, auto-enter and exit capabilities, with custom play/pause actions.
- **Clean Architecture**: Layered architecture for maintainable, testable, and scalable code.
- **MVVM Architecture**: Efficiently separates UI, data, and business logic with observable states.
- **Jetpack Compose UI**: Declarative UI with Material Design 3 styling for a modern look.
- **Hilt Dependency Injection**: Simplified dependency management using Hilt.
- **ExoPlayer Integration**: High-performance video playback with extensive customization options.
- **Kotlin Coroutines**: Asynchronous programming for seamless user experiences.

## 📱 Technologies and Libraries

### Core Libraries

- **AndroidX Core KTX**
- **Material Design 3**
- **ExoPlayer** for video playback

### Architecture Components

- **MVVM (Model-View-ViewModel)**: Manages UI-related data with ViewModel for better separation of concerns.
- **Hilt**: Dependency injection framework to simplify object creation and management.

### UI

- **Jetpack Compose**: Build UIs declaratively and manage them efficiently with state.
- **Compose Navigation**: Easily navigate between screens in a modular fashion.

### Video Playback

- **ExoPlayer**: For smooth video playback with customizable PiP support.

### Dependency Injection

- **Hilt**: Dependency Injection for streamlined and efficient code.

## 🛠️ Project Structure

The app is structured using **Clean Architecture** principles, ensuring a clear separation of responsibilities and making the codebase easier to maintain, test, and scale.

1. **Presentation Layer**: Includes UI components using Jetpack Compose and ViewModel, following the MVVM pattern.
2. **Domain Layer**: Holds core business logic in UseCases, which keeps functionality isolated from data and UI layers.
3. **Data Layer**: Responsible for data handling, currently utilizing ExoPlayer for video sources.

## 🧩 Project Setup

### Clone the Repository

```bash
git clone https://github.com/hajhosseinico/Best-PIP-video-player-sample.git
cd Best-PIP-video-player-sample
```

### Setup Dependencies

Open the project in Android Studio, and allow it to sync and download dependencies automatically.

### Build and Run

Select a device or emulator, and click on the **Run** button in Android Studio to start the app.

## 📄 License

Feel free to use this code to improve your projects, add PiP functionality, or explore ExoPlayer integration with Clean Architecture!

## 📬 Contact

For any questions or suggestions, feel free to reach out:

- **Author**: Ali Hajhosseini
- **Linkedin**: [Ali Hajhosseini](https://www.linkedin.com/in/ali-hajhosseini-354601aa/)
- **Email**: [hajhosseinico@gmail.com](mailto:hajhosseinico@gmail.com)
- **GitHub**: [hajhosseinico](https://github.com/hajhosseinico)
