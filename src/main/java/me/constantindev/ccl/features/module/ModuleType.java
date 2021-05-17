package me.constantindev.ccl.features.module;

public enum ModuleType {
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

    ModuleType(String name) {
        this.n = name;
    }

    public String getN() {
        return n;
    }
}
