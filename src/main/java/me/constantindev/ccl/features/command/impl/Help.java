package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.features.command.Command;
import me.constantindev.ccl.gui.screen.DocsScreen;

public class Help extends Command {
    public Help() {
        super("Help", "Shows all commands and modules", new String[]{"h", "help", "man", "?", "commands", "modules"});
    }

    @Override
    public void onExecute(String[] args) {
        new Thread(() -> {
            STL.sleep(10);
            Cornos.minecraft.openScreen(new DocsScreen(true));
        }).start();
        super.onExecute(args);
    }
}
