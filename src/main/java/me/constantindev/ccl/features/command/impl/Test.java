package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.etc.render.Notification;
import me.constantindev.ccl.features.command.Command;

public class Test extends Command {

    public Test() {
        super("Test", "bruh", new String[]{"test"});
    }

    @Override
    public void onExecute(String[] args) {
        for (int i = 0; i < 10; i++) {
            String[] s = new String[i];
            for (int ii = 0; ii < i; ii++) s[ii] = "amogs";
            Notification.create("Among", s, 10000);
        }
        super.onExecute(args);
    }


}
