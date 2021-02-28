package me.constantindev.ccl.etc.helper;

import me.constantindev.ccl.Cornos;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ClientHelper {
    public static void sendChat(String msg) {
        assert Cornos.minecraft.player != null;
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
}
