package banking;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static banking.Database.url;

public class BankAccount extends Bank{

    private Scanner sc;
    private String cardNumber;
    private String pin;
    private int balance;

    public BankAccount() {
        this.balance = 0;
        this.sc = new Scanner(System.in);
    }

    public BankAccount(String cardNumber, String pin, int balance) {
        this.sc = new Scanner(System.in);
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

    public void transfer(String cardNumber, List<BankAccount> bankAccountList){
        int inputTransferMoney;
        if(!checkLuhn(cardNumber)){
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        }
        if(bankAccountList.stream().noneMatch(bankAccount -> bankAccount.getCardNumber().equals(cardNumber))){
            System.out.println("Such a card does not exist.");
            return;
        }
        System.out.println("Enter how much money you want to transfer:");
        inputTransferMoney= sc.nextInt();
        if(inputTransferMoney > this.getBalance()) {
            System.out.println("Not enough money!");
            return;
        }
        this.setBalance(this.getBalance() -inputTransferMoney);
        BankAccount bankAccount = Objects.requireNonNull(bankAccountList
                        .stream()
                        .filter(f -> f.getCardNumber().equals(cardNumber))
                        .findFirst()
                        .orElse(null));
        bankAccount.setBalance(bankAccount.getBalance()+inputTransferMoney);
        db.UpdateDatabase(this,bankAccount);
    }

    public boolean checkLuhn(String cardNumber){
        int sum =0;
        if (cardNumber.length() != 16)
            return false;
        for (int i = 0; i <cardNumber.length()-1 ; i++) {
            String ch = String.valueOf(cardNumber.charAt(i));
            int j = Integer.parseInt(ch);

            if((i+1) % 2 == 1)
                j *= 2;
            if(j > 9)
                j -= 9;
            sum+= j;
        }
        String ch = String.valueOf(cardNumber.charAt(cardNumber.length()-1));
        int j = Integer.parseInt(ch);
        sum += j;

        return sum % 10 == 0;
    }

    public void addIncome(int income){
        this.balance += income;
        db.UpdateDatabase(this.balance, this.cardNumber);
    }

    public void toDbInsert(){
        db.toDbInsert(this.cardNumber,this.getPin(),this.getBalance());
    }

    public void toDbDelete(){
        db.toDbDelete(this);
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

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
