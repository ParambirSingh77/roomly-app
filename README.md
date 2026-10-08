# Roomly (Employee interface)

Android Studio project for COMP2000 Assessment 1, Exercise 5.

This stage builds the high fidelity user interface for the **Employee** role only. There is no API or database yet. Sample data is kept in memory in `BookingStore.java`.

## Screens
1. Login
2. Rooms (choose a date and time, see only free rooms)
3. Request booking (confirm details and send)
4. My bookings (status, cancel)

The design follows version 2 of the prototype: a pending request shows "Waiting for manager approval" with a note explaining that a manager will review it.

## How to run
1. Open the project folder in Android Studio.
2. Let Gradle sync finish.
3. Run on an emulator or phone (Android 7.0 or higher).
4. Log in as any username. Use the username **Alex** to see a sample confirmed booking on My bookings.

## Adapting to screen sizes
* Portrait and landscape layouts for Login and Rooms (`layout-land`).
* Tablet values in `values-sw600dp` (larger padding and two column lists).
* Dark mode colours in `values-night`.

## Notes
* Manager screens, the REST API and the local database belong to Assessment 2.
* Some code and layouts were written with AI assistance. Declare this in the AI declaration and make sure you can explain every file.
