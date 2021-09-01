package banking;

import java.io.File;
import java.io.IOException;

public class Database {

    static String url;

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
