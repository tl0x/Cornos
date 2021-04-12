package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import net.minecraft.util.registry.Registry;

public class Test extends Command {

    public Test() {
        super("Test", "bruh", new String[]{"test"});
    }

    @Override
    public void onExecute(String[] args) {

        super.onExecute(args);
    }
}
