package me.constantindev.ccl.features.module.impl.movement;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "toggle sprint for poor people", ModuleType.MOVEMENT);
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
