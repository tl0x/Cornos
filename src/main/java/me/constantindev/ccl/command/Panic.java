package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Panic extends Command {
    public Panic() {
        super("Panic", "Turns off every module at once.", new String[]{"panic"});
    }

    @Override
    public void onExecute(String[] args) {
        for (Module m : ModuleRegistry.getAll()) {
            if (m.isOn.isOn()) {
                m.isOn.setState(false);
            }
        }
    }
}
