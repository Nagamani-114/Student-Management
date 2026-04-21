package studentmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private int nextId = 1;

    // ─── CREATE ───────────────────────────────────────────────
    public Student addStudent(String name, int age, String branch, double cgpa) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (age < 17 || age > 30) {
            throw new IllegalArgumentException("Age must be between 17 and 30.");
        }
        if (cgpa < 0.0 || cgpa > 10.0) {
            throw new IllegalArgumentException("CGPA must be between 0.0 and 10.0.");
        }
        Student s = new Student(nextId++, name.trim(), age, branch.trim(), cgpa);
        students.add(s);
        return s;
    }

    // ─── READ ALL ─────────────────────────────────────────────
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // ─── READ BY ID ───────────────────────────────────────────
    public Optional<Student> getStudentById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }

    // ─── SEARCH BY NAME ───────────────────────────────────────
    public List<Student> searchByName(String keyword) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    // ─── UPDATE ───────────────────────────────────────────────
    public boolean updateStudent(int id, String name, int age, String branch, double cgpa) {
        Optional<Student> opt = getStudentById(id);
        if (opt.isPresent()) {
            Student s = opt.get();
            s.setName(name.trim());
            s.setAge(age);
            s.setBranch(branch.trim());
            s.setCGPA(cgpa);
            return true;
        }
        return false;
    }

    // ─── DELETE ───────────────────────────────────────────────
    public boolean deleteStudent(int id) {
        return students.removeIf(s -> s.getId() == id);
    }

    // ─── STATS ────────────────────────────────────────────────
    public double getAverageCgpa() {
        return students.stream().mapToDouble(Student::getCgpa).average().orElse(0.0);
    }

    public Optional<Student> getTopStudent() {
        return students.stream().max((a, b) -> Double.compare(a.getCgpa(), b.getCgpa()));
    }

    public int getTotalCount() {
        return students.size();
    }
}
