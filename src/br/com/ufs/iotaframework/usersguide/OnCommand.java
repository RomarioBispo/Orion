package br.com.ufs.iotaframework.usersguide;

public class OnCommand {
    private String type = "command";
    private String value;

    public OnCommand() {

    }
    public OnCommand(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
