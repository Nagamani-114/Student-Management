package studentmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.FileReader;
public class StudentManager {

    // ─── CREATE ───────────────────────────────────────────────
    public void addStudent(String name, int age, String branch, double cgpa) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (age < 17 || age > 30) {
            throw new IllegalArgumentException("Age must be between 17 and 30.");
        }
        if (cgpa < 0.0 || cgpa > 10.0) {
            throw new IllegalArgumentException("CGPA must be between 0.0 and 10.0.");
        }

        try (Connection con = DBconnection.getConnection()) {

            String query = "INSERT INTO students(name, age, branch, cgpa) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name.trim());
            ps.setInt(2, age);
            ps.setString(3, branch.trim());
            ps.setDouble(4, cgpa);

            ps.executeUpdate();
            System.out.println("Student added successfully");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ─── READ ALL ─────────────────────────────────────────────
    public List<Student> getAllStudents() {

        List<Student> list = new ArrayList<>();

        try (Connection con = DBconnection.getConnection()) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("branch"),
                        rs.getDouble("cgpa")
                ));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    // ─── READ BY ID ───────────────────────────────────────────
    public Optional<Student> getStudentById(int id) {

        try (Connection con = DBconnection.getConnection()) {

            String query = "SELECT * FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("branch"),
                        rs.getDouble("cgpa")
                ));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return Optional.empty();
    }

    // ─── SEARCH BY NAME ───────────────────────────────────────
    public List<Student> searchByName(String keyword) {

        List<Student> list = new ArrayList<>();

        try (Connection con = DBconnection.getConnection()) {

            String query = "SELECT * FROM students WHERE name LIKE ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("branch"),
                        rs.getDouble("cgpa")
                ));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    // ─── UPDATE ───────────────────────────────────────────────
    public boolean updateStudent(int id, String name, int age, String branch, double cgpa) {

        try (Connection con = DBconnection.getConnection()) {

            String query = "UPDATE students SET name=?, age=?, branch=?, cgpa=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name.trim());
            ps.setInt(2, age);
            ps.setString(3, branch.trim());
            ps.setDouble(4, cgpa);
            ps.setInt(5, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    // ─── DELETE ───────────────────────────────────────────────
    public boolean deleteStudent(int id) {

        try (Connection con = DBconnection.getConnection()) {

            String query = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    // ─── STATS ────────────────────────────────────────────────
    public double getAverageCgpa() {

        try (Connection con = DBconnection.getConnection()) {

            ResultSet rs = con.createStatement()
                    .executeQuery("SELECT AVG(cgpa) AS avg FROM students");

            if (rs.next()) return rs.getDouble("avg");

        } catch (Exception e) {
            System.out.println(e);
        }

        return 0.0;
    }

    public Optional<Student> getTopStudent() {

        try (Connection con = DBconnection.getConnection()) {

            ResultSet rs = con.createStatement()
                    .executeQuery("SELECT * FROM students ORDER BY cgpa DESC LIMIT 1");

            if (rs.next()) {
                return Optional.of(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("branch"),
                        rs.getDouble("cgpa")
                ));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return Optional.empty();
    }

    public int getTotalCount() {

        try (Connection con = DBconnection.getConnection()) {

            ResultSet rs = con.createStatement()
                    .executeQuery("SELECT COUNT(*) AS total FROM students");

            if (rs.next()) return rs.getInt("total");

        } catch (Exception e) {
            System.out.println(e);
        }

        return 0;
    }
    public void loadFromCSV(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isFirst = true;

            while ((line = br.readLine()) != null) {

                // skip header
                if (isFirst) {
                    isFirst = false;
                    continue;
                }

                String[] data = line.split(",");

                String name = data[0].trim();
                int age = Integer.parseInt(data[1].trim());
                String branch = data[2].trim();
                double cgpa = Double.parseDouble(data[3].trim());

                addStudent(name, age, branch, cgpa);
            }

            System.out.println("✅ CSV data loaded successfully");

        } catch (Exception e) {
            System.out.println("❌ Error reading CSV: " + e.getMessage());
        }
    }
}