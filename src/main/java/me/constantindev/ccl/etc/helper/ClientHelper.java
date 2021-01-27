package me.constantindev.ccl.etc.helper;

import me.constantindev.ccl.CornClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ClientHelper {
    public static void sendChat(String msg) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.of(Formatting.DARK_AQUA + "[ " + Formatting.AQUA + CornClient.MOD_ID.toUpperCase() + Formatting.DARK_AQUA + " ] " + Formatting.RESET + msg), false);
    }
}
