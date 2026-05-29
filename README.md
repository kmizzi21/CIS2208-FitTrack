# FitTrack

FitTrack is an Android workout tracking application developed using Java and Android Studio. The application allows users to record, manage, and monitor their workouts through a simple and user-friendly interface.

## Features

* Add workout entries
* View workout history
* View detailed workout information
* Edit existing workouts
* Delete workouts with confirmation dialogs
* Upload progress images from device storage
* Display motivational fitness tips retrieved from an online API
* Responsive user interface with animations
* Local data persistence using SQLite

## Technologies Used

* Java
* Android Studio
* SQLite
* RecyclerView
* Fragments
* Intents and ActivityResultLauncher
* HTTP Requests (ZenQuotes API)
* Material Design Components
* XML Layouts

## Project Structure

* `MainActivity` – Hosts the HomeFragment
* `HomeFragment` – Home screen and fitness tip functionality
* `AddWorkoutActivity` – Create new workout entries
* `HistoryActivity` – Display workout history
* `WorkoutDetailsActivity` – View workout information
* `EditWorkoutActivity` – Update existing workouts
* `WorkoutDatabaseHelper` – SQLite database operations
* `WorkoutAdapter` – RecyclerView adapter

## Database Functionality

The application uses SQLite for local storage and supports full CRUD operations:

* Create workout entries
* Read workout data
* Update existing workouts
* Delete workouts

## API Integration

FitTrack uses the ZenQuotes API to retrieve motivational quotes dynamically using HTTP GET requests.

API Endpoint:

https://zenquotes.io/api/random

## Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Build and run the application on an emulator or Android device.

## Author

Keane Mizzi
