package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;

public class Test extends Command {

    public Test() {
        super("Test", "bruh", new String[]{"test"});
    }

    @Override
    public void onExecute(String[] args) {
        //Cornos.minecraft.player.setPos(Double.NaN,Double.NaN,Double.NaN);
        super.onExecute(args);
    }
}
