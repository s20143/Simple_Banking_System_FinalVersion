package banking;

public class BankAccount extends Bank{
    private String cardNumber;
    private String pin;
    private int balance;

    public BankAccount() {
        balance = 0;
    }
    public void create(){
        for (int i = 0; i < 2 ; i++) {
            StringBuilder creatorOfNumbers = new StringBuilder();
            if(i == 0) {
                creatorOfNumbers.append("400000");
                for (int j = 0; j <10; j++) {
                    creatorOfNumbers.append((int)(Math.random()*10));
                }
                cardNumber = creatorOfNumbers.toString();
            }else {
                for (int j = 0; j < 4; j++) {
                    creatorOfNumbers.append((int)(Math.random()*10));
                }
                pin = creatorOfNumbers.toString();
            }
        }
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
