package me.constantindev.ccl.etc;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class ServerCrasherManager {
    public static int strength = 100;
    public static String mode = "";
    public static Thread runner = new Thread(() -> {
        while (true) {
            try {
                if (!ModuleRegistry.getByName("servercrasher").isEnabled) {
                    Thread.sleep(10);
                    continue;
                }
                if (MinecraftClient.getInstance().getNetworkHandler() == null) {
                    ModuleRegistry.getByName("servercrasher").isEnabled = false;
                    continue;
                }
                System.out.println("Crasher called");
                Random r = new Random();
                switch (mode) {
                    case "rotation":
                        int yaw = r.nextInt(65535);
                        int pitch = r.nextInt(65535);
                        MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookOnly(yaw, pitch, true));
                        break;
                    case "location":
                        double x = r.nextDouble();
                        double z = r.nextDouble();
                        assert MinecraftClient.getInstance().player != null;
                        BlockPos pp = MinecraftClient.getInstance().player.getBlockPos();
                        MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionOnly(pp.getX() + x, pp.getY(), pp.getZ() + z, true));
                        break;
                    case "biglocation":
                        double x1 = r.nextDouble() * 10;
                        double z1 = r.nextDouble() * 10;
                        assert MinecraftClient.getInstance().player != null;
                        BlockPos pp1 = MinecraftClient.getInstance().player.getBlockPos();
                        MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionOnly(pp1.getX() + x1, pp1.getY(), pp1.getZ() + z1, true));
                        break;
                }
                Thread.sleep(100 / strength - 1);
            } catch (Exception ignored) {
                ModuleRegistry.getByName("servercrasher").isEnabled = false;
            }
        }
    });
}
