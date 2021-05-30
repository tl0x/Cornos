package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class XRAY extends Module {
	public XRAY() {
		super("XRAY", "Tired of stripmining?", ModuleType.WORLD);
	}

	@Override
	public void onEnable() {
		Cornos.minecraft.worldRenderer.reload();
		super.onEnable();
	}

	@Override
	public void onDisable() {
		Cornos.minecraft.worldRenderer.reload();
		super.onDisable();
	}
	// Logic: XrayHandler.java & AbstractBlockStateHook.java

}
