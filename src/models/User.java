package models;

import java.io.File;
import java.util.ArrayList;

public class User {
    protected String name;
    protected String currency;
    protected ArrayList<Transactions> transactions;
    protected File userFile;

    public User(String name, String currency, ArrayList<Transactions> transactions, File userFile) {
        this.name = name;
        this.currency = currency;
        this.transactions = transactions;
        this.userFile = userFile;
    }

    public String getName() {
        return name;
    }
    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }
    public File getUserFile() {
        return userFile;
    }
}
