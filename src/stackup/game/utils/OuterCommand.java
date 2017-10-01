package stackup.game.utils;

public class OuterCommand {
    
    Command command = null;
    
    public void execute() {
        command.execute();
        command = null;
    }
    
    public Command getCommand() {
        return command;
    }
    
    public void setCommand(Command command) {
        this.command = command;
    }
}
