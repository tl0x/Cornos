package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.command.Command;
import net.minecraft.util.math.BlockPos;

public class Filler extends Command {
    public Filler() {
        super("Filler", "Config for the module", new String[]{"filler", "fillerconfig", "fconfig", "fconf"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 0) {
            STL.notifyUser("Action argument required");
            return;
        }
        BlockPos ppos = Cornos.minecraft.player.getBlockPos();
        switch (args[0].toLowerCase()) {
            case "pos1":
                me.zeroX150.cornos.features.module.impl.world.Filler.start = ppos;
                break;
            case "pos2":
                me.zeroX150.cornos.features.module.impl.world.Filler.end = ppos;
                break;
            default:
                STL.notifyUser("Invalid action. Valid actions: pos1, pos2");
        }
    }
}
