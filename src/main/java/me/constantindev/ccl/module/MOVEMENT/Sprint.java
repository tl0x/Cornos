package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.ms.MType;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Like toggle sprint but as a hack :)", MType.MOVEMENT);
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        if (Cornos.minecraft.options.keyForward.isPressed() && !Cornos.minecraft.options.keyBack.isPressed() && !Cornos.minecraft.player.isSneaking() && !Cornos.minecraft.player.horizontalCollision) {
            Cornos.minecraft.player.setSprinting(true);
        }
        super.onExecute();
    }
}
