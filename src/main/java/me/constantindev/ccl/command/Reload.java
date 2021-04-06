/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Reload
# Created by constantin at 22:34, Mär 29 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.reg.CommandRegistry;
import me.constantindev.ccl.etc.reg.ModuleRegistry;

public class Reload extends Command {
    public Reload() {
        super("Reload", "Reloads all commands and modules", new String[]{"reload", "rl", "mcrl"});
    }

    @Override
    public void onExecute(String[] args) {
        ModuleRegistry.reload();
        CommandRegistry.reload();
        super.onExecute(args);
    }
}
