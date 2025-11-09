package commands;

import models.Transactions;

import java.util.ArrayList;
import java.util.List;

public class ViewAccountBalanceCommand extends Command {
    protected ArrayList<Transactions> transactionList;

    public ViewAccountBalanceCommand(ArrayList<Transactions> transactionList) {
        super("View account balance");
        this.transactionList = transactionList;
    }

    @Override
    public void execute() {
        List<Transactions> expenseList = getExpenseList(transactionList);
        List<Transactions> incomeList = getIncomeList(transactionList);

        double totalExpense = expenseList.stream()
                .mapToDouble(Transactions::getSum)
                .sum();

        double totalIncome = incomeList.stream()
                .mapToDouble(Transactions::getSum)
                .sum();

        double acountBalance = totalIncome + totalExpense;

        System.out.println("Total expense: " + totalExpense);
        System.out.println("Total income: " + totalIncome);
        System.out.println("Current account balance: " + acountBalance);

    }

    protected List<Transactions> getExpenseList(ArrayList<Transactions> transactionList) {
        return transactionList.stream()
                .filter(t -> t.getSum() < 0)
                .toList();
    }

    protected List<Transactions> getIncomeList(ArrayList<Transactions> transactionList) {
        return transactionList.stream()
                .filter(t -> t.getSum() > 0)
                .toList();
    }
}
