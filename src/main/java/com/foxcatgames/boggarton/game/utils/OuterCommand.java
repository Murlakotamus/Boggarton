package com.foxcatgames.boggarton.game.utils;

public class OuterCommand {

    private ICommand command;

    public void execute() {
        if (command == null)
            return;
        command.execute();
        command = null;
    }

    public ICommand getCommand() {
        return command;
    }

    public void setCommand(final ICommand command) {
        this.command = command;
    }
}
