package me.constantindev.ccl.etc.ms;

import me.constantindev.ccl.Cornos;
import net.minecraft.client.util.InputUtil;

public class KeyBind {
    public int keycode;
    boolean pressedbefore = false;

    public KeyBind(int kc) {
        this.keycode = kc;
    }

    public boolean isHeld() {
        if (keycode < 0) return false;
        return InputUtil.isKeyPressed(Cornos.minecraft.getWindow().getHandle(), keycode) && Cornos.minecraft.currentScreen == null;
    }

    public boolean isPressed() {
        if (keycode < 0) return false;
        boolean flag1 = InputUtil.isKeyPressed(Cornos.minecraft.getWindow().getHandle(), keycode);
        if (flag1 && !pressedbefore) {
            pressedbefore = true;
            return true;
        }
        if (!flag1) pressedbefore = false;
        return false;
    }
}
