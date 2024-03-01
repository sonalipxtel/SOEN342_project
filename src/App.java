import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class App {

    public static void main(String[] args) throws Exception {
        String jdbcUrl = "jdbc:sqlite:/C:\\SQLite\\sqlite-tools-win-x64-3450100\\usersdb.db";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            String sql = "SELECT * FROM registeredUsers";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String username = result.getString("username");
                System.out.println(username);
            }

        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

    }
}
