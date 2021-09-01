package banking;

import java.io.File;
import java.io.IOException;

import static banking.Connect.connect;

public class Main {
    public static void main(String[] args) {
        Bank bank= new Bank();
        File file = new File(args[1]);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bank.start();
    }
}