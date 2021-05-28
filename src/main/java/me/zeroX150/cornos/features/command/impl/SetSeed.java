package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.command.Command;
import me.zeroX150.cornos.features.module.ModuleRegistry;
import me.zeroX150.cornos.features.module.impl.external.OreSim;

public class SetSeed extends Command {

    public SetSeed() {
        super("SetSeed", "Specify your worldseed for OreSim", new String[]{"setseed"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 1 && STL.tryParseL(args[0])) {
            ((OreSim) ModuleRegistry.search(OreSim.class)).setWorldSeed(Long.parseLong(args[0]));
        } else {
            STL.notifyUser("You need to input a valid number example: .setseed -42069");
        }
        super.onExecute(args);
    }
}
