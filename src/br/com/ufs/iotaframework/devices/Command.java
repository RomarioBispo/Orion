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
     * @param type  It must be 'command'.Command representation depends on protocol.
     */

    public Command(String name, String type) {
        this.name = name;
        this.type = type;
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

    public void setType(String type) {
        this.type = type;
    }
}
