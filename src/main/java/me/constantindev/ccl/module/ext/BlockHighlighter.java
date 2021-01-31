package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ModuleConfig;
import me.constantindev.ccl.etc.helper.ClientHelper;

public class BlockHighlighter extends Module {
    public BlockHighlighter() {
        super("BlockHighlighter", "Highlights a specific block you set");
        this.mconf.add(new ModuleConfig.ConfigKey("pos", "0 0 0"));
    }

    @Override
    public void onExecute() {
        String bpos = this.mconf.getByName("pos").value;
        if (bpos.split(" ").length != 3) {
            ClientHelper.sendChat("BlockHighlighter was misconfigured. \"pos\" should be \"<x> <y> <z>\"");
            this.mconf.getByName("pos").setValue("0 0 0");
        }
        // Actual logic in WorldRenderMixin.java
        super.onExecute();
    }
}
