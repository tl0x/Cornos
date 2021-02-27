package me.constantindev.ccl.etc;

public class MType {
    public static MType MISC = new MType("Miscellaneous");
    public static MType EXPLOIT = new MType("Exploit");
    public static MType WORLD = new MType("World");
    public static MType MOVEMENT = new MType("Movement");
    public static MType[] ALL = new MType[]{
            MISC,
            EXPLOIT,
            WORLD,
            MOVEMENT
    };
    String n;

    public MType(String name) {
        this.n = name;
    }

    @Override
    public String toString() {
        return this.n;
    }
}
