package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class BuildLimit extends Module {
	public BuildLimit() {
		super("BuildLimit", "Allows you to build underneath the block you target", ModuleType.WORLD);
	}
	// Logic: PacketTryUseItemOnBlockHook.java
}
