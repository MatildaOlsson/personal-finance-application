package commands;


public abstract class Command {
    protected final String name;

    public Command(String name) {
        this.name = name;
    }

    public abstract void execute();


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}


