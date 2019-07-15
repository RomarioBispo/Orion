package br.com.ufs.iotaframework.devices;


import java.util.List;

/**
 * This class help to represent a command list in JSON format as java Object.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see Command
 */
public class ComandList {
    private List<Command> commands;

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public ComandList(List<Command> commands) {
        this.commands = commands;
    }
}
