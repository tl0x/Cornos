package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class AntiPacketKick extends Module {
    public AntiPacketKick() {
        super("AntiPacketKick", "Prevents you from being affected by chunk bans or similar", ModuleType.EXPLOIT);
    }
}
