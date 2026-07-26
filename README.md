# Laraib_Task3 - Calculator App

## Objective
A clean, fully functional calculator application built for Android using Java and XML. This project demonstrates a responsive button grid interface capable of handling standard arithmetic operations with proper error handling. This project was built as part of my hands-on experience with Android app development, focusing on UI layout design, expression parsing, and edge-case handling.

## Overview
This app allows users to perform basic mathematical calculations through an intuitive touch interface. It follows the standard order of operations (multiplication and division before addition and subtraction) and gracefully handles edge cases such as division by zero.

## Tech Stack
- **Language:** Java
- **Layout:** XML (GridLayout for the keypad, LinearLayout for the overall structure)
- **IDE:** Android Studio
- **Minimum SDK:** API 21 (Android 5.0 Lollipop) or higher

## Features
- Live expression and result display
- Digit buttons (0–9) with decimal point support
- Arithmetic operators: addition, subtraction, multiplication, division
- Percentage calculation
- Equals button to evaluate the full expression
- Clear button to reset the calculator
- Backspace button to remove the last entered character
- Division-by-zero protection with an on-screen "Error" message
- Stable performance under rapid or repeated button presses

## Project Structure
```
app/
└── src/
    └── main/
        ├── java/
        │   └── com/yourpackage/calculator/
        │       └── MainActivity.java
        ├── res/
        │   ├── layout/
        │   │   └── activity_main.xml
        │   └── values/
        │       ├── colors.xml
        │       └── styles.xml
        └── AndroidManifest.xml
```

## How It Works
1. Numeric and operator input is captured and appended to a `StringBuilder`, which builds the full expression as the user types.
2. When the equals button is pressed, the expression is parsed and split into numbers and operators.
3. Multiplication and division are resolved first, followed by addition and subtraction, to maintain correct mathematical order.
4. If a division by zero is detected during evaluation, the app catches the exception and displays "Error" instead of crashing.
5. The backspace button removes the most recent character, while the clear button resets the entire calculator state.

## How to Run
1. Clone or download this project into Android Studio.
2. Let Gradle sync automatically, or trigger it manually via **File → Sync Project with Gradle Files**.
3. Connect a physical device or start an emulator.
4. Run the app using the **Run** button in Android Studio.

## Download APK
You can also directly install the app on your Android device without building it from source:
1. Download the APK file from this repository
2. Enable "Install from Unknown Sources" in your device settings (if not already enabled)
3. Open the downloaded APK file and follow the installation prompts

## Usage
- Tap number buttons to build an expression.
- Tap an operator to chain further calculations.
- Tap the equals button to see the final result.
- Tap clear to start a new calculation.
- Tap backspace to correct a mistake without clearing everything.

## Known Limitations
- Does not currently support parentheses or advanced functions (e.g., square root, exponents).
- Only supports single-line expressions without operator precedence beyond basic BODMAS rules.

## Future Improvements
- Add support for parentheses and nested expressions.
- Add scientific calculator functions.
- Add calculation history.
- Add light/dark theme toggle.

## Author
Laraib Tariq
