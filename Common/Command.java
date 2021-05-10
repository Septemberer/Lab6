import java.io.Serializable;

public class Command implements Serializable {
    private static final long serialVersionUID = -45585686568324L;
    private String command;
    private String argument;

    public Command(String command){
        String[] cmd = command.trim().split(" ");
        if (cmd.length >= 2){
            this.command = cmd[0].trim();
            this.argument = cmd[cmd.length - 1].trim();
        }
        else {
            this.command = cmd[0].trim();
            this.argument = " ";
        }
    }

    public String getCommand(){
        return this.command;
    }

    public String getArgument(){
        return this.argument;
    }
}
