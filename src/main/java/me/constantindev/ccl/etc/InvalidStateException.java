package me.constantindev.ccl.etc;

public class InvalidStateException extends Exception {
    public InvalidStateException(String propname, String propvalue) {
        super("Field " + propname + " has invalid state " + propvalue + ". If this was caused upon RAM modification, do not report this.");
    }
}
