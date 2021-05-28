package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.features.command.Command;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleRegistry;

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
