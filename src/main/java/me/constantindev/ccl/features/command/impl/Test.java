package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.features.command.Command;

public class Test extends Command {

    public Test() {
        super("Test", "bruh", new String[]{"test"});
    }

    @Override
    public void onExecute(String[] args) {

        super.onExecute(args);
    }


}
