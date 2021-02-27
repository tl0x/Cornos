package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.base.Module;

public class NoFireOverlay extends Module {
    public NoFireOverlay() {
        super("NoFireOverlay", "Removes the ANNOYING BIG FIRE OVERLAY", MType.MISC);
    }
    // Logic: WorldRenderMixin.java, again
}
