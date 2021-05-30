package me.zeroX150.cornos.features.module.impl.external;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class AutoFemboy extends Module {
	public static Map<UUID, Integer> repository = new HashMap<>();

	public AutoFemboy() {
		super("AutoFemboy", "uwu owo uwu owo", ModuleType.FUN);
	}
}
