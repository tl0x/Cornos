package me.constantindev.ccl.etc.helper;

import me.constantindev.ccl.etc.KeyBind;
import me.constantindev.ccl.etc.reg.ModuleRegistry;

import java.util.HashMap;
import java.util.Map;

public class KeyBindManager {
    public static Map<String, KeyBind> binds = new HashMap<>();

    public static void init() {
        ModuleRegistry.getAll().forEach(module -> {
            if (!module.mconf.getByName("keybind").value.equals("-1")) {
                binds.put(module.name, new KeyBind(Integer.parseInt(module.mconf.getByName("keybind").value)));
            }
        });
    }

    public static void tick() {
        binds.forEach((s, keyBinding) -> {

            if (keyBinding.isPressed()) ModuleRegistry.getByName(s).setEnabled(!ModuleRegistry.getByName(s).isEnabled);
        });
    }
}
