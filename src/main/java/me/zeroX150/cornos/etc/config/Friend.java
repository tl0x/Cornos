package me.zeroX150.cornos.etc.config;

public class Friend {

    private final String realName;
    private String fakeName;

    public Friend(String realName, String fakeName) {
        this.realName = realName;
        this.fakeName = fakeName;
    }

    public String getRealName() {
        return realName;
    }

    public String getFakeName() {
        return fakeName;
    }

    public void setFakeName(String fakeName) {
        this.fakeName = fakeName;
    }
}
