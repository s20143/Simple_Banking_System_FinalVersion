package banking;

import java.io.File;
import java.io.IOException;

import static banking.Connect.connect;
import static banking.Database.createURL;
import static banking.Database.databaseCreateOrOpen;

public class Main {
    public static void main(String[] args) {
        databaseCreateOrOpen(args[1]);
        createURL(args[1]);
        Bank bank= new Bank();
        bank.start();
    }
}