package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class AntiPacketKick extends Module {
    public AntiPacketKick() {
        super("AntiPacketKick", "Prevents you from being affected by chunk bans or similar", ModuleType.EXPLOIT);
    }
}
