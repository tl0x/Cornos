package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.etc.reg.ModuleRegistry;

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
