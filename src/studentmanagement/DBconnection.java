package studentmanagement;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBconnection {
    private static final String URL = "jdbc:mysql://localhost:3306/DATABASE";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
