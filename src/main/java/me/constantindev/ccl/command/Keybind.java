package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.helper.KeybindMan;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.etc.ms.KeyBind;
import me.constantindev.ccl.etc.reg.ModuleRegistry;

public class Keybind extends Command {
    public Keybind() {
        super("Keybind", "Manages keybinds for a module", new String[]{"keybinds", "keybind", "kb", "bind"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 0) {
            STL.notifyUser("All binds rn:");
            KeybindMan.binds.forEach((s, keyBind) -> STL.notifyUser("  " + s + " -> " + ((char) keyBind.keycode)));
        } else if (args.length == 1) {
            String modname = args[0];
            Module mod = ModuleRegistry.search(modname);
            if (mod == null) {
                STL.notifyUser("Im not sure if I know a module called \"" + modname + "\"");
                return;
            }
            KeyBind kb = KeybindMan.binds.get(mod.name);
            if (kb == null) {
                STL.notifyUser(mod.name + " does not have a bind set");
                return;
            }
            STL.notifyUser(mod.name + " has " + ((char) kb.keycode) + " as bind");
        } else {
            String modname = args[0];
            Module mod = ModuleRegistry.search(modname);
            if (mod == null) {
                STL.notifyUser("Im not sure if I know a module called \"" + modname + "\"");
                return;
            }
            mod.mconf.getByName("keybind").setValue(((int) args[1].toUpperCase().charAt(0)) + "");
            STL.notifyUser("Set " + mod.name + "'s keybind to " + args[1].toUpperCase().charAt(0));
            STL.notifyUser("Reloading keybinds");
            KeybindMan.reload();
        }
        super.onExecute(args);
    }
}
