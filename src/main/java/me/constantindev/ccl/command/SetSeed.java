package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.ext.OreSim;

public class SetSeed extends Command {

    public SetSeed() {
        super(".SetSeed", "Specify your worldseed for OreSim", new String[]{"setseed"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 1 && ClientHelper.isLongValid(args[0])) {
            ((OreSim) ModuleRegistry.getByName("oresim")).setWorldSeed(Long.parseLong(args[0]));
        } else {
            ClientHelper.sendChat("You need to input a valid number example: .setseed -42069");
        }
        super.onExecute(args);
    }
}
