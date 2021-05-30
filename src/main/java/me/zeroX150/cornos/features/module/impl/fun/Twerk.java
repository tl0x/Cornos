package me.zeroX150.cornos.features.module.impl.fun;

import java.util.concurrent.ThreadLocalRandom;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class Twerk extends Module {

	MConfToggleable randomize = new MConfToggleable("randomize", false);

	public Twerk() {
		super("Twerk", "shake your ass for the whole server", ModuleType.FUN);
		this.mconf.add(randomize);
	}

	@Override
	public void onExecute() {
		assert Cornos.minecraft.player != null;
		if (this.randomize.isEnabled()) {
			Cornos.minecraft.options.keySneak.setPressed(ThreadLocalRandom.current().nextBoolean());
		} else {
			Cornos.minecraft.options.keySneak.setPressed(!Cornos.minecraft.player.isSneaking());
		}
		super.onExecute();
	}
}
