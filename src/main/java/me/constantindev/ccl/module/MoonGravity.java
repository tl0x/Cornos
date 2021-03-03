package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;

public class MoonGravity extends Module {
    public MoonGravity() {
        super("MoonGravity", "Makes you go weee", MType.MOVEMENT);
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        Cornos.minecraft.player.addVelocity(0, 0.0568000030517578, 0); // Yes, this was precisely calculated.
        super.onExecute();
    }
}
