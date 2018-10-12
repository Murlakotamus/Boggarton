package com.foxcatgames.boggarton.game.utils;

public class OuterCommand {

    ICommand command = null;

    public void execute() {
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
