package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.Notification;
import me.constantindev.ccl.etc.base.Command;

public class Test extends Command {

    public Test() {
        super("Test", "bruh", new String[]{"test"});
    }

    @Override
    public void onExecute(String[] args) {

        super.onExecute(args);
    }
}
