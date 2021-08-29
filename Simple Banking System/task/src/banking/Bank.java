package banking;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Bank {
    Scanner sc;
    List<BankAccount> bankAccountList;

    public Bank() {
        this.sc = new Scanner(System.in);
        bankAccountList = new ArrayList<>();
    }

    public void start(){
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
                        System.out.println("Enter your card number:");
                        String inputNumber = sc.next();
                        if (inputNumber.length() != 16) {
                            System.out.println("This number is too short or too long!");
                            System.exit(0);
                        }
                        for (BankAccount bankAccountCheck:
                                bankAccountList) {
                            if (bankAccountCheck.getCardNumber().equals(inputNumber)) {
                                System.out.println("Enter your PIN:");
                                String inputPin = sc.next();
                                if (inputPin.length() != 4) {
                                    System.out.println("This number is too short or too long!");
                                    System.exit(0);
                                }
                                if(!inputPin.equals(bankAccountCheck.getPin()))
                                    System.out.println("Wrong card number or PIN!");
                                else {
                                    while (true) {
                                        System.out.println("\n"+"You have successfully logged in!"+"\n");
                                        System.out.println("1. Balance\n" +
                                                "2. Log out\n" +
                                                "0. Exit");
                                        int choice = sc.nextInt();
                                        if (choice == 1)
                                            System.out.println("Balance " + bankAccountCheck.getBalance());
                                        else if (choice == 2) {
                                            System.out.println("\n"+"You have successfully logged in!"+"\n");
                                            break;
                                        } else {
                                            System.out.println("Bye!");
                                            System.exit(0);
                                        }
                                    }
                                }
                            }
                        }
                        break;

                        case 0:
                            System.exit(0);
                            System.out.println("Bye!");
                            break;

                            default:
                                System.err.println("Option " + input + " doesn't exist");
                                System.exit(0);
                                break;
            }
        }
    }
}

