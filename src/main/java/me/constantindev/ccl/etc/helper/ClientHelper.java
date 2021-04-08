package me.constantindev.ccl.etc.helper;

import me.constantindev.ccl.Cornos;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.lang.reflect.Field;

public class ClientHelper {
    public static void sendChat(String msg) {
        if (Cornos.minecraft.player == null) return;
        Cornos.minecraft.player.sendMessage(Text.of(Formatting.DARK_AQUA + "[ " + Formatting.AQUA + Cornos.MOD_ID.toUpperCase() + Formatting.DARK_AQUA + " ] " + Formatting.RESET + msg), false);
    }

    public static boolean isIntValid(String intToParse) {
        boolean isValid;
        try {
            Integer.parseInt(intToParse);
            isValid = true;
        } catch (Exception exc) {
            isValid = false;
        }
        return isValid;
    }

    public static void setField(Object t, String n1, String n2, Object value) {
        Field f;
        try {
            f = t.getClass().getField(n1);
        } catch (Exception exc) {
            try {
                f = t.getClass().getField(n2);
            } catch (Exception ignored) {
                return;
            }
        }
        if (!f.isAccessible()) f.setAccessible(true);
        try {
            f.set(t, value);
        } catch (Exception ignored) {
        }
    }
    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception ignored) {}
    }
}
