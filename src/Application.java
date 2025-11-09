import commands.*;
import models.Transactions;
import models.User;
import services.TerminalCommandService;
import utils.ReadTerminalInput;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;


public class Application {
    TerminalCommandService terminalCommandService = new TerminalCommandService();
    ReadTerminalInput input = new ReadTerminalInput();

    String nameInput = "";
    String currencyInput = "";
    ArrayList<Transactions> transactions = new ArrayList<>();
    ArrayList<Command> commandoList = terminalCommandService.getListOfCommands();

    public void start() {

        User user = creatUser(transactions);

        terminalCommandService.registerCommand(new RegisterTransactionCommand(user.getUserFile(), "Income", currencyInput, user.getTransactions()));
        terminalCommandService.registerCommand(new RegisterTransactionCommand(user.getUserFile(), "Expense", currencyInput, user.getTransactions()));
        terminalCommandService.registerCommand(new DeleteTransactionCommand(currencyInput, user.getTransactions()));
        terminalCommandService.registerCommand(new ViewAccountBalanceCommand(user.getTransactions()));
        terminalCommandService.registerCommand(new ViewHistoryCommand(user.getTransactions()));
        terminalCommandService.registerCommand(new FilterHistoryCommand(user.getTransactions()));

        while (true) {
            System.out.println("====YOUR PERSONAL-FINANCE-APPLICATION====");
            System.out.println("Choice one of following commands:");
            //Kommandomenyn printas ut
            int i = 1;
            for (Command c : commandoList) {
                System.out.println(i + ". " + c.getName());
                i += 1;
            }
            System.out.println("7. Quit application");

            try {
                int choice = input.intInput();

                if (choice == 7)
                    return;
                else {
                    int index = choice - 1;
                    terminalCommandService.executeCommand(index, commandoList);
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input. Try again using a number");
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
        }
    }

    protected User creatUser(ArrayList<Transactions> transactionList) {
        System.out.println("Enter name:");
        try {
            nameInput = input.stringInput();
        } catch (Exception e) {
            System.out.println("Something went wrong. Enter a name using letters");
        }
        System.out.println("Enter currency: ");
        try {
            currencyInput = input.stringInput();
        } catch (Exception e) {
            System.out.println("Something went wrong. Enter your currency");
        }

        File userFile = repositories.TransactionRepository.createNewFile(nameInput);
        User user = new User(nameInput, currencyInput, transactionList, userFile);
        return user;
    }
}
