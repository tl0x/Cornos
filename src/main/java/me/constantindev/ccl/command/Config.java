package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ModuleConfig;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.ArrayUtils;

public class Config extends Command {
    public Config() {
        super("Config", "Changes configuration of modules", new String[]{"config", "conf"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 0) {
            ClientHelper.sendChat(Formatting.RED + "Please provide a module's name, a config key and a value to set.");
        } else if (args.length == 1) {
            Module m = ModuleRegistry.getByName(args[0]);
            if (m == null) {
                ClientHelper.sendChat(Formatting.RED + "Module " + args[0] + " does not exist.");
                return;
            }
            ClientHelper.sendChat("Configuration of " + m.name);
            m.mconf.config.forEach(configKey -> ClientHelper.sendChat("  " + configKey.key + ": " + configKey.value));
        } else if (args.length == 2) {
            Module m = ModuleRegistry.getByName(args[0]);
            if (m == null) {
                ClientHelper.sendChat(Formatting.RED + "Module " + args[0] + " does not exist.");
                return;
            }
            ModuleConfig.ConfigKey cfk = m.mconf.getByName(args[1]);
            if (cfk == null) {
                ClientHelper.sendChat(Formatting.RED + "Config key not found.");
                return;
            }
            ClientHelper.sendChat(cfk.key + ": " + cfk.value);
        } else {
            Module m = ModuleRegistry.getByName(args[0]);
            if (m == null) {
                ClientHelper.sendChat(Formatting.RED + "Module " + args[0] + " does not exist.");
                return;
            }
            ModuleConfig.ConfigKey cfk = m.mconf.getOrDefault(args[1], new ModuleConfig.ConfigKey(args[1], "0"));
            cfk.setValue(String.join(" ", ArrayUtils.subarray(args, 2, args.length)));
        }
    }
}
