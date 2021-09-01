package banking;

import java.sql.*;

import static banking.Connect.url;

public class BankAccount extends Bank{


    private String cardNumber;
    private String pin;
    private int balance;

    public BankAccount() {
        this.balance = 0;
    }

    public BankAccount(String cardNumber, String pin, int balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public void create(){
        int counter = 0;

        for (int i = 0; i < 2 ; i++) {
            StringBuilder creatorOfNumbers = new StringBuilder();
            if(i == 0) {
                creatorOfNumbers.append("400000");
                for (int j = 7; j < 16; j++) {
                    int numberToAppend = (int)(Math.random()*10);
                    creatorOfNumbers.append(numberToAppend);
                    if(j % 2 == 1)
                        numberToAppend *= 2;
                    if(numberToAppend > 9)
                        numberToAppend -= 9;

                    counter += numberToAppend;
                }
                for (int j = 0; j < 10 ; j++) {
                    if((counter + j + 8) % 10 == 0)
                        creatorOfNumbers.append(j);
                }
                cardNumber = creatorOfNumbers.toString();
            }else {
                for (int j = 0; j < 4; j++) {
                    creatorOfNumbers.append((int)(Math.random()*10));
                }
                pin = creatorOfNumbers.toString();
            }
        }
        toDbInsert();
    }

    public void toDbInsert(){
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

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }
}
