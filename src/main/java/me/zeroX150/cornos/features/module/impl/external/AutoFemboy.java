package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoFemboy extends Module {
    public static Map<UUID, Integer> repository = new HashMap<>();

    public AutoFemboy() {
        super("AutoFemboy", "uwu owo uwu owo", ModuleType.FUN);
    }

    @Override
    public String getContext() {
        return repository.size() + " femboys";
    }
}
