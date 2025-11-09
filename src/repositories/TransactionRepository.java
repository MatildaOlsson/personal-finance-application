package repositories;

import models.Transactions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TransactionRepository {

    public static File createNewFile(String fileName) {
        File userFile = new File(fileName + ".txt");

        try {
            if (userFile.createNewFile()) {
                System.out.println("New user and file was created: " + userFile.getAbsolutePath());
            } else {
                System.out.println("The user and file: " + fileName + ", already exists. File: " + userFile.getAbsolutePath());
            }
            return userFile;

        } catch (IOException e) {
            System.err.println("File was not able to be created" + e.getMessage());
            return null;
        }
    }

    public static void saveToFile(Transactions transaction, String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
        String sum = transaction.getSum() + "";
        String year = transaction.getYear() + "";

        writer
                .append("Amount: ")
                .append(sum)
                .append(" ")
                .append(transaction.getCurrency())
                .append("\n")
                .append("Date: ")
                .append(year) // TODO Se över så att filsparningen blir snyggare
                .append("-")
                .append(transaction.getMonth())
                .append("-")
                .append(transaction.getDay())
                .append("\n")
                .append("--------")
                .append("\n");

        System.out.println("The transaction was saved to file");

    } catch (IOException e) {
        System.out.println("Transaction was not able to be saved to file");
        e.printStackTrace();
    }
}
}
