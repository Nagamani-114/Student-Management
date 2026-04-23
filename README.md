# 🎓 Student Management System — Java + MySQL

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![IntelliJ](https://img.shields.io/badge/IntelliJ-IDEA-purple?style=for-the-badge&logo=intellijidea)
![Status](https://img.shields.io/badge/Status-Complete-green?style=for-the-badge)

> A console-based Student Management System built in Java with MySQL database, CSV import, Leaderboard, and Batch filtering — built as part of Auto Rabbit Java Internship preparation.

---

## ✨ Features

| Feature | Description |
|---|---|
| ➕ Add Student | Add student with Name, Age, Branch, CGPA, Batch |
| 📋 View All | View all students sorted A→Z alphabetically |
| 🔍 Search | Search students by name keyword |
| ✏️ Update | Update any student field by ID |
| 🗑️ Delete | Delete student from database by ID |
| 📊 Statistics | Total count, Average CGPA, Top student |
| 🎓 Batch Filter | Filter students by batch year (2026 / 2027) |
| 🏆 Leaderboard | Ranked by CGPA with 🥇🥈🥉 medals |
| 📂 CSV Import | Bulk import students from a `.csv` file |
| 💾 MySQL DB | All data saved permanently to MySQL |

---

## 🛠️ Tech Stack

- **Language** — Java 17
- **Database** — MySQL 8.x with JDBC
- **IDE** — IntelliJ IDEA Community
- **Concepts** — OOP, Collections, Streams, DAO Pattern, Singleton

---

## 📁 Project Structure

```
StudentManagementSystem/
└── src/
    └── studentmanagement/
        ├── Student.java              # Entity class — fields, getters, setters
        ├── StudentManager.java       # Business logic — CRUD, sort, filter
        ├── DatabaseConnection.java   # JDBC Singleton — MySQL connection
        ├── StudentDAO.java           # DAO — all SQL queries
        ├── CSVLoader.java            # CSV file reader and bulk inserter
        └── Main.java                 # Entry point — console menu
```

---

## 🚀 How to Run

### Step 1 — MySQL Setup

Open MySQL Workbench and run:

```sql
CREATE DATABASE student_db;
USE student_db;

CREATE TABLE students (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    age     INT NOT NULL,
    branch  VARCHAR(50)  NOT NULL,
    cgpa    DOUBLE       NOT NULL,
    batch   INT          NOT NULL
);
```

### Step 2 — Update Credentials

Open `DatabaseConnection.java` and update:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/student_db";
private static final String USER     = "root";
private static final String PASSWORD = "your_password_here";
```

### Step 3 — Add MySQL JDBC Driver

- Download `mysql-connector-j-8.x.jar` from [MySQL Downloads](https://dev.mysql.com/downloads/connector/j/)
- In IntelliJ: `File → Project Structure → Libraries → + → Add JAR`

### Step 4 — Run

- Right-click `Main.java` → **Run**

---

## 📡 Application Menu

```
┌─────────────────────────────┐
│         MAIN MENU           │
├─────────────────────────────┤
│  1. Add Student             │
│  2. View All Students       │
│  3. Search Student          │
│  4. Update Student          │
│  5. Delete Student          │
│  6. View Statistics         │
│  7. Filter by Batch         │
│  8. Leaderboard             │
│  9. Load from CSV           │
│  10. Exit                   │
└─────────────────────────────┘
```

---

## 📂 CSV Import Format

Create a file `students.csv`:

```csv
name,age,branch,cgpa,batch
Arjun Sharma,20,CSE,8.9,2026
Priya Reddy,21,ECE,9.1,2027
Rahul Verma,19,IT,7.5,2026
Sneha Patel,22,CSE,8.3,2027
```

Then choose **Option 9** from menu and enter the file path.

---

## 🧠 Concepts Demonstrated

| Concept | Where Used |
|---|---|
| **Encapsulation** | `Student.java` — private fields + getters/setters |
| **Immutable Field** | `id` has getter only — cannot be changed after creation |
| **Singleton Pattern** | `DatabaseConnection.java` — single DB connection |
| **DAO Pattern** | `StudentDAO.java` — separates SQL from business logic |
| **Java Streams** | `filter()`, `sorted()`, `map()`, `collect()` |
| **Comparator** | Alphabetical sort + CGPA leaderboard |
| **Optional\<T\>** | Safe null handling in `getById()` |
| **PreparedStatement** | Prevents SQL injection |
| **BufferedReader** | CSV file reading line by line |
| **Exception Handling** | Input validation with custom error messages |

---

## 📸 Sample Output

```
╔══════════════════════════════════════╗
║   Student Management System v2.0     ║
╚══════════════════════════════════════╝

🏆 ── LEADERBOARD (CGPA High → Low) ── 🏆
┌──────┬──────────────────────┬──────┬──────────────┬────────┬────────┐
│ ID   │ Name                 │ Age  │ Branch       │ CGPA   │ Batch  │
├──────┼──────────────────────┼──────┼──────────────┼────────┼────────┤
🥇 Rank 1: | 2    | Priya Reddy          | 21   | ECE          |   9.10 | 2027   |
🥈 Rank 2: | 1    | Arjun Sharma         | 20   | CSE          |   8.90 | 2026   |
🥉 Rank 3: | 4    | Sneha Patel          | 22   | CSE          |   8.30 | 2027   |
```

---


---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
