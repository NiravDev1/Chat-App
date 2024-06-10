# 📱 Chat Application

Welcome to the Chat Application project! This app enables users to communicate with each other in real-time. Users can sign up, log in, reset their passwords, and personalize their profiles with profile pictures. The backend is powered by Firebase, providing robust and scalable data storage and authentication services.

## 🛠️ Project Overview

The Chat Application is designed to offer a seamless and intuitive user experience for real-time communication. It includes features such as user authentication, secure messaging, and profile customization. The app is built using Android and utilizes Firebase for its backend services.

- **User Authentication**: Sign up, log in, and reset password functionalities.
- **Real-time Chat**: Send and receive messages instantly.
- **Profile Management**: Update profile information and add profile pictures.

## 📋 Features

### Authentication
- **Sign Up**: Register a new account using an email and password.
- **Log In**: Secure login using registered credentials.
- **Forgot Password**: Reset password functionality for users who have forgotten their password.

### Chat Functionality
- **Real-time Messaging**: Exchange messages in real-time with other users.
- **Message Status**: Indicators for sent, delivered, and read messages.

### Profile Management
- **Profile Picture**: Upload and update profile pictures.
- **User Info**: Update personal information such as username and status message.

## 🛠️ Technologies Used

- **Frontend**: Kotlin (Android)
- **Backend**: Firebase (Firestore, Realtime Database, Authentication, Storage)
- **Tools**: Android Studio, Firebase Console


## 🚀 Getting Started

### Prerequisites
- Android Studio installed on your system.
- Firebase account set up with Firestore, Realtime Database, Authentication, and Storage enabled.
- Basic knowledge of Kotlin and Firebase services.

### Installation Steps

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/yourusername/chat-application.git
    ```

2. **Open in Android Studio**:
    - Launch Android Studio and open the cloned repository.

3. **Set Up Firebase**:
    - Create a new project in Firebase Console.
    - Add your Android app to the Firebase project and download the `google-services.json` file.
    - Place `google-services.json` in the `app` directory of your project.

4. **Configure Firebase Services**:
    - Enable Firebase Authentication for email and password sign-in.
    - Set up Firestore for storing chat messages and user data.
    - Enable Firebase Storage for uploading profile pictures.

5. **Sync the Project**:
    - Go to `Tools` > `Firebase` in Android Studio and integrate Firebase services with your project.

6. **Build and Run**:
    - Build the project and run it on your preferred emulator or Android device.

### Firebase Setup

1. **Firestore Database**:
    - Create collections such as `Users`, `Chats`, and `Messages`.
    - Define appropriate rules and permissions to secure data access.

2. **Realtime Database**:
    - Utilize for real-time updates and message synchronization.

3. **Firebase Authentication**:
    - Enable email/password authentication for user sign-up and login.

4. **Firebase Storage**:
    - Set up a storage bucket for user profile pictures and other media.

## 📝 Usage

1. **Sign Up**:
    - Open the app and navigate to the Sign Up screen.
    - Enter your email and password to create a new account.

2. **Log In**:
    - Use your registered email and password to log in.
    - Access the chat interface and start messaging other users.

3. **Forgot Password**:
    - If you forget your password, use the Forgot Password feature to reset it via email.

4. **Profile Management**:
    - Navigate to the profile section to update your profile picture and personal information.

5. **Chat**:
    - Select a user from the chat list and start sending messages.
    - Messages are updated in real-time, and you can see the delivery status.

## 🤝 Contributing

Contributions are welcome! Here’s how you can contribute:

1. Fork the repository.
2. Create a new feature branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Create a Pull Request.

## 🧑‍💻 Contact

For any queries or suggestions, feel free to reach out:

- **LinkedIn**: (https://www.linkedin.com/in/nirav-luhar-a3a2491a9/)

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Enjoy chatting! 😊
