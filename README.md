
# Phone Authentication Project

This project is an Android application for sending a phone number to a server and interacting with USB devices via UART. The project is built using the MVVM architecture, Retrofit for network communication, and Hilt for dependency injection.

## Features
- **Send Phone Number to Server**: Send the entered phone number to the server and receive a response.
- **Interact with USB Devices**: Send the server's response to connected USB devices.
- **UART Support**: Communicate with UART devices via USB for data transmission.
- **Error Handling**: Implement error management and appropriate user feedback.

## Technologies Used
- **Kotlin**: The main programming language for the project.
- **Jetpack Compose**: For building the user interface.
- **Retrofit**: For making HTTP requests to the server.
- **Hilt**: For dependency injection.
- **MVVM**: The architecture pattern implemented in the project.
- **USB Serial Library**: For interacting with USB devices.

## Prerequisites
To run the project, you need the following:
- Android Studio 8.0 or higher.
- Java Development Kit (JDK) 8 or higher.

## Installation and Setup
1. **Clone the repository**:
   ```bash
   git clone https://github.com/mrnoise6848/TestUart.git
   ```

2. **Open the project in Android Studio**:
   - Open the project using Android Studio.

3. **Add permissions**:
   Make sure to add the following permissions to your `AndroidManifest.xml` file:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.USB_PERMISSION" />
   <uses-feature android:name="android.hardware.usb.host" />
   ```

4. **Run the project**:
   - Build and run the project on an emulator or physical device using Android Studio.

## Usage
1. **Enter Phone Number**:
   - The user can enter their phone number using a numeric keypad.

2. **Send Phone Number to Server**:
   - The entered phone number is sent to the server by clicking the send button.

3. **Receive Response and Send to USB Device**:
   - The server response, which includes a six-digit code, is sent to the connected USB device.

4. **Error Handling**:
   - Appropriate messages are displayed to the user if any errors occur.


## Development and Collaboration
1. **Create a new branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Build and test the code**:
   - Add new code and test it thoroughly.

3. **Commit changes**:
   ```bash
   git add .
   git commit -m "Added new feature"
   git push origin feature/your-feature-name
   ```

4. **Create a Pull Request (PR)**:
   - Submit your PR to the main branch and explain your changes.

## Troubleshooting
If you encounter any issues with the project, please check the following:
- Ensure that the correct versions of Android Studio and JDK are installed.
- Verify that the permissions in `AndroidManifest.xml` are correct.
- Review the logs for detailed error information.

## License
This project is licensed under the [MIT License](https://github.com/mrnoise6848). For more details, please check the `LICENSE` file.

---
