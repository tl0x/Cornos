package me.constantindev.ccl.etc.ms;

public enum MType {
    MISC("Miscellaneous"),
    EXPLOIT("Exploit"),
    WORLD("World"),
    MOVEMENT("Movement"),
    COMBAT("Combat"),
    CRASH("Crash"),
    HIDDEN("Hidden"),
    RENDER("Render"),
    FUN("Fun");

    String n;

    MType(String name) {
        this.n = name;
    }

    public String getN() {
        return n;
    }
}
