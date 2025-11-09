package commands;

import models.Transactions;
import utils.ReadTerminalInput;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterHistoryCommand extends Command {
    protected int choice = 0;
    protected String year;
    protected String month;
    ArrayList<Transactions> transactionList;
    String day;
    String week;
    ReadTerminalInput input = new ReadTerminalInput();

    public FilterHistoryCommand(ArrayList<Transactions> transactionList) {
        super("Filter transactions history");
        this.transactionList = transactionList;
    }

    @Override
    public void execute() {
        System.out.println("Select type of filter by selecting a number:");
        System.out.println("1. Year");
        System.out.println("2. Month");
        System.out.println("3. Day");
        System.out.println("4. Week");

        try {
            choice = input.intInput();
        } catch (Exception e) {
            System.out.println("Invalid choice. Select a number (1-4) representing the filter you want");
        }

        switch (choice) {
            case 1:
                year = SelectYear(); //TODO här är problem med int string--- måste fixas.. ta bort min week metod och ändra om i transactionsobjekten?
                streamAndFilter(transactionList, year, null, null, null);
                break;
            case 2:
                year = SelectYear();
                month = SelectDayMonthOrWeek("month");
                streamAndFilter(transactionList, year, month, null, null);
                break;
            case 3:
                year = SelectYear();
                month = SelectDayMonthOrWeek("month");
                day = SelectDayMonthOrWeek("day");
                streamAndFilter(transactionList, year, month, day, null);
                break;
            case 4:
                week = SelectDayMonthOrWeek("week");
                streamAndFilter(transactionList, "2025", null, null, week);
                break;
        }

    }

    public String SelectYear() {
        System.out.println("Type the year you want history (format yyyy): ");
        String year = "";
        Predicate<String> formatValidationString = s -> s.length() == 4;
        try {
            String sInput = input.stringInput();  //TODO lägga till samma logik i metoden nedanför? //Gard clauses !??
            if (formatValidationString.test(sInput)) {
                year = sInput;
            } else {
                System.out.println("Wrong format, year must be in format xxxx. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Wrong input. Number required");
        }
        return year;
    }

    public String SelectDayMonthOrWeek(String type) {
        String value = "";
        Predicate<String> formatValidationString = s -> s.length() <= 2 && !s.isEmpty();
        while (true) {
            System.out.println("Type the " + type + " you want history:");
            try {
                String sValue = input.stringInput();
                if (formatValidationString.test(sValue)) {
                    value = sValue;
                    break;
                } else {
                    System.out.println("Wrong format, your " + type + " must be in format xx. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Wrong input. Number required");
            }
        }
        return value;
    }

    private void streamAndFilter(ArrayList<Transactions> transactionList, String year, String month, String day, String week) {

        List<Transactions> matchingTransactions = transactionList.stream()
                .filter(t -> t.getYear().equals(year))
                .filter(t -> month == null || t.getMonth().equals(month))
                .filter(t -> day == null || t.getDay().equals(day))
                .filter(t -> week == null || t.getWeek().equals(week))
                .toList();

        if (matchingTransactions.isEmpty()) {
            System.out.println("No matching transactions was found");
        } else {
            matchingTransactions.forEach(t -> System.out.println(t));
        }

    }
}