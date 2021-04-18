package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.ms.MType;

public class FancyChat extends Module {
    public FancyChat() {
        super("FancyChat", "makes your messages go fancy", MType.MISC);
    }
}
