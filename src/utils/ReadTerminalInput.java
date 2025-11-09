package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ReadTerminalInput implements IReadInput {
    protected final Scanner input = new Scanner(System.in);


    @Override
    public int intInput() throws Exception{
        System.out.println("->");
        try {
            int inputInt = input.nextInt();
            input.nextLine();
            return inputInt;
        } catch (InputMismatchException e) {
            input.nextLine();
            throw e;
        }
    }

    @Override
    public String stringInput() throws Exception {
        System.out.println("->");
        String inputString = input.nextLine();
        return inputString;

    }

    @Override
    public double doubleInput() throws Exception{
        System.out.println("->");
        try {
            double inputDouble = input.nextDouble();
            input.nextLine();
            return inputDouble;
        } catch (InputMismatchException e) {
            input.nextLine();
            throw e;
        }
    }
}
