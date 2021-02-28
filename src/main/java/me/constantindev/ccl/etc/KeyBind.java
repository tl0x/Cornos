package me.constantindev.ccl.etc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

public class KeyBind {
    public int keycode;
    boolean pressedbefore = false;

    public KeyBind(int kc) {
        this.keycode = kc;
    }

    public boolean isPressed() {
        if (keycode < 0) return false;
        boolean flag1 = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), keycode);
        if (flag1 && !pressedbefore) {
            pressedbefore = true;
            return true;
        }
        if (!flag1) pressedbefore = false;
        return false;
    }
}
