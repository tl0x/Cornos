package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.base.Module;

public class NoFireOverlay extends Module {
    public NoFireOverlay() {
        super("NoFireOverlay", "Doesn't render the fire overlay", MType.MISC);
    }
    // Logic: WorldRenderMixin.java, again
}
