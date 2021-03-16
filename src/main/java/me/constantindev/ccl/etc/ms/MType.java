package me.constantindev.ccl.etc.ms;

public enum MType {
    MISC("Miscellaneous"),
    EXPLOIT("Exploit"),
    WORLD("World"),
    MOVEMENT("Movement"),
    COMBAT("Combat"),
    CRASH("Crash");

    String n;

    MType(String name) {
        this.n = name;
    }

    public String getN() {
        return n;
    }
}
