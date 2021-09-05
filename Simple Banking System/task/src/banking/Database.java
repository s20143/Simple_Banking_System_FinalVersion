package banking;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Database {

    static String url;

    public Database() {
        createNewTable();
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void toDbInsert(String cardNumber ,String pin,int balance){
        String sql = "INSERT INTO card (number,pin,balance) VALUES (?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,cardNumber);
            pstmt.setString(2,pin);
            pstmt.setInt(3,balance);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.getErrorCode();
        }
    }

    public void toDbDelete(BankAccount bankAccount){
        String sql = "DELETE FROM card WHERE number="+bankAccount.getCardNumber();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.getErrorCode();
        }
    }

    public void UpdateDatabase(int balance , String cardNumber){
        String sql = "UPDATE card SET balance ="+balance+" WHERE number = "+cardNumber;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.getErrorCode();
        }
    }
    public void UpdateDatabase(BankAccount bankAccountIn, BankAccount bankAccountOut){
        String sql = "UPDATE card SET balance ="+bankAccountIn.getBalance()+" WHERE number = "+bankAccountIn.getCardNumber();
        String sql1 = "UPDATE card SET balance ="+bankAccountOut.getBalance()+" WHERE number = "+bankAccountOut.getCardNumber();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.getErrorCode();
        }
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql1)) {
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.getErrorCode();
        }
    }

    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	number text NOT NULL,\n"
                + "	balance int,\n"
                + "	pin text DEFAULT 0\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void databaseCreateOrOpen(String fileName){
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void createURL(String fileName){
        url = "jdbc:sqlite:"+fileName;
    }
}
