package me.zeroX150.cornos.features.module.impl.movement;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class MoonGravity extends Module {
	public MoonGravity() {
		super("MoonGravity", "Tired of being on earth?", ModuleType.MOVEMENT);
	}

	@Override
	public void onExecute() {
		assert Cornos.minecraft.player != null;
		Cornos.minecraft.player.addVelocity(0, 0.0568000030517578, 0); // Yes, this was precisely calculated.
		super.onExecute();
	}
}
