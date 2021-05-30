package me.zeroX150.cornos.features.module.impl.movement;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class Sprint extends Module {
	public Sprint() {
		super("Sprint", "toggle sprint for poor people", ModuleType.MOVEMENT);
	}

	@Override
	public void onExecute() {
		assert Cornos.minecraft.player != null;
		if (Cornos.minecraft.options.keyForward.isPressed() && !Cornos.minecraft.options.keyBack.isPressed()
				&& !Cornos.minecraft.player.isSneaking() && !Cornos.minecraft.player.horizontalCollision) {
			Cornos.minecraft.player.setSprinting(true);
		}
		super.onExecute();
	}
}
