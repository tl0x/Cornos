package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import net.minecraft.client.render.debug.PathfindingDebugRenderer;
import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class Test extends Command {

    public Test() {
        super("Test", "bruh", new String[]{"test"});
    }

    @Override
    public void onExecute(String[] args) {
        //Cornos.minecraft.player.setPos(Double.NaN,Double.NaN,Double.NaN);
        Cornos.minecraft.player.playSound(Cornos.BONG_SOUND,1f,1f);
        super.onExecute(args);
    }
}
