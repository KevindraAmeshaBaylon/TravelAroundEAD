# TravelAround - Hospitality & Tourism Management System

An enterprise-grade Java Swing desktop application developed for the hospitality and tourism sector. This system implements modern software engineering practices, utilizing a standard Model-View-Controller (MVC) design pattern and an integrated MySQL database to manage room operations and hotel references dynamically.

---

## 🚀 Key Features & Implementation Standards

* **Model-View-Controller (MVC) Architecture:** Separated cleanly into `model`, `view`, and `controller` packages to maximize maintainability and code reusability.
* **Advanced User Interfaces:** Built with fully responsive forms, customized data layouts, interactive tables, and data auto-selection utilities.
* **Robust CRUD Operations:** Features real-time connection lifecycle handling linked straight to a relational MySQL database.
* **Comprehensive Reporting:** Integrates Jasper Reports (`BookingSummaryReport`) compiling operational statistics pulled across multiple operational tables.
* **Error Resilience & Validation:** Enforces input numerical validation, type parsing mismatch catch frames, and utilizes a user-defined custom exception structure (`RoomUnavailableException`).

---
## 📺 Project Presentation & Demo Video
Click the link below to watch the full system walkthrough and execution video:

👉 **[Watch the System Demo Video Here](https://drive.google.com/file/d/1WYWv6t3T8TfL0kS-HP4d7s9aaX8XJC_2/view?usp=sharing)**

## 🛠️ Project Structure

```text
com.travelaround
│
├── config/             # Database connectivity layers (DBConnection.java)
├── controller/         # Application orchestrators (RoomController, HotelController, etc.)
├── exception/          # Custom exceptions (RoomUnavailableException.java)
├── main/               # Application bootstrap execution entry point
├── model/              # Data schemas & business object definitions (Hotel, Room, etc.)
├── model.report/       # Document layouts (Jasper engine configurations)
└── view/               # Desktop UI components (RoomManagementForm, DashboardForm, etc.)

📦 Deployment Formats Included
Per structural requirements, the deployment assets are organized as follows[cite: 1]:

Source Code Execution: Run via NetBeans IDE utilizing JDK environment parameters.

JAR Distribution: Stored inside the executable distribution cluster (/dist/TravelAround.jar).

Standalone Executable: Compiled setup architecture for Windows machines (TravelAround.exe).

⚙️ How to Get Started
Prerequisites
Java Development Kit (JDK) 8 or higher installed.

MySQL Server instance configured and active.

NetBeans IDE or preferred Java IDE.

Database Setup

Create a database named travelaround_db in your local MySQL instance.

Configure your server access credentials inside com/travelaround/config/DBConnection.java.

Import the system schema to populate target application tables (hotels, rooms, bookings, users, customers).

✒️ Development Summary
Developer Platform: NetBeans IDE[28].

Database Engine: MySQL Server[XAMPP Control Panel v3.3.0].

Reporting Engine: JasperReports Library[libraries are attached in the dist/ folder in a folder called lib/].

Version Control: Git Framework[GitHub].
