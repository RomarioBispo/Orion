package br.com.ufs.iotaframework.devices;

/**
 * This class help to represent a command in JSON format as java Object.
 *
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 * @see
 */
public class Command {
    private String name;
    private String type;

    /**
     * Attributes working as commands.
     *
     * @param name command identifier
     */

    public Command(String name) {
        this.name = name;
        this.type = "command";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
}
