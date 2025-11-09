package commands;

import models.Transactions;
import repositories.TransactionRepository;
import utils.ReadTerminalInput;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

public class RegisterTransactionCommand extends Command {
    protected ArrayList<Transactions> transactionList;
    protected File userFile;
    protected double sum;
    protected String type;
    protected String currency;
    protected String week;
    LocalDate today = LocalDate.now();
    protected int day = today.getDayOfMonth();
    protected int month = today.getMonthValue();
    protected int year = today.getYear();
    ReadTerminalInput input = new ReadTerminalInput();

    public RegisterTransactionCommand(File userFile, String type, String currency, ArrayList<Transactions> transactionlist) {
        super(type + " registration");
        this.userFile = userFile;
        this.type = type;
        this.currency = currency;
        this.transactionList = transactionlist;
    }

    protected static String getStringFormatOfDateValue(int dayMonthOrWeekValue) {
        String stringedValue;
        if (dayMonthOrWeekValue < 10 && dayMonthOrWeekValue > 0) {
            stringedValue = "0" + dayMonthOrWeekValue;
        } else {
            stringedValue = "" + dayMonthOrWeekValue;
        }
        return stringedValue;
    }

    @Override
    public void execute() {
        saveTransactionToFile(type, userFile.getName()); // I nuläget sparar den också till arraylist för säkerhet och för att all implementation inte är klar
    }

    protected void saveTransactionToFile(String type, String fileName) {
        double sum = registerSum(type);

        System.out.println("Do you want to add the transaction on today's date? (yes/no)");
        String choice = "";

        try {
            choice = input.stringInput();
        } catch (Exception e) {
            System.out.println("Wrong input. Try again with letters");
        }

        if (choice.contains("n")) {
            Transactions transactions = setOwnDateOnTransaction();
            transactions.setSum(sum);
            printTransaction(transactions);
            transactionList.add(transactions); // Sparar både till arraylist och till fil då jag inte hunnit implementera hela repositorit
            TransactionRepository.saveToFile(transactions, fileName);
        } else {
            String monthString = getStringFormatOfDateValue(month); //Default att registrera på dagens datum
            String dayString = getStringFormatOfDateValue(day);
            String yearString = getStringFormatOfDateValue(year);

            if (year == 2025) {
                week = weekOfDate2025(month, day);
            } else {
                week = "Not available";
            }
            Transactions transactions = new Transactions(sum, type, currency, yearString, monthString, dayString, week);
            printTransaction(transactions);
            transactionList.add(transactions);
            TransactionRepository.saveToFile(transactions, fileName);
        }
    }

    protected double registerSum(String type) {
        System.out.println("Please enter your " + type + ":"); //TODO While-loop även här?
        if (type.equalsIgnoreCase("income")) {
            try {
                sum = input.doubleInput();
            } catch (Exception e) {
                System.out.println("Error. Format needs to be numbers");
            }
        } else if (type.equalsIgnoreCase("expense")) {
            try {
                sum = input.doubleInput() * -1;
            } catch (Exception e) {
                System.out.println("Error. format needs to be numbers");
            }
        }
        return sum;
    }

    protected Transactions setOwnDateOnTransaction() {
        while (true) {
            System.out.println("Type year (format xxxx)");
            Predicate<Integer> formatValidationYear = i -> i > 1500 && i < 2500;
            try {
                int iInput = input.intInput();
                if (!formatValidationYear.test(iInput)) {
                    System.out.println("Wrong format of year, please enter a valid year (format xxxx)");
                } else {
                    year = iInput;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Wrong datatype, type numbers");

            }
        }

        while (true) {
            System.out.println("Type month (format mm)");
            Predicate<Integer> formatValidationMonth = i -> i > 0 && i < 13;
            try {
                int iInput = input.intInput();
                if (!formatValidationMonth.test(iInput)) {
                    System.out.println("Wrong input. Type the month using numbers");
                } else {
                    month = iInput;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Wrong datatype, use numbers");

            }
        }
        while (true) {
            System.out.println("Type day (format dd)");
            Predicate<Integer> formatValidationDay = i -> i > 0 && i < 32;

            try {
                int iInput = input.intInput();
                if (!formatValidationDay.test(iInput)) {
                    System.out.println("Wrong input. Type the day using numbers");
                } else {
                    day = iInput;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Wrong datatype, use numbers");
            }
        }

        if (year == 2025) {
            week = weekOfDate2025(month, day);   //
        } else {
            week = "Not available";
        }

        String monthString = getStringFormatOfDateValue(month);
        String dayString = getStringFormatOfDateValue(day);
        String yearString = getStringFormatOfDateValue(year);

        return new Transactions(sum, type, currency, yearString, monthString, dayString, week);
    }

    protected void printTransaction(Transactions transactions) {
        String dayString = getStringFormatOfDateValue(day);
        String monthString = getStringFormatOfDateValue(month);

        System.out.println("Your transaction: " + sum + " " + currency + " will be saved on date: " + year + "-" + monthString + "-" + dayString);
    }

    private String weekOfDate2025(int monthValue, int dayValue) {
        //Skapar en array med 13 platser där alla månaders dagar hårdkodas
        int[] dayOfMonth = new int[13];
        dayOfMonth[0] = 0;  //justerar index för att matcha månadsnummer
        dayOfMonth[1] = 25; //januari (från den 6e januari)
        dayOfMonth[2] = 28;
        dayOfMonth[3] = 31;
        dayOfMonth[4] = 30;
        dayOfMonth[5] = 31; // maj
        dayOfMonth[6] = 30;
        dayOfMonth[7] = 31;
        dayOfMonth[8] = 31;
        dayOfMonth[9] = 30;
        dayOfMonth[10] = 31;
        dayOfMonth[11] = 30; //november
        dayOfMonth[12] = 31;
        float totalDaysBeforeMonth = 0;
        int week = 0;

        //Loopar igenom alla månader innan aktuell månad för att få fram antal dagar
        for (int i = 0; i < monthValue; i++) {
            totalDaysBeforeMonth += dayOfMonth[i];
        }
        //Specialcase då första veckan i januari 2025 börjar en onsdag
        if (monthValue == 1 && dayValue <= 5) {
            week = 1;
        }
        //Spceicalräkning för januarimånad då den månaden inte följer samma mönster som övriga
        else if (monthValue == 1) {
            double sub = (totalDaysBeforeMonth + dayValue - 2) / 7;
            week = (int) Math.round(sub) + 1;
        }
        //Övriga månader år 2025
        else {
            double sub = (totalDaysBeforeMonth + dayValue + 4) / 7;
            week = (int) Math.round(sub) + 1;
        }
        return getStringFormatOfDateValue(week);
    }

}