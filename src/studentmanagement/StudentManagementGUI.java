package studentmanagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class StudentManagementGUI extends JFrame {
    private StudentManager manager;
    private JTabbedPane tabbedPane;
    
    // Components for Add Panel
    private JTextField addNameField, addAgeField, addCGPAField, addBranchField;
    private JButton addButton;
    private JLabel addStatusLabel;
    
    // Components for View Panel
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton viewRefreshButton;
    
    // Components for Search Panel
    private JTextField searchField;
    private JButton searchButton;
    private JTable searchTable;
    private DefaultTableModel searchTableModel;
    
    // Components for Update Panel
    private JTextField updateIdField, updateNameField, updateAgeField;
    private JTextField updateCGPAField, updateBranchField;
    private JButton updateButton, updateSearchButton;
    private JLabel updateStatusLabel;
    
    // Components for Delete Panel
    private JTextField deleteIdField;
    private JButton deleteButton;
    private JLabel deleteStatusLabel;
    
    // Components for Statistics Panel
    private JLabel totalStudentsLabel, avgCGPALabel, topStudentLabel;
    
    public StudentManagementGUI() {
        manager = new StudentManager();
        initializeFrame();
        createTabbedInterface();
    }
    
    private void initializeFrame() {
        setTitle("Student Management System v1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createTabbedInterface() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        
        tabbedPane.addTab("➕ Add Student", createAddPanel());
        tabbedPane.addTab("📋 View All", createViewPanel());
        tabbedPane.addTab("🔍 Search", createSearchPanel());
        tabbedPane.addTab("✏️ Update", createUpdatePanel());
        tabbedPane.addTab("🗑️ Delete", createDeletePanel());
        tabbedPane.addTab("📊 Statistics", createStatisticsPanel());
        
        add(tabbedPane);
    }
    
    private JPanel createAddPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        JLabel titleLabel = new JLabel("Add New Student");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        addNameField = new JTextField(20);
        panel.add(addNameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        addAgeField = new JTextField(20);
        panel.add(addAgeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Branch:"), gbc);
        gbc.gridx = 1;
        addBranchField = new JTextField(20);
        panel.add(addBranchField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("CGPA (0.0-10.0):"), gbc);
        gbc.gridx = 1;
        addCGPAField = new JTextField(20);
        panel.add(addCGPAField, gbc);
        
        // Add Button
        addButton = new JButton("Add Student");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.addActionListener(e -> addStudentAction());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);
        
        // Status Label
        addStatusLabel = new JLabel("");
        addStatusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        gbc.gridy = 6;
        panel.add(addStatusLabel, gbc);
        
        gbc.gridy = 10;
        panel.add(Box.createVerticalStrut(50), gbc);
        
        return panel;
    }
    
    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        
        // Title
        JLabel titleLabel = new JLabel("All Students");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Name", "Age", "Branch", "CGPA"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 11));
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh Button
        JPanel buttonPanel = new JPanel();
        viewRefreshButton = new JButton("🔄 Refresh");
        viewRefreshButton.setBackground(new Color(33, 150, 243));
        viewRefreshButton.setForeground(Color.WHITE);
        viewRefreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewRefreshButton.addActionListener(e -> refreshStudentTable());
        buttonPanel.add(viewRefreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load data on first load
        refreshStudentTable();
        
        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        
        // Search Panel
        JPanel searchInputPanel = new JPanel();
        searchInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchInputPanel.add(new JLabel("Search by Name:"));
        searchField = new JTextField(25);
        searchInputPanel.add(searchField);
        
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(76, 175, 80));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.addActionListener(e -> searchStudentAction());
        searchInputPanel.add(searchButton);
        
        panel.add(searchInputPanel, BorderLayout.NORTH);
        
        // Results Table
        String[] columns = {"ID", "Name", "Age", "Branch", "CGPA"};
        searchTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        searchTable = new JTable(searchTableModel);
        searchTable.setRowHeight(25);
        searchTable.setFont(new Font("Arial", Font.PLAIN, 11));
        searchTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(searchTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        JLabel titleLabel = new JLabel("Update Student");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);
        
        // Search Section
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        updateIdField = new JTextField(20);
        panel.add(updateIdField, gbc);
        
        updateSearchButton = new JButton("Search");
        updateSearchButton.setBackground(new Color(33, 150, 243));
        updateSearchButton.setForeground(Color.WHITE);
        updateSearchButton.addActionListener(e -> loadStudentForUpdate());
        gbc.gridx = 2;
        panel.add(updateSearchButton, gbc);
        
        // Update Fields
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        updateNameField = new JTextField(20);
        panel.add(updateNameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        updateAgeField = new JTextField(20);
        panel.add(updateAgeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Branch:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        updateBranchField = new JTextField(20);
        panel.add(updateBranchField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(new JLabel("CGPA:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        updateCGPAField = new JTextField(20);
        panel.add(updateCGPAField, gbc);
        
        // Update Button
        updateButton = new JButton("Update Student");
        updateButton.setBackground(new Color(255, 152, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 12));
        updateButton.addActionListener(e -> updateStudentAction());
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        panel.add(updateButton, gbc);
        
        // Status Label
        updateStatusLabel = new JLabel("");
        updateStatusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        gbc.gridy = 7;
        panel.add(updateStatusLabel, gbc);
        
        return panel;
    }
    
    private JPanel createDeletePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        JLabel titleLabel = new JLabel("Delete Student");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        deleteIdField = new JTextField(20);
        panel.add(deleteIdField, gbc);
        
        // Delete Button
        deleteButton = new JButton("Delete Student");
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.addActionListener(e -> deleteStudentAction());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(deleteButton, gbc);
        
        // Status Label
        deleteStatusLabel = new JLabel("");
        deleteStatusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        gbc.gridy = 3;
        panel.add(deleteStatusLabel, gbc);
        
        // Warning Label
        JLabel warningLabel = new JLabel("⚠️ This action cannot be undone!");
        warningLabel.setFont(new Font("Arial", Font.BOLD, 11));
        warningLabel.setForeground(new Color(244, 67, 54));
        gbc.gridy = 4;
        panel.add(warningLabel, gbc);
        
        return panel;
    }
    
    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        // Title
        JLabel titleLabel = new JLabel("Statistics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);
        
        // Total Students
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel statsLabel1 = new JLabel("Total Students:");
        statsLabel1.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(statsLabel1, gbc);
        
        totalStudentsLabel = new JLabel("0");
        totalStudentsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(totalStudentsLabel, gbc);
        
        // Average CGPA
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel statsLabel2 = new JLabel("Average CGPA:");
        statsLabel2.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(statsLabel2, gbc);
        
        avgCGPALabel = new JLabel("0.0");
        avgCGPALabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(avgCGPALabel, gbc);
        
        // Top Student
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel statsLabel3 = new JLabel("Top Student:");
        statsLabel3.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(statsLabel3, gbc);
        
        topStudentLabel = new JLabel("N/A");
        topStudentLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(topStudentLabel, gbc);
        
        // Refresh Button
        JButton refreshStatsButton = new JButton("🔄 Refresh Statistics");
        refreshStatsButton.setBackground(new Color(33, 150, 243));
        refreshStatsButton.setForeground(Color.WHITE);
        refreshStatsButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshStatsButton.addActionListener(e -> updateStatistics());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(refreshStatsButton, gbc);
        
        // Initial load
        updateStatistics();
        
        return panel;
    }
    
    // Action Methods
    
    private void addStudentAction() {
        try {
            String name = addNameField.getText().trim();
            String ageStr = addAgeField.getText().trim();
            String branch = addBranchField.getText().trim();
            String cgpaStr = addCGPAField.getText().trim();
            
            if (name.isEmpty() || ageStr.isEmpty() || branch.isEmpty() || cgpaStr.isEmpty()) {
                addStatusLabel.setText("❌ All fields are required!");
                addStatusLabel.setForeground(new Color(244, 67, 54));
                return;
            }
            
            int age = Integer.parseInt(ageStr);
            double cgpa = Double.parseDouble(cgpaStr);
            
            manager.addStudent(name, age, branch, cgpa);
            
            addStatusLabel.setText("✅ Student added successfully!");
            addStatusLabel.setForeground(new Color(76, 175, 80));
            
            // Clear fields
            addNameField.setText("");
            addAgeField.setText("");
            addBranchField.setText("");
            addCGPAField.setText("");
            
            // Refresh tables
            refreshStudentTable();
            
        } catch (NumberFormatException e) {
            addStatusLabel.setText("❌ Invalid number format!");
            addStatusLabel.setForeground(new Color(244, 67, 54));
        } catch (IllegalArgumentException e) {
            addStatusLabel.setText("❌ " + e.getMessage());
            addStatusLabel.setForeground(new Color(244, 67, 54));
        }
    }
    
    private void refreshStudentTable() {
        tableModel.setRowCount(0);
        List<Student> students = manager.getAllStudents();
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getBranch(),
                student.getCgpa()
            });
        }
    }
    
    private void searchStudentAction() {
        String keyword = searchField.getText().trim();
        searchTableModel.setRowCount(0);
        
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        List<Student> results = manager.searchByName(keyword);
        for (Student student : results) {
            searchTableModel.addRow(new Object[]{
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getBranch(),
                student.getCgpa()
            });
        }
        
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students found matching '" + keyword + "'", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void loadStudentForUpdate() {
        try {
            int id = Integer.parseInt(updateIdField.getText().trim());
            var student = manager.getStudentById(id);
            
            if (student.isPresent()) {
                Student s = student.get();
                updateNameField.setText(s.getName());
                updateAgeField.setText(String.valueOf(s.getAge()));
                updateBranchField.setText(s.getBranch());
                updateCGPAField.setText(String.valueOf(s.getCgpa()));
                updateStatusLabel.setText("✅ Student found and loaded");
                updateStatusLabel.setForeground(new Color(76, 175, 80));
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                updateStatusLabel.setText("❌ Student not found");
                updateStatusLabel.setForeground(new Color(244, 67, 54));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStudentAction() {
        try {
            int id = Integer.parseInt(updateIdField.getText().trim());
            String name = updateNameField.getText().trim();
            String ageStr = updateAgeField.getText().trim();
            String branch = updateBranchField.getText().trim();
            String cgpaStr = updateCGPAField.getText().trim();
            
            if (name.isEmpty() || ageStr.isEmpty() || branch.isEmpty() || cgpaStr.isEmpty()) {
                updateStatusLabel.setText("❌ All fields are required!");
                updateStatusLabel.setForeground(new Color(244, 67, 54));
                return;
            }
            
            int age = Integer.parseInt(ageStr);
            double cgpa = Double.parseDouble(cgpaStr);
            
            manager.updateStudent(id, name, age, branch, cgpa);
            
            updateStatusLabel.setText("✅ Student updated successfully!");
            updateStatusLabel.setForeground(new Color(76, 175, 80));
            
            refreshStudentTable();
            
        } catch (NumberFormatException e) {
            updateStatusLabel.setText("❌ Invalid number format!");
            updateStatusLabel.setForeground(new Color(244, 67, 54));
        } catch (IllegalArgumentException e) {
            updateStatusLabel.setText("❌ " + e.getMessage());
            updateStatusLabel.setForeground(new Color(244, 67, 54));
        }
    }
    
    private void deleteStudentAction() {
        try {
            int id = Integer.parseInt(deleteIdField.getText().trim());
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                manager.deleteStudent(id);
                deleteStatusLabel.setText("✅ Student deleted successfully!");
                deleteStatusLabel.setForeground(new Color(76, 175, 80));
                deleteIdField.setText("");
                refreshStudentTable();
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStatistics() {
        List<Student> students = manager.getAllStudents();
        totalStudentsLabel.setText(String.valueOf(students.size()));
        
        if (students.isEmpty()) {
            avgCGPALabel.setText("0.0");
            topStudentLabel.setText("N/A");
        } else {
            double avgCGPA = students.stream()
                .mapToDouble(Student::getCgpa)
                .average()
                .orElse(0.0);
            avgCGPALabel.setText(String.format("%.2f", avgCGPA));
            
            Student topStudent = students.stream()
                .max((s1, s2) -> Double.compare(s1.getCgpa(), s2.getCgpa()))
                .orElse(null);
            
            if (topStudent != null) {
                topStudentLabel.setText(topStudent.getName() + " (" + topStudent.getCgpa() + ")");
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementGUI frame = new StudentManagementGUI();
            frame.setVisible(true);
        });
    }
}
