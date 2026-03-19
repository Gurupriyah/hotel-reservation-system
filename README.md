[+]# 🏨 Eden Inn — Hotel Reservation System

## 📌 Overview

Eden Inn is a simple console-based hotel reservation application originally created as part of the Udacity Java Programming course.

The project demonstrates core Java concepts such as object-oriented programming (OOP), use of collections, and basic application design by providing a small, working system for managing hotel bookings.

---

## 🚀 Features

- Create and manage customer accounts
- Search for available rooms
- Book rooms and view reservations
- Admin tools for managing rooms and reservations

---

## 🛠️ Technologies

- Java (JDK 8 or newer)
- Java Collections
- Console (command-line) interface

---

## 🏗️ Project Structure

```
src/
├── api/       # Resource layer (HotelResource, AdminResource)
├── menu/      # CLI interface (MainMenu, AdminMenu)
├── model/     # Domain models (Customer, Room, Reservation, etc.)
└── service/   # Business logic and in-memory data handling
```

---

## ▶️ How to run

### 🔹 Using an IDE (recommended)

1. Open the project in IntelliJ IDEA (or another Java IDE).
2. Make sure a Java SDK (JDK 8+) is configured for the project.
3. Run the `menu.MainMenu` class as a Java application.
4. Follow the console prompts.

### 🔹 Using the command line (PowerShell)

Note: These examples use Windows PowerShell. Ensure `javac` and `java` are on your PATH.

```powershell
# Create output directory (if it doesn't exist)
if (-not (Test-Path -Path out)) { New-Item -ItemType Directory -Path out | Out-Null }

# Compile all Java sources under src and place class files in the out directory
javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object -ExpandProperty FullName)

# Run the application
java -cp out menu.MainMenu
```

If you prefer a UNIX-like shell (Git Bash, WSL, etc.) you can use the following commands instead:

```bash
# Create output directory
mkdir -p out

# Compile all Java files
javac -d out $(find src -name "*.java")

# Run the application
java -cp out menu.MainMenu
```

---

## Notes

- This project uses an in-memory data store (no external database). Data will be lost when the program exits.
- The console menus are under `src/menu` and are the recommended way to interact with the application.
- If you encounter compilation errors, confirm your JDK version and that your CLASSPATH is not interfering with compilation.

---

If you'd like, I can also add build scripts (Gradle or Maven) to make compilation and running easier.
