package services;

import commands.Command;

import java.util.ArrayList;

public class TerminalCommandService implements ICommandService {
    private final ArrayList<Command> listOfCommands = new ArrayList<>();


    @Override
    public void registerCommand(Command command) {
        this.listOfCommands.add(command);
    }


    @Override
    public void executeCommand(int index, ArrayList<Command> listOfCommands) {
        try {
            Command command = listOfCommands.get(index);
            command.execute();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Couldn't find a matching command, try again!");
        }
    }

    public ArrayList<Command> getListOfCommands() {
        return listOfCommands;
    }
}




