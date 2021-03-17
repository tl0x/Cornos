package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;

public class NoFireOverlay extends Module {
    public NoFireOverlay() {
        super("NoFireOverlay", "Doesn't render the fire overlay", MType.RENDER);
    }
    // Logic: WorldRenderMixin.java, again
}
