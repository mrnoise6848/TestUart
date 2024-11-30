
Of course! Here's an English version of the README.md file for your project:

Phone Authentication Project
This project is an Android application for sending a phone number to a server and interacting with USB devices via UART. The project is built using the MVVM architecture, Retrofit for network communication, and Hilt for dependency injection.

Features
Send Phone Number to Server: Send the entered phone number to the server and receive a response.
Interact with USB Devices: Send the server's response to connected USB devices.
UART Support: Communicate with UART devices via USB for data transmission.
Error Handling: Implement error management and appropriate user feedback.
Technologies Used
Kotlin: The main programming language for the project.
Jetpack Compose: For building the user interface.
Retrofit: For making HTTP requests to the server.
Hilt: For dependency injection.
MVVM: The architecture pattern implemented in the project.
USB Serial Library: For interacting with USB devices.
Prerequisites
To run the project, you need the following:

Android Studio 4.0 or higher.
Java Development Kit (JDK) 8 or higher.
Installation and Setup
Clone the repository:

bash
Copy code
git clone https://github.com/your-username/phone-auth-project.git
Open the project in Android Studio:

Open the project using Android Studio.
Add permissions: Make sure to add the following permissions to your AndroidManifest.xml file:

xml
Copy code
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.USB_PERMISSION" />
<uses-feature android:name="android.hardware.usb.host" />
Run the project:

Build and run the project on an emulator or physical device using Android Studio.
Usage
Enter Phone Number:

The user can enter their phone number using a numeric keypad.
Send Phone Number to Server:

The entered phone number is sent to the server by clicking the send button.
Receive Response and Send to USB Device:

The server response, which includes a six-digit code, is sent to the connected USB device.
Error Handling:

Appropriate messages are displayed to the user if any errors occur.
Project Structure
css
Copy code

Development and Collaboration
Create a new branch:

bash
Copy code
git checkout -b feature/your-feature-name
Build and test the code:

Add new code and test it thoroughly.
Commit changes:

bash
Copy code
git add .
git commit -m "Added new feature"
git push origin feature/your-feature-name
Create a Pull Request (PR):

Submit your PR to the main branch and explain your changes.
Troubleshooting
If you encounter any issues with the project, please check the following:

Ensure that the correct versions of Android Studio and JDK are installed.
Verify that the permissions in AndroidManifest.xml are correct.
Review the logs for detailed error information.
