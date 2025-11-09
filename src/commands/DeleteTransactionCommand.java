package commands;

import models.Transactions;
import utils.ReadTerminalInput;

import java.util.ArrayList;


public class DeleteTransactionCommand extends Command {
    ArrayList<Transactions> transactionList;
    String currency;
    int choice = 0;

    ReadTerminalInput input = new ReadTerminalInput();

    public DeleteTransactionCommand(String currency, ArrayList<Transactions> transactionList) {
        super("Delete transaction");
        this.currency = currency;
        this.transactionList = transactionList;
    }

    @Override
    public void execute() {
        System.out.println("Please select the transaction you want to delete: ");
        for (int i = 0; i < transactionList.size(); i++) { //TODO ENHANCED LOOPS??
            System.out.println(i + 1 + ". " + transactionList.get(i).getSum() + " " + currency + " (" + transactionList.get(i).getYear() + "-" + transactionList.get(i).getMonth()+ "-" + transactionList.get(i).getDay() + ")");
        }
//        Scanner input = new Scanner(System.in);
// TODO IMPORTERA UTILS SOM SCANNER?

        try {
            choice = input.intInput() - 1;
        } catch (Exception e) {
            System.out.println("Somethinh went wrong");
        }

        try {
            if (choice > 0 || choice < transactionList.size()) {
                System.out.println("The transaction " + transactionList.get(choice).getSum() + " " + currency+ " will be deleted.");
                transactionList.remove(choice);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid input. There where no such transaction found");
        }
    }



    }



