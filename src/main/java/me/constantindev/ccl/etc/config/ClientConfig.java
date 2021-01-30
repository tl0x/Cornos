package me.constantindev.ccl.etc.config;

public class ClientConfig {
    public static String chatPrefix;
    public static boolean blockNextMainScreenCall;

    public static void init() {
        chatPrefix = "}";
        blockNextMainScreenCall = true;
    }
}
