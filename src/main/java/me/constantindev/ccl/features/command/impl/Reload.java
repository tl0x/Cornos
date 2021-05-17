/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Reload
# Created by constantin at 22:34, Mär 29 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.features.command.Command;
import me.constantindev.ccl.features.command.CommandRegistry;
import me.constantindev.ccl.features.module.ModuleRegistry;

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
