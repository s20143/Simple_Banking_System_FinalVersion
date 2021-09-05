package banking;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static banking.Database.url;

public class Bank {
    private Scanner sc;
    private List<BankAccount> bankAccountList;
    static Database db;

    public Bank() {
        this.sc = new Scanner(System.in);
        this.db = new Database();
        this.bankAccountList = new ArrayList<>();
    }

    public void fillListofAccounts(){
        String sql = "SELECT number,pin,balance FROM card";
        try (Connection conn = db.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                bankAccountList.add(new BankAccount(rs.getString("number"), rs.getString("pin"),rs.getInt("balance")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start(){
        fillListofAccounts();
        menuGeneral();
    }

    public void menuGeneral(){
        while(true){
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            int input = 0;
            try {
                input = sc.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Wrong input");
            }
            switch (input) {
                case 1:
                    BankAccount bankAccount = new BankAccount();
                    bankAccount.create();
                    bankAccountList.add(bankAccount);
                    System.out.println("Your card number"+"\n"
                            +bankAccount.getCardNumber()+"\n"
                            +"Your card PIN"+"\n"
                            +bankAccount.getPin());
                    break;

                case 2:
                    menuClient();
                    break;

                case 0:
                    exit();
                    break;

                default:
                    System.err.println("Option " + input + " doesn't exist");
                    break;
            }
        }
    }

    public void menuClient(){
        System.out.println("Enter your card number:");
        String inputNumber = sc.next();
        if (inputNumber.length() != 16) {
            System.out.println("This number is too short or too long!");
        }

        for (BankAccount bankAccountCheck:
                bankAccountList) {
            if (bankAccountCheck.getCardNumber().equals(inputNumber)) {
                System.out.println("Enter your PIN:");
                String inputPin = sc.next();
                if (inputPin.length() != 4) {
                    System.out.println("This number is too short or too long!");
                }
                if(!inputPin.equals(bankAccountCheck.getPin()))
                    System.out.println("Wrong card number or PIN!");
                else {
                    System.out.println("\n"+"You have successfully logged in!"+"\n");
                    while (true) {
                        System.out.println("1. Balance\n" +
                                "2. Add income\n" +
                                "3. Do transfer\n" +
                                "4. Close account\n" +
                                "5. Log out\n" +
                                "0. Exit");
                     operationInMenu(bankAccountCheck);
                    }
                }
            }
        }
    }

    public void operationInMenu(BankAccount bankAccountCheck){
        int choice = sc.nextInt();
        switch(choice){
            case 1:
                System.out.println("Balance " + bankAccountCheck.getBalance());
                break;

                case 2:
                    System.out.println("Enter income:");
                    bankAccountCheck.addIncome(sc.nextInt());
                    System.out.println("Income was added!");
                    break;

                    case 3:
                        System.out.println("Transfer\n" +
                                "Enter card number:");
                        bankAccountCheck.transfer(sc.next(),this.bankAccountList);

                        break;

                        case 4:
                            bankAccountList.remove(bankAccountCheck);
                            bankAccountCheck.toDbDelete();
                            break;

                            case 5:
                                System.out.println("\n"+"You have successfully logged out!"+"\n");
                                menuGeneral();
                                break;

                                case 0:
                                    exit();
                                    break;

                                    default:
                                        System.out.println("This option does not exist! Try again");
                                        operationInMenu(bankAccountCheck);
                                        break;
        }
    }
    public void exit(){
        System.out.println("Bye!");
        System.exit(0);
    }
}

