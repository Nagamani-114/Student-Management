package studentmanagement;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Main {

    static StudentManager manager = new StudentManager();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedData(); // Add sample students for demo
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   Student Management System v1.0     ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (true) {
            printMenu();
            int choice = readInt("Enter choice: ");
            System.out.println();
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> viewStats();
                case 7 -> loadCSV();
                case 8 -> { System.out.println("👋 Goodbye!"); return; }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }
    static void loadCSV() {
        System.out.print("Enter CSV file path: ");
        String path = sc.nextLine();

        manager.loadFromCSV(path);
    }

    // ─── MENU ─────────────────────────────────────────────────
    static void printMenu() {
        System.out.println("\n┌─────────────────────────────┐");
        System.out.println("│         MAIN MENU           │");
        System.out.println("├─────────────────────────────┤");
        System.out.println("│  1. Add Student             │");
        System.out.println("│  2. View All Students       │");
        System.out.println("│  3. Search Student          │");
        System.out.println("│  4. Update Student          │");
        System.out.println("│  5. Delete Student          │");
        System.out.println("│  6. View Statistics         │");
        System.out.println("│  7. Load from CSV           │");
        System.out.println("│  8. exit                    │");
        System.out.println("└─────────────────────────────┘");
    }

    // ─── ADD ──────────────────────────────────────────────────
    static void addStudent() {
        System.out.println("── Add New Student ──");
        try {
            System.out.print("Name   : "); String name = sc.nextLine();
            int age = readInt("Age    : ");
            System.out.print("Branch : "); String branch = sc.nextLine();
            double cgpa = readDouble();

            manager.addStudent(name, age, branch, cgpa);

            System.out.println("✅ Student added successfully!");
            System.out.println("👉 ID is auto-generated in database");

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // ─── VIEW ALL ─────────────────────────────────────────────
    static void viewAllStudents() {
        List<Student> list = manager.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("📭 No students found.");
            return;
        }
        printHeader();
        list.forEach(System.out::println);
        printFooter(list.size());
    }

    // ─── SEARCH ───────────────────────────────────────────────
    static void searchStudent() {
        System.out.print("Search by name: ");
        String keyword = sc.nextLine();
        List<Student> results = manager.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("🔍 No students found matching '" + keyword + "'.");
        } else {
            printHeader();
            results.forEach(System.out::println);
            printFooter(results.size());
        }
    }

    // ─── UPDATE ───────────────────────────────────────────────
    static void updateStudent() {
        int id = readInt("Enter Student ID to update: ");
        Optional<Student> opt = manager.getStudentById(id);
        if (opt.isEmpty()) {
            System.out.println("❌ Student ID " + id + " not found.");
            return;
        }
        Student existing = opt.get();
        System.out.println("Current: " + existing);
        try {
            System.out.print("New Name   [" + existing.getName() + "]: ");
            String name = sc.nextLine();
            if (name.isBlank()) name = existing.getName();

            int age = readIntOptional("New Age    [" + existing.getAge() + "]: ", existing.getAge());

            System.out.print("New Branch [" + existing.getBranch() + "]: ");
            String branch = sc.nextLine();
            if (branch.isBlank()) branch = existing.getBranch();

            double cgpa = readDoubleOptional("New CGPA   [" + existing.getCgpa() + "]: ", existing.getCgpa());

            manager.updateStudent(id, name, age, branch, cgpa);
            System.out.println("✅ Student updated successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // ─── DELETE ───────────────────────────────────────────────
    static void deleteStudent() {
        int id = readInt("Enter Student ID to delete: ");
        if (manager.deleteStudent(id)) {
            System.out.println("✅ Student ID " + id + " deleted.");
        } else {
            System.out.println("❌ Student ID " + id + " not found.");
        }
    }

    // ─── STATS ────────────────────────────────────────────────
    static void viewStats() {
        System.out.println("── Statistics ──");
        System.out.println("Total Students : " + manager.getTotalCount());
        System.out.printf("Average CGPA   : %.2f%n", manager.getAverageCgpa());
        manager.getTopStudent().ifPresent(s ->
                System.out.println("Top Student    : " + s.getName() + " (CGPA: " + s.getCgpa() + ")")
        );
    }

    // ─── HELPERS ──────────────────────────────────────────────
    static void printHeader() {
        System.out.println("┌──────┬──────────────────────┬──────┬──────────────┬────────┐");
        System.out.println("│ ID   │ Name                 │ Age  │ Branch       │ CGPA   │");
        System.out.println("├──────┼──────────────────────┼──────┼──────────────┼────────┤");
    }

    static void printFooter(int count) {
        System.out.println("└──────┴──────────────────────┴──────┴──────────────┴────────┘");
        System.out.println("  Total: " + count + " student(s)");
    }

    static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Please enter a valid number.");
            }
        }
    }

    static double readDouble() {
        while (true) {
            try {
                System.out.print("CGPA   : ");
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Please enter a valid decimal number.");
            }
        }
    }

    static int readIntOptional(String prompt, int defaultVal) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        if (input.isBlank()) return defaultVal;
        try { return Integer.parseInt(input); }
        catch (NumberFormatException e) { return defaultVal; }
    }

    static double readDoubleOptional(String prompt, double defaultVal) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        if (input.isBlank()) return defaultVal;
        try { return Double.parseDouble(input); }
        catch (NumberFormatException e) { return defaultVal; }
    }

    // ─── SEED DATA ────────────────────────────────────────────
    static void seedData() {
        manager.addStudent("Arjun Sharma", 20, "CSE", 8.9);
        manager.addStudent("Priya Reddy", 21, "ECE", 9.1);
        manager.addStudent("Rahul Verma", 19, "IT", 7.5);
        manager.addStudent("Sneha Patel", 22, "CSE", 8.3);
    }
}
