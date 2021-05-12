package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.ModuleType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoFemboy extends Module {
    public static Map<UUID, Integer> repository = new HashMap<>();

    public AutoFemboy() {
        super("AutoFemboy", "uwu owo uwu owo", ModuleType.FUN);
    }
}
