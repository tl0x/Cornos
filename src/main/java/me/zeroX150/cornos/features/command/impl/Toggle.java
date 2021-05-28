package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.command.Command;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleRegistry;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", "Toggles a module either on or off, dependent on its current state", new String[]{"t", "toggle", "trigger", "on", "off"});
    }

    @Override
    public void onExecute(String[] args) {
        //ClientHelper.sendChat(String.join(" ",args));
        if (args.length == 0) {
            STL.notifyUser("Module not found bruh");
            return;
        }
        Module m = ModuleRegistry.search(args[0]);
        if (m == null) {
            STL.notifyUser("Module not found bruh");
            return;
        }
        m.setEnabled(!m.isEnabled());
    }
}
