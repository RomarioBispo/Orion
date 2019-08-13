package br.com.ufs.iotaframework.usersguide;

public class TestComand {

    private OnCommand ring;

    public TestComand() {
    }

    public TestComand(OnCommand on) {
        this.ring = on;
    }


    public OnCommand getOn() {
        return ring;
    }

    public void setOn(OnCommand on) {
        this.ring = on;
    }



}
