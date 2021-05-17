package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.features.command.Command;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleRegistry;

public class Panic extends Command {
    public Panic() {
        super("Panic", "Turns off every module at once.", new String[]{"panic"});
    }

    @Override
    public void onExecute(String[] args) {
        for (Module m : ModuleRegistry.getAll()) {
            if (m.isEnabled()) {
                m.setEnabled(false);
            }
        }
    }
}
