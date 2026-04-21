package studentmanagement;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBconnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Nagamani@114";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
