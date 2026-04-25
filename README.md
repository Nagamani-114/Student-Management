# Student Management System

A Java-based Student Management System with CRUD operations, MySQL database integration, CSV bulk import functionality, student analytics, and search features.

## Features

* Add Student
* View All Students
* Search Student by Name
* Update Student Details
* Delete Student
* View Student Statistics
* CSV File Import
* Input Validation
* MySQL Database Connectivity
* Console-based Interactive UI

---

# Technologies Used

* Java
* JDBC
* MySQL
* CSV File Handling
* Object-Oriented Programming

---

# Project Structure

```text
StudentManagement/
│
├── src/
│   ├── Main.java
│   ├── Student.java
│   ├── StudentManager.java
│   ├── DBConnection.java
│
├── students.csv
├── README.md
└── mysql-connector-j.jar
```

---

# Database Setup

## Create Database

```sql
CREATE DATABASE student_management;
```

## Use Database

```sql
USE student_management;
```

## Create Table

```sql
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    age INT,
    branch VARCHAR(50),
    cgpa DOUBLE
);
```

---

# MySQL Configuration

Update database credentials inside:

```text
DBConnection.java
```

Example:

```java
String url = "jdbc:mysql://localhost:3306/student_management";
String user = "root";
String password = "your_password";
```

---

# How to Run the Project

## Step 1: Clone Repository

```bash
git clone <repository-link>
```

## Step 2: Open Project

Open the project in:

* IntelliJ IDEA
* Eclipse
* VS Code

---

## Step 3: Add MySQL Connector

Download and add:

```text
mysql-connector-j.jar
```

to project libraries.

---

## Step 4: Compile and Run

Run:

```text
Main.java
```

---

# Main Menu

```text
1. Add Student
2. View All Students
3. Search Student
4. Update Student
5. Delete Student
6. View Statistics
7. Load from CSV
8. Exit
```

---

# CSV Import

Example CSV format:

```csv
name,age,branch,cgpa
Arjun,20,CSE,8.7
Priya,21,ECE,9.1
Rahul,19,IT,7.9
```

---

# Statistics Included

* Total Students
* Average CGPA
* Top Performing Student

---

# Concepts Used

* OOP Concepts
* Encapsulation
* Collections Framework
* Exception Handling
* File Handling
* JDBC Connectivity
* SQL Operations
* Optional Class
* Stream Processing

---

# Future Enhancements

* Spring Boot Integration
* Web-based Dashboard
* Authentication System
* REST API Development
* AI-based Student Performance Prediction
* IoT-based Attendance Tracking

---

# Author

Nagamani Taripi
B.Tech CSE (IoT)

Interested in:

* IoT
* AI
* Automation
* Innovative Software Solutions

---

# Output Preview

```text
╔══════════════════════════════════════╗
║   Student Management System v1.0    ║
╚══════════════════════════════════════╝
```

---

# License

This project is developed for learning, academic, and portfolio purposes.
