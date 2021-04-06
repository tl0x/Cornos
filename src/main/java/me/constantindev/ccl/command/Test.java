package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.Notification;
import me.constantindev.ccl.etc.base.Command;

public class Test extends Command {

    public Test() {
        super("Test", "bruh", new String[]{"test"});
    }

    @Override
    public void onExecute(String[] args) {
        for (int i = 0; i < 40; i++) {
            Notification.create("Notif test", new String[]{"This will disappear in " + (i * 100) + "ms"}, i * 100);
        }
        super.onExecute(args);
    }
}
