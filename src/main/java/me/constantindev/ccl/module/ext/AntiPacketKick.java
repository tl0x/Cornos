package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.ModuleType;

public class AntiPacketKick extends Module {
    public AntiPacketKick() {
        super("AntiPacketKick", "Prevents you from being affected by chunk bans or similar", ModuleType.EXPLOIT);
    }
}
