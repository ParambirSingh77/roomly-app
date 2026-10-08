# Commit plan

Commit each stage when you have added its files, built the project and checked it works.
Spread the stages over real working sessions so the history shows steady progress.

First time only, inside the project folder:

    git init
    git branch -M main
    git remote add origin <your repository address>

## Stage 1: Project setup
Files: `.gitignore`, `settings.gradle`, `build.gradle`, `gradle.properties`, `gradle/wrapper/gradle-wrapper.properties`, `app/build.gradle`, `app/src/main/AndroidManifest.xml`, `app/src/main/res/values/` (colors, themes, dimens, integers, strings), `app/src/main/res/drawable/ic_roomly_logo.xml`

    git add .
    git commit -m "Set up Roomly project with theme, colours and strings"

## Stage 2: Sample data model
Files: `Booking.java`, `BookingStore.java`

    git commit -am "Add Booking model and sample data store"

## Stage 3: Request booking screen
Files: `RequestActivity.java`, `layout/activity_request.xml`, `drawable/ic_back.xml`

    git add . && git commit -m "Add request booking screen"

## Stage 4: Rooms screen
Files: `RoomAdapter.java`, `RoomsFragment.java`, `layout/item_room.xml`, `layout/fragment_rooms.xml`

    git add . && git commit -m "Add rooms screen with date and time pickers"

## Stage 5: My bookings screen
Files: `BookingAdapter.java`, `MyBookingsFragment.java`, `layout/item_booking.xml`, `layout/fragment_my_bookings.xml`, `drawable/tag_free.xml`, `drawable/tag_wait.xml`

    git add . && git commit -m "Add my bookings screen with status tags and cancel"

## Stage 6: Main screen and navigation
Files: `MainActivity.java`, `layout/activity_main.xml`, `menu/bottom_nav.xml`, `drawable/ic_rooms.xml`, `ic_bookings.xml`, `ic_logout.xml`

    git add . && git commit -m "Add main screen with bottom navigation and log out"

## Stage 7: Login screen
Files: `LoginActivity.java`, `layout/activity_login.xml`

    git add . && git commit -m "Add login screen with validation"

## Stage 8: Screen size and orientation support
Files: `layout-land/` (login and rooms), `values-sw600dp/`, `values-night/`

    git add . && git commit -m "Add landscape, tablet and dark mode layouts"

## Stage 9: Documentation
Files: `README.md`, `COMMIT_PLAN.md`

    git add . && git commit -m "Add README"

Push after each commit:

    git push -u origin main

Also commit smaller fixes as you find them, for example text changes or layout tweaks.
