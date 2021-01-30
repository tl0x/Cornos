package me.constantindev.ccl.etc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.network.packet.c2s.play.CraftRequestC2SPacket;
import net.minecraft.recipe.Recipe;

public class Ctec {
    public static Thread createRunnerThread(CtecConfiguration conf) {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    if (MinecraftClient.getInstance().player == null) break;
                    if (MinecraftClient.getInstance().currentScreen == null) break;
                    if (!(MinecraftClient.getInstance().currentScreen instanceof CraftingScreen)) break;
                    int sync = MinecraftClient.getInstance().player.currentScreenHandler.syncId;
                    Thread.sleep((long) conf.delay);
                    MinecraftClient.getInstance().getNetworkHandler().sendPacket(new CraftRequestC2SPacket(sync, conf.r1, true));
                    Thread.sleep((long) conf.delay);
                    MinecraftClient.getInstance().getNetworkHandler().sendPacket(new CraftRequestC2SPacket(sync, conf.r2, true));
                } catch (Exception ignored) {
                }
            }
        });
        t.start();
        return t;
    }

    public static class CtecConfiguration {
        public Recipe<?> r1;
        public Recipe<?> r2;
        public float delay;

        public CtecConfiguration(Recipe<?> r1, Recipe<?> r2, float delay) {
            this.r1 = r1;
            this.r2 = r2;
            this.delay = delay;
        }
    }
}
