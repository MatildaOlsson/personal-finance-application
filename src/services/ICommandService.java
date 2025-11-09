package services;

import commands.Command;

import java.util.ArrayList;

public interface ICommandService {
    void registerCommand(Command command);
    void executeCommand(int index, ArrayList<Command> commandArrayList);
}
