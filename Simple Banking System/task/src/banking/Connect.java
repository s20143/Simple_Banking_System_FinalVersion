package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static banking.Database.url;

/**
 *
 * @author sqlitetutorial.net
 */
public class Connect {
    /**
     * Connect to a sample database
     */

    public static void connect() {
        Connection conn = null;
        try {
            // db parameters

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
