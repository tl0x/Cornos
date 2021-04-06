package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.reg.CommandRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class RemovePlayer extends Command {
    public RemovePlayer() {
        super("RemovePlayer", "Removes the player you are looking at (CLIENT SIDE!)",new String[]{"rmplayer","removeplayer"});
    }

    @Override
    public void onExecute(String[] args) {
        HitResult hr = Cornos.minecraft.crosshairTarget;
        if (!(hr instanceof EntityHitResult) || ((EntityHitResult) hr).getEntity() instanceof PlayerEntity) {
            ClientHelper.sendChat("Not sure if you are looking at a player");
            return;
        }
        ((EntityHitResult) hr).getEntity().remove();
        super.onExecute(args);
    }
}
